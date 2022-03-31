package ru.sharipov.snack.example.complicated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sharipov.snack.R
import ru.sharipov.snack.example.App
import ru.sharipov.snack.example.ExampleSnack

class ComplicatedSnackFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_snack_complicated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.snack_button)
        val progress = view.findViewById<ProgressBar>(R.id.snack_progress_bar)
        val viewModel = ViewModelProvider(this).get<ComplicatedSnackViewModel>()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.observeState()
                .collectLatest { snackState ->
                    progress.isVisible = snackState == ComplicatedSnackState.LOADING
                    button.text = when(snackState) {
                        ComplicatedSnackState.LOADING -> "Loading"
                        ComplicatedSnackState.ERROR -> "Retry?"
                        ComplicatedSnackState.SUCCESS -> "Success (close)"
                    }
                    button.isEnabled = snackState != ComplicatedSnackState.LOADING
                    button.setOnClickListener {
                        when(snackState) {
                            ComplicatedSnackState.LOADING -> {

                            }
                            ComplicatedSnackState.ERROR -> {
                                viewModel.handleError()
                            }
                            ComplicatedSnackState.SUCCESS -> {
                                (requireActivity().application as? App)?.snackController?.close(
                                    ExampleSnack.ComplicatedSnack
                                )
                            }
                        }
                    }
                }
        }
    }
}