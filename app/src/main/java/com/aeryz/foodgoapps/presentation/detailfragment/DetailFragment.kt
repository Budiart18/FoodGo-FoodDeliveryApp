package com.aeryz.foodgoapps.presentation.detailfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aeryz.foodgoapps.R
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
        setClickListener()
        countTotalFood()
    }

    private fun setClickListener() {
        binding.tvShopLocation.setOnClickListener{
            navigateToShopMaps()
        }
        binding.bAddToCart.setOnClickListener {
            addFoodToCart()
        }
    }

    private fun addFoodToCart() {
        Toast.makeText(requireContext(), "${food?.foodName} Added to cart!", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToShopMaps() {
        val url = food?.foodShopUrl
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun countTotalFood(){
        var totalFood : Int = 1
        val plusButton = binding.ivPlusButton
        val minusButton = binding.ivMinusButton
        val textTotalFood = binding.tvFoodTotal
        plusButton.setOnClickListener{
            totalFood += 1
            textTotalFood.text = totalFood.toString()
        }
        minusButton.setOnClickListener{
            if (totalFood <= 1){
                totalFood = 1
            } else {
                totalFood -= 1
                textTotalFood.text = totalFood.toString()
            }
        }
    }


    private fun showFoodDetail() {
        if (food != null){
            binding.ivFoodDetailImg.setImageResource(food?.foodImage!!)
            binding.tvFoodName.text = food?.foodName
            binding.tvFoodPrice.text = getString(R.string.text_food_price_format,food?.foodPrice)
            binding.tvFoodDescription.text = food?.foodDescription
            binding.tvFoodShopDistance.text = getString(R.string.text_shop_distance_format,food?.foodShopDistance)
            binding.tvFoodRating.text = getString(R.string.text_food_reviews_format,food?.foodRating)
            binding.tvShopLocation.text = food?.foodShopLocation
        } else {
            Toast.makeText(requireContext(), "Food Detail is Null", Toast.LENGTH_SHORT).show()
        }
    }

}