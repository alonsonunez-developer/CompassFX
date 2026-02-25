package com.compassfx.controls;

import com.compassfx.enums.ToggleSize;
import com.compassfx.skins.CFXToggleSkin;
import javafx.beans.property.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Toggle - A Material Design inspired toggle/switch component
 * Supports different sizes and smooth animations
 */
public class CFXToggle extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-toggle";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-toggle.css";

    // Properties
    private final BooleanProperty selected;
    private final StringProperty text;
    private final ObjectProperty<ToggleSize> size;

    /**
     * Creates a CFXToggle with no text
     */
    public CFXToggle() {
        this("");
    }

    /**
     * Creates a CFXToggle with text
     * @param text The toggle label text
     */
    public CFXToggle(String text) {
        this.selected = new SimpleBooleanProperty(this, "selected", false);
        this.text = new SimpleStringProperty(this, "text", text);
        this.size = new SimpleObjectProperty<>(this, "size", ToggleSize.MEDIUM);

        initialize();
    }

    /**
     * Initialize the toggle
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXToggle stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXToggle stylesheet: " + e.getMessage());
        }

        updateStyleClasses();

        // Add listeners
        selected.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        size.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        disabledProperty().addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );

        getStyleClass().add(size.get().getStyleClass());

        if (selected.get()) {
            getStyleClass().add("selected");
        }

        if (isDisabled()) {
            getStyleClass().add("disabled");
        }
    }

    /**
     * Toggle the selected state
     */
    public void toggle() {
        if (!isDisabled()) {
            setSelected(!isSelected());
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXToggleSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // ===== Property Getters and Setters =====

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public ToggleSize getSize() {
        return size.get();
    }

    public void setSize(ToggleSize size) {
        this.size.set(size);
    }

    public ObjectProperty<ToggleSize> sizeProperty() {
        return size;
    }
}
