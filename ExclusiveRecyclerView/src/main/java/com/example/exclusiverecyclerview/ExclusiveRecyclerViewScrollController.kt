package com.example.exclusiverecyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExclusiveRecyclerViewScrollController : RecyclerView.OnScrollListener() {

    var callback: ExclusiveRecyclerViewScrollCallback? = null
    var positionListener: ExclusiveRecyclerViewPositionListener? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

        val childCount = linearLayoutManager.childCount
        val itemCount = linearLayoutManager.itemCount
        val visibleFirstPosition = linearLayoutManager.findFirstVisibleItemPosition()

        if (callback?.checkLoad() == false && callback?.checkBuffer() == true) {
            if ((childCount + visibleFirstPosition >= itemCount) && visibleFirstPosition > 0) {
                callback?.loadData()
            }

        }

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val position = linearLayoutManager.findFirstVisibleItemPosition()
            positionListener?.onPageChanged(position)
        }
    }

}