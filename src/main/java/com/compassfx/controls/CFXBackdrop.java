package com.compassfx.controls;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * CompassFX Backdrop - A backdrop overlay component
 * Shows content that slides down from top with overlay behind
 * Perfect for filters, additional options, search panels, etc.
 */
public class CFXBackdrop extends Region {

    private static final String DEFAULT_STYLE_CLASS = "cfx-backdrop";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-backdrop.css";

    // Properties
    private final BooleanProperty revealed;
    private final ObjectProperty<Node> frontContent;
    private final ObjectProperty<Node> backContent;
    private final DoubleProperty backHeight;
    private final BooleanProperty closeOnClickOutside;
    private final BooleanProperty animated;
    private final ObjectProperty<Duration> animationDuration;

    // Event handlers
    private ObjectProperty<EventHandler<ActionEvent>> onReveal;
    private ObjectProperty<EventHandler<ActionEvent>> onConceal;

    // UI Components
    private final StackPane container;
    private final StackPane frontLayer;
    private final StackPane backLayer;
    private final Region overlay;

    public CFXBackdrop() {
        this.revealed = new SimpleBooleanProperty(this, "revealed", false);
        this.frontContent = new SimpleObjectProperty<>(this, "frontContent", null);
        this.backContent = new SimpleObjectProperty<>(this, "backContent", null);
        this.backHeight = new SimpleDoubleProperty(this, "backHeight", 300);
        this.closeOnClickOutside = new SimpleBooleanProperty(this, "closeOnClickOutside", true);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.animationDuration = new SimpleObjectProperty<>(this, "animationDuration", Duration.millis(250));

        // Create UI components
        container = new StackPane();
        container.getStyleClass().add("backdrop-container");

        backLayer = new StackPane();
        backLayer.getStyleClass().add("backdrop-back");
        backLayer.setVisible(false);
        backLayer.setManaged(false);

        overlay = new Region();
        overlay.getStyleClass().add("backdrop-overlay");
        overlay.setVisible(false);
        overlay.setOpacity(0);

        frontLayer = new StackPane();
        frontLayer.getStyleClass().add("backdrop-front");

        container.getChildren().addAll(backLayer, overlay, frontLayer);
        getChildren().add(container);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // Content bindings
        frontContent.addListener((obs, old, newVal) -> {
            frontLayer.getChildren().clear();
            if (newVal != null) {
                frontLayer.getChildren().add(newVal);
            }
        });

        backContent.addListener((obs, old, newVal) -> {
            backLayer.getChildren().clear();
            if (newVal != null) {
                backLayer.getChildren().add(newVal);
            }
        });

        // Reveal/conceal handling
        revealed.addListener((obs, old, newVal) -> {
            if (newVal) {
                doReveal();
            } else {
                doConceal();
            }
        });

        // Overlay click handling
        overlay.setOnMouseClicked(e -> {
            if (closeOnClickOutside.get()) {
                setRevealed(false);
            }
        });

        // Front layer click to toggle
        frontLayer.setOnMouseClicked(e -> {
            if (!revealed.get()) {
                setRevealed(true);
            }
        });
    }

    @Override
    protected void layoutChildren() {
        double width = getWidth();
        double height = getHeight();

        // Layout container to fill entire area
        container.resizeRelocate(0, 0, width, height);

        // Layout overlay
        overlay.resizeRelocate(0, 0, width, height);

        // Layout back layer
        double backH = backHeight.get();
        backLayer.resizeRelocate(0, 0, width, backH);

        // Layout front layer below back layer
        frontLayer.resizeRelocate(0, 0, width, height);
    }

    private void doReveal() {
        backLayer.setVisible(true);
        backLayer.setManaged(true);
        overlay.setVisible(true);

        backLayer.toFront();
        overlay.toFront();
        frontLayer.toFront();

        if (animated.get()) {
            animateReveal();
        } else {
            backLayer.setTranslateY(0);
            frontLayer.setTranslateY(backHeight.get());
            overlay.setOpacity(0.5);
        }

        if (onReveal.get() != null) {
            onReveal.get().handle(new ActionEvent());
        }
    }

    private void doConceal() {
        if (animated.get()) {
            animateConceal();
        } else {
            backLayer.setVisible(false);
            backLayer.setManaged(false);
            overlay.setVisible(false);
            backLayer.setTranslateY(-backHeight.get());
            frontLayer.setTranslateY(0);
        }

        if (onConceal.get() != null) {
            onConceal.get().handle(new ActionEvent());
        }
    }

    private void animateReveal() {
        // Set initial positions
        backLayer.setTranslateY(-backHeight.get());
        frontLayer.setTranslateY(0);
        overlay.setOpacity(0);

        // Animate back layer sliding down
        TranslateTransition backSlide = new TranslateTransition(animationDuration.get(), backLayer);
        backSlide.setToY(0);

        // Animate front layer sliding down
        TranslateTransition frontSlide = new TranslateTransition(animationDuration.get(), frontLayer);
        frontSlide.setToY(backHeight.get());

        // Fade in overlay
        FadeTransition overlayFade = new FadeTransition(animationDuration.get(), overlay);
        overlayFade.setToValue(0.5);

        backSlide.play();
        frontSlide.play();
        overlayFade.play();
    }

    private void animateConceal() {
        // Animate back layer sliding up
        TranslateTransition backSlide = new TranslateTransition(animationDuration.get(), backLayer);
        backSlide.setToY(-backHeight.get());

        // Animate front layer sliding up
        TranslateTransition frontSlide = new TranslateTransition(animationDuration.get(), frontLayer);
        frontSlide.setToY(0);

        // Fade out overlay
        FadeTransition overlayFade = new FadeTransition(animationDuration.get(), overlay);
        overlayFade.setToValue(0);

        backSlide.setOnFinished(e -> {
            backLayer.setVisible(false);
            backLayer.setManaged(false);
            overlay.setVisible(false);
        });

        backSlide.play();
        frontSlide.play();
        overlayFade.play();
    }

    // Public methods
    public void reveal() {
        setRevealed(true);
    }

    public void conceal() {
        setRevealed(false);
    }

    public void toggle() {
        setRevealed(!isRevealed());
    }

    // Getters and Setters
    public boolean isRevealed() { return revealed.get(); }
    public void setRevealed(boolean revealed) { this.revealed.set(revealed); }
    public BooleanProperty revealedProperty() { return revealed; }

    public Node getFrontContent() { return frontContent.get(); }
    public void setFrontContent(Node content) { this.frontContent.set(content); }
    public ObjectProperty<Node> frontContentProperty() { return frontContent; }

    public Node getBackContent() { return backContent.get(); }
    public void setBackContent(Node content) { this.backContent.set(content); }
    public ObjectProperty<Node> backContentProperty() { return backContent; }

    public double getBackHeight() { return backHeight.get(); }
    public void setBackHeight(double height) { this.backHeight.set(height); }
    public DoubleProperty backHeightProperty() { return backHeight; }

    public boolean isCloseOnClickOutside() { return closeOnClickOutside.get(); }
    public void setCloseOnClickOutside(boolean close) { this.closeOnClickOutside.set(close); }
    public BooleanProperty closeOnClickOutsideProperty() { return closeOnClickOutside; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public Duration getAnimationDuration() { return animationDuration.get(); }
    public void setAnimationDuration(Duration duration) { this.animationDuration.set(duration); }
    public ObjectProperty<Duration> animationDurationProperty() { return animationDuration; }

    public EventHandler<ActionEvent> getOnReveal() { return onReveal == null ? null : onReveal.get(); }
    public void setOnReveal(EventHandler<ActionEvent> handler) {
        if (onReveal == null) {
            onReveal = new SimpleObjectProperty<>(this, "onReveal");
        }
        onReveal.set(handler);
    }
    public ObjectProperty<EventHandler<ActionEvent>> onRevealProperty() {
        if (onReveal == null) {
            onReveal = new SimpleObjectProperty<>(this, "onReveal");
        }
        return onReveal;
    }

    public EventHandler<ActionEvent> getOnConceal() { return onConceal == null ? null : onConceal.get(); }
    public void setOnConceal(EventHandler<ActionEvent> handler) {
        if (onConceal == null) {
            onConceal = new SimpleObjectProperty<>(this, "onConceal");
        }
        onConceal.set(handler);
    }
    public ObjectProperty<EventHandler<ActionEvent>> onConcealProperty() {
        if (onConceal == null) {
            onConceal = new SimpleObjectProperty<>(this, "onConceal");
        }
        return onConceal;
    }
}