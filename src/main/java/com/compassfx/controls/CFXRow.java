package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * CompassFX Row - A horizontal row container for grid layouts
 */
public class CFXRow extends HBox {

    private static final String DEFAULT_STYLE_CLASS = "cfx-row";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-grid.css";

    private final DoubleProperty gutter;
    private final BooleanProperty wrap;

    public CFXRow() {
        this(16);
    }

    public CFXRow(double gutter) {
        super(gutter);
        this.gutter = new SimpleDoubleProperty(this, "gutter", gutter);
        this.wrap = new SimpleBooleanProperty(this, "wrap", false);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.TOP_LEFT);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
        }

        // Bind spacing to gutter
        spacingProperty().bind(gutter);
    }

    public void addColumn(Node node, int span) {
        CFXGrid.setColumnSpan(node, span);
        getChildren().add(node);
    }

    public DoubleProperty gutterProperty() { return gutter; }
    public double getGutter() { return gutter.get(); }
    public void setGutter(double gutter) { this.gutter.set(gutter); }

    public BooleanProperty wrapProperty() { return wrap; }
    public boolean isWrap() { return wrap.get(); }
    public void setWrap(boolean wrap) { this.wrap.set(wrap); }
}