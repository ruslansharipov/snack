package ru.sharipov.snack.extensions

import androidx.fragment.app.FragmentManager

internal fun FragmentManager.removeFragment(tag: String, removeAnimRes: Int?) {
    val fragmentToRemove = findFragmentByTag(tag)
    if (fragmentToRemove != null) {
        val transaction = beginTransaction()
        if (removeAnimRes != null) {
            transaction.setCustomAnimations(0, removeAnimRes, 0, removeAnimRes)
        }
        transaction
            .remove(fragmentToRemove)
            .commit()
    }
}