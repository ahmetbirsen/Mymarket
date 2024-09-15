package com.example.mymarket.presentation.screens.detail.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mymarket.R
import com.example.mymarket.domain.model.FavoriteProduct
import com.example.mymarket.domain.util.StringExt.empty
import com.example.mymarket.domain.util.StringExt.formatPrice
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.DefaultNavigationBar
import com.example.mymarket.presentation.components.ProductCounter
import com.example.mymarket.presentation.screens.detail.DetailEvent
import com.example.mymarket.presentation.screens.detail.DetailViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(DetailEvent.GetDetail(productId = state.productId))
    }
    LaunchedEffect(key1 = state.product) {
        println("DetailScreenAhmet: ${state.product.quantity}")
    }

    Scaffold(
        topBar = {
            DefaultNavigationBar(
                navController = navController,
                title = state.product.name,
                backButtonHidden = false,
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(16.dp)

            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(modifier = Modifier) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(225.dp),
                            painter = rememberImagePainter(data = state.product?.image),
                            contentDescription = String.empty
                        )
                        Icon(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(2.dp)
                                .clickable {
                                    if (!state.isFavorite) {
                                        viewModel.onEvent(
                                            DetailEvent.InsertFavorite(
                                                FavoriteProduct(
                                                    name = state.product?.name ?: String.empty,
                                                    id = state.product?.id ?: String.empty,
                                                )
                                            )
                                        )
                                    } else {
                                        viewModel.onEvent(
                                            DetailEvent.DeleteFavorite(
                                                productId = state.product?.id ?: String.empty
                                            )
                                        )
                                    }
                                }
                                .padding(4.dp),
                            painter = if (state.isFavorite) painterResource(id = R.drawable.ic_favorites_yellow) else painterResource(
                                id = R.drawable.ic_favorites
                            ),
                            contentDescription = String.empty,
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = state.product?.name ?: String.empty
                    )
                    Text(
                        text = state.product?.description ?: String.empty
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(id = R.string.price))
                        Text(
                            text = stringResource(
                                id = R.string.price_with_currency,
                                state.product.price?.formatPrice() ?: String.empty
                            )
                        )
                    }
                    if (state.product.quantity > 0) {
                        ProductCounter(
                            modifier = Modifier.weight(1f),
                            totalCount = state.product.quantity.toString(),
                            decreaseClick = {
                                viewModel.onEvent(
                                    DetailEvent.DecreaseCartProduct(
                                        state.product ?: return@ProductCounter
                                    )
                                )
                            },
                            increaseClick = {
                                viewModel.onEvent(
                                    DetailEvent.IncreaseCartProduct(
                                        state.product ?: return@ProductCounter
                                    )
                                )
                            }
                        )
                    }else{
                        CustomButton(
                            title = stringResource(id = R.string.add_to_card),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.onEvent(
                                    DetailEvent.AddProductToCart(
                                        state.product ?: return@CustomButton
                                    )
                                )
                            }
                        )
                    }
                }
            }
            if (state.message.isNotBlank()) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.onEvent(DetailEvent.RestoreState)
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDetailScreen() {
    val navController = rememberNavController()
    DetailScreen(
        navController = navController,
    )
}