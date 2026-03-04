package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.ButtonSize;
import com.compassfx.enums.ButtonVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonDemo {

    public void showDemo(Label title, VBox root) {
        Label containedLabel = new Label("Contained Buttons");
        containedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox containedButtons = new HBox(10);
        containedButtons.setAlignment(Pos.CENTER);

        CFXButton primaryBtn = new CFXButton("Primary");
        CFXButton secondaryBtn = new CFXButton("Secondary");
        secondaryBtn.setColor(ButtonColor.SECONDARY);
        CFXButton successBtn = new CFXButton("Success");
        successBtn.setColor(ButtonColor.SUCCESS);
        CFXButton warningBtn = new CFXButton("Warning");
        warningBtn.setColor(ButtonColor.WARNING);
        CFXButton errorBtn = new CFXButton("Error");
        errorBtn.setColor(ButtonColor.ERROR);

        containedButtons.getChildren().addAll(primaryBtn, secondaryBtn, successBtn, warningBtn, errorBtn);

        // Outlined Buttons
        Label outlinedLabel = new Label("Outlined Buttons");
        outlinedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox outlinedButtons = new HBox(10);
        outlinedButtons.setAlignment(Pos.CENTER);

        CFXButton outlinedPrimary = new CFXButton("Primary");
        outlinedPrimary.setVariant(ButtonVariant.OUTLINED);
        CFXButton outlinedSecondary = new CFXButton("Secondary");
        outlinedSecondary.setVariant(ButtonVariant.OUTLINED);
        outlinedSecondary.setColor(ButtonColor.SECONDARY);

        outlinedButtons.getChildren().addAll(outlinedPrimary, outlinedSecondary);

        // Text Buttons
        Label textLabel = new Label("Text Buttons");
        textLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox textButtons = new HBox(10);
        textButtons.setAlignment(Pos.CENTER);

        CFXButton textPrimary = new CFXButton("Primary");
        textPrimary.setVariant(ButtonVariant.TEXT);
        CFXButton textSecondary = new CFXButton("Secondary");
        textSecondary.setVariant(ButtonVariant.TEXT);
        textSecondary.setColor(ButtonColor.SECONDARY);

        textButtons.getChildren().addAll(textPrimary, textSecondary);

        // Sizes
        Label sizesLabel = new Label("Button Sizes");
        sizesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox sizeButtons = new HBox(10);
        sizeButtons.setAlignment(Pos.CENTER);

        CFXButton smallBtn = new CFXButton("Small");
        smallBtn.setSize(ButtonSize.SMALL);
        CFXButton mediumBtn = new CFXButton("Medium");
        CFXButton largeBtn = new CFXButton("Large");
        largeBtn.setSize(ButtonSize.LARGE);

        sizeButtons.getChildren().addAll(smallBtn, mediumBtn, largeBtn);

        // Disabled
        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox disabledButtons = new HBox(10);
        disabledButtons.setAlignment(Pos.CENTER);

        CFXButton disabledContained = new CFXButton("Disabled");
        disabledContained.setDisable(true);
        CFXButton disabledOutlined = new CFXButton("Disabled");
        disabledOutlined.setVariant(ButtonVariant.OUTLINED);
        disabledOutlined.setDisable(true);

        disabledButtons.getChildren().addAll(disabledContained, disabledOutlined);

        // Full Width
        Label fullWidthLabel = new Label("Full Width Button");
        fullWidthLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton fullWidthBtn = new CFXButton("Full Width Button");
        fullWidthBtn.setFullWidth(true);
        fullWidthBtn.setMaxWidth(400);

        root.getChildren().addAll(
                title, new Separator(),
                containedLabel, containedButtons, new Separator(),
                outlinedLabel, outlinedButtons, new Separator(),
                textLabel, textButtons, new Separator(),
                sizesLabel, sizeButtons, new Separator(),
                disabledLabel, disabledButtons, new Separator(),
                fullWidthLabel, fullWidthBtn
        );
    }
}