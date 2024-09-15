package com.example.mymarket.domain.util

import java.text.NumberFormat
import java.util.Locale

object StringExt {
    val String.Companion.empty: String
        inline get() = ""

    val String.Companion.space: String
        inline get() = ""

    fun String.formatPrice(currency: String? = null, fractionDigits: Int = 0): String? {
        return try {
            val priceAsDouble = this.toDoubleOrNull() ?: return null

            val turkishNumberFormat = NumberFormat.getNumberInstance(Locale("tr", "TR"))
            turkishNumberFormat.maximumFractionDigits = fractionDigits

            currency?.let {
                return turkishNumberFormat.format(priceAsDouble).plus(String.space).plus(currency)
            }

            turkishNumberFormat.format(priceAsDouble)
        } catch (e: Exception) {
            null
        }
    }

    fun String.formatPriceWithZero(currency: String? = null, fractionDigits: Int = 0): String? {
        return try {
            val priceAsDouble = this.toDoubleOrNull() ?: return null

            val turkishNumberFormat = NumberFormat.getNumberInstance(Locale("tr", "TR"))
            turkishNumberFormat.maximumFractionDigits = fractionDigits
            val formattedPrice = turkishNumberFormat.format(priceAsDouble)
            val finalPrice = "$formattedPrice.000"
            currency?.let {
                return finalPrice.plus(String.space).plus(currency)
            }
            finalPrice
        } catch (e: Exception) {
            null
        }
    }
}