package com.compassfx.enums;

public enum ButtonVariant {
    CONTAINED("contained"),
    OUTLINED("outlined"),
    TEXT("text");

    private final String styleClass;

    ButtonVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
