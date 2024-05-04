package com.example.sampleapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.model.Result

class CustomRecyclerViewAdapter(private val list: ArrayList<Result>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val NOT_FOUND = 99

    var dataStatus: DataStatus? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> ItemViewHolder(parent)
            else -> NoDataViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == ITEM) {
            val model = list?.get(position)
            val itemViewHolder = holder as ItemViewHolder
            model?.let {
                itemViewHolder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return when (dataStatus) {
            DataStatus.NOT_FOUND, DataStatus.ERROR -> 1
            else -> list?.size ?: 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        if ((list?.size ?: 0) > 0) {
            return ITEM
        }
        return NOT_FOUND
    }

}