// ============================================
// CFXDrawer.java - Drawer Control (FIXED)
// src/main/java/com/compassfx/controls/CFXDrawer.java
// ============================================
package com.compassfx.controls;

import com.compassfx.enums.DrawerPosition;
import com.compassfx.enums.DrawerSize;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * CompassFX Drawer - A sliding drawer panel component
 * Can be opened from left, right, top, or bottom
 */
public class CFXDrawer extends Region {

    private static final String DEFAULT_STYLE_CLASS = "cfx-drawer";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-drawer.css";

    // Properties
    private final BooleanProperty open;
    private final ObjectProperty<DrawerPosition> position;
    private final ObjectProperty<DrawerSize> size;
    private final DoubleProperty customSize;
    private final ObjectProperty<Node> content;
    private final BooleanProperty overlay;
    private final BooleanProperty modal;
    private final BooleanProperty closeOnClickOutside;
    private final BooleanProperty animated;
    private final ObjectProperty<Duration> animationDuration;

    // Event handlers
    private ObjectProperty<EventHandler<ActionEvent>> onOpen;
    private ObjectProperty<EventHandler<ActionEvent>> onClose;

    // UI Components
    private final Region overlayPane;
    private final StackPane drawerPane;
    private final StackPane contentContainer;

    public CFXDrawer() {
        this.open = new SimpleBooleanProperty(this, "open", false);
        this.position = new SimpleObjectProperty<>(this, "position", DrawerPosition.LEFT);
        this.size = new SimpleObjectProperty<>(this, "size", DrawerSize.MEDIUM);
        this.customSize = new SimpleDoubleProperty(this, "customSize", 0);
        this.content = new SimpleObjectProperty<>(this, "content", null);
        this.overlay = new SimpleBooleanProperty(this, "overlay", true);
        this.modal = new SimpleBooleanProperty(this, "modal", true);
        this.closeOnClickOutside = new SimpleBooleanProperty(this, "closeOnClickOutside", true);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.animationDuration = new SimpleObjectProperty<>(this, "animationDuration", Duration.millis(300));
        this.onOpen = new SimpleObjectProperty<>(this, "onOpen", null);
        this.onClose = new SimpleObjectProperty<>(this, "onClose", null);

        // Create overlay
        overlayPane = new Region();
        overlayPane.getStyleClass().add("drawer-overlay");
        overlayPane.setVisible(false);
        overlayPane.setOpacity(0);

        // Create drawer pane
        drawerPane = new StackPane();
        drawerPane.getStyleClass().add("drawer-pane");
        drawerPane.setVisible(false);

        // Create content container
        contentContainer = new StackPane();
        contentContainer.getStyleClass().add("drawer-content");
        contentContainer.setPadding(new Insets(20));

        drawerPane.getChildren().add(contentContainer);

        getChildren().addAll(overlayPane, drawerPane);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // CRITICAL: Make this control mouse transparent by default
        setMouseTransparent(true);
        setPickOnBounds(false);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("INFO: Cargado stylesheet: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
        }

        updateStyleClasses();
        position.addListener((obs, oldVal, newVal) -> {
            updateStyleClasses();
        });

        // Content binding
        content.addListener((obs, oldVal, newVal) -> {
            contentContainer.getChildren().clear();
            if (newVal != null) {
                contentContainer.getChildren().add(newVal);
            }
        });

        // Open/Close handling
        open.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                openDrawer();
            } else {
                closeDrawer();
            }
        });

        // Overlay click handling
        overlayPane.setOnMouseClicked(e -> {
            if (closeOnClickOutside.get()) {
                close();
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(position.get().getStyleClass());
        getStyleClass().add(size.get().getStyleClass());
    }

    @Override
    protected void layoutChildren() {
        double width = getWidth();
        double height = getHeight();

        // Layout overlay to fill entire area
        overlayPane.resizeRelocate(0, 0, width, height);

        // Layout drawer based on position
        double drawerSize = customSize.get() > 0 ? customSize.get() : size.get().getSize();

        switch (position.get()) {
            case LEFT:
                drawerPane.resizeRelocate(0, 0, drawerSize, height);
                break;
            case RIGHT:
                drawerPane.resizeRelocate(width - drawerSize, 0, drawerSize, height);
                break;
            case TOP:
                drawerPane.resizeRelocate(0, 0, width, drawerSize);
                break;
            case BOTTOM:
                drawerPane.resizeRelocate(0, height - drawerSize, width, drawerSize);
                break;
        }
    }

    private void openDrawer() {
        // Make the drawer control interactive
        setMouseTransparent(false);
        setPickOnBounds(true);

        overlayPane.setVisible(true);
        drawerPane.setVisible(true);

        overlayPane.toFront();
        drawerPane.toFront();

        if (animated.get()) {
            animateOpen();
        } else {
            drawerPane.setOpacity(1);
            drawerPane.setTranslateX(0);
            drawerPane.setTranslateY(0);
            overlayPane.setOpacity(overlay.get() ? 0.5 : 0);
        }

        if (onOpen.get() != null) {
            onOpen.get().handle(new ActionEvent());
        }
    }

    private void closeDrawer() {
        if (animated.get()) {
            animateClose();
        } else {
            drawerPane.setVisible(false);
            overlayPane.setVisible(false);
            // Make drawer transparent to mouse again
            setMouseTransparent(true);
            setPickOnBounds(false);
        }

        if (onClose.get() != null) {
            onClose.get().handle(new ActionEvent());
        }
    }

    private void animateOpen() {
        double drawerSize = customSize.get() > 0 ? customSize.get() : size.get().getSize();

        // Set initial position (off-screen)
        switch (position.get()) {
            case LEFT:
                drawerPane.setTranslateX(-drawerSize);
                drawerPane.setTranslateY(0);
                break;
            case RIGHT:
                drawerPane.setTranslateX(drawerSize);
                drawerPane.setTranslateY(0);
                break;
            case TOP:
                drawerPane.setTranslateX(0);
                drawerPane.setTranslateY(-drawerSize);
                break;
            case BOTTOM:
                drawerPane.setTranslateX(0);
                drawerPane.setTranslateY(drawerSize);
                break;
        }

        drawerPane.setOpacity(1);
        overlayPane.setOpacity(0);

        // Create animations
        TranslateTransition drawerSlide = new TranslateTransition(animationDuration.get(), drawerPane);
        drawerSlide.setToX(0);
        drawerSlide.setToY(0);

        FadeTransition overlayFade = new FadeTransition(animationDuration.get(), overlayPane);
        overlayFade.setToValue(overlay.get() ? 0.5 : 0);

        ParallelTransition transition = new ParallelTransition(drawerSlide, overlayFade);
        transition.play();
    }

    private void animateClose() {
        double drawerSize = customSize.get() > 0 ? customSize.get() : size.get().getSize();

        TranslateTransition drawerSlide = new TranslateTransition(animationDuration.get(), drawerPane);

        switch (position.get()) {
            case LEFT:
                drawerSlide.setToX(-drawerSize);
                drawerSlide.setToY(0);
                break;
            case RIGHT:
                drawerSlide.setToX(drawerSize);
                drawerSlide.setToY(0);
                break;
            case TOP:
                drawerSlide.setToX(0);
                drawerSlide.setToY(-drawerSize);
                break;
            case BOTTOM:
                drawerSlide.setToX(0);
                drawerSlide.setToY(drawerSize);
                break;
        }

        FadeTransition overlayFade = new FadeTransition(animationDuration.get(), overlayPane);
        overlayFade.setToValue(0);

        ParallelTransition transition = new ParallelTransition(drawerSlide, overlayFade);
        transition.setOnFinished(e -> {
            drawerPane.setVisible(false);
            overlayPane.setVisible(false);
            // Make drawer transparent to mouse again
            setMouseTransparent(true);
            setPickOnBounds(false);
        });
        transition.play();
    }

    // Public methods
    public void open() {
        open.set(true);
    }

    public void close() {
        open.set(false);
    }

    public void toggle() {
        open.set(!open.get());
    }

    // Getters and Setters
    public boolean isOpen() { return open.get(); }
    public void setOpen(boolean open) { this.open.set(open); }
    public BooleanProperty openProperty() { return open; }

    public DrawerPosition getPosition() { return position.get(); }
    public void setPosition(DrawerPosition position) { this.position.set(position); }
    public ObjectProperty<DrawerPosition> positionProperty() { return position; }

    public DrawerSize getSize() { return size.get(); }
    public void setSize(DrawerSize size) { this.size.set(size); }
    public ObjectProperty<DrawerSize> sizeProperty() { return size; }

    public double getCustomSize() { return customSize.get(); }
    public void setCustomSize(double customSize) { this.customSize.set(customSize); }
    public DoubleProperty customSizeProperty() { return customSize; }

    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    public boolean isOverlay() { return overlay.get(); }
    public void setOverlay(boolean overlay) { this.overlay.set(overlay); }
    public BooleanProperty overlayProperty() { return overlay; }

    public boolean isModal() { return modal.get(); }
    public void setModal(boolean modal) { this.modal.set(modal); }
    public BooleanProperty modalProperty() { return modal; }

    public boolean isCloseOnClickOutside() { return closeOnClickOutside.get(); }
    public void setCloseOnClickOutside(boolean closeOnClickOutside) {
        this.closeOnClickOutside.set(closeOnClickOutside);
    }
    public BooleanProperty closeOnClickOutsideProperty() { return closeOnClickOutside; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public Duration getAnimationDuration() { return animationDuration.get(); }
    public void setAnimationDuration(Duration animationDuration) {
        this.animationDuration.set(animationDuration);
    }
    public ObjectProperty<Duration> animationDurationProperty() { return animationDuration; }

    public EventHandler<ActionEvent> getOnOpen() { return onOpen.get(); }
    public void setOnOpen(EventHandler<ActionEvent> handler) { this.onOpen.set(handler); }
    public ObjectProperty<EventHandler<ActionEvent>> onOpenProperty() { return onOpen; }

    public EventHandler<ActionEvent> getOnClose() { return onClose.get(); }
    public void setOnClose(EventHandler<ActionEvent> handler) { this.onClose.set(handler); }
    public ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() { return onClose; }
}