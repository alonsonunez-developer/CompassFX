package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.DockPosition;
import com.compassfx.models.CFXDockItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DockDemo {

    private Label eventLog;

    public void showDemo(Label title, VBox root) {
        // Event log
        eventLog = new Label("Click on dock items to see actions");
        eventLog.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-padding: 15px 25px; " +
                        "-fx-background-color: rgba(255,255,255,0.9); " +
                        "-fx-background-radius: 12px; " +
                        "-fx-text-fill: #333; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.3, 0, 2);"
        );

        // Info
        Label info = new Label("💡 Hover over icons to see the magnification effect!");
        info.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill: #1565C0; " +
                        "-fx-padding: 12px 20px; " +
                        "-fx-background-color: rgba(255,255,255,0.8); " +
                        "-fx-background-radius: 10px;"
        );

        // ====================================
        // Main Dock (Bottom)
        // ====================================
        Label bottomLabel = new Label("macOS Style Dock");
        bottomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #1976D2;");

        CFXDock bottomDock = new CFXDock();
        bottomDock.setPosition(DockPosition.BOTTOM);
        bottomDock.setIconSize(64);
        bottomDock.setMagnification(1.6);
        bottomDock.setSpacing(12);

        // Add items one by one
        bottomDock.getItems().add(createDockItem("Finder", createEmojiIcon("📁", 40, "#2196F3"), () -> log("Opened Finder")));
        bottomDock.getItems().add(createDockItem("Safari", createEmojiIcon("🌐", 40, "#00BCD4"), () -> log("Opened Safari")));
        bottomDock.getItems().add(createDockItem("Messages", createEmojiIcon("💬", 40, "#4CAF50"), () -> log("Opened Messages"), 5));
        bottomDock.getItems().add(createDockItem("Mail", createEmojiIcon("✉️", 40, "#2196F3"), () -> log("Opened Mail"), 12));
        bottomDock.getItems().add(createDockItem("Music", createEmojiIcon("🎵", 40, "#E91E63"), () -> log("Opened Music")));
        bottomDock.getItems().add(createDockItem("Photos", createEmojiIcon("🖼️", 40, "#FF9800"), () -> log("Opened Photos")));
        bottomDock.getItems().add(createDockItem("Calendar", createEmojiIcon("📅", 40, "#F44336"), () -> log("Opened Calendar")));
        bottomDock.getItems().add(createDockItem("Settings", createEmojiIcon("⚙️", 40, "#9E9E9E"), () -> log("Opened Settings")));
        bottomDock.getItems().add(createDockItem("Trash", createEmojiIcon("🗑️", 40, "#757575"), () -> log("Opened Trash")));

        // ====================================
        // Side Docks Comparison
        // ====================================
        Label sideLabel = new Label("Different Positions");
        sideLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #1976D2;");

        HBox sideDocks = new HBox(40);
        sideDocks.setAlignment(Pos.CENTER);

        // Left dock
        VBox leftBox = new VBox(15);
        leftBox.setAlignment(Pos.CENTER);
        Label leftLabel = new Label("LEFT");
        leftLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #1976D2;");

        CFXDock leftDock = new CFXDock();
        leftDock.setPosition(DockPosition.LEFT);
        leftDock.setIconSize(48);

        leftDock.getItems().add(createDockItem("Home", createEmojiIcon("🏠", 32, "#2196F3"), () -> log("Home")));
        leftDock.getItems().add(createDockItem("Search", createEmojiIcon("🔍", 32, "#FF9800"), () -> log("Search")));
        leftDock.getItems().add(createDockItem("Library", createEmojiIcon("📚", 32, "#9C27B0"), () -> log("Library")));
        leftDock.getItems().add(createDockItem("Add", createEmojiIcon("➕", 32, "#4CAF50"), () -> log("Add")));

        leftBox.getChildren().addAll(leftLabel, leftDock);

        // Right dock
        VBox rightBox = new VBox(15);
        rightBox.setAlignment(Pos.CENTER);
        Label rightLabel = new Label("RIGHT");
        rightLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #1976D2;");

        CFXDock rightDock = new CFXDock();
        rightDock.setPosition(DockPosition.RIGHT);
        rightDock.setIconSize(48);

        rightDock.getItems().add(createDockItem("Profile", createEmojiIcon("👤", 32, "#2196F3"), () -> log("Profile")));
        rightDock.getItems().add(createDockItem("Notifications", createEmojiIcon("🔔", 32, "#FF9800"), () -> log("Notifications"), 3));
        rightDock.getItems().add(createDockItem("Settings", createEmojiIcon("⚙️", 32, "#9E9E9E"), () -> log("Settings")));
        rightDock.getItems().add(createDockItem("Logout", createEmojiIcon("🚪", 32, "#F44336"), () -> log("Logout")));

        rightBox.getChildren().addAll(rightLabel, rightDock);

        sideDocks.getChildren().addAll(leftBox, rightBox);

        // ====================================
        // Controls
        // ====================================
        Label controlsLabel = new Label("Controls");
        controlsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #1976D2;");

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);

        CFXButton toggleAnim = new CFXButton("Toggle Animations");
        toggleAnim.setVariant(ButtonVariant.CONTAINED);
        toggleAnim.setOnAction(e -> {
            bottomDock.setAnimated(!bottomDock.isAnimated());
            leftDock.setAnimated(!leftDock.isAnimated());
            rightDock.setAnimated(!rightDock.isAnimated());
        });

        CFXButton addItem = new CFXButton("Add Item");
        addItem.setVariant(ButtonVariant.OUTLINED);
        addItem.setOnAction(e -> {
            bottomDock.getItems().add(createDockItem("New App", createEmojiIcon("⭐", 40, "#FFC107"), () -> log("New App!")));
        });

        CFXButton removeItem = new CFXButton("Remove Last");
        removeItem.setVariant(ButtonVariant.TEXT);
        removeItem.setOnAction(e -> {
            if (!bottomDock.getItems().isEmpty()) {
                bottomDock.getItems().remove(bottomDock.getItems().size() - 1);
            }
        });

        controls.getChildren().addAll(toggleAnim, addItem, removeItem);

        root.getChildren().addAll(
                title,
                info,
                new Separator(),
                bottomLabel,
                bottomDock,
                eventLog,
                new Separator(),
                sideLabel,
                sideDocks,
                new Separator(),
                controlsLabel,
                controls
        );
    }

    private void log(String message) {
        eventLog.setText("Action: " + message);
    }

    private CFXDockItem createDockItem(String label, StackPane icon, Runnable action) {
        return new CFXDockItem(label, icon, action);
    }

    private CFXDockItem createDockItem(String label, StackPane icon, Runnable action, int badgeCount) {
        CFXDockItem item = new CFXDockItem(label, icon, action);
        item.setBadgeCount(badgeCount);
        return item;
    }

    private StackPane createEmojiIcon(String emoji, int size, String bgColor) {
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(size));
        emojiLabel.setAlignment(Pos.CENTER);

        StackPane container = new StackPane(emojiLabel);
        container.setMinSize(size + 16, size + 16);
        container.setMaxSize(size + 16, size + 16);
        container.setPrefSize(size + 16, size + 16);
        container.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 12px;"
        );

        return container;
    }

}