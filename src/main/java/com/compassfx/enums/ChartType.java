package com.compassfx.enums;

public enum ChartType {
    PIE("pie"),
    DONUT("donut"),
    LINE("line"),
    BAR("bar"),
    RADAR("radar");

    private final String styleClass;

    ChartType(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
