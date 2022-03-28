package ru.sharipov.snack.bus

import ru.sharipov.snack.command.Snack

interface SnackCommandEmitter {

    fun open(snack: Snack)

    fun close(snack: Snack)

    fun close(tag: String, exitAnimationRes: Int? = null)
}