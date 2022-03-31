package ru.sharipov.snack.example

import android.os.Bundle
import android.view.Gravity
import androidx.core.os.bundleOf
import ru.sharipov.snack.R
import ru.sharipov.snack.animations.*
import ru.sharipov.snack.command.DefaultSnack

sealed class ExampleSnack: DefaultSnack() {

    sealed class SimpleSnack: ExampleSnack() {
        abstract val text: String
        abstract val bgRes: Int
        abstract val gravity: Int

        override val tag: String get() = "ExampleSnackCommand $text"
        override val timeoutMs: Long? = 1500
        override val className: String = "ru.sharipov.snack.example.ExampleSnackFragment"

        override fun prepareBundle(bundle: Bundle) {
            bundle.putAll(
                bundleOf(
                    ExampleSnackFragment.ARGS_BG_RES to bgRes,
                    ExampleSnackFragment.ARGS_TEXT to text,
                    ExampleSnackFragment.ARGS_GRAVITY to gravity
                )
            )
        }

        data class TopErrorSnack(override val text: String): SimpleSnack() {
            override val bgRes: Int = R.drawable.bg_rounded_red_12
            override val gravity: Int = Gravity.TOP
            override val animations: Animations = FadeInFadeOutAnimations
        }

        data class BottomSuccessSnack(override val text: String): SimpleSnack() {
            override val bgRes: Int = R.drawable.bg_rounded_green_12
            override val gravity: Int = Gravity.BOTTOM
            override val animations: Animations = BottomInBottomOutAnimations
        }

        object BottomRightSnack: SimpleSnack() {
            override val text: String = "Bottom in, right out"
            override val animations: Animations = BottomInRightOutAnimations
            override val bgRes: Int = R.drawable.bg_rounded_green_12
            override val gravity: Int = Gravity.BOTTOM
        }
    }

    object ComplicatedSnack: ExampleSnack() {
        override val timeoutMs: Long? = null
        override val animations: Animations = BottomInBottomOutAnimations
        override val containerId: Int = R.id.complicated_snack_container
        override val className: String = "ru.sharipov.snack.example.complicated.ComplicatedSnackFragment"
    }

    object SnackWithIcon: ExampleSnack() {
        override val timeoutMs: Long = 3000
        override val className: String = "ru.sharipov.snack.example.icon.IconSnackFragment"
        override val animations: Animations = TopInRightOutAnimations
    }
}