package com.compassfx.controls;

import com.compassfx.enums.CheckboxVariant;
import com.compassfx.skins.CFXCheckboxSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Skin;

/**
 * CompassFX Checkbox - A Material Design inspired checkbox component
 * Supports indeterminate state, ripple effect, and color variants
 */
public class CFXCheckbox extends CheckBox {

    private static final String DEFAULT_STYLE_CLASS = "cfx-checkbox";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-checkbox.css";

    private final BooleanProperty rippleEnabled;
    private final ObjectProperty<CheckboxVariant> variant;

    /**
     * Creates a CFXCheckbox with no text
     */
    public CFXCheckbox() {
        this("");
    }

    /**
     * Creates a CFXCheckbox with text
     * @param text The checkbox label text
     */
    public CFXCheckbox(String text) {
        super(text);
        this.rippleEnabled = new SimpleBooleanProperty(this, "rippleEnabled", true);
        this.variant = new SimpleObjectProperty<>(this, "variant", CheckboxVariant.PRIMARY);
        initialize();
    }

    /**
     * Initialize the checkbox
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXCheckbox stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXCheckbox stylesheet: " + e.getMessage());
        }

        updateStyleClasses();

        // Listen for variant changes
        variant.addListener((obs, old, newVal) -> updateStyleClasses());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("check-box")
        );
        getStyleClass().add(variant.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXCheckboxSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Property getters and setters
    public boolean isRippleEnabled() {
        return rippleEnabled.get();
    }

    public void setRippleEnabled(boolean rippleEnabled) {
        this.rippleEnabled.set(rippleEnabled);
    }

    public BooleanProperty rippleEnabledProperty() {
        return rippleEnabled;
    }

    public CheckboxVariant getVariant() {
        return variant.get();
    }

    public void setVariant(CheckboxVariant variant) {
        this.variant.set(variant);
    }

    public ObjectProperty<CheckboxVariant> variantProperty() {
        return variant;
    }
}