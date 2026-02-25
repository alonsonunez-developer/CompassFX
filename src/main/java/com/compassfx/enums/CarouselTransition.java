package com.compassfx.enums;

public enum CarouselTransition {
    SLIDE("slide"),
    FADE("fade"),
    SCALE("scale"),
    NONE("none");

    private final String styleClass;

    CarouselTransition(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
