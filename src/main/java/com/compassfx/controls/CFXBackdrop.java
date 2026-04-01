package com.compassfx.controls;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * CompassFX Backdrop - A backdrop overlay component.
 *
 * Layout strategy:
 *  - The component itself grows vertically when revealed (backHeight is added
 *    to its pref/min height), so the surrounding VBox/parent allocates real
 *    space and never clips anything.
 *  - The back layer sits at y=0, always laid out with its full backHeight.
 *  - The front layer sits immediately below the back layer (y = currentOffset),
 *    where currentOffset animates from 0 → backHeight on reveal and back to 0
 *    on conceal.  Because we drive this through layoutChildren() every frame,
 *    no TranslateY is involved and the parent's clip is never violated.
 *  - A semi-transparent overlay is placed over the front layer only.
 */
public class CFXBackdrop extends Region {

    private static final String DEFAULT_STYLE_CLASS = "cfx-backdrop";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-backdrop.css";

    // ── Properties ──────────────────────────────────────────────────────────
    private final BooleanProperty revealed;
    private final ObjectProperty<Node> frontContent;
    private final ObjectProperty<Node> backContent;
    private final DoubleProperty backHeight;
    private final BooleanProperty closeOnClickOutside;
    private final BooleanProperty animated;
    private final ObjectProperty<Duration> animationDuration;

    // Event handlers (lazily initialised)
    private ObjectProperty<EventHandler<ActionEvent>> onReveal;
    private ObjectProperty<EventHandler<ActionEvent>> onConceal;

    // ── Internal layout state ────────────────────────────────────────────────
    /**
     * How many pixels the front layer is currently pushed down.
     * Drives layoutChildren() on every frame during animation so the parent
     * always sees the correct preferred height and never clips.
     */
    private final DoubleProperty currentOffset =
            new SimpleDoubleProperty(this, "currentOffset", 0) {
                @Override
                protected void invalidated() {
                    requestLayout();   // re-layout every animation tick
                }
            };

    // ── UI Components ────────────────────────────────────────────────────────
    private final StackPane backLayer;
    private final StackPane frontLayer;
    private final Region overlay;

    // Active animation (kept so we can stop it before starting a new one)
    private Timeline activeTimeline;

    // ── Constructor ──────────────────────────────────────────────────────────
    public CFXBackdrop() {
        this.revealed            = new SimpleBooleanProperty(this, "revealed", false);
        this.frontContent        = new SimpleObjectProperty<>(this, "frontContent", null);
        this.backContent         = new SimpleObjectProperty<>(this, "backContent", null);
        this.backHeight          = new SimpleDoubleProperty(this, "backHeight", 300);
        this.closeOnClickOutside = new SimpleBooleanProperty(this, "closeOnClickOutside", true);
        this.animated            = new SimpleBooleanProperty(this, "animated", true);
        this.animationDuration   = new SimpleObjectProperty<>(this, "animationDuration", Duration.millis(250));

        backLayer = new StackPane();
        backLayer.getStyleClass().add("backdrop-back");
        backLayer.setVisible(false);
        // Allow back layer to be interactive by consuming mouse events
        backLayer.setPickOnBounds(true);

        overlay = new Region();
        overlay.getStyleClass().add("backdrop-overlay");
        overlay.setVisible(false);
        overlay.setOpacity(0);
        overlay.setPickOnBounds(true);

        frontLayer = new StackPane();
        frontLayer.getStyleClass().add("backdrop-front");

        // When revealed: back layer is interactive and on top of overlay
        // The order ensures back layer receives clicks first when visible
        getChildren().addAll(frontLayer, overlay, backLayer);

        initialize();
    }

    // ── Initialisation ───────────────────────────────────────────────────────
    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) getStylesheets().add(cssUrl.toExternalForm());
        } catch (Exception e) {
            System.err.println("CFXBackdrop: could not load stylesheet – " + e.getMessage());
        }

        frontContent.addListener((obs, old, node) -> {
            frontLayer.getChildren().clear();
            if (node != null) frontLayer.getChildren().add(node);
        });

        backContent.addListener((obs, old, node) -> {
            backLayer.getChildren().clear();
            if (node != null) backLayer.getChildren().add(node);
        });

        revealed.addListener((obs, old, nowRevealed) -> {
            if (nowRevealed) doReveal(); else doConceal();
        });

        overlay.setOnMouseClicked(e -> {
            if (closeOnClickOutside.get()) setRevealed(false);
        });

        // Tapping the front layer while concealed reveals the backdrop
        frontLayer.setOnMouseClicked(e -> {
            if (!revealed.get()) setRevealed(true);
        });
    }

    // ── Layout ───────────────────────────────────────────────────────────────

    /**
     * All children are positioned here using real layout coordinates (no
     * TranslateY).  Because currentOffset invalidates layout on every frame,
     * the parent sees the growing/shrinking preferred height and allocates
     * space accordingly — nothing is clipped.
     */
    @Override
    protected void layoutChildren() {
        double w      = getWidth();
        double h      = getPrefHeight() - currentOffset.get(); // front layer's own height
        double backH  = backHeight.get();
        double offset = currentOffset.get();   // 0 … backH

        // Back layer: always anchored at the top, full backHeight tall
        if (backLayer.isVisible()) {
            backLayer.resizeRelocate(0, 0, w, backH);
        }

        // Front layer: pushed down by 'offset' pixels
        frontLayer.resizeRelocate(0, offset, w, h);

        // Overlay: covers exactly the front layer area so clicking it conceals
        overlay.resizeRelocate(0, offset, w, h);
    }

    /**
     * Tell the parent how tall we need to be so it never clips us.
     * When fully revealed that is backHeight + frontHeight.
     */
    @Override
    protected double computePrefHeight(double width) {
        double backH = backHeight.get();
        double frontH = frontLayer.prefHeight(width);
        double offset = currentOffset.get();

        // When revealed, we need backHeight + frontHeight space
        // The offset drives how much of that space is visible
        if (offset > 0) {
            return backH + frontH;
        }
        return frontH;
    }

    @Override
    protected double computeMinHeight(double width) {
        return computePrefHeight(width);
    }

    // ── Reveal / Conceal ────────────────────────────────────────────────────
    private void doReveal() {
        stopActiveAnimation();

        backLayer.setVisible(true);
        overlay.setVisible(true);

        fire(onReveal);

        if (!animated.get()) {
            currentOffset.set(backHeight.get());
            overlay.setOpacity(0.5);
            return;
        }

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(currentOffset, currentOffset.get()),
                        new KeyValue(overlay.opacityProperty(), overlay.getOpacity())),
                new KeyFrame(animationDuration.get(),
                        new KeyValue(currentOffset, backHeight.get()),
                        new KeyValue(overlay.opacityProperty(), 0.5))
        );
        activeTimeline = tl;
        tl.play();
    }

    private void doConceal() {
        stopActiveAnimation();

        fire(onConceal);

        if (!animated.get()) {
            currentOffset.set(0);
            backLayer.setVisible(false);
            overlay.setVisible(false);
            overlay.setOpacity(0);
            return;
        }

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(currentOffset, currentOffset.get()),
                        new KeyValue(overlay.opacityProperty(), overlay.getOpacity())),
                new KeyFrame(animationDuration.get(),
                        new KeyValue(currentOffset, 0),
                        new KeyValue(overlay.opacityProperty(), 0))
        );
        tl.setOnFinished(e -> {
            backLayer.setVisible(false);
            overlay.setVisible(false);
        });
        activeTimeline = tl;
        tl.play();
    }

    private void stopActiveAnimation() {
        if (activeTimeline != null) {
            activeTimeline.stop();
            activeTimeline = null;
        }
    }

    private void fire(ObjectProperty<EventHandler<ActionEvent>> handlerProp) {
        if (handlerProp != null && handlerProp.get() != null) {
            handlerProp.get().handle(new ActionEvent());
        }
    }

    // ── Public API ───────────────────────────────────────────────────────────
    public void reveal()  { setRevealed(true);  }
    public void conceal() { setRevealed(false); }
    public void toggle()  { setRevealed(!isRevealed()); }

    // revealed
    public boolean isRevealed()                         { return revealed.get(); }
    public void setRevealed(boolean v)                  { revealed.set(v); }
    public BooleanProperty revealedProperty()           { return revealed; }

    // frontContent
    public Node getFrontContent()                       { return frontContent.get(); }
    public void setFrontContent(Node v)                 { frontContent.set(v); }
    public ObjectProperty<Node> frontContentProperty()  { return frontContent; }

    // backContent
    public Node getBackContent()                        { return backContent.get(); }
    public void setBackContent(Node v)                  { backContent.set(v); }
    public ObjectProperty<Node> backContentProperty()   { return backContent; }

    // backHeight
    public double getBackHeight()                       { return backHeight.get(); }
    public void setBackHeight(double v)                 { backHeight.set(v); }
    public DoubleProperty backHeightProperty()          { return backHeight; }

    // closeOnClickOutside
    public boolean isCloseOnClickOutside()              { return closeOnClickOutside.get(); }
    public void setCloseOnClickOutside(boolean v)       { closeOnClickOutside.set(v); }
    public BooleanProperty closeOnClickOutsideProperty(){ return closeOnClickOutside; }

    // animated
    public boolean isAnimated()                         { return animated.get(); }
    public void setAnimated(boolean v)                  { animated.set(v); }
    public BooleanProperty animatedProperty()           { return animated; }

    // animationDuration
    public Duration getAnimationDuration()              { return animationDuration.get(); }
    public void setAnimationDuration(Duration v)        { animationDuration.set(v); }
    public ObjectProperty<Duration> animationDurationProperty() { return animationDuration; }

    // onReveal
    public EventHandler<ActionEvent> getOnReveal()      { return onReveal == null ? null : onReveal.get(); }
    public void setOnReveal(EventHandler<ActionEvent> h) {
        if (onReveal == null) onReveal = new SimpleObjectProperty<>(this, "onReveal");
        onReveal.set(h);
    }
    public ObjectProperty<EventHandler<ActionEvent>> onRevealProperty() {
        if (onReveal == null) onReveal = new SimpleObjectProperty<>(this, "onReveal");
        return onReveal;
    }

    // onConceal
    public EventHandler<ActionEvent> getOnConceal()     { return onConceal == null ? null : onConceal.get(); }
    public void setOnConceal(EventHandler<ActionEvent> h) {
        if (onConceal == null) onConceal = new SimpleObjectProperty<>(this, "onConceal");
        onConceal.set(h);
    }
    public ObjectProperty<EventHandler<ActionEvent>> onConcealProperty() {
        if (onConceal == null) onConceal = new SimpleObjectProperty<>(this, "onConceal");
        return onConceal;
    }
}