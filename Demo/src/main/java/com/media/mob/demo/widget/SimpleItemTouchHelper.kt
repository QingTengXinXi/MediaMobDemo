package com.media.mob.demo.widget

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SimpleItemTouchHelper(private val itemTouchHelperInterface: ItemTouchHelperInterface) : ItemTouchHelper.Callback() {

    private var longPressDragEnable: Boolean = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, ItemTouchHelper.LEFT)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return longPressDragEnable
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
        itemTouchHelperInterface.itemMove(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        itemTouchHelperInterface.itemDelete(viewHolder.bindingAdapterPosition)
    }

    fun insertLongPressDragEnable(enable: Boolean) {
        this.longPressDragEnable = enable
    }
}