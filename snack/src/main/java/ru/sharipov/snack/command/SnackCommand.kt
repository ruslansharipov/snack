package ru.sharipov.snack.command

sealed class SnackCommand<S> {
    abstract val snack: S

    data class Open<S>(override val snack: S): SnackCommand<S>()
    data class Close<S>(override val snack: S): SnackCommand<S>()
}