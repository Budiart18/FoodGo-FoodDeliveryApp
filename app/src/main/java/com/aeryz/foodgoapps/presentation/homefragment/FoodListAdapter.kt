package com.aeryz.foodgoapps.presentation.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aeryz.foodgoapps.databinding.ItemListFoodsBinding
import com.aeryz.foodgoapps.model.Food

class FoodListAdapter(private val onItemClick: (Food) -> Unit)
    : RecyclerView.Adapter<FoodItemListViewHolder>() {

    private val differ = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Food>(){
        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.foodName == newItem.foodName
        }

        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    fun setData(data : List<Food>){
        differ.submitList(data)
        notifyItemRangeChanged(0,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemListViewHolder {
        return FoodItemListViewHolder(
            binding = ItemListFoodsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick = onItemClick
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FoodItemListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class FoodItemListViewHolder(
    private val binding: ItemListFoodsBinding,
    private val onItemClick : (Food) -> Unit
) : ViewHolder(binding.root) {
    fun bind (item : Food) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.sivFoodImage.setImageResource(item.foodImage)
        binding.tvFoodName.text = item.foodName
        binding.tvFoodPrice.text = item.foodPrice
        binding.tvFoodShopDistance.text = item.foodShopDistance
        binding.tvFoodRating.text = item.foodRating
    }
}