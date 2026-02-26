package com.compassfx.enums;

public enum TimeFormat {
    HOUR_12("12h"),
    HOUR_24("24h");

    private final String styleClass;

    TimeFormat(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
