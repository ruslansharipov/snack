package ru.sharipov.snack.executor

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.sharipov.snack.command.SnackCommand
import ru.sharipov.snack.command.Snack
import ru.sharipov.snack.extensions.removeFragment
import ru.sharipov.snack.extensions.removeOnTimeout
import kotlin.collections.ArrayList

internal class SnackCommandExecutor<S: Snack>(
    private val fragmentManager: FragmentManager,
    savedState: Bundle?
) {

    init {
        restoreState(savedState)
    }

    fun execute(command: SnackCommand<S>) {
        when(command) {
            is SnackCommand.Close -> executeCloseCommand(command.snack)
            is SnackCommand.Open -> executeOpenCommand(command.snack)
        }

    }

    fun saveState(outState: Bundle?) {
        if (outState != null) {
            val savedStack = fragmentManager.fragments.mapNotNull { fragment: Fragment ->
                fragment.arguments?.getSerializable(SNACK_STATE_ENTITY_KEY) as? SnackStateEntity
            }
            outState.putSerializable(SNACK_COMMAND_EXECUTOR_STATE_KEY, ArrayList(savedStack))
        }
    }

    private fun executeOpenCommand(snack: S) {
        val transaction = fragmentManager.beginTransaction()

        val animations = snack.animations
        if (animations != null) {
            transaction.setCustomAnimations(animations.enter, animations.exit)
        }

        val snackFragment = snack.createFragment()
        val snackStateEntity = SnackStateEntity(snack.tag, snack.timeoutMs, snack.animations?.exit)
        snackFragment.arguments?.putSerializable(SNACK_STATE_ENTITY_KEY, snackStateEntity)

        val tag = snack.tag
        transaction.add(snack.containerId, snackFragment, tag)
        val timeoutMs = snack.timeoutMs
        if (timeoutMs != null) {
            snackFragment.removeOnTimeout(tag, timeoutMs, animations?.exit)
        }
        transaction.commit()
    }

    private fun executeCloseCommand(snack: S) {
        fragmentManager.removeFragment(snack.tag, snack.animations?.exit)
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