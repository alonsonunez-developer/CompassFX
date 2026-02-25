// ============================================
// SkeletonVariant.java
// src/main/java/com/compassfx/enums/SkeletonVariant.java
// ============================================
package com.compassfx.enums;

public enum SkeletonVariant {
    TEXT("text"),
    CIRCULAR("circular"),
    RECTANGULAR("rectangular"),
    ROUNDED("rounded");

    private final String styleClass;

    SkeletonVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}