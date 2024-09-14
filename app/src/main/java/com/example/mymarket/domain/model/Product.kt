package com.example.mymarket.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("model")
    val model: String? = null,
    @SerializedName("brand")
    val brand: String? = null,
    @SerializedName("id")
    val id: String? = null
)
