package com.secureup.safeclick.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.secureup.safeclick.ui.theme.Background
import com.secureup.safeclick.ui.theme.Border
import com.secureup.safeclick.ui.theme.Primary
import com.secureup.safeclick.ui.theme.SurfaceSoft
import com.secureup.safeclick.ui.theme.TextPrimary
import com.secureup.safeclick.ui.theme.TextSecondary
import com.secureup.safeclick.ui.viewmodel.AnalisisViewModel

@Composable
fun AnalizarScreen(
    viewModel: AnalisisViewModel,
    onAnalizarClick: () -> Unit,
    onIrAPremium: () -> Unit,
    onLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Analizar enlace",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            )
            androidx.compose.material3.TextButton(onClick = onLogin) {
                Text(
                    text = if (uiState.signedIn) "Mi cuenta" else "Ingresar",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Revisa si un enlace es una estafa. En español simple.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextSecondary
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.urlInput,
            onValueChange = { viewModel.onUrlInputChange(it) },
            placeholder = { Text("Pega aquí la URL o enlace") },
            modifier = Modifier
                .fillMaxWidth()
                .height(116.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Border,
                cursorColor = Primary,
                focusedContainerColor = Background,
                unfocusedContainerColor = Background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (viewModel.consumirAnalisisSiNecesario()) {
                    viewModel.analizar()
                    onAnalizarClick()
                } else {
                    onIrAPremium()
                }
            },
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
                text = "Analizar",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (uiState.esPremium) "Análisis ilimitados" else "Te quedan ${uiState.analisisRestantes} análisis gratis hoy",
            style = MaterialTheme.typography.bodySmall.copy(
                color = TextSecondary
            )
        )
    }
}