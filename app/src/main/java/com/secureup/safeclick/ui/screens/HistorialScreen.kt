package com.secureup.safeclick.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.secureup.safeclick.ui.theme.Background
import com.secureup.safeclick.ui.theme.Border
import com.secureup.safeclick.ui.theme.NivelColors
import com.secureup.safeclick.ui.theme.Primary
import com.secureup.safeclick.ui.theme.TextPrimary
import com.secureup.safeclick.ui.theme.TextSecondary
import com.secureup.safeclick.ui.viewmodel.AnalisisItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialScreen(
    historial: List<AnalisisItem>,
    onItemClick: (AnalisisItem) -> Unit
) {
    val dateFormat = SimpleDateFormat("HH:mm dd/MM/yy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Tus análisis recientes",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Primary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Consulta lo que revisaste, cuando quieras.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextSecondary
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (historial.isEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = Background,
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Aún no has analizado ningún link",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextSecondary
                        )
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(historial) { item ->
                    HistorialItem(
                        item = item,
                        timeText = dateFormat.format(Date(item.timestamp)),
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun HistorialItem(
    item: AnalisisItem,
    timeText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Background
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (item.resultado.nivel) {
                    "PELIGROSO" -> Icons.Default.Error
                    "SOSPECHOSO" -> Icons.Default.Warning
                    else -> Icons.Default.CheckCircle
                },
                contentDescription = null,
                tint = when (item.resultado.nivel) {
                    "PELIGROSO" -> NivelColors.peligrosoText
                    "SOSPECHOSO" -> NivelColors.sospechosoText
                    else -> NivelColors.sinSenalesText
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.url,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timeText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondary
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            NivelEtiqueta(nivel = item.resultado.nivel)
        }
    }
}

@Composable
fun NivelEtiqueta(nivel: String) {
    val (bgColor, textColor) = when (nivel) {
        "PELIGROSO" -> NivelColors.peligrosoBackground to NivelColors.peligrosoText
        "SOSPECHOSO" -> NivelColors.sospechosoBackground to NivelColors.sospechosoText
        else -> NivelColors.sinSenalesBackground to NivelColors.sinSenalesText
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bgColor
    ) {
        Text(
            text = when (nivel) {
                "PELIGROSO" -> "PELIGROSO"
                "SOSPECHOSO" -> "SOSPECHOSO"
                else -> "SIN SEÑALES DE PELIGRO"
            },
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}