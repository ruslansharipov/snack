package ru.sharipov.snack.animations

import ru.sharipov.snack.R

object FromTopToTopAnimations: Animations {
    override val enter: Int = R.anim.slide_in_from_top
    override val exit: Int = R.anim.slide_out_to_top
}

