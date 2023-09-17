package com.aeryz.foodgoapps.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.databinding.ActivityMainBinding
import com.aeryz.foodgoapps.model.Food
import com.aeryz.foodgoapps.presentation.homefragment.FoodListAdapter

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}