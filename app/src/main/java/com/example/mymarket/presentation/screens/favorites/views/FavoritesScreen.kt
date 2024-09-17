package com.example.mymarket.presentation.screens.favorites.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymarket.R
import com.example.mymarket.presentation.components.DefaultNavigationBar
import com.example.mymarket.presentation.screens.favorites.FavoritesEvent
import com.example.mymarket.presentation.screens.favorites.FavoritesViewModel
import com.example.mymarket.presentation.screens.home.views.ProductListRow
import com.example.mymarket.presentation.util.Screen

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(FavoritesEvent.GetFavoriteProducts)
    }
    Scaffold(
        topBar = {
            DefaultNavigationBar(
                navController = navController,
                title = stringResource(id = R.string.my_favorites),
                backButtonHidden = false,
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            if (state.favoriteProducts.isEmpty()){
                Text(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    text = stringResource(id = R.string.no_favorite_product),
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 32.sp
                    )
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        items(state.favoriteProducts) { product ->
                            ProductListRow(
                                modifier = Modifier.padding(4.dp),
                                product = product,
                                addableProduct = false,
                                onFavoriteClick = {
                                   if (it.isFavorite) {
                                        viewModel.onEvent(
                                            FavoritesEvent.DeleteFavorite(it)
                                        )
                                    } else {
                                        viewModel.onEvent(
                                            FavoritesEvent.InsertFavorite(it)
                                        )
                                    }
                                },
                                onItemClick = {
                                    navController.navigate("${Screen.DetailScreen.route}/${it.id}")
                                },
                            )
                        }
                    }
                )
            }
        }
    }
}
