package com.example.sampleapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.databinding.EmptyViewBinding

class NoDataViewHolder(binding: EmptyViewBinding) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(EmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

}