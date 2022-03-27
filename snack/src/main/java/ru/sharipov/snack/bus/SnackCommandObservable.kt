package ru.sharipov.snack.bus

import kotlinx.coroutines.flow.Flow
import ru.sharipov.snack.command.Snack
import ru.sharipov.snack.command.SnackCommand

interface SnackCommandObservable<S : Snack> {

    fun observeSnackCommands(): Flow<SnackCommand<S>>
}