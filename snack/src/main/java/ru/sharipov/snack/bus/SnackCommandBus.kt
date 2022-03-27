package ru.sharipov.snack.bus

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.sharipov.snack.command.Snack
import ru.sharipov.snack.command.SnackCommand

open class SnackCommandBus<S : Snack> :
    SnackCommandEmitter<S>,
    SnackCommandObservable<S> {

    private val commandSharedFlow = MutableSharedFlow<SnackCommand<S>>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 16
    )

    override fun observeSnackCommands(): Flow<SnackCommand<S>> {
        return commandSharedFlow
    }

    override fun open(snack: S) {
        commandSharedFlow.tryEmit(SnackCommand.Open(snack))
    }

    override fun close(snack: S) {
        commandSharedFlow.tryEmit(SnackCommand.Close(snack))
    }
}