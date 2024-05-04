package com.example.sampleapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sampleapplication.databinding.ItemLayoutBinding
import com.example.sampleapplication.model.Result

class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun bind(result: Result) {
        binding.model = result
        Glide.with(binding.root.context)
            .load(result.image)
            .apply(RequestOptions().placeholder(R.drawable.ic_download).error(R.drawable.ic_error_image))
            .into(binding.acivImage)
    }
}