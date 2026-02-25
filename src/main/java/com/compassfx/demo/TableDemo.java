package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXTable;
import com.compassfx.controls.CFXTextField;
import com.compassfx.enums.TableDensity;
import com.compassfx.enums.TableSelectionMode;
import com.compassfx.models.TableColumn;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TableDemo extends Application {

    // Sample data model
    public static class User {
        private String name;
        private String email;
        private String role;
        private String status;
        private int age;

        public User(String name, String email, String role, String status, int age) {
            this.name = name;
            this.email = email;
            this.role = role;
            this.status = status;
            this.age = age;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public String getStatus() { return status; }
        public int getAge() { return age; }

        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setRole(String role) { this.role = role; }
        public void setStatus(String status) { this.status = status; }
        public void setAge(int age) { this.age = age; }
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(40);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("CompassFX Table Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Basic Table with Sorting
        // ====================================
        Label basicLabel = new Label("Basic Table with Sorting");
        basicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTable<User> basicTable = createBasicTable();
        basicTable.setMaxWidth(900);

        Label sortInfo = new Label("Click column headers to sort");
        sortInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-style: italic;");

        // ====================================
        // Table with Search
        // ====================================
        Label searchLabel = new Label("Table with Search");
        searchLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox searchBox = new VBox(10);
        searchBox.setAlignment(Pos.TOP_LEFT);
        searchBox.setMaxWidth(900);

        CFXTextField searchField = new CFXTextField();
        searchField.setPromptText("Search users...");
        searchField.setMaxWidth(300);

        CFXTable<User> searchTable = createBasicTable();
        searchTable.setMaxWidth(900);

        searchField.textProperty().addListener((obs, old, newVal) -> {
            searchTable.filter(newVal);
        });

        searchBox.getChildren().addAll(searchField, searchTable);

        // ====================================
        // Table with Pagination
        // ====================================
        Label paginationLabel = new Label("Table with Pagination");
        paginationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTable<User> paginatedTable = createBasicTable();
        paginatedTable.setShowPagination(true);
        paginatedTable.setItemsPerPage(5);
        paginatedTable.setMaxWidth(900);

        // ====================================
        // Density Variants
        // ====================================
        Label densityLabel = new Label("Table Density Variants");
        densityLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox densityBox = new VBox(15);
        densityBox.setAlignment(Pos.TOP_CENTER);

        Label compactLabel = new Label("Compact:");
        compactLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXTable<User> compactTable = createSmallTable();
        compactTable.setDensity(TableDensity.COMPACT);
        compactTable.setMaxWidth(900);

        Label comfortableLabel = new Label("Comfortable:");
        comfortableLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXTable<User> comfortableTable = createSmallTable();
        comfortableTable.setDensity(TableDensity.COMFORTABLE);
        comfortableTable.setMaxWidth(900);

        densityBox.getChildren().addAll(compactLabel, compactTable, comfortableLabel, comfortableTable);

        // ====================================
        // Table Variants
        // ====================================
        Label variantsLabel = new Label("Table Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox variantsBox = new VBox(15);
        variantsBox.setAlignment(Pos.TOP_CENTER);

        Label stripedLabel = new Label("Striped Rows:");
        stripedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXTable<User> stripedTable = createSmallTable();
        stripedTable.setStriped(true);
        stripedTable.setMaxWidth(900);

        Label borderedLabel = new Label("Without Borders:");
        borderedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXTable<User> noBorderTable = createSmallTable();
        noBorderTable.setBordered(false);
        noBorderTable.setMaxWidth(900);

        variantsBox.getChildren().addAll(stripedLabel, stripedTable, borderedLabel, noBorderTable);

        // ====================================
        // Custom Cell Rendering
        // ====================================
        Label customLabel = new Label("Custom Cell Rendering");
        customLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTable<User> customTable = new CFXTable<>();
        customTable.setMaxWidth(900);

        TableColumn<User> nameCol = new TableColumn<>("Name", "name", 200);
        TableColumn<User> emailCol = new TableColumn<>("Email", "email", 250);
        TableColumn<User> roleCol = new TableColumn<>("Role", "role", 150);

        // Custom status column with colored indicators
        TableColumn<User> statusCol = new TableColumn<>("Status", "status", 150);
        statusCol.setCellFactory(user -> {
            HBox statusBox = new HBox(8);
            statusBox.setAlignment(Pos.CENTER_LEFT);

            Circle indicator = new Circle(5);
            switch (user.getStatus()) {
                case "Active":
                    indicator.setFill(Color.web("#4CAF50"));
                    break;
                case "Inactive":
                    indicator.setFill(Color.web("#F44336"));
                    break;
                case "Pending":
                    indicator.setFill(Color.web("#FF9800"));
                    break;
            }

            Label statusLabel = new Label(user.getStatus());
            statusBox.getChildren().addAll(indicator, statusLabel);
            return statusBox;
        });

        // Custom actions column
        TableColumn<User> actionsCol = new TableColumn<>("Actions", "name", 150);
        actionsCol.setSortable(false);
        actionsCol.setCellFactory(user -> {
            HBox actionsBox = new HBox(5);
            actionsBox.setAlignment(Pos.CENTER_LEFT);

            CFXButton editBtn = new CFXButton("Edit");
            editBtn.setStyle("-fx-font-size: 11px; -fx-padding: 4px 12px;");
            editBtn.setOnAction(e -> System.out.println("Edit: " + user.getName()));

            CFXButton deleteBtn = new CFXButton("Delete");
            deleteBtn.setStyle("-fx-font-size: 11px; -fx-padding: 4px 12px;");
            deleteBtn.setOnAction(e -> System.out.println("Delete: " + user.getName()));

            actionsBox.getChildren().addAll(editBtn, deleteBtn);
            return actionsBox;
        });

        customTable.getColumns().addAll(nameCol, emailCol, roleCol, statusCol, actionsCol);
        customTable.setData(getSampleData());

        // ====================================
        // Interactive Example
        // ====================================
        Label interactiveLabel = new Label("Interactive Selection");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox interactiveBox = new VBox(15);
        interactiveBox.setAlignment(Pos.TOP_CENTER);

        CFXTable<User> interactiveTable = createBasicTable();
        interactiveTable.setSelectionMode(TableSelectionMode.MULTIPLE);
        interactiveTable.setMaxWidth(900);

        Label selectionLabel = new Label("Selected: None");
        selectionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2196F3; -fx-font-weight: 600;");

        interactiveTable.setOnSelectionChange(event -> {
            List<User> selected = event.getSelectedItems();
            if (selected.isEmpty()) {
                selectionLabel.setText("Selected: None");
            } else {
                StringBuilder sb = new StringBuilder("Selected: ");
                for (int i = 0; i < selected.size(); i++) {
                    sb.append(selected.get(i).getName());
                    if (i < selected.size() - 1) sb.append(", ");
                }
                selectionLabel.setText(sb.toString());
            }
        });

        interactiveTable.setOnRowDoubleClick(event -> {
            System.out.println("Double-clicked: " + event.getItem().getName());
        });

        interactiveBox.getChildren().addAll(interactiveTable, selectionLabel);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicTable,
                sortInfo,
                new Separator(),
                searchLabel,
                searchBox,
                new Separator(),
                paginationLabel,
                paginatedTable,
                new Separator(),
                densityLabel,
                densityBox,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                customLabel,
                customTable,
                new Separator(),
                interactiveLabel,
                interactiveBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #F5F5F5; -fx-background-color: #F5F5F5;");

        Scene scene = new Scene(scrollPane, 1100, 900);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Table Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static CFXTable<User> createBasicTable() {
        CFXTable<User> table = new CFXTable<>();

        table.addColumn("Name", "name", 200);
        table.addColumn("Email", "email", 250);
        table.addColumn("Role", "role", 150);
        table.addColumn("Status", "status", 120);
        table.addColumn("Age", "age", 80);

        table.setData(getSampleData());
        return table;
    }

    static CFXTable<User> createSmallTable() {
        CFXTable<User> table = new CFXTable<>();

        table.addColumn("Name", "name", 250);
        table.addColumn("Email", "email", 300);
        table.addColumn("Role", "role", 200);

        List<User> data = getSampleData();
        table.setData(data.subList(0, Math.min(4, data.size())));
        return table;
    }

    static List<User> getSampleData() {
        List<User> users = new ArrayList<>();
        users.add(new User("John Doe", "john.doe@example.com", "Admin", "Active", 32));
        users.add(new User("Jane Smith", "jane.smith@example.com", "Editor", "Active", 28));
        users.add(new User("Bob Johnson", "bob.j@example.com", "Viewer", "Inactive", 45));
        users.add(new User("Alice Williams", "alice.w@example.com", "Editor", "Active", 35));
        users.add(new User("Charlie Brown", "charlie.b@example.com", "Admin", "Pending", 29));
        users.add(new User("Diana Prince", "diana.p@example.com", "Editor", "Active", 31));
        users.add(new User("Eve Adams", "eve.a@example.com", "Viewer", "Active", 27));
        users.add(new User("Frank Miller", "frank.m@example.com", "Admin", "Inactive", 38));
        users.add(new User("Grace Lee", "grace.l@example.com", "Editor", "Active", 26));
        users.add(new User("Henry Ford", "henry.f@example.com", "Viewer", "Pending", 42));
        users.add(new User("Iris West", "iris.w@example.com", "Admin", "Active", 30));
        users.add(new User("Jack Ryan", "jack.r@example.com", "Editor", "Active", 34));
        return users;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
