package com.compassfx.controls;

import com.compassfx.enums.ComboBoxVariant;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * CompassFX Autocomplete - A searchable/filterable ComboBox
 * Allows typing to filter options in real-time
 */
public class CFXAutocomplete<T> extends ComboBox<T> {

    private static final String DEFAULT_STYLE_CLASS = "cfx-autocomplete";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-combobox.css";

    // Properties
    private final ObjectProperty<ComboBoxVariant> variant;
    private final StringProperty label;
    private final StringProperty helperText;
    private final StringProperty errorText;
    private final BooleanProperty error;
    private final BooleanProperty caseSensitive;
    private final IntegerProperty maxSuggestions;

    // Data
    private ObservableList<T> originalItems;
    private FilteredList<T> filteredItems;
    private TextField editor;

    public CFXAutocomplete() {
        super();

        this.variant = new SimpleObjectProperty<>(this, "variant", ComboBoxVariant.OUTLINED);
        this.label = new SimpleStringProperty(this, "label", "");
        this.helperText = new SimpleStringProperty(this, "helperText", "");
        this.errorText = new SimpleStringProperty(this, "errorText", "");
        this.error = new SimpleBooleanProperty(this, "error", false);
        this.caseSensitive = new SimpleBooleanProperty(this, "caseSensitive", false);
        this.maxSuggestions = new SimpleIntegerProperty(this, "maxSuggestions", 10);

        this.originalItems = FXCollections.observableArrayList();
        this.filteredItems = new FilteredList<>(originalItems, p -> true);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setEditable(true);

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

        // Setup after skin is created
        skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                setupEditor();
            }
        });

        // Listeners
        variant.addListener((obs, old, newVal) -> updateStyleClasses());
        error.addListener((obs, old, newVal) -> updateStyleClasses());
        focusedProperty().addListener((obs, old, newVal) -> updateStyleClasses());
    }

    private void setupEditor() {
        // Get the editor field
        editor = getEditor();
        if (editor == null) return;

        // Handle text changes
        editor.textProperty().addListener((obs, oldText, newText) -> {
            if (!isShowing()) {
                show();
            }
            filterItems(newText);
        });

        // Handle key events
        editor.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Select first item if available
                if (!getItems().isEmpty() && getValue() == null) {
                    setValue(getItems().get(0));
                    hide();
                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                hide();
                editor.setText("");
            }
        });

        // Clear filter when popup is hidden
        showingProperty().addListener((obs, wasShowing, isShowing) -> {
            if (!isShowing) {
                // Reset to show all items when closed
                filteredItems.setPredicate(p -> true);
            }
        });

        // Handle selection
        getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                editor.setText(newVal.toString());
            }
        });
    }

    private void filterItems(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            filteredItems.setPredicate(p -> true);
            setItems(FXCollections.observableArrayList(
                    filteredItems.stream()
                            .limit(maxSuggestions.get())
                            .toList()
            ));
            return;
        }

        String search = caseSensitive.get() ? searchText : searchText.toLowerCase();

        filteredItems.setPredicate(item -> {
            if (item == null) return false;

            String itemText = caseSensitive.get()
                    ? item.toString()
                    : item.toString().toLowerCase();

            return itemText.contains(search);
        });

        // Update items with max suggestions limit
        setItems(FXCollections.observableArrayList(
                filteredItems.stream()
                        .limit(maxSuggestions.get())
                        .toList()
        ));
    }

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
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Override setItems to update originalItems
    public void setAllItems(ObservableList<T> items) {
        this.originalItems = items;
        this.filteredItems = new FilteredList<>(originalItems, p -> true);
        setItems(FXCollections.observableArrayList(
                filteredItems.stream()
                        .limit(maxSuggestions.get())
                        .toList()
        ));
    }

    // Getters and Setters
    public ComboBoxVariant getVariant() { return variant.get(); }
    public void setVariant(ComboBoxVariant variant) { this.variant.set(variant); }
    public ObjectProperty<ComboBoxVariant> variantProperty() { return variant; }

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

    public boolean isCaseSensitive() { return caseSensitive.get(); }
    public void setCaseSensitive(boolean caseSensitive) { this.caseSensitive.set(caseSensitive); }
    public BooleanProperty caseSensitiveProperty() { return caseSensitive; }

    public int getMaxSuggestions() { return maxSuggestions.get(); }
    public void setMaxSuggestions(int maxSuggestions) { this.maxSuggestions.set(maxSuggestions); }
    public IntegerProperty maxSuggestionsProperty() { return maxSuggestions; }
}