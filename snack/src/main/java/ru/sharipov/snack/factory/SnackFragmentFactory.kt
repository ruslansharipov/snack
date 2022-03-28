package ru.sharipov.snack.factory

import androidx.fragment.app.Fragment
import ru.sharipov.snack.command.Snack

interface SnackFragmentFactory {
    fun createFragment(snack: Snack) : Fragment
}