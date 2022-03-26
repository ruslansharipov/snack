package ru.sharipov.snack.extensions

import androidx.fragment.app.Fragment
import ru.sharipov.snack.lifecycle.RemoveOnTimeoutLifecycleDelegate

fun Fragment.removeOnTimeout(
    tag: String,
    timeoutMs: Long,
    removeAnimRes: Int?
) {
    val lifecycleDelegate = RemoveOnTimeoutLifecycleDelegate(
        tag = tag,
        timeout = timeoutMs,
        fragment = this,
        removeAnimRes = removeAnimRes
    )
    lifecycle.addObserver(lifecycleDelegate)
}