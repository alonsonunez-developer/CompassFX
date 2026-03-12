package com.compassfx.controls;

import com.compassfx.enums.MarqueeDirection;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * CompassFX Marquee - Auto-scrolling content display
 * Perfect for showcasing images, cards, logos, or any content
 */
public class CFXMarquee extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-marquee";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-marquee.css";

    // Properties
    private final ObjectProperty<ObservableList<Node>> items;
    private final ObjectProperty<MarqueeDirection> direction;
    private final DoubleProperty speed;
    private final DoubleProperty spacing;
    private final BooleanProperty pauseOnHover;
    private final BooleanProperty running;

    // UI Components
    private Pane animationContainer;
    private TranslateTransition animation;

    // Temporary container to render items before taking snapshots
    private StackPane offscreenContainer;

    public CFXMarquee() {
        this.items = new SimpleObjectProperty<>(this, "items", FXCollections.observableArrayList());
        this.direction = new SimpleObjectProperty<>(this, "direction", MarqueeDirection.RIGHT_TO_LEFT);
        this.speed = new SimpleDoubleProperty(this, "speed", 50);
        this.spacing = new SimpleDoubleProperty(this, "spacing", 20);
        this.pauseOnHover = new SimpleBooleanProperty(this, "pauseOnHover", true);
        this.running = new SimpleBooleanProperty(this, "running", true);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.CENTER_LEFT);

        // Create offscreen container for rendering
        offscreenContainer = new StackPane();
        offscreenContainer.setVisible(false);
        offscreenContainer.setManaged(false);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        setupListeners();
    }

    private void setupListeners() {
        // Items change
        items.get().addListener((ListChangeListener<Node>) c -> {
            rebuildMarquee();
        });

        // Direction change
        direction.addListener((obs, old, newVal) -> {
            rebuildMarquee();
        });

        // Pause on hover
        setOnMouseEntered(e -> {
            if (pauseOnHover.get() && animation != null) {
                animation.pause();
            }
        });

        setOnMouseExited(e -> {
            if (pauseOnHover.get() && animation != null && running.get()) {
                animation.play();
            }
        });

        // Running property
        running.addListener((obs, old, newVal) -> {
            if (animation != null) {
                if (newVal) {
                    animation.play();
                } else {
                    animation.pause();
                }
            }
        });

        // Speed change
        speed.addListener((obs, old, newVal) -> {
            if (animation != null && !items.get().isEmpty()) {
                rebuildMarquee();
            }
        });
    }

    private void rebuildMarquee() {
        // Stop current animation
        if (animation != null) {
            animation.stop();
        }

        // Clear everything
        getChildren().clear();

        if (items.get().isEmpty()) {
            return;
        }

        // Add items to offscreen container temporarily
        offscreenContainer.getChildren().clear();
        for (Node item : items.get()) {
            offscreenContainer.getChildren().add(item);
        }

        // Add offscreen container to scene temporarily to allow rendering
        if (!getChildren().contains(offscreenContainer)) {
            getChildren().add(offscreenContainer);
        }

        // Wait for items to be laid out, then take snapshots
        javafx.application.Platform.runLater(() -> {
            javafx.application.Platform.runLater(() -> {
                createMarqueeFromSnapshots();
            });
        });
    }

    private void createMarqueeFromSnapshots() {
        // Remove offscreen container
        getChildren().remove(offscreenContainer);

        if (items.get().isEmpty()) {
            return;
        }

        // Create snapshots of all items
        java.util.List<ImageView> snapshots = new java.util.ArrayList<>();

        for (Node item : offscreenContainer.getChildren()) {
            try {
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                WritableImage image = item.snapshot(params, null);

                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);

                snapshots.add(imageView);
            } catch (Exception e) {
                System.err.println("Error creating snapshot: " + e.getMessage());
            }
        }

        // Build the marquee with snapshots
        MarqueeDirection dir = direction.get();

        if (dir == MarqueeDirection.LEFT_TO_RIGHT || dir == MarqueeDirection.RIGHT_TO_LEFT) {
            buildHorizontalMarquee(snapshots);
        } else {
            buildVerticalMarquee(snapshots);
        }

        // Return items to offscreen container for future use
        offscreenContainer.getChildren().clear();
        offscreenContainer.getChildren().addAll(items.get());
    }

    private void buildHorizontalMarquee(java.util.List<ImageView> snapshots) {
        HBox container = new HBox(spacing.get());
        container.setAlignment(Pos.CENTER_LEFT);

        // Add snapshots 3 times for seamless loop
        for (int copy = 0; copy < 3; copy++) {
            for (ImageView snapshot : snapshots) {
                ImageView clone = new ImageView(snapshot.getImage());
                clone.setPreserveRatio(true);
                clone.setSmooth(true);
                container.getChildren().add(clone);
            }
        }

        animationContainer = container;
        getChildren().add(animationContainer);

        // Start animation after layout
        javafx.application.Platform.runLater(() -> {
            startHorizontalAnimation(snapshots.size());
        });
    }

    private void buildVerticalMarquee(java.util.List<ImageView> snapshots) {
        VBox container = new VBox(spacing.get());
        container.setAlignment(Pos.TOP_CENTER);

        // Add snapshots 3 times for seamless loop
        for (int copy = 0; copy < 3; copy++) {
            for (ImageView snapshot : snapshots) {
                ImageView clone = new ImageView(snapshot.getImage());
                clone.setPreserveRatio(true);
                clone.setSmooth(true);
                container.getChildren().add(clone);
            }
        }

        animationContainer = container;
        getChildren().add(animationContainer);

        // Start animation after layout
        javafx.application.Platform.runLater(() -> {
            startVerticalAnimation(snapshots.size());
        });
    }

    private void startHorizontalAnimation(int itemCount) {
        if (animationContainer == null || itemCount == 0) return;

        // Calculate width of one set
        double singleSetWidth = 0;
        for (int i = 0; i < itemCount; i++) {
            Node node = animationContainer.getChildrenUnmodifiable().get(i);
            singleSetWidth += node.getBoundsInParent().getWidth();
        }
        singleSetWidth += spacing.get() * (itemCount - 1);

        if (singleSetWidth <= 0) return;

        double duration = (singleSetWidth / speed.get()) * 1000;

        animation = new TranslateTransition(Duration.millis(duration), animationContainer);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.setCycleCount(Animation.INDEFINITE);

        if (direction.get() == MarqueeDirection.RIGHT_TO_LEFT) {
            animation.setFromX(0);
            animation.setToX(-singleSetWidth);
        } else {
            animation.setFromX(-singleSetWidth);
            animation.setToX(0);
        }

        if (running.get()) {
            animation.play();
        }
    }

    private void startVerticalAnimation(int itemCount) {
        if (animationContainer == null || itemCount == 0) return;

        // Calculate height of one set
        double singleSetHeight = 0;
        for (int i = 0; i < itemCount; i++) {
            Node node = animationContainer.getChildrenUnmodifiable().get(i);
            singleSetHeight += node.getBoundsInParent().getHeight();
        }
        singleSetHeight += spacing.get() * (itemCount - 1);

        if (singleSetHeight <= 0) return;

        double duration = (singleSetHeight / speed.get()) * 1000;

        animation = new TranslateTransition(Duration.millis(duration), animationContainer);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.setCycleCount(Animation.INDEFINITE);

        if (direction.get() == MarqueeDirection.BOTTOM_TO_TOP) {
            animation.setFromY(0);
            animation.setToY(-singleSetHeight);
        } else {
            animation.setFromY(-singleSetHeight);
            animation.setToY(0);
        }

        if (running.get()) {
            animation.play();
        }
    }

    // Public methods
    public void start() {
        setRunning(true);
    }

    public void stop() {
        setRunning(false);
    }

    public void pause() {
        if (animation != null) {
            animation.pause();
        }
    }

    public void resume() {
        if (animation != null && running.get()) {
            animation.play();
        }
    }

    // Getters and Setters
    public ObservableList<Node> getItems() { return items.get(); }
    public void setItems(ObservableList<Node> items) { this.items.set(items); }
    public ObjectProperty<ObservableList<Node>> itemsProperty() { return items; }

    public MarqueeDirection getDirection() { return direction.get(); }
    public void setDirection(MarqueeDirection direction) { this.direction.set(direction); }
    public ObjectProperty<MarqueeDirection> directionProperty() { return direction; }

    public double getSpeed() { return speed.get(); }
    public void setSpeed(double speed) { this.speed.set(speed); }
    public DoubleProperty speedProperty() { return speed; }

    public double getSpacing() { return spacing.get(); }
    public void setSpacing(double spacing) { this.spacing.set(spacing); }
    public DoubleProperty spacingProperty() { return spacing; }

    public boolean isPauseOnHover() { return pauseOnHover.get(); }
    public void setPauseOnHover(boolean pauseOnHover) { this.pauseOnHover.set(pauseOnHover); }
    public BooleanProperty pauseOnHoverProperty() { return pauseOnHover; }

    public boolean isRunning() { return running.get(); }
    public void setRunning(boolean running) { this.running.set(running); }
    public BooleanProperty runningProperty() { return running; }
}