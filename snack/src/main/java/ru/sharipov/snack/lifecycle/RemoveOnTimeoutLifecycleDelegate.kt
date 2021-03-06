package ru.sharipov.snack.lifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import ru.sharipov.snack.executor.SnackCommandExecutor
import ru.sharipov.snack.executor.SnackStateEntity
import ru.sharipov.snack.extensions.removeFragment

internal class RemoveOnTimeoutLifecycleDelegate(
    private val tag: String,
    private val timeout: Long,
    override val fragment: Fragment,
    private val removeAnimRes: Int?
): ScopedLifecycleDelegate() {

    private var millisLeft: Long = timeout
    private var timerJob: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        setUpTimer()
    }

    override fun onStop(owner: LifecycleOwner) {
        timerJob?.cancel()
        val snackEntity = fragment.arguments?.getSerializable(SnackCommandExecutor.SNACK_STATE_ENTITY_KEY) as? SnackStateEntity
        if (snackEntity != null) {
            val updatedEntity = snackEntity.copy(timeLeftMs = millisLeft)
            fragment.arguments?.putSerializable(SnackCommandExecutor.SNACK_STATE_ENTITY_KEY, updatedEntity)
        }
    }

    private fun setUpTimer() {
        timerJob = delegateScope.launch {
            ticker(TIMER_TICK_MS)
                .consumeAsFlow()
                .collectLatest {
                    millisLeft -= TIMER_TICK_MS
                    if (millisLeft <= 0) {
                        withContext(Dispatchers.Main) {
                            fragment.parentFragmentManager.removeFragment(tag, removeAnimRes)
                        }
                    }
                }
        }
    }

    companion object {
        private const val TIMER_TICK_MS = 100L
    }
}