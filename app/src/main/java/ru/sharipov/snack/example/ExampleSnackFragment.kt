package ru.sharipov.snack.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import ru.sharipov.snack.R

class ExampleSnackFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_snack_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val colorRes = requireArguments().getInt(ARGS_COLOR_RES)
        val text = requireArguments().getString(ARGS_TEXT)
        val snackContainer = view.findViewById<FrameLayout>(R.id.example_snack_container)
        val snackTv = view.findViewById<TextView>(R.id.example_snack_tv)
        snackTv.setText(text)
        snackTv.setBackgroundResource(colorRes)
        snackContainer.updateLayoutParams<FrameLayout.LayoutParams> {
            gravity = requireArguments().getInt(ARGS_GRAVITY)
        }
    }

    companion object {
        const val ARGS_TEXT = "ExampleSnackFragment.ARGS_TEXT"
        const val ARGS_COLOR_RES = "ExampleSnackFragment.ARGS_COLOR_RES"
        const val ARGS_GRAVITY = "ExampleSnackFragment.ARGS_GRAVITY"
    }
}