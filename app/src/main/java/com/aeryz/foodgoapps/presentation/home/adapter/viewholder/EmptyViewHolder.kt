package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemEmptyViewsBinding
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection

class EmptyViewHolder(private val binding : ItemEmptyViewsBinding)
    : RecyclerView.ViewHolder(binding.root),
    ViewHolderBinder<HomeSection>
{
    override fun bind(item: HomeSection) {
        //no-op
    }
}