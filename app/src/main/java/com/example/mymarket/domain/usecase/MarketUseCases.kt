package com.example.mymarket.domain.usecase

import com.example.mymarket.domain.usecase.getProductById.GetProductByIdUseCase
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.domain.usecase.getproductsfromroom.GetProductsFromRoom
import com.example.mymarket.domain.usecase.insertfavorite.InsertFavoriteProductUseCase

data class MarketUseCases(
    val getProductsUseCase: GetProductsUseCase,
    val getProductById: GetProductByIdUseCase,
    val insertFavoriteProductUseCase: InsertFavoriteProductUseCase
)