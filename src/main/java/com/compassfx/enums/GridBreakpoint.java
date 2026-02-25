package com.compassfx.enums;

public enum GridBreakpoint {
    XS("xs", 0),      // Extra small (phones)
    SM("sm", 576),    // Small (tablets)
    MD("md", 768),    // Medium (small laptops)
    LG("lg", 992),    // Large (desktops)
    XL("xl", 1200),   // Extra large (large desktops)
    XXL("xxl", 1400); // Extra extra large

    private final String styleClass;
    private final int minWidth;

    GridBreakpoint(String styleClass, int minWidth) {
        this.styleClass = styleClass;
        this.minWidth = minWidth;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public static GridBreakpoint fromWidth(double width) {
        if (width >= XXL.minWidth) return XXL;
        if (width >= XL.minWidth) return XL;
        if (width >= LG.minWidth) return LG;
        if (width >= MD.minWidth) return MD;
        if (width >= SM.minWidth) return SM;
        return XS;
    }
}
