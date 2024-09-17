package com.example.mymarket.core.util

object BooleanExt {
    fun Boolean?.safeGet(default: Boolean = false) = this ?: default
}