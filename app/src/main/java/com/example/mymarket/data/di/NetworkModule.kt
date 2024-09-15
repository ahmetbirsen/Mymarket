package com.example.mymarket.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mymarket.data.datasource.locale.MarketDao
import com.example.mymarket.data.datasource.locale.MarketDatabase
import com.example.mymarket.data.datasource.remote.ProductService
import com.example.mymarket.data.repository.ProductRepositoryImpl
import com.example.mymarket.domain.repository.ProductRepository
import com.example.mymarket.domain.usecase.MarketUseCases
import com.example.mymarket.domain.usecase.addproducttocart.AddProductToCartUseCase
import com.example.mymarket.domain.usecase.decreasecartproduct.DecreaseCartProductUseCase
import com.example.mymarket.domain.usecase.deletefavorite.DeleteFavoriteProductUseCase
import com.example.mymarket.domain.usecase.getProductById.GetProductByIdUseCase
import com.example.mymarket.domain.usecase.getcartproductcount.GetCartProductCountUseCase
import com.example.mymarket.domain.usecase.getcartproducts.GetCartProductsUseCase
import com.example.mymarket.domain.usecase.getproducts.GetProductsUseCase
import com.example.mymarket.domain.usecase.getproductsfromroom.GetProductsFromRoom
import com.example.mymarket.domain.usecase.increasecartproduct.IncreaseCartProductUseCase
import com.example.mymarket.domain.usecase.insertfavorite.InsertFavoriteProductUseCase
import com.example.mymarket.domain.usecase.isproductfavorite.IsProductFavorite
import com.example.mymarket.domain.usecase.removeproductfromcart.RemoveProductFromCartUseCase
import com.example.mymarket.domain.usecase.updatecartproduct.UpdateCartProductUseCase
import com.example.mymarket.domain.util.Constants.BASE_URL
import com.example.mymarket.domain.util.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideMarketDatabase(app: Application): MarketDatabase {
        return Room.databaseBuilder(
            app,
            MarketDatabase::class.java,
            MarketDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMarketDao(db: MarketDatabase): MarketDao {
        return db.marketDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(productService : ProductService, db: MarketDatabase) : ProductRepository {
        return ProductRepositoryImpl(productService = productService, dao = db.marketDao())
    }

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceManager(context)

    @Provides
    @Singleton
    fun provideMarketUseCases(repository: ProductRepository, db: MarketDatabase): MarketUseCases {
        return MarketUseCases(
            getProductsUseCase = GetProductsUseCase(repository, db.marketDao()),
            getProductById = GetProductByIdUseCase(dao = db.marketDao()),
            insertFavoriteProductUseCase = InsertFavoriteProductUseCase(dao = db.marketDao()),
            isProductFavorite = IsProductFavorite(repository = repository),
            deleteFavoriteProductUseCase = DeleteFavoriteProductUseCase(productRepository = repository),
            addProductToCartUseCase = AddProductToCartUseCase(productRepository = repository),
            getCartProductCountUseCase = GetCartProductCountUseCase(repository = repository),
            getCartProductsUseCase = GetCartProductsUseCase(productRepository = repository),
            removeProductFromCartUseCase = RemoveProductFromCartUseCase(productRepository = repository),
            updateCartProductUseCase = UpdateCartProductUseCase(productRepository = repository),
            increaseCartProductUseCase = IncreaseCartProductUseCase(productRepository = repository),
            decreaseCartProductUseCase = DecreaseCartProductUseCase(productRepository = repository)
        )
    }
}