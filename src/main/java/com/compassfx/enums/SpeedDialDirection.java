package com.compassfx.enums;

/**
 * Direction for Speed Dial options expansion
 */
public enum SpeedDialDirection {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right");

    private final String styleClass;

    SpeedDialDirection(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}