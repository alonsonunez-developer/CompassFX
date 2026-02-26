package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ProgressColor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Demo application showcasing CFXSlider features
 */
public class SliderDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FFFFFF;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        // Title
        Label title = new Label("CompassFX Slider Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ===== BASIC SLIDERS SECTION =====
        Label basicLabel = new Label("Basic Sliders");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox basicSection = new VBox(20);
        basicSection.setAlignment(Pos.CENTER_LEFT);
        basicSection.setMaxWidth(500);

        // Simple slider
        VBox simple = createLabeledSlider("Simple Slider", 0, 100, 50);

        // Slider with value display
        VBox withValue = new VBox(8);
        Label valueLabel = new Label("Volume: 50");
        valueLabel.setStyle("-fx-font-weight: 500;");

        CFXSlider volumeSlider = new CFXSlider(0, 100, 50);
        volumeSlider.setPrefWidth(500);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            valueLabel.setText("Volume: " + Math.round(newVal.doubleValue()));
        });

        withValue.getChildren().addAll(valueLabel, volumeSlider);

        basicSection.getChildren().addAll(simple, withValue);

        // ===== COLOR VARIANTS SECTION =====
        Label colorLabel = new Label("Color Variants");
        colorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox colorSection = new VBox(20);
        colorSection.setAlignment(Pos.CENTER_LEFT);
        colorSection.setMaxWidth(500);

        VBox primary = createColoredSlider("Primary", ProgressColor.PRIMARY, 60);
        VBox secondary = createColoredSlider("Secondary", ProgressColor.SECONDARY, 60);
        VBox success = createColoredSlider("Success", ProgressColor.SUCCESS, 60);
        VBox warning = createColoredSlider("Warning", ProgressColor.WARNING, 60);
        VBox error = createColoredSlider("Error", ProgressColor.ERROR, 60);

        colorSection.getChildren().addAll(primary, secondary, success, warning, error);

        // ===== DISCRETE SLIDERS SECTION =====
        Label discreteLabel = new Label("Discrete Sliders (Step Values)");
        discreteLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox discreteSection = new VBox(20);
        discreteSection.setAlignment(Pos.CENTER_LEFT);
        discreteSection.setMaxWidth(500);

        // Discrete slider with steps
        VBox discrete1 = new VBox(8);
        Label discrete1Label = new Label("Rating: 5");
        discrete1Label.setStyle("-fx-font-weight: 500;");

        CFXSlider ratingSlider = new CFXSlider(0, 10, 5);
        ratingSlider.setPrefWidth(500);
        ratingSlider.setMajorTickUnit(1);
        ratingSlider.setMinorTickCount(0);
        ratingSlider.setSnapToTicks(true);
        ratingSlider.setShowTickMarks(true);
        ratingSlider.setDiscrete(true);
        ratingSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            discrete1Label.setText("Rating: " + Math.round(newVal.doubleValue()));
        });

        discrete1.getChildren().addAll(discrete1Label, ratingSlider);

        // Discrete slider with labels
        VBox discrete2 = new VBox(8);
        Label discrete2Label = new Label("Temperature: 20°C");
        discrete2Label.setStyle("-fx-font-weight: 500;");

        CFXSlider tempSlider = new CFXSlider(15, 30, 20);
        tempSlider.setPrefWidth(500);
        tempSlider.setMajorTickUnit(5);
        tempSlider.setMinorTickCount(4);
        tempSlider.setShowTickMarks(true);
        tempSlider.setShowTickLabels(true);
        tempSlider.setDiscrete(true);
        tempSlider.setColor(ProgressColor.WARNING);
        tempSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            discrete2Label.setText("Temperature: " + Math.round(newVal.doubleValue()) + "°C");
        });

        discrete2.getChildren().addAll(discrete2Label, tempSlider);

        discreteSection.getChildren().addAll(discrete1, discrete2);

        // ===== VERTICAL SLIDERS SECTION =====
        Label verticalLabel = new Label("Vertical Sliders");
        verticalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox verticalSection = new HBox(40);
        verticalSection.setAlignment(Pos.CENTER);
        verticalSection.setPrefHeight(250);

        VBox vertical1 = createVerticalSlider("Bass", ProgressColor.PRIMARY, 50);
        VBox vertical2 = createVerticalSlider("Mid", ProgressColor.SUCCESS, 70);
        VBox vertical3 = createVerticalSlider("Treble", ProgressColor.WARNING, 60);
        VBox vertical4 = createVerticalSlider("Volume", ProgressColor.ERROR, 80);

        verticalSection.getChildren().addAll(vertical1, vertical2, vertical3, vertical4);

        // ===== DISABLED STATE SECTION =====
        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox disabledSection = new VBox(20);
        disabledSection.setAlignment(Pos.CENTER_LEFT);
        disabledSection.setMaxWidth(500);

        VBox disabled = new VBox(8);
        Label disabledLabelText = new Label("Disabled Slider");
        disabledLabelText.setStyle("-fx-font-weight: 500;");

        CFXSlider disabledSlider = new CFXSlider(0, 100, 40);
        disabledSlider.setPrefWidth(500);
        disabledSlider.setDisable(true);

        disabled.getChildren().addAll(disabledLabelText, disabledSlider);
        disabledSection.getChildren().add(disabled);

        // ===== REAL-WORLD EXAMPLES SECTION =====
        Label examplesLabel = new Label("Real-World Examples");
        examplesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox examplesSection = new VBox(20);
        examplesSection.setAlignment(Pos.CENTER_LEFT);
        examplesSection.setMaxWidth(600);

        // Volume control example
        CFXCard volumeCard = new CFXCard();
        volumeCard.setPrefWidth(600);

        VBox volumeContent = new VBox(15);

        Label volumeTitle = new Label("Audio Settings");
        volumeTitle.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");

        Label masterLabel = new Label("Master Volume: 75%");
        masterLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider masterSlider = new CFXSlider(0, 100, 75);
        masterSlider.setPrefWidth(560);
        masterSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            masterLabel.setText("Master Volume: " + Math.round(newVal.doubleValue()) + "%");
        });

        Label bassLabel = new Label("Bass: 50%");
        bassLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider bassSlider = new CFXSlider(0, 100, 50);
        bassSlider.setPrefWidth(560);
        bassSlider.setColor(ProgressColor.SUCCESS);
        bassSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            bassLabel.setText("Bass: " + Math.round(newVal.doubleValue()) + "%");
        });

        Label trebleLabel = new Label("Treble: 50%");
        trebleLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider trebleSlider = new CFXSlider(0, 100, 50);
        trebleSlider.setPrefWidth(560);
        trebleSlider.setColor(ProgressColor.WARNING);
        trebleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            trebleLabel.setText("Treble: " + Math.round(newVal.doubleValue()) + "%");
        });

        volumeContent.getChildren().addAll(
                volumeTitle,
                masterLabel, masterSlider,
                bassLabel, bassSlider,
                trebleLabel, trebleSlider
        );
        volumeCard.setContent(volumeContent);

        // Brightness control example
        CFXCard brightnessCard = new CFXCard();
        brightnessCard.setPrefWidth(600);

        VBox brightnessContent = new VBox(15);

        Label brightnessTitle = new Label("Display Settings");
        brightnessTitle.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");

        Label brightnessLabel = new Label("Brightness: 70%");
        brightnessLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider brightnessSlider = new CFXSlider(0, 100, 70);
        brightnessSlider.setPrefWidth(560);
        brightnessSlider.setColor(ProgressColor.WARNING);
        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            brightnessLabel.setText("Brightness: " + Math.round(newVal.doubleValue()) + "%");
        });

        Label contrastLabel = new Label("Contrast: 50");
        contrastLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider contrastSlider = new CFXSlider(0, 100, 50);
        contrastSlider.setPrefWidth(560);
        contrastSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            contrastLabel.setText("Contrast: " + Math.round(newVal.doubleValue()));
        });

        brightnessContent.getChildren().addAll(
                brightnessTitle,
                brightnessLabel, brightnessSlider,
                contrastLabel, contrastSlider
        );
        brightnessCard.setContent(brightnessContent);

        examplesSection.getChildren().addAll(volumeCard, brightnessCard);

        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicSection,
                new Separator(),
                colorLabel,
                colorSection,
                new Separator(),
                discreteLabel,
                discreteSection,
                new Separator(),
                verticalLabel,
                verticalSection,
                new Separator(),
                disabledLabel,
                disabledSection,
                new Separator(),
                examplesLabel,
                examplesSection
        );

        // Create scene and apply theme
        Scene scene = new Scene(scrollPane, 900, 1800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Slider Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create a labeled slider
     */
    static VBox createLabeledSlider(String label, double min, double max, double value) {
        VBox container = new VBox(8);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-weight: 500;");

        CFXSlider slider = new CFXSlider(min, max, value);
        slider.setPrefWidth(500);

        container.getChildren().addAll(labelText, slider);
        return container;
    }

    /**
     * Create a colored slider
     */
    static VBox createColoredSlider(String label, ProgressColor color, double value) {
        VBox container = new VBox(8);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-weight: 500;");

        CFXSlider slider = new CFXSlider(0, 100, value);
        slider.setPrefWidth(500);
        slider.setColor(color);

        container.getChildren().addAll(labelText, slider);
        return container;
    }

    /**
     * Create a vertical slider
     */
    static VBox createVerticalSlider(String label, ProgressColor color, double value) {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        CFXSlider slider = new CFXSlider(0, 100, value);
        slider.setOrientation(Orientation.VERTICAL);
        slider.setPrefHeight(200);
        slider.setColor(color);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 12px;");

        Label valueLabel = new Label(Math.round(value) + "");
        valueLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: 600;");

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            valueLabel.setText(Math.round(newVal.doubleValue()) + "");
        });

        container.getChildren().addAll(valueLabel, slider, labelText);
        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }
}