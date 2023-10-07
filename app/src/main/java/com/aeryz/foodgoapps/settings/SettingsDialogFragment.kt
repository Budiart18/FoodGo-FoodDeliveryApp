package com.aeryz.foodgoapps.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aeryz.foodgoapps.data.local.datastore.UserPreferenceDataSourceImpl
import com.aeryz.foodgoapps.data.local.datastore.appDataStore
import com.aeryz.foodgoapps.databinding.FragmentSettingsDialogBinding
import com.aeryz.foodgoapps.presentation.main.MainViewModel
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.PreferenceDataStoreHelperImpl
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SettingsDialogFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentSettingsDialogBinding

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
        binding = FragmentSettingsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwitchAction()
        observeDarkModePref()
    }

    private fun observeDarkModePref() {
        mainViewModel.userDarkModeLiveData.observe(this) { isUsingDarkMode ->
            binding.swDarkMode.isChecked = isUsingDarkMode
        }
    }

    private fun setSwitchAction() {
        binding.swDarkMode.setOnCheckedChangeListener { _, isUsingDarkMode ->
            viewModel.setUserDarkModePref(isUsingDarkMode)
        }
    }
}