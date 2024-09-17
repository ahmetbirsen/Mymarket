package com.example.mymarket.domain.usecase

import com.example.mymarket.domain.usecase.addproducttocart.AddProductToCartUseCase
import com.example.mymarket.domain.usecase.clearCartProducts.ClearCartProducts
import com.example.mymarket.domain.usecase.clearCartProducts.ClearFavoriteProducts
import com.example.mymarket.domain.usecase.completeorder.CompleteOrderUseCase
import com.example.mymarket.domain.usecase.decreasecartproduct.DecreaseCartProductUseCase
import com.example.mymarket.domain.usecase.deletefavorite.DeleteFavoriteProductUseCase
import com.example.mymarket.domain.usecase.getProductById.GetProductByIdUseCase
import com.example.mymarket.domain.usecase.getcartproductcount.GetCartProductCountUseCase
import com.example.mymarket.domain.usecase.getcartproducts.GetCartProductsUseCase
import com.example.mymarket.domain.usecase.getfavoriteproducts.GetFavoriteProductsUseCase
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.domain.usecase.increasecartproduct.IncreaseCartProductUseCase
import com.example.mymarket.domain.usecase.insertfavorite.InsertFavoriteProductUseCase
import com.example.mymarket.domain.usecase.isproductfavorite.IsProductFavorite
import com.example.mymarket.domain.usecase.removeproductfromcart.RemoveProductFromCartUseCase
import com.example.mymarket.domain.usecase.updatecartproduct.UpdateCartProductUseCase

data class MarketUseCases(
    val getProductsUseCase: GetProductsUseCase,
    val getProductById: GetProductByIdUseCase,
    val insertFavoriteProductUseCase: InsertFavoriteProductUseCase,
    val isProductFavorite: IsProductFavorite,
    val deleteFavoriteProductUseCase: DeleteFavoriteProductUseCase,
    val addProductToCartUseCase: AddProductToCartUseCase,
    val getCartProductCountUseCase: GetCartProductCountUseCase,
    val getCartProductsUseCase: GetCartProductsUseCase,
    val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    val updateCartProductUseCase: UpdateCartProductUseCase,
    val increaseCartProductUseCase: IncreaseCartProductUseCase,
    val decreaseCartProductUseCase: DecreaseCartProductUseCase,
    val clearCartProducts: ClearCartProducts,
    val clearFavoriteProducts: ClearFavoriteProducts,
    val completeOrderUseCase: CompleteOrderUseCase,
    val getFavoriteProductsUseCase: GetFavoriteProductsUseCase
)