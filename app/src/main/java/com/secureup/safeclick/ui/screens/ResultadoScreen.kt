package com.secureup.safeclick.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.secureup.safeclick.analisis.Resultado
import com.secureup.safeclick.ui.theme.Background
import com.secureup.safeclick.ui.theme.Border
import com.secureup.safeclick.ui.theme.NivelColors
import com.secureup.safeclick.ui.theme.Primary
import com.secureup.safeclick.ui.theme.TextPrimary
import com.secureup.safeclick.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadoScreen(
    resultado: Resultado?,
    url: String,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resultado",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },
        containerColor = Background
    ) { paddingValues ->
        resultado?.let { res ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Tarjeta de veredicto
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (res.nivel) {
                            "PELIGROSO" -> NivelColors.peligrosoBackground
                            "SOSPECHOSO" -> NivelColors.sospechosoBackground
                            else -> NivelColors.sinSenalesBackground
                        }
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (res.nivel) {
                            "PELIGROSO" -> NivelColors.peligrosoBorder
                            "SOSPECHOSO" -> NivelColors.sospechosoBorder
                            else -> NivelColors.sinSenalesBorder
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (res.nivel) {
                                    "PELIGROSO" -> Icons.Default.Error
                                    "SOSPECHOSO" -> Icons.Default.Warning
                                    else -> Icons.Default.CheckCircle
                                },
                                contentDescription = null,
                                tint = when (res.nivel) {
                                    "PELIGROSO" -> NivelColors.peligrosoText
                                    "SOSPECHOSO" -> NivelColors.sospechosoText
                                    else -> NivelColors.sinSenalesText
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = res.nivel,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = when (res.nivel) {
                                        "PELIGROSO" -> NivelColors.peligrosoText
                                        "SOSPECHOSO" -> NivelColors.sospechosoText
                                        else -> NivelColors.sinSenalesText
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = url,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Razones
                Text(
                    text = "Por qué",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))

                res.razones.forEach { razon ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = razon,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Border),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Primary
                        )
                    ) {
                        Text("Volver", fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = { /* compartir no implementado en Fase 1 */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Background
                        )
                    ) {
                        Text("Compartir", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}