package com.aeryz.foodgoapps.presentation.home.adapter.viewholder

import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.core.ViewHolderBinder
import com.aeryz.foodgoapps.databinding.ItemSectionProductListBinding
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.presentation.home.adapter.model.HomeSection
import com.aeryz.foodgoapps.presentation.home.adapter.model.LayoutManagerType
import com.aeryz.foodgoapps.presentation.home.adapter.model.ProductViewModel
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.ProductListAdapter
import com.aeryz.foodgoapps.utils.proceedWhen

class ProductsSectionViewHolder(
    private val binding: ItemSectionProductListBinding,
    private val onClickListener: (Product) -> Unit,
    private val productViewModel: ProductViewModel
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {

    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(layoutManagerType = LayoutManagerType.LINEAR) {
            onClickListener.invoke(it)
        }
    }

    init {
        binding.rvListFoods.layoutManager = LinearLayoutManager(binding.root.context)
        productViewModel.layoutManagerType.observe(itemView.context as LifecycleOwner) {layoutManagerType ->
            adapter.setLayoutManagerType(layoutManagerType)
            updateLayoutManagerImage(layoutManagerType)
            adapter.refreshList()
        }
        setupSwitchModeListener()
    }

    private fun setupSwitchModeListener(){
        binding.ibSwitchMode.setOnClickListener {
            val newLayoutManagerType = when (productViewModel.layoutManagerType.value) {
                LayoutManagerType.LINEAR -> LayoutManagerType.GRID
                LayoutManagerType.GRID -> LayoutManagerType.LINEAR
                else -> LayoutManagerType.LINEAR
            }
            productViewModel.setLayoutManagerType(newLayoutManagerType)
            updateLayoutManagerImage(newLayoutManagerType)
            switchLayoutManager(newLayoutManagerType)
            adapter.refreshList()
        }
    }
    private fun updateLayoutManagerImage(layoutManagerType: LayoutManagerType?) {
        val imageResId = when (layoutManagerType) {
            LayoutManagerType.GRID -> R.drawable.ic_grid_mode
            else -> R.drawable.ic_linear_mode          }
        binding.ibSwitchMode.setImageResource(imageResId)
    }

    private fun switchLayoutManager(layoutManagerType: LayoutManagerType) {
        binding.rvListFoods.layoutManager = when (layoutManagerType) {
            LayoutManagerType.LINEAR -> LinearLayoutManager(binding.root.context)
            LayoutManagerType.GRID -> GridLayoutManager(binding.root.context, 2)
        }
        adapter.setLayoutManagerType(layoutManagerType)
        adapter.refreshList()
    }

    override fun bind(item: HomeSection) {
        if (item is HomeSection.ProductSection) {
            item.data.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvListFoods.apply {
                    isVisible = true
                    adapter = this@ProductsSectionViewHolder.adapter
                }
                item.data.payload?.let { data -> adapter.submitData(data) }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvListFoods.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = item.data.exception?.message.orEmpty()
                binding.rvListFoods.isVisible = false
            })
        }
    }
}
