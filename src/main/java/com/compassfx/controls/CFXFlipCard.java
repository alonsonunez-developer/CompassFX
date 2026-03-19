package com.compassfx.controls;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.Point3D;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * CompassFX FlipCard - A card that flips with 3D animation
 */
public class CFXFlipCard extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-flip-card";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-flip-card.css";

    // Properties
    private final ObjectProperty<CFXCard> frontCard;
    private final ObjectProperty<CFXCard> backCard;
    private final BooleanProperty flipped;
    private final ObjectProperty<FlipDirection> flipDirection;
    private final DoubleProperty flipDuration;
    private final BooleanProperty flipOnClick;

    // Containers
    private StackPane frontContainer;
    private StackPane backContainer;

    // Rotations
    private final Rotate containerRotate;
    private final Rotate backCardRotate;

    private Timeline flipAnimation;

    public enum FlipDirection {
        HORIZONTAL,
        VERTICAL
    }

    public CFXFlipCard() {
        this.frontCard = new SimpleObjectProperty<>(this, "frontCard", null);
        this.backCard = new SimpleObjectProperty<>(this, "backCard", null);
        this.flipped = new SimpleBooleanProperty(this, "flipped", false);
        this.flipDirection = new SimpleObjectProperty<>(this, "flipDirection", FlipDirection.HORIZONTAL);
        this.flipDuration = new SimpleDoubleProperty(this, "flipDuration", 600);
        this.flipOnClick = new SimpleBooleanProperty(this, "flipOnClick", true);

        this.containerRotate = new Rotate(0, Rotate.Y_AXIS);
        this.backCardRotate = new Rotate(180, Rotate.Y_AXIS);

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

        // Apply rotation to the entire flip card
        getTransforms().add(containerRotate);

        setupContainers();
        setupListeners();
        updateCards();
    }

    private void setupContainers() {
        frontContainer = new StackPane();
        backContainer = new StackPane();

        // Pre-rotate the back container 180 degrees so it appears correctly
        // when the main container rotates
        backContainer.getTransforms().add(backCardRotate);

        getChildren().addAll(frontContainer, backContainer);
    }

    private void setupListeners() {
        frontCard.addListener((obs, old, newCard) -> updateCards());
        backCard.addListener((obs, old, newCard) -> updateCards());

        flipped.addListener((obs, old, newVal) -> {
            if (newVal) {
                flipToBack();
            } else {
                flipToFront();
            }
        });

        flipDirection.addListener((obs, old, newVal) -> {
            updateRotationAxis();
        });

        flipOnClick.addListener((obs, old, newVal) -> {
            if (newVal) {
                setOnMouseClicked(e -> flip());
                setStyle("-fx-cursor: hand;");
            } else {
                setOnMouseClicked(null);
                setStyle("-fx-cursor: default;");
            }
        });
    }

    private void updateCards() {
        frontContainer.getChildren().clear();
        backContainer.getChildren().clear();

        if (frontCard.get() != null) {
            frontContainer.getChildren().add(frontCard.get());
        }

        if (backCard.get() != null) {
            backContainer.getChildren().add(backCard.get());
        }

        updateRotationAxis();
        updateVisibility();

        // Enable click to flip if set
        if (flipOnClick.get()) {
            setOnMouseClicked(e -> flip());
            setStyle("-fx-cursor: hand;");
        }
    }

    private void updateRotationAxis() {
        Point3D axis = flipDirection.get() == FlipDirection.HORIZONTAL
                ? Rotate.Y_AXIS
                : Rotate.X_AXIS;

        containerRotate.setAxis(axis);
        backCardRotate.setAxis(axis);
    }

    private void updateVisibility() {
        double angle = Math.abs(containerRotate.getAngle() % 360);

        // Front is visible when angle is close to 0
        // Back is visible when angle is close to 180
        boolean showFront = angle < 90 || angle > 270;

        frontContainer.setVisible(showFront);
        frontContainer.setMouseTransparent(!showFront);

        backContainer.setVisible(!showFront);
        backContainer.setMouseTransparent(showFront);
    }

    public void flip() {
        setFlipped(!isFlipped());
    }

    private void flipToBack() {
        animateFlip(0, 180);
    }

    private void flipToFront() {
        animateFlip(180, 0);
    }

    private void animateFlip(double from, double to) {
        if (flipAnimation != null) {
            flipAnimation.stop();
        }

        // Show both cards during animation for smooth transition
        frontContainer.setVisible(true);
        backContainer.setVisible(true);

        containerRotate.setAngle(from);

        KeyValue kv = new KeyValue(containerRotate.angleProperty(), to, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.millis(flipDuration.get()), kv);

        flipAnimation = new Timeline(kf);
        flipAnimation.setOnFinished(e -> {
            updateVisibility();
        });

        flipAnimation.play();
    }

    // Getters and Setters
    public CFXCard getFrontCard() { return frontCard.get(); }
    public void setFrontCard(CFXCard card) { this.frontCard.set(card); }
    public ObjectProperty<CFXCard> frontCardProperty() { return frontCard; }

    public CFXCard getBackCard() { return backCard.get(); }
    public void setBackCard(CFXCard card) { this.backCard.set(card); }
    public ObjectProperty<CFXCard> backCardProperty() { return backCard; }

    public boolean isFlipped() { return flipped.get(); }
    public void setFlipped(boolean flipped) { this.flipped.set(flipped); }
    public BooleanProperty flippedProperty() { return flipped; }

    public FlipDirection getFlipDirection() { return flipDirection.get(); }
    public void setFlipDirection(FlipDirection direction) { this.flipDirection.set(direction); }
    public ObjectProperty<FlipDirection> flipDirectionProperty() { return flipDirection; }

    public double getFlipDuration() { return flipDuration.get(); }
    public void setFlipDuration(double duration) { this.flipDuration.set(duration); }
    public DoubleProperty flipDurationProperty() { return flipDuration; }

    public boolean isFlipOnClick() { return flipOnClick.get(); }
    public void setFlipOnClick(boolean flipOnClick) { this.flipOnClick.set(flipOnClick); }
    public BooleanProperty flipOnClickProperty() { return flipOnClick; }
}