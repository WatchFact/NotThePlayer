/**
 * ReTune Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

/** Neutral ReTune palette: retains accessibility-friendly monochrome fallbacks. */
private val ReTuneDarkColors: ColorScheme =
    ColorScheme(
        primary = Color(0xFFE8E8E8),
        onPrimary = Color(0xFF1B1B1B),
        primaryContainer = Color(0xFF323232),
        onPrimaryContainer = Color(0xFFF2F2F2),
        inversePrimary = Color(0xFF2F2F2F),
        secondary = Color(0xFFC6C6C6),
        onSecondary = Color(0xFF2B2B2B),
        secondaryContainer = Color(0xFF3A3A3A),
        onSecondaryContainer = Color(0xFFEBEBEB),
        tertiary = Color(0xFFB4B4B4),
        onTertiary = Color(0xFF2E2E2E),
        tertiaryContainer = Color(0xFF404040),
        onTertiaryContainer = Color(0xFFE4E4E4),
        background = Color(0xFF0C0C0C),
        onBackground = Color(0xFFE6E6E6),
        surface = Color(0xFF0C0C0C),
        onSurface = Color(0xFFE6E6E6),
        surfaceVariant = Color(0xFF272727),
        onSurfaceVariant = Color(0xFFBDBDBD),
        surfaceTint = Color(0xFFE8E8E8),
        inverseSurface = Color(0xFFE6E6E6),
        inverseOnSurface = Color(0xFF161616),
        error = Color(0xFFD08C8C),
        onError = Color(0xFF3A1414),
        errorContainer = Color(0xFF5A2626),
        onErrorContainer = Color(0xFFF6DADA),
        outline = Color(0xFF6E6E6E),
        outlineVariant = Color(0xFF3A3A3A),
        scrim = Color(0xFF000000),
        surfaceBright = Color(0xFF343434),
        surfaceDim = Color(0xFF0C0C0C),
        surfaceContainerLowest = Color(0xFF070707),
        surfaceContainerLow = Color(0xFF141414),
        surfaceContainer = Color(0xFF181818),
        surfaceContainerHigh = Color(0xFF222222),
        surfaceContainerHighest = Color(0xFF2D2D2D),
    )

/** Neutral light scheme, same single grey axis. */
private val ReTuneLightColors: ColorScheme =
    ColorScheme(
        primary = Color(0xFF1B1B1B),
        onPrimary = Color(0xFFF5F5F5),
        primaryContainer = Color(0xFFDEDEDE),
        onPrimaryContainer = Color(0xFF161616),
        inversePrimary = Color(0xFFE8E8E8),
        secondary = Color(0xFF4C4C4C),
        onSecondary = Color(0xFFF5F5F5),
        secondaryContainer = Color(0xFFE3E3E3),
        onSecondaryContainer = Color(0xFF1E1E1E),
        tertiary = Color(0xFF5C5C5C),
        onTertiary = Color(0xFFF5F5F5),
        tertiaryContainer = Color(0xFFE8E8E8),
        onTertiaryContainer = Color(0xFF232323),
        background = Color(0xFFFAFAFA),
        onBackground = Color(0xFF161616),
        surface = Color(0xFFFAFAFA),
        onSurface = Color(0xFF161616),
        surfaceVariant = Color(0xFFE7E7E7),
        onSurfaceVariant = Color(0xFF4C4C4C),
        surfaceTint = Color(0xFF1B1B1B),
        inverseSurface = Color(0xFF2A2A2A),
        inverseOnSurface = Color(0xFFEDEDED),
        error = Color(0xFF8E3A3A),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFF6DADA),
        onErrorContainer = Color(0xFF410E0E),
        outline = Color(0xFF8A8A8A),
        outlineVariant = Color(0xFFCFCFCF),
        scrim = Color(0xFF000000),
        surfaceBright = Color(0xFFFAFAFA),
        surfaceDim = Color(0xFFE0E0E0),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF4F4F4),
        surfaceContainer = Color(0xFFF0F0F0),
        surfaceContainerHigh = Color(0xFFEAEAEA),
        surfaceContainerHighest = Color(0xFFE4E4E4),
    )

/**
 * Restrained corner radii. Material 3's expressive defaults balloon up to 28dp, which reads as
 * bubbly rather than minimal and costs extra overdraw on every container.
 */
private val ReTuneShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

/** Wallpaper-based Material 3 colours, with manually selectable accessible fallbacks. */
@Composable
fun MetrolistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    dynamicColor: Boolean = false,
    accentColor: Int = 0,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val colorScheme = remember(darkTheme, pureBlack, dynamicColor, accentColor, configuration, context) {
        val base = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme ->
                dynamicDarkColorScheme(context)
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
                dynamicLightColorScheme(context)
            darkTheme -> ReTuneDarkColors.withAccent(accentColor, true)
            else -> ReTuneLightColors.withAccent(accentColor, false)
        }
        if (darkTheme && pureBlack) base.pureBlack(true) else base
    }
    MaterialTheme(colorScheme = colorScheme, shapes = ReTuneShapes, content = content)
}

/** A zero-valued accent keeps the original monochrome palette. */
private fun ColorScheme.withAccent(index: Int, dark: Boolean): ColorScheme {
    if (index == 0) return this
    val palette = when (index) {
        1 -> if (dark) listOf(Color(0xFFD0BCFF), Color(0xFF381E72), Color(0xFF4F378B), Color(0xFFEADDFF))
             else listOf(Color(0xFF6750A4), Color.White, Color(0xFFEADDFF), Color(0xFF21005D))
        2 -> if (dark) listOf(Color(0xFFA9C7FF), Color(0xFF00315B), Color(0xFF184A77), Color(0xFFD5E3FF))
             else listOf(Color(0xFF315F8F), Color.White, Color(0xFFD5E3FF), Color(0xFF001C3B))
        3 -> if (dark) listOf(Color(0xFF9BD7B8), Color(0xFF003826), Color(0xFF19523B), Color(0xFFB6F2D2))
             else listOf(Color(0xFF286A4E), Color.White, Color(0xFFB6F2D2), Color(0xFF002114))
        4 -> if (dark) listOf(Color(0xFFFFB49B), Color(0xFF612B18), Color(0xFF80432D), Color(0xFFFFDBCF))
             else listOf(Color(0xFF925139), Color.White, Color(0xFFFFDBCF), Color(0xFF371204))
        else -> return this
    }
    val (accent, onAccent, container, onContainer) = palette
    return copy(
        primary = accent,
        onPrimary = onAccent,
        primaryContainer = container,
        onPrimaryContainer = onContainer,
        secondary = accent,
        onSecondary = onAccent,
        secondaryContainer = container,
        onSecondaryContainer = onContainer,
        tertiary = accent,
        onTertiary = onAccent,
        tertiaryContainer = container,
        onTertiaryContainer = onContainer,
        inversePrimary = accent,
        surfaceTint = accent,
    )
}

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black,
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}
