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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mymarket.domain.model.Product
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.presentation.components.DefaultNavigationBar
import com.example.mymarket.presentation.screens.detail.DetailState
import com.example.mymarket.presentation.screens.detail.DetailViewModel
import com.example.mymarket.domain.util.StringExt.empty
import com.example.mymarket.domain.util.StringExt.formatPrice
import com.example.mymarket.presentation.screens.detail.DetailEvent

@Composable
fun DetailScreenRoot(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(DetailEvent.GetDetail(productId = state.productId))
    }
    DetailScreen(navController = navController, state = state)
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    state: DetailState
) {
    val context = LocalContext.current
    val isFavorite = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            DefaultNavigationBar(
                navController = navController,
                title = state.product?.name,
                backButtonHidden = false,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(16.dp)

        ) {
            Column(
                modifier = Modifier.weight(1f)
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
                                isFavorite.value = !isFavorite.value
                            }
                            .padding(4.dp),
                        painter = if (isFavorite.value) painterResource(id = R.drawable.ic_favorites_yellow) else painterResource(
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
                            state.product?.price?.formatPrice() ?: String.empty
                        )
                    )
                }
                CustomButton(
                    title = stringResource(id = R.string.add_to_card),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (state.message.isNotBlank()) {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview
@Composable
private fun PreviewDetailScreen() {
    val navController = rememberNavController()
    DetailScreen(
        navController = navController,
        state = DetailState(
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