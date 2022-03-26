package ru.sharipov.snack.executor

import java.io.Serializable

internal data class SnackStateEntity(
    val tag: String,
    val timeLeftMs: Long?,
    val exitAnimRes: Int?
): Serializable