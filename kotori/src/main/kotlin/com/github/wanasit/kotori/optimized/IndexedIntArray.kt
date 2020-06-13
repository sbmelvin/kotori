package com.github.wanasit.kotori.optimized

private val EMPTY_INT_ARRAY: IntArray = intArrayOf()

/**
 * An optimized data structure for inserting/reading members (Int) at index value (Int)
 * where typical Array<MutableList<Int>> is not fast enough
 */
class IndexedIntArray(length: Int) {

    val index: Array<IntArray> = Array(length) { EMPTY_INT_ARRAY }
    val indexSize: IntArray = IntArray(length) { 0 }

    fun insert(indexingValue: Int, member: Int) {
        val memberIndex = indexSize[indexingValue]++
        ensureSize(index, indexingValue, memberIndex)
        index[indexingValue][memberIndex] = member
    }

    fun hasMemberAtIndex(indexedValue: Int) : Boolean {
        return indexSize[indexedValue] > 0
    }

    inline fun accessMembersAtIndex(indexedValue: Int, apply: (Int) -> Unit) {
        val size = indexSize[indexedValue]
        if (size == 0) {
            return
        }

        val dataArray = index[indexedValue]
        for (i in 0 until size) {
            apply(dataArray[i])
        }
    }

    private fun ensureSize(dataArray: Array<IntArray>, index: Int, size: Int) {
        if (size == 0) {
            dataArray[index] = IntArray(4)

        } else if (dataArray[index].size <= size) {
            val newArray = IntArray(size * 2)
            dataArray[index].copyInto(newArray)
            dataArray[index] = newArray
        }
    }
}
