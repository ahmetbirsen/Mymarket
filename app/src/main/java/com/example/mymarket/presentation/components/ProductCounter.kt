package com.example.mymarket.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymarket.R
import com.example.mymarket.domain.model.CartProduct
import com.example.mymarket.domain.model.Product
import com.example.mymarket.domain.util.StringExt.empty


@Composable
fun ProductCounter(
    modifier: Modifier = Modifier,
    totalCount: String = String.empty,
    decreaseClick : () -> Unit = {},
    increaseClick : () -> Unit = {},
) {
    Row(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .clickable { decreaseClick() }
            .weight(1f)
            .background(color = Color(0xFFDADEE4), shape = RoundedCornerShape(4.dp) )
        )
        {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp).fillMaxWidth(),
                text = stringResource(id = R.string.minus_symbol),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            )
        }
        Box(modifier = Modifier
            .weight(1f)
            .background(color = Color(0xFF2A59FE), shape = RoundedCornerShape(4.dp) )
        )
        {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp).fillMaxWidth(),
                text = totalCount,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            )
        }
        Box(modifier = Modifier
            .clickable { increaseClick() }
            .weight(1f)
            .background(color = Color(0xFFDADEE4), shape = RoundedCornerShape(4.dp) )
        )
        {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp).fillMaxWidth(),
                text = stringResource(id = R.string.plus_symbol),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewComponent() {
    ProductCounter()
}