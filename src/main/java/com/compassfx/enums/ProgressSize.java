package com.compassfx.enums;

public enum ProgressSize {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String styleClass;

    ProgressSize(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
