package com.example.mymarket.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymarket.domain.util.StringExt.empty
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @SerializedName("name")
    var name: String? = String.empty,
    @SerializedName("id")
    @PrimaryKey var id: String = String.empty,
)
