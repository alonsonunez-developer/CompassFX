package com.compassfx.skins;

import com.compassfx.controls.CFXRadioButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.skin.RadioButtonSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Custom skin for CFXRadioButton with Material Design ripple effect
 */
public class CFXRadioButtonSkin extends RadioButtonSkin {

    private final CFXRadioButton radioButton;
    private final StackPane rippleContainer;

    public CFXRadioButtonSkin(CFXRadioButton radioButton) {
        super(radioButton);
        this.radioButton = radioButton;
        this.rippleContainer = new StackPane();

        // Setup ripple container
        rippleContainer.setMouseTransparent(true);
        rippleContainer.getStyleClass().add("cfx-radiobutton-ripple-container");
        rippleContainer.setMaxSize(40, 40);
        rippleContainer.setMinSize(40, 40);

        getChildren().add(0, rippleContainer);

        // Add ripple effect on mouse press
        radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, this::createRippleEffect);
    }

    private void createRippleEffect(MouseEvent event) {
        if (!radioButton.isRippleEnabled() || radioButton.isDisabled()) {
            return;
        }

        // Create ripple circle
        Circle ripple = new Circle(20);
        ripple.setFill(Color.rgb(33, 150, 243, 0.3));
        ripple.setScaleX(0);
        ripple.setScaleY(0);

        rippleContainer.getChildren().add(ripple);

        // Create scale animation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(600), ripple);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Create fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600), ripple);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Play animations
        scaleTransition.play();
        fadeTransition.play();

        // Remove ripple after animation
        fadeTransition.setOnFinished(e -> rippleContainer.getChildren().remove(ripple));
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        // Position ripple container at the radio button circle location
        double boxWidth = 20;
        double boxHeight = 20;
        layoutInArea(rippleContainer, x - 10, y - 10, boxWidth + 20, boxHeight + 20,
                0, HPos.LEFT, VPos.CENTER);
    }
}