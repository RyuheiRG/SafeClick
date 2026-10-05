package com.secureup.safeclick.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.secureup.safeclick.analisis.AnalizadorLink
import com.secureup.safeclick.analisis.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AnalisisItem(
    val url: String,
    val resultado: Resultado,
    val timestamp: Long
)

data class AnalisisUiState(
    val urlInput: String = "",
    val resultado: Resultado? = null,
    val historial: List<AnalisisItem> = emptyList(),
    val analisisRestantes: Int = 3,
    val esPremium: Boolean = false,
    val isLoading: Boolean = false,
    val llegoPorLimite: Boolean = false,
    val signedIn: Boolean = false,
    val accountEmail: String = "",
    val isGuest: Boolean = true
)

class AnalisisViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AnalisisUiState())
    val uiState: StateFlow<AnalisisUiState> = _uiState.asStateFlow()

    fun onUrlInputChange(newValue: String) {
        _uiState.update { it.copy(urlInput = newValue) }
    }

    fun analizar() {
        val current = _uiState.value
        val url = current.urlInput.trim()
        if (url.isEmpty()) return

        // Al intentar un análisis con 0 restantes y sin Premium -> debe navegar a Premium
        if (!current.esPremium && current.analisisRestantes <= 0) {
            // Marcamos flag? pero navegación lo maneja UI; devolvemos evento? simple: no analizamos
            return
        }

        val resultado = AnalizadorLink.analizar(url)
        val nuevoItem = AnalisisItem(
            url = url,
            resultado = resultado,
            timestamp = System.currentTimeMillis()
        )

        _uiState.update {
            it.copy(
                resultado = resultado,
                historial = listOf(nuevoItem) + it.historial,
                analisisRestantes = if (!it.esPremium) {
                    maxOf(0, it.analisisRestantes - 1)
                } else {
                    it.analisisRestantes
                }
            )
        }
    }

    fun setResultado(resultado: Resultado?) {
        _uiState.update { it.copy(resultado = resultado) }
    }

    fun activarPremium() {
        _uiState.update { it.copy(esPremium = true, analisisRestantes = 3, llegoPorLimite = false) }
    }

    fun consumirAnalisisSiNecesario(): Boolean {
        // Devuelve true si puede analizar, false si necesita ir a Premium
        val current = _uiState.value
        if (current.esPremium) {
            return true
        }
        if (current.analisisRestantes <= 0) {
            _uiState.update { it.copy(llegoPorLimite = true) }
            return false
        }
        return true
    }

    fun clearResultado() {
        _uiState.update { it.copy(resultado = null) }
    }

    fun signIn(email: String) {
        _uiState.update { it.copy(signedIn = true, accountEmail = email, isGuest = false) }
    }

    fun signOut() {
        _uiState.update { it.copy(signedIn = false, accountEmail = "", isGuest = true, esPremium = false) }
    }

    fun setGuest(value: Boolean) {
        _uiState.update { it.copy(isGuest = value, signedIn = false) }
    }
}