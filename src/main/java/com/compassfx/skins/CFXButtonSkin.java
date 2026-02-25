package com.compassfx.skins;

import com.compassfx.controls.CFXButton;
import javafx.animation.*;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CFXButtonSkin extends ButtonSkin {

    private final CFXButton button;
    private final StackPane rippleContainer;

    public CFXButtonSkin(CFXButton button) {
        super(button);
        this.button = button;
        this.rippleContainer = new StackPane();

        rippleContainer.setMouseTransparent(true);
        rippleContainer.getStyleClass().add("ripple-container");

        getChildren().add(0, rippleContainer);

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, this::createRippleEffect);
    }

    private void createRippleEffect(MouseEvent event) {
        if (!button.isRippleEnabled() || button.isDisabled()) {
            return;
        }

        Circle ripple = new Circle();
        ripple.setFill(Color.rgb(255, 255, 255, 0.3));
        ripple.setRadius(0);

        double x = event.getX();
        double y = event.getY();
        ripple.setTranslateX(x - button.getWidth() / 2);
        ripple.setTranslateY(y - button.getHeight() / 2);

        rippleContainer.getChildren().add(ripple);

        double maxRadius = Math.max(button.getWidth(), button.getHeight());

        Timeline scaleTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(ripple.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(600), new KeyValue(ripple.radiusProperty(), maxRadius))
        );

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600), ripple);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(0.0);

        scaleTimeline.play();
        fadeTransition.play();

        fadeTransition.setOnFinished(e -> rippleContainer.getChildren().remove(ripple));
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        rippleContainer.resizeRelocate(x, y, w, h);
    }
}