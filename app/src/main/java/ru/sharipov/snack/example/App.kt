package ru.sharipov.snack.example

import android.app.Application
import ru.sharipov.snack.extensions.SnackCommandEmitter

class App: Application() {

    val snackCommandEmitter = SnackCommandEmitter(this)
}