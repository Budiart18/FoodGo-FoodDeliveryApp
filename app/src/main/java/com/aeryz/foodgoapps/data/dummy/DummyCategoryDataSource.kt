package com.aeryz.foodgoapps.data.dummy

import com.aeryz.foodgoapps.model.Category

interface DummyCategoryDataSource {
    fun getCategoriesData() : List<Category>
}

class DummyCategoryDataSourceImpl : DummyCategoryDataSource {
    override fun getCategoriesData(): List<Category> {
        return mutableListOf(
            Category(
                categoryImage = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/category_image/ic_category_chicken.webp",
                categoryName = "Chicken"
            ),
            Category(
                categoryImage = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/category_image/ic_category_burger.webp",
                categoryName = "Burger"
            ),
            Category(
                categoryImage = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/category_image/ic_category_noodles.webp",
                categoryName = "Noodle"
            ),
            Category(
                categoryImage = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/category_image/ic_category_dessert.webp",
                categoryName = "Dessert"
            ),
            Category(
                categoryImage = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/category_image/ic_category_pizza.webp",
                categoryName = "Pizza"
            ),
            Category(
                categoryImage = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/category_image/ic_category_drink.webp",
                categoryName = "Drink"
            )
        )
    }
}