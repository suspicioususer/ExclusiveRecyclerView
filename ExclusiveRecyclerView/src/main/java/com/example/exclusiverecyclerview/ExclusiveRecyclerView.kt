package com.example.exclusiverecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.exclusiverecyclerview.databinding.ExclusiveRecyclerViewLayoutBinding
import com.example.exclusiverecyclerview.model.DataProcessor

class ExclusiveRecyclerView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    private var binding: ExclusiveRecyclerViewLayoutBinding
    private var adapter: ExclusiveRecyclerViewAdapter? = null
    private var scrollController: ExclusiveRecyclerViewScrollController? = null

    private var callback: ExclusiveRecyclerViewScrollCallback? = null

    private var totalCharacterCount = 0
    private var scrollPosition = 0
    private var loadCount: Int = 20

    init {
        binding = ExclusiveRecyclerViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        setSnapHelper()
        setAdapter()
        setScrollController()
    }

    private fun setSnapHelper() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvList)
    }

    fun setAdapter(adapter: ExclusiveRecyclerViewAdapter) {
        this.adapter = adapter
        setAdapter()
    }

    private fun setAdapter() {
        if (adapter == null) {
            this.adapter = ExclusiveRecyclerViewAdapter()
        }
        binding.rvList.adapter = adapter
    }

    fun setScrollController(scrollController: ExclusiveRecyclerViewScrollController) {
        this.scrollController = scrollController
        setScrollController()
    }

    private fun setScrollController() {
        if (scrollController == null) {
            this.scrollController = ExclusiveRecyclerViewScrollController()
            this.scrollController?.positionListener = object : ExclusiveRecyclerViewPositionListener {
                override fun onPageChanged(position: Int) {
                    setScrollPosition(position)
                }

            }
        }
        scrollController?.let {
            binding.rvList.addOnScrollListener(it)
        }
    }

    fun setCallback(callback: ExclusiveRecyclerViewScrollCallback) {
        this.callback = callback
        scrollController?.callback = callback
    }

    fun setList(list: ArrayList<DataProcessor>?) {
        adapter?.list = list
    }

    fun setDataStatus(status: ExclusiveDataStatus?, refresh: Boolean = false) {
        adapter?.exclusiveDataStatus = status
        if (refresh) {
            adapter?.notifyDataSetChanged()
        }
    }

    fun refreshAll() {
        adapter?.notifyDataSetChanged()
    }

    fun refreshIndex(index: Int) {
        adapter?.notifyItemChanged(index)
    }

    fun refreshRange(beginIndex: Int) {
        adapter?.notifyItemRangeChanged(beginIndex, loadCount)
    }

    fun refreshPageRange(page: Int) {
        var beginIndex = 0
        if (page > 0) {
            beginIndex = page * loadCount
        }
        adapter?.notifyItemRangeChanged(beginIndex, loadCount)
    }

    fun hideProgressBar() {
        binding.pbLoading.visibility = View.GONE
    }

    fun showProgressBar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    fun setCharacterCount(count: Int) {
        this.totalCharacterCount = count
        binding.actvIndicator.text = context.getString(R.string.indicator, scrollPosition + 1, totalCharacterCount)
    }

    fun setLoadCount(count: Int) {
        this.loadCount = count
    }

    private fun setScrollPosition(position: Int) {
        this.scrollPosition = position
        binding.actvIndicator.text = context.getString(R.string.indicator, scrollPosition + 1, totalCharacterCount)
    }

}