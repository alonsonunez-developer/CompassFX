package com.compassfx.controls;

import com.compassfx.enums.TextFieldVariant;
import com.compassfx.skins.CFXTextFieldSkin;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

/**
 * CompassFX TextField - A Material Design inspired text field component
 * Supports outlined and filled variants, labels, helper text, and error states
 */
public class CFXTextField extends TextField {

    private static final String DEFAULT_STYLE_CLASS = "cfx-textfield";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-textfield.css";

    // Properties
    private final ObjectProperty<TextFieldVariant> variant;
    private final StringProperty label;
    private final StringProperty helperText;
    private final StringProperty errorText;
    private final BooleanProperty error;
    private final BooleanProperty floatingLabel;
    private final ObjectProperty<Node> leadingIcon;
    private final ObjectProperty<Node> trailingIcon;
    private final BooleanProperty required;

    /**
     * Creates a CFXTextField
     */
    public CFXTextField() {
        this("");
    }

    /**
     * Creates a CFXTextField with prompt text
     * @param promptText The placeholder text
     */
    public CFXTextField(String promptText) {
        super();
        setPromptText(promptText);

        // Initialize properties
        this.variant = new SimpleObjectProperty<>(this, "variant", TextFieldVariant.OUTLINED);
        this.label = new SimpleStringProperty(this, "label", "");
        this.helperText = new SimpleStringProperty(this, "helperText", "");
        this.errorText = new SimpleStringProperty(this, "errorText", "");
        this.error = new SimpleBooleanProperty(this, "error", false);
        this.floatingLabel = new SimpleBooleanProperty(this, "floatingLabel", true);
        this.leadingIcon = new SimpleObjectProperty<>(this, "leadingIcon", null);
        this.trailingIcon = new SimpleObjectProperty<>(this, "trailingIcon", null);
        this.required = new SimpleBooleanProperty(this, "required", false);

        initialize();
    }

    /**
     * Initialize the text field
     */
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXTextField stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXTextField stylesheet: " + e.getMessage());
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
                        !styleClass.equals("text-input") &&
                        !styleClass.equals("text-field")
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
        return new CFXTextFieldSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // ===== Property Getters and Setters =====

    public TextFieldVariant getVariant() {
        return variant.get();
    }

    public void setVariant(TextFieldVariant variant) {
        this.variant.set(variant);
    }

    public ObjectProperty<TextFieldVariant> variantProperty() {
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

    public Node getLeadingIcon() {
        return leadingIcon.get();
    }

    public void setLeadingIcon(Node leadingIcon) {
        this.leadingIcon.set(leadingIcon);
    }

    public ObjectProperty<Node> leadingIconProperty() {
        return leadingIcon;
    }

    public Node getTrailingIcon() {
        return trailingIcon.get();
    }

    public void setTrailingIcon(Node trailingIcon) {
        this.trailingIcon.set(trailingIcon);
    }

    public ObjectProperty<Node> trailingIconProperty() {
        return trailingIcon;
    }

    public boolean isRequired() {
        return required.get();
    }

    public void setRequired(boolean required) {
        this.required.set(required);
    }

    public BooleanProperty requiredProperty() {
        return required;
    }
}
