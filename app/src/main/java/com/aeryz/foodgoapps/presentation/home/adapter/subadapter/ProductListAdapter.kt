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
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.presentation.home.adapter.model.LayoutManagerType
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.GridFoodItemViewHolder
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.LinearFoodItemViewHolder

class ProductListAdapter(
    private var layoutManagerType: LayoutManagerType,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Product>(){
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            LayoutManagerType.LINEAR.ordinal -> {
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
        (holder as ViewHolderBinder<Product>).bind(dataDiffer.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return layoutManagerType.ordinal
    }

    fun submitData(data : List<Product>){
        dataDiffer.submitList(data)
    }

    fun refreshList(){
        notifyItemRangeChanged(0,dataDiffer.currentList.size)
    }

    fun setItems(items: List<Product>) {
        dataDiffer.submitList(items)
    }

    fun addItems(items: List<Product>) {
        val currentList = dataDiffer.currentList.toMutableList()
        currentList.addAll(items)
        dataDiffer.submitList(currentList)
    }

    fun clearItems() {
        dataDiffer.submitList(emptyList())
    }

    fun setLayoutManagerType(layoutManagerType: LayoutManagerType) {
        if (this.layoutManagerType != layoutManagerType) {
            this.layoutManagerType = layoutManagerType
            refreshList()
        }
    }

}