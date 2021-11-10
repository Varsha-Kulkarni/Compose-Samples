package com.varshakulkarni.composehangman.ui

sealed class Route(val route: String, val name: String) {
    object Welcome : Route("welcome", "Welcome")
    object Game : Route("game", "Game")
    object Score: Route("score/finalScore", "Score"){
        fun route(finalScore: Int) = "score/$finalScore"

        const val FINAL_SCORE : String = "finalScore"
    }
}
