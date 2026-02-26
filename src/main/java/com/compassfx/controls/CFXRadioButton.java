package com.compassfx.controls;

import com.compassfx.skins.CFXRadioButtonSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Skin;

/**
 * CompassFX RadioButton - A Material Design inspired radio button component
 * Supports ripple effect
 */
public class CFXRadioButton extends RadioButton {

    private static final String DEFAULT_STYLE_CLASS = "cfx-radiobutton";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-radiobutton.css";

    private final BooleanProperty rippleEnabled;

    /**
     * Creates a CFXRadioButton with no text
     */
    public CFXRadioButton() {
        this("");
    }

    /**
     * Creates a CFXRadioButton with text
     * @param text The radio button label text
     */
    public CFXRadioButton(String text) {
        super(text);
        this.rippleEnabled = new SimpleBooleanProperty(this, "rippleEnabled", true);
        initialize();
    }

    /**
     * Initialize the radio button
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXRadioButton stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXRadioButton stylesheet: " + e.getMessage());
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXRadioButtonSkin(this);
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
}