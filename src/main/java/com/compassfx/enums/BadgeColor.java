package com.compassfx.enums;

public enum BadgeColor {
    DEFAULT("default"),
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error"),
    INFO("info");

    private final String styleClass;

    BadgeColor(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
