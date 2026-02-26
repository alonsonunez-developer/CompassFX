// ============================================
// DrawerPosition.java
// src/main/java/com/compassfx/enums/DrawerPosition.java
// ============================================
package com.compassfx.enums;

public enum DrawerPosition {
    LEFT("left"),
    RIGHT("right"),
    TOP("top"),
    BOTTOM("bottom");

    private final String styleClass;

    DrawerPosition(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}