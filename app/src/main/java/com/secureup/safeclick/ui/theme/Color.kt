package com.secureup.safeclick.ui.theme

import androidx.compose.ui.graphics.Color

// Colores tema claro (extraídos exactamente de _referencia/src/index.css)
val Primary = Color(0xFFA734A5) // Títulos, botones, logo, ícono activo barra inferior

val TextPrimary = Color(0xFF172B43) // Texto principal
val TextSecondary = Color(0xFF5C6D80) // Texto secundario

val Border = Color(0xFFDCE3ED) // Bordes
val SurfaceSoft = Color(0xFFE8EFF8) // Superficie suave
val Background = Color(0xFFFFFFFF) // Fondo

// Colores por nivel de análisis (exactos según referencia)
object NivelColors {
    val peligrosoText = Color(0xFFD93025)
    val peligrosoBorder = Color(0xFFF44336)
    val peligrosoBackground = Color(0xFFFFF0EF)

    val sospechosoText = Color(0xFF7B4B00)
    val sospechosoBorder = Color(0xFFF59E0B)
    val sospechosoBackground = Color(0xFFFFF6DF)

    val sinSenalesText = Color(0xFF466921)
    val sinSenalesBorder = Color(0xFF7CB342)
    val sinSenalesBackground = Color(0xFFEFF5E3)
}