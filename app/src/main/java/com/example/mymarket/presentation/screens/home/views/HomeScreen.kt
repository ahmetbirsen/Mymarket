package com.example.mymarket.presentation.screens.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymarket.R
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.SearchTextFieldComponent
import com.example.mymarket.presentation.screens.home.HomeEvent
import com.example.mymarket.presentation.screens.home.HomeViewModel
import com.example.mymarket.presentation.util.Screen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val textState = rememberSaveable { mutableStateOf(state.searchQuery) }
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(HomeEvent.GetProducts)
    }
    LaunchedEffect(key1 = textState.value, block = {
        if (textState.value.isBlank()){
            viewModel.onEvent(HomeEvent.GetProducts)
        }else{
            delay(300)
            viewModel.onEvent(HomeEvent.SearchFilter(textState.value))
        }
    })
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            SearchTextFieldComponent(
                state = textState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                placeHolder = stringResource(id = R.string.search),
                placeHolderTextStyle = TextStyle(color = Color.Gray),
                onDone = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(id = R.string.search),
                        tint = Color.Gray
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.filters)
                )
                CustomButton(
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color.Gray,
                    title = stringResource(id = R.string.select_filters),
                    onClick = {
                        viewModel.onEvent(
                            HomeEvent.UpdateFilterModal(true)
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                content = {
                    state.products?.let {
                        items(it) { product ->
                            ProductListRow(
                                product = product,
                                onFavoriteClick = {
                                    if (it.isFavorite?.not() == true) {
                                        viewModel.onEvent(
                                            HomeEvent.InsertFavorite(it)
                                        )
                                    }
                                    else{
                                        viewModel.onEvent(
                                            HomeEvent.DeleteFavorite(it)
                                        )
                                    }
                                    viewModel.onEvent(HomeEvent.InsertFavorite(product))
                                },
                                onItemClick = {
                                    navController.navigate("${Screen.DetailScreen.route}/${it.id}")
                                },
                                onAddToCartClick = {
                                    viewModel.onEvent(HomeEvent.AddToCart(product))
                                },
                                onDecreaseClick = {
                                    viewModel.onEvent(HomeEvent.DecreaseCartProduct(product))
                                },
                                onIncreaseClick = {
                                    viewModel.onEvent(HomeEvent.IncreaseCartProduct(product))
                                },
                            )
                        }
                    }

                }
            )
        }
        if (!state.error.isNullOrEmpty()) {
            ShowError(modifier = Modifier.align(Alignment.Center))
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.filterBottomModal){
            ModalBottomSheet(
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                onDismissRequest = { viewModel.onEvent(
                    HomeEvent.UpdateFilterModal(false)
                ) }
            ) {
                FilterScreen(
                    state = state,
                    getBrands = {viewModel.onEvent(HomeEvent.GetBrands)},
                    getModels = {viewModel.onEvent(HomeEvent.GetModels)},
                    onSearchBrand = {viewModel.onEvent(HomeEvent.FilterByBrand(it))},
                    onSearchModel = {viewModel.onEvent(HomeEvent.FilterByModel(it))},
                    onApplyFilter = {
                        println("Ahaa : " +it)
                    }
                    )
            }
        }
    }
}

@Composable
fun ShowError(modifier: Modifier= Modifier){
    Text(
        text = "No products found",
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(14.dp)
    )
}
