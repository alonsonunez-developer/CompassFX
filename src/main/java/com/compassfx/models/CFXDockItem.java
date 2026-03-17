package com.compassfx.models;

import javafx.beans.property.*;
import javafx.scene.Node;

/**
 * Represents an item in the CFXDock
 */
public class CFXDockItem {

    private final StringProperty label;
    private final ObjectProperty<Node> icon;
    private final ObjectProperty<Runnable> onAction;
    private final BooleanProperty badge;
    private final IntegerProperty badgeCount;
    private final ObjectProperty<Object> userData;

    public CFXDockItem(String label, Node icon) {
        this.label = new SimpleStringProperty(this, "label", label);
        this.icon = new SimpleObjectProperty<>(this, "icon", icon);
        this.onAction = new SimpleObjectProperty<>(this, "onAction", null);
        this.badge = new SimpleBooleanProperty(this, "badge", false);
        this.badgeCount = new SimpleIntegerProperty(this, "badgeCount", 0);
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
    }

    public CFXDockItem(String label, Node icon, Runnable onAction) {
        this(label, icon);
        this.onAction.set(onAction);
    }

    // Properties
    public String getLabel() { return label.get(); }
    public void setLabel(String label) { this.label.set(label); }
    public StringProperty labelProperty() { return label; }

    public Node getIcon() { return icon.get(); }
    public void setIcon(Node icon) { this.icon.set(icon); }
    public ObjectProperty<Node> iconProperty() { return icon; }

    public Runnable getOnAction() { return onAction.get(); }
    public void setOnAction(Runnable action) { this.onAction.set(action); }
    public ObjectProperty<Runnable> onActionProperty() { return onAction; }

    public boolean isBadge() { return badge.get(); }
    public void setBadge(boolean badge) { this.badge.set(badge); }
    public BooleanProperty badgeProperty() { return badge; }

    public int getBadgeCount() { return badgeCount.get(); }
    public void setBadgeCount(int count) { this.badgeCount.set(count); }
    public IntegerProperty badgeCountProperty() { return badgeCount; }

    public Object getUserData() { return userData.get(); }
    public void setUserData(Object data) { this.userData.set(data); }
    public ObjectProperty<Object> userDataProperty() { return userData; }
}