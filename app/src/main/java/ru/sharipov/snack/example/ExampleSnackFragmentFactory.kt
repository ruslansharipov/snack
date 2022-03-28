package ru.sharipov.snack.example

import androidx.fragment.app.Fragment
import ru.sharipov.snack.command.Snack
import ru.sharipov.snack.example.complicated.ComplicatedSnackFragment
import ru.sharipov.snack.factory.SnackFragmentFactory
import java.lang.IllegalStateException

class ExampleSnackFragmentFactory: SnackFragmentFactory {

    override fun createFragment(snack: Snack): Fragment {
        return when(snack) {
            is ExampleSnack.SimpleSnack -> ExampleSnackFragment()
            is ExampleSnack.ComplicatedSnack -> ComplicatedSnackFragment()
            else -> throw IllegalStateException()
        }
    }
}