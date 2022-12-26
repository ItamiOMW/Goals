package com.example.goals.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.goals.ui.theme.GoalsTheme
import com.example.goals.ui.theme.GrayShadeDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = GrayShadeDark
                ) {
                    Text(text = "SMTH")
                }
            }
        }
    }
}
