package com.example.custom_ui_component_for_internet_usage_in_jetpack_compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomComponent(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backGroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    backGroundIndicatorWidth: Float = 100f,
    forGroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    forGroundIndicatorWidth: Float = 100f
) {
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue){
        indicatorValue
    }else{
        maxIndicatorValue
    }
    val animatedIndicatorValue = remember {
        Animatable(
            initialValue = 0f
        )
    }
    LaunchedEffect(key1 = allowedIndicatorValue, block ={
        animatedIndicatorValue.animateTo(allowedIndicatorValue.toFloat())
    })

    val percentage = (animatedIndicatorValue.value / maxIndicatorValue) * 100
    val sweepAngle by animateFloatAsState(targetValue = (2.4f * percentage.toFloat()),
        animationSpec = tween(1000)
    )


    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                backGroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backGroundIndicatorColor,
                    indicatorStrokeWidth = backGroundIndicatorWidth,

                    )
                forGroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = forGroundIndicatorColor,
                    indicatorStrokeWidth = forGroundIndicatorWidth,
                )
            }
    ) {

    }

}

fun DrawScope.backGroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f,
        )
    )

}

fun DrawScope.forGroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
//        color = indicatorColor,
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF39F3BB),
                Color(0xFF3DD0AD),
                Color(0xFF2B9992),
                Color(0xFF1C6179),
                Color(0xFF00354C),

//                Color(0xFFE53935), // Red
//                Color(0xFFF57C00), // Yellow
//                Color(0xFF4CAF50), // Green
//                Color(0xFF2196F3)
            ),
//            start = Offset(0.8f,0.6f),
//            end = Offset(0.2f, 1.2f)
        ),

        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f,
        )
    )

}

@Preview(showBackground = true)
@Composable
fun CustomComponentPreview() {
    CustomComponent()
}