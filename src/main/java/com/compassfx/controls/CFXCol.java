package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * CompassFX Column - A column container for grid layouts
 */
public class CFXCol extends Region {

    private static final String DEFAULT_STYLE_CLASS = "cfx-col";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-grid.css";

    private final IntegerProperty span;
    private final IntegerProperty xs;
    private final IntegerProperty sm;
    private final IntegerProperty md;
    private final IntegerProperty lg;
    private final IntegerProperty xl;
    private final Node content;

    public CFXCol(Node content) {
        this(content, 1);
    }

    public CFXCol(Node content, int span) {
        this.content = content;
        this.span = new SimpleIntegerProperty(this, "span", span);
        this.xs = new SimpleIntegerProperty(this, "xs", 0);
        this.sm = new SimpleIntegerProperty(this, "sm", 0);
        this.md = new SimpleIntegerProperty(this, "md", 0);
        this.lg = new SimpleIntegerProperty(this, "lg", 0);
        this.xl = new SimpleIntegerProperty(this, "xl", 0);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
        }

        getChildren().add(content);
        CFXGrid.setColumnSpan(this, span.get());

        // Update column span when span changes
        span.addListener((obs, old, newVal) -> {
            CFXGrid.setColumnSpan(this, newVal.intValue());
        });
    }

    @Override
    protected void layoutChildren() {
        if (content != null) {
            content.resizeRelocate(0, 0, getWidth(), getHeight());
        }
    }

    public Node getContent() { return content; }

    public int getSpan() { return span.get(); }
    public void setSpan(int span) { this.span.set(span); }
    public IntegerProperty spanProperty() { return span; }

    // Responsive properties
    public int getXs() { return xs.get(); }
    public void setXs(int xs) { this.xs.set(xs); }
    public IntegerProperty xsProperty() { return xs; }

    public int getSm() { return sm.get(); }
    public void setSm(int sm) { this.sm.set(sm); }
    public IntegerProperty smProperty() { return sm; }

    public int getMd() { return md.get(); }
    public void setMd(int md) { this.md.set(md); }
    public IntegerProperty mdProperty() { return md; }

    public int getLg() { return lg.get(); }
    public void setLg(int lg) { this.lg.set(lg); }
    public IntegerProperty lgProperty() { return lg; }

    public int getXl() { return xl.get(); }
    public void setXl(int xl) { this.xl.set(xl); }
    public IntegerProperty xlProperty() { return xl; }
}