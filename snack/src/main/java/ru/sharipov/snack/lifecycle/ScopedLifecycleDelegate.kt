package ru.sharipov.snack.lifecycle

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class ScopedLifecycleDelegate: DefaultLifecycleObserver {

    protected abstract val fragment: Fragment

    protected val delegateScope = CoroutineScope(Job())

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        delegateScope.cancel()
        fragment.lifecycle.removeObserver(this)
    }
}