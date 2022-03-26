package ru.sharipov.snack.bus

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.sharipov.snack.command.SnackNavigationCommand

open class SnackCommandBus<C : SnackNavigationCommand> :
    SnackCommandEmitter<C>,
    SnackCommandObservable<C> {

    private val commandSharedFlow = MutableSharedFlow<C>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 16
    )

    override fun observeSnackCommands(): Flow<C> {
        return commandSharedFlow
    }

    override fun emitSnackCommand(command: C) {
        commandSharedFlow.tryEmit(command)
    }
}