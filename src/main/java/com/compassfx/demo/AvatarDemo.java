// ============================================
// AvatarDemo.java - Avatar Demo Application
// src/main/java/com/compassfx/demo/AvatarDemo.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXAvatar;
import com.compassfx.enums.AvatarShape;
import com.compassfx.enums.AvatarSize;
import com.compassfx.enums.AvatarVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AvatarDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(40);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("CompassFX Avatar Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Sizes
        // ====================================
        Label sizesLabel = new Label("Avatar Sizes");
        sizesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox sizesBox = new HBox(20);
        sizesBox.setAlignment(Pos.CENTER);

        VBox xsBox = createLabeledAvatar("XS", "AB", AvatarSize.EXTRA_SMALL);
        VBox smallBox = createLabeledAvatar("Small", "CD", AvatarSize.SMALL);
        VBox mediumBox = createLabeledAvatar("Medium", "EF", AvatarSize.MEDIUM);
        VBox largeBox = createLabeledAvatar("Large", "GH", AvatarSize.LARGE);
        VBox xlBox = createLabeledAvatar("XL", "IJ", AvatarSize.EXTRA_LARGE);
        VBox hugeBox = createLabeledAvatar("Huge", "KL", AvatarSize.HUGE);

        sizesBox.getChildren().addAll(xsBox, smallBox, mediumBox, largeBox, xlBox, hugeBox);

        // ====================================
        // Shapes
        // ====================================
        Label shapesLabel = new Label("Avatar Shapes");
        shapesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox shapesBox = new HBox(30);
        shapesBox.setAlignment(Pos.CENTER);

        VBox circleBox = new VBox(10);
        circleBox.setAlignment(Pos.CENTER);
        Label circleLabel = new Label("Circle");
        CFXAvatar circleAvatar = new CFXAvatar("JD");
        circleAvatar.setAvatarShape(AvatarShape.CIRCLE);
        circleAvatar.setAvatarSize(AvatarSize.LARGE);
        circleBox.getChildren().addAll(circleAvatar, circleLabel);

        VBox roundedBox = new VBox(10);
        roundedBox.setAlignment(Pos.CENTER);
        Label roundedLabel = new Label("Rounded");
        CFXAvatar roundedAvatar = new CFXAvatar("AS");
        roundedAvatar.setAvatarShape(AvatarShape.ROUNDED);
        roundedAvatar.setAvatarSize(AvatarSize.LARGE);
        roundedBox.getChildren().addAll(roundedAvatar, roundedLabel);

        VBox squareBox = new VBox(10);
        squareBox.setAlignment(Pos.CENTER);
        Label squareLabel = new Label("Square");
        CFXAvatar squareAvatar = new CFXAvatar("MK");
        squareAvatar.setAvatarShape(AvatarShape.SQUARE);
        squareAvatar.setAvatarSize(AvatarSize.LARGE);
        squareBox.getChildren().addAll(squareAvatar, squareLabel);

        shapesBox.getChildren().addAll(circleBox, roundedBox, squareBox);

        // ====================================
        // Variants
        // ====================================
        Label variantsLabel = new Label("Avatar Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox variantsBox = new HBox(30);
        variantsBox.setAlignment(Pos.CENTER);

        VBox filledBox = new VBox(10);
        filledBox.setAlignment(Pos.CENTER);
        Label filledLabel = new Label("Filled");
        CFXAvatar filledAvatar = new CFXAvatar("FI");
        filledAvatar.setVariant(AvatarVariant.FILLED);
        filledAvatar.setAvatarSize(AvatarSize.LARGE);
        filledBox.getChildren().addAll(filledAvatar, filledLabel);

        VBox outlinedBox = new VBox(10);
        outlinedBox.setAlignment(Pos.CENTER);
        Label outlinedLabel = new Label("Outlined");
        CFXAvatar outlinedAvatar = new CFXAvatar("OU");
        outlinedAvatar.setVariant(AvatarVariant.OUTLINED);
        outlinedAvatar.setAvatarSize(AvatarSize.LARGE);
        outlinedBox.getChildren().addAll(outlinedAvatar, outlinedLabel);

        VBox lightBox = new VBox(10);
        lightBox.setAlignment(Pos.CENTER);
        Label lightLabel = new Label("Light");
        CFXAvatar lightAvatar = new CFXAvatar("LI");
        lightAvatar.setVariant(AvatarVariant.LIGHT);
        lightAvatar.setAvatarSize(AvatarSize.LARGE);
        lightBox.getChildren().addAll(lightAvatar, lightLabel);

        variantsBox.getChildren().addAll(filledBox, outlinedBox, lightBox);

        // ====================================
        // Colors
        // ====================================
        Label colorsLabel = new Label("Avatar Colors");
        colorsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox colorsBox = new HBox(20);
        colorsBox.setAlignment(Pos.CENTER);

        CFXAvatar blueAvatar = new CFXAvatar("BL");
        blueAvatar.setColor(Color.web("#2196F3"));
        blueAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar greenAvatar = new CFXAvatar("GR");
        greenAvatar.setColor(Color.web("#4CAF50"));
        greenAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar orangeAvatar = new CFXAvatar("OR");
        orangeAvatar.setColor(Color.web("#FF9800"));
        orangeAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar redAvatar = new CFXAvatar("RD");
        redAvatar.setColor(Color.web("#F44336"));
        redAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar purpleAvatar = new CFXAvatar("PU");
        purpleAvatar.setColor(Color.web("#9C27B0"));
        purpleAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar pinkAvatar = new CFXAvatar("PI");
        pinkAvatar.setColor(Color.web("#E91E63"));
        pinkAvatar.setAvatarSize(AvatarSize.LARGE);

        colorsBox.getChildren().addAll(
                blueAvatar, greenAvatar, orangeAvatar,
                redAvatar, purpleAvatar, pinkAvatar
        );

        // ====================================
        // With Status Indicator
        // ====================================
        Label statusLabel = new Label("With Status Indicator");
        statusLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox statusBox = new HBox(30);
        statusBox.setAlignment(Pos.CENTER);

        VBox onlineBox = new VBox(10);
        onlineBox.setAlignment(Pos.CENTER);
        Label onlineLabel = new Label("Online");
        CFXAvatar onlineAvatar = new CFXAvatar("ON");
        onlineAvatar.setAvatarSize(AvatarSize.LARGE);
        onlineAvatar.setShowStatus(true);
        onlineAvatar.setStatusColor(Color.web("#4CAF50"));
        onlineBox.getChildren().addAll(onlineAvatar, onlineLabel);

        VBox awayBox = new VBox(10);
        awayBox.setAlignment(Pos.CENTER);
        Label awayLabel = new Label("Away");
        CFXAvatar awayAvatar = new CFXAvatar("AW");
        awayAvatar.setAvatarSize(AvatarSize.LARGE);
        awayAvatar.setShowStatus(true);
        awayAvatar.setStatusColor(Color.web("#FF9800"));
        awayBox.getChildren().addAll(awayAvatar, awayLabel);

        VBox busyBox = new VBox(10);
        busyBox.setAlignment(Pos.CENTER);
        Label busyLabel = new Label("Busy");
        CFXAvatar busyAvatar = new CFXAvatar("BS");
        busyAvatar.setAvatarSize(AvatarSize.LARGE);
        busyAvatar.setShowStatus(true);
        busyAvatar.setStatusColor(Color.web("#F44336"));
        busyBox.getChildren().addAll(busyAvatar, busyLabel);

        VBox offlineBox = new VBox(10);
        offlineBox.setAlignment(Pos.CENTER);
        Label offlineLabel = new Label("Offline");
        CFXAvatar offlineAvatar = new CFXAvatar("OF");
        offlineAvatar.setAvatarSize(AvatarSize.LARGE);
        offlineAvatar.setShowStatus(true);
        offlineAvatar.setStatusColor(Color.web("#9E9E9E"));
        offlineBox.getChildren().addAll(offlineAvatar, offlineLabel);

        statusBox.getChildren().addAll(onlineBox, awayBox, busyBox, offlineBox);

        // ====================================
        // With Border
        // ====================================
        Label borderLabel = new Label("With Border");
        borderLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox borderBox = new HBox(30);
        borderBox.setAlignment(Pos.CENTER);

        CFXAvatar border1 = new CFXAvatar("B1");
        border1.setAvatarSize(AvatarSize.LARGE);
        border1.setShowBorder(true);
        border1.setBorderWidth(3);
        border1.setBorderColor(Color.WHITE);

        CFXAvatar border2 = new CFXAvatar("B2");
        border2.setAvatarSize(AvatarSize.LARGE);
        border2.setShowBorder(true);
        border2.setBorderWidth(4);
        border2.setBorderColor(Color.web("#FFD700"));
        border2.setColor(Color.web("#9C27B0"));

        CFXAvatar border3 = new CFXAvatar("B3");
        border3.setAvatarSize(AvatarSize.LARGE);
        border3.setShowBorder(true);
        border3.setBorderWidth(3);
        border3.setBorderColor(Color.web("#4CAF50"));
        border3.setColor(Color.web("#FF9800"));

        borderBox.getChildren().addAll(border1, border2, border3);

        // ====================================
        // User List Example
        // ====================================
        Label userListLabel = new Label("User List Example");
        userListLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox userList = new VBox(15);
        userList.setMaxWidth(400);
        userList.setStyle(
                "-fx-background-color: white; " +
                        "-fx-padding: 20px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 12px;"
        );

        userList.getChildren().addAll(
                createUserItem("John Doe", "john@example.com", Color.web("#2196F3"), true),
                createUserItem("Jane Smith", "jane@example.com", Color.web("#4CAF50"), true),
                createUserItem("Bob Johnson", "bob@example.com", Color.web("#FF9800"), false),
                createUserItem("Alice Williams", "alice@example.com", Color.web("#9C27B0"), true)
        );

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                sizesLabel,
                sizesBox,
                new Separator(),
                shapesLabel,
                shapesBox,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                colorsLabel,
                colorsBox,
                new Separator(),
                statusLabel,
                statusBox,
                new Separator(),
                borderLabel,
                borderBox,
                new Separator(),
                userListLabel,
                userList
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #F5F5F5; -fx-background-color: #F5F5F5;");

        Scene scene = new Scene(scrollPane, 1000, 900);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Avatar Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static VBox createLabeledAvatar(String label, String initials, AvatarSize size) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        CFXAvatar avatar = new CFXAvatar(initials);
        avatar.setAvatarSize(size);

        Label labelNode = new Label(label);
        labelNode.setStyle("-fx-font-size: 12px;");

        box.getChildren().addAll(avatar, labelNode);
        return box;
    }

    static HBox createUserItem(String name, String email, Color color, boolean online) {
        HBox userItem = new HBox(15);
        userItem.setAlignment(Pos.CENTER_LEFT);

        CFXAvatar avatar = new CFXAvatar(name);
        avatar.setAvatarSize(AvatarSize.LARGE);
        avatar.setColor(color);
        avatar.setShowStatus(true);
        avatar.setStatusColor(online ? Color.web("#4CAF50") : Color.web("#9E9E9E"));

        VBox info = new VBox(3);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label emailLabel = new Label(email);
        emailLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");
        info.getChildren().addAll(nameLabel, emailLabel);

        userItem.getChildren().addAll(avatar, info);
        return userItem;
    }

    public static void main(String[] args) {
        launch(args);
    }
}