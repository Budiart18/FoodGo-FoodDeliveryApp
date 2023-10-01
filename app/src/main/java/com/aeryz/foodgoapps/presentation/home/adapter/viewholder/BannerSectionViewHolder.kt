package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemSectionBannerHomeBinding
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection

class BannerSectionViewHolder(
    private val binding : ItemSectionBannerHomeBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    override fun bind(item: HomeSection) {
        if(item is HomeSection.BannerSection){
            //no-op
        }
    }
}