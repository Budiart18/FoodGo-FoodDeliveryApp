package com.aeryz.foodgoapps.di

import com.aeryz.foodgoapps.data.local.database.AppDatabase
import com.aeryz.foodgoapps.data.local.database.datasource.CartDataSource
import com.aeryz.foodgoapps.data.local.database.datasource.CartDatabaseDataSource
import com.aeryz.foodgoapps.data.local.datastore.UserPreferenceDataSource
import com.aeryz.foodgoapps.data.local.datastore.UserPreferenceDataSourceImpl
import com.aeryz.foodgoapps.data.local.datastore.appDataStore
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoApiDataSource
import com.aeryz.foodgoapps.data.network.api.datasource.FoodGoDataSource
import com.aeryz.foodgoapps.data.network.api.service.FoodGoApiService
import com.aeryz.foodgoapps.data.network.firebase.auth.FirebaseAuthDataSource
import com.aeryz.foodgoapps.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.aeryz.foodgoapps.data.repository.CartRepository
import com.aeryz.foodgoapps.data.repository.CartRepositoryImpl
import com.aeryz.foodgoapps.data.repository.ProductRepository
import com.aeryz.foodgoapps.data.repository.ProductRepositoryImpl
import com.aeryz.foodgoapps.data.repository.UserRepository
import com.aeryz.foodgoapps.data.repository.UserRepositoryImpl
import com.aeryz.foodgoapps.presentation.cart.CartViewModel
import com.aeryz.foodgoapps.presentation.checkout.CheckoutViewModel
import com.aeryz.foodgoapps.presentation.detail.DetailViewModel
import com.aeryz.foodgoapps.presentation.home.HomeViewModel
import com.aeryz.foodgoapps.presentation.login.LoginViewModel
import com.aeryz.foodgoapps.presentation.main.MainViewModel
import com.aeryz.foodgoapps.presentation.profile.ProfileViewModel
import com.aeryz.foodgoapps.presentation.register.RegisterViewModel
import com.aeryz.foodgoapps.presentation.splashscreen.SplashViewModel
import com.aeryz.foodgoapps.settings.SettingsViewModel
import com.aeryz.foodgoapps.utils.PreferenceDataStoreHelper
import com.aeryz.foodgoapps.utils.PreferenceDataStoreHelperImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    fun getModules(): List<Module> = listOf(
        localModule,
        networkModule,
        dataSource,
        repository,
        viewModels
    )

    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { FoodGoApiService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSource = module {
        single<FoodGoDataSource> { FoodGoApiDataSource(get()) }
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repository = module {
        single<ProductRepository> { ProductRepositoryImpl(get()) }
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModels = module {
        viewModel { HomeViewModel(get(), get()) }
        viewModel { params -> DetailViewModel(params.get(), get()) }
        viewModelOf(::MainViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::SplashViewModel)
        viewModelOf(::SettingsViewModel)
    }
}
