package com.compassfx.utils;

import com.compassfx.enums.ChartColor;
import javafx.scene.paint.Color;

public class ChartColorPalette {

    // Material Design Colors
    private static final Color[] MATERIAL_COLORS = {
            Color.web("#2196F3"), // Blue
            Color.web("#F44336"), // Red
            Color.web("#4CAF50"), // Green
            Color.web("#FF9800"), // Orange
            Color.web("#9C27B0"), // Purple
            Color.web("#00BCD4"), // Cyan
            Color.web("#FFEB3B"), // Yellow
            Color.web("#795548"), // Brown
            Color.web("#607D8B"), // Blue Grey
            Color.web("#E91E63")  // Pink
    };

    // Pastel Colors
    private static final Color[] PASTEL_COLORS = {
            Color.web("#AED6F1"), // Light Blue
            Color.web("#F5B7B1"), // Light Red
            Color.web("#A9DFBF"), // Light Green
            Color.web("#FAD7A0"), // Light Orange
            Color.web("#D7BDE2"), // Light Purple
            Color.web("#A3E4D7"), // Light Cyan
            Color.web("#F9E79F"), // Light Yellow
            Color.web("#D7CCC8"), // Light Brown
            Color.web("#B0BEC5"), // Light Grey
            Color.web("#F8BBD0")  // Light Pink
    };

    // Vibrant Colors
    private static final Color[] VIBRANT_COLORS = {
            Color.web("#0D47A1"), // Dark Blue
            Color.web("#B71C1C"), // Dark Red
            Color.web("#1B5E20"), // Dark Green
            Color.web("#E65100"), // Dark Orange
            Color.web("#4A148C"), // Dark Purple
            Color.web("#006064"), // Dark Cyan
            Color.web("#F57F17"), // Dark Yellow
            Color.web("#3E2723"), // Dark Brown
            Color.web("#263238"), // Dark Grey
            Color.web("#880E4F")  // Dark Pink
    };

    // Monochrome Colors
    private static final Color[] MONOCHROME_COLORS = {
            Color.web("#212121"), // Black
            Color.web("#424242"), // Dark Grey
            Color.web("#616161"), // Grey
            Color.web("#757575"), // Medium Grey
            Color.web("#9E9E9E"), // Light Grey
            Color.web("#BDBDBD"), // Lighter Grey
            Color.web("#E0E0E0"), // Very Light Grey
            Color.web("#EEEEEE"), // Almost White
            Color.web("#F5F5F5"), // Off White
            Color.web("#FAFAFA")  // Near White
    };

    public static Color getColor(ChartColor scheme, int index) {
        Color[] colors = getColorArray(scheme);
        return colors[index % colors.length];
    }

    public static Color[] getColorArray(ChartColor scheme) {
        switch (scheme) {
            case PASTEL: return PASTEL_COLORS;
            case VIBRANT: return VIBRANT_COLORS;
            case MONOCHROME: return MONOCHROME_COLORS;
            case MATERIAL:
            default: return MATERIAL_COLORS;
        }
    }

    public static Color getColorWithOpacity(ChartColor scheme, int index, double opacity) {
        Color baseColor = getColor(scheme, index);
        return new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                opacity
        );
    }
}
