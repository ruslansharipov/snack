package ru.sharipov.snack.executor

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.sharipov.snack.command.SnackNavigationCommand
import ru.sharipov.snack.extensions.removeOnTimeout
import kotlin.collections.ArrayList

class SnackCommandExecutor<C: SnackNavigationCommand>(
    private val fragmentManager: FragmentManager,
    savedState: Bundle?
) {

    init {
        restoreState(savedState)
    }

    fun execute(command: C) {
        val transaction = fragmentManager.beginTransaction()

        val animations = command.animations
        if (animations != null) {
            transaction.setCustomAnimations(animations.enter, animations.exit)
        }

        val snack = command.createFragment()
        val snackStateEntity = SnackStateEntity(command.tag, command.timeoutMs, command.animations?.exit)
        snack.arguments?.putSerializable(SNACK_STATE_ENTITY_KEY, snackStateEntity)

        val tag = command.tag
        transaction.add(command.containerId, snack, tag)
        val timeoutMs = command.timeoutMs
        if (timeoutMs != null) {
            snack.removeOnTimeout(tag, timeoutMs, animations?.exit)
        }
        transaction.commit()
    }

    fun saveState(outState: Bundle?) {
        if (outState != null) {
            val savedStack = fragmentManager.fragments.mapNotNull { fragment: Fragment ->
                fragment.arguments?.getSerializable(SNACK_STATE_ENTITY_KEY) as? SnackStateEntity
            }
            outState.putSerializable(SNACK_COMMAND_EXECUTOR_STATE_KEY, ArrayList(savedStack))
        }
    }

    private fun restoreState(bundle: Bundle?) {
        val snackList = bundle?.getSerializable(SNACK_COMMAND_EXECUTOR_STATE_KEY) as? ArrayList<SnackStateEntity>
        if (!snackList.isNullOrEmpty()) {
            snackList.forEach { snackStateEntity: SnackStateEntity ->
                val (tag, timeLeftMs, exitAnimRes) = snackStateEntity
                if (timeLeftMs != null) {
                    val fragment = fragmentManager.findFragmentByTag(tag)
                    fragment?.removeOnTimeout(tag, timeLeftMs, exitAnimRes)
                }
            }
        }
    }

    internal companion object {
        const val SNACK_COMMAND_EXECUTOR_STATE_KEY = "SNACK_COMMAND_STATE_KEY"
        const val SNACK_STATE_ENTITY_KEY = "SNACK_COMMAND_KEY"
    }
}