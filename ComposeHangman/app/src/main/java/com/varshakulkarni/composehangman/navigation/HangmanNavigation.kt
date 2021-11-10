package com.varshakulkarni.composehangman.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.varshakulkarni.composehangman.ui.Route
import com.varshakulkarni.composehangman.ui.screens.GameScreen
import com.varshakulkarni.composehangman.ui.screens.ScoreScreen
import com.varshakulkarni.composehangman.ui.screens.WelcomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

const val NAV_HOST_ROUTE = "nav-host-route"

@ExperimentalAnimationApi
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@Composable
fun HangmanNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Route.Welcome.route, route = NAV_HOST_ROUTE) {

        composable(Route.Welcome.route){
            WelcomeScreen(navController = navController)
        }
        composable(
            Route.Game.route
        ) {
            GameScreen(navController)
        }
        composable(
            Route.Score.route,
            arguments = listOf(
                navArgument(Route.Score.FINAL_SCORE) { type = NavType.IntType }
            )
        ) {
            val finalScore = requireNotNull(it.arguments?.getString(Route.Score.FINAL_SCORE))
            ScoreScreen(navController, finalScore)
        }
    }
}

