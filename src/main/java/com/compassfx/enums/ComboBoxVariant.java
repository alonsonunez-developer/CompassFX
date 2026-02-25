// ============================================
// ComboBoxVariant.java
// src/main/java/com/compassfx/enums/ComboBoxVariant.java
// ============================================
package com.compassfx.enums;

public enum ComboBoxVariant {
    OUTLINED("outlined"),
    FILLED("filled"),
    DEFAULT("default"),
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error");

    private final String styleClass;

    ComboBoxVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}