package com.compassfx.controls;

import com.compassfx.enums.SpeedDialDirection;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

/**
 * CompassFX SpeedDial - A floating action button with expandable options
 * Supports custom icons and multiple expansion directions
 */
public class CFXSpeedDial extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-speed-dial";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-speed-dial.css";

    // Properties
    private final BooleanProperty open;
    private final ObjectProperty<SpeedDialDirection> direction;
    private final ObjectProperty<Node> mainIcon;
    private final StringProperty mainTooltip;
    private final ObjectProperty<ObservableList<CFXSpeedDialItem>> items;
    private final BooleanProperty animated;
    private final ObjectProperty<Duration> animationDuration;

    // UI Components
    private final Button mainButton;
    private final VBox verticalContainer;
    private final HBox horizontalContainer;
    private final StackPane overlay;

    public CFXSpeedDial() {
        this.open = new SimpleBooleanProperty(this, "open", false);
        this.direction = new SimpleObjectProperty<>(this, "direction", SpeedDialDirection.UP);
        this.mainIcon = new SimpleObjectProperty<>(this, "mainIcon", createDefaultIcon());
        this.mainTooltip = new SimpleStringProperty(this, "mainTooltip", "Actions");
        this.items = new SimpleObjectProperty<>(this, "items", FXCollections.observableArrayList());
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.animationDuration = new SimpleObjectProperty<>(this, "animationDuration", Duration.millis(150));

        // Create UI
        overlay = new StackPane();
        overlay.getStyleClass().add("speed-dial-overlay");
        overlay.setVisible(false);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(false);

        verticalContainer = new VBox(10);
        verticalContainer.setAlignment(Pos.CENTER);
        verticalContainer.setPickOnBounds(false);
        verticalContainer.setVisible(false);
        verticalContainer.setManaged(false);

        horizontalContainer = new HBox(10);
        horizontalContainer.setAlignment(Pos.CENTER);
        horizontalContainer.setPickOnBounds(false);
        horizontalContainer.setVisible(false);
        horizontalContainer.setManaged(false);

        mainButton = new Button();
        mainButton.getStyleClass().add("speed-dial-main");

        getChildren().addAll(overlay, verticalContainer, horizontalContainer);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.CENTER);
        setPickOnBounds(false);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        setupMainButton();
        setupListeners();
        updateItems();
    }

    private void setupMainButton() {
        mainButton.setGraphic(mainIcon.get());

        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(mainTooltip);
        mainButton.setTooltip(tooltip);

        mainButton.setOnAction(e -> toggle());

        // Update icon when property changes
        mainIcon.addListener((obs, old, newVal) -> {
            mainButton.setGraphic(newVal);
        });
    }

    private void setupListeners() {
        // Open/close handling
        open.addListener((obs, old, newVal) -> {
            if (newVal) {
                openDial();
            } else {
                closeDial();
            }
        });

        // Items changes
        items.get().addListener((javafx.collections.ListChangeListener<CFXSpeedDialItem>) c -> {
            updateItems();
        });

        // Direction changes
        direction.addListener((obs, old, newVal) -> {
            updateItems();
        });

        // Overlay click to close
        overlay.setOnMouseClicked(e -> close());
    }

    private void updateItems() {
        verticalContainer.getChildren().clear();
        horizontalContainer.getChildren().clear();

        if (items.get().isEmpty()) {
            return;
        }

        SpeedDialDirection dir = direction.get();

        if (dir == SpeedDialDirection.UP) {
            // Items go UP - items appear ABOVE the main button
            for (CFXSpeedDialItem item : items.get()) {
                verticalContainer.getChildren().add(createItemButton(item));
            }
            verticalContainer.getChildren().add(mainButton);
            verticalContainer.setVisible(true);
            verticalContainer.setManaged(true);
            horizontalContainer.setVisible(false);
            horizontalContainer.setManaged(false);

            // Position main button at bottom of vertical container
            StackPane.setAlignment(verticalContainer, Pos.CENTER);

        } else if (dir == SpeedDialDirection.DOWN) {
            // Items go DOWN - items appear BELOW the main button
            verticalContainer.getChildren().add(mainButton);
            for (CFXSpeedDialItem item : items.get()) {
                verticalContainer.getChildren().add(createItemButton(item));
            }
            verticalContainer.setVisible(true);
            verticalContainer.setManaged(true);
            horizontalContainer.setVisible(false);
            horizontalContainer.setManaged(false);

            // Position main button at top of vertical container
            StackPane.setAlignment(verticalContainer, Pos.CENTER);

        } else if (dir == SpeedDialDirection.LEFT) {
            // Items go LEFT - items appear to the LEFT of main button
            for (CFXSpeedDialItem item : items.get()) {
                horizontalContainer.getChildren().add(createItemButton(item));
            }
            horizontalContainer.getChildren().add(mainButton);
            horizontalContainer.setVisible(true);
            horizontalContainer.setManaged(true);
            verticalContainer.setVisible(false);
            verticalContainer.setManaged(false);

            // Position main button at right of horizontal container
            StackPane.setAlignment(horizontalContainer, Pos.CENTER);

        } else { // RIGHT
            // Items go RIGHT - items appear to the RIGHT of main button
            horizontalContainer.getChildren().add(mainButton);
            for (CFXSpeedDialItem item : items.get()) {
                horizontalContainer.getChildren().add(createItemButton(item));
            }
            horizontalContainer.setVisible(true);
            horizontalContainer.setManaged(true);
            verticalContainer.setVisible(false);
            verticalContainer.setManaged(false);

            // Position main button at left of horizontal container
            StackPane.setAlignment(horizontalContainer, Pos.CENTER);
        }
    }

    private Button createItemButton(CFXSpeedDialItem item) {
        Button button = new Button();
        button.getStyleClass().add("speed-dial-item");

        if (item.getIcon() != null) {
            button.setGraphic(item.getIcon());
        }

        if (item.getTooltipText() != null && !item.getTooltipText().isEmpty()) {
            Tooltip tooltip = new Tooltip(item.getTooltipText());
            button.setTooltip(tooltip);
        }

        button.setOnAction(e -> {
            if (item.getOnAction() != null) {
                item.getOnAction().handle(e);
            }
            close();
        });

        // Initially hidden
        button.setVisible(false);
        button.setScaleX(0);
        button.setScaleY(0);

        return button;
    }

    private void openDial() {
        overlay.setVisible(true);
        overlay.setMouseTransparent(false);

        if (animated.get()) {
            animateOpen();
        } else {
            showItems();
            overlay.setOpacity(0.3);
        }

        // Rotate main button icon
        rotateMainButton(45);
    }

    private void closeDial() {
        if (animated.get()) {
            animateClose();
        } else {
            hideItems();
            overlay.setVisible(false);
        }

        // Rotate main button back
        rotateMainButton(0);
    }

    private void animateOpen() {
        // Fade in overlay
        FadeTransition overlayFade = new FadeTransition(animationDuration.get(), overlay);
        overlayFade.setToValue(0.3);
        overlayFade.play();

        // Animate items from the active container (but not the main button)
        javafx.scene.layout.Pane activeContainer = verticalContainer.isVisible() ? verticalContainer : horizontalContainer;
        int index = 0;

        for (javafx.scene.Node node : activeContainer.getChildren()) {
            if (node instanceof Button && node != mainButton) {
                animateItemOpen((Button) node, index++);
            }
        }
    }

    private void animateItemOpen(Button button, int index) {
        button.setVisible(true);

        Duration delay = animationDuration.get().multiply(index * 0.3);

        ScaleTransition scale = new ScaleTransition(animationDuration.get(), button);
        scale.setDelay(delay);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);

        FadeTransition fade = new FadeTransition(animationDuration.get(), button);
        fade.setDelay(delay);
        fade.setFromValue(0);
        fade.setToValue(1);

        scale.play();
        fade.play();
    }

    private void animateClose() {
        // Fade out overlay
        FadeTransition overlayFade = new FadeTransition(animationDuration.get(), overlay);
        overlayFade.setToValue(0);
        overlayFade.setOnFinished(e -> overlay.setVisible(false));
        overlayFade.play();

        // Animate items from the active container (but not the main button)
        javafx.scene.layout.Pane activeContainer = verticalContainer.isVisible() ? verticalContainer : horizontalContainer;

        for (javafx.scene.Node node : activeContainer.getChildren()) {
            if (node instanceof Button && node != mainButton) {
                animateItemClose((Button) node);
            }
        }
    }

    private void animateItemClose(Button button) {
        ScaleTransition scale = new ScaleTransition(animationDuration.get().divide(2), button);
        scale.setToX(0);
        scale.setToY(0);
        scale.setOnFinished(e -> button.setVisible(false));
        scale.play();
    }

    private void showItems() {
        javafx.scene.layout.Pane activeContainer = verticalContainer.isVisible() ? verticalContainer : horizontalContainer;

        for (javafx.scene.Node node : activeContainer.getChildren()) {
            if (node instanceof Button && node != mainButton) {
                Button btn = (Button) node;
                btn.setVisible(true);
                btn.setScaleX(1);
                btn.setScaleY(1);
            }
        }
    }

    private void hideItems() {
        for (javafx.scene.Node node : verticalContainer.getChildren()) {
            if (node instanceof Button && node != mainButton) {
                Button btn = (Button) node;
                btn.setVisible(false);
                btn.setScaleX(0);
                btn.setScaleY(0);
            }
        }

        for (javafx.scene.Node node : horizontalContainer.getChildren()) {
            if (node instanceof Button && node != mainButton) {
                Button btn = (Button) node;
                btn.setVisible(false);
                btn.setScaleX(0);
                btn.setScaleY(0);
            }
        }
    }

    private void rotateMainButton(double angle) {
        if (animated.get()) {
            RotateTransition rotate = new RotateTransition(animationDuration.get(), mainButton);
            rotate.setToAngle(angle);
            rotate.play();
        } else {
            mainButton.setRotate(angle);
        }
    }

    private Node createDefaultIcon() {
        SVGPath plus = new SVGPath();
        plus.setContent("M 19 13 h -6 v 6 h -2 v -6 H 5 v -2 h 6 V 5 h 2 v 6 h 6 z");
        plus.setFill(Color.WHITE);
        return plus;
    }

    // Public methods
    public void open() {
        setOpen(true);
    }

    public void close() {
        setOpen(false);
    }

    public void toggle() {
        setOpen(!isOpen());
    }

    // Getters and Setters
    public boolean isOpen() { return open.get(); }
    public void setOpen(boolean open) { this.open.set(open); }
    public BooleanProperty openProperty() { return open; }

    public SpeedDialDirection getDirection() { return direction.get(); }
    public void setDirection(SpeedDialDirection direction) { this.direction.set(direction); }
    public ObjectProperty<SpeedDialDirection> directionProperty() { return direction; }

    public Node getMainIcon() { return mainIcon.get(); }
    public void setMainIcon(Node icon) { this.mainIcon.set(icon); }
    public ObjectProperty<Node> mainIconProperty() { return mainIcon; }

    public String getMainTooltip() { return mainTooltip.get(); }
    public void setMainTooltip(String tooltip) { this.mainTooltip.set(tooltip); }
    public StringProperty mainTooltipProperty() { return mainTooltip; }

    public ObservableList<CFXSpeedDialItem> getItems() { return items.get(); }
    public void setItems(ObservableList<CFXSpeedDialItem> items) { this.items.set(items); }
    public ObjectProperty<ObservableList<CFXSpeedDialItem>> itemsProperty() { return items; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public Duration getAnimationDuration() { return animationDuration.get(); }
    public void setAnimationDuration(Duration duration) { this.animationDuration.set(duration); }
    public ObjectProperty<Duration> animationDurationProperty() { return animationDuration; }
}