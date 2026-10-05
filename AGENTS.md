# SafeClick (Android)

App Android que analiza links sospechosos y explica en español simple por qué
son estafa. Prototipo escolar para una demo; no es producto final.

## Stack
- Kotlin + Jetpack Compose + Material 3. Módulo `app`, paquete com.secureup.safeclick.
- Sin Firebase, sin backend, sin APIs externas. Todo local, en memoria (ViewModel).

## Reglas
- Textos de la interfaz en español de México, claros y sin tecnicismos.
- Nunca usar la palabra "SEGURO". Niveles: PELIGROSO, SOSPECHOSO,
  SIN SEÑALES DE PELIGRO (con aviso "Ten cuidado igual").
- Código simple y comentado en español: quien lo escribe está aprendiendo Kotlin
  y debe poder explicarlo en su presentación.
- Dependencias con el catálogo gradle/libs.versions.toml.
- Iconos: Material Icons. No importar los SVG de la referencia.
- Tras cada bloque de cambios, compilar con `gradlew.bat assembleDebug` y corregir errores.

## Referencia
`_referencia/` es el prototipo web (React + Tailwind). Solo es referencia visual:
`src/App.tsx` (pantallas y textos) y `src/index.css` (colores y espaciados).
NO migrar su veredicto: está hardcodeado y es falso.

## Pantallas
Analizar, Resultado, Historial, Premium, Pago (simulado), Pago exitoso y Login
(opcional, omitir si complica). Navegación inferior: Analizar, Historial, Premium.

## Lógica real
`analisis/AnalizadorLink.kt` (`AnalizadorLink.analizar(entrada): Resultado`)
decide el nivel y las razones. No modificarlo sin avisar.

## Premium simulado
3 análisis gratis en la demo. Al pasar el límite: pantalla de límite → Premium →
Pago simulado (banner "no se cobrará nada") → éxito → esPremium = true.