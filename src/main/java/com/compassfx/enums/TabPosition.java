// ============================================
// TabPosition.java
// src/main/java/com/compassfx/enums/TabPosition.java
// ============================================
package com.compassfx.enums;

public enum TabPosition {
    TOP("top"),
    BOTTOM("bottom"),
    LEFT("left"),
    RIGHT("right");

    private final String styleClass;

    TabPosition(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}