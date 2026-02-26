package com.compassfx.enums;

public enum SliderOrientation {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String styleClass;

    SliderOrientation(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}