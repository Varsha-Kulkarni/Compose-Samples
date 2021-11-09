package com.varshakulkarni.composehangman.ui

sealed class Route(val route: String, val name: String) {
    object Welcome : Route("welcome", "Welcome")
    object Game : Route("game", "Game")
}
