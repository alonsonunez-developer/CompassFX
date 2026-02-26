package com.compassfx.controls;

import com.compassfx.enums.ProgressColor;
import com.compassfx.enums.ProgressSize;
import com.compassfx.skins.CFXProgressSpinnerSkin;
import javafx.beans.property.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX ProgressSpinner - A Material Design inspired circular progress indicator
 * Supports determinate and indeterminate modes
 */
public class CFXProgressSpinner extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-progressspinner";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-progress.css";

    // Properties
    private final DoubleProperty progress;
    private final ObjectProperty<ProgressSize> size;
    private final ObjectProperty<ProgressColor> color;
    private final BooleanProperty indeterminate;

    /**
     * Creates a CFXProgressSpinner
     */
    public CFXProgressSpinner() {
        this(0.0, false);
    }

    /**
     * Creates a CFXProgressSpinner with specified progress
     * @param progress Initial progress (0.0 to 1.0)
     * @param indeterminate Whether to show indeterminate animation
     */
    public CFXProgressSpinner(double progress, boolean indeterminate) {
        this.progress = new SimpleDoubleProperty(this, "progress", progress);
        this.size = new SimpleObjectProperty<>(this, "size", ProgressSize.MEDIUM);
        this.color = new SimpleObjectProperty<>(this, "color", ProgressColor.PRIMARY);
        this.indeterminate = new SimpleBooleanProperty(this, "indeterminate", indeterminate);

        initialize();
    }

    /**
     * Initialize the spinner
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXProgressSpinner stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXProgressSpinner stylesheet: " + e.getMessage());
        }

        updateStyleClasses();

        // Add listeners
        size.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        color.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        indeterminate.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );

        getStyleClass().add(size.get().getStyleClass());
        getStyleClass().add(color.get().getStyleClass());

        if (indeterminate.get()) {
            getStyleClass().add("indeterminate");
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXProgressSpinnerSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // ===== Property Getters and Setters =====

    public double getProgress() {
        return progress.get();
    }

    public void setProgress(double progress) {
        this.progress.set(Math.max(0.0, Math.min(1.0, progress)));
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public ProgressSize getSize() {
        return size.get();
    }

    public void setSize(ProgressSize size) {
        this.size.set(size);
    }

    public ObjectProperty<ProgressSize> sizeProperty() {
        return size;
    }

    public ProgressColor getColor() {
        return color.get();
    }

    public void setColor(ProgressColor color) {
        this.color.set(color);
    }

    public ObjectProperty<ProgressColor> colorProperty() {
        return color;
    }

    public boolean isIndeterminate() {
        return indeterminate.get();
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate.set(indeterminate);
    }

    public BooleanProperty indeterminateProperty() {
        return indeterminate;
    }
}
