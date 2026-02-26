package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import com.compassfx.skins.CFXMultiSelectSkin;

/**
 * CompassFX MultiSelect - Modern multiselect with checkboxes and chips
 * Shows selected items as removable chips inside the field
 */
public class CFXMultiSelect<T> extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-multiselect";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-multiselect.css";

    // Properties
    private final ObjectProperty<ObservableList<T>> items;
    private final ObjectProperty<ObservableList<T>> selectedItems;
    private final StringProperty label;
    private final StringProperty promptText;
    private final IntegerProperty maxChipsDisplay;
    private final BooleanProperty selectAllEnabled;

    public CFXMultiSelect() {
        this.items = new SimpleObjectProperty<>(this, "items", FXCollections.observableArrayList());
        this.selectedItems = new SimpleObjectProperty<>(this, "selectedItems", FXCollections.observableArrayList());
        this.label = new SimpleStringProperty(this, "label", "");
        this.promptText = new SimpleStringProperty(this, "promptText", "Select items...");
        this.maxChipsDisplay = new SimpleIntegerProperty(this, "maxChipsDisplay", 3);
        this.selectAllEnabled = new SimpleBooleanProperty(this, "selectAllEnabled", true);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXMultiSelectSkin<>(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Getters and Setters
    public ObservableList<T> getItems() { return items.get(); }
    public void setItems(ObservableList<T> items) { this.items.set(items); }
    public ObjectProperty<ObservableList<T>> itemsProperty() { return items; }

    public ObservableList<T> getSelectedItems() { return selectedItems.get(); }
    public void setSelectedItems(ObservableList<T> selectedItems) { this.selectedItems.set(selectedItems); }
    public ObjectProperty<ObservableList<T>> selectedItemsProperty() { return selectedItems; }

    public String getLabel() { return label.get(); }
    public void setLabel(String label) { this.label.set(label); }
    public StringProperty labelProperty() { return label; }

    public String getPromptText() { return promptText.get(); }
    public void setPromptText(String promptText) { this.promptText.set(promptText); }
    public StringProperty promptTextProperty() { return promptText; }

    public int getMaxChipsDisplay() { return maxChipsDisplay.get(); }
    public void setMaxChipsDisplay(int maxChipsDisplay) { this.maxChipsDisplay.set(maxChipsDisplay); }
    public IntegerProperty maxChipsDisplayProperty() { return maxChipsDisplay; }

    public boolean isSelectAllEnabled() { return selectAllEnabled.get(); }
    public void setSelectAllEnabled(boolean selectAllEnabled) { this.selectAllEnabled.set(selectAllEnabled); }
    public BooleanProperty selectAllEnabledProperty() { return selectAllEnabled; }
}