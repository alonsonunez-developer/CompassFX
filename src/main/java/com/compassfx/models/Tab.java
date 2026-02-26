// ============================================
// Tab.java - Tab Model
// src/main/java/com/compassfx/models/Tab.java
// ============================================
package com.compassfx.models;

import javafx.beans.property.*;
import javafx.scene.Node;

/**
 * Represents a single tab in CFXTabs
 */
public class Tab {

    private final StringProperty text;
    private final ObjectProperty<Node> graphic;
    private final ObjectProperty<Node> content;
    private final BooleanProperty closable;
    private final BooleanProperty disabled;
    private final ObjectProperty<Object> userData;

    public Tab() {
        this("");
    }

    public Tab(String text) {
        this(text, null);
    }

    public Tab(String text, Node content) {
        this.text = new SimpleStringProperty(this, "text", text);
        this.graphic = new SimpleObjectProperty<>(this, "graphic", null);
        this.content = new SimpleObjectProperty<>(this, "content", content);
        this.closable = new SimpleBooleanProperty(this, "closable", false);
        this.disabled = new SimpleBooleanProperty(this, "disabled", false);
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
    }

    // Factory methods
    public static Tab withIcon(String text, Node icon, Node content) {
        Tab tab = new Tab(text, content);
        tab.setGraphic(icon);
        return tab;
    }

    public static Tab closable(String text, Node content) {
        Tab tab = new Tab(text, content);
        tab.setClosable(true);
        return tab;
    }

    // Text
    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }
    public StringProperty textProperty() { return text; }

    // Graphic (icon)
    public Node getGraphic() { return graphic.get(); }
    public void setGraphic(Node graphic) { this.graphic.set(graphic); }
    public ObjectProperty<Node> graphicProperty() { return graphic; }

    // Content
    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    // Closable
    public boolean isClosable() { return closable.get(); }
    public void setClosable(boolean closable) { this.closable.set(closable); }
    public BooleanProperty closableProperty() { return closable; }

    // Disabled
    public boolean isDisabled() { return disabled.get(); }
    public void setDisabled(boolean disabled) { this.disabled.set(disabled); }
    public BooleanProperty disabledProperty() { return disabled; }

    // User Data
    public Object getUserData() { return userData.get(); }
    public void setUserData(Object userData) { this.userData.set(userData); }
    public ObjectProperty<Object> userDataProperty() { return userData; }

    @Override
    public String toString() {
        return text.get();
    }
}