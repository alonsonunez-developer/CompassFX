package com.compassfx.enums;

public enum StepStatus {
    PENDING("pending"),
    ACTIVE("active"),
    COMPLETED("completed"),
    ERROR("error"),
    WARNING("warning");

    private final String styleClass;

    StepStatus(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
