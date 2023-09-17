package com.aeryz.foodgoapps.presentation.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aeryz.foodgoapps.databinding.FragmentDetailBinding
import com.aeryz.foodgoapps.model.Food

class DetailFragment : Fragment() {

    companion object{
        const val ARGS_FOOD = "ARGS_FOOD"
    }

    private val food : Food? by lazy {
//        arguments?.getParcelable(ARGS_FOOD)
        DetailFragmentArgs.fromBundle(arguments as Bundle).food
    }

    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFoodDetail()
    }

    private fun showFoodDetail() {
        if (food != null){
            binding.ivFoodDetailImg.setImageResource(food?.foodImage!!)
            binding.tvFoodName.text = food?.foodName
            binding.tvFoodPrice.text = food?.foodPrice
            binding.tvFoodDescription.text = food?.foodDescription
            binding.tvFoodShopDistance.text = food?.foodShopDistance
            binding.tvFoodRating.text = food?.foodRating
            binding.tvShopLocation.text = food?.foodShopLocation
        } else {
            Toast.makeText(requireContext(), "Food Detail is Null", Toast.LENGTH_SHORT).show()
        }
    }

}