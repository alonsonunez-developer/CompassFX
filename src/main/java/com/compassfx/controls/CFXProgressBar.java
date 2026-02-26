package com.compassfx.controls;

import com.compassfx.enums.ProgressColor;
import com.compassfx.enums.ProgressSize;
import javafx.beans.property.*;
import javafx.scene.control.ProgressBar;

/**
 * CompassFX ProgressBar - A Material Design inspired progress bar component
 * Supports determinate and indeterminate modes with different colors and sizes
 */
public class CFXProgressBar extends ProgressBar {

    private static final String DEFAULT_STYLE_CLASS = "cfx-progressbar";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-progress.css";

    // Properties
    private final ObjectProperty<ProgressSize> size;
    private final ObjectProperty<ProgressColor> color;
    private final BooleanProperty showLabel;
    private final StringProperty labelFormat;

    /**
     * Creates a CFXProgressBar with 0 progress
     */
    public CFXProgressBar() {
        this(0.0);
    }

    /**
     * Creates a CFXProgressBar with specified progress
     * @param progress Initial progress (0.0 to 1.0)
     */
    public CFXProgressBar(double progress) {
        super(progress);

        this.size = new SimpleObjectProperty<>(this, "size", ProgressSize.MEDIUM);
        this.color = new SimpleObjectProperty<>(this, "color", ProgressColor.PRIMARY);
        this.showLabel = new SimpleBooleanProperty(this, "showLabel", false);
        this.labelFormat = new SimpleStringProperty(this, "labelFormat", "{0}%");

        initialize();
    }

    /**
     * Initialize the progress bar
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXProgressBar stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXProgressBar stylesheet: " + e.getMessage());
        }

        updateStyleClasses();

        // Add listeners
        size.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        color.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("progress-bar")
        );

        getStyleClass().add(size.get().getStyleClass());
        getStyleClass().add(color.get().getStyleClass());
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // ===== Property Getters and Setters =====

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

    public boolean isShowLabel() {
        return showLabel.get();
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel.set(showLabel);
    }

    public BooleanProperty showLabelProperty() {
        return showLabel;
    }

    public String getLabelFormat() {
        return labelFormat.get();
    }

    public void setLabelFormat(String labelFormat) {
        this.labelFormat.set(labelFormat);
    }

    public StringProperty labelFormatProperty() {
        return labelFormat;
    }
}
