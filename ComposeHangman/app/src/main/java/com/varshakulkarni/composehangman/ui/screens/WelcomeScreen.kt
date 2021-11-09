package com.varshakulkarni.composehangman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.varshakulkarni.composehangman.R
import com.varshakulkarni.composehangman.ui.Route

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to ${stringResource(R.string.app_name)}",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(36.dp),
            color = MaterialTheme.colors.onSecondary,
        )
        Button(onClick = { startGame(navController) }) {
            Text("Play")
        }
    }
}

fun startGame(navController: NavHostController) {
    navController.navigate(Route.Game.route)
}
