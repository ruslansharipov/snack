package ru.sharipov.snack.bus

import android.app.Application
import androidx.fragment.app.FragmentFactory
import ru.sharipov.snack.callbacks.SnackNavigationLifecycleCallbacks
import ru.sharipov.snack.command.Snack

interface SnackController {

    fun open(snack: Snack)

    fun close(snack: Snack)

    fun close(tag: String, exitAnimationRes: Int? = null)

    companion object {

        fun create(
            application: Application,
            fragmentFactory: FragmentFactory? = null
        ) : SnackController {
            val snackCommandBus = SnackCommandBus()
            val callbacks = SnackNavigationLifecycleCallbacks(snackCommandBus, fragmentFactory)

            application.registerActivityLifecycleCallbacks(callbacks)

            return snackCommandBus
        }
    }
}