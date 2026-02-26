package com.compassfx.models;

import com.compassfx.enums.StepStatus;
import javafx.beans.property.*;
import javafx.scene.Node;

/**
 * Represents a single step in a stepper component
 */
public class StepperItem {

    private final StringProperty title;
    private final StringProperty description;
    private final ObjectProperty<Node> icon;
    private final ObjectProperty<Node> content;
    private final ObjectProperty<StepStatus> status;
    private final BooleanProperty optional;
    private final ObjectProperty<Object> userData;

    public StepperItem(String title) {
        this(title, null);
    }

    public StepperItem(String title, String description) {
        this.title = new SimpleStringProperty(this, "title", title);
        this.description = new SimpleStringProperty(this, "description", description);
        this.icon = new SimpleObjectProperty<>(this, "icon", null);
        this.content = new SimpleObjectProperty<>(this, "content", null);
        this.status = new SimpleObjectProperty<>(this, "status", StepStatus.PENDING);
        this.optional = new SimpleBooleanProperty(this, "optional", false);
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
    }

    // Title
    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    // Description
    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public StringProperty descriptionProperty() { return description; }

    // Icon
    public Node getIcon() { return icon.get(); }
    public void setIcon(Node icon) { this.icon.set(icon); }
    public ObjectProperty<Node> iconProperty() { return icon; }

    // Content
    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    // Status
    public StepStatus getStatus() { return status.get(); }
    public void setStatus(StepStatus status) { this.status.set(status); }
    public ObjectProperty<StepStatus> statusProperty() { return status; }

    // Optional
    public boolean isOptional() { return optional.get(); }
    public void setOptional(boolean optional) { this.optional.set(optional); }
    public BooleanProperty optionalProperty() { return optional; }

    // User Data
    public Object getUserData() { return userData.get(); }
    public void setUserData(Object userData) { this.userData.set(userData); }
    public ObjectProperty<Object> userDataProperty() { return userData; }

    @Override
    public String toString() {
        return title.get();
    }
}
