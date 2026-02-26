package com.compassfx.models;

import javafx.beans.property.*;
import javafx.scene.Node;

/**
 * Represents a single item in a breadcrumb navigation
 */
public class BreadcrumbItem {

    private final StringProperty text;
    private final ObjectProperty<Node> icon;
    private final ObjectProperty<Object> userData;
    private final BooleanProperty disabled;

    public BreadcrumbItem(String text) {
        this(text, null);
    }

    public BreadcrumbItem(String text, Node icon) {
        this.text = new SimpleStringProperty(this, "text", text);
        this.icon = new SimpleObjectProperty<>(this, "icon", icon);
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
        this.disabled = new SimpleBooleanProperty(this, "disabled", false);
    }

    // Text
    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }
    public StringProperty textProperty() { return text; }

    // Icon
    public Node getIcon() { return icon.get(); }
    public void setIcon(Node icon) { this.icon.set(icon); }
    public ObjectProperty<Node> iconProperty() { return icon; }

    // User Data (for storing custom data like routes, IDs, etc.)
    public Object getUserData() { return userData.get(); }
    public void setUserData(Object userData) { this.userData.set(userData); }
    public ObjectProperty<Object> userDataProperty() { return userData; }

    // Disabled
    public boolean isDisabled() { return disabled.get(); }
    public void setDisabled(boolean disabled) { this.disabled.set(disabled); }
    public BooleanProperty disabledProperty() { return disabled; }

    @Override
    public String toString() {
        return text.get();
    }
}
