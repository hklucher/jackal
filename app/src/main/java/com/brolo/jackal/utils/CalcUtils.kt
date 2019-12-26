package com.brolo.jackal.utils

class CalcUtils {

    companion object {
        fun percentage(subCollection: List<Any>, fullCollection: List<Any>): Float {
            return (subCollection.size.toFloat() / fullCollection.size.toFloat() * 100)
        }
    }
}
