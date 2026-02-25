// ============================================
// SimpleDrawerTest.java - Simple Test
// src/main/java/com/compassfx/demo/SimpleDrawerTest.java
// ============================================
package com.compassfx.demo;

import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXDrawer;
import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.DrawerPosition;
import com.compassfx.enums.DrawerSize;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SimpleDrawerTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Simple content
        VBox content = new VBox(30);
        content.setPadding(new Insets(50));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("Simple Drawer Test");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        CFXButton testBtn = new CFXButton("Click Me!");
        testBtn.setColor(ButtonColor.PRIMARY);
        testBtn.setOnAction(e -> {
            System.out.println("Button clicked!");
        });

        CFXButton openDrawerBtn = new CFXButton("Open Drawer");
        openDrawerBtn.setColor(ButtonColor.SUCCESS);

        content.getChildren().addAll(title, testBtn, openDrawerBtn);

        // Create drawer with simple content
        VBox drawerContent = new VBox(20);
        drawerContent.setPadding(new Insets(20));
        Label drawerLabel = new Label("Drawer Content");
        drawerLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        drawerContent.getChildren().add(drawerLabel);

        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.LEFT);
        drawer.setSize(DrawerSize.SMALL);
        drawer.setContent(drawerContent);

        openDrawerBtn.setOnAction(e -> {
            System.out.println("Opening drawer...");
            drawer.open();
        });

        // Root layout
        StackPane root = new StackPane(content, drawer);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Simple Drawer Test");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("App started!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}