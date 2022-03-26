package ru.sharipov.snack.command

import ru.sharipov.snack.animations.Animations

interface SnackNavigationCommand {
    val containerId: Int
    val tag: String
    val timeoutMs: Long?
    val animations: Animations?
}