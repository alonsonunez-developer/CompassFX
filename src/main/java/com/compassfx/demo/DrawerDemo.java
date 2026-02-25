// ============================================
// DrawerDemo.java - Drawer Demo Application
// src/main/java/com/compassfx/demo/DrawerDemo.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXDrawer;
import com.compassfx.controls.CFXTextField;
import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.DrawerPosition;
import com.compassfx.enums.DrawerSize;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DrawerDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout with drawer
        StackPane root = new StackPane();

        // Content area
        VBox content = new VBox(30);
        content.setPadding(new Insets(50));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("CompassFX Drawer Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        Label subtitle = new Label("Click buttons to open drawers from different positions");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        // ====================================
        // Left Drawer - Navigation Menu
        // ====================================
        CFXDrawer leftDrawer = new CFXDrawer();
        leftDrawer.setPosition(DrawerPosition.LEFT);
        leftDrawer.setSize(DrawerSize.SMALL);
        leftDrawer.setContent(createNavigationMenu());

        VBox leftSection = new VBox(10);
        leftSection.setAlignment(Pos.CENTER);

        Label leftLabel = new Label("Left Drawer - Navigation Menu");
        leftLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openLeftBtn = new CFXButton("Open Left Drawer");
        openLeftBtn.setColor(ButtonColor.PRIMARY);
        openLeftBtn.setOnAction(e -> leftDrawer.open());

        leftSection.getChildren().addAll(leftLabel, openLeftBtn);

        // ====================================
        // Right Drawer - Settings Form
        // ====================================
        CFXDrawer rightDrawer = new CFXDrawer();
        rightDrawer.setPosition(DrawerPosition.RIGHT);
        rightDrawer.setSize(DrawerSize.MEDIUM);
        rightDrawer.setContent(createSettingsForm(rightDrawer));

        VBox rightSection = new VBox(10);
        rightSection.setAlignment(Pos.CENTER);

        Label rightLabel = new Label("Right Drawer - Settings Form");
        rightLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openRightBtn = new CFXButton("Open Right Drawer");
        openRightBtn.setColor(ButtonColor.SUCCESS);
        openRightBtn.setOnAction(e -> rightDrawer.open());

        rightSection.getChildren().addAll(rightLabel, openRightBtn);

        // ====================================
        // Top Drawer - Notifications
        // ====================================
        CFXDrawer topDrawer = new CFXDrawer();
        topDrawer.setPosition(DrawerPosition.TOP);
        topDrawer.setSize(DrawerSize.SMALL);
        topDrawer.setContent(createNotifications());

        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);

        Label topLabel = new Label("Top Drawer - Notifications");
        topLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openTopBtn = new CFXButton("Open Top Drawer");
        openTopBtn.setColor(ButtonColor.WARNING);
        openTopBtn.setOnAction(e -> topDrawer.open());

        topSection.getChildren().addAll(topLabel, openTopBtn);

        // ====================================
        // Bottom Drawer - User Actions
        // ====================================
        CFXDrawer bottomDrawer = new CFXDrawer();
        bottomDrawer.setPosition(DrawerPosition.BOTTOM);
        bottomDrawer.setCustomSize(250);
        bottomDrawer.setContent(createActionsMenu());

        VBox bottomSection = new VBox(10);
        bottomSection.setAlignment(Pos.CENTER);

        Label bottomLabel = new Label("Bottom Drawer - Actions Menu");
        bottomLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openBottomBtn = new CFXButton("Open Bottom Drawer");
        openBottomBtn.setColor(ButtonColor.ERROR);
        openBottomBtn.setOnAction(e -> bottomDrawer.open());

        bottomSection.getChildren().addAll(bottomLabel, openBottomBtn);

        // ====================================
        // Large Drawer Example
        // ====================================
        CFXDrawer largeDrawer = new CFXDrawer();
        largeDrawer.setPosition(DrawerPosition.RIGHT);
        largeDrawer.setSize(DrawerSize.LARGE);
        largeDrawer.setContent(createLargeContent(largeDrawer));

        VBox largeSection = new VBox(10);
        largeSection.setAlignment(Pos.CENTER);

        Label largeLabel = new Label("Large Drawer - Document Editor");
        largeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openLargeBtn = new CFXButton("Open Large Drawer");
        openLargeBtn.setColor(ButtonColor.SECONDARY);
        openLargeBtn.setOnAction(e -> largeDrawer.open());

        largeSection.getChildren().addAll(largeLabel, openLargeBtn);

        // Add all sections to content
        HBox row1 = new HBox(40, leftSection, rightSection);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(40, topSection, bottomSection);
        row2.setAlignment(Pos.CENTER);

        content.getChildren().addAll(
                title,
                subtitle,
                new Separator(),
                row1,
                new Separator(),
                row2,
                new Separator(),
                largeSection
        );

        // Add all drawers to root
        root.getChildren().addAll(
                content,
                leftDrawer,
                rightDrawer,
                topDrawer,
                bottomDrawer,
                largeDrawer
        );

        Scene scene = new Scene(root, 1000, 700);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Drawer Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createNavigationMenu() {
        VBox menu = new VBox(5);
        menu.setAlignment(Pos.TOP_LEFT);

        Label menuTitle = new Label("Navigation");
        menuTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        menuTitle.setPadding(new Insets(0, 0, 15, 0));

        menu.getChildren().add(menuTitle);

        String[] menuItems = {"🏠 Home", "👤 Profile", "📊 Dashboard", "⚙️ Settings",
                "📄 Documents", "💬 Messages", "🔔 Notifications", "❓ Help"};

        for (String item : menuItems) {
            Label menuItem = new Label(item);
            menuItem.setFont(Font.font("System", 14));
            menuItem.setMaxWidth(Double.MAX_VALUE);
            menuItem.setPadding(new Insets(12, 15, 12, 15));
            menuItem.getStyleClass().add("menu-item");
            menuItem.setCursor(javafx.scene.Cursor.HAND);

            menuItem.setOnMouseEntered(e -> {
                menuItem.setStyle("-fx-background-color: rgba(33, 150, 243, 0.08); -fx-background-radius: 6px;");
            });
            menuItem.setOnMouseExited(e -> {
                menuItem.setStyle("");
            });
            menuItem.setOnMouseClicked(e -> {
                System.out.println("Clicked: " + item);
            });

            menu.getChildren().add(menuItem);
        }

        return menu;
    }

    private VBox createSettingsForm(CFXDrawer drawer) {
        VBox form = new VBox(20);
        form.setAlignment(Pos.TOP_LEFT);

        Label formTitle = new Label("Settings");
        formTitle.setFont(Font.font("System", FontWeight.BOLD, 24));

        Label nameLabel = new Label("Name");
        nameLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        CFXTextField nameField = new CFXTextField();
        nameField.setPromptText("Enter your name");

        Label emailLabel = new Label("Email");
        emailLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        CFXTextField emailField = new CFXTextField();
        emailField.setPromptText("Enter your email");

        Label bioLabel = new Label("Bio");
        bioLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        TextArea bioArea = new TextArea();
        bioArea.setPromptText("Tell us about yourself");
        bioArea.setPrefRowCount(4);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setColor(ButtonColor.SECONDARY);
        cancelBtn.setOnAction(e -> drawer.close());

        CFXButton saveBtn = new CFXButton("Save Changes");
        saveBtn.setColor(ButtonColor.SUCCESS);
        saveBtn.setOnAction(e -> {
            System.out.println("Settings saved!");
            drawer.close();
        });

        buttons.getChildren().addAll(cancelBtn, saveBtn);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        form.getChildren().addAll(
                formTitle,
                new Separator(),
                nameLabel, nameField,
                emailLabel, emailField,
                bioLabel, bioArea,
                spacer,
                buttons
        );

        return form;
    }

    private VBox createNotifications() {
        VBox notifications = new VBox(10);
        notifications.setAlignment(Pos.TOP_LEFT);

        Label notifTitle = new Label("Recent Notifications");
        notifTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        notifications.getChildren().add(notifTitle);

        String[][] notifs = {
                {"New message from John", "2 minutes ago"},
                {"Your report is ready", "1 hour ago"},
                {"System update available", "3 hours ago"}
        };

        for (String[] notif : notifs) {
            VBox notifBox = new VBox(3);
            notifBox.setPadding(new Insets(10));
            notifBox.setStyle("-fx-background-color: white; -fx-background-radius: 6px; " +
                    "-fx-border-color: #E0E0E0; -fx-border-width: 1px; -fx-border-radius: 6px;");

            Label notifText = new Label(notif[0]);
            notifText.setFont(Font.font("System", FontWeight.MEDIUM, 14));

            Label notifTime = new Label(notif[1]);
            notifTime.setStyle("-fx-text-fill: #999; -fx-font-size: 12px;");

            notifBox.getChildren().addAll(notifText, notifTime);
            notifications.getChildren().add(notifBox);
        }

        return notifications;
    }

    private HBox createActionsMenu() {
        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(20));

        CFXButton shareBtn = new CFXButton("📤 Share");
        CFXButton downloadBtn = new CFXButton("⬇️ Download");
        CFXButton deleteBtn = new CFXButton("🗑️ Delete");
        deleteBtn.setColor(ButtonColor.ERROR);
        CFXButton moreBtn = new CFXButton("⋮ More");
        moreBtn.setColor(ButtonColor.SECONDARY);

        actions.getChildren().addAll(shareBtn, downloadBtn, deleteBtn, moreBtn);

        return actions;
    }

    private VBox createLargeContent(CFXDrawer drawer) {
        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_LEFT);

        Label docTitle = new Label("Document Editor");
        docTitle.setFont(Font.font("System", FontWeight.BOLD, 24));

        CFXTextField titleField = new CFXTextField();
        titleField.setPromptText("Document title");

        TextArea editor = new TextArea();
        editor.setPromptText("Start writing...");
        editor.setPrefRowCount(15);
        VBox.setVgrow(editor, Priority.ALWAYS);

        HBox toolbar = new HBox(10);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        CFXButton boldBtn = new CFXButton("B");
        CFXButton italicBtn = new CFXButton("I");
        CFXButton underlineBtn = new CFXButton("U");

        toolbar.getChildren().addAll(boldBtn, italicBtn, underlineBtn);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        CFXButton closeBtn = new CFXButton("Close");
        closeBtn.setColor(ButtonColor.SECONDARY);
        closeBtn.setOnAction(e -> drawer.close());

        CFXButton saveDocBtn = new CFXButton("Save Document");
        saveDocBtn.setColor(ButtonColor.PRIMARY);
        saveDocBtn.setOnAction(e -> {
            System.out.println("Document saved!");
        });

        buttons.getChildren().addAll(closeBtn, saveDocBtn);

        content.getChildren().addAll(
                docTitle,
                new Separator(),
                titleField,
                toolbar,
                editor,
                buttons
        );

        return content;
    }

    public static void main(String[] args) {
        launch(args);
    }
}