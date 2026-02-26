package com.compassfx.enums;

public enum TextFieldVariant {
    OUTLINED("outlined"),
    FILLED("filled");

    private final String styleClass;

    TextFieldVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
