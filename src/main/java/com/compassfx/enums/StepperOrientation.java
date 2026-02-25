package com.compassfx.enums;

public enum StepperOrientation {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String styleClass;

    StepperOrientation(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
