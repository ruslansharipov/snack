package ru.sharipov.snack.command

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.sharipov.snack.animations.Animations
import java.io.Serializable

interface SnackNavigationCommand: Serializable {
    val containerId: Int
    val timeoutMs: Long?
    val animations: Animations?
    val fragmentClass: Class<out Fragment>
    val tag: String

    fun prepareBundle(bundle: Bundle) {
        // Override if you need to pass extra data to a snack fragment
    }

    fun createFragment(): Fragment {
        val snackFragment = fragmentClass.getConstructor().newInstance()
        val bundle = Bundle()
        snackFragment.arguments = bundle

        prepareBundle(bundle)
        return snackFragment
    }
}