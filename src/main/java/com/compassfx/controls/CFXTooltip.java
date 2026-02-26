package com.compassfx.controls;

import com.compassfx.enums.TooltipPosition;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;

/**
 * CompassFX Tooltip - A Material Design inspired tooltip component
 * Supports multiple positions and customizable content
 */
public class CFXTooltip extends Popup {

    private static final String DEFAULT_STYLE_CLASS = "cfx-tooltip";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-tooltip.css";

    // Properties
    private final StringProperty text;
    private final ObjectProperty<TooltipPosition> position;
    private final ObjectProperty<Duration> showDelay;
    private final ObjectProperty<Duration> hideDelay;
    private final BooleanProperty arrow;

    // UI Components
    private final StackPane container;
    private final Label contentLabel;

    // Animation
    private PauseTransition showTimer;
    private PauseTransition hideTimer;
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;

    // Target node
    private Node targetNode;

    /**
     * Creates a CFXTooltip with empty text
     */
    public CFXTooltip() {
        this("");
    }

    /**
     * Creates a CFXTooltip with specified text
     * @param text The tooltip text
     */
    public CFXTooltip(String text) {
        this.text = new SimpleStringProperty(this, "text", text);
        this.position = new SimpleObjectProperty<>(this, "position", TooltipPosition.TOP);
        this.showDelay = new SimpleObjectProperty<>(this, "showDelay", Duration.millis(500));
        this.hideDelay = new SimpleObjectProperty<>(this, "hideDelay", Duration.millis(200));
        this.arrow = new SimpleBooleanProperty(this, "arrow", true);

        this.container = new StackPane();
        this.contentLabel = new Label();

        initialize();
    }

    /**
     * Initialize the tooltip
     */
    private void initialize() {
        // Setup container
        container.getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                container.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("WARNING: CFXTooltip stylesheet not found: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXTooltip stylesheet: " + e.getMessage());
        }

        // Setup content label
        contentLabel.getStyleClass().add("cfx-tooltip-content");
        contentLabel.textProperty().bind(text);
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(250);

        container.getChildren().add(contentLabel);
        getContent().add(container);

        // Setup animations
        setupAnimations();

        // Update style classes
        updateStyleClasses();

        // Add listeners
        position.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        arrow.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Auto hide on window focus lost
        setAutoHide(true);
    }

    /**
     * Setup fade animations
     */
    private void setupAnimations() {
        fadeIn = new FadeTransition(Duration.millis(150), container);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut = new FadeTransition(Duration.millis(100), container);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> hide());
    }

    /**
     * Update CSS style classes
     */
    private void updateStyleClasses() {
        container.getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );

        container.getStyleClass().add(position.get().getStyleClass());

        if (arrow.get()) {
            container.getStyleClass().add("with-arrow");
        }
    }

    /**
     * Install tooltip on a node
     * @param node The node to attach the tooltip to
     */
    public void install(Node node) {
        this.targetNode = node;

        // Show on hover
        node.setOnMouseEntered(e -> {
            if (hideTimer != null) {
                hideTimer.stop();
            }

            showTimer = new PauseTransition(showDelay.get());
            showTimer.setOnFinished(ev -> showTooltip());
            showTimer.play();
        });

        // Hide on exit
        node.setOnMouseExited(e -> {
            if (showTimer != null) {
                showTimer.stop();
            }

            hideTimer = new PauseTransition(hideDelay.get());
            hideTimer.setOnFinished(ev -> hideTooltip());
            hideTimer.play();
        });
    }

    /**
     * Uninstall tooltip from the current node
     */
    public void uninstall() {
        if (targetNode != null) {
            targetNode.setOnMouseEntered(null);
            targetNode.setOnMouseExited(null);
            targetNode = null;
        }

        if (showTimer != null) {
            showTimer.stop();
        }
        if (hideTimer != null) {
            hideTimer.stop();
        }
    }

    /**
     * Show the tooltip
     */
    private void showTooltip() {
        if (targetNode == null || text.get() == null || text.get().isEmpty()) {
            return;
        }

        // Calculate position
        Bounds bounds = targetNode.localToScreen(targetNode.getBoundsInLocal());
        if (bounds == null) {
            return;
        }

        double x = 0;
        double y = 0;
        double offset = 8; // Space between node and tooltip

        // Force layout to get correct size
        container.applyCss();
        container.layout();

        double tooltipWidth = container.prefWidth(-1);
        double tooltipHeight = container.prefHeight(-1);

        switch (position.get()) {
            case TOP:
                x = bounds.getMinX() + (bounds.getWidth() - tooltipWidth) / 2;
                y = bounds.getMinY() - tooltipHeight - offset;
                break;
            case BOTTOM:
                x = bounds.getMinX() + (bounds.getWidth() - tooltipWidth) / 2;
                y = bounds.getMaxY() + offset;
                break;
            case LEFT:
                x = bounds.getMinX() - tooltipWidth - offset;
                y = bounds.getMinY() + (bounds.getHeight() - tooltipHeight) / 2;
                break;
            case RIGHT:
                x = bounds.getMaxX() + offset;
                y = bounds.getMinY() + (bounds.getHeight() - tooltipHeight) / 2;
                break;
        }

        // Show with animation
        container.setOpacity(0);
        show(targetNode, x, y);
        fadeIn.play();
    }

    /**
     * Hide the tooltip
     */
    private void hideTooltip() {
        if (isShowing()) {
            fadeOut.play();
        }
    }

    /**
     * Create and install a tooltip on a node (convenience method)
     * @param node The node to attach tooltip to
     * @param text The tooltip text
     * @return The created tooltip
     */
    public static CFXTooltip install(Node node, String text) {
        CFXTooltip tooltip = new CFXTooltip(text);
        tooltip.install(node);
        return tooltip;
    }

    /**
     * Create and install a tooltip with position
     * @param node The node to attach tooltip to
     * @param text The tooltip text
     * @param position The tooltip position
     * @return The created tooltip
     */
    public static CFXTooltip install(Node node, String text, TooltipPosition position) {
        CFXTooltip tooltip = new CFXTooltip(text);
        tooltip.setPosition(position);
        tooltip.install(node);
        return tooltip;
    }

    // ===== Property Getters and Setters =====

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public TooltipPosition getPosition() {
        return position.get();
    }

    public void setPosition(TooltipPosition position) {
        this.position.set(position);
    }

    public ObjectProperty<TooltipPosition> positionProperty() {
        return position;
    }

    public Duration getShowDelay() {
        return showDelay.get();
    }

    public void setShowDelay(Duration showDelay) {
        this.showDelay.set(showDelay);
    }

    public ObjectProperty<Duration> showDelayProperty() {
        return showDelay;
    }

    public Duration getHideDelay() {
        return hideDelay.get();
    }

    public void setHideDelay(Duration hideDelay) {
        this.hideDelay.set(hideDelay);
    }

    public ObjectProperty<Duration> hideDelayProperty() {
        return hideDelay;
    }

    public boolean isArrow() {
        return arrow.get();
    }

    public void setArrow(boolean arrow) {
        this.arrow.set(arrow);
    }

    public BooleanProperty arrowProperty() {
        return arrow;
    }
}