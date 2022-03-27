package ru.sharipov.snack.example

import android.app.Application
import ru.sharipov.snack.bus.SnackCommandBus
import ru.sharipov.snack.callbacks.SnackNavigationLifecycleCallbacks

class App: Application() {

    val snackCommandBus = SnackCommandBus<ExampleSnack>()

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(
            SnackNavigationLifecycleCallbacks(snackCommandBus)
        )
    }
}