package com.example.mymarket.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ItemFilterCheck(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    onChecked: (String, Boolean) -> Unit = { _, _ -> },
    textStyle: TextStyle? = null,
) {
    Row(
        modifier = modifier
            .clickable {
                onChecked(text, !isChecked)
            }
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckBox(
            modifier = Modifier,
            checked = isChecked,
            onCheckedChange = {
                onChecked(text, it)
            },
            uncheckedColor = Color.Blue,
            checkedBoxColor = Color.Blue,
            checkMarkColor = Color.White,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = text,
            style = textStyle ?: TextStyle()
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(showSystemUi = true)
@Composable
private fun PreviewItem() {
    Column {
        ItemFilterCheck(
            text = "checked",
            modifier = Modifier,
            isChecked = true,
        )
    }
}