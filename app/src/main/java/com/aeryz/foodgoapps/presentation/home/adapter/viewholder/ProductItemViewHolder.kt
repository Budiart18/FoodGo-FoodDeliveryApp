package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemGridFoodsBinding
import com.aeryz.foodgoapps.databinding.ItemLinearFoodsBinding
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.utils.toCurrencyFormat

class LinearFoodItemViewHolder(
    private val binding: ItemLinearFoodsBinding,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.sivFoodImage.load(item.productImageUrl) {
            crossfade(true)
        }
        binding.tvFoodName.text = item.productName
        binding.tvFoodPrice.text = item.productPrice.toCurrencyFormat()
    }
}

class GridFoodItemViewHolder(
    private val binding: ItemGridFoodsBinding,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.sivFoodImage.load(item.productImageUrl) {
            crossfade(true)
        }
        binding.tvFoodName.text = item.productName
        binding.tvFoodPrice.text = item.productPrice.toCurrencyFormat()
    }
}
