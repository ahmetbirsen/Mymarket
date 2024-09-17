package com.example.mymarket.domain.model

import com.example.mymarket.domain.util.SortOrder

data class FilterCriteria(
    val selectedBrands: List<String> = emptyList(),
    val selectedModels: List<String> = emptyList(),
    val sortOrder: SortOrder? = null
)
