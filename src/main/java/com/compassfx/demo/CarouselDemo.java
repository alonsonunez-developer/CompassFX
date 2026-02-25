package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXCarousel;
import com.compassfx.enums.CarouselIndicatorStyle;
import com.compassfx.enums.CarouselTransition;
import com.compassfx.models.CarouselItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CarouselDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(40);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("CompassFX Carousel Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Basic Text Carousel with Slide Transition
        // ====================================
        Label slideLabel = new Label("Text Carousel - Slide Transition");
        slideLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel slideCarousel = new CFXCarousel();
        slideCarousel.setTransition(CarouselTransition.SLIDE);
        slideCarousel.addTextSlide(
                "Welcome to CompassFX",
                "A modern JavaFX UI library with Material Design components"
        );
        slideCarousel.addTextSlide(
                "Beautiful Components",
                "Pre-built, customizable UI components for your applications"
        );
        slideCarousel.addTextSlide(
                "Easy to Use",
                "Simple API with comprehensive documentation and examples"
        );
        slideCarousel.addTextSlide(
                "Get Started Today",
                "Download now and start building amazing interfaces"
        );

        // ====================================
        // Fade Transition with Auto-Play
        // ====================================
        Label fadeLabel = new Label("Auto-Play Carousel - Fade Transition");
        fadeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel fadeCarousel = new CFXCarousel();
        fadeCarousel.setTransition(CarouselTransition.FADE);
        fadeCarousel.setAutoPlay(true);
        fadeCarousel.setAutoPlayDelay(Duration.seconds(2));

        fadeCarousel.addTextSlide("Slide 1", "This carousel auto-plays with fade transitions");
        fadeCarousel.addTextSlide("Slide 2", "Watch it automatically change every 2 seconds");
        fadeCarousel.addTextSlide("Slide 3", "Click the navigation buttons to control it manually");

        // ====================================
        // Scale Transition
        // ====================================
        Label scaleLabel = new Label("Scale Transition");
        scaleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel scaleCarousel = new CFXCarousel();
        scaleCarousel.setTransition(CarouselTransition.SCALE);

        scaleCarousel.addTextSlide("Feature 1", "Zoom in and out transitions");
        scaleCarousel.addTextSlide("Feature 2", "Smooth scaling animations");
        scaleCarousel.addTextSlide("Feature 3", "Modern visual effects");

        // ====================================
        // Different Indicator Styles
        // ====================================
        Label indicatorsLabel = new Label("Different Indicator Styles");
        indicatorsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox indicatorsBox = new VBox(20);
        indicatorsBox.setAlignment(Pos.CENTER);

        // Dots (default)
        Label dotsLabel = new Label("Dots Indicators:");
        dotsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCarousel dotsCarousel = new CFXCarousel();
        dotsCarousel.setIndicatorStyle(CarouselIndicatorStyle.DOTS);
        dotsCarousel.setItemHeight(250);
        dotsCarousel.addTextSlide("Slide 1", "Using dot indicators");
        dotsCarousel.addTextSlide("Slide 2", "Most common style");
        dotsCarousel.addTextSlide("Slide 3", "Clean and minimal");

        // Lines
        Label linesLabel = new Label("Line Indicators:");
        linesLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCarousel linesCarousel = new CFXCarousel();
        linesCarousel.setIndicatorStyle(CarouselIndicatorStyle.LINES);
        linesCarousel.setItemHeight(250);
        linesCarousel.addTextSlide("Slide 1", "Using line indicators");
        linesCarousel.addTextSlide("Slide 2", "Modern and sleek");
        linesCarousel.addTextSlide("Slide 3", "Great for minimal designs");

        // Numbers
        Label numbersLabel = new Label("Number Indicators:");
        numbersLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCarousel numbersCarousel = new CFXCarousel();
        numbersCarousel.setIndicatorStyle(CarouselIndicatorStyle.NUMBERS);
        numbersCarousel.setItemHeight(250);
        numbersCarousel.addTextSlide("Slide 1", "Using number indicators");
        numbersCarousel.addTextSlide("Slide 2", "Shows position clearly");
        numbersCarousel.addTextSlide("Slide 3", "Good for step-by-step content");

        indicatorsBox.getChildren().addAll(
                dotsLabel, dotsCarousel,
                linesLabel, linesCarousel,
                numbersLabel, numbersCarousel
        );

        // ====================================
        // Custom Content Carousel
        // ====================================
        Label customLabel = new Label("Carousel with Custom Content");
        customLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel customCarousel = new CFXCarousel();
        customCarousel.setItemHeight(300);

        // Create custom content slides
        for (int i = 1; i <= 4; i++) {
            VBox customContent = new VBox(20);
            customContent.setAlignment(Pos.CENTER);
            customContent.setPadding(new Insets(40));

            Rectangle colorBox = new Rectangle(200, 150);
            colorBox.setArcWidth(12);
            colorBox.setArcHeight(12);

            switch (i) {
                case 1: colorBox.setFill(Color.web("#2196F3")); break;
                case 2: colorBox.setFill(Color.web("#4CAF50")); break;
                case 3: colorBox.setFill(Color.web("#FF9800")); break;
                case 4: colorBox.setFill(Color.web("#F44336")); break;
            }

            Label label = new Label("Custom Slide " + i);
            label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            customContent.getChildren().addAll(colorBox, label);

            CarouselItem item = CarouselItem.createWithContent(customContent);
            customCarousel.addItem(item);
        }

        // ====================================
        // Minimal Carousel (No indicators, smaller)
        // ====================================
        Label minimalLabel = new Label("Minimal Carousel");
        minimalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel minimalCarousel = new CFXCarousel();
        minimalCarousel.setShowIndicators(false);
        minimalCarousel.setItemHeight(200);
        minimalCarousel.setItemWidth(400);

        minimalCarousel.addTextSlide("Quote 1", "\"Design is not just what it looks like. Design is how it works.\"");
        minimalCarousel.addTextSlide("Quote 2", "\"Simplicity is the ultimate sophistication.\"");
        minimalCarousel.addTextSlide("Quote 3", "\"Good design is obvious. Great design is transparent.\"");

        // ====================================
        // Interactive Controls Example
        // ====================================
        Label controlsLabel = new Label("Interactive Controls");
        controlsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel controlledCarousel = new CFXCarousel();
        controlledCarousel.addTextSlide("Step 1", "Use the controls below to navigate");
        controlledCarousel.addTextSlide("Step 2", "You can control the carousel programmatically");
        controlledCarousel.addTextSlide("Step 3", "Perfect for tutorials and onboarding");
        controlledCarousel.addTextSlide("Step 4", "Or let users navigate freely");

        VBox controlsBox = new VBox(15);
        controlsBox.setAlignment(Pos.CENTER);

        javafx.scene.layout.HBox buttonBox = new javafx.scene.layout.HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        CFXButton firstBtn = new CFXButton("First");
        firstBtn.setOnAction(e -> controlledCarousel.goToSlide(0));

        CFXButton prevBtn = new CFXButton("Previous");
        prevBtn.setOnAction(e -> controlledCarousel.previous());

        CFXButton nextBtn = new CFXButton("Next");
        nextBtn.setOnAction(e -> controlledCarousel.next());

        CFXButton lastBtn = new CFXButton("Last");
        lastBtn.setOnAction(e -> controlledCarousel.goToSlide(controlledCarousel.getItems().size() - 1));

        buttonBox.getChildren().addAll(firstBtn, prevBtn, nextBtn, lastBtn);

        Label currentLabel = new Label("Current Slide: 1");
        currentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2196F3;");

        controlledCarousel.setOnSlideChange(event -> {
            currentLabel.setText("Current Slide: " + (event.getIndex() + 1) + " - " + event.getItem().getTitle());
        });

        controlsBox.getChildren().addAll(controlledCarousel, buttonBox, currentLabel);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                slideLabel,
                slideCarousel,
                new Separator(),
                fadeLabel,
                fadeCarousel,
                new Separator(),
                scaleLabel,
                scaleCarousel,
                new Separator(),
                indicatorsLabel,
                indicatorsBox,
                new Separator(),
                customLabel,
                customCarousel,
                new Separator(),
                minimalLabel,
                minimalCarousel,
                new Separator(),
                controlsLabel,
                controlsBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #F5F5F5; -fx-background-color: #F5F5F5;");

        Scene scene = new Scene(scrollPane, 1100, 900);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Carousel Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
