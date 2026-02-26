package com.compassfx.controls;

import com.compassfx.enums.BadgeColor;
import com.compassfx.enums.BadgeStyle;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * CompassFX Chip - Beautiful pill-shaped badge component
 * Perfect for tags, labels, status indicators, and counts
 */
public class CFXChip extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-chip";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-chip.css";

    // Properties
    private final StringProperty text;
    private final IntegerProperty value;
    private final IntegerProperty max;
    private final ObjectProperty<BadgeColor> color;
    private final ObjectProperty<BadgeStyle> badgeStyle;
    private final BooleanProperty closeable;

    // UI Components
    private final Label label;
    private final Label closeButton;

    public CFXChip() {
        this("");
    }

    public CFXChip(String text) {
        this.text = new SimpleStringProperty(this, "text", text);
        this.value = new SimpleIntegerProperty(this, "value", -1);
        this.max = new SimpleIntegerProperty(this, "max", 99);
        this.color = new SimpleObjectProperty<>(this, "color", BadgeColor.PRIMARY);
        this.badgeStyle = new SimpleObjectProperty<>(this, "badgeStyle", BadgeStyle.SOLID);
        this.closeable = new SimpleBooleanProperty(this, "closeable", false);

        // Create label
        label = new Label();
        label.getStyleClass().add("chip-label");

        // Create close button
        closeButton = new Label("×");
        closeButton.getStyleClass().add("chip-close");
        closeButton.setVisible(false);
        closeButton.setManaged(false);
        closeButton.setOnMouseClicked(e -> {
            if (getOnClose() != null) {
                getOnClose().handle(null);
            }
        });

        getChildren().addAll(label, closeButton);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.CENTER);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        updateText();
        updateStyleClasses();

        // Listeners
        text.addListener((obs, old, newVal) -> updateText());
        value.addListener((obs, old, newVal) -> updateText());
        max.addListener((obs, old, newVal) -> updateText());
        color.addListener((obs, old, newVal) -> updateStyleClasses());
        badgeStyle.addListener((obs, old, newVal) -> updateStyleClasses());
        closeable.addListener((obs, old, newVal) -> {
            closeButton.setVisible(newVal);
            closeButton.setManaged(newVal);
        });
    }

    private void updateText() {
        String displayText;

        if (text.get() != null && !text.get().isEmpty()) {
            displayText = text.get();
        } else if (value.get() >= 0) {
            int val = value.get();
            int maxVal = max.get();
            displayText = val > maxVal ? maxVal + "+" : String.valueOf(val);
        } else {
            displayText = "";
        }

        label.setText(displayText);
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(color.get().getStyleClass());
        getStyleClass().add(badgeStyle.get().getStyleClass());
    }

    // Event handler property
    private ObjectProperty<javafx.event.EventHandler<javafx.event.ActionEvent>> onClose;

    public javafx.event.EventHandler<javafx.event.ActionEvent> getOnClose() {
        return onClose == null ? null : onClose.get();
    }

    public void setOnClose(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        onCloseProperty().set(handler);
    }

    public ObjectProperty<javafx.event.EventHandler<javafx.event.ActionEvent>> onCloseProperty() {
        if (onClose == null) {
            onClose = new SimpleObjectProperty<>(this, "onClose");
        }
        return onClose;
    }

    // Getters and Setters
    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }
    public StringProperty textProperty() { return text; }

    public int getValue() { return value.get(); }
    public void setValue(int value) { this.value.set(value); }
    public IntegerProperty valueProperty() { return value; }

    public int getMax() { return max.get(); }
    public void setMax(int max) { this.max.set(max); }
    public IntegerProperty maxProperty() { return max; }

    public BadgeColor getColor() { return color.get(); }
    public void setColor(BadgeColor color) { this.color.set(color); }
    public ObjectProperty<BadgeColor> colorProperty() { return color; }

    public BadgeStyle getBadgeStyle() { return badgeStyle.get(); }
    public void setBadgeStyle(BadgeStyle badgeStyle) { this.badgeStyle.set(badgeStyle); }
    public ObjectProperty<BadgeStyle> badgeStyleProperty() { return badgeStyle; }

    public boolean isCloseable() { return closeable.get(); }
    public void setCloseable(boolean closeable) { this.closeable.set(closeable); }
    public BooleanProperty closeableProperty() { return closeable; }
}