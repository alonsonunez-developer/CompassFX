// ============================================
// AvatarSize.java
// src/main/java/com/compassfx/enums/AvatarSize.java
// ============================================
package com.compassfx.enums;

public enum AvatarSize {
    EXTRA_SMALL("xs", 24),
    SMALL("small", 32),
    MEDIUM("medium", 40),
    LARGE("large", 56),
    EXTRA_LARGE("xl", 80),
    HUGE("huge", 120);

    private final String styleClass;
    private final double size;

    AvatarSize(String styleClass, double size) {
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