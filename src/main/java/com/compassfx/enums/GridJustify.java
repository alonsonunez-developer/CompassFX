package com.compassfx.enums;

public enum GridJustify {
    START("start"),
    CENTER("center"),
    END("end"),
    SPACE_BETWEEN("space-between"),
    SPACE_AROUND("space-around"),
    SPACE_EVENLY("space-evenly");

    private final String styleClass;

    GridJustify(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
