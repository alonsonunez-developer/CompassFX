package com.compassfx.enums;

public enum ChartColor {
    MATERIAL("material"),
    PASTEL("pastel"),
    VIBRANT("vibrant"),
    MONOCHROME("monochrome");

    private final String styleClass;

    ChartColor(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
