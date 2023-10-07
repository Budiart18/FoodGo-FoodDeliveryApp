package com.aeryz.foodgoapps.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.isEditModeEnabled.observe(viewLifecycleOwner, Observer { isEditModeEnabled ->
            if (isEditModeEnabled) {
                binding.ivEdit.setImageResource(R.drawable.ic_edit_active)
            } else {
                binding.ivEdit.setImageResource(R.drawable.ic_edit)
            }
            binding.etUsername.isEnabled = isEditModeEnabled
            binding.etPassword.isEnabled = isEditModeEnabled
            binding.etEmail.isEnabled = isEditModeEnabled
            binding.etPhoneNumber.isEnabled = isEditModeEnabled
        })


        binding.ivEdit.setOnClickListener {
            viewModel.toggleEditMode()
        }

        binding.buttonSubmitProfile.setOnClickListener {
            viewModel.submitProfile()
        }
    }

}