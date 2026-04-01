package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ButtonVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BackdropDemo {

    public void showDemo(Label title, VBox root) {

        Label description = new Label("Click on any card below to reveal the backdrop");
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        // ====================================
        // Example 1: Filter Backdrop
        // ====================================
        Label filterLabel = new Label("Filter Backdrop Example");
        filterLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXBackdrop filterBackdrop = createFilterBackdrop();
        filterBackdrop.setPrefSize(600, 500);

        // ====================================
        // Example 2: Settings Backdrop
        // ====================================
        Label settingsLabel = new Label("Settings Backdrop Example");
        settingsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXBackdrop settingsBackdrop = createSettingsBackdrop();
        settingsBackdrop.setPrefSize(600, 500);

        // ====================================
        // Example 3: Search Backdrop
        // ====================================
        Label searchLabel = new Label("Search Backdrop Example");
        searchLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXBackdrop searchBackdrop = createSearchBackdrop();
        searchBackdrop.setPrefSize(600, 500);

        // Add all to root
        root.getChildren().addAll(
                title,
                description,
                new Separator(),
                filterLabel,
                filterBackdrop,
                new Separator(),
                settingsLabel,
                settingsBackdrop,
                new Separator(),
                searchLabel,
                searchBackdrop
        );
    }
    private CFXBackdrop createFilterBackdrop() {
        CFXBackdrop backdrop = new CFXBackdrop();
        backdrop.setBackHeight(250);

        // Front content (main view)
        VBox frontContent = new VBox(20);
        frontContent.setPadding(new Insets(30));
        frontContent.setAlignment(Pos.TOP_CENTER);
        frontContent.setStyle("-fx-background-color: #E3F2FD;");

        Label frontTitle = new Label("Product List");
        frontTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label instruction = new Label("Click here to open filters");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        VBox products = new VBox(10);
        products.getChildren().addAll(
                createProductCard("MacBook Pro", "$2,499"),
                createProductCard("iPhone 15", "$999"),
                createProductCard("iPad Air", "$599"),
                createProductCard("Apple Watch", "$399")
        );

        frontContent.getChildren().addAll(frontTitle, instruction, products);

        // Back content (filters)
        VBox backContent = new VBox(15);
        backContent.setPadding(new Insets(20));
        backContent.setStyle("-fx-background-color: white;");

        Label backTitle = new Label("Filters");
        backTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox priceFilters = new HBox(10);
        CFXCheckbox under500 = new CFXCheckbox("Under $500");
        CFXCheckbox under1000 = new CFXCheckbox("Under $1000");
        CFXCheckbox above1000 = new CFXCheckbox("Above $1000");
        priceFilters.getChildren().addAll(under500, under1000, above1000);

        HBox categoryFilters = new HBox(10);
        CFXCheckbox computers = new CFXCheckbox("Computers");
        CFXCheckbox phones = new CFXCheckbox("Phones");
        CFXCheckbox accessories = new CFXCheckbox("Accessories");
        categoryFilters.getChildren().addAll(computers, phones, accessories);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        CFXButton applyBtn = new CFXButton("Apply Filters");
        applyBtn.setVariant(ButtonVariant.CONTAINED);
        applyBtn.setOnAction(e -> backdrop.conceal());
        CFXButton clearBtn = new CFXButton("Clear");
        clearBtn.setVariant(ButtonVariant.TEXT);
        buttons.getChildren().addAll(clearBtn, applyBtn);

        backContent.getChildren().addAll(backTitle,
                new Label("Price Range:"), priceFilters,
                new Label("Category:"), categoryFilters,
                buttons);

        backdrop.setFrontContent(frontContent);
        backdrop.setBackContent(backContent);

        return backdrop;
    }

    private CFXBackdrop createSettingsBackdrop() {
        CFXBackdrop backdrop = new CFXBackdrop();
        backdrop.setBackHeight(250);

        // Front content
        VBox frontContent = new VBox(20);
        frontContent.setPadding(new Insets(30));
        frontContent.setAlignment(Pos.TOP_CENTER);
        frontContent.setStyle("-fx-background-color: #F3E5F5;");

        Label frontTitle = new Label("User Dashboard");
        frontTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label instruction = new Label("Click here to open settings");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        GridPane stats = new GridPane();
        stats.setHgap(20);
        stats.setVgap(10);
        stats.setAlignment(Pos.CENTER);
        stats.add(createStatCard("Posts", "125"), 0, 0);
        stats.add(createStatCard("Followers", "1.2K"), 1, 0);
        stats.add(createStatCard("Following", "340"), 2, 0);

        frontContent.getChildren().addAll(frontTitle, instruction, stats);

        // Back content
        VBox backContent = new VBox(15);
        backContent.setPadding(new Insets(20));

        Label backTitle = new Label("Quick Settings");
        backTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXCheckbox notifications = new CFXCheckbox("Enable Notifications");
        notifications.setSelected(true);
        CFXCheckbox darkMode = new CFXCheckbox("Dark Mode");
        CFXCheckbox autoSave = new CFXCheckbox("Auto-save");
        autoSave.setSelected(true);

        CFXButton saveBtn = new CFXButton("Save Settings");
        saveBtn.setVariant(ButtonVariant.CONTAINED);
        saveBtn.setOnAction(e -> backdrop.conceal());

        backContent.getChildren().addAll(backTitle, notifications, darkMode, autoSave, saveBtn);

        backdrop.setFrontContent(frontContent);
        backdrop.setBackContent(backContent);

        return backdrop;
    }

    private CFXBackdrop createSearchBackdrop() {
        CFXBackdrop backdrop = new CFXBackdrop();
        backdrop.setBackHeight(180);

        // Front content
        VBox frontContent = new VBox(20);
        frontContent.setPadding(new Insets(30));
        frontContent.setAlignment(Pos.TOP_CENTER);
        frontContent.setStyle("-fx-background-color: #FFF3E0;");

        Label frontTitle = new Label("Documents");
        frontTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label instruction = new Label("Click here to search");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        VBox docs = new VBox(8);
        docs.getChildren().addAll(
                new Label("📄 Report Q4 2024.pdf"),
                new Label("📄 Meeting Notes.docx"),
                new Label("📄 Budget 2025.xlsx"),
                new Label("📄 Project Plan.pdf")
        );

        frontContent.getChildren().addAll(frontTitle, instruction, docs);

        // Back content
        VBox backContent = new VBox(15);
        backContent.setPadding(new Insets(20));

        Label backTitle = new Label("Search Documents");
        backTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXTextField searchField = new CFXTextField();
        searchField.setLabel("Search");
        searchField.setPromptText("Type to search...");
        searchField.setPrefWidth(400);

        HBox filterButtons = new HBox(10);
        CFXButton pdfBtn = new CFXButton("PDFs");
        CFXButton docBtn = new CFXButton("Docs");
        CFXButton xlsBtn = new CFXButton("Sheets");
        filterButtons.getChildren().addAll(pdfBtn, docBtn, xlsBtn);

        backContent.getChildren().addAll(backTitle, searchField, filterButtons);

        backdrop.setFrontContent(frontContent);
        backdrop.setBackContent(backContent);

        return backdrop;
    }

    private VBox createProductCard(String name, String price) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; " +
                "-fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: 600;");
        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");

        card.getChildren().addAll(nameLabel, priceLabel);
        return card;
    }

    private VBox createStatCard(String label, String value) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; " +
                "-fx-border-radius: 8px; -fx-background-radius: 8px;");
        card.setPrefWidth(120);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #9C27B0;");
        Label labelText = new Label(label);
        labelText.setStyle("-fx-text-fill: #666;");

        card.getChildren().addAll(valueLabel, labelText);
        return card;
    }

}