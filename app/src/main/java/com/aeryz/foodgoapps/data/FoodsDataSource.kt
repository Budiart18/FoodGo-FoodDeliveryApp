package com.aeryz.foodgoapps.data

import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.model.Food

interface FoodsDataSource {
    fun getFoodsData() : List<Food>
}

class FoodsDataSourceImpl() : FoodsDataSource {
    override fun getFoodsData(): List<Food> {
        return mutableListOf(
            Food(R.drawable.iv_starbucks, "Coffee Starbucks", "Rp. 40000", "4.8", "1.0 km"),
            Food(R.drawable.iv_kfc, "Fried Chicken KFC", "Rp. 42000", "4.5", "0.5 km"),
            Food(R.drawable.iv_mc_donalds, "Burger McDonalds", "Rp. 35000", "4.5", "1.2 km"),
            Food(R.drawable.iv_j_co, "Donat J.Co", "Rp. 45000", "4.9", "1.5 km"),
            Food(R.drawable.iv_fruits_salad, "Fruits Salad", "Rp. 25000", "4.3", "0.7 km"),
            Food(R.drawable.iv_noodles, "Noodles Chicken", "Rp. 18000", "4.4", "2.0 km"),
        )
    }
}