package com.example.mymarket.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    selectedColor: Color? = null,
    enabled: Boolean = true,
    unselectedColor: Color? = null,
    disabledSelectedColor: Color? = null,
    disabledUnselectedColor: Color? = null,
    onSelectedChange: ((Boolean) -> Unit)? = null,
) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        onClick = { onSelectedChange?.invoke(!selected) },
        enabled = enabled,
        colors = RadioButtonDefaults.colors(
            selectedColor = selectedColor ?: MaterialTheme.colorScheme.primary,
            unselectedColor = unselectedColor
                ?: MaterialTheme.colorScheme.secondaryContainer,
            disabledSelectedColor = disabledSelectedColor ?: MaterialTheme.colorScheme.primary,
            disabledUnselectedColor = disabledUnselectedColor
                ?: MaterialTheme.colorScheme.secondary,
        )
    )
}

@Preview
@Composable
fun PreviewCustomRadioButton() {
    Column {
        CustomRadioButton(selected = true)
        Spacer(modifier = Modifier.height(8.dp))
        CustomRadioButton()
        Spacer(modifier = Modifier.height(8.dp))
        CustomRadioButton(selected = true, selectedColor = Color.Red)
        CustomRadioButton(selected = false, unselectedColor = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        CustomRadioButton(selected = true, enabled = false, disabledSelectedColor = Color.Yellow)
        CustomRadioButton(enabled = false, disabledUnselectedColor = Color.Green)
    }
}