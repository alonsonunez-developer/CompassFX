package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXAvatar;
import com.compassfx.controls.CFXBadge;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXChip;
import com.compassfx.enums.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ChipDemo {

    public void showDemo(Label title, VBox root){
        Label pillsLabel = new Label("Standalone Badge Pills");
        pillsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        // Chip sólido (como tus Text Pills)
        CFXChip chip1 = new CFXChip("Primary");
        chip1.setColor(BadgeColor.PRIMARY);
        chip1.setBadgeStyle(BadgeStyle.SOLID);

// Chip con número
        CFXChip chip2 = new CFXChip("Warning");
        chip2.setColor(BadgeColor.WARNING);
        chip2.setBadgeStyle(BadgeStyle.SOLID);

// Chip outline
        CFXChip chip3 = new CFXChip("Info");
        chip3.setColor(BadgeColor.INFO);
        chip3.setBadgeStyle(BadgeStyle.OUTLINE);

// Chip subtle
        CFXChip chip4 = new CFXChip("Success");
        chip4.setColor(BadgeColor.SUCCESS);
        chip4.setBadgeStyle(BadgeStyle.SUBTLE);

// Chip surface
        CFXChip chip5 = new CFXChip("Error");
        chip5.setColor(BadgeColor.ERROR);
        chip5.setBadgeStyle(BadgeStyle.SURFACE);

// Chip with close button
        CFXChip chip6 = new CFXChip("Removable");
        chip6.setCloseable(true);
        chip6.setOnClose(e -> System.out.println("Chip closed!"));
        root.setMaxWidth(200);
        root.getChildren().addAll(
                title,
                new Separator(),
                pillsLabel,
                chip1,
                chip2,
                chip3,
                chip4,
                chip5,
                chip6
        );
    }

}