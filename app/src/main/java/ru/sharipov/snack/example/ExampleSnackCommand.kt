package ru.sharipov.snack.example

import ru.sharipov.snack.animations.Animations
import ru.sharipov.snack.animations.FromBottomToBottomAnimations
import ru.sharipov.snack.animations.FromBottomToRightAnimations
import ru.sharipov.snack.animations.FromTopToTopAnimations
import ru.sharipov.snack.command.DefaultSnackCommand
import ru.sharipov.snack.command.SnackNavigationCommand

sealed class ExampleSnackCommand: DefaultSnackCommand() {

    abstract val text: String

    override val tag: String = "ExampleSnackCommand $text"

    override val timeoutMs: Long = 3000

    data class TopErrorSnack(override val text: String): ExampleSnackCommand() {
        override val animations: Animations = FromTopToTopAnimations
    }

    data class BottomSuccessSnack(override val text: String): ExampleSnackCommand() {
        override val animations: Animations = FromBottomToBottomAnimations
    }

    object BottomRightSnack: ExampleSnackCommand() {
        override val text: String = "Bottom in, right out"
        override val animations: Animations = FromBottomToRightAnimations
    }
}