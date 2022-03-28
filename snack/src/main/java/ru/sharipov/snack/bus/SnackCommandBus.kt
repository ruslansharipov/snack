package ru.sharipov.snack.bus

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.sharipov.snack.command.Snack
import ru.sharipov.snack.command.SnackCommand

internal class SnackCommandBus :
    SnackCommandEmitter,
    SnackCommandObservable {

    private val commandSharedFlow = MutableSharedFlow<SnackCommand>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 16
    )

    override fun observeSnackCommands(): Flow<SnackCommand> {
        return commandSharedFlow
    }

    override fun open(snack: Snack) {
        commandSharedFlow.tryEmit(SnackCommand.Open(snack))
    }

    override fun close(snack: Snack) {
        commandSharedFlow.tryEmit(SnackCommand.Close(snack))
    }

    override fun close(tag: String, exitAnimationRes: Int?) {
        commandSharedFlow.tryEmit(SnackCommand.Close(tag, exitAnimationRes))
    }
}