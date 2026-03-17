package com.compassfx.models;

import com.compassfx.enums.FileTreeItemType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Represents an item in the CFXFileTree
 */
public class FileTreeItem {

    private final StringProperty name;
    private final ObjectProperty<FileTreeItemType> type;
    private final ObjectProperty<Node> customIcon;
    private final BooleanProperty expanded;
    private final ObservableList<FileTreeItem> children;
    private final ObjectProperty<Object> userData;

    public FileTreeItem(String name, FileTreeItemType type) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.type = new SimpleObjectProperty<>(this, "type", type);
        this.customIcon = new SimpleObjectProperty<>(this, "customIcon", null);
        this.expanded = new SimpleBooleanProperty(this, "expanded", false);
        this.children = FXCollections.observableArrayList();
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
    }

    // Convenience constructor
    public FileTreeItem(String name) {
        this(name, FileTreeItemType.FILE);
    }

    // Add child
    public void addChild(FileTreeItem child) {
        children.add(child);
    }

    // Check if it's a folder
    public boolean isFolder() {
        return type.get() == FileTreeItemType.FOLDER;
    }

    // Check if it has children
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    // Properties
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public FileTreeItemType getType() { return type.get(); }
    public void setType(FileTreeItemType type) { this.type.set(type); }
    public ObjectProperty<FileTreeItemType> typeProperty() { return type; }

    public Node getCustomIcon() { return customIcon.get(); }
    public void setCustomIcon(Node icon) { this.customIcon.set(icon); }
    public ObjectProperty<Node> customIconProperty() { return customIcon; }

    public boolean isExpanded() { return expanded.get(); }
    public void setExpanded(boolean expanded) { this.expanded.set(expanded); }
    public BooleanProperty expandedProperty() { return expanded; }

    public ObservableList<FileTreeItem> getChildren() { return children; }

    public Object getUserData() { return userData.get(); }
    public void setUserData(Object data) { this.userData.set(data); }
    public ObjectProperty<Object> userDataProperty() { return userData; }
}