package com.compassfx.controls;

import com.compassfx.enums.ProgressColor;
import javafx.beans.property.*;
import javafx.scene.control.Slider;

/**
 * CompassFX Slider - A Material Design inspired slider component
 * Supports continuous and discrete modes with tick marks
 */
public class CFXSlider extends Slider {

    private static final String DEFAULT_STYLE_CLASS = "cfx-slider";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-slider.css";

    // Properties
    private final ObjectProperty<ProgressColor> color;
    private final BooleanProperty discrete;
    private final BooleanProperty showValueLabel;

    /**
     * Creates a CFXSlider with default range (0-100)
     */
    public CFXSlider() {
        this(0, 100, 50);
    }

    /**
     * Creates a CFXSlider with specified range and value
     * @param min Minimum value
     * @param max Maximum value
     * @param value Initial value
     */
    public CFXSlider(double min, double max, double value) {
        super(min, max, value);

        this.color = new SimpleObjectProperty<>(this, "color", ProgressColor.PRIMARY);
        this.discrete = new SimpleBooleanProperty(this, "discrete", false);
        this.showValueLabel = new SimpleBooleanProperty(this, "showValueLabel", false);

        initialize();
    }

    /**
     * Initialize the slider
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXSlider stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXSlider stylesheet: " + e.getMessage());
        }

        updateStyleClasses();

        // Add listeners
        color.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        discrete.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        orientationProperty().addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("slider")
        );

        getStyleClass().add(color.get().getStyleClass());

        if (discrete.get()) {
            getStyleClass().add("discrete");
        }

        if (getOrientation() == javafx.geometry.Orientation.VERTICAL) {
            getStyleClass().add("vertical");
        } else {
            getStyleClass().add("horizontal");
        }
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // ===== Property Getters and Setters =====

    public ProgressColor getColor() {
        return color.get();
    }

    public void setColor(ProgressColor color) {
        this.color.set(color);
    }

    public ObjectProperty<ProgressColor> colorProperty() {
        return color;
    }

    public boolean isDiscrete() {
        return discrete.get();
    }

    public void setDiscrete(boolean discrete) {
        this.discrete.set(discrete);
    }

    public BooleanProperty discreteProperty() {
        return discrete;
    }

    public boolean isShowValueLabel() {
        return showValueLabel.get();
    }

    public void setShowValueLabel(boolean showValueLabel) {
        this.showValueLabel.set(showValueLabel);
    }

    public BooleanProperty showValueLabelProperty() {
        return showValueLabel;
    }

    public void setShowTicks(boolean b) {
    }
}