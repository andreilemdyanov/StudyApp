package ru.fundamentals.studyapp.presentation.movie_details.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val lastIndex = state.itemCount - 1
        when {
            parent.getChildLayoutPosition(view) == 0 -> {
                outRect.right = spacing
            }
            parent.getChildLayoutPosition(view) == lastIndex -> {
                outRect.left = spacing
            }
            else -> {
                outRect.left = spacing
                outRect.right = spacing
            }
        }
    }
}