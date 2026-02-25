// ============================================
// TabVariant.java
// src/main/java/com/compassfx/enums/TabVariant.java
// ============================================
package com.compassfx.enums;

public enum TabVariant {
    STANDARD("standard"),
    FILLED("filled"),
    PILLS("pills"),
    UNDERLINE("underline");

    private final String styleClass;

    TabVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}