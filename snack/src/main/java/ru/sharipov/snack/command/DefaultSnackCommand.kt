package ru.sharipov.snack.command

import ru.sharipov.snack.animations.Animations

abstract class DefaultSnackCommand : SnackNavigationCommand {
    override val containerId: Int = android.R.id.content
    override val animations: Animations? = null
    override val tag: String get() = fragmentClass.simpleName
}