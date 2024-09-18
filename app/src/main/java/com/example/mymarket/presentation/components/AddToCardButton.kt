package com.example.mymarket.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymarket.R
import com.example.mymarket.core.util.StringExt.empty

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    title : String = String.empty,
    textColor : Color = Color.White,
    backgroundColor : Color = Color(0xFF2A59FE),
    onClick : () -> Unit = {}
) {
    Box(modifier = modifier
        .clickable { onClick() }
        .fillMaxWidth()
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(8.dp)
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.button.copy(
                textAlign = TextAlign.Center,
                color = textColor
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewComponent() {
    CustomButton(
        title = stringResource(id = R.string.add_to_card),
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    )
}