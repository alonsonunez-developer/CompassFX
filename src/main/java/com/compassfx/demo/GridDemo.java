package com.compassfx.demo;

import com.compassfx.controls.CFXGrid;
import com.compassfx.controls.CFXRow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GridDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // Basic 12-Column Grid
        // ====================================
        Label basicLabel = new Label("Basic 12-Column Grid");
        basicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXGrid basicGrid = new CFXGrid(12);
        basicGrid.setHgap(16);
        basicGrid.setVgap(16);
        basicGrid.setMaxWidth(1000);

        // Full width item
        basicGrid.addItem(createGridItem("12 Columns", Color.web("#2196F3")), 12);

        // Half width items
        basicGrid.addItem(createGridItem("6 Columns", Color.web("#4CAF50")), 6);
        basicGrid.addItem(createGridItem("6 Columns", Color.web("#4CAF50")), 6);

        // Third width items
        basicGrid.addItem(createGridItem("4 Col", Color.web("#FF9800")), 4);
        basicGrid.addItem(createGridItem("4 Col", Color.web("#FF9800")), 4);
        basicGrid.addItem(createGridItem("4 Col", Color.web("#FF9800")), 4);

        // Quarter width items
        basicGrid.addItem(createGridItem("3", Color.web("#9C27B0")), 3);
        basicGrid.addItem(createGridItem("3", Color.web("#9C27B0")), 3);
        basicGrid.addItem(createGridItem("3", Color.web("#9C27B0")), 3);
        basicGrid.addItem(createGridItem("3", Color.web("#9C27B0")), 3);

        // ====================================
        // Different Column Spans
        // ====================================
        Label spansLabel = new Label("Different Column Spans");
        spansLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXGrid spansGrid = new CFXGrid(12);
        spansGrid.setHgap(16);
        spansGrid.setVgap(16);
        spansGrid.setMaxWidth(1000);

        spansGrid.addItem(createGridItem("8 Columns", Color.web("#2196F3")), 8);
        spansGrid.addItem(createGridItem("4 Columns", Color.web("#F44336")), 4);

        spansGrid.addItem(createGridItem("3", Color.web("#00BCD4")), 3);
        spansGrid.addItem(createGridItem("6", Color.web("#FFEB3B")), 6);
        spansGrid.addItem(createGridItem("3", Color.web("#00BCD4")), 3);

        spansGrid.addItem(createGridItem("5", Color.web("#8BC34A")), 5);
        spansGrid.addItem(createGridItem("7", Color.web("#FF5722")), 7);

        // ====================================
        // Card Layout Example
        // ====================================
        Label cardsLabel = new Label("Card Layout Example");
        cardsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXGrid cardsGrid = new CFXGrid(12);
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setMaxWidth(1000);

        for (int i = 1; i <= 6; i++) {
            VBox card = createCard("Card " + i, "This is card number " + i);
            cardsGrid.addItem(card, 4); // 3 cards per row
        }

        // ====================================
        // Dashboard Layout
        // ====================================
        Label dashboardLabel = new Label("Dashboard Layout");
        dashboardLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXGrid dashboardGrid = new CFXGrid(12);
        dashboardGrid.setHgap(20);
        dashboardGrid.setVgap(20);
        dashboardGrid.setMaxWidth(1000);

        // Header
        dashboardGrid.addItem(createDashboardItem("Header", "Full Width Header", 80), 12);

        // Sidebar and Main Content
        dashboardGrid.addItem(createDashboardItem("Sidebar", "Navigation", 300), 3);
        dashboardGrid.addItem(createDashboardItem("Main Content", "Content Area", 300), 9);

        // Footer
        dashboardGrid.addItem(createDashboardItem("Footer", "Footer Content", 60), 12);

        // ====================================
        // 6-Column Grid
        // ====================================
        Label sixColLabel = new Label("6-Column Grid");
        sixColLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXGrid sixColGrid = new CFXGrid(6);
        sixColGrid.setHgap(12);
        sixColGrid.setVgap(12);
        sixColGrid.setMaxWidth(1000);

        for (int i = 1; i <= 6; i++) {
            sixColGrid.addItem(createGridItem("Col " + i, Color.web("#607D8B")), 1);
        }

        for (int i = 1; i <= 3; i++) {
            sixColGrid.addItem(createGridItem("2 Cols", Color.web("#795548")), 2);
        }

        sixColGrid.addItem(createGridItem("3 Columns", Color.web("#E91E63")), 3);
        sixColGrid.addItem(createGridItem("3 Columns", Color.web("#E91E63")), 3);

        // ====================================
        // Nested Grids
        // ====================================
        Label nestedLabel = new Label("Nested Grids");
        nestedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXGrid outerGrid = new CFXGrid(12);
        outerGrid.setHgap(20);
        outerGrid.setVgap(20);
        outerGrid.setMaxWidth(1000);

        // Left section with nested grid
        CFXGrid nestedLeft = new CFXGrid(2);
        nestedLeft.setHgap(10);
        nestedLeft.setVgap(10);
        nestedLeft.addItem(createGridItem("A", Color.web("#FF6B6B")), 1);
        nestedLeft.addItem(createGridItem("B", Color.web("#4ECDC4")), 1);
        nestedLeft.addItem(createGridItem("C", Color.web("#45B7D1")), 2);

        VBox leftContainer = new VBox(nestedLeft);
        leftContainer.setPadding(new Insets(15));
        leftContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px;"
        );

        outerGrid.addItem(leftContainer, 6);

        // Right section
        outerGrid.addItem(createDashboardItem("Right Panel", "Main Content", 200), 6);

        // ====================================
        // Using CFXRow
        // ====================================
        Label rowLabel = new Label("Using CFXRow Component");
        rowLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox rowContainer = new VBox(15);
        rowContainer.setMaxWidth(1000);

        CFXRow row1 = new CFXRow(20);
        row1.getChildren().addAll(
                createGridItem("Item 1", Color.web("#3F51B5")),
                createGridItem("Item 2", Color.web("#009688")),
                createGridItem("Item 3", Color.web("#FFC107"))
        );

        CFXRow row2 = new CFXRow(20);
        row2.getChildren().addAll(
                createGridItem("Column A", Color.web("#673AB7")),
                createGridItem("Column B", Color.web("#03A9F4"))
        );

        rowContainer.getChildren().addAll(row1, row2);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicGrid,
                new Separator(),
                spansLabel,
                spansGrid,
                new Separator(),
                cardsLabel,
                cardsGrid,
                new Separator(),
                dashboardLabel,
                dashboardGrid,
                new Separator(),
                sixColLabel,
                sixColGrid,
                new Separator(),
                nestedLabel,
                outerGrid,
                new Separator(),
                rowLabel,
                rowContainer
        );
    }

    private StackPane createGridItem(String text, Color color) {
        StackPane item = new StackPane();
        item.setStyle(
                "-fx-background-color: " + toRgbString(color) + "; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 30px;"
        );

        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));

        item.getChildren().add(label);
        return item;
    }

    private VBox createCard(String titleText, String description) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.2, 0, 2);"
        );

        Label title = new Label(titleText);
        title.setFont(Font.font("System", FontWeight.BOLD, 18));

        Label desc = new Label(description);
        desc.setStyle("-fx-text-fill: #666;");
        desc.setWrapText(true);

        card.getChildren().addAll(title, desc);
        return card;
    }

    private VBox createDashboardItem(String titleText, String subtitle, double minHeight) {
        VBox item = new VBox(5);
        item.setPadding(new Insets(20));
        item.setMinHeight(minHeight);
        item.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 8px;"
        );

        Label title = new Label(titleText);
        title.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label sub = new Label(subtitle);
        sub.setStyle("-fx-text-fill: #757575;");

        item.getChildren().addAll(title, sub);
        return item;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255)
        );
    }
}