package com.example.exclusiverecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exclusiverecyclerview.model.DataProcessor
import com.example.exclusiverecyclerview.viewholder.ExclusiveItemViewHolder
import com.example.exclusiverecyclerview.viewholder.ExclusiveNoDataViewHolder

class ExclusiveRecyclerViewAdapter(var list: ArrayList<DataProcessor>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val NOT_FOUND = 99

    var exclusiveDataStatus: ExclusiveDataStatus? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> ExclusiveItemViewHolder(parent)
            else -> ExclusiveNoDataViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == ITEM) {
            val model = list?.get(position)
            val itemViewHolder = holder as ExclusiveItemViewHolder
            model?.let {
                itemViewHolder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return when (exclusiveDataStatus) {
            ExclusiveDataStatus.NOT_FOUND, ExclusiveDataStatus.ERROR -> 1
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