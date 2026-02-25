package com.compassfx.enums;

public enum TimePickerVariant {
    ANALOG("analog"),
    DIGITAL("digital");

    private final String styleClass;

    TimePickerVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
