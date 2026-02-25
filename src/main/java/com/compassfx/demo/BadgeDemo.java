package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXBadge;
import com.compassfx.controls.CFXButton;
import com.compassfx.enums.BadgeColor;
import com.compassfx.enums.BadgePosition;
import com.compassfx.enums.BadgeVariant;
import com.compassfx.enums.ButtonVariant;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BadgeDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FAFAFA;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        Label title = new Label("CompassFX Badge Demo");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Basic Badges with Different Colors
        // ====================================
        Label colorsLabel = new Label("Badge Colors");
        colorsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox colorsBox = new HBox(20);
        colorsBox.setAlignment(Pos.CENTER);

        CFXBadge defaultBadge = createBadge("Default", BadgeColor.DEFAULT, 4);
        CFXBadge primaryBadge = createBadge("Primary", BadgeColor.PRIMARY, 3);
        CFXBadge secondaryBadge = createBadge("Secondary", BadgeColor.SECONDARY, 7);
        CFXBadge successBadge = createBadge("Success", BadgeColor.SUCCESS, 5);
        CFXBadge warningBadge = createBadge("Warning", BadgeColor.WARNING, 12);
        CFXBadge errorBadge = createBadge("Error", BadgeColor.ERROR, 2);
        CFXBadge infoBadge = createBadge("Info", BadgeColor.INFO, 9);

        colorsBox.getChildren().addAll(
                defaultBadge, primaryBadge, secondaryBadge, successBadge,
                warningBadge, errorBadge, infoBadge
        );

        // ====================================
        // Badge Variants
        // ====================================
        Label variantsLabel = new Label("Badge Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox variantsBox = new HBox(30);
        variantsBox.setAlignment(Pos.CENTER);

        // Standard variant
        CFXBadge standardBadge = new CFXBadge(createBox("Standard", Color.web("#2196F3")));
        standardBadge.setValue(99);
        standardBadge.setColor(BadgeColor.ERROR);

        // Dot variant
        CFXBadge dotBadge = new CFXBadge(createBox("Dot", Color.web("#4CAF50")));
        dotBadge.setVariant(BadgeVariant.DOT);
        dotBadge.setColor(BadgeColor.SUCCESS);

        variantsBox.getChildren().addAll(standardBadge, dotBadge);

        // ====================================
        // Max Value (99+)
        // ====================================
        Label maxLabel = new Label("Max Value Display");
        maxLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox maxBox = new HBox(30);
        maxBox.setAlignment(Pos.CENTER);

        CFXBadge badge50 = new CFXBadge(createBox("50", Color.web("#FF9800")));
        badge50.setValue(50);
        badge50.setColor(BadgeColor.WARNING);

        CFXBadge badge100 = new CFXBadge(createBox("100", Color.web("#F44336")));
        badge100.setValue(100);
        badge100.setColor(BadgeColor.ERROR);

        CFXBadge badge999 = new CFXBadge(createBox("999", Color.web("#9C27B0")));
        badge999.setValue(999);
        badge999.setColor(BadgeColor.SECONDARY);

        maxBox.getChildren().addAll(badge50, badge100, badge999);

        // ====================================
        // Different Positions
        // ====================================
        Label positionsLabel = new Label("Badge Positions");
        positionsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox positionsBox = new HBox(30);
        positionsBox.setAlignment(Pos.CENTER);

        CFXBadge topRightBadge = new CFXBadge(createBox("Top Right", Color.web("#2196F3")));
        topRightBadge.setValue(1);
        topRightBadge.setPosition(BadgePosition.TOP_RIGHT);
        topRightBadge.setColor(BadgeColor.PRIMARY);

        CFXBadge topLeftBadge = new CFXBadge(createBox("Top Left", Color.web("#4CAF50")));
        topLeftBadge.setValue(2);
        topLeftBadge.setPosition(BadgePosition.TOP_LEFT);
        topLeftBadge.setColor(BadgeColor.SUCCESS);

        CFXBadge bottomRightBadge = new CFXBadge(createBox("Bottom Right", Color.web("#FF9800")));
        bottomRightBadge.setValue(3);
        bottomRightBadge.setPosition(BadgePosition.BOTTOM_RIGHT);
        bottomRightBadge.setColor(BadgeColor.WARNING);

        CFXBadge bottomLeftBadge = new CFXBadge(createBox("Bottom Left", Color.web("#F44336")));
        bottomLeftBadge.setValue(4);
        bottomLeftBadge.setPosition(BadgePosition.BOTTOM_LEFT);
        bottomLeftBadge.setColor(BadgeColor.ERROR);

        positionsBox.getChildren().addAll(
                topRightBadge, topLeftBadge, bottomRightBadge, bottomLeftBadge
        );

        // ====================================
        // Custom Text Badges
        // ====================================
        Label customTextLabel = new Label("Custom Text Badges");
        customTextLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox customTextBox = new HBox(30);
        customTextBox.setAlignment(Pos.CENTER);

        CFXBadge newBadge = new CFXBadge(createBox("NEW", Color.web("#9C27B0")));
        newBadge.setText("NEW");
        newBadge.setColor(BadgeColor.SECONDARY);

        CFXBadge hotBadge = new CFXBadge(createBox("HOT", Color.web("#F44336")));
        hotBadge.setText("HOT");
        hotBadge.setColor(BadgeColor.ERROR);

        CFXBadge saleBadge = new CFXBadge(createBox("SALE", Color.web("#FF9800")));
        saleBadge.setText("SALE");
        saleBadge.setColor(BadgeColor.WARNING);

        customTextBox.getChildren().addAll(newBadge, hotBadge, saleBadge);

        // ====================================
        // Interactive Demo with Buttons
        // ====================================
        Label interactiveLabel = new Label("Interactive Badge");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox interactiveBox = new VBox(15);
        interactiveBox.setAlignment(Pos.CENTER);

        // Create a badge with a circle icon
        Circle mailIcon = new Circle(30);
        mailIcon.setFill(Color.web("#2196F3"));
        mailIcon.setStroke(Color.web("#1976D2"));
        mailIcon.setStrokeWidth(2);

        Label mailLabel = new Label("📧");
        mailLabel.setStyle("-fx-font-size: 24px;");

        VBox mailBox = new VBox(5, mailIcon, mailLabel);
        mailBox.setAlignment(Pos.CENTER);

        CFXBadge mailBadge = new CFXBadge(mailBox);
        mailBadge.setValue(0);
        mailBadge.setColor(BadgeColor.ERROR);
        mailBadge.setShowZero(false); // Badge will be hidden when value is 0

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        CFXButton addBtn = new CFXButton("Add Message");
        addBtn.setVariant(ButtonVariant.CONTAINED);
        addBtn.setOnAction(e -> mailBadge.setValue(mailBadge.getValue() + 1));

        CFXButton removeBtn = new CFXButton("Remove Message");
        removeBtn.setVariant(ButtonVariant.OUTLINED);
        removeBtn.setOnAction(e -> {
            if (mailBadge.getValue() > 0) {
                mailBadge.setValue(mailBadge.getValue() - 1);
            }
        });

        CFXButton resetBtn = new CFXButton("Reset");
        resetBtn.setVariant(ButtonVariant.TEXT);
        resetBtn.setOnAction(e -> mailBadge.setValue(0));

        buttonBox.getChildren().addAll(addBtn, removeBtn, resetBtn);
        interactiveBox.getChildren().addAll(mailBadge, buttonBox);

        // ====================================
        // Badges with Icons
        // ====================================
        Label iconsLabel = new Label("Badges with Different Content");
        iconsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox iconsBox = new HBox(30);
        iconsBox.setAlignment(Pos.CENTER);

        // Badge with circle
        Circle circle = new Circle(25, Color.web("#E91E63"));
        CFXBadge circleBadge = new CFXBadge(circle);
        circleBadge.setValue(8);
        circleBadge.setColor(BadgeColor.WARNING);

        // Badge with emoji
        Label emoji = new Label("🔔");
        emoji.setStyle("-fx-font-size: 40px;");
        CFXBadge emojiBadge = new CFXBadge(emoji);
        emojiBadge.setValue(15);
        emojiBadge.setColor(BadgeColor.ERROR);

        // Badge with button
        CFXButton button = new CFXButton("Messages");
        CFXBadge buttonBadge = new CFXBadge(button);
        buttonBadge.setValue(3);
        buttonBadge.setColor(BadgeColor.SUCCESS);

        iconsBox.getChildren().addAll(circleBadge, emojiBadge, buttonBadge);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                colorsLabel,
                colorsBox,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                maxLabel,
                maxBox,
                new Separator(),
                positionsLabel,
                positionsBox,
                new Separator(),
                customTextLabel,
                customTextBox,
                new Separator(),
                iconsLabel,
                iconsBox,
                new Separator(),
                interactiveLabel,
                interactiveBox
        );

        Scene scene = new Scene(scrollPane, 1000, 1100);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Badge Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CFXBadge createBadge(String label, BadgeColor color, int value) {
        VBox content = createBox(label, getColorForBadge(color));
        CFXBadge badge = new CFXBadge(content);
        badge.setValue(value);
        badge.setColor(color);
        return badge;
    }

    private VBox createBox(String text, Color color) {
        Rectangle rect = new Rectangle(60, 60);
        rect.setFill(color);
        rect.setArcWidth(8);
        rect.setArcHeight(8);

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 10px; -fx-text-fill: white; -fx-font-weight: 600;");

        VBox box = new VBox(5, rect, label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Color getColorForBadge(BadgeColor badgeColor) {
        switch (badgeColor) {
            case PRIMARY: return Color.web("#2196F3");
            case SECONDARY: return Color.web("#9C27B0");
            case SUCCESS: return Color.web("#4CAF50");
            case WARNING: return Color.web("#FF9800");
            case ERROR: return Color.web("#F44336");
            case INFO: return Color.web("#2196F3");
            case DEFAULT:
            default: return Color.web("#323232");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
