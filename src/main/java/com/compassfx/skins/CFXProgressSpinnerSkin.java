package com.compassfx.skins;

import com.compassfx.controls.CFXProgressSpinner;
import javafx.animation.*;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Skin for CFXProgressSpinner
 * Creates circular progress indicator with perfect alignment
 */
public class CFXProgressSpinnerSkin extends SkinBase<CFXProgressSpinner> {

    private final StackPane container;
    private final Circle track;
    private final Arc progressArc;
    private RotateTransition rotateTransition;

    public CFXProgressSpinnerSkin(CFXProgressSpinner control) {
        super(control);

        container = new StackPane();
        container.getStyleClass().add("spinner-container");

        // Get size based on control size property
        double size = getSpinnerSize(control);
        double strokeWidth = getStrokeWidth(control);

        // Calculate radius accounting for stroke width
        double radius = (size - strokeWidth) / 2.0;

        // Background track circle
        track = new Circle(radius);
        track.getStyleClass().add("cfx-progress-track");
        track.setStrokeWidth(strokeWidth);

        // Progress arc - CRITICAL: Same center and radius as track
        progressArc = new Arc();
        progressArc.setType(ArcType.OPEN);
        progressArc.setCenterX(0);  // Centered at 0,0 in StackPane
        progressArc.setCenterY(0);  // Centered at 0,0 in StackPane
        progressArc.setRadiusX(radius);  // Same radius as track
        progressArc.setRadiusY(radius);  // Same radius as track
        progressArc.setStartAngle(90);   // Start at top
        progressArc.getStyleClass().add("cfx-progress-arc");
        progressArc.setStrokeWidth(strokeWidth);

        container.getChildren().addAll(track, progressArc);
        getChildren().add(container);

        // Set container size
        container.setPrefSize(size, size);
        container.setMinSize(size, size);
        container.setMaxSize(size, size);

        // Update progress
        updateProgress();

        // Listen for changes
        control.progressProperty().addListener((obs, old, newVal) -> updateProgress());
        control.indeterminateProperty().addListener((obs, old, newVal) -> updateIndeterminate());
        control.sizeProperty().addListener((obs, old, newVal) -> updateSize());

        // Setup indeterminate animation if needed
        if (control.isIndeterminate()) {
            setupIndeterminateAnimation();
        }
    }

    private double getSpinnerSize(CFXProgressSpinner control) {
        switch (control.getSize()) {
            case SMALL: return 32;
            case LARGE: return 64;
            case MEDIUM:
            default: return 48;
        }
    }

    private double getStrokeWidth(CFXProgressSpinner control) {
        switch (control.getSize()) {
            case SMALL: return 3;
            case LARGE: return 5;
            case MEDIUM:
            default: return 4;
        }
    }

    private void updateProgress() {
        if (!getSkinnable().isIndeterminate()) {
            double progress = getSkinnable().getProgress();
            double angle = progress * 360.0;
            progressArc.setLength(-angle);  // Negative for clockwise
        }
    }

    private void updateIndeterminate() {
        if (getSkinnable().isIndeterminate()) {
            setupIndeterminateAnimation();
        } else {
            if (rotateTransition != null) {
                rotateTransition.stop();
            }
            progressArc.setRotate(0);
            updateProgress();
        }
    }

    private void updateSize() {
        double size = getSpinnerSize(getSkinnable());
        double strokeWidth = getStrokeWidth(getSkinnable());
        double radius = (size - strokeWidth) / 2.0;

        container.setPrefSize(size, size);
        container.setMinSize(size, size);
        container.setMaxSize(size, size);

        track.setRadius(radius);
        track.setStrokeWidth(strokeWidth);

        progressArc.setRadiusX(radius);
        progressArc.setRadiusY(radius);
        progressArc.setStrokeWidth(strokeWidth);
    }

    private void setupIndeterminateAnimation() {
        // Set arc to show partial circle
        progressArc.setLength(-270);  // 3/4 circle

        // Create rotation animation
        rotateTransition = new RotateTransition(Duration.seconds(1.4), progressArc);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();
    }

    @Override
    public void dispose() {
        if (rotateTransition != null) {
            rotateTransition.stop();
        }
        super.dispose();
    }
}