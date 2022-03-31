package ru.sharipov.snack.example

import android.app.Application
import ru.sharipov.snack.bus.SnackController

class App: Application() {

    val snackController = SnackController.create(this)
}