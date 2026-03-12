package com.compassfx.enums;

/**
 * Variants for CFXTextArea styling
 */
public enum TextAreaVariant {
    OUTLINED("outlined"),
    FILLED("filled");

    private final String styleClass;

    TextAreaVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}