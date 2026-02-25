package com.compassfx.enums;

public enum MenuPosition {
    TOP("top"),
    BOTTOM("bottom"),
    LEFT("left"),
    RIGHT("right");

    private final String styleClass;

    MenuPosition(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
