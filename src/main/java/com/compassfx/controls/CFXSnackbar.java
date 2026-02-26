package com.compassfx.controls;

import com.compassfx.enums.SnackbarPosition;
import com.compassfx.enums.SnackbarType;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;

/**
 * CompassFX Snackbar - A Material Design inspired snackbar/toast notification
 * Supports auto-dismiss, actions, and multiple positions
 */
public class CFXSnackbar extends Popup {

    private static final String DEFAULT_STYLE_CLASS = "cfx-snackbar";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-snackbar.css";

    // Properties
    private final StringProperty message;
    private final ObjectProperty<SnackbarPosition> position;
    private final ObjectProperty<SnackbarType> type;
    private final ObjectProperty<Duration> duration;
    private final StringProperty actionText;
    private final ObjectProperty<Runnable> onAction;

    // UI Components
    private final HBox container;
    private final Label messageLabel;
    private final Button actionButton;
    private final Button closeButton;

    // Animation
    private Timeline autoHideTimer;
    private TranslateTransition slideIn;
    private TranslateTransition slideOut;
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;

    /**
     * Creates a CFXSnackbar with a message
     * @param message The notification message
     */
    public CFXSnackbar(String message) {
        this.message = new SimpleStringProperty(this, "message", message);
        this.position = new SimpleObjectProperty<>(this, "position", SnackbarPosition.BOTTOM_CENTER);
        this.type = new SimpleObjectProperty<>(this, "type", SnackbarType.DEFAULT);
        this.duration = new SimpleObjectProperty<>(this, "duration", Duration.seconds(3));
        this.actionText = new SimpleStringProperty(this, "actionText", "");
        this.onAction = new SimpleObjectProperty<>(this, "onAction", null);

        this.container = new HBox(12);
        this.messageLabel = new Label();
        this.actionButton = new Button();
        this.closeButton = new Button("✕");

        initialize();
    }

    /**
     * Initialize the snackbar
     */
    private void initialize() {
        // Setup container
        container.getStyleClass().add(DEFAULT_STYLE_CLASS);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(12, 16, 12, 16));
        container.setMinHeight(48);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                container.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXSnackbar stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXSnackbar stylesheet: " + e.getMessage());
        }

        // Setup message label
        messageLabel.getStyleClass().add("cfx-snackbar-message");
        messageLabel.textProperty().bind(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(400);

        // Setup action button
        actionButton.getStyleClass().add("cfx-snackbar-action");
        actionButton.textProperty().bind(actionText);
        actionButton.managedProperty().bind(actionButton.visibleProperty());
        actionButton.setVisible(false);
        actionButton.setOnAction(e -> {
            if (onAction.get() != null) {
                onAction.get().run();
            }
            hide();
        });

        // Setup close button
        closeButton.getStyleClass().add("cfx-snackbar-close");
        closeButton.setOnAction(e -> hide());

        // Add spacer
        StackPane spacer = new StackPane();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        container.getChildren().addAll(messageLabel, spacer, actionButton, closeButton);
        getContent().add(container);

        // Update style classes
        updateStyleClasses();

        // Add listeners
        position.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        type.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        actionText.addListener((obs, oldVal, newVal) -> {
            actionButton.setVisible(newVal != null && !newVal.isEmpty());
        });

        // Auto hide
        setAutoHide(false);

        // Setup animations
        setupAnimations();
    }

    /**
     * Setup animations
     */
    private void setupAnimations() {
        // Fade animations
        fadeIn = new FadeTransition(Duration.millis(200), container);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut = new FadeTransition(Duration.millis(150), container);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> super.hide());

        // Slide animations
        slideIn = new TranslateTransition(Duration.millis(250), container);
        slideOut = new TranslateTransition(Duration.millis(200), container);
        slideOut.setOnFinished(e -> super.hide());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        container.getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );

        container.getStyleClass().add(position.get().getStyleClass());
        container.getStyleClass().add(type.get().getStyleClass());
    }

    /**
     * Show the snackbar on a scene
     * @param scene The scene to show the snackbar on
     */
    public void show(Scene scene) {
        if (scene == null || scene.getWindow() == null) {
            System.err.println("ERROR: Scene or window is null");
            return;
        }

        // Force layout to get correct size
        container.applyCss();
        container.layout();

        double snackbarWidth = Math.max(288, container.prefWidth(-1));
        double snackbarHeight = Math.max(48, container.prefHeight(-1));

        double windowX = scene.getWindow().getX();
        double windowY = scene.getWindow().getY();
        double windowWidth = scene.getWindow().getWidth();
        double windowHeight = scene.getWindow().getHeight();

        double x = 0;
        double y = 0;
        double margin = 20;

        // Calculate x position based on horizontal alignment
        switch (position.get()) {
            case TOP_LEFT:
            case BOTTOM_LEFT:
                x = windowX + margin;
                break;
            case TOP_CENTER:
            case BOTTOM_CENTER:
                x = windowX + (windowWidth - snackbarWidth) / 2;
                break;
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
                x = windowX + windowWidth - snackbarWidth - margin;
                break;
        }

        // Calculate y position and animation based on vertical alignment
        boolean isTop = position.get().name().startsWith("TOP");

        if (isTop) {
            y = windowY + margin + 30; // Account for window title bar
            slideIn.setFromY(-snackbarHeight - margin);
            slideIn.setToY(0);
            slideOut.setToY(-snackbarHeight - margin);
        } else {
            y = windowY + windowHeight - snackbarHeight - margin - 10;
            slideIn.setFromY(snackbarHeight + margin);
            slideIn.setToY(0);
            slideOut.setToY(snackbarHeight + margin);
        }

        // Set initial state
        container.setOpacity(0);
        container.setTranslateY(slideIn.getFromY());

        // Show popup
        super.show(scene.getWindow(), x, y);

        // Animate in
        ParallelTransition showAnimation = new ParallelTransition(fadeIn, slideIn);
        showAnimation.play();

        // Auto hide timer
        if (duration.get() != null && !duration.get().equals(Duration.INDEFINITE)) {
            if (autoHideTimer != null) {
                autoHideTimer.stop();
            }
            autoHideTimer = new Timeline(
                    new KeyFrame(duration.get(), e -> hide())
            );
            autoHideTimer.play();
        }
    }

    /**
     * Hide the snackbar with animation
     */
    @Override
    public void hide() {
        if (autoHideTimer != null) {
            autoHideTimer.stop();
        }

        ParallelTransition hideAnimation = new ParallelTransition(fadeOut, slideOut);
        hideAnimation.play();
    }

    // ===== Builder Methods =====

    public CFXSnackbar withAction(String text, Runnable action) {
        setActionText(text);
        setOnAction(action);
        return this;
    }

    public CFXSnackbar withPosition(SnackbarPosition position) {
        setPosition(position);
        return this;
    }

    public CFXSnackbar withType(SnackbarType type) {
        setType(type);
        return this;
    }

    public CFXSnackbar withDuration(Duration duration) {
        setDuration(duration);
        return this;
    }

    // ===== Static Factory Methods =====

    public static CFXSnackbar success(String message) {
        return new CFXSnackbar(message).withType(SnackbarType.SUCCESS);
    }

    public static CFXSnackbar error(String message) {
        return new CFXSnackbar(message).withType(SnackbarType.ERROR);
    }

    public static CFXSnackbar warning(String message) {
        return new CFXSnackbar(message).withType(SnackbarType.WARNING);
    }

    public static CFXSnackbar info(String message) {
        return new CFXSnackbar(message).withType(SnackbarType.INFO);
    }

    // ===== Property Getters and Setters =====

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public SnackbarPosition getPosition() {
        return position.get();
    }

    public void setPosition(SnackbarPosition position) {
        this.position.set(position);
    }

    public ObjectProperty<SnackbarPosition> positionProperty() {
        return position;
    }

    public SnackbarType getType() {
        return type.get();
    }

    public void setType(SnackbarType type) {
        this.type.set(type);
    }

    public ObjectProperty<SnackbarType> typeProperty() {
        return type;
    }

    public Duration getDuration() {
        return duration.get();
    }

    public void setDuration(Duration duration) {
        this.duration.set(duration);
    }

    public ObjectProperty<Duration> durationProperty() {
        return duration;
    }

    public String getActionText() {
        return actionText.get();
    }

    public void setActionText(String actionText) {
        this.actionText.set(actionText);
    }

    public StringProperty actionTextProperty() {
        return actionText;
    }

    public Runnable getOnAction() {
        return onAction.get();
    }

    public void setOnAction(Runnable onAction) {
        this.onAction.set(onAction);
    }

    public ObjectProperty<Runnable> onActionProperty() {
        return onAction;
    }
}