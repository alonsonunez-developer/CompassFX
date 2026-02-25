package com.compassfx.enums;

public enum SnackbarType {
    DEFAULT("default"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error"),
    INFO("info");

    private final String styleClass;

    SnackbarType(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}