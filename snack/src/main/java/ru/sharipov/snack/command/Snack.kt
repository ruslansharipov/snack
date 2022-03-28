package ru.sharipov.snack.command

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ru.sharipov.snack.animations.Animations
import java.io.Serializable

interface Snack: Serializable {

    val containerId: Int
    val timeoutMs: Long?
    val animations: Animations?
    val className: String
    val tag: String

    fun prepareBundle(bundle: Bundle) {
        // Override if you need to pass extra data to a snack fragment
    }

    fun createFragment(factory: FragmentFactory): Fragment {
        val fragment = factory.instantiate(ClassLoader.getSystemClassLoader(), className)
        val bundle = Bundle()
        prepareBundle(bundle)
        fragment.arguments = bundle
        return fragment
    }
}