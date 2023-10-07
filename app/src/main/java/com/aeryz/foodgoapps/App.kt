package com.aeryz.foodgoapps

import android.app.Application
import com.aeryz.foodgoapps.data.local.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}