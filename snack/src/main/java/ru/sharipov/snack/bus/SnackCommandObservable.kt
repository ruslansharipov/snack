package ru.sharipov.snack.bus

import kotlinx.coroutines.flow.Flow
import ru.sharipov.snack.command.SnackCommand

internal interface SnackCommandObservable {

    fun observeSnackCommands(): Flow<SnackCommand>
}