package com.v2ray.ang.ui.main

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeConnectionCard(
    isRunning: Boolean,
    isConnecting: Boolean,
    isTesting: Boolean,
    displayText: String,
    onConnectClick: () -> Unit,
    onTestClick: () -> Unit
) {
    val pulse = rememberInfiniteTransition(label = "connection-pulse")
    val pulseScale by pulse.animateFloat(
        initialValue = 1f,
        targetValue = 1.055f,
        animationSpec = infiniteRepeatable(
            animation = tween(850),
            repeatMode = RepeatMode.Reverse
        ),
        label = "connection-pulse-scale"
    )
    val accent = MaterialTheme.colorScheme.primary
    val buttonColor = if (isRunning) Color(0xFF21C77A) else accent
    val iconColor = if (isRunning) Color.White else MaterialTheme.colorScheme.onPrimary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF080716))
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CosmicBackdrop(modifier = Modifier.matchParentSize())
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 26.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .graphicsLayerCompat(if (isConnecting) pulseScale else 1f)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    buttonColor.copy(alpha = 0.95f),
                                    buttonColor.copy(alpha = 0.60f),
                                    Color(0xFF120D2B)
                                )
                            )
                        )
                        .clickable(enabled = !isConnecting) { onConnectClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = buttonColor.copy(alpha = 0.42f),
                            radius = size.minDimension * 0.49f,
                            style = Stroke(width = 5.dp.toPx())
                        )
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(Color.Transparent, buttonColor.copy(alpha = 0.38f))
                            ),
                            radius = size.minDimension * 0.47f
                        )
                    }
                    if (isConnecting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(76.dp),
                            color = iconColor,
                            strokeWidth = 5.dp
                        )
                    } else {
                        PowerPawIcon(
                            modifier = Modifier.size(82.dp),
                            color = iconColor
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = when {
                        isConnecting -> "Подключаемся…"
                        isRunning -> "VPN подключён"
                        else -> "VPN отключён"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = when {
                        isConnecting -> "Устанавливаем защищённое соединение"
                        isRunning -> "Защищённое соединение активно"
                        else -> "Нажмите на кнопку, чтобы подключиться"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD1C8F2)
                )
                Spacer(Modifier.height(18.dp))
                OutlinedButton(
                    onClick = onTestClick,
                    enabled = isRunning && !isTesting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Text(if (isTesting) "Проверяем соединение…" else "Проверить соединение")
                }
                if (displayText.isNotBlank()) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFFD1C8F2)
                    )
                }
            }
        }
    }
}

@Composable
private fun CosmicBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF080716), Color(0xFF17103A), Color(0xFF080716))
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x664F2BA8), Color(0x22402080), Color.Transparent),
                center = Offset(size.width * 0.78f, size.height * 0.28f),
                radius = size.width * 0.72f
            ),
            radius = size.width * 0.72f,
            center = Offset(size.width * 0.78f, size.height * 0.28f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x554B2C9B), Color.Transparent),
                center = Offset(size.width * 0.14f, size.height * 0.72f),
                radius = size.width * 0.58f
            ),
            radius = size.width * 0.58f,
            center = Offset(size.width * 0.14f, size.height * 0.72f)
        )
        val stars = listOf(
            0.08f to 0.10f, 0.19f to 0.23f, 0.33f to 0.08f, 0.46f to 0.18f,
            0.61f to 0.07f, 0.76f to 0.16f, 0.91f to 0.09f, 0.12f to 0.43f,
            0.28f to 0.57f, 0.42f to 0.39f, 0.57f to 0.52f, 0.73f to 0.42f,
            0.88f to 0.59f, 0.07f to 0.78f, 0.25f to 0.88f, 0.48f to 0.77f,
            0.69f to 0.90f, 0.86f to 0.82f, 0.95f to 0.34f
        )
        stars.forEachIndexed { index, point ->
            val radius = if (index % 4 == 0) 2.2f else 1.25f
            drawCircle(
                color = Color(0xFFDCD3FF).copy(alpha = if (index % 3 == 0) 0.95f else 0.62f),
                radius = radius.dp.toPx(),
                center = Offset(size.width * point.first, size.height * point.second)
            )
        }
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x553B5BBF), Color(0x223B5BBF), Color.Transparent)
            ),
            radius = size.width * 0.19f,
            center = Offset(size.width * 0.86f, size.height * 0.14f)
        )
    }
}

@Composable
private fun PowerPawIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val paw = color
        // Four toe pads and the broad central pad form a clean, recognizable paw mark.
        drawOval(paw, topLeft = Offset(w * 0.12f, h * 0.27f), size = Size(w * 0.19f, h * 0.27f))
        drawOval(paw, topLeft = Offset(w * 0.32f, h * 0.08f), size = Size(w * 0.18f, h * 0.28f))
        drawOval(paw, topLeft = Offset(w * 0.53f, h * 0.08f), size = Size(w * 0.18f, h * 0.28f))
        drawOval(paw, topLeft = Offset(w * 0.72f, h * 0.27f), size = Size(w * 0.17f, h * 0.27f))
        drawOval(paw, topLeft = Offset(w * 0.22f, h * 0.43f), size = Size(w * 0.57f, h * 0.43f))
    }
}

private fun Modifier.graphicsLayerCompat(scale: Float): Modifier =
    this.then(Modifier.graphicsLayer {
        scaleX = scale
        scaleY = scale
    })
