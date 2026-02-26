package com.compassfx.enums;

public enum ButtonSize {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String styleClass;

    ButtonSize(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}