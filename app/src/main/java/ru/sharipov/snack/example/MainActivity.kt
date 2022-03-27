package ru.sharipov.snack.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.sharipov.snack.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.top_btn).setOnClickListener {
            (application as App).snackCommandBus.open(
                ExampleSnack.SimpleSnack.TopErrorSnack("Снэк сверху")
            )
        }
        findViewById<Button>(R.id.bottom_btn).setOnClickListener {
            (application as App).snackCommandBus.open(
                ExampleSnack.SimpleSnack.BottomSuccessSnack("Снэк снизу")
            )
        }
        findViewById<Button>(R.id.bottom_right_btn).setOnClickListener {
            (application as App).snackCommandBus.open(
                ExampleSnack.SimpleSnack.BottomRightSnack
            )
        }
        findViewById<Button>(R.id.button_complex).setOnClickListener {
            (application as App).snackCommandBus.open(ExampleSnack.ComplicatedSnack)
        }
    }
}