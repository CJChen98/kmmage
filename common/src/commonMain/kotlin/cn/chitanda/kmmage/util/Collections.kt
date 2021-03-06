package cn.chitanda.kmmage.util

import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * @author: Chen
 * @createTime: 2022/3/14 15:59
 * @description:
 **/

internal inline fun <R, T> List<R>.firstNotNullOfOrNullIndices(transform: (R) -> T?): T? {
    for (i in indices) {
        transform(get(i))?.let { return it }
    }
    return null
}

internal inline fun <T> MutableList<T>.removeIfIndices(predicate: (T) -> Boolean) {
    var numDeleted = 0
    for (rawIndex in indices) {
        val index = rawIndex - numDeleted
        if (predicate(get(index))) {
            removeAt(index)
            numDeleted++
        }
    }
}

internal fun <K, V> Map<K, V>.toImmutableMap(): Map<K, V> = when (size) {
    0 -> emptyMap()
    1 -> entries.first().let { (key, value) -> Collections.singletonMap(key, value) }
    else -> Collections.unmodifiableMap(LinkedHashMap(this))
}

/**
 * Functionally the same as [Iterable.forEach] except it generates
 * an index-based loop that doesn't use an [Iterator].
 */
internal inline fun <T> List<T>.forEachIndices(action: (T) -> Unit) {
    for (i in indices) {
        action(get(i))
    }
}

/**
 * Returns a list containing the **non null** results of applying the given
 * [transform] function to each entry in the original map.
 */
internal inline fun <K, V, R : Any> Map<K, V>.mapNotNullValues(
    transform: (Map.Entry<K, V>) -> R?
): Map<K, R> {
    val destination = mutableMapOf<K, R>()
    for (entry in entries) {
        val value = transform(entry)
        if (value != null) {
            destination[entry.key] = value
        }
    }
    return destination
}

internal fun <T> List<T>.indexOfElement(element: T,startIndex:Int =0):Int{
    for (i in startIndex until size) {
        if (this[i] == element) {
            return i
        }
    }
    return size
}