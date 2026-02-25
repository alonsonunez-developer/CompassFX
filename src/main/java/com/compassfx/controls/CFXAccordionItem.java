package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.scene.Node;

/**
 * Represents a single item in a CFXAccordion
 */
public class CFXAccordionItem {

    private final StringProperty title;
    private final ObjectProperty<Node> content;
    private final ObjectProperty<Node> icon;
    private final BooleanProperty expanded;
    private final BooleanProperty disabled;

    public CFXAccordionItem() {
        this("", null);
    }

    public CFXAccordionItem(String title) {
        this(title, null);
    }

    public CFXAccordionItem(String title, Node content) {
        this.title = new SimpleStringProperty(this, "title", title);
        this.content = new SimpleObjectProperty<>(this, "content", content);
        this.icon = new SimpleObjectProperty<>(this, "icon", null);
        this.expanded = new SimpleBooleanProperty(this, "expanded", false);
        this.disabled = new SimpleBooleanProperty(this, "disabled", false);
    }

    // Title
    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    // Content
    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    // Icon
    public Node getIcon() { return icon.get(); }
    public void setIcon(Node icon) { this.icon.set(icon); }
    public ObjectProperty<Node> iconProperty() { return icon; }

    // Expanded
    public boolean isExpanded() { return expanded.get(); }
    public void setExpanded(boolean expanded) { this.expanded.set(expanded); }
    public BooleanProperty expandedProperty() { return expanded; }

    // Disabled
    public boolean isDisabled() { return disabled.get(); }
    public void setDisabled(boolean disabled) { this.disabled.set(disabled); }
    public BooleanProperty disabledProperty() { return disabled; }

    // Toggle expanded state
    public void toggle() {
        if (!isDisabled()) {
            setExpanded(!isExpanded());
        }
    }
}
