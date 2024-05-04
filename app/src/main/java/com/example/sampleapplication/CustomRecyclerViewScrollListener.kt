package com.example.sampleapplication

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class CustomRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

        val childCount = linearLayoutManager.childCount
        val itemCount = linearLayoutManager.itemCount
        val visibleFirstPosition = linearLayoutManager.findFirstVisibleItemPosition()
        //val page = (itemCount ?: 0) / 20

        if (!checkLoad() && checkBuffer()) {
            if ((childCount + visibleFirstPosition >= itemCount) && visibleFirstPosition > 0) {
                loadData()
            }

        }

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val position = linearLayoutManager.findFirstVisibleItemPosition()
            onPageChanged(position)
        }
    }

    abstract fun loadData()

    abstract fun checkBuffer(): Boolean

    abstract fun checkLoad(): Boolean

    abstract fun onPageChanged(position: Int)

}