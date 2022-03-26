package ru.sharipov.snack.example

import android.R
import android.view.Gravity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.sharipov.snack.command.SnackFragmentFactory

class ExampleSnackFragmentFactory: SnackFragmentFactory<ExampleSnackCommand> {

    override fun createSnack(command: ExampleSnackCommand): Fragment {
        val exampleFragment = ExampleSnackFragment()
        val colorRes = when (command) {
            is ExampleSnackCommand.BottomSuccessSnack -> R.color.holo_green_light
            is ExampleSnackCommand.TopErrorSnack -> R.color.holo_red_light
            ExampleSnackCommand.BottomRightSnack -> R.color.holo_green_light
        }
        val gravity = when (command) {
            is ExampleSnackCommand.BottomSuccessSnack -> Gravity.BOTTOM
            is ExampleSnackCommand.TopErrorSnack -> Gravity.TOP
            ExampleSnackCommand.BottomRightSnack -> Gravity.BOTTOM
        }
        exampleFragment.arguments = bundleOf(
            ExampleSnackFragment.ARGS_COLOR_RES to colorRes,
            ExampleSnackFragment.ARGS_TEXT to command.text,
            ExampleSnackFragment.ARGS_GRAVITY to gravity
        )
        return exampleFragment
    }
}