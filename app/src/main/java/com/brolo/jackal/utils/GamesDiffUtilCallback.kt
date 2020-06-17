package com.brolo.jackal.utils

import androidx.recyclerview.widget.DiffUtil
import com.brolo.jackal.model.Game

class GamesDiffUtilCallback(
    private val oldList: List<Game>,
    private val newList: List<Game>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].status != newList[newItemPosition].status
    }
}
