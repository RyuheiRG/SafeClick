package com.secureup.safeclick.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Background,
    background = Background,
    onBackground = TextPrimary,
    surface = Background,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = Border,
    outlineVariant = Border,
    surfaceContainer = SurfaceSoft
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp), // Esquinas de 16 dp
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun SafeClickTheme(
    darkTheme: Boolean = false, // Solo esquema claro
    content: @Composable () -> Unit
) {
    // Forzamos tema claro según requerimiento
    MaterialTheme(
        colorScheme = LightColorScheme,
        shapes = AppShapes,
        content = content
    )
}