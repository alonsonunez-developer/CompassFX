// ============================================
// AvatarVariant.java
// src/main/java/com/compassfx/enums/AvatarVariant.java
// ============================================
package com.compassfx.enums;

public enum AvatarVariant {
    FILLED("filled"),
    OUTLINED("outlined"),
    LIGHT("light");

    private final String styleClass;

    AvatarVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}