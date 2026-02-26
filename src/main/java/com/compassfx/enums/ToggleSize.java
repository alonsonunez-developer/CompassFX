package com.compassfx.enums;

public enum ToggleSize {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String styleClass;

    ToggleSize(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}

