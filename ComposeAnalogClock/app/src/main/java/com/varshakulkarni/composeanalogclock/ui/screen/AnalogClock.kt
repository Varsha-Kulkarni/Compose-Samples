package com.varshakulkarni.composeanalogclock.ui.screen

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClock() {

    var time by remember { mutableStateOf(Calendar.getInstance()) }

    val seconds = time.get(Calendar.SECOND)
    val minutes = time.get(Calendar.MINUTE)
    val hours = time.get(Calendar.HOUR)

    LaunchedEffect("Clock") {
        while (isActive) {
            delay(1000)
            time = Calendar.getInstance()
        }
    }

    Canvas(modifier = Modifier.fillMaxSize(),
        onDraw = {

            val padding = 50f
            val min = size.width.coerceAtMost(size.height)
            val width = size.width / 2
            val height = size.height / 2
            val radius = min / 2 - padding
            val handTruncation = min / 20
            val hourHandTruncation = min / 7
            val degree06 = Math.PI / 30f
            val degree30 = degree06 * 5f
            val degree90 = degree30 * 3f

            drawCircle(
                Color.Black, radius + padding - 10,
                Offset(width, height),
                style = Stroke(
                    width = 8f,
                ),
            )
            drawCircle(
                Color.Black, 10f,
                Offset(width, height),
                style = Stroke(
                    width = 8f,
                ),
            )

            val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            val rect = Rect()

            //Paint().asFrameworkPaint().apply {} can also be used from
            //androidx.compose.ui.graphics.Paint
            //Need to explore what is the difference
            val paint = Paint()

            for (number in numbers) {
                val angle = degree30 * (number - 3f)
                val tempString = number.toString()

                paint.getTextBounds(tempString, 0, tempString.length, rect)
                paint.textSize = 48f
                paint.color = 0xff000000.toInt()
                val x =
                    (width + cos(angle) * radius - rect.width() / 2).toFloat()
                val y =
                    (height + sin(angle) * radius + rect.height() / 2).toFloat()

                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        tempString,
                        x,
                        y,
                        paint
                    )
                }
            }

            val secondsAngle = degree90 - degree06 * seconds

            val secondX = width + (cos(secondsAngle) * radius).toFloat()
            val secondY = height - (sin(secondsAngle) * radius).toFloat()

            drawLine(
                color = Color.Red,
                start = Offset(width, height),
                end = Offset(
                    x = secondX,
                    y = secondY
                ),
                strokeWidth = 4f,
            )

            val minuteSeconds = (minutes + seconds / 60f)

            val minutesAngle =
                degree90 - degree06 * minuteSeconds

            val minutesX = width + (cos(minutesAngle) * (radius - padding)).toFloat()
            val minutesY = height - (sin(minutesAngle) * (radius - padding)).toFloat()

            drawLine(
                color = Color.Black,
                start = Offset(width, height),
                end = Offset(
                    x = minutesX,
                    y = minutesY
                ),
                strokeWidth = 6f,
            )

            val hoursAngle =
                degree90 - degree30 * (hours + minuteSeconds / 60f)

            val hourX =
                width + (cos(hoursAngle) * (radius - handTruncation - hourHandTruncation)).toFloat()
            val hourY =
                height - (sin(hoursAngle) * (radius - handTruncation - hourHandTruncation)).toFloat()

            drawLine(
                color = Color.Black,
                start = Offset(width, height),
                end = Offset(
                    x = hourX,
                    y = hourY
                ),
                strokeWidth = 10f,
            )
        })

}
