package com.aeryz.foodgoapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.foodgoapps.data.dummy.DummyCategoryDataSource
import com.aeryz.foodgoapps.data.dummy.DummyCategoryDataSourceImpl
import com.aeryz.foodgoapps.data.local.database.AppDatabase
import com.aeryz.foodgoapps.data.local.database.datasource.ProductDatabaseDataSource
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.data.repository.ProductRepositoryImpl
import com.aeryz.foodgoapps.databinding.FragmentHomeBinding
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.presentation.detail.DetailActivity
import com.aeryz.foodgoapps.presentation.home.adapter.HomeAdapter
import com.aeryz.foodgoapps.settings.SettingsDialogFragment
import com.aeryz.foodgoapps.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val adapter: HomeAdapter by lazy {
        HomeAdapter(
            onProductClicked = { navigateToDetail(it) },
            onSettingsClicked = { openSettingDialog() }
        )
    }

    private fun openSettingDialog() {
        SettingsDialogFragment().show(childFragmentManager, null)
    }

    private val viewModel: HomeViewModel by viewModels {
        val cds: DummyCategoryDataSource = DummyCategoryDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val productDao = database.productDao()
        val productDataSource = ProductDatabaseDataSource(productDao)
        val repo: ProductRepository = ProductRepositoryImpl(productDataSource, cds)
        GenericViewModelFactory.create(HomeViewModel(repo))
    }

    private fun navigateToDetail(product: Product) {
        DetailActivity.startActivity(requireContext(),product)
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
        fetchData()
    }

    private fun setupList() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }

    private fun fetchData() {
        viewModel.homeData.observe(viewLifecycleOwner){
            adapter.submitData(it)
        }
    }

}
