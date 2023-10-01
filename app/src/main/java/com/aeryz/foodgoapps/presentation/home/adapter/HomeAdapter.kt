package com.aeryz.foodgoapps.presentation.home.adapter

import FoodsSectionViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemEmptyViewsBinding
import com.aeryz.foodgoapps.databinding.ItemSectionBannerHomeBinding
import com.aeryz.foodgoapps.databinding.ItemSectionCategoryBinding
import com.aeryz.foodgoapps.databinding.ItemSectionFoodListBinding
import com.aeryz.foodgoapps.databinding.ItemSectionHeaderHomeBinding
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.BannerSectionViewHolder
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.CategoriesSectionViewHolder
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.EmptyViewHolder
import com.aeryz.foodgoapps.presentation.home.adapter.viewholder.HeaderSectionViewHolder

class HomeAdapter(
    private val onProductClicked : (Food) -> Unit,
    private val onNotificationClicked: () -> Unit,
    private val onLayoutModeChanged: () -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {

    private val items: MutableList<HomeSection> = mutableListOf()

    fun setItems(items: List<HomeSection>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<HomeSection>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems(){
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> {
                HeaderSectionViewHolder(
                    ItemSectionHeaderHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onNotificationClicked
                )
            }
            ITEM_TYPE_BANNER -> {
                BannerSectionViewHolder(
                    ItemSectionBannerHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_TYPE_CATEGORY_LIST -> {
                CategoriesSectionViewHolder(
                    ItemSectionCategoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_TYPE_FOOD_LIST -> {
                FoodsSectionViewHolder(
                    ItemSectionFoodListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onProductClicked,
                    onLayoutModeChanged()
                )
            }
            else -> EmptyViewHolder(
                ItemEmptyViewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<HomeSection>).bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            HomeSection.HeaderSection -> ITEM_TYPE_HEADER
            HomeSection.BannerSection -> ITEM_TYPE_BANNER
            is HomeSection.CategoriesSection -> ITEM_TYPE_CATEGORY_LIST
            is HomeSection.FoodsSection -> ITEM_TYPE_FOOD_LIST
        }
    }


    companion object {
        const val ITEM_TYPE_HEADER = 1
        const val ITEM_TYPE_BANNER = 2
        const val ITEM_TYPE_CATEGORY_LIST = 3
        const val ITEM_TYPE_FOOD_LIST = 4
    }
}