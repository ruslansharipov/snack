package ru.sharipov.snack.bus

import kotlinx.coroutines.flow.Flow
import ru.sharipov.snack.command.SnackNavigationCommand

interface SnackCommandObservable<C : SnackNavigationCommand> {

    fun observeSnackCommands(): Flow<C>
}