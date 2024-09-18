package com.example.mymarket.presentation.screens.mainscreen.views

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mymarket.presentation.navigation.BottomBarScreen
import com.example.mymarket.presentation.navigation.BottomNavGraph
import com.example.mymarket.presentation.screens.mainscreen.MainScreenEvent
import com.example.mymarket.presentation.screens.mainscreen.MainScreenViewModel
import okhttp3.internal.wait


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(MainScreenEvent.ObserveCartCount)
    }
    Scaffold(
        bottomBar = { BottomBar(navController = navController, cartCount = state.cartCount) }
    ) {
        BottomNavGraph(
            modifier = Modifier.padding(it),
            navController = navController)
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    cartCount : Int = 0
    ) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Basket,
        BottomBarScreen.Favorite,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
                itemCount = cartCount
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    itemCount: Int = 0
) {
    BottomNavigationItem(
        icon = {
            if (screen == BottomBarScreen.Basket && itemCount > 0) {
                BadgedBox(
                    badge = {
                        Badge(
                            modifier = Modifier.size(18.dp).width(12.dp),
                            contentColor = Color.White) { Text(text = itemCount.toString(), style = TextStyle(Color.White, textAlign = TextAlign.Center)) }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = screen.iconRes),
                        contentDescription = "Basket Icon"
                    )
                }
            } else {
            Icon(
                painter = painterResource(id = screen.iconRes),
                contentDescription = "Navigation Icon"
            )
        }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}