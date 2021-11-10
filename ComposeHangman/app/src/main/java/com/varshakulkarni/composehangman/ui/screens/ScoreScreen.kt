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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.varshakulkarni.composehangman.navigation.NAV_HOST_ROUTE
import com.varshakulkarni.composehangman.ui.Route

@Composable
fun ScoreScreen(navController: NavHostController, finalScore: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Final Score is $finalScore",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(36.dp),
            color = MaterialTheme.colors.onSecondary,
        )

        Button(onClick = { startGame(navController) }) {
            Text("Play again?")
        }

        Button(onClick = { exitGame(navController) }) {
            Text("Exit")
        }
    }
}
fun exitGame(navController: NavHostController) {
    navController.navigate(Route.Welcome.route,
        builder = {popUpTo(NAV_HOST_ROUTE)
        launchSingleTop = true})
}