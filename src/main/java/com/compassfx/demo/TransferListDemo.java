package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXTransferList;
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

import java.util.Arrays;
import java.util.List;

public class TransferListDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // User Permissions Transfer
        // ====================================
        Label permissionsLabel = new Label("Assign User Permissions");
        permissionsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTransferList<String> permissionsTransfer = new CFXTransferList<>();
        permissionsTransfer.setAvailableTitle("Available Permissions");
        permissionsTransfer.setSelectedTitle("Assigned Permissions");
        permissionsTransfer.setSearchPrompt("Search permissions...");
        permissionsTransfer.setListHeight(300);

        List<String> permissions = Arrays.asList(
                "Read Users", "Create Users", "Update Users", "Delete Users",
                "Read Posts", "Create Posts", "Update Posts", "Delete Posts",
                "Read Comments", "Create Comments", "Update Comments", "Delete Comments",
                "Manage Settings", "View Reports", "Export Data", "Import Data",
                "Manage Roles", "Assign Permissions", "View Audit Log"
        );

        permissionsTransfer.setAvailableItems(FXCollections.observableArrayList(permissions));
        permissionsTransfer.setSelectedItems(FXCollections.observableArrayList(
                "Read Users", "Read Posts", "Create Posts"
        ));

        // ====================================
        // Team Members Transfer
        // ====================================
        Label teamLabel = new Label("Select Team Members");
        teamLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTransferList<String> teamTransfer = new CFXTransferList<>();
        teamTransfer.setAvailableTitle("All Employees");
        teamTransfer.setSelectedTitle("Team Members");
        teamTransfer.setSearchPrompt("Search employees...");
        teamTransfer.setListHeight(300);

        List<String> employees = Arrays.asList(
                "Alice Johnson", "Bob Smith", "Charlie Brown", "Diana Prince",
                "Eve Adams", "Frank Miller", "Grace Lee", "Henry Davis",
                "Ivy Chen", "Jack Wilson", "Kate Martinez", "Leo Garcia",
                "Maria Rodriguez", "Nathan Taylor", "Olivia Anderson"
        );

        teamTransfer.setAvailableItems(FXCollections.observableArrayList(employees));
        teamTransfer.setSelectedItems(FXCollections.observableArrayList(
                "Alice Johnson", "Bob Smith", "Charlie Brown"
        ));

        // ====================================
        // Languages Transfer (No Search)
        // ====================================
        Label languagesLabel = new Label("Select Programming Languages (No Search)");
        languagesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTransferList<String> languagesTransfer = new CFXTransferList<>();
        languagesTransfer.setAvailableTitle("Available Languages");
        languagesTransfer.setSelectedTitle("Your Stack");
        languagesTransfer.setSearchable(false); // Disable search
        languagesTransfer.setShowCounts(true);
        languagesTransfer.setListHeight(250);

        List<String> languages = Arrays.asList(
                "Java", "JavaScript", "Python", "C++", "C#", "Ruby",
                "Go", "Rust", "TypeScript", "Swift", "Kotlin", "PHP"
        );

        languagesTransfer.setAvailableItems(FXCollections.observableArrayList(languages));
        languagesTransfer.setSelectedItems(FXCollections.observableArrayList(
                "Java", "JavaScript", "Python"
        ));

        // ====================================
        // Countries Transfer (Custom Height)
        // ====================================
        Label countriesLabel = new Label("Select Countries to Visit");
        countriesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTransferList<String> countriesTransfer = new CFXTransferList<>();
        countriesTransfer.setAvailableTitle("World Countries");
        countriesTransfer.setSelectedTitle("My Travel List");
        countriesTransfer.setSearchPrompt("Search countries...");
        countriesTransfer.setListHeight(200); // Shorter lists
        countriesTransfer.setShowCounts(true);

        List<String> countries = Arrays.asList(
                "United States", "Canada", "Mexico", "Brazil", "Argentina",
                "United Kingdom", "France", "Germany", "Italy", "Spain",
                "Japan", "China", "India", "Australia", "New Zealand",
                "South Korea", "Thailand", "Singapore", "Russia", "Turkey"
        );

        countriesTransfer.setAvailableItems(FXCollections.observableArrayList(countries));

        // Display selected items
        Label selectedDisplay = new Label("Selected Countries: None");
        selectedDisplay.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        countriesTransfer.getSelectedItems().addListener(
                (javafx.collections.ListChangeListener<String>) c -> {
                    if (countriesTransfer.getSelectedItems().isEmpty()) {
                        selectedDisplay.setText("Selected Countries: None");
                    } else {
                        selectedDisplay.setText("Selected Countries: " +
                                String.join(", ", countriesTransfer.getSelectedItems()));
                    }
                });

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                permissionsLabel,
                permissionsTransfer,
                new Separator(),
                teamLabel,
                teamTransfer,
                new Separator(),
                languagesLabel,
                languagesTransfer,
                new Separator(),
                countriesLabel,
                countriesTransfer,
                selectedDisplay
        );
    }
}