package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.SpeedDialDirection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class SpeedDialDemo {

    private CFXSpeedDial speedDial;
    private Label statusLabel;

    public void showDemo(Label title, VBox root) {
        // Description
        Label description = new Label("Select a direction and click the blue button");
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        // Status label
        statusLabel = new Label("Ready - Select a direction below");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2196F3; " +
                "-fx-font-weight: 600; -fx-padding: 15px; " +
                "-fx-background-color: #E3F2FD; -fx-background-radius: 8px;");

        // Direction selector
        VBox directionSelector = createDirectionSelector();

        // Speed dial container (large area to show expansions clearly)
        StackPane speedDialContainer = new StackPane();
        speedDialContainer.setPrefSize(600, 600);
        speedDialContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.3, 0, 4);"
        );

        // Create speed dial
        speedDial = createSpeedDial();
        speedDialContainer.getChildren().add(speedDial);

        // Instructions
        VBox instructions = new VBox(10);
        instructions.setAlignment(Pos.CENTER);
        instructions.setPadding(new Insets(20));
        instructions.setStyle("-fx-background-color: #FFF3E0; -fx-background-radius: 8px;");

        Label instructionTitle = new Label("💡 How to use:");
        instructionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        Label inst1 = new Label("1️⃣ Select a direction using the radio buttons");
        Label inst2 = new Label("2️⃣ Click the blue speed dial button");
        Label inst3 = new Label("3️⃣ Click any action icon");
        Label inst4 = new Label("4️⃣ Try different directions to see the animations");

        inst1.setStyle("-fx-font-size: 14px;");
        inst2.setStyle("-fx-font-size: 14px;");
        inst3.setStyle("-fx-font-size: 14px;");
        inst4.setStyle("-fx-font-size: 14px;");

        instructions.getChildren().addAll(instructionTitle, inst1, inst2, inst3, inst4);

        // Add all to root
        root.getChildren().addAll(
                title,
                description,
                statusLabel,
                new Separator(),
                directionSelector,
                speedDialContainer,
                instructions
        );
    }

    private VBox createDirectionSelector() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        container.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 4, 0.2, 0, 2);"
        );

        Label selectorTitle = new Label("🎯 Select Direction");
        selectorTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #212121;");

        // Create radio buttons in a grid
        GridPane radioGrid = new GridPane();
        radioGrid.setHgap(40);
        radioGrid.setVgap(20);
        radioGrid.setAlignment(Pos.CENTER);

        ToggleGroup directionGroup = new ToggleGroup();

        // UP
        CFXRadioButton upRadio = new CFXRadioButton("⬆️  UP");
        upRadio.setToggleGroup(directionGroup);
        upRadio.setSelected(true); // Default
        upRadio.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");
        upRadio.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                speedDial.setDirection(SpeedDialDirection.UP);
                updateStatus("Direction changed to UP");
            }
        });

        // DOWN
        CFXRadioButton downRadio = new CFXRadioButton("⬇️  DOWN");
        downRadio.setToggleGroup(directionGroup);
        downRadio.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");
        downRadio.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                speedDial.setDirection(SpeedDialDirection.DOWN);
                updateStatus("Direction changed to DOWN");
            }
        });

        // LEFT
        CFXRadioButton leftRadio = new CFXRadioButton("⬅️  LEFT");
        leftRadio.setToggleGroup(directionGroup);
        leftRadio.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");
        leftRadio.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                speedDial.setDirection(SpeedDialDirection.LEFT);
                updateStatus("Direction changed to LEFT");
            }
        });

        // RIGHT
        CFXRadioButton rightRadio = new CFXRadioButton("➡️  RIGHT");
        rightRadio.setToggleGroup(directionGroup);
        rightRadio.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");
        rightRadio.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                speedDial.setDirection(SpeedDialDirection.RIGHT);
                updateStatus("Direction changed to RIGHT");
            }
        });

        // Add to grid
        radioGrid.add(upRadio, 1, 0);
        radioGrid.add(leftRadio, 0, 1);
        radioGrid.add(rightRadio, 2, 1);
        radioGrid.add(downRadio, 1, 2);

        container.getChildren().addAll(selectorTitle, radioGrid);
        return container;
    }

    private CFXSpeedDial createSpeedDial() {
        CFXSpeedDial dial = new CFXSpeedDial();
        dial.setDirection(SpeedDialDirection.UP);
        dial.setMainTooltip("Quick Actions");

        // Edit action
        CFXSpeedDialItem edit = new CFXSpeedDialItem("Edit", createEditIcon());
        edit.setTooltipText("Edit document");
        edit.setOnAction(e -> updateStatus("✏️ Edit action clicked"));

        // Share action
        CFXSpeedDialItem share = new CFXSpeedDialItem("Share", createShareIcon());
        share.setTooltipText("Share with others");
        share.setOnAction(e -> updateStatus("📤 Share action clicked"));

        // Download action
        CFXSpeedDialItem download = new CFXSpeedDialItem("Download", createDownloadIcon());
        download.setTooltipText("Download file");
        download.setOnAction(e -> updateStatus("⬇️ Download action clicked"));

        // Settings action
        CFXSpeedDialItem settings = new CFXSpeedDialItem("Settings", createSettingsIcon());
        settings.setTooltipText("Open settings");
        settings.setOnAction(e -> updateStatus("⚙️ Settings action clicked"));

        // Delete action
        CFXSpeedDialItem delete = new CFXSpeedDialItem("Delete", createDeleteIcon());
        delete.setTooltipText("Delete item");
        delete.setOnAction(e -> updateStatus("🗑️ Delete action clicked"));

        dial.getItems().addAll(edit, share, download, settings, delete);
        return dial;
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);

        // Change color based on action
        if (message.contains("Delete")) {
            statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #F44336; " +
                    "-fx-font-weight: 600; -fx-padding: 15px; " +
                    "-fx-background-color: #FFEBEE; -fx-background-radius: 8px;");
        } else if (message.contains("Download") || message.contains("Share")) {
            statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4CAF50; " +
                    "-fx-font-weight: 600; -fx-padding: 15px; " +
                    "-fx-background-color: #E8F5E9; -fx-background-radius: 8px;");
        } else if (message.contains("Settings")) {
            statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #FF9800; " +
                    "-fx-font-weight: 600; -fx-padding: 15px; " +
                    "-fx-background-color: #FFF3E0; -fx-background-radius: 8px;");
        } else {
            statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2196F3; " +
                    "-fx-font-weight: 600; -fx-padding: 15px; " +
                    "-fx-background-color: #E3F2FD; -fx-background-radius: 8px;");
        }
    }

    // Icon creation methods
    private SVGPath createEditIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M 3 17.25 V 21 h 3.75 L 17.81 9.94 l -3.75 -3.75 L 3 17.25 z M 20.71 7.04 c 0.39 -0.39 0.39 -1.02 0 -1.41 l -2.34 -2.34 c -0.39 -0.39 -1.02 -0.39 -1.41 0 l -1.83 1.83 l 3.75 3.75 l 1.83 -1.83 z");
        icon.setFill(Color.web("#2196F3"));
        icon.setScaleX(0.9);
        icon.setScaleY(0.9);
        return icon;
    }

    private SVGPath createShareIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M 18 16.08 c -0.76 0 -1.44 0.3 -1.96 0.77 L 8.91 12.7 c 0.05 -0.23 0.09 -0.46 0.09 -0.7 s -0.04 -0.47 -0.09 -0.7 l 7.05 -4.11 c 0.54 0.5 1.25 0.81 2.04 0.81 c 1.66 0 3 -1.34 3 -3 s -1.34 -3 -3 -3 s -3 1.34 -3 3 c 0 0.24 0.04 0.47 0.09 0.7 L 7.04 9.81 C 6.5 9.31 5.79 9 5 9 c -1.66 0 -3 1.34 -3 3 s 1.34 3 3 3 c 0.79 0 1.5 -0.31 2.04 -0.81 l 7.12 4.16 c -0.05 0.21 -0.08 0.43 -0.08 0.65 c 0 1.61 1.31 2.92 2.92 2.92 s 2.92 -1.31 2.92 -2.92 s -1.31 -2.92 -2.92 -2.92 z");
        icon.setFill(Color.web("#4CAF50"));
        icon.setScaleX(0.9);
        icon.setScaleY(0.9);
        return icon;
    }

    private SVGPath createDownloadIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M 19 9 h -4 V 3 H 9 v 6 H 5 l 7 7 l 7 -7 z M 5 18 v 2 h 14 v -2 H 5 z");
        icon.setFill(Color.web("#00BCD4"));
        icon.setScaleX(0.9);
        icon.setScaleY(0.9);
        return icon;
    }

    private SVGPath createSettingsIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M 19.14 12.94 c 0.04 -0.3 0.06 -0.61 0.06 -0.94 c 0 -0.32 -0.02 -0.64 -0.07 -0.94 l 2.03 -1.58 c 0.18 -0.14 0.23 -0.41 0.12 -0.61 l -1.92 -3.32 c -0.12 -0.22 -0.37 -0.29 -0.59 -0.22 l -2.39 0.96 c -0.5 -0.38 -1.03 -0.7 -1.62 -0.94 L 14.4 2.81 c -0.04 -0.24 -0.24 -0.41 -0.48 -0.41 h -3.84 c -0.24 0 -0.43 0.17 -0.47 0.41 L 9.25 5.35 C 8.66 5.59 8.12 5.92 7.63 6.29 L 5.24 5.33 c -0.22 -0.08 -0.47 0 -0.59 0.22 L 2.74 8.87 C 2.62 9.08 2.66 9.34 2.86 9.48 l 2.03 1.58 C 4.84 11.36 4.8 11.69 4.8 12 s 0.02 0.64 0.07 0.94 l -2.03 1.58 c -0.18 0.14 -0.23 0.41 -0.12 0.61 l 1.92 3.32 c 0.12 0.22 0.37 0.29 0.59 0.22 l 2.39 -0.96 c 0.5 0.38 1.03 0.7 1.62 0.94 l 0.36 2.54 c 0.05 0.24 0.24 0.41 0.48 0.41 h 3.84 c 0.24 0 0.44 -0.17 0.47 -0.41 l 0.36 -2.54 c 0.59 -0.24 1.13 -0.56 1.62 -0.94 l 2.39 0.96 c 0.22 0.08 0.47 0 0.59 -0.22 l 1.92 -3.32 c 0.12 -0.22 0.07 -0.47 -0.12 -0.61 L 19.14 12.94 z M 12 15.6 c -1.98 0 -3.6 -1.62 -3.6 -3.6 s 1.62 -3.6 3.6 -3.6 s 3.6 1.62 3.6 3.6 S 13.98 15.6 12 15.6 z");
        icon.setFill(Color.web("#FF9800"));
        icon.setScaleX(0.75);
        icon.setScaleY(0.75);
        return icon;
    }

    private SVGPath createDeleteIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M 6 19 c 0 1.1 0.9 2 2 2 h 8 c 1.1 0 2 -0.9 2 -2 V 7 H 6 v 12 z M 19 4 h -3.5 l -1 -1 h -5 l -1 1 H 5 v 2 h 14 V 4 z");
        icon.setFill(Color.web("#F44336"));
        icon.setScaleX(0.9);
        icon.setScaleY(0.9);
        return icon;
    }

}