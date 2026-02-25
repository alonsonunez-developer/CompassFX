// ============================================
// CFXSkeleton.java - Skeleton Loader Control
// src/main/java/com/compassfx/controls/CFXSkeleton.java
// ============================================
package com.compassfx.controls;

import com.compassfx.enums.SkeletonAnimation;
import com.compassfx.enums.SkeletonVariant;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * CompassFX Skeleton - Animated loading placeholder
 * Beautiful shimmer effect for loading states
 */
public class CFXSkeleton extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-skeleton";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-skeleton.css";

    // Properties
    private final ObjectProperty<SkeletonVariant> variant;
    private final ObjectProperty<SkeletonAnimation> animation;
    private final DoubleProperty customWidth;
    private final DoubleProperty customHeight;
    private final BooleanProperty active;

    // UI Components
    private final Region skeleton;
    private final Region shimmer;
    private Timeline shimmerAnimation;
    private Timeline pulseAnimation;

    public CFXSkeleton() {
        this(SkeletonVariant.RECTANGULAR);
    }

    public CFXSkeleton(SkeletonVariant variant) {
        this.variant = new SimpleObjectProperty<>(this, "variant", variant);
        this.animation = new SimpleObjectProperty<>(this, "animation", SkeletonAnimation.WAVE);
        this.customWidth = new SimpleDoubleProperty(this, "customWidth", 0);
        this.customHeight = new SimpleDoubleProperty(this, "customHeight", 0);
        this.active = new SimpleBooleanProperty(this, "active", true);

        // Create skeleton shape
        skeleton = new Region();
        skeleton.getStyleClass().add("skeleton-shape");

        // Create shimmer overlay as Region
        shimmer = new Region();
        shimmer.getStyleClass().add("skeleton-shimmer");
        shimmer.setStyle("-fx-background-color: linear-gradient(to right, " +
                "rgba(255,255,255,0) 0%, " +
                "rgba(255,255,255,0.6) 50%, " +
                "rgba(255,255,255,0) 100%);");
        shimmer.setOpacity(0);
        shimmer.setMouseTransparent(true);

        getChildren().addAll(skeleton, shimmer);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.CENTER_LEFT);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("INFO: Cargado stylesheet: " + STYLESHEET);
            } else {
                System.err.println("ERROR: No se pudo encontrar el recurso en: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
            e.printStackTrace();
        }

        updateStyleClasses();
        updateSize();
        updateShape();
        setupAnimation();

        // Listeners
        variant.addListener((obs, old, newVal) -> {
            updateStyleClasses();
            updateShape();
            updateSize();
        });
        animation.addListener((obs, old, newVal) -> {
            updateStyleClasses();
            setupAnimation();
        });
        customWidth.addListener((obs, old, newVal) -> updateSize());
        customHeight.addListener((obs, old, newVal) -> updateSize());
        active.addListener((obs, old, newVal) -> {
            if (newVal) {
                startAnimation();
            } else {
                stopAnimation();
            }
        });

        widthProperty().addListener((obs, old, newVal) -> updateShimmerSize());
        heightProperty().addListener((obs, old, newVal) -> updateShimmerSize());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(variant.get().getStyleClass());
        getStyleClass().add(animation.get().getStyleClass());
    }

    private void updateSize() {
        double width = customWidth.get();
        double height = customHeight.get();

        // Set default sizes based on variant
        if (width == 0) {
            switch (variant.get()) {
                case TEXT:
                    width = 200;
                    break;
                case CIRCULAR:
                    width = 40;
                    break;
                case RECTANGULAR:
                case ROUNDED:
                    width = 300;
                    break;
            }
        }

        if (height == 0) {
            switch (variant.get()) {
                case TEXT:
                    height = 16;
                    break;
                case CIRCULAR:
                    height = width; // Keep circular
                    break;
                case RECTANGULAR:
                case ROUNDED:
                    height = 200;
                    break;
            }
        }

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        skeleton.setPrefSize(width, height);
        skeleton.setMinSize(width, height);
        skeleton.setMaxSize(width, height);
    }

    private void updateShape() {
        skeleton.setClip(null);

        double width = getPrefWidth();
        double height = getPrefHeight();

        switch (variant.get()) {
            case CIRCULAR:
                Circle circle = new Circle(width / 2);
                circle.setCenterX(width / 2);
                circle.setCenterY(height / 2);
                skeleton.setClip(circle);
                break;
            case ROUNDED:
                Rectangle rounded = new Rectangle(width, height);
                rounded.setArcWidth(12);
                rounded.setArcHeight(12);
                skeleton.setClip(rounded);
                break;
            case TEXT:
                Rectangle text = new Rectangle(width, height);
                text.setArcWidth(4);
                text.setArcHeight(4);
                skeleton.setClip(text);
                break;
            case RECTANGULAR:
                Rectangle rect = new Rectangle(width, height);
                skeleton.setClip(rect);
                break;
        }
    }

    private void updateShimmerSize() {
        double width = getWidth() > 0 ? getWidth() : getPrefWidth();
        shimmer.setPrefWidth(width * 0.3); // Narrower shimmer bar
        shimmer.setMinWidth(width * 0.3);
        shimmer.setMaxWidth(width * 0.3);

        double height = getHeight() > 0 ? getHeight() : getPrefHeight();
        shimmer.setPrefHeight(height);
        shimmer.setMinHeight(height);
        shimmer.setMaxHeight(height);
    }

    private void setupAnimation() {
        stopAnimation();

        if (!active.get()) {
            return;
        }

        switch (animation.get()) {
            case WAVE:
                setupWaveAnimation();
                break;
            case PULSE:
                setupPulseAnimation();
                break;
            case NONE:
                // No animation
                break;
        }
    }

    private void setupWaveAnimation() {
        updateShimmerSize();

        double shimmerWidth = shimmer.getPrefWidth();
        double containerWidth = getPrefWidth();

        shimmerAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(shimmer.translateXProperty(), -shimmerWidth),
                        new KeyValue(shimmer.opacityProperty(), 0)
                ),
                new KeyFrame(Duration.millis(100),
                        new KeyValue(shimmer.opacityProperty(), 1.0)
                ),
                new KeyFrame(Duration.millis(1200),
                        new KeyValue(shimmer.translateXProperty(), containerWidth + shimmerWidth),
                        new KeyValue(shimmer.opacityProperty(), 1.0)
                ),
                new KeyFrame(Duration.millis(1300),
                        new KeyValue(shimmer.opacityProperty(), 0)
                ),
                new KeyFrame(Duration.millis(2500),
                        new KeyValue(shimmer.translateXProperty(), -shimmerWidth)
                )
        );

        shimmerAnimation.setCycleCount(Timeline.INDEFINITE);
        shimmerAnimation.play();
    }

    private void setupPulseAnimation() {
        pulseAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(skeleton.opacityProperty(), 1.0)
                ),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(skeleton.opacityProperty(), 0.5)
                ),
                new KeyFrame(Duration.millis(1600),
                        new KeyValue(skeleton.opacityProperty(), 1.0)
                )
        );

        pulseAnimation.setCycleCount(Timeline.INDEFINITE);
        pulseAnimation.play();
    }

    private void startAnimation() {
        setupAnimation();
    }

    private void stopAnimation() {
        if (shimmerAnimation != null) {
            shimmerAnimation.stop();
        }
        if (pulseAnimation != null) {
            pulseAnimation.stop();
        }
        skeleton.setOpacity(1.0);
        shimmer.setOpacity(0);
    }

    // Static helper methods for common skeleton patterns
    public static CFXSkeleton text() {
        return text(200);
    }

    public static CFXSkeleton text(double width) {
        CFXSkeleton skeleton = new CFXSkeleton(SkeletonVariant.TEXT);
        skeleton.setCustomWidth(width);
        return skeleton;
    }

    public static CFXSkeleton circle(double size) {
        CFXSkeleton skeleton = new CFXSkeleton(SkeletonVariant.CIRCULAR);
        skeleton.setCustomWidth(size);
        skeleton.setCustomHeight(size);
        return skeleton;
    }

    public static CFXSkeleton rectangle(double width, double height) {
        CFXSkeleton skeleton = new CFXSkeleton(SkeletonVariant.RECTANGULAR);
        skeleton.setCustomWidth(width);
        skeleton.setCustomHeight(height);
        return skeleton;
    }

    public static CFXSkeleton rounded(double width, double height) {
        CFXSkeleton skeleton = new CFXSkeleton(SkeletonVariant.ROUNDED);
        skeleton.setCustomWidth(width);
        skeleton.setCustomHeight(height);
        return skeleton;
    }

    // Getters and Setters
    public SkeletonVariant getVariant() { return variant.get(); }
    public void setVariant(SkeletonVariant variant) { this.variant.set(variant); }
    public ObjectProperty<SkeletonVariant> variantProperty() { return variant; }

    public SkeletonAnimation getAnimation() { return animation.get(); }
    public void setAnimation(SkeletonAnimation animation) { this.animation.set(animation); }
    public ObjectProperty<SkeletonAnimation> animationProperty() { return animation; }

    public double getCustomWidth() { return customWidth.get(); }
    public void setCustomWidth(double customWidth) { this.customWidth.set(customWidth); }
    public DoubleProperty customWidthProperty() { return customWidth; }

    public double getCustomHeight() { return customHeight.get(); }
    public void setCustomHeight(double customHeight) { this.customHeight.set(customHeight); }
    public DoubleProperty customHeightProperty() { return customHeight; }

    public boolean isActive() { return active.get(); }
    public void setActive(boolean active) { this.active.set(active); }
    public BooleanProperty activeProperty() { return active; }
}