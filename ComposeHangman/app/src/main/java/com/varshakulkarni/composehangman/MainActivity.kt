package com.varshakulkarni.composehangman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.varshakulkarni.composehangman.navigation.HangmanNavigation
import com.varshakulkarni.composehangman.ui.theme.ComposeHangmanTheme
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : ComponentActivity() {
    @InternalCoroutinesApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHangmanTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HangmanNavigation()
                }
            }
        }
    }
}
