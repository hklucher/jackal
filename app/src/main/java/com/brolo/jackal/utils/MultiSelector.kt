package com.brolo.jackal.utils

class MultiSelector {
    private var ids = mutableListOf<Int>()

    fun select(id: Int) {
        ids.add(id)
    }

    fun deselect(id: Int) {
        ids.remove(id)
    }

    fun isSelected(id: Int): Boolean {
        return ids.contains(id)
    }

}
