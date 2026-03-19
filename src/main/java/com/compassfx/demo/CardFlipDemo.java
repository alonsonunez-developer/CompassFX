package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CardFlipDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("CFXCard & CFXFlipCard Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        // ====================================
        // Card Variants
        // ====================================
        Label variantsLabel = new Label("Card Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        HBox variantsBox = new HBox(20);
        variantsBox.setAlignment(Pos.CENTER);

        CFXCard filledCard = createSimpleCard("Filled Variant", "Default style with solid background");
        filledCard.setVariant(CardVariant.FILLED);

        CFXCard outlinedCard = createSimpleCard("Outlined Variant", "Border only, transparent background");
        outlinedCard.setVariant(CardVariant.OUTLINED);

        CFXCard elevatedCard = createSimpleCard("Elevated Variant", "Enhanced shadow effect");
        elevatedCard.setVariant(CardVariant.ELEVATED);

        variantsBox.getChildren().addAll(filledCard, outlinedCard, elevatedCard);

        // ====================================
        // Dark Mode Cards
        // ====================================
        Label darkLabel = new Label("Dark Mode");
        darkLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        HBox darkBox = new HBox(20);
        darkBox.setAlignment(Pos.CENTER);
        darkBox.setStyle("-fx-background-color: #121212; -fx-padding: 20px; -fx-background-radius: 8px;");

        CFXCard darkFilled = createSimpleCard("Dark Filled", "Dark mode with filled variant");
        darkFilled.setDarkMode(true);
        darkFilled.setVariant(CardVariant.FILLED);

        CFXCard darkOutlined = createSimpleCard("Dark Outlined", "Dark mode with outlined variant");
        darkOutlined.setDarkMode(true);
        darkOutlined.setVariant(CardVariant.OUTLINED);

        CFXCard darkElevated = createSimpleCard("Dark Elevated", "Dark mode with elevated variant");
        darkElevated.setDarkMode(true);
        darkElevated.setVariant(CardVariant.ELEVATED);

        darkBox.getChildren().addAll(darkFilled, darkOutlined, darkElevated);

        // ====================================
        // Flip Cards
        // ====================================
        Label flipLabel = new Label("Flip Cards (Click to Flip)");
        flipLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        HBox flipBox = new HBox(20);
        flipBox.setAlignment(Pos.CENTER);

        // Horizontal flip
        CFXFlipCard horizontalFlip = new CFXFlipCard();
        horizontalFlip.setFrontCard(createFlipFrontCard("Horizontal Flip", "Click to see back", "#2196F3"));
        horizontalFlip.setBackCard(createFlipBackCard("Back Side", "Click to return", "#FF9800"));
        horizontalFlip.setFlipDirection(CFXFlipCard.FlipDirection.HORIZONTAL);

        // Vertical flip
        CFXFlipCard verticalFlip = new CFXFlipCard();
        verticalFlip.setFrontCard(createFlipFrontCard("Vertical Flip", "Click to flip up", "#4CAF50"));
        verticalFlip.setBackCard(createFlipBackCard("Flipped!", "Click to flip down", "#E91E63"));
        verticalFlip.setFlipDirection(CFXFlipCard.FlipDirection.VERTICAL);

        // Profile flip card
        CFXFlipCard profileFlip = new CFXFlipCard();
        profileFlip.setFrontCard(createProfileFront());
        profileFlip.setBackCard(createProfileBack());

        flipBox.getChildren().addAll(horizontalFlip, verticalFlip, profileFlip);

        // ====================================
        // Dividers
        // ====================================
        Label dividerLabel = new Label("Dividers");
        dividerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        VBox dividerBox = new VBox(15);
        dividerBox.setAlignment(Pos.CENTER);
        dividerBox.setPrefWidth(600);

        Label text1 = new Label("Default Divider");
        CFXDivider div1 = new CFXDivider();

        Label text2 = new Label("Thick Divider");
        CFXDivider div2 = new CFXDivider();
        div2.setThickness(3);

        Label text3 = new Label("Dashed Divider");
        CFXDivider div3 = new CFXDivider();
        div3.setDashed(true);

        dividerBox.getChildren().addAll(text1, div1, text2, div2, text3, div3);

        // ====================================
        // Programmatic Flip Control
        // ====================================
        Label controlLabel = new Label("Programmatic Control");
        controlLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXFlipCard controlledFlip = new CFXFlipCard();
        controlledFlip.setFrontCard(createSimpleCard("Front", "Use buttons below to flip"));
        controlledFlip.setBackCard(createSimpleCard("Back", "Flipped programmatically!"));
        controlledFlip.setFlipOnClick(false); // Disable auto-flip on click

        HBox controlButtons = new HBox(10);
        controlButtons.setAlignment(Pos.CENTER);

        CFXButton flipBtn = new CFXButton("Flip");
        flipBtn.setVariant(ButtonVariant.CONTAINED);
        flipBtn.setOnAction(e -> controlledFlip.flip());

        CFXButton frontBtn = new CFXButton("Show Front");
        frontBtn.setVariant(ButtonVariant.OUTLINED);
        frontBtn.setOnAction(e -> controlledFlip.setFlipped(false));

        CFXButton backBtn = new CFXButton("Show Back");
        backBtn.setVariant(ButtonVariant.OUTLINED);
        backBtn.setOnAction(e -> controlledFlip.setFlipped(true));

        controlButtons.getChildren().addAll(flipBtn, frontBtn, backBtn);

        root.getChildren().addAll(
                title,
                new CFXDivider(),
                variantsLabel,
                variantsBox,
                new CFXDivider(),
                darkLabel,
                darkBox,
                new CFXDivider(),
                flipLabel,
                flipBox,
                new CFXDivider(),
                dividerLabel,
                dividerBox,
                new CFXDivider(),
                controlLabel,
                controlledFlip,
                controlButtons
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #F5F5F5; -fx-background-color: #F5F5F5;");

        Scene scene = new Scene(scrollPane, 1000, 900);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CFXCard & CFXFlipCard Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CFXCard createSimpleCard(String title, String description) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(250);

        VBox content = new VBox(8);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");
        Label desc = new Label(description);
        desc.setWrapText(true);
        desc.setStyle("-fx-font-size: 13px;");
        content.getChildren().addAll(titleLabel, desc);

        card.setContent(content);
        return card;
    }

    private CFXCard createFlipFrontCard(String title, String subtitle, String color) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(250);
        card.setPrefHeight(300);

        Rectangle media = new Rectangle(250, 150);
        media.setFill(Color.web(color));
        card.setMedia(media);

        VBox content = new VBox(12);
        content.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        Label icon = new Label("🔄");
        icon.setStyle("-fx-font-size: 32px;");

        content.getChildren().addAll(titleLabel, subtitleLabel, icon);
        card.setContent(content);

        return card;
    }

    private CFXCard createFlipBackCard(String title, String subtitle, String color) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(250);
        card.setPrefHeight(300);

        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 8px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Label icon = new Label("✓");
        icon.setStyle("-fx-font-size: 48px; -fx-text-fill: white;");

        content.getChildren().addAll(icon, titleLabel, subtitleLabel);
        card.setContent(content);

        return card;
    }

    private CFXCard createProfileFront() {
        CFXCard card = new CFXCard();
        card.setPrefWidth(280);
        card.setPrefHeight(320);

        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        Label avatar = new Label("👤");
        avatar.setStyle("-fx-font-size: 80px;");

        Label name = new Label("John Doe");
        name.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label role = new Label("Software Developer");
        role.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        Label hint = new Label("Click to see details");
        hint.setStyle("-fx-font-size: 12px; -fx-text-fill: #2196F3;");

        content.getChildren().addAll(avatar, name, role, hint);
        card.setContent(content);

        return card;
    }

    private CFXCard createProfileBack() {
        CFXCard card = new CFXCard();
        card.setPrefWidth(280);
        card.setPrefHeight(320);

        VBox content = new VBox(12);
        content.setPadding(new Insets(30));

        Label title = new Label("Contact Info");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label email = new Label("📧 john.doe@email.com");
        email.setStyle("-fx-font-size: 13px;");

        Label phone = new Label("📱 +1 (555) 123-4567");
        phone.setStyle("-fx-font-size: 13px;");

        Label location = new Label("📍 San Francisco, CA");
        location.setStyle("-fx-font-size: 13px;");

        Label website = new Label("🌐 johndoe.dev");
        website.setStyle("-fx-font-size: 13px;");

        Label hint = new Label("Click to return");
        hint.setStyle("-fx-font-size: 12px; -fx-text-fill: #2196F3; -fx-padding: 10px 0 0 0;");

        content.getChildren().addAll(title, email, phone, location, website, hint);
        card.setContent(content);

        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}