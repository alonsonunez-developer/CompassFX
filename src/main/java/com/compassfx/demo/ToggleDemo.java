package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXToggle;
import com.compassfx.enums.ToggleSize;
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

/**
 * Demo application showcasing CFXToggle features
 */
public class ToggleDemo extends Application {

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
        Label title = new Label("CompassFX Toggle/Switch Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ===== BASIC TOGGLES SECTION =====
        Label basicLabel = new Label("Basic Toggles");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox basicSection = new VBox(15);
        basicSection.setAlignment(Pos.CENTER_LEFT);
        basicSection.setMaxWidth(400);

        CFXToggle toggle1 = new CFXToggle("Enable notifications");
        toggle1.selectedProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Notifications: " + (newVal ? "ON" : "OFF"));
        });

        CFXToggle toggle2 = new CFXToggle("Auto-save");
        toggle2.setSelected(true);

        CFXToggle toggle3 = new CFXToggle("Dark mode");

        CFXToggle toggle4 = new CFXToggle("Enable sound effects");
        toggle4.setSelected(true);

        basicSection.getChildren().addAll(toggle1, toggle2, toggle3, toggle4);

        // ===== SIZE VARIANTS SECTION =====
        Label sizeLabel = new Label("Size Variants");
        sizeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox sizeSection = new VBox(15);
        sizeSection.setAlignment(Pos.CENTER_LEFT);
        sizeSection.setMaxWidth(400);

        CFXToggle smallToggle = new CFXToggle("Small toggle");
        smallToggle.setSize(ToggleSize.SMALL);
        smallToggle.setSelected(true);

        CFXToggle mediumToggle = new CFXToggle("Medium toggle (default)");
        mediumToggle.setSize(ToggleSize.MEDIUM);
        mediumToggle.setSelected(true);

        CFXToggle largeToggle = new CFXToggle("Large toggle");
        largeToggle.setSize(ToggleSize.LARGE);
        largeToggle.setSelected(true);

        sizeSection.getChildren().addAll(smallToggle, mediumToggle, largeToggle);

        // ===== WITHOUT LABELS SECTION =====
        Label noLabelLabel = new Label("Toggles Without Labels");
        noLabelLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox noLabelSection = new HBox(20);
        noLabelSection.setAlignment(Pos.CENTER_LEFT);

        CFXToggle noLabel1 = new CFXToggle();
        CFXToggle noLabel2 = new CFXToggle();
        noLabel2.setSelected(true);
        CFXToggle noLabel3 = new CFXToggle();

        noLabelSection.getChildren().addAll(noLabel1, noLabel2, noLabel3);

        // ===== DISABLED STATE SECTION =====
        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox disabledSection = new VBox(15);
        disabledSection.setAlignment(Pos.CENTER_LEFT);
        disabledSection.setMaxWidth(400);

        CFXToggle disabledOff = new CFXToggle("Disabled (OFF)");
        disabledOff.setDisable(true);

        CFXToggle disabledOn = new CFXToggle("Disabled (ON)");
        disabledOn.setSelected(true);
        disabledOn.setDisable(true);

        disabledSection.getChildren().addAll(disabledOff, disabledOn);

        // ===== INTERACTIVE EXAMPLE SECTION =====
        Label interactiveLabel = new Label("Interactive Example");
        interactiveLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox interactiveSection = new VBox(15);
        interactiveSection.setAlignment(Pos.CENTER_LEFT);
        interactiveSection.setMaxWidth(400);

        Label statusLabel = new Label("Status: All features disabled");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXToggle feature1 = new CFXToggle("Feature 1");
        CFXToggle feature2 = new CFXToggle("Feature 2");
        CFXToggle feature3 = new CFXToggle("Feature 3");

        // Update status when toggles change
        Runnable updateStatus = () -> {
            int enabledCount = 0;
            if (feature1.isSelected()) enabledCount++;
            if (feature2.isSelected()) enabledCount++;
            if (feature3.isSelected()) enabledCount++;

            if (enabledCount == 0) {
                statusLabel.setText("Status: All features disabled");
            } else if (enabledCount == 3) {
                statusLabel.setText("Status: All features enabled ✓");
            } else {
                statusLabel.setText("Status: " + enabledCount + " feature(s) enabled");
            }
        };

        feature1.selectedProperty().addListener((obs, oldVal, newVal) -> updateStatus.run());
        feature2.selectedProperty().addListener((obs, oldVal, newVal) -> updateStatus.run());
        feature3.selectedProperty().addListener((obs, oldVal, newVal) -> updateStatus.run());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        CFXButton enableAllBtn = new CFXButton("Enable All");
        enableAllBtn.setOnAction(e -> {
            feature1.setSelected(true);
            feature2.setSelected(true);
            feature3.setSelected(true);
        });

        CFXButton disableAllBtn = new CFXButton("Disable All");
        disableAllBtn.setOnAction(e -> {
            feature1.setSelected(false);
            feature2.setSelected(false);
            feature3.setSelected(false);
        });

        buttonBox.getChildren().addAll(enableAllBtn, disableAllBtn);

        interactiveSection.getChildren().addAll(
                statusLabel,
                feature1, feature2, feature3,
                buttonBox
        );

        // ===== SETTINGS FORM EXAMPLE =====
        Label settingsLabel = new Label("Settings Panel Example");
        settingsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox settingsSection = new VBox(15);
        settingsSection.setAlignment(Pos.CENTER_LEFT);
        settingsSection.setMaxWidth(500);
        settingsSection.setPadding(new Insets(15));
        settingsSection.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 1px; -fx-border-radius: 8px;");

        Label settingsTitle = new Label("Application Settings");
        settingsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");

        // Notifications section
        Label notificationsHeader = new Label("Notifications");
        notificationsHeader.setStyle("-fx-font-weight: 500;");

        CFXToggle emailNotif = new CFXToggle("Email notifications");
        emailNotif.setSelected(true);

        CFXToggle pushNotif = new CFXToggle("Push notifications");
        pushNotif.setSelected(true);

        CFXToggle smsNotif = new CFXToggle("SMS notifications");

        // Privacy section
        Label privacyHeader = new Label("Privacy");
        privacyHeader.setStyle("-fx-font-weight: 500;");

        CFXToggle shareData = new CFXToggle("Share analytics data");
        CFXToggle showOnline = new CFXToggle("Show online status");
        showOnline.setSelected(true);

        CFXToggle allowMessages = new CFXToggle("Allow direct messages");
        allowMessages.setSelected(true);

        // Accessibility section
        Label accessibilityHeader = new Label("Accessibility");
        accessibilityHeader.setStyle("-fx-font-weight: 500;");

        CFXToggle highContrast = new CFXToggle("High contrast mode");
        CFXToggle largeText = new CFXToggle("Large text");
        CFXToggle reduceMotion = new CFXToggle("Reduce motion");

        // Save button
        HBox saveBox = new HBox();
        saveBox.setAlignment(Pos.CENTER_RIGHT);

        CFXButton saveBtn = new CFXButton("Save Settings");
        saveBtn.setOnAction(e -> {
            System.out.println("=== Settings Saved ===");
            System.out.println("Email Notifications: " + emailNotif.isSelected());
            System.out.println("Push Notifications: " + pushNotif.isSelected());
            System.out.println("SMS Notifications: " + smsNotif.isSelected());
            System.out.println("Share Data: " + shareData.isSelected());
            System.out.println("Show Online: " + showOnline.isSelected());
            System.out.println("Allow Messages: " + allowMessages.isSelected());
            System.out.println("High Contrast: " + highContrast.isSelected());
            System.out.println("Large Text: " + largeText.isSelected());
            System.out.println("Reduce Motion: " + reduceMotion.isSelected());
        });

        saveBox.getChildren().add(saveBtn);

        settingsSection.getChildren().addAll(
                settingsTitle,
                new Separator(),
                notificationsHeader,
                emailNotif, pushNotif, smsNotif,
                new Separator(),
                privacyHeader,
                shareData, showOnline, allowMessages,
                new Separator(),
                accessibilityHeader,
                highContrast, largeText, reduceMotion,
                new Separator(),
                saveBox
        );

        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicSection,
                new Separator(),
                sizeLabel,
                sizeSection,
                new Separator(),
                noLabelLabel,
                noLabelSection,
                new Separator(),
                disabledLabel,
                disabledSection,
                new Separator(),
                interactiveLabel,
                interactiveSection,
                new Separator(),
                settingsLabel,
                settingsSection
        );

        // Create scene and apply theme
        Scene scene = new Scene(scrollPane, 1200, 1800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Toggle Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}