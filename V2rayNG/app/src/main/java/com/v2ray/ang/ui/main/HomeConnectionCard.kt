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
import androidx.compose.ui.graphics.graphicsLayer
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
    val buttonColor = if (isRunning) MaterialTheme.colorScheme.error else accent
    val buttonTextColor = if (isRunning) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .graphicsLayer {
                        val scale = if (isConnecting) pulseScale else 1f
                        scaleX = scale
                        scaleY = scale
                    }
                    .clip(CircleShape)
                    .background(buttonColor)
                    .clickable(enabled = !isConnecting) {
                        onConnectClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isConnecting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(76.dp),
                        color = buttonTextColor,
                        strokeWidth = 5.dp
                    )
                } else {
                    Canvas(modifier = Modifier.size(76.dp)) {
                        val stroke = size.minDimension * 0.075f
                        drawArc(color = buttonTextColor, startAngle = 135f, sweepAngle = 270f, useCenter = false, topLeft = androidx.compose.ui.geometry.Offset(size.width * 0.13f, size.height * 0.13f), size = androidx.compose.ui.geometry.Size(size.width * 0.74f, size.height * 0.74f), style = Stroke(width = stroke, cap = StrokeCap.Round))
                        drawLine(color = buttonTextColor, start = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.08f), end = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.51f), strokeWidth = stroke, cap = StrokeCap.Round)
                    }

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
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = when {
                    isConnecting -> "Устанавливаем защищённое соединение"
                    isRunning -> "Защищённое соединение активно"
                    else -> "Нажмите на кнопку, чтобы подключиться"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
