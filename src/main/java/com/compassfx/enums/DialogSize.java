package com.compassfx.enums;

public enum DialogSize {
    SMALL("small", 400),
    MEDIUM("medium", 600),
    LARGE("large", 800),
    EXTRA_LARGE("extra-large", 1000);

    private final String styleClass;
    private final double width;

    DialogSize(String styleClass, double width) {
        this.styleClass = styleClass;
        this.width = width;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public double getWidth() {
        return width;
    }
}