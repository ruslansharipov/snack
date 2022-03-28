package ru.sharipov.snack.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sharipov.snack.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.top_btn).setOnClickListener {
            (application as App).snackCommandEmitter.open(
                ExampleSnack.SimpleSnack.TopErrorSnack("Снэк сверху")
            )
        }
        findViewById<Button>(R.id.bottom_btn).setOnClickListener {
            (application as App).snackCommandEmitter.open(
                ExampleSnack.SimpleSnack.BottomSuccessSnack("Снэк снизу")
            )
        }
        findViewById<Button>(R.id.bottom_right_btn).setOnClickListener {
            lifecycleScope.launch {
                delay(2000)
                (application as App).snackCommandEmitter.open(ExampleSnack.SimpleSnack.BottomRightSnack)
            }
        }
        findViewById<Button>(R.id.button_complex).setOnClickListener {
            (application as App).snackCommandEmitter.open(ExampleSnack.ComplicatedSnack)
        }
    }
}