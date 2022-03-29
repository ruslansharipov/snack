package ru.sharipov.snack.example

import android.app.Application
import ru.sharipov.snack.bus.SnackCommandEmitter

class App: Application() {

    val snackCommandEmitter = SnackCommandEmitter.create(this)
}