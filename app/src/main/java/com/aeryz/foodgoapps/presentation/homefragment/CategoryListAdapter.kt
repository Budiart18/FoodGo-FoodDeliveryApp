package com.aeryz.foodgoapps.presentation.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.data.CategoryDataSource
import com.aeryz.foodgoapps.databinding.ItemListCategoriesBinding
import com.aeryz.foodgoapps.model.Category

class CategoryListAdapter(
    private val categoryDataSource: CategoryDataSource,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListCategoriesBinding.inflate(inflater, parent, false)
        return CategoryItemViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val category = categoryDataSource.getCategoriesData()[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categoryDataSource.getCategoriesData().size
    }
}