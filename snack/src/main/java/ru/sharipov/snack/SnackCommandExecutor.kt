package ru.sharipov.snack

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.sharipov.snack.command.SnackNavigationCommand
import ru.sharipov.snack.extensions.removeOnTimeout
import java.io.Serializable
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
        snack.arguments?.putSerializable(SNACK_COMMAND_KEY, SnackCommandEntity(command))

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
                fragment.arguments?.getSerializable(SNACK_COMMAND_KEY) as? SnackCommandEntity
            }
            outState.putSerializable(SNACK_COMMAND_STATE_KEY, ArrayList(savedStack))
        }
    }

    private fun restoreState(bundle: Bundle?) {
        val snackList = bundle?.getSerializable(SNACK_COMMAND_STATE_KEY) as? ArrayList<SnackCommandEntity>
        if (!snackList.isNullOrEmpty()) {
            snackList.forEach { snackCommandEntity: SnackCommandEntity ->
                val command = snackCommandEntity.command
                val timeoutMs = command.timeoutMs
                if (timeoutMs != null) {
                    val fragment = fragmentManager.findFragmentByTag(command.tag)
                    fragment?.removeOnTimeout(command.tag, timeoutMs, command.animations?.exit)
                }
            }
        }
    }

    private data class SnackCommandEntity(val command: SnackNavigationCommand): Serializable

    companion object {
        const val SNACK_COMMAND_STATE_KEY = "SNACK_COMMAND_STATE_KEY"
        const val SNACK_COMMAND_KEY = "SNACK_COMMAND_KEY"
    }
}

