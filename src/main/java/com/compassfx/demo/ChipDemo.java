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

public class ChipDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FAFAFA;");

        Label title = new Label("CompassFX Badge Demo");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Standalone Pills (números solos)
        // ====================================
        Label pillsLabel = new Label("Standalone Badge Pills");
        pillsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        // Chip sólido (como tus Text Pills)
        CFXChip chip1 = new CFXChip("NEW");
        chip1.setColor(BadgeColor.PRIMARY);
        chip1.setBadgeStyle(BadgeStyle.SOLID);

// Chip con número
        CFXChip chip2 = new CFXChip();
        chip2.setValue(12);
        chip2.setColor(BadgeColor.WARNING);
        chip2.setBadgeStyle(BadgeStyle.SOLID);

// Chip outline
        CFXChip chip3 = new CFXChip("BETA");
        chip3.setColor(BadgeColor.INFO);
        chip3.setBadgeStyle(BadgeStyle.OUTLINE);

// Chip subtle
        CFXChip chip4 = new CFXChip("PRO");
        chip4.setColor(BadgeColor.SUCCESS);
        chip4.setBadgeStyle(BadgeStyle.SUBTLE);

// Chip surface
        CFXChip chip5 = new CFXChip("SALE");
        chip5.setColor(BadgeColor.ERROR);
        chip5.setBadgeStyle(BadgeStyle.SURFACE);

// Chip with close button
        CFXChip chip6 = new CFXChip("Removable");
        chip6.setCloseable(true);
        chip6.setOnClose(e -> System.out.println("Chip closed!"));
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

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #FAFAFA; -fx-background-color: #FAFAFA;");

        Scene scene = new Scene(scrollPane, 1000, 1200);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Chip Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

    public static void main(String[] args) {
        launch(args);
    }
}