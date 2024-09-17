package com.example.mymarket.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymarket.core.util.StringExt.empty
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_products")
data class CartProduct(
    @SerializedName("id")
    @PrimaryKey var id: String = String.empty,
    @SerializedName("quantity")
    var quantity: Int
)
