package com.formationandroid.memokotlin.helpers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.formationandroid.memokotlin.memos.adapter.MemoAdapter


class ItemTouchHelperCallback(private val adapter: MemoAdapter) : ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlagsUpDown = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val dragFlagsRight = ItemTouchHelper.RIGHT
        return makeMovementFlags(
            dragFlagsUpDown,
            dragFlagsRight
        )
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }


}
