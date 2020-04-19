package com.brolo.jackal.utils

/**
 * @param T the object to check if is not null
 * @param callback a function to run when T is not null
 */
inline fun <T:Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}
