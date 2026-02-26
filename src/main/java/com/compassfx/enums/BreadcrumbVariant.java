package com.compassfx.enums;

public enum BreadcrumbVariant {
    STANDARD("standard"),
    OUTLINED("outlined"),
    FILLED("filled");

    private final String styleClass;

    BreadcrumbVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
