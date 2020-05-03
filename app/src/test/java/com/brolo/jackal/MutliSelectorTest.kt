package com.brolo.jackal

import com.brolo.jackal.utils.MultiSelector
import org.junit.Test

class MultiSelectorTest {
    private val selector = MultiSelector()

    @Test
    fun select_addsItem() {
        selector.select(1)

        assert(selector.isSelected(1))
    }

    @Test
    fun deselect_removesItem() {
        selector.select(1)
        selector.deselect(1)

        assert(!selector.isSelected(1))
    }

    @Test
    fun isSelected_returnsTrueWhenSelected() {
        selector.select(1)

        assert(selector.isSelected(1))
    }

    @Test
    fun isSelected_returnsFalseWhenNotSelected() {
        assert(!selector.isSelected(1))
    }

}
