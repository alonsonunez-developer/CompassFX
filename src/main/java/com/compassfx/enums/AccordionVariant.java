package com.compassfx.enums;

public enum AccordionVariant {
    STANDARD("standard"),
    OUTLINED("outlined"),
    FILLED("filled");

    private final String styleClass;

    AccordionVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
