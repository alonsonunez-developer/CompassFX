package com.compassfx.enums;

public enum ButtonColor {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error");

    private final String styleClass;

    ButtonColor(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
