package com.example.mymarket.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymarket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String? = null,
    titleView: (@Composable () -> Unit)? = null,
    backButtonHidden: Boolean = false,
    backButtonAction: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    CenterAlignedTopAppBar(
        modifier = modifier
            .shadow(elevation = 4.dp),
        title = {
            titleView?.invoke() ?: Text(
                modifier = Modifier,
                text = title ?: "",
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            if (!backButtonHidden) {
                Icon(
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        backButtonAction?.invoke() ?: navController.popBackStack()
                    },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                )
            } else {
                navigationIcon.invoke()
            }

        },
        actions = actions ?: {},
        windowInsets = WindowInsets(top = 0), colors = TopAppBarColors(
            containerColor = Color(0xFF2A59FE),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = Color.White,
            actionIconContentColor = Color.White,
        )
    )
}