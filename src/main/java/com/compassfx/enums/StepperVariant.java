package com.compassfx.enums;

public enum StepperVariant {
    STANDARD("standard"),
    OUTLINED("outlined"),
    DOTS("dots");

    private final String styleClass;

    StepperVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
