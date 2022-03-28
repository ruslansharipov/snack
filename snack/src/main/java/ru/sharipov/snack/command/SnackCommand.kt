package ru.sharipov.snack.command

internal sealed class SnackCommand {

    data class Open(val snack: Snack) : SnackCommand()

    data class Close(val tag: String, val exitAnimationRes: Int?) : SnackCommand() {
        constructor(snack: Snack) : this(tag = snack.tag, exitAnimationRes = snack.animations?.exit)
    }
}