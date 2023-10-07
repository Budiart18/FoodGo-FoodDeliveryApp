package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemSectionHeaderHomeBinding
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection

class HeaderSectionViewHolder(
    private val binding: ItemSectionHeaderHomeBinding,
    private val onSettingsClicked: () -> Unit,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    override fun bind(item: HomeSection) {
        if(item is HomeSection.HeaderSection){
            binding.ivSettings.setOnClickListener {
                onSettingsClicked.invoke()
            }
        }
    }
}