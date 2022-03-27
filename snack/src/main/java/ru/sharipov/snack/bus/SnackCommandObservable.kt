package ru.sharipov.snack.bus

import kotlinx.coroutines.flow.Flow
import ru.sharipov.snack.command.SnackCommand

interface SnackCommandObservable {

    fun observeSnackCommands(): Flow<SnackCommand>
}