package com.compassfx.enums;

public enum SortOrder {
    ASCENDING("ascending", "▲"),
    DESCENDING("descending", "▼"),
    NONE("none", "");

    private final String styleClass;
    private final String symbol;

    SortOrder(String styleClass, String symbol) {
        this.styleClass = styleClass;
        this.symbol = symbol;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public String getSymbol() {
        return symbol;
    }

    public SortOrder toggle() {
        switch (this) {
            case NONE:
            case DESCENDING:
                return ASCENDING;
            case ASCENDING:
                return DESCENDING;
            default:
                return NONE;
        }
    }
}
