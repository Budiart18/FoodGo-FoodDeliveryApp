package com.aeryz.foodgoapps.presentation.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemGridFoodsBinding
import com.aeryz.foodgoapps.databinding.ItemLinearFoodsBinding
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.presentation.home.adapter.model.AdapterLayoutMode
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.GridFoodItemViewHolder
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.LinearFoodItemViewHolder

class FoodListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick: (Food) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var items: MutableList<Food> = mutableListOf()

    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Food>(){
        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            AdapterLayoutMode.LINEAR.ordinal -> {
                LinearFoodItemViewHolder(
                    binding = ItemLinearFoodsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onItemClick = onItemClick
                )
            }
            else -> {
                GridFoodItemViewHolder(
                    binding = ItemGridFoodsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onItemClick = onItemClick
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return dataDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Food>).bind(dataDiffer.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    fun submitList(data : List<Food>){
        dataDiffer.submitList(data)
    }


    fun refreshList(){
        notifyItemRangeChanged(0,dataDiffer.currentList.size)
    }



    fun setItems(items: List<Food>) {
        dataDiffer.submitList(items)
    }

    fun addItems(items: List<Food>) {
        val currentList = dataDiffer.currentList.toMutableList()
        currentList.addAll(items)
        dataDiffer.submitList(currentList)
    }

    fun clearItems() {
        dataDiffer.submitList(emptyList())
    }

}