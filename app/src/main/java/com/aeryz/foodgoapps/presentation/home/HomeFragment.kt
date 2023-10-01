package com.aeryz.foodgoapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.foodgoapps.data.datasource.dummy.CategoryDataSource
import com.aeryz.foodgoapps.data.datasource.dummy.CategoryDataSourceImpl
import com.aeryz.foodgoapps.data.datasource.dummy.FoodsDataSource
import com.aeryz.foodgoapps.data.datasource.dummy.FoodsDataSourceImpl
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.data.repository.ProductRepositoryImpl
import com.aeryz.foodgoapps.databinding.FragmentHomeBinding
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.presentation.detail.DetailActivity
import com.aeryz.foodgoapps.presentation.home.adapter.HomeAdapter
import com.aeryz.foodgoapps.settings.SettingsToogleLayoutMode
import com.aeryz.foodgoapps.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val adapter: HomeAdapter by lazy {
        HomeAdapter(
            onProductClicked = { navigateToDetail(it) },
            onNotificationClicked = { openNotification() },
            onLayoutModeChanged = { toggleLayoutMode() }
        )
    }

    private fun toggleLayoutMode() {
        SettingsToogleLayoutMode()
    }

    private fun openNotification() {
        Toast.makeText(requireContext(), "Notification is empty", Toast.LENGTH_SHORT).show()
    }


    private fun setupList() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
        adapter.setItems(viewModel.getHomeSectionData())
    }


    private fun navigateToDetail(food: Food) {
        DetailActivity.startActivity(requireContext(),food)
    }

    private val viewModel: HomeViewModel by viewModels {
        val cds: CategoryDataSource = CategoryDataSourceImpl()
        val fds: FoodsDataSource = FoodsDataSourceImpl()
        val repo: ProductRepository = ProductRepositoryImpl(cds, fds)
        GenericViewModelFactory.create(HomeViewModel(repo))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
    }

}
