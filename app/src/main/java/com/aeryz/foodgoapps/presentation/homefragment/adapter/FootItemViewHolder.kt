package com.aeryz.foodgoapps.presentation.homefragment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemGridFoodsBinding
import com.aeryz.foodgoapps.databinding.ItemLinearFoodsBinding
import com.aeryz.foodgoapps.model.Food


class LinearFoodItemViewHolder(
    private val binding: ItemLinearFoodsBinding,
    private val onItemClick : (Food) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Food> {
    override fun bind (item : Food) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.sivFoodImage.setImageResource(item.foodImage)
        binding.tvFoodName.text = item.foodName
        binding.tvFoodPrice.text = itemView.context.getString(R.string.text_food_price_format, item.foodPrice)
        binding.tvFoodShopDistance.text = itemView.context.getString(R.string.text_shop_distance_format, item.foodShopDistance)
        binding.tvFoodRating.text = itemView.context.getString(R.string.text_food_reviews_format, item.foodRating)
    }
}

class GridFoodItemViewHolder(
    private val binding: ItemGridFoodsBinding,
    private val onItemClick : (Food) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Food> {
    override fun bind (item : Food) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.sivFoodImage.setImageResource(item.foodImage)
        binding.tvFoodName.text = item.foodName
        binding.tvFoodPrice.text = itemView.context.getString(R.string.text_food_price_format, item.foodPrice)
        binding.tvFoodShopDistance.text = itemView.context.getString(R.string.text_shop_distance_format, item.foodShopDistance)
        binding.tvFoodRating.text = itemView.context.getString(R.string.text_food_reviews_format, item.foodRating)
    }
}