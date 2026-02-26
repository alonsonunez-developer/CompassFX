package com.compassfx.enums;

public enum MenuVariant {
    STANDARD("standard"),
    ELEVATED("elevated"),
    MINIMAL("minimal");

    private final String styleClass;

    MenuVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
