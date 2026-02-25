package com.compassfx.enums;

public enum SnackbarPosition {
    TOP_LEFT("top-left"),
    TOP_CENTER("top-center"),
    TOP_RIGHT("top-right"),
    BOTTOM_LEFT("bottom-left"),
    BOTTOM_CENTER("bottom-center"),
    BOTTOM_RIGHT("bottom-right");

    private final String styleClass;

    SnackbarPosition(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
