package com.compassfx.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

/**
 * Represents a menu item in CFXMenu
 */
public class MenuItem {

    private final StringProperty text;
    private final ObjectProperty<Node> graphic;
    private final ObservableList<MenuItem> items;
    private final BooleanProperty disabled;
    private final BooleanProperty separator;
    private final ObjectProperty<KeyCombination> accelerator;
    private final ObjectProperty<EventHandler<ActionEvent>> onAction;
    private final ObjectProperty<Object> userData;

    public MenuItem() {
        this("");
    }

    public MenuItem(String text) {
        this(text, null);
    }

    public MenuItem(String text, Node graphic) {
        this.text = new SimpleStringProperty(this, "text", text);
        this.graphic = new SimpleObjectProperty<>(this, "graphic", graphic);
        this.items = FXCollections.observableArrayList();
        this.disabled = new SimpleBooleanProperty(this, "disabled", false);
        this.separator = new SimpleBooleanProperty(this, "separator", false);
        this.accelerator = new SimpleObjectProperty<>(this, "accelerator", null);
        this.onAction = new SimpleObjectProperty<>(this, "onAction", null);
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
    }

    // Factory methods
    public static MenuItem separator() {
        MenuItem item = new MenuItem();
        item.setSeparator(true);
        return item;
    }

    public static MenuItem withIcon(String text, Node icon) {
        return new MenuItem(text, icon);
    }

    // Helper methods
    public boolean hasSubItems() {
        return !items.isEmpty();
    }

    // Text
    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }
    public StringProperty textProperty() { return text; }

    // Graphic
    public Node getGraphic() { return graphic.get(); }
    public void setGraphic(Node graphic) { this.graphic.set(graphic); }
    public ObjectProperty<Node> graphicProperty() { return graphic; }

    // Items (sub-menu)
    public ObservableList<MenuItem> getItems() { return items; }

    // Disabled
    public boolean isDisabled() { return disabled.get(); }
    public void setDisabled(boolean disabled) { this.disabled.set(disabled); }
    public BooleanProperty disabledProperty() { return disabled; }

    // Separator
    public boolean isSeparator() { return separator.get(); }
    public void setSeparator(boolean separator) { this.separator.set(separator); }
    public BooleanProperty separatorProperty() { return separator; }

    // Accelerator (keyboard shortcut)
    public KeyCombination getAccelerator() { return accelerator.get(); }
    public void setAccelerator(KeyCombination accelerator) { this.accelerator.set(accelerator); }
    public ObjectProperty<KeyCombination> acceleratorProperty() { return accelerator; }

    // Action
    public EventHandler<ActionEvent> getOnAction() { return onAction.get(); }
    public void setOnAction(EventHandler<ActionEvent> handler) { this.onAction.set(handler); }
    public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }

    // User Data
    public Object getUserData() { return userData.get(); }
    public void setUserData(Object userData) { this.userData.set(userData); }
    public ObjectProperty<Object> userDataProperty() { return userData; }

    @Override
    public String toString() {
        return text.get();
    }
}
