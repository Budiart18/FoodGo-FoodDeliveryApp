package com.aeryz.foodgoapps.presentation.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.foodgoapps.data.FoodsDataSourceImpl
import com.aeryz.foodgoapps.databinding.FragmentFoodListBinding
import com.aeryz.foodgoapps.model.Food

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val adapter: FoodListAdapter by lazy {
        FoodListAdapter {
            navigateToDetailFragment(it)
        }
    }

    private fun navigateToDetailFragment(food: Food? = null) {
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
    }

    private fun setupRecyclerView() {
        binding.rvListFoods.adapter = adapter
        binding.rvListFoods.layoutManager = LinearLayoutManager(requireContext())
        adapter.setData(FoodsDataSourceImpl().getFoodsData())
    }

}