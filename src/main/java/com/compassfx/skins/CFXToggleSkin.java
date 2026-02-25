package com.compassfx.skins;

import com.compassfx.controls.CFXToggle;
import javafx.animation.TranslateTransition;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Custom skin for CFXToggle with Material Design animations
 */
public class CFXToggleSkin extends SkinBase<CFXToggle> {

    private final CFXToggle toggle;
    private final HBox container;
    private final StackPane track;
    private final Rectangle trackBackground;
    private final Circle thumb;
    private final Label label;

    // Size constants
    private double trackWidth;
    private double trackHeight;
    private double thumbSize;
    private double thumbPadding;

    public CFXToggleSkin(CFXToggle toggle) {
        super(toggle);
        this.toggle = toggle;

        // Create components
        this.container = new HBox(12);
        this.track = new StackPane();
        this.trackBackground = new Rectangle();
        this.thumb = new Circle();
        this.label = new Label();

        setupComponents();
        setupListeners();
        updateToggleState(false); // Initial state without animation
    }

    private void setupComponents() {
        // Update sizes based on toggle size
        updateSizes();

        // Setup track background
        trackBackground.setArcWidth(trackHeight);
        trackBackground.setArcHeight(trackHeight);
        trackBackground.getStyleClass().add("cfx-toggle-track");

        // Setup thumb
        thumb.getStyleClass().add("cfx-toggle-thumb");
        thumb.setCursor(javafx.scene.Cursor.HAND);

        // Position thumb initially (off position)
        thumb.setTranslateX(-getThumbTravelDistance() / 2);

        // Setup track
        track.getChildren().addAll(trackBackground, thumb);
//        track.setAlignment(Pos.CENTER_LEFT);
        track.setCursor(javafx.scene.Cursor.HAND);
        track.getStyleClass().add("cfx-toggle-track-container");

        // Setup label
        label.textProperty().bind(toggle.textProperty());
        label.getStyleClass().add("cfx-toggle-label");

        // Setup container
        container.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().addAll(track, label);

        // Make label visible only if text is present
        label.managedProperty().bind(label.textProperty().isNotEmpty());
        label.visibleProperty().bind(label.textProperty().isNotEmpty());

        getChildren().add(container);

        // Click handlers
        track.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!toggle.isDisabled()) {
                toggle.toggle();
            }
        });

        label.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!toggle.isDisabled()) {
                toggle.toggle();
            }
        });
    }

    private void setupListeners() {
        // Listen to selected property changes
        toggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            updateToggleState(true);
        });

        // Listen to size property changes
        toggle.sizeProperty().addListener((obs, oldVal, newVal) -> {
            updateSizes();
            updateToggleState(false);
        });
    }

    private void updateSizes() {
        switch (toggle.getSize()) {
            case SMALL:
                trackWidth = 32;
                trackHeight = 16;
                thumbSize = 12;
                thumbPadding = 2;
                break;
            case LARGE:
                trackWidth = 52;
                trackHeight = 28;
                thumbSize = 22;
                thumbPadding = 3;
                break;
            case MEDIUM:
            default:
                trackWidth = 42;
                trackHeight = 22;
                thumbSize = 18;
                thumbPadding = 2;
                break;
        }

        trackBackground.setWidth(trackWidth);
        trackBackground.setHeight(trackHeight);
        thumb.setRadius(thumbSize / 2);
    }

    private double getThumbTravelDistance() {
        return trackWidth - thumbSize - (thumbPadding * 2);
    }

    private void updateToggleState(boolean animate) {
        double targetX;
        Color targetTrackColor;
        Color targetThumbColor;

        if (toggle.isSelected()) {
            // ON state
            targetX = getThumbTravelDistance() / 2;
            targetTrackColor = Color.rgb(33, 150, 243, 0.5); // Primary color with opacity
            targetThumbColor = Color.rgb(33, 150, 243); // Primary color
        } else {
            // OFF state
            targetX = -getThumbTravelDistance() / 2;
            targetTrackColor = Color.rgb(0, 0, 0, 0.38); // Gray
            targetThumbColor = Color.rgb(245, 245, 245); // Light gray
        }

        if (animate) {
            // Animate thumb position
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumb);
            thumbTransition.setToX(targetX);
            thumbTransition.play();

            // Animate track color
            FillTransition trackTransition = new FillTransition(Duration.millis(200), trackBackground);
            trackTransition.setToValue(targetTrackColor);
            trackTransition.play();

            // Animate thumb color
            FillTransition thumbColorTransition = new FillTransition(Duration.millis(200), thumb);
            thumbColorTransition.setToValue(targetThumbColor);
            thumbColorTransition.play();
        } else {
            // Set immediately without animation
            thumb.setTranslateX(targetX);
            trackBackground.setFill(targetTrackColor);
            thumb.setFill(targetThumbColor);
        }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset,
                                     double bottomInset, double leftInset) {
        return leftInset + trackWidth +
                (label.getText().isEmpty() ? 0 : 12 + label.prefWidth(-1)) + rightInset;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset,
                                      double bottomInset, double leftInset) {
        return topInset + Math.max(trackHeight, label.prefHeight(-1)) + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset,
                                      double bottomInset, double leftInset) {
        return computeMinWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset,
                                       double bottomInset, double leftInset) {
        return computeMinHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}