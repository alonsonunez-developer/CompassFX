// ============================================
// AvatarShape.java
// src/main/java/com/compassfx/enums/AvatarShape.java
// ============================================
package com.compassfx.enums;

public enum AvatarShape {
    CIRCLE("circle"),
    ROUNDED("rounded"),
    SQUARE("square");

    private final String styleClass;

    AvatarShape(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}