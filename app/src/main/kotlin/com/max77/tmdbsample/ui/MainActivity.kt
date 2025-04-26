package com.max77.tmdbsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.max77.tmdbsample.ui.screens.RootScreen
import com.max77.tmdbsample.ui.theme.SkeletonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SkeletonTheme {
                Scaffold(
                    modifier = Modifier.systemBarsPadding()
                ) { innerPadding ->
                    RootScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}