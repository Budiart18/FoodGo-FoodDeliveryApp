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
                foodDescription = "Kopi Starbucks: Kenikmatan kopi berkualitas dengan berbagai pilihan rasa dan susu.",
                foodPrice = 30000.0,
                foodImage = R.drawable.iv_starbucks,
                foodRating = 4.7,
                foodShopDistance = 1.0,
                foodShopLocation = "Jl. Kemanggisan Raya No.34, RT.8/RW.5, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                foodShopUrl = "https://maps.app.goo.gl/iRdDCzuR5UAeEsZj9"
            ),
            Food(
                foodName = "Fried Chicken KFC",
                foodDescription = "Kopi Starbucks: Kenikmatan kopi berkualitas dengan berbagai pilihan rasa dan susu.",
                foodPrice = 20000.0,
                foodImage = R.drawable.iv_kfc,
                foodRating = 4.5,
                foodShopDistance = 0.5,
                foodShopLocation = "Jl. Panjang Arteri Klp. Dua Raya, RT.6/RW.2, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                foodShopUrl = "https://maps.app.goo.gl/FD3PP1QBXFND6pvB8"
            ),
            Food(
                foodName = "Burger McDonalds",
                foodDescription = "Burger ikonik dengan daging panggang, sayuran segar, dan saus lezat dalam roti empuk.",
                foodPrice = 30000.0,
                foodImage = R.drawable.iv_mc_donalds,
                foodRating = 4.5,
                foodShopDistance = 1.0,
                foodShopLocation = "Jl. Panjang Arteri Klp. Dua Raya No.16, RT.1/RW.2, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                foodShopUrl = "https://maps.app.goo.gl/2QgZ9riRuzEQsyWu6"
            ),
            Food(
                foodName = "Donat J.Co",
                foodDescription = "Donat lembut dengan berbagai pilihan toping dan gula halus yang membuatnya nikmat.",
                foodPrice = 10000.0,
                foodImage = R.drawable.iv_j_co,
                foodRating = 4.7,
                foodShopDistance = 1.5,
                foodShopLocation = "Jl. Joglo Raya No.34C, RT.9/RW.3, Joglo, Kec. Kembangan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11640",
                foodShopUrl = "https://maps.app.goo.gl/QVdnXm9puNxQVQWg8"
            ),
            Food(
                foodName = "Fruits Salad",
                foodDescription = "Campuran segar berbagai buah dengan rasa manis alami dan nutrisi yang sehat.",
                foodPrice = 40000.0,
                foodImage = R.drawable.iv_fruits_salad,
                foodRating = 4.7,
                foodShopDistance = 0.7,
                foodShopLocation = "Jl. Kebon jeruk raya no.8 RT 7 RW 1 Kebon Jeruk 1, Daerah Khusus Ibukota Jakarta 11530",
                foodShopUrl = "https://maps.app.goo.gl/qAG6johqSB7QSTRAA"
            ),
            Food(
                foodName = "Noodles Chicken",
                foodDescription = "Mie lezat dengan potongan ayam, kuah khas, dan bumbu yang menggugah selera.",
                foodPrice = 25000.0,
                foodImage = R.drawable.iv_noodles,
                foodRating = 4.6,
                foodShopDistance = 2.0,
                foodShopLocation = "Jl. Panjang Arteri Klp. Dua Raya No.12, RT.8/RW.2, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                foodShopUrl = "https://maps.app.goo.gl/jL6hszRaS7CwiZ9z5"
            ),

        )
    }
}