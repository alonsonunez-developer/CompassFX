package com.compassfx.enums;

public enum DatePickerVariant {
    STANDARD("standard"),
    INLINE("inline"),
    MODAL("modal");

    private final String styleClass;

    DatePickerVariant(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
