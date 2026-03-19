package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

/**
 * CompassFX Divider - A styled separator component
 */
public class CFXDivider extends Separator {

    private static final String DEFAULT_STYLE_CLASS = "cfx-divider";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-divider.css";

    private final DoubleProperty thickness;
    private final DoubleProperty spacing;
    private final BooleanProperty dashed;

    public CFXDivider() {
        this(Orientation.HORIZONTAL);
    }

    public CFXDivider(Orientation orientation) {
        super(orientation);
        this.thickness = new SimpleDoubleProperty(this, "thickness", 1.0);
        this.spacing = new SimpleDoubleProperty(this, "spacing", 16.0);
        this.dashed = new SimpleBooleanProperty(this, "dashed", false);

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
            System.err.println("ERROR: " + e.getMessage());
        }

        setupListeners();
        updateStyle();
    }

    private void setupListeners() {
        thickness.addListener((obs, old, newVal) -> updateStyle());
        dashed.addListener((obs, old, newVal) -> updateStyle());
    }

    private void updateStyle() {
        getStyleClass().removeIf(s -> s.equals("dashed"));

        if (dashed.get()) {
            getStyleClass().add("dashed");
        }

        setStyle("-fx-border-width: " + thickness.get() + "px;");
    }

    // Properties
    public double getThickness() { return thickness.get(); }
    public void setThickness(double thickness) { this.thickness.set(thickness); }
    public DoubleProperty thicknessProperty() { return thickness; }

    public double getSpacing() { return spacing.get(); }
    public void setSpacing(double spacing) { this.spacing.set(spacing); }
    public DoubleProperty spacingProperty() { return spacing; }

    public boolean isDashed() { return dashed.get(); }
    public void setDashed(boolean dashed) { this.dashed.set(dashed); }
    public BooleanProperty dashedProperty() { return dashed; }
}