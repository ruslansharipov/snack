package ru.sharipov.snack.bus

import ru.sharipov.snack.command.Snack

interface SnackCommandEmitter<S : Snack> {

    fun open(snack: S)

    fun close(snack: S)
}