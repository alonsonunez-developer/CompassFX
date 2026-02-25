package com.compassfx.enums;

public enum BadgePosition {
    TOP_RIGHT("top-right"),
    TOP_LEFT("top-left"),
    BOTTOM_RIGHT("bottom-right"),
    BOTTOM_LEFT("bottom-left");

    private final String styleClass;

    BadgePosition(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
