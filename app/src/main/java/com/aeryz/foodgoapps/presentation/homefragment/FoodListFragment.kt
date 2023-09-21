package com.aeryz.foodgoapps.presentation.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.FoodsDataSource
import com.aeryz.foodgoapps.data.FoodsDataSourceImpl
import com.aeryz.foodgoapps.databinding.FragmentFoodListBinding
import com.aeryz.foodgoapps.model.Food

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val dataSource: FoodsDataSource by lazy {
        FoodsDataSourceImpl()
    }

    private val adapter: FoodListAdapter by lazy {
        FoodListAdapter(AdapterLayoutMode.LINEAR) {food : Food ->
            navigateToDetail(food)
        }
    }

    private fun navigateToDetail(food: Food) {
        val action = FoodListFragmentDirections.navigateToDetailFragment(food)
        findNavController().navigate(action)
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
        setupRecyclerView()
        setupSwitch()
    }

    private fun setupRecyclerView() {
        val span = if (adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvListFoods.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@FoodListFragment.adapter
        }
        adapter.submitList(dataSource.getFoodsData())
    }

    /*private fun setupSwitch() {
       binding.ibSwitchMode.setOnClickListener(){
           if (adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) {
               binding.ibSwitchMode.setImageDrawable(
                   ContextCompat.getDrawable(
                       requireContext(),
                       R.drawable.ic_linear_mode
                   )
               )
               (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 1
               adapter.adapterLayoutMode = AdapterLayoutMode.GRID
               adapter.refreshList()
           } else {
               binding.ibSwitchMode.setImageDrawable(
                   ContextCompat.getDrawable(
                       requireContext(),
                       R.drawable.ic_grid_mode
                   )
               )
               (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 2
               adapter.adapterLayoutMode = AdapterLayoutMode.LINEAR
               adapter.refreshList()
           }
       }
        *//*binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = if (isChecked) 2 else 1
            adapter.adapterLayoutMode = if (isChecked) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapter.refreshList()
        }*//*
    }*/

    private fun setupSwitch() {
        binding.ibSwitchMode.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        when (adapter.adapterLayoutMode) {
            AdapterLayoutMode.LINEAR -> {
                binding.ibSwitchMode.setImageResource(R.drawable.ic_grid_mode)
                (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 2
                adapter.adapterLayoutMode = AdapterLayoutMode.GRID
            }
            AdapterLayoutMode.GRID -> {
                binding.ibSwitchMode.setImageResource(R.drawable.ic_linear_mode)
                (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 1
                adapter.adapterLayoutMode = AdapterLayoutMode.LINEAR
            }
        }
        adapter.refreshList()
    }


}