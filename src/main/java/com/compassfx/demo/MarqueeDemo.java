package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MarqueeDemo {


    public void showDemo(Label title, VBox root) {
        // ====================================
        // Cards Marquee (Right to Left)
        // ====================================
        Label cardsLabel = new Label("Product Showcase");
        cardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMarquee cardsMarquee = new CFXMarquee();
        cardsMarquee.setPrefHeight(280);
        cardsMarquee.setMaxHeight(280);
        cardsMarquee.setDirection(MarqueeDirection.RIGHT_TO_LEFT);
        cardsMarquee.setSpeed(30);
        cardsMarquee.setSpacing(20);

        cardsMarquee.setItems(FXCollections.observableArrayList(
                createProductCard("MacBook Pro", "$2,499", "#2196F3"),
                createProductCard("iPhone 15", "$999", "#4CAF50"),
                createProductCard("iPad Air", "$599", "#FF9800"),
                createProductCard("Apple Watch", "$399", "#9C27B0"),
                createProductCard("AirPods Pro", "$249", "#F44336"),
                createProductCard("iMac", "$1,299", "#00BCD4")
        ));

        // ====================================
        // Logo Marquee (Left to Right)
        // ====================================
        Label logosLabel = new Label("Partner Logos");
        logosLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMarquee logosMarquee = new CFXMarquee();
        logosMarquee.setPrefHeight(120);
        logosMarquee.setMaxHeight(120);
        logosMarquee.setDirection(MarqueeDirection.LEFT_TO_RIGHT);
        logosMarquee.setSpeed(40);
        logosMarquee.setSpacing(40);

        logosMarquee.setItems(FXCollections.observableArrayList(
                createLogoBox("Google", "#4285F4"),
                createLogoBox("Microsoft", "#00A4EF"),
                createLogoBox("Amazon", "#FF9900"),
                createLogoBox("Meta", "#0668E1"),
                createLogoBox("Apple", "#555555"),
                createLogoBox("Netflix", "#E50914")
        ));

        // ====================================
        // Testimonials Marquee
        // ====================================
        Label testimonialsLabel = new Label("Customer Testimonials");
        testimonialsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMarquee testimonialsMarquee = new CFXMarquee();
        testimonialsMarquee.setPrefHeight(200);
        testimonialsMarquee.setMaxHeight(200);
        testimonialsMarquee.setDirection(MarqueeDirection.RIGHT_TO_LEFT);
        testimonialsMarquee.setSpeed(25);
        testimonialsMarquee.setSpacing(20);

        testimonialsMarquee.setItems(FXCollections.observableArrayList(
                createTestimonialCard("Amazing product!", "Sarah J.", "⭐⭐⭐⭐⭐"),
                createTestimonialCard("Best purchase ever", "Mike D.", "⭐⭐⭐⭐⭐"),
                createTestimonialCard("Highly recommended", "Emma W.", "⭐⭐⭐⭐⭐"),
                createTestimonialCard("Excellent quality", "John S.", "⭐⭐⭐⭐⭐"),
                createTestimonialCard("Love it!", "Lisa M.", "⭐⭐⭐⭐⭐")
        ));

        // ====================================
        // Stats Marquee (Fast)
        // ====================================
        Label statsLabel = new Label("Live Stats");
        statsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMarquee statsMarquee = new CFXMarquee();
        statsMarquee.setPrefHeight(120);
        statsMarquee.setMaxHeight(120);
        statsMarquee.setDirection(MarqueeDirection.RIGHT_TO_LEFT);
        statsMarquee.setSpeed(60);
        statsMarquee.setSpacing(30);

        statsMarquee.setItems(FXCollections.observableArrayList(
                createStatBox("1M+", "Users", "#2196F3"),
                createStatBox("50K+", "Downloads", "#4CAF50"),
                createStatBox("4.9★", "Rating", "#FF9800"),
                createStatBox("24/7", "Support", "#9C27B0"),
                createStatBox("99.9%", "Uptime", "#00BCD4")
        ));

        // ====================================
        // Controls
        // ====================================
        Label controlsLabel = new Label("Controls");
        controlsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);

        CFXButton pauseAll = new CFXButton("Pause All");
        pauseAll.setVariant(ButtonVariant.OUTLINED);
        pauseAll.setOnAction(e -> {
            cardsMarquee.pause();
            logosMarquee.pause();
            testimonialsMarquee.pause();
            statsMarquee.pause();
        });

        CFXButton resumeAll = new CFXButton("Resume All");
        resumeAll.setVariant(ButtonVariant.CONTAINED);
        resumeAll.setOnAction(e -> {
            cardsMarquee.resume();
            logosMarquee.resume();
            testimonialsMarquee.resume();
            statsMarquee.resume();
        });

        CFXButton reverseDirection = new CFXButton("Reverse Direction");
        reverseDirection.setVariant(ButtonVariant.TEXT);
        reverseDirection.setOnAction(e -> {
            toggleDirection(cardsMarquee);
            toggleDirection(logosMarquee);
            toggleDirection(testimonialsMarquee);
            toggleDirection(statsMarquee);
        });

        controls.getChildren().addAll(pauseAll, resumeAll, reverseDirection);

        // Info
        Label info = new Label("💡 Hover over any marquee to pause it");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-padding: 10px; " +
                "-fx-background-color: #FFF3E0; -fx-background-radius: 8px;");

        root.getChildren().addAll(
                title,
                info,
                new Separator(),
                cardsLabel,
                cardsMarquee,
                new Separator(),
                logosLabel,
                logosMarquee,
                new Separator(),
                testimonialsLabel,
                testimonialsMarquee,
                new Separator(),
                statsLabel,
                statsMarquee,
                new Separator(),
                controlsLabel,
                controls
        );
    }

    private CFXCard createProductCard(String name, String price, String color) {
        CFXCard card = new CFXCard();
        card.setElevation(CardElevation.MEDIUM);
        // FIXED SIZE - all cards same size
        card.setMinWidth(220);
        card.setMaxWidth(220);
        card.setPrefWidth(220);
        card.setMinHeight(240);
        card.setMaxHeight(240);
        card.setPrefHeight(240);

        // Header with color
        VBox header = new VBox(5);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);
        header.setMinHeight(120);
        header.setMaxHeight(120);
        header.setStyle("-fx-background-color: " + color + ";");

        Label icon = new Label("📦");
        icon.setStyle("-fx-font-size: 48px;");

        header.getChildren().add(icon);
        card.setMedia(header);

        // Content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");
        nameLabel.setMaxWidth(180);
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        content.getChildren().addAll(nameLabel, priceLabel);
        card.setContent(content);

        return card;
    }

    private StackPane createLogoBox(String name, String color) {
        StackPane box = new StackPane();
        // FIXED SIZE - all logos same size
        box.setMinSize(160, 80);
        box.setMaxSize(160, 80);
        box.setPrefSize(160, 80);
        box.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0.2, 0, 2);"
        );

        Label label = new Label(name);
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        box.getChildren().add(label);
        return box;
    }

    private CFXCard createTestimonialCard(String quote, String author, String rating) {
        CFXCard card = new CFXCard();
        card.setElevation(CardElevation.LOW);
        // FIXED SIZE - all testimonials same size
        card.setMinWidth(300);
        card.setMaxWidth(300);
        card.setPrefWidth(300);
        card.setMinHeight(180);
        card.setMaxHeight(180);
        card.setPrefHeight(180);

        VBox content = new VBox(12);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);

        Label ratingLabel = new Label(rating);
        ratingLabel.setStyle("-fx-font-size: 16px;");

        Label quoteLabel = new Label("\"" + quote + "\"");
        quoteLabel.setStyle("-fx-font-size: 15px; -fx-font-style: italic;");
        quoteLabel.setWrapText(true);
        quoteLabel.setMaxWidth(260);

        Label authorLabel = new Label("- " + author);
        authorLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 600; -fx-text-fill: #666;");

        content.getChildren().addAll(ratingLabel, quoteLabel, authorLabel);
        card.setContent(content);

        return card;
    }

    private VBox createStatBox(String value, String label, String color) {
        VBox box = new VBox(8);
        // FIXED SIZE - all stats same size
        box.setMinSize(150, 80);
        box.setMaxSize(150, 80);
        box.setPrefSize(150, 80);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + color + "; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0.3, 0, 2);"
        );

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 13px; -fx-text-fill: #666; -fx-font-weight: 500;");

        box.getChildren().addAll(valueLabel, labelText);
        return box;
    }

    private void toggleDirection(CFXMarquee marquee) {
        MarqueeDirection current = marquee.getDirection();
        if (current == MarqueeDirection.RIGHT_TO_LEFT) {
            marquee.setDirection(MarqueeDirection.LEFT_TO_RIGHT);
        } else {
            marquee.setDirection(MarqueeDirection.RIGHT_TO_LEFT);
        }
    }

}