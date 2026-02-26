package com.compassfx.enums;

public enum BadgeVariant {
    STANDARD("standard"),
    DOT("dot");

    private final String styleClass;

    BadgeVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
