package com.example.mymarket.presentation.screens.basket.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mymarket.R
import com.example.mymarket.core.util.StringExt.empty
import com.example.mymarket.core.util.StringExt.formatPrice
import com.example.mymarket.presentation.components.BasketItem
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.DefaultNavigationBar
import com.example.mymarket.presentation.screens.basket.BasketEvent
import com.example.mymarket.presentation.screens.basket.BasketViewModel

@Composable
fun BasketScreen(
    navController: NavController,
    viewModel: BasketViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(BasketEvent.GetCartProducts)
    }
    val completeButton : @Composable RowScope.() -> Unit = {
        if (state.myCartProducts.isNotEmpty()) {
            CustomButton(
                title = stringResource(id = R.string.complete),
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.onEvent(BasketEvent.CompleteOrder)
                }
            )
        }
    }
    Scaffold(
        topBar = {
            DefaultNavigationBar(
                navController = navController,
                title = stringResource(id = R.string.e_market),
                backButtonHidden = true,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(state.myCartProducts.size) { index ->
                        val product = state.myCartProducts[index]
                        BasketItem(
                            product = product,
                            onDecreaseClick = {
                                viewModel.onEvent(BasketEvent.DecreaseCartProduct(product))
                            },
                            onIncreaseClick = {
                                viewModel.onEvent(BasketEvent.IncreaseCartProduct(product))
                            }
                        )
                        Spacer(modifier = Modifier.height(23.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.total),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color(0xFF2A59FE)
                            )
                        )
                        Text(
                            text = stringResource(
                                id = R.string.price_with_currency,
                                state.totalPrice.toString().formatPrice() ?: String.empty
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    completeButton()
                }
            }
        }
    }

}

@Preview
@Composable
private fun PreviewScreen() {
    BasketScreen(
        navController = rememberNavController()
    )
}