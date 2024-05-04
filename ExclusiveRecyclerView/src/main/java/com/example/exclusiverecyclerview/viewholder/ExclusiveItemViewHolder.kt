package com.example.exclusiverecyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.exclusiverecyclerview.R
import com.example.exclusiverecyclerview.databinding.ExclusiveItemLayoutBinding
import com.example.exclusiverecyclerview.databinding.ExclusiveSingularItemLayoutBinding
import com.example.exclusiverecyclerview.model.DataProcessor

//class ExclusiveItemViewHolder(private val binding: ExclusiveItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
class ExclusiveItemViewHolder(private val binding: ExclusiveSingularItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    //constructor(parent: ViewGroup) : this(ExclusiveItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    constructor(parent: ViewGroup) : this(ExclusiveSingularItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun bind(dataProcessor: DataProcessor) {
        binding.dataProcessor = dataProcessor
        Glide.with(binding.root.context)
            .load(dataProcessor.getCharacterImageURL())
            .apply(RequestOptions().placeholder(R.drawable.ic_download).error(R.drawable.ic_error_image))
            .into(binding.acivImage)

        /*dataProcessor.getCharacterStatus()?.let { status ->
            var colorId = R.color.gray
            if (status.equals("Alive", true)) {
                colorId = R.color.green
            } else if (status.equals("Dead", true)) {
                colorId = R.color.red
            }
            binding.actvStatus.compoundDrawablesRelative.forEach {
                it?.setTint(ContextCompat.getColor(binding.root.context, colorId))
            }
        }*/

    }
}