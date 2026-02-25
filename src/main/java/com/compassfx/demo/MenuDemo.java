package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXContextMenu;
import com.compassfx.controls.CFXMenuBar;
import com.compassfx.enums.MenuVariant;
import com.compassfx.models.MenuItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MenuDemo extends Application {

    private TextArea logArea;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FAFAFA;");
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToHeight(true);

        // ====================================
        // Standard MenuBar
        // ====================================
        CFXMenuBar menuBar = createStandardMenuBar();
        root.setTop(menuBar);

        // ====================================
        // Content Area
        // ====================================
        VBox content = new VBox(30);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("CompassFX Menu Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // Log area
        Label logLabel = new Label("Action Log:");
        logLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(200);
        logArea.setMaxWidth(600);
        logArea.setPromptText("Menu actions will be logged here...");

        // Context Menu Demo
        Label contextLabel = new Label("Context Menu Demo");
        contextLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox contextBox = new VBox(10);
        contextBox.setAlignment(Pos.CENTER);
        contextBox.setPadding(new Insets(30));
        contextBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 12px;"
        );
        contextBox.setMaxWidth(400);

        Label contextInfo = new Label("Right-click in this area to open context menu");
        contextInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        CFXContextMenu contextMenu = createContextMenu();

        contextBox.setOnContextMenuRequested(e -> {
            contextMenu.show(contextBox, e.getScreenX(), e.getScreenY());
            e.consume();
        });

        contextBox.getChildren().add(contextInfo);

        // Different MenuBar Variants
        Label variantsLabel = new Label("MenuBar Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox variantsBox = new VBox(15);
        variantsBox.setAlignment(Pos.TOP_CENTER);
        variantsBox.setMaxWidth(800);

        // Elevated variant
        Label elevatedLabel = new Label("Elevated MenuBar:");
        elevatedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXMenuBar elevatedMenu = createSimpleMenuBar();
        elevatedMenu.setVariant(MenuVariant.ELEVATED);

        // Minimal variant
        Label minimalLabel = new Label("Minimal MenuBar:");
        minimalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXMenuBar minimalMenu = createSimpleMenuBar();
        minimalMenu.setVariant(MenuVariant.MINIMAL);

        variantsBox.getChildren().addAll(
                elevatedLabel, elevatedMenu,
                minimalLabel, minimalMenu
        );

        content.getChildren().addAll(
                title,
                new Separator(),
                logLabel,
                logArea,
                new Separator(),
                contextLabel,
                contextBox,
                new Separator(),
                variantsLabel,
                variantsBox
        );

        root.setCenter(content);

        Scene scene = new Scene(scrollPane, 1000, 800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Menu Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CFXMenuBar createStandardMenuBar() {
        CFXMenuBar menuBar = new CFXMenuBar();

        // File Menu
        MenuItem fileMenu = new MenuItem("File");

        MenuItem newItem = new MenuItem("New");
        newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newItem.setOnAction(e -> log("Action: New File"));

        MenuItem openItem = new MenuItem("Open");
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openItem.setOnAction(e -> log("Action: Open File"));

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveItem.setOnAction(e -> log("Action: Save File"));

        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        saveAsItem.setOnAction(e -> log("Action: Save As"));

        MenuItem closeItem = new MenuItem("Close");
        closeItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        closeItem.setOnAction(e -> log("Action: Close File"));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        exitItem.setOnAction(e -> log("Action: Exit Application"));

        fileMenu.getItems().addAll(
                newItem, openItem, MenuItem.separator(),
                saveItem, saveAsItem, MenuItem.separator(),
                closeItem, exitItem
        );

        // Edit Menu
        MenuItem editMenu = new MenuItem("Edit");

        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoItem.setOnAction(e -> log("Action: Undo"));

        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redoItem.setOnAction(e -> log("Action: Redo"));

        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        cutItem.setOnAction(e -> log("Action: Cut"));

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copyItem.setOnAction(e -> log("Action: Copy"));

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        pasteItem.setOnAction(e -> log("Action: Paste"));

        MenuItem selectAllItem = new MenuItem("Select All");
        selectAllItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        selectAllItem.setOnAction(e -> log("Action: Select All"));

        editMenu.getItems().addAll(
                undoItem, redoItem, MenuItem.separator(),
                cutItem, copyItem, pasteItem, MenuItem.separator(),
                selectAllItem
        );

        // View Menu
        MenuItem viewMenu = new MenuItem("View");

        MenuItem zoomInItem = new MenuItem("Zoom In");
        zoomInItem.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN));
        zoomInItem.setOnAction(e -> log("Action: Zoom In"));

        MenuItem zoomOutItem = new MenuItem("Zoom Out");
        zoomOutItem.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN));
        zoomOutItem.setOnAction(e -> log("Action: Zoom Out"));

        MenuItem fullscreenItem = new MenuItem("Fullscreen");
        fullscreenItem.setAccelerator(new KeyCodeCombination(KeyCode.F11));
        fullscreenItem.setOnAction(e -> log("Action: Toggle Fullscreen"));

        viewMenu.getItems().addAll(zoomInItem, zoomOutItem, MenuItem.separator(), fullscreenItem);

        // Help Menu
        MenuItem helpMenu = new MenuItem("Help");

        MenuItem docsItem = new MenuItem("Documentation");
        docsItem.setOnAction(e -> log("Action: Open Documentation"));

        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> log("Action: Show About Dialog"));

        helpMenu.getItems().addAll(docsItem, MenuItem.separator(), aboutItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);

        return menuBar;
    }

    private CFXMenuBar createSimpleMenuBar() {
        CFXMenuBar menuBar = new CFXMenuBar();

        MenuItem file = new MenuItem("File");
        file.getItems().addAll(
                new MenuItem("New"),
                new MenuItem("Open"),
                new MenuItem("Save")
        );

        MenuItem edit = new MenuItem("Edit");
        edit.getItems().addAll(
                new MenuItem("Cut"),
                new MenuItem("Copy"),
                new MenuItem("Paste")
        );

        MenuItem view = new MenuItem("View");
        view.getItems().addAll(
                new MenuItem("Zoom In"),
                new MenuItem("Zoom Out")
        );

        menuBar.getMenus().addAll(file, edit, view);
        return menuBar;
    }

    private CFXContextMenu createContextMenu() {
        CFXContextMenu menu = new CFXContextMenu();

        // With icons
        MenuItem cutItem = MenuItem.withIcon("Cut", new Circle(4, Color.web("#F44336")));
        cutItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        cutItem.setOnAction(e -> log("Context Action: Cut"));

        MenuItem copyItem = MenuItem.withIcon("Copy", new Circle(4, Color.web("#2196F3")));
        copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copyItem.setOnAction(e -> log("Context Action: Copy"));

        MenuItem pasteItem = MenuItem.withIcon("Paste", new Circle(4, Color.web("#4CAF50")));
        pasteItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        pasteItem.setOnAction(e -> log("Context Action: Paste"));

        MenuItem deleteItem = MenuItem.withIcon("Delete", new Circle(4, Color.web("#FF9800")));
        deleteItem.setOnAction(e -> log("Context Action: Delete"));

        MenuItem selectAllItem = new MenuItem("Select All");
        selectAllItem.setOnAction(e -> log("Context Action: Select All"));

        menu.getItems().addAll(
                cutItem, copyItem, pasteItem,
                MenuItem.separator(),
                deleteItem,
                MenuItem.separator(),
                selectAllItem
        );

        return menu;
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
        System.out.println(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
