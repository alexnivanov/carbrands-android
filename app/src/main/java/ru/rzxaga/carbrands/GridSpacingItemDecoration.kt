package ru.rzxaga.carbrands

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpacingItemDecoration(private val spanCount: Int,
                                private val itemSize: Float) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State?) {
        val position = parent.getChildLayoutPosition(view)
        val column = position % spanCount
        val row = position / spanCount
        val spacing = (parent.width - itemSize * spanCount).toInt() / (spanCount + 1)

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount
        outRect.top = spacing - row * spacing / spanCount
        outRect.bottom = (row + 1) * spacing / spanCount
    }

}
