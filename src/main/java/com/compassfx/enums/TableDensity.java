package com.compassfx.enums;

public enum TableDensity {
    COMPACT("compact"),
    STANDARD("standard"),
    COMFORTABLE("comfortable");

    private final String styleClass;

    TableDensity(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
