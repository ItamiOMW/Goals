package com.example.goals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.goals.ui.theme.GoalsTheme
import com.example.goals.ui.theme.GrayShade

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = GrayShade
                ) {
                    Text(text = "SMTH")
                }
            }
        }
    }
}
