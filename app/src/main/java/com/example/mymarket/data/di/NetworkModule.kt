package com.example.mymarket.data.di

import com.example.mymarket.data.remote.ProductService
import com.example.mymarket.data.repository.ProductRepositoryImpl
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMovieApi() : ProductService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(productService : ProductService) : ProductRepository {
        return ProductRepositoryImpl(productService = productService)
    }
}