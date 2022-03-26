package ru.sharipov.snack.command

import androidx.fragment.app.Fragment

interface SnackFragmentFactory<C: SnackNavigationCommand> {
    fun createSnack(command: C) : Fragment
}