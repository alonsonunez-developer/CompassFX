// ============================================
// BadgeStyle.java
// src/main/java/com/compassfx/enums/BadgeStyle.java
// ============================================
package com.compassfx.enums;

public enum BadgeStyle {
    SOLID("solid"),           // Filled with color
    OUTLINE("outline"),       // Border only
    SUBTLE("subtle"),         // Light background
    SURFACE("surface");       // White background with colored text

    private final String styleClass;

    BadgeStyle(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}