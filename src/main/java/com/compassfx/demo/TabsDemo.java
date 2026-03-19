// ============================================
// TabsDemo.java - Tabs Demo Application
// src/main/java/com/compassfx/demo/TabsDemo.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXTabs;
import com.compassfx.enums.TabPosition;
import com.compassfx.enums.TabVariant;
import com.compassfx.models.Tab;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TabsDemo {

    public void showDemo(Label title, VBox root) {
        Label standardLabel = new Label("Standard Tabs");
        standardLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs standardTabs = new CFXTabs();
        standardTabs.setMaxWidth(800);
        standardTabs.setPrefHeight(300);

        standardTabs.addTab("Home", createContent("Home Tab", "Welcome to the home page!"));
        standardTabs.addTab("Profile", createContent("Profile Tab", "View and edit your profile here."));
        standardTabs.addTab("Settings", createContent("Settings Tab", "Configure your settings."));
        standardTabs.addTab("Messages", createContent("Messages Tab", "Check your messages."));

        standardTabs.setOnTabChange(event -> {
            System.out.println("Tab changed to: " + event.getTab().getText());
        });

        // ====================================
        // Tabs with Icons
        // ====================================
        Label iconsLabel = new Label("Tabs with Icons");
        iconsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs iconTabs = new CFXTabs();
        iconTabs.setMaxWidth(800);
        iconTabs.setPrefHeight(300);

        Tab homeTab = Tab.withIcon("Home",
                new Circle(5, Color.web("#2196F3")),
                createContent("Home", "Home content with icon"));

        Tab profileTab = Tab.withIcon("Profile",
                new Circle(5, Color.web("#4CAF50")),
                createContent("Profile", "Profile content with icon"));

        Tab settingsTab = Tab.withIcon("Settings",
                new Circle(5, Color.web("#FF9800")),
                createContent("Settings", "Settings content with icon"));

        iconTabs.getTabs().addAll(homeTab, profileTab, settingsTab);

        // ====================================
        // Closable Tabs
        // ====================================
        Label closableLabel = new Label("Closable Tabs");
        closableLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs closableTabs = new CFXTabs();
        closableTabs.setMaxWidth(800);
        closableTabs.setPrefHeight(300);

        Tab tab1 = Tab.closable("Document 1", createContent("Document 1", "This tab can be closed"));
        Tab tab2 = Tab.closable("Document 2", createContent("Document 2", "Click the × to close"));
        Tab tab3 = Tab.closable("Document 3", createContent("Document 3", "Closable tabs are useful"));

        closableTabs.getTabs().addAll(tab1, tab2, tab3);

        closableTabs.setOnTabClose(event -> {
            System.out.println("Tab closed: " + event.getTab().getText());
        });

        // ====================================
        // Tab Variants
        // ====================================
        Label variantsLabel = new Label("Tab Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox variantsBox = new VBox(20);
        variantsBox.setAlignment(Pos.TOP_CENTER);

        // Filled variant
        Label filledLabel = new Label("Filled Variant:");
        filledLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXTabs filledTabs = new CFXTabs();
        filledTabs.setVariant(TabVariant.FILLED);
        filledTabs.setMaxWidth(800);
        filledTabs.setPrefHeight(250);

        filledTabs.addTab("Tab 1", createContent("Filled 1", "Filled variant with background"));
        filledTabs.addTab("Tab 2", createContent("Filled 2", "Selected tab has colored background"));
        filledTabs.addTab("Tab 3", createContent("Filled 3", "Clean and modern look"));

        // Pills variant
        Label pillsLabel = new Label("Pills Variant:");
        pillsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXTabs pillsTabs = new CFXTabs();
        pillsTabs.setVariant(TabVariant.PILLS);
        pillsTabs.setMaxWidth(800);
        pillsTabs.setPrefHeight(250);

        pillsTabs.addTab("Overview", createContent("Overview", "Rounded pill-style tabs"));
        pillsTabs.addTab("Analytics", createContent("Analytics", "Modern rounded design"));
        pillsTabs.addTab("Reports", createContent("Reports", "Great for dashboards"));

        // Underline variant
        Label underlineLabel = new Label("Underline Variant:");
        underlineLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXTabs underlineTabs = new CFXTabs();
        underlineTabs.setVariant(TabVariant.UNDERLINE);
        underlineTabs.setMaxWidth(800);
        underlineTabs.setPrefHeight(250);

        underlineTabs.addTab("Files", createContent("Files", "Minimal underline design"));
        underlineTabs.addTab("Folders", createContent("Folders", "Clean and simple"));
        underlineTabs.addTab("Shared", createContent("Shared", "Perfect for content apps"));

        variantsBox.getChildren().addAll(
                filledLabel, filledTabs,
                pillsLabel, pillsTabs,
                underlineLabel, underlineTabs
        );

        // ====================================
        // Bottom Position
        // ====================================
        Label bottomLabel = new Label("Bottom Position");
        bottomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs bottomTabs = new CFXTabs();
        bottomTabs.setTabPosition(TabPosition.BOTTOM);
        bottomTabs.setMaxWidth(800);
        bottomTabs.setPrefHeight(300);

        bottomTabs.addTab("Tab 1", createContent("Bottom 1", "Tabs at the bottom"));
        bottomTabs.addTab("Tab 2", createContent("Bottom 2", "Alternative layout"));
        bottomTabs.addTab("Tab 3", createContent("Bottom 3", "Good for mobile-style UIs"));

        // ====================================
        // Disabled Tab
        // ====================================
        Label disabledLabel = new Label("With Disabled Tab");
        disabledLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs disabledTabs = new CFXTabs();
        disabledTabs.setMaxWidth(800);
        disabledTabs.setPrefHeight(250);

        Tab enabledTab = new Tab("Enabled", createContent("Enabled", "This tab is active"));
        Tab disTab = new Tab("Disabled", createContent("Disabled", "This tab is disabled"));
        disTab.setDisabled(true);
        Tab anotherTab = new Tab("Another", createContent("Another", "This one works too"));

        disabledTabs.getTabs().addAll(enabledTab, disTab, anotherTab);

        // ====================================
        // Dynamic Tabs Example
        // ====================================
        Label dynamicLabel = new Label("Dynamic Tabs");
        dynamicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs dynamicTabs = new CFXTabs();
        dynamicTabs.setMaxWidth(800);
        dynamicTabs.setPrefHeight(300);

        dynamicTabs.addTab("Tab 1", createContent("Dynamic 1", "Initial tab"));
        dynamicTabs.addTab("Tab 2", createContent("Dynamic 2", "Another tab"));

        VBox dynamicContainer = new VBox(10);
        dynamicContainer.setAlignment(Pos.CENTER);

        javafx.scene.control.Button addTabBtn = new javafx.scene.control.Button("Add New Tab");
        addTabBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
        addTabBtn.setOnAction(e -> {
            int count = dynamicTabs.getTabs().size() + 1;
            Tab newTab = Tab.closable("Tab " + count,
                    createContent("Tab " + count, "Dynamically added tab"));
            dynamicTabs.addTab(newTab);
            dynamicTabs.selectTab(newTab);
        });

        dynamicContainer.getChildren().addAll(dynamicTabs, addTabBtn);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                standardLabel,
                standardTabs,
                new Separator(),
                iconsLabel,
                iconTabs,
                new Separator(),
                closableLabel,
                closableTabs,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                bottomLabel,
                bottomTabs,
                new Separator(),
                disabledLabel,
                disabledTabs,
                new Separator(),
                dynamicLabel,
                dynamicContainer
        );
    }

    static VBox createContent(String titleText, String description) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);

        Label title = new Label(titleText);
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        Label desc = new Label(description);
        desc.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
        desc.setWrapText(true);

        TextArea textArea = new TextArea();
        textArea.setPromptText("Tab content area...");
        textArea.setPrefHeight(150);

        content.getChildren().addAll(title, desc, textArea);
        return content;
    }
}