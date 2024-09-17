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
    secondText: String? = null,
    textStyle: TextStyle? = null,
    secondTextStyle: TextStyle? = null,
    onChecked: (Boolean) -> Unit = {},
    checked: MutableState<Boolean> = mutableStateOf(false)
) {
    Row(modifier = modifier
        .clickable {
            onChecked(!checked.value)
            checked.value = !checked.value
        }
        .fillMaxWidth()
        .background(Color.White),
        verticalAlignment = Alignment.CenterVertically) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCheckBox(
                modifier = Modifier,
                checked = checked.value,
                onCheckedChange = {
                    onChecked(it)
                    checked.value = it
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
}

@SuppressLint("UnrememberedMutableState")
@Preview(showSystemUi = true)
@Composable
private fun PreviewItem() {
    Column {
        ItemFilterCheck(
            text = "checked",
            checked = mutableStateOf(true),
            modifier = Modifier
        )
    }
}