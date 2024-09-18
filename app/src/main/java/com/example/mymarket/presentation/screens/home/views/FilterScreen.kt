package com.example.mymarket.presentation.screens.home.views

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mymarket.core.util.StringExt.empty
import com.example.mymarket.domain.model.FilterCriteria
import com.example.mymarket.domain.util.SortOrder
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.CustomRadioButton
import com.example.mymarket.presentation.components.ItemFilterCheck
import com.example.mymarket.presentation.components.SearchTextFieldComponent
import com.example.mymarket.presentation.screens.home.HomeState
import kotlinx.coroutines.delay

@Composable
fun FilterScreen(
    state: HomeState,
    getBrands: () -> Unit = {},
    getModels: () -> Unit = {},
    onApplyFilter : (FilterCriteria) -> Unit = {},
    onSearchBrand: (String) -> Unit = {},
    onSearchModel: (String) -> Unit = {},
) {
    val textStateForBrand = rememberSaveable { mutableStateOf(String.empty) }
    val textStateForModel = rememberSaveable { mutableStateOf(String.empty) }
    val newToOldRadioSelected = remember { mutableStateOf(false) }
    val oldToNewRadioSelected = remember { mutableStateOf(false) }
    val priceHighToLowRadioSelected = remember { mutableStateOf(false) }
    val priceLowToHighRadioSelected = remember { mutableStateOf(false) }
    val selectedBrandList = remember { mutableStateMapOf<String, Boolean>() }
    val selectedModelList = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(key1 = textStateForBrand.value, block = {
        if (textStateForBrand.value.isBlank()){
            getBrands()
        }else{
            delay(300)
            onSearchBrand(textStateForBrand.value)
        }
    })
    LaunchedEffect(key1 = textStateForModel.value, block = {
        if (textStateForModel.value.isBlank()){
            getModels()
        }else{
            delay(300)
            onSearchModel(textStateForModel.value)
        }
    })

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
        ItemFilterRadio(
            title = R.string.old_to_new,
            selectedOption = oldToNewRadioSelected,
            onSelect = {
                newToOldRadioSelected.value = false
                priceHighToLowRadioSelected.value = false
                priceLowToHighRadioSelected.value = false
            }
        )
        ItemFilterRadio(
            title = R.string.new_to_old,
            selectedOption = newToOldRadioSelected,
            onSelect = {
                oldToNewRadioSelected.value = false
                priceHighToLowRadioSelected.value = false
                priceLowToHighRadioSelected.value = false
            }
        )
        ItemFilterRadio(
            title = R.string.price_high_to_low,
            selectedOption = priceHighToLowRadioSelected,
            onSelect = {
                oldToNewRadioSelected.value = false
                newToOldRadioSelected.value = false
                priceLowToHighRadioSelected.value = false
            }
        )
        ItemFilterRadio(
            title = R.string.price_low_to_hight,
            selectedOption = priceLowToHighRadioSelected,
            onSelect = {
                oldToNewRadioSelected.value = false
                newToOldRadioSelected.value = false
                priceHighToLowRadioSelected.value = false
            }
        )
        Divider(color = Color.Gray, modifier = Modifier
            .fillMaxWidth()
            .width(1.dp))
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, top = 8.dp),
            text = stringResource(id = R.string.brand),
            style = TextStyle(
                color = Color.LightGray
            )
        )
        SearchTextFieldComponent(
            state = textStateForBrand,
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
        Box(modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.filteredBrands) { brand ->
                    val isChecked = selectedBrandList[brand] ?: false
                    ItemFilterCheck(
                        text = brand,
                        isChecked = isChecked,
                        onChecked = { selectedBrand, checked ->
                            selectedBrandList[selectedBrand] = checked
                        }
                    )
                }
            }
        }
        Divider(color = Color.Gray, modifier = Modifier
            .fillMaxWidth()
            .width(1.dp))

        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, top = 8.dp),
            text = stringResource(id = R.string.model),
            style = TextStyle(
                color = Color.LightGray
            )
        )
        SearchTextFieldComponent(
            state = textStateForModel,
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
        Box(modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.filteredModels) { model ->
                    val isChecked = selectedModelList[model] ?: false
                    ItemFilterCheck(
                        text = model,
                        isChecked = isChecked,
                        onChecked = { selectedModel, checked ->
                            selectedModelList[selectedModel] = checked
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(title = stringResource(id = R.string.apply_filter), onClick = {
            val selectedBrands = selectedBrandList.filterValues { it }.keys.toList()
            val selectedModels = selectedModelList.filterValues { it }.keys.toList()
            val filterCriteria = FilterCriteria(
                selectedBrands = selectedBrands,
                selectedModels = selectedModels,
                sortOrder = when {
                    oldToNewRadioSelected.value -> SortOrder.OLD_TO_NEW
                    newToOldRadioSelected.value -> SortOrder.NEW_TO_OLD
                    priceHighToLowRadioSelected.value -> SortOrder.PRICE_HIGH_TO_LOW
                    priceLowToHighRadioSelected.value -> SortOrder.PRICE_LOW_TO_HIGH
                    else -> null
                }
            )
            onApplyFilter(filterCriteria)
        })
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ItemFilterRadio(
    @StringRes title: Int,
    selectedOption : MutableState<Boolean> = mutableStateOf(false),
    onSelect: () -> Unit
){
    val selected = remember { selectedOption }
    Row(modifier = Modifier
        .clickable {
            onSelect()
        }
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        CustomRadioButton(
            selected = selected.value,
            onSelectedChange = {
                selected.value = it
                onSelect()
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