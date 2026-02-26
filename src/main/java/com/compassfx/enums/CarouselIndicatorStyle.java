package com.compassfx.enums;

public enum CarouselIndicatorStyle {
    DOTS("dots"),
    LINES("lines"),
    NUMBERS("numbers"),
    THUMBNAILS("thumbnails"),
    NONE("none");

    private final String styleClass;

    CarouselIndicatorStyle(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
