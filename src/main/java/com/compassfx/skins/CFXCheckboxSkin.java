package com.compassfx.skins;

import com.compassfx.controls.CFXCheckbox;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.skin.CheckBoxSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Custom skin for CFXCheckbox with Material Design ripple effect
 */
public class CFXCheckboxSkin extends CheckBoxSkin {

    private final CFXCheckbox checkbox;
    private final StackPane rippleContainer;

    public CFXCheckboxSkin(CFXCheckbox checkbox) {
        super(checkbox);
        this.checkbox = checkbox;
        this.rippleContainer = new StackPane();

        // Setup ripple container
        rippleContainer.setMouseTransparent(true);
        rippleContainer.getStyleClass().add("cfx-checkbox-ripple-container");
        rippleContainer.setMaxSize(40, 40);
        rippleContainer.setMinSize(40, 40);

        getChildren().add(0, rippleContainer);

        // Add ripple effect on mouse press
        checkbox.addEventHandler(MouseEvent.MOUSE_PRESSED, this::createRippleEffect);
    }

    private void createRippleEffect(MouseEvent event) {
        if (!checkbox.isRippleEnabled() || checkbox.isDisabled()) {
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

        // Position ripple container at the checkbox box location
        double boxWidth = 20;
        double boxHeight = 20;
        layoutInArea(rippleContainer, x - 10, y - 10, boxWidth + 20, boxHeight + 20,
                0, HPos.LEFT, VPos.CENTER);
    }
}
