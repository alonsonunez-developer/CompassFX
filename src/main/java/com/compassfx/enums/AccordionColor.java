package com.compassfx.enums;

public enum AccordionColor {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    NEUTRAL("neutral");

    private final String styleClass;

    AccordionColor(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
