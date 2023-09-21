package com.aeryz.foodgoapps.presentation.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.CategoryDataSource
import com.aeryz.foodgoapps.data.CategoryDataSourceImpl
import com.aeryz.foodgoapps.data.FoodsDataSource
import com.aeryz.foodgoapps.data.FoodsDataSourceImpl
import com.aeryz.foodgoapps.databinding.FragmentFoodListBinding
import com.aeryz.foodgoapps.model.Category
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.presentation.homefragment.adapter.AdapterLayoutMode
import com.aeryz.foodgoapps.presentation.homefragment.adapter.CategoryListAdapter
import com.aeryz.foodgoapps.presentation.homefragment.adapter.FoodListAdapter

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val foodDataSource: FoodsDataSource by lazy {
        FoodsDataSourceImpl()
    }

    private val categoryDataSource: CategoryDataSource by lazy {
        CategoryDataSourceImpl()
    }

    private val foodAdapter: FoodListAdapter by lazy {
        FoodListAdapter(AdapterLayoutMode.LINEAR) { food : Food ->
            navigateToDetail(food)
        }
    }

    private fun navigateToDetail(food: Food) {
        val action = FoodListFragmentDirections.navigateToDetailFragment(food)
        findNavController().navigate(action)
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(categoryDataSource){
            clickCategory(it)
        }
    }

    private fun clickCategory(category: Category) {
        val categoryName = category.categoryName
        Toast.makeText(requireContext(), "You selected $categoryName", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecyclerview()
        setupFoodRecyclerView()
        setupSwitch()
    }

    private fun setupCategoryRecyclerview(){
        binding.rvListCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = this@FoodListFragment.categoryAdapter
        }
    }

    private fun setupFoodRecyclerView() {
        val span = if (foodAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvListFoods.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@FoodListFragment.foodAdapter
        }
        foodAdapter.submitList(foodDataSource.getFoodsData())
    }

    private fun setupSwitch() {
        binding.ibSwitchMode.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        when (foodAdapter.adapterLayoutMode) {
            AdapterLayoutMode.LINEAR -> {
                binding.ibSwitchMode.setImageResource(R.drawable.ic_grid_mode)
                (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 2
                foodAdapter.adapterLayoutMode = AdapterLayoutMode.GRID
            }
            AdapterLayoutMode.GRID -> {
                binding.ibSwitchMode.setImageResource(R.drawable.ic_linear_mode)
                (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 1
                foodAdapter.adapterLayoutMode = AdapterLayoutMode.LINEAR
            }
        }
        foodAdapter.refreshList()
    }
}
