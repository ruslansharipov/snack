package ru.sharipov.snack.bus

import ru.sharipov.snack.command.SnackNavigationCommand

interface SnackCommandEmitter<C : SnackNavigationCommand> {

    fun emitSnackCommand(command: C)
}