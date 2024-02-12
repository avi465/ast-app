package com.ast.app.presentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ast.app.ui.theme.AdvancedStudyTutorialsTheme

const val numberOfDots = 7
val dotSize = 10.dp
val dotColor: Color = Color.Blue
const val delayUnit = 200
const val duration = numberOfDots * delayUnit
val spaceBetween = 2.dp
@Composable
fun DotsTyping() {
    val maxOffset = (numberOfDots * 2).toFloat()

    @Composable
    fun Dot(offset: Float) {
        Spacer(
            Modifier
                .size(dotSize)
                .offset(y = -offset.dp)
                .background(
                    color = dotColor,
                    shape = CircleShape
                )
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = duration
            0f at delay with LinearEasing
            maxOffset at delay + delayUnit with LinearEasing
            0f at delay + (duration/2)
        }), label = ""
    )

    val offsets = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        offsets.add(animateOffsetWithDelay(delay = i * delayUnit))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        offsets.forEach {
            Dot(it.value)
            Spacer(Modifier.width(spaceBetween))
        }
    }
}

@Preview()
@Composable
fun LoaderPreview(){
    AdvancedStudyTutorialsTheme{
        DotsTyping()
    }
}