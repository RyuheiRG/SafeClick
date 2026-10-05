package com.secureup.safeclick

import java.net.URI

enum class Nivel { PELIGROSO, SOSPECHOSO, SIN_SENALES }

data class Resultado(
    val nivel: Nivel,
    val puntos: Int,
    val razones: List<String>
)

object AnalizadorLink {

    private val acortadores = setOf(
        "bit.ly", "tinyurl.com", "cutt.ly", "t.co", "is.gd", "rb.gy", "shorturl.at"
    )

    // palabra que aparece en el dominio -> dominios oficiales
    private val marcas = mapOf(
        "bbva" to listOf("bbva.mx", "bbva.com"),
        "banorte" to listOf("banorte.com"),
        "santander" to listOf("santander.com.mx"),
        "mercadolibre" to listOf("mercadolibre.com.mx", "mercadolibre.com"),
        "paypal" to listOf("paypal.com"),
        "netflix" to listOf("netflix.com"),
        "telcel" to listOf("telcel.com"),
        "dhl" to listOf("dhl.com"),
        "fedex" to listOf("fedex.com"),
        "estafeta" to listOf("estafeta.com")
    )

    private val palabrasUrgentes = listOf(
        "bloqueo", "bloqueada", "verifica", "urgente", "premio", "beca",
        "gratis", "ganaste", "suspendida", "aclaracion", "confirma", "paquete"
    )

    private val tldsSospechosos = listOf(".xyz", ".top", ".click", ".site", ".online", ".icu")

    fun analizar(entrada: String): Resultado {
        val texto = entrada.trim()
        if (texto.isEmpty()) {
            return Resultado(Nivel.SOSPECHOSO, 0, listOf("No escribiste ningún link."))
        }

        val conEsquema = if ("://" in texto) texto else "http://$texto"
        val uri = try { URI(conEsquema) } catch (e: Exception) { null }
        val host = uri?.host?.lowercase()
            ?: return Resultado(Nivel.SOSPECHOSO, 2, listOf("El link tiene un formato extraño y no pudimos leerlo."))

        var puntos = 0
        val razones = mutableListOf<String>()

        if (conEsquema.startsWith("http://")) {
            puntos += 2
            razones += "No usa HTTPS, así que la conexión no es segura."
        }
        if (host in acortadores) {
            puntos += 2
            razones += "Es un link acortado: no se ve a dónde te lleva realmente."
        }
        if (Regex("""^\d{1,3}(\.\d{1,3}){3}$""").matches(host)) {
            puntos += 3
            razones += "Usa una dirección numérica en lugar de un nombre. Los sitios reales casi nunca lo hacen."
        }
        for ((marca, oficiales) in marcas) {
            val esOficial = oficiales.any { host == it || host.endsWith(".$it") }
            if (marca in host && !esOficial) {
                puntos += 4
                razones += "Dice '$marca' pero NO es el sitio oficial. Parece una imitación."
                break
            }
        }
        if (palabrasUrgentes.any { it in conEsquema.lowercase() }) {
            puntos += 2
            razones += "Usa palabras de urgencia o premio (bloqueo, beca, gratis...). Es típico de estafas."
        }
        if (tldsSospechosos.any { host.endsWith(it) }) {
            puntos += 2
            razones += "Su terminación de dominio es muy usada en páginas de estafa."
        }
        if (host.count { it == '-' } >= 2) {
            puntos += 1
            razones += "El dominio tiene muchos guiones, algo común en sitios falsos."
        }
        if (host.split(".").size > 4) {
            puntos += 1
            razones += "Tiene demasiados subdominios, una forma de disfrazar el sitio real."
        }
        if ("@" in texto) {
            puntos += 3
            razones += "Contiene '@', un truco para esconder el destino real."
        }

        val nivel = when {
            puntos >= 5 -> Nivel.PELIGROSO
            puntos >= 2 -> Nivel.SOSPECHOSO
            else -> Nivel.SIN_SENALES
        }
        if (razones.isEmpty()) {
            razones += "No encontramos señales de estafa, pero ningún análisis es perfecto. Ten cuidado igual."
        }
        return Resultado(nivel, puntos, razones)
    }
}