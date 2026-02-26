// ============================================
// DrawerSize.java
// src/main/java/com/compassfx/enums/DrawerSize.java
// ============================================
package com.compassfx.enums;

public enum DrawerSize {
    SMALL("small", 280),
    MEDIUM("medium", 400),
    LARGE("large", 600),
    FULL("full", 0); // 0 means full width/height

    private final String styleClass;
    private final double size;

    DrawerSize(String styleClass, double size) {
        this.styleClass = styleClass;
        this.size = size;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public double getSize() {
        return size;
    }
}