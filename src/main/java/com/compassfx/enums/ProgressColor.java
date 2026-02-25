package com.compassfx.enums;

public enum ProgressColor {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error");

    private final String styleClass;

    ProgressColor(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}