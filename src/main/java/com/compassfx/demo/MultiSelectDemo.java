package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXMultiSelect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultiSelectDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FAFAFA;");
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Label title = new Label("CompassFX MultiSelect Demo");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // ====================================
        // Basic MultiSelect
        // ====================================
        Label basicLabel = new Label("Basic MultiSelect");
        basicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMultiSelect<String> basicSelect = new CFXMultiSelect<>();
        basicSelect.setLabel("Select Fruits");
        basicSelect.setPromptText("Choose your favorite fruits...");
        basicSelect.setPrefWidth(400);
        basicSelect.setItems(FXCollections.observableArrayList(
                "Apple", "Banana", "Orange", "Mango", "Strawberry",
                "Pineapple", "Watermelon", "Grapes", "Kiwi", "Peach"
        ));

        // ====================================
        // Countries MultiSelect
        // ====================================
        Label countriesLabel = new Label("Countries MultiSelect");
        countriesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMultiSelect<String> countriesSelect = new CFXMultiSelect<>();
        countriesSelect.setLabel("Select Countries");
        countriesSelect.setPromptText("Choose countries to visit...");
        countriesSelect.setPrefWidth(400);
        countriesSelect.setItems(FXCollections.observableArrayList(
                "United States", "Canada", "Mexico", "Brazil", "Argentina",
                "United Kingdom", "France", "Germany", "Italy", "Spain",
                "Japan", "China", "India", "Australia", "New Zealand"
        ));

        // ====================================
        // Programming Languages
        // ====================================
        Label languagesLabel = new Label("Programming Languages");
        languagesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMultiSelect<String> languagesSelect = new CFXMultiSelect<>();
        languagesSelect.setLabel("Select Languages");
        languagesSelect.setPromptText("Pick your tech stack...");
        languagesSelect.setPrefWidth(400);
        languagesSelect.setMaxChipsDisplay(4);
        languagesSelect.setItems(FXCollections.observableArrayList(
                "Java", "JavaScript", "Python", "C++", "C#",
                "Ruby", "Go", "Rust", "TypeScript", "Swift",
                "Kotlin", "PHP", "Scala", "Dart", "R"
        ));

        // ====================================
        // With Pre-selected Items
        // ====================================
        Label preselectedLabel = new Label("With Pre-selected Items");
        preselectedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMultiSelect<String> preselectedSelect = new CFXMultiSelect<>();
        preselectedSelect.setLabel("Select Skills");
        preselectedSelect.setPromptText("Choose your skills...");
        preselectedSelect.setPrefWidth(400);
        preselectedSelect.setItems(FXCollections.observableArrayList(
                "Communication", "Leadership", "Problem Solving", "Teamwork",
                "Time Management", "Creativity", "Adaptability", "Critical Thinking"
        ));
        preselectedSelect.setSelectedItems(FXCollections.observableArrayList(
                "Communication", "Problem Solving", "Teamwork"
        ));

        // ====================================
        // Without Select All
        // ====================================
        Label noSelectAllLabel = new Label("Without Select All Option");
        noSelectAllLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXMultiSelect<String> noSelectAllSelect = new CFXMultiSelect<>();
        noSelectAllSelect.setLabel("Select Tags");
        noSelectAllSelect.setPromptText("Add tags...");
        noSelectAllSelect.setPrefWidth(400);
        noSelectAllSelect.setSelectAllEnabled(false);
        noSelectAllSelect.setMaxChipsDisplay(5);
        noSelectAllSelect.setItems(FXCollections.observableArrayList(
                "urgent", "important", "work", "personal", "todo",
                "in-progress", "completed", "blocked", "review", "archived"
        ));

        // ====================================
        // Display Selected Items
        // ====================================
        Label displayLabel = new Label("Selected Items Display");
        displayLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        VBox displayBox = new VBox(10);
        displayBox.setAlignment(Pos.CENTER_LEFT);
        displayBox.setPrefWidth(400);

        Label fruitsDisplay = new Label("Selected Fruits: None");
        fruitsDisplay.setStyle("-fx-font-size: 14px;");

        basicSelect.selectedItemsProperty().addListener((obs, old, newVal) -> {
            if (newVal.isEmpty()) {
                fruitsDisplay.setText("Selected Fruits: None");
            } else {
                fruitsDisplay.setText("Selected Fruits: " + String.join(", ", newVal));
            }
        });

        displayBox.getChildren().add(fruitsDisplay);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicSelect,
                displayBox,
                new Separator(),
                countriesLabel,
                countriesSelect,
                new Separator(),
                languagesLabel,
                languagesSelect,
                new Separator(),
                preselectedLabel,
                preselectedSelect,
                new Separator(),
                noSelectAllLabel,
                noSelectAllSelect
        );

        Scene scene = new Scene(scrollPane, 800, 1000);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX MultiSelect Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}