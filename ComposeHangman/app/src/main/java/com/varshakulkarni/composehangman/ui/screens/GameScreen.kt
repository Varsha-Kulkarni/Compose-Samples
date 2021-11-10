package com.varshakulkarni.composehangman.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.varshakulkarni.composehangman.R
import com.varshakulkarni.composehangman.ui.DrawHangman
import com.varshakulkarni.composehangman.ui.Route

enum class GameState {
    GameInit,
    Running,
    Pause,
    GameOver
}

private fun refactorHiddenString(gameString: String, correctGuesses: MutableList<String>): String {
    var hiddenWord = ""
    gameString.forEach { s ->
        if (s == ' ' || s == ':' || s == '-' || s == '.' || s.toString().compareTo("'") == 0)
            correctGuesses.add(s.toString())

        hiddenWord += (checkIfGuessed(s.toString(), correctGuesses))
    }
    return hiddenWord
}

private fun checkIfGuessed(s: String, correctGuesses: List<String>): String {
    return when (correctGuesses.contains(s)) {
        true -> s
        false -> "-"
    }
}

@Composable
fun GameScreen(navController: NavHostController) {

    val gameString = stringArrayResource(id = R.array.movie_titles).random().toLowerCase()

    val alphabets = listOf(
        listOf("a", "b", "c", "d", "e", "f"), listOf("g", "h", "i", "j", "k", "l"),
        listOf("m", "n", "o", "p", "q", "r"), listOf("s", "t", "u", "v", "w", "x"),
        listOf("y", "z", "1", "2", "3", "4"), listOf("5", "6", "7", "8", "9", "0")
    )


    Column(modifier = Modifier.padding(20.dp)) {
        HangmanGame(gameString = gameString, alphabets, navController)
    }
}

@Composable
fun DrawGameText(hiddenWord: String) {
    Text(text = hiddenWord, letterSpacing = 4.sp, fontSize = 24.sp)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HangmanGame(
    gameString: String,
    alphabets: List<List<String>>,
    navController: NavHostController
) {

    val correctGuesses = remember { mutableStateListOf<String>() }
    var guessed by remember { mutableStateOf("") }
    var gameScore by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(6) }
    var gameState by remember { mutableStateOf(GameState.GameInit) }
    val visibleState = remember { mutableStateOf(false) }
    val buttonMap by remember { mutableStateOf(HashMap<String, Boolean>()) }
    var hiddenWord by remember { mutableStateOf(gameString) }

    if (gameState != GameState.GameOver)
        hiddenWord = refactorHiddenString(gameString, correctGuesses = correctGuesses)

    if (gameState == GameState.GameInit) {
        for (row in alphabets) {
            for (alphabet in row) {
                buttonMap[alphabet] = true
            }
        }
    }
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,

        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DrawGameText(hiddenWord)

                Spacer(modifier = Modifier.padding(28.dp))
                Row {
                    Text("Game Score: $gameScore")
                    Spacer(modifier = Modifier.padding(28.dp))
                    Text("Lives: $lives")
                }

                Column {
                    for (row in alphabets) {
                        Row {
                            for (alphabet in row) {
                                buttonMap[alphabet]?.let {
                                    Button(
                                        onClick = {
                                            guessed = alphabet
                                            gameState = GameState.Running
                                            buttonMap[alphabet] = false
                                        },
                                        enabled = it,
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            backgroundColor = Color.White,
                                            contentColor = Color.DarkGray
                                        ),
                                    ) {
                                        Text(alphabet)
                                    }
                                }
                            }
                        }
                    }
                }
                if (gameString.contains(guessed) && gameState == GameState.Running) {

                    if (guessed.isNotEmpty() && guessed.isNotBlank())
                        gameScore += 10
                    correctGuesses.add(guessed)

                    if (gameState != GameState.GameOver)
                        hiddenWord = refactorHiddenString(gameString, correctGuesses)

                    if (!hiddenWord.contains('-')) {
                        gameState = GameState.GameOver
                        visibleState.value = true
                        navController.navigate(Route.Score.route(gameScore))

                    }
                    gameState = GameState.Pause
                } else {
                    if (lives >= 1 && gameState == GameState.Running) {
                        lives--
                        if (gameScore != 0)
                            gameScore -= 5
                        gameState = GameState.Pause
                    }
                }
                if (lives == 0) {
                    gameState = GameState.GameOver
                    visibleState.value = true
                    navController.navigate(Route.Score.route(gameScore))
                }
                DrawHangman(lives)
            }
//        }, snackbarHost = {
//            if (visibleState.value) {
//                hiddenWord = gameString
//                Snackbar(
//                    action = {
//                        Button(onClick = {
//                            navController.navigateUp()
//                        }) {
//                            Text(text = "OK")
//                        }
//                    }
//                ) {
//                    Text(text = "Game Over, score is $gameScore")
//                }

        }
    )
}