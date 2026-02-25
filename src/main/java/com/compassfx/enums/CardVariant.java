// ============================================
// CardVariant.java
// src/main/java/com/compassfx/enums/CardVariant.java
// ============================================
package com.compassfx.enums;

public enum CardVariant {
    FILLED("filled"),
    OUTLINED("outlined"),
    ELEVATED("elevated");

    private final String styleClass;

    CardVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}