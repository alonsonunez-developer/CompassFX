package com.compassfx.enums;

public enum BreadcrumbSeparator {
    SLASH("/"),
    CHEVRON("›"),
    ARROW("→"),
    DOT("•"),
    PIPE("|");

    private final String symbol;

    BreadcrumbSeparator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
