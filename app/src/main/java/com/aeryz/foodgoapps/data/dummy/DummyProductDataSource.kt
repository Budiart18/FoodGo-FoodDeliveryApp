package com.aeryz.foodgoapps.data.dummy

import com.aeryz.foodgoapps.model.Product

interface DummyProductDataSource {
    fun getProductList(): List<Product>
}

class DummyProductDataSourceImpl() : DummyProductDataSource {
    override fun getProductList(): List<Product> {
        return mutableListOf(
            Product(
                productName = "Coffee Starbucks",
                productDescription = "Kopi Starbucks: Kenikmatan kopi berkualitas dengan berbagai pilihan rasa dan susu.",
                productPrice = 30000.00,
                productImageUrl = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/product_image/img_product_starbucks.webp",
                productShopLocation = "Jl. Kemanggisan Raya No.34, RT.8/RW.5, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                productFormattedPrice = ""
            ),
            Product(
                productName = "Fried Chicken KFC",
                productDescription = "Kopi Starbucks: Kenikmatan kopi berkualitas dengan berbagai pilihan rasa dan susu.",
                productPrice = 20000.00,
                productImageUrl = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/product_image/img_product_kfc.webp",
                productShopLocation = "Jl. Panjang Arteri Klp. Dua Raya, RT.6/RW.2, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                productFormattedPrice = ""
            ),
            Product(
                productName = "Burger McDonalds",
                productDescription = "Burger ikonik dengan daging panggang, sayuran segar, dan saus lezat dalam roti empuk.",
                productPrice = 30000.00,
                productImageUrl = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/product_image/img_product_donalds.webp",
                productShopLocation = "Jl. Panjang Arteri Klp. Dua Raya No.16, RT.1/RW.2, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                productFormattedPrice = ""
            ),
            Product(
                productName = "Donat J.Co",
                productDescription = "Donat lembut dengan berbagai pilihan toping dan gula halus yang membuatnya nikmat.",
                productPrice = 10000.00,
                productImageUrl = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/product_image/img_product_j_co.webp",
                productShopLocation = "Jl. Joglo Raya No.34C, RT.9/RW.3, Joglo, Kec. Kembangan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11640",
                productFormattedPrice = ""
            ),
            Product(
                productName = "Fruits Salad",
                productDescription = "Campuran segar berbagai buah dengan rasa manis alami dan nutrisi yang sehat.",
                productPrice = 40000.00,
                productImageUrl = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/product_image/img_product_salad.webp",
                productShopLocation = "Jl. Kebon jeruk raya no.8 RT 7 RW 1 Kebon Jeruk 1, Daerah Khusus Ibukota Jakarta 11530",
                productFormattedPrice = ""
            ),
            Product(
                productName = "Noodles Chicken",
                productDescription = "Mie lezat dengan potongan ayam, kuah khas, dan bumbu yang menggugah selera.",
                productPrice = 25000.00,
                productImageUrl = "https://raw.githubusercontent.com/Budiart18/FoodGo-assets/main/product_image/img_product_noodles.webp",
                productShopLocation = "Jl. Panjang Arteri Klp. Dua Raya No.12, RT.8/RW.2, Kb. Jeruk, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11530",
                productFormattedPrice = ""
            )

        )
    }
}
