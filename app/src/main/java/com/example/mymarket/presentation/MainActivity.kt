package com.example.mymarket.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymarket.presentation.screens.mainscreen.MainScreen
import com.example.mymarket.presentation.ui.theme.MyMarketTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMarketTheme {
               MainScreen()
            }
        }
    }
}