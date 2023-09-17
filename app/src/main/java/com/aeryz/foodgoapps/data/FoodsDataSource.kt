package com.aeryz.foodgoapps.data

import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.model.Food

interface FoodsDataSource {
    fun getFoodsData() : List<Food>
}

class FoodsDataSourceImpl() : FoodsDataSource {
    override fun getFoodsData(): List<Food> {
        return mutableListOf(
            Food(
                foodName = "Coffee Starbucks",
                foodDescription = "inpo detail",
                foodPrice = "Rp. 40000",
                foodImage = R.drawable.iv_starbucks,
                foodRating = "4.8",
                foodShopDistance = "1.0 km",
                foodShopLocation = "inpo lokasi"
            ),
            Food(
                foodName = "Fried Chicken KFC",
                foodDescription = "inpo detail",
                foodPrice = "Rp. 42000",
                foodImage = R.drawable.iv_kfc,
                foodRating = "4.5",
                foodShopDistance = "0.5 km",
                foodShopLocation = "inpo lokasi"
            ),
            Food(
                foodName = "Burger McDonalds",
                foodDescription = "inpo detail",
                foodPrice = "Rp. 35000",
                foodImage = R.drawable.iv_mc_donalds,
                foodRating = "4.5",
                foodShopDistance = "1.0 km",
                foodShopLocation = "inpo lokasi"
            ),
            Food(
                foodName = "Donat J.Co",
                foodDescription = "inpo detail",
                foodPrice = "Rp. 45000",
                foodImage = R.drawable.iv_j_co,
                foodRating = "4.9",
                foodShopDistance = "1.5 km",
                foodShopLocation = "inpo lokasi"
            ),
            Food(
                foodName = "Fruits Salad",
                foodDescription = "inpo detail",
                foodPrice = "Rp. 25000",
                foodImage = R.drawable.iv_fruits_salad,
                foodRating = "4.3",
                foodShopDistance = "0.7 km",
                foodShopLocation = "inpo lokasi"
            ),
            Food(
                foodName = "Noodles Chicken",
                foodDescription = "inpo detail",
                foodPrice = "Rp. 18000",
                foodImage = R.drawable.iv_noodles,
                foodRating = "4.4",
                foodShopDistance = "2.0 km",
                foodShopLocation = "inpo lokasi"
            ),

        )
    }
}