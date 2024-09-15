package com.example.mymarket.presentation.screens.basket.views

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mymarket.R
import com.example.mymarket.domain.model.Product
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.DefaultNavigationBar
import com.example.mymarket.presentation.components.ProductCounter
import com.example.mymarket.presentation.screens.basket.BasketState
import com.example.mymarket.presentation.screens.basket.BasketViewModel
import com.example.mymarket.domain.util.StringExt.empty
import com.example.mymarket.domain.util.StringExt.formatPrice

@Composable
fun BasketScreenRoot(
    navController: NavController,
    viewModel: BasketViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    BasketScreen(navController = navController, state = state)
}


@Composable
fun BasketScreen(
    navController: NavController,
    state: BasketState = BasketState(
        product = Product(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = "822312.0",
            image = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
        )
    )
) {
    val isFavorite = remember { mutableStateOf(false) }
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
                modifier = Modifier.padding(16.dp)
            ) {
                Row {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = state.product?.name ?: String.empty,
                        )
                        Text(
                            text = stringResource(
                                id = R.string.price_with_currency,
                                state.product?.price?.formatPrice() ?: String.empty
                            ),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF2A59FE)
                            )
                        )
                    }
                    ProductCounter(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
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
                            state.product?.price?.formatPrice() ?: String.empty
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                CustomButton(
                    title = stringResource(id = R.string.complete),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    BasketScreen(
        navController = rememberNavController(),
        state = BasketState(
            product = Product(
                id = "1",
                name = "Product 1",
                description = "Description 1",
                price = "822312.0",
                image = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
            )
        )
    )
}