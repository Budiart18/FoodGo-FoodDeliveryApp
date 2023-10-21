package com.aeryz.foodgoapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.local.datastore.UserPreferenceDataSourceImpl
import com.aeryz.foodgoapps.data.local.datastore.appDataStore
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoApiDataSource
import com.aeryz.foodgoapps.data.network.api.service.FoodGoApiService
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.data.repository.ProductRepositoryImpl
import com.aeryz.foodgoapps.databinding.FragmentHomeBinding
import com.aeryz.foodgoapps.model.Product
import com.aeryz.foodgoapps.presentation.detail.DetailActivity
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.CategoryListAdapter
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.ProductListAdapter
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.ProductListAdapter.Companion.GRID_LAYOUT
import com.aeryz.foodgoapps.presentation.home.adapter.subadapter.ProductListAdapter.Companion.LINEAR_LAYOUT
import com.aeryz.foodgoapps.settings.SettingsDialogFragment
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.PreferenceDataStoreHelperImpl
import com.aeryz.foodgoapps.utils.proceedWhen
import com.chuckerteam.chucker.api.ChuckerInterceptor

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private val categoryAdapter : CategoryListAdapter by lazy {
        CategoryListAdapter(){
            viewModel.getProducts(it.categoryName.lowercase())
        }
    }
    private val productAdapter : ProductListAdapter by lazy {
        ProductListAdapter(LINEAR_LAYOUT){
            navigateToDetail(it)
        }
    }

    private val viewModel: HomeViewModel by viewModels {
        val chuckerInterceptor = ChuckerInterceptor(requireContext().applicationContext)
        val service = FoodGoApiService.invoke(chuckerInterceptor)
        val dataSource = FoodGoApiDataSource(service)
        val repo: ProductRepository =
            ProductRepositoryImpl(dataSource)

        val dataStore = requireActivity().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(HomeViewModel(repo, userPreferenceDataSource))
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
        setClickListener()
        getData()
        observeData()
    }

    private fun getData() {
        viewModel.getCategories()
        viewModel.getProducts()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner){
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateCategory.root.isVisible = false
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvListCategories.apply {
                    isVisible = true
                    adapter = categoryAdapter
                }
                it.payload?.let { data -> categoryAdapter.submitData(data) }
            }, doOnLoading = {
                binding.layoutStateCategory.root.isVisible = true
                binding.layoutStateCategory.pbLoading.isVisible = true
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvListCategories.isVisible = false
            }, doOnError = {
                binding.layoutStateCategory.root.isVisible = true
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = true
                binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                binding.rvListCategories.isVisible = false
            })
        }
        viewModel.products.observe(viewLifecycleOwner){
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateProduct.root.isVisible = false
                binding.layoutStateProduct.pbLoading.isVisible = false
                binding.layoutStateProduct.tvError.isVisible = false
                setUpProductRv()
                it.payload?.let { data -> productAdapter.submitData(data) }
            }, doOnLoading = {
                binding.layoutStateProduct.root.isVisible = true
                binding.layoutStateProduct.pbLoading.isVisible = true
                binding.layoutStateProduct.tvError.isVisible = false
                binding.rvListFoods.isVisible = false
            }, doOnError = {
                binding.layoutStateProduct.root.isVisible = true
                binding.layoutStateProduct.pbLoading.isVisible = false
                binding.layoutStateProduct.tvError.isVisible = true
                binding.layoutStateProduct.tvError.text = it.exception?.message.orEmpty()
                binding.rvListFoods.isVisible = false
            }, doOnEmpty = {
                binding.layoutStateProduct.root.isVisible = true
                binding.layoutStateProduct.pbLoading.isVisible = false
                binding.layoutStateProduct.tvError.isVisible = true
                binding.layoutStateProduct.tvError.text = R.string.text_product_not_found.toString()
                binding.rvListFoods.isVisible = false
            })
        }
    }

    private fun setUpProductRv(){
        viewModel.userLayoutMode.observe(viewLifecycleOwner){layoutMode ->
            binding.rvListFoods.apply {
                isVisible = true
                adapter = productAdapter
                layoutManager = GridLayoutManager(requireContext(), layoutMode)
            }
            productAdapter.layoutMode = layoutMode
        }
    }

    private fun setClickListener() {
        binding.ivSettings.setOnClickListener {
            openSettingDialog()
        }
        binding.ibSwitchMode.setOnClickListener {
            changeAdapterLayoutMode()
        }
    }

    private fun changeAdapterLayoutMode() {
        if (productAdapter.layoutMode == LINEAR_LAYOUT){
            productAdapter.layoutMode = GRID_LAYOUT
            binding.ibSwitchMode.load(R.drawable.ic_grid_mode)
            viewModel.setUserLayoutMode(GRID_LAYOUT)
            (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 2
        } else if (productAdapter.layoutMode == GRID_LAYOUT) {
            productAdapter.layoutMode = LINEAR_LAYOUT
            binding.ibSwitchMode.load(R.drawable.ic_linear_mode)
            viewModel.setUserLayoutMode(LINEAR_LAYOUT)
            (binding.rvListFoods.layoutManager as GridLayoutManager).spanCount = 1
        }
        productAdapter.refreshList()
    }

    private fun openSettingDialog() {
        SettingsDialogFragment().show(childFragmentManager, null)
    }

}