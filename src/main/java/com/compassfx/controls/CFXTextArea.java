package com.compassfx.controls;

import com.compassfx.enums.TextAreaVariant;
import javafx.beans.property.*;
import javafx.scene.control.TextArea;

/**
 * CompassFX TextArea - A Material Design inspired text area
 * Supports auto-resize and multiple style variants
 */
public class CFXTextArea extends TextArea {

    private static final String DEFAULT_STYLE_CLASS = "cfx-textarea";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-textarea.css";

    // Properties
    private final ObjectProperty<TextAreaVariant> variant;
    private final StringProperty label;
    private final StringProperty helperText;
    private final StringProperty errorText;
    private final BooleanProperty error;
    private final BooleanProperty autoResize;
    private final IntegerProperty minRows;
    private final IntegerProperty maxRows;

    private static final double LINE_HEIGHT = 20; // Approximate line height

    public CFXTextArea() {
        super();

        this.variant = new SimpleObjectProperty<>(this, "variant", TextAreaVariant.OUTLINED);
        this.label = new SimpleStringProperty(this, "label", "");
        this.helperText = new SimpleStringProperty(this, "helperText", "");
        this.errorText = new SimpleStringProperty(this, "errorText", "");
        this.error = new SimpleBooleanProperty(this, "error", false);
        this.autoResize = new SimpleBooleanProperty(this, "autoResize", false);
        this.minRows = new SimpleIntegerProperty(this, "minRows", 3);
        this.maxRows = new SimpleIntegerProperty(this, "maxRows", 10);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setWrapText(true);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        updateStyleClasses();
        setupAutoResize();

        // Listeners
        variant.addListener((obs, old, newVal) -> updateStyleClasses());
        error.addListener((obs, old, newVal) -> updateStyleClasses());
        focusedProperty().addListener((obs, old, newVal) -> updateStyleClasses());
    }

    private void setupAutoResize() {
        // Listen to text changes for auto-resize
        textProperty().addListener((obs, old, newText) -> {
            if (autoResize.get()) {
                resizeToContent();
            }
        });

        autoResize.addListener((obs, old, newVal) -> {
            if (newVal) {
                resizeToContent();
            } else {
                // Reset to default size
                setPrefRowCount(minRows.get());
            }
        });

        minRows.addListener((obs, old, newVal) -> {
            if (autoResize.get()) {
                resizeToContent();
            }
        });

        maxRows.addListener((obs, old, newVal) -> {
            if (autoResize.get()) {
                resizeToContent();
            }
        });
    }

    private void resizeToContent() {
        String text = getText();
        if (text == null || text.isEmpty()) {
            setPrefRowCount(minRows.get());
            return;
        }

        // Count lines
        int lineCount = 1;
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                lineCount++;
            }
        }

        // Apply min/max constraints
        int targetRows = Math.max(minRows.get(), Math.min(lineCount, maxRows.get()));
        setPrefRowCount(targetRows);
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("text-area") &&
                        !styleClass.equals("text-input")
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
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Property getters and setters
    public TextAreaVariant getVariant() { return variant.get(); }
    public void setVariant(TextAreaVariant variant) { this.variant.set(variant); }
    public ObjectProperty<TextAreaVariant> variantProperty() { return variant; }

    public String getLabel() { return label.get(); }
    public void setLabel(String label) { this.label.set(label); }
    public StringProperty labelProperty() { return label; }

    public String getHelperText() { return helperText.get(); }
    public void setHelperText(String helperText) { this.helperText.set(helperText); }
    public StringProperty helperTextProperty() { return helperText; }

    public String getErrorText() { return errorText.get(); }
    public void setErrorText(String errorText) {
        this.errorText.set(errorText);
        this.error.set(errorText != null && !errorText.isEmpty());
    }
    public StringProperty errorTextProperty() { return errorText; }

    public boolean isError() { return error.get(); }
    public void setError(boolean error) { this.error.set(error); }
    public BooleanProperty errorProperty() { return error; }

    public boolean isAutoResize() { return autoResize.get(); }
    public void setAutoResize(boolean autoResize) { this.autoResize.set(autoResize); }
    public BooleanProperty autoResizeProperty() { return autoResize; }

    public int getMinRows() { return minRows.get(); }
    public void setMinRows(int minRows) { this.minRows.set(minRows); }
    public IntegerProperty minRowsProperty() { return minRows; }

    public int getMaxRows() { return maxRows.get(); }
    public void setMaxRows(int maxRows) { this.maxRows.set(maxRows); }
    public IntegerProperty maxRowsProperty() { return maxRows; }
}