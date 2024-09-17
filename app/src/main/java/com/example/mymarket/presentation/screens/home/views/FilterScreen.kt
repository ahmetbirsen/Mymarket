package com.example.mymarket.presentation.screens.home.views

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymarket.R
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.CustomRadioButton
import com.example.mymarket.presentation.components.ItemFilterCheck
import com.example.mymarket.presentation.components.SearchTextFieldComponent
import com.example.mymarket.presentation.screens.home.HomeState

@Composable
fun FilterScreen(
    state: HomeState,
    checked: MutableState<Boolean> = mutableStateOf(false)
) {
    val textState = rememberSaveable { mutableStateOf(state.searchQuery) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.filter), style = TextStyle(
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 32.sp
        ))
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(id = R.string.sort_by),
            style = TextStyle(
                color = Color.LightGray
            )
        )
        ItemFilterRadio(title = R.string.old_to_new, state = state, onSelect = {})
        ItemFilterRadio(title = R.string.new_to_old, state = state, onSelect = {})
        ItemFilterRadio(title = R.string.price_high_to_low, state = state, onSelect = {})
        ItemFilterRadio(title = R.string.price_low_to_hight, state = state, onSelect = {})
        Divider(color = Color.Gray, modifier = Modifier
            .fillMaxWidth()
            .width(1.dp))
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            text = stringResource(id = R.string.brand),
            style = TextStyle(
                color = Color.LightGray
            )
        )
        SearchTextFieldComponent(
            state = textState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
            placeHolder = stringResource(id = R.string.search),
            onDone = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.search),
                )
            },
        )
        ItemFilterCheck(text = "Apple", onChecked = {}, checked = checked)
        Divider(color = Color.Gray, modifier = Modifier
            .fillMaxWidth()
            .width(1.dp))

        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            text = stringResource(id = R.string.model),
            style = TextStyle(
                color = Color.LightGray
            )
        )
        SearchTextFieldComponent(
            state = textState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
            placeHolder = stringResource(id = R.string.search),
            onDone = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.search),
                )
            },
        )
        ItemFilterCheck(text = "Apple", onChecked = {}, checked = checked)
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(title = stringResource(id = R.string.apply_filter), onClick = {})
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ItemFilterRadio(
    @StringRes title : Int,
    state : HomeState,
    onSelect : () -> Unit
){
    Row(modifier = Modifier
        .clickable {
            onSelect()
            /*onEvent(
                PriceTrackingNotificationScreenAction.UpdateEveryPriceDecrease(
                    !state.everyPriceDecrease
                )
            )*/
        }
        .fillMaxWidth()
        .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        CustomRadioButton(
            //selected = state.everyPriceDecrease,
            onSelectedChange = {
                onSelect()
                /*onEvent(
                    PriceTrackingNotificationScreenAction.UpdateEveryPriceDecrease(
                        !state.everyPriceDecrease
                    )
                )*/
            }
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = title),
            style = TextStyle(
                textAlign = TextAlign.Start
            ),
        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewScreen() {
    FilterScreen(
        state = HomeState()
    )
}