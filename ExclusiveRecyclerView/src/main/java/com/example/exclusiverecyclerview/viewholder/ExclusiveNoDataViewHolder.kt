package com.example.exclusiverecyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exclusiverecyclerview.databinding.ExclusiveEmptyViewLayoutBinding

class ExclusiveNoDataViewHolder(binding: ExclusiveEmptyViewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(ExclusiveEmptyViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

}