package ru.sharipov.snack.example

import android.os.Bundle
import android.view.Gravity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.sharipov.snack.animations.*
import ru.sharipov.snack.command.DefaultSnackCommand

sealed class ExampleSnackCommand: DefaultSnackCommand() {

    abstract val text: String
    abstract val colorRes: Int
    abstract val gravity: Int

    override val tag: String get() = "ExampleSnackCommand $text"
    override val timeoutMs: Long = 1500
    override val fragmentClass: Class<out Fragment> = ExampleSnackFragment::class.java

    override fun prepareBundle(bundle: Bundle) {
        bundle.putAll(
            bundleOf(
                ExampleSnackFragment.ARGS_COLOR_RES to colorRes,
                ExampleSnackFragment.ARGS_TEXT to text,
                ExampleSnackFragment.ARGS_GRAVITY to gravity
            )
        )
    }

    data class TopErrorSnack(override val text: String): ExampleSnackCommand() {
        override val colorRes: Int = android.R.color.holo_red_light
        override val gravity: Int = Gravity.TOP
        override val animations: Animations = FadeInFadeOutAnimations
    }

    data class BottomSuccessSnack(override val text: String): ExampleSnackCommand() {
        override val colorRes: Int = android.R.color.holo_green_light
        override val gravity: Int = Gravity.BOTTOM
        override val animations: Animations = FromBottomToBottomAnimations
    }

    object BottomRightSnack: ExampleSnackCommand() {
        override val text: String = "Bottom in, right out"
        override val animations: Animations = FromBottomToRightAnimations
        override val colorRes: Int = android.R.color.holo_green_light
        override val gravity: Int = Gravity.BOTTOM
    }
}