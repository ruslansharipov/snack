package ru.sharipov.snack.extensions

import android.app.Application
import androidx.fragment.app.FragmentFactory
import ru.sharipov.snack.bus.SnackCommandBus
import ru.sharipov.snack.bus.SnackCommandEmitter
import ru.sharipov.snack.callbacks.SnackNavigationLifecycleCallbacks

fun SnackCommandEmitter(
    application: Application,
    fragmentFactory: FragmentFactory? = null
) : SnackCommandEmitter {
    val snackCommandBus = SnackCommandBus()
    val callbacks = SnackNavigationLifecycleCallbacks(snackCommandBus, fragmentFactory)

    application.registerActivityLifecycleCallbacks(callbacks)

    return snackCommandBus
}

fun Application.createSnackEmitter(
    fragmentFactory: FragmentFactory? = null
) : SnackCommandEmitter {
    return SnackCommandEmitter(this, fragmentFactory)
}