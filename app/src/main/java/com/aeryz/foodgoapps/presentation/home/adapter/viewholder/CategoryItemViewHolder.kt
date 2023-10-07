package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemListCategoriesBinding
import com.aeryz.foodgoapps.model.Category

class CategoryItemViewHolder(
    private val binding: ItemListCategoriesBinding,
    private val onItemClick : (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.sivCategoryImage.load(item.categoryImage)
        binding.tvCategoryName.text = item.categoryName
    }
}