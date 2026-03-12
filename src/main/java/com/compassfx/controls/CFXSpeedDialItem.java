package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * Speed Dial Item - represents a single action in the speed dial
 */
public class CFXSpeedDialItem {

    private final StringProperty text;
    private final ObjectProperty<Node> icon;
    private final StringProperty tooltipText;
    private ObjectProperty<EventHandler<ActionEvent>> onAction;

    public CFXSpeedDialItem() {
        this("", null);
    }

    public CFXSpeedDialItem(String text) {
        this(text, null);
    }

    public CFXSpeedDialItem(String text, Node icon) {
        this.text = new SimpleStringProperty(this, "text", text);
        this.icon = new SimpleObjectProperty<>(this, "icon", icon);
        this.tooltipText = new SimpleStringProperty(this, "tooltipText", text);
    }

    // Text
    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }
    public StringProperty textProperty() { return text; }

    // Icon
    public Node getIcon() { return icon.get(); }
    public void setIcon(Node icon) { this.icon.set(icon); }
    public ObjectProperty<Node> iconProperty() { return icon; }

    // Tooltip
    public String getTooltipText() { return tooltipText.get(); }
    public void setTooltipText(String tooltipText) { this.tooltipText.set(tooltipText); }
    public StringProperty tooltipTextProperty() { return tooltipText; }

    // Action
    public EventHandler<ActionEvent> getOnAction() {
        return onAction == null ? null : onAction.get();
    }
    public void setOnAction(EventHandler<ActionEvent> handler) {
        if (onAction == null) {
            onAction = new SimpleObjectProperty<>(this, "onAction");
        }
        onAction.set(handler);
    }
    public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
        if (onAction == null) {
            onAction = new SimpleObjectProperty<>(this, "onAction");
        }
        return onAction;
    }
}