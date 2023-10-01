package com.aeryz.foodgoapps.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.datasource.local.datastore.UserPreferenceDataSourceImpl
import com.aeryz.foodgoapps.data.datasource.local.datastore.appDataStore
import com.aeryz.foodgoapps.databinding.ItemSectionFoodListBinding
import com.aeryz.foodgoapps.presentation.main.MainViewModel
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.PreferenceDataStoreHelperImpl
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsToogleLayoutMode : BottomSheetDialogFragment() {


    private lateinit var binding: ItemSectionFoodListBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var isGridModeKey: Preferences.Key<String>
    private lateinit var isGridLayoutManager: Preferences.Key<String>

    private val viewModel: SettingsViewModel by viewModels {
        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(SettingsViewModel(userPreferenceDataSource))
    }

    private val mainViewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemSectionFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().appDataStore
        isGridModeKey = stringPreferencesKey("isGridMode")
        isGridLayoutManager = stringPreferencesKey("isGridLayoutManager")

        lifecycleScope.launch {
            observeGridMode()
            observeLayoutManager()
            setupImageButton()
            setupRecyclerView()
        }
    }

    private suspend fun observeGridMode() {
        dataStore.data.first()[isGridModeKey]?.let { isGridMode ->
            updateImage(isGridMode.toBoolean())
        }

        dataStore.data.collect { preferences ->
            preferences[isGridModeKey]?.let { isGridMode ->
                updateImage(isGridMode.toBoolean())
            }
        }
    }

    private suspend fun observeLayoutManager() {
        dataStore.data.first()[isGridLayoutManager]?.let { isGridLayoutManager ->
            updateLayoutManager(isGridLayoutManager.toBoolean())
        }

        dataStore.data.collect { preferences ->
            preferences[isGridLayoutManager]?.let { isGridLayoutManager ->
                updateLayoutManager(isGridLayoutManager.toBoolean())
            }
        }
    }

    private fun setupImageButton() {
        binding.ibSwitchMode.setOnClickListener {
            lifecycleScope.launch {
                val currentGridMode = getCurrentGridMode()
                val newGridMode = !currentGridMode
                updateGridMode(newGridMode)
                updateLayoutManager(newGridMode)
            }
        }
    }

    private suspend fun getCurrentGridMode(): Boolean {
        return dataStore.data.first()[isGridModeKey]?.toBoolean() ?: false
    }

    private suspend fun updateGridMode(isGridMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[isGridModeKey] = isGridMode.toString()
        }
    }

    private suspend fun updateLayoutManager(isGridMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[isGridLayoutManager] = isGridMode.toString()
        }
        setupRecyclerView()
    }

    private fun updateImage(isGridMode: Boolean) {
        val imageRes = if (isGridMode) R.drawable.ic_grid_mode else R.drawable.ic_linear_mode
        binding.ibSwitchMode.setImageResource(imageRes)
    }

    private suspend fun setupRecyclerView() {
        val layoutManager = if (getCurrentGridMode()) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }
        binding.rvListFoods.layoutManager = layoutManager
    }

}