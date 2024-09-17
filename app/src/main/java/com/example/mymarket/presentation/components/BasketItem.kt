package com.example.mymarket.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.mymarket.R
import com.example.mymarket.domain.model.ProductDto
import com.example.mymarket.core.util.StringExt.empty
import com.example.mymarket.core.util.StringExt.formatPrice

@Composable
fun BasketItem(
    product : ProductDto,
    modifier: Modifier = Modifier,
    onDecreaseClick : () -> Unit = {},
    onIncreaseClick : () -> Unit = {}
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.name ?: String.empty,
            )
            Text(
                text = stringResource(
                    id = R.string.price_with_currency,
                    product.price?.formatPrice() ?: String.empty
                ),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF2A59FE)
                )
            )
        }
        ProductCounter(
            totalCount = product.quantity.toString(),
            modifier = Modifier.weight(1f),
            decreaseClick = onDecreaseClick,
            increaseClick = onIncreaseClick
        )
    }
}