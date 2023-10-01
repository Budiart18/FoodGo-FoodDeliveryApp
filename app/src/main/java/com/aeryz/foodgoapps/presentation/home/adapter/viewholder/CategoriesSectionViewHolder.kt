package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemSectionCategoryBinding
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.CategoryListAdapter

class CategoriesSectionViewHolder(
    private val binding: ItemSectionCategoryBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    private val adapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            Toast.makeText(binding.root.context, it.categoryName, Toast.LENGTH_SHORT).show()
        }
    }
    override fun bind(item: HomeSection) {
        if(item is HomeSection.CategoriesSection){
            binding.rvListCategories.apply {
                adapter = this@CategoriesSectionViewHolder.adapter
            }
            adapter.setItems(items = item.data)
        }
    }
}