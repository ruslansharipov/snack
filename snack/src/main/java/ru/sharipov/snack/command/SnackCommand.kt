package ru.sharipov.snack.command

sealed class SnackCommand {
    abstract val snack: Snack

    data class Open(override val snack: Snack): SnackCommand()
    data class Close(override val snack: Snack): SnackCommand()
}