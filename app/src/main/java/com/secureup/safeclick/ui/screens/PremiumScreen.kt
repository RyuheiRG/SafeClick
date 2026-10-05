package com.secureup.safeclick.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.secureup.safeclick.ui.theme.Background
import com.secureup.safeclick.ui.theme.Primary
import com.secureup.safeclick.ui.theme.SurfaceSoft
import com.secureup.safeclick.ui.theme.TextPrimary
import com.secureup.safeclick.ui.theme.TextSecondary

@Composable
fun PremiumScreen(
    esPremium: Boolean,
    llegoPorLimite: Boolean,
    onSuscribirse: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (llegoPorLimite && !esPremium) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = com.secureup.safeclick.ui.theme.NivelColors.sospechosoBackground
            ) {
                Text(
                    text = "Llegaste a tu límite de hoy. Con Premium analizas todos los links que quieras.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = com.secureup.safeclick.ui.theme.NivelColors.sospechosoText
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier
                .height(64.dp)
                .width(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SafeClick Premium",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Primary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$29 MXN / mes",
            style = MaterialTheme.typography.titleLarge.copy(
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Background
            ),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                BeneficioItem(text = "Análisis ilimitados")
                BeneficioItem(text = "Historial completo")
                BeneficioItem(text = "Explicaciones detalladas")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (esPremium) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = com.secureup.safeclick.ui.theme.NivelColors.sinSenalesBackground
            ) {
                Text(
                    text = "Ya eres Premium",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = com.secureup.safeclick.ui.theme.NivelColors.sinSenalesText,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        } else {
            Button(
                onClick = onSuscribirse,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Background
                )
            ) {
                Text(
                    text = "Suscribirme",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Plan para escuelas disponible: $500 MXN al mes",
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextSecondary
            )
        )
    }
}

@Composable
fun BeneficioItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
            shape = RoundedCornerShape(12.dp),
            color = SurfaceSoft
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextPrimary
            )
        )
    }
}