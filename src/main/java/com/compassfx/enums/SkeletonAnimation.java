// ============================================
// SkeletonAnimation.java
// src/main/java/com/compassfx/enums/SkeletonAnimation.java
// ============================================
package com.compassfx.enums;

public enum SkeletonAnimation {
    WAVE("wave"),
    PULSE("pulse"),
    NONE("none");

    private final String styleClass;

    SkeletonAnimation(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}