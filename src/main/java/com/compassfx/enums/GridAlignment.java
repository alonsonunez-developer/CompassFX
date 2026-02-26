package com.compassfx.enums;

public enum GridAlignment {
    START("start"),
    CENTER("center"),
    END("end"),
    STRETCH("stretch"),
    BASELINE("baseline");

    private final String styleClass;

    GridAlignment(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
