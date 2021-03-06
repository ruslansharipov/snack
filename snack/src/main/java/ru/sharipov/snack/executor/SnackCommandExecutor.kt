package ru.sharipov.snack.executor

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.sharipov.snack.animations.Animations
import ru.sharipov.snack.command.SnackCommand
import ru.sharipov.snack.command.Snack
import ru.sharipov.snack.extensions.removeFragment
import ru.sharipov.snack.extensions.removeOnTimeout
import kotlin.collections.ArrayList

internal class SnackCommandExecutor(
    private val fragmentManager: FragmentManager,
    optionalFragmentFactory: FragmentFactory?,
    savedState: Bundle?
) {

    private val fragmentFactory: FragmentFactory = optionalFragmentFactory ?: fragmentManager.fragmentFactory

    init {
        restoreState(savedState)
    }

    fun execute(command: SnackCommand) {
        when(command) {
            is SnackCommand.Close -> executeClose(command)
            is SnackCommand.Open -> executeOpen(command.snack)
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

    private fun executeOpen(snack: Snack) {
        val transaction = fragmentManager.beginTransaction()

        supplyAnimations(snack, transaction)
        val snackFragment = createSnackFragment(snack)
        setUpTimeout(snack, transaction, snackFragment)

        transaction.commit()
    }

    private fun executeClose(closeCommand: SnackCommand.Close) {
        fragmentManager.removeFragment(closeCommand.tag, closeCommand.exitAnimationRes)
    }

    private fun setUpTimeout(
        snack: Snack,
        transaction: FragmentTransaction,
        snackFragment: Fragment
    ) {
        val tag = snack.tag
        transaction.add(snack.containerId, snackFragment, tag)
        val timeoutMs = snack.timeoutMs
        if (timeoutMs != null) {
            snackFragment.removeOnTimeout(tag, timeoutMs, snack.animations?.exit)
        }
    }

    private fun supplyAnimations(
        snack: Snack,
        transaction: FragmentTransaction
    ): Animations? {
        val animations = snack.animations
        if (animations != null) {
            transaction.setCustomAnimations(animations.enter, animations.exit)
        }
        return animations
    }

    private fun createSnackFragment(snack: Snack): Fragment {
        val snackStateEntity = SnackStateEntity(snack.tag, snack.timeoutMs, snack.animations?.exit)
        val snackFragment = snack.createFragment(fragmentFactory)
        snackFragment.arguments?.putSerializable(SNACK_STATE_ENTITY_KEY, snackStateEntity)
        return snackFragment
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