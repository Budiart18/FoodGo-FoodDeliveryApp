package com.aeryz.foodgoapps.data.network.api.service

import com.aeryz.foodgoapps.BuildConfig
import com.aeryz.foodgoapps.data.network.api.model.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantService {

    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String): MenuResponse

    companion object {
        @JvmStatic
        operator fun invoke(): MenuResponse {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(MenuResponse::class.java)
        }
    }

}