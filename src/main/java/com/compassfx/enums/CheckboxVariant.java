// ============================================
// CheckboxVariant.java
// src/main/java/com/compassfx/enums/CheckboxVariant.java
// ============================================
package com.compassfx.enums;

public enum CheckboxVariant {
    DEFAULT("default"),
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error");

    private final String styleClass;

    CheckboxVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}