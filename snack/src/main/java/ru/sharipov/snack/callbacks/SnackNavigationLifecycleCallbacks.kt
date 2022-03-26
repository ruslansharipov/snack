package ru.sharipov.snack.callbacks

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.sharipov.snack.SnackCommandExecutor
import ru.sharipov.snack.bus.SnackCommandBus
import ru.sharipov.snack.command.SnackNavigationCommand
import ru.sharipov.snack.command.SnackFragmentFactory

class SnackNavigationLifecycleCallbacks<C: SnackNavigationCommand>(
    snackCommandBus: SnackCommandBus<C>,
    private val snackFragmentFactory: SnackFragmentFactory<C>
): DefaultActivityLifecycleCallbacks() {

    private val handler = Handler(Looper.getMainLooper())

    private var navigationExecutor: SnackCommandExecutor<C>? = null
    private var activeActivity: Activity? = null
    private var pendingCommand: C? = null

    private var savedState: Bundle? = null

    init {
        GlobalScope.launch {
            snackCommandBus.observeSnackCommands()
                .flowOn(Dispatchers.Main)
                .collectLatest { navigationCommand: C ->
                    handleNewNavigationCommand(
                        command = navigationCommand,
                        executor = navigationExecutor,
                        activity = activeActivity
                    )
                }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        savedState = savedInstanceState
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        navigationExecutor?.saveState(outState)
    }

    override fun onActivityResumed(activity: Activity) {
        require(activity is AppCompatActivity)
        activeActivity = activity
        navigationExecutor = SnackCommandExecutor(activity.supportFragmentManager, snackFragmentFactory, savedState)
        savedState = null
    }

    override fun onActivityPostResumed(activity: Activity) {
        handleNewNavigationCommand(pendingCommand, navigationExecutor, activeActivity)
        pendingCommand = null
    }

    private fun handleNewNavigationCommand(
        command: C?,
        executor: SnackCommandExecutor<C>?,
        activity: Activity?
    ) {
        if (activity != null && activity.isFinishing) {
            pendingCommand = command
        } else if (executor != null && command != null) {
            handler.post {
                executor.execute(command)
            }
        }
    }
}