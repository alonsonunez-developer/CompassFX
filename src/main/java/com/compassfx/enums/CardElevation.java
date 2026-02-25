// ============================================
// CardElevation.java
// src/main/java/com/compassfx/enums/CardElevation.java
// ============================================
package com.compassfx.enums;

public enum CardElevation {
    NONE("none"),
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private final String styleClass;

    CardElevation(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}