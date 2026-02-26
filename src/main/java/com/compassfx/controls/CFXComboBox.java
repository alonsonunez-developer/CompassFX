package com.compassfx.controls;

import com.compassfx.enums.ComboBoxVariant;
import com.compassfx.skins.CFXComboBoxSkin;
import javafx.beans.property.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;

/**
 * CompassFX ComboBox - A Material Design inspired combobox/select component
 * Supports outlined and filled variants with floating labels
 * @param <T> The type of items in the ComboBox
 */
public class CFXComboBox<T> extends ComboBox<T> {

    private static final String DEFAULT_STYLE_CLASS = "cfx-combobox";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-combobox.css";

    // Properties
    private final ObjectProperty<ComboBoxVariant> variant;
    private final StringProperty label;
    private final StringProperty helperText;
    private final StringProperty errorText;
    private final BooleanProperty error;
    private final BooleanProperty floatingLabel;

    /**
     * Creates a CFXComboBox
     */
    public CFXComboBox() {
        super();

        // Initialize properties
        this.variant = new SimpleObjectProperty<>(this, "variant", ComboBoxVariant.OUTLINED);
        this.label = new SimpleStringProperty(this, "label", "");
        this.helperText = new SimpleStringProperty(this, "helperText", "");
        this.errorText = new SimpleStringProperty(this, "errorText", "");
        this.error = new SimpleBooleanProperty(this, "error", false);
        this.floatingLabel = new SimpleBooleanProperty(this, "floatingLabel", true);

        initialize();
    }

    /**
     * Initialize the combobox
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXComboBox stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXComboBox stylesheet: " + e.getMessage());
        }

        updateStyleClasses();

        // Add listeners
        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        error.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        focusedProperty().addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("combo-box-base") &&
                        !styleClass.equals("combo-box")
        );

        getStyleClass().add(variant.get().getStyleClass());

        if (error.get()) {
            getStyleClass().add("error");
        }

        if (isFocused()) {
            getStyleClass().add("focused");
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXComboBoxSkin<>(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // ===== Property Getters and Setters =====

    public ComboBoxVariant getVariant() {
        return variant.get();
    }

    public void setVariant(ComboBoxVariant variant) {
        this.variant.set(variant);
    }

    public ObjectProperty<ComboBoxVariant> variantProperty() {
        return variant;
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public StringProperty labelProperty() {
        return label;
    }

    public String getHelperText() {
        return helperText.get();
    }

    public void setHelperText(String helperText) {
        this.helperText.set(helperText);
    }

    public StringProperty helperTextProperty() {
        return helperText;
    }

    public String getErrorText() {
        return errorText.get();
    }

    public void setErrorText(String errorText) {
        this.errorText.set(errorText);
        this.error.set(errorText != null && !errorText.isEmpty());
    }

    public StringProperty errorTextProperty() {
        return errorText;
    }

    public boolean isError() {
        return error.get();
    }

    public void setError(boolean error) {
        this.error.set(error);
    }

    public BooleanProperty errorProperty() {
        return error;
    }

    public boolean isFloatingLabel() {
        return floatingLabel.get();
    }

    public void setFloatingLabel(boolean floatingLabel) {
        this.floatingLabel.set(floatingLabel);
    }

    public BooleanProperty floatingLabelProperty() {
        return floatingLabel;
    }
}