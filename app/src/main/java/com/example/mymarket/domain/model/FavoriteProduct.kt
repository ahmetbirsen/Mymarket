package com.example.mymarket.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymarket.core.util.StringExt.empty
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @SerializedName("name")
    var name: String? = String.empty,
    @SerializedName("id")
    @PrimaryKey var id: String = String.empty,
)

data class FavoriteDto(
    @SerializedName("createdAt")
    var createdAt: String? = String.empty,
    @SerializedName("name")
    var name: String? = String.empty,
    @SerializedName("image")
    var image: String? = String.empty,
    @SerializedName("price")
    var price: String = String.empty,
    @SerializedName("description")
    var description: String? = String.empty,
    @SerializedName("model")
    var model: String? = String.empty,
    @SerializedName("brand")
    var brand: String? = String.empty,
    @SerializedName("id")
    var id: String = String.empty,
    @SerializedName("isFavorite")
    var isFavorite: Boolean? = false,
)
