package com.compassfx.enums;

public enum TableSelectionMode {
    SINGLE("single"),
    MULTIPLE("multiple"),
    NONE("none");

    private final String styleClass;

    TableSelectionMode(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
