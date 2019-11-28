package com.brolo.jackal.utils

import com.brolo.jackal.model.FilterOption

class FilterOptionUtils {

    companion object {
        fun getSelectedOption(options: List<FilterOption>): FilterOption? {
            return options.find { it.isSelected }
        }
    }
}
