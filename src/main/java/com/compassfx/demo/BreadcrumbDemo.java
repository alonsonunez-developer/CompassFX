package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXBreadcrumb;
import com.compassfx.enums.BreadcrumbSeparator;
import com.compassfx.enums.BreadcrumbVariant;
import com.compassfx.models.BreadcrumbItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BreadcrumbDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(40);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #FAFAFA;");
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Label title = new Label("CompassFX Breadcrumb Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Standard Breadcrumb
        // ====================================
        Label standardLabel = new Label("Standard Breadcrumb");
        standardLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXBreadcrumb breadcrumb1 = new CFXBreadcrumb();
        breadcrumb1.setPath("Home", "Products", "Electronics", "Laptops", "MacBook Pro");
        breadcrumb1.setOnItemClick(event -> {
            System.out.println("Clicked: " + event.getItem().getText() + " at index " + event.getIndex());
        });

        // ====================================
        // With Home Icon
        // ====================================
        Label homeIconLabel = new Label("With Home Icon");
        homeIconLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXBreadcrumb breadcrumb2 = new CFXBreadcrumb();
        breadcrumb2.setShowHomeIcon(true);
        breadcrumb2.setPath("Home", "Documents", "Projects", "CompassFX", "src");

        // ====================================
        // Different Separators
        // ====================================
        Label separatorsLabel = new Label("Different Separators");
        separatorsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox separatorsBox = new VBox(15);
        separatorsBox.setAlignment(Pos.TOP_LEFT);

        // Chevron (default)
        Label chevronLabel = new Label("Chevron (›):");
        chevronLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbChevron = new CFXBreadcrumb();
        breadcrumbChevron.setSeparator(BreadcrumbSeparator.CHEVRON);
        breadcrumbChevron.setPath("Level 1", "Level 2", "Level 3");

        // Slash
        Label slashLabel = new Label("Slash (/):");
        slashLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbSlash = new CFXBreadcrumb();
        breadcrumbSlash.setSeparator(BreadcrumbSeparator.SLASH);
        breadcrumbSlash.setPath("Users", "Documents", "Photos");

        // Arrow
        Label arrowLabel = new Label("Arrow (→):");
        arrowLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbArrow = new CFXBreadcrumb();
        breadcrumbArrow.setSeparator(BreadcrumbSeparator.ARROW);
        breadcrumbArrow.setPath("Step 1", "Step 2", "Step 3", "Complete");

        // Dot
        Label dotLabel = new Label("Dot (•):");
        dotLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbDot = new CFXBreadcrumb();
        breadcrumbDot.setSeparator(BreadcrumbSeparator.DOT);
        breadcrumbDot.setPath("Category", "Subcategory", "Item");

        separatorsBox.getChildren().addAll(
                chevronLabel, breadcrumbChevron,
                slashLabel, breadcrumbSlash,
                arrowLabel, breadcrumbArrow,
                dotLabel, breadcrumbDot
        );

        // ====================================
        // Variants
        // ====================================
        Label variantsLabel = new Label("Breadcrumb Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox variantsBox = new VBox(15);
        variantsBox.setAlignment(Pos.TOP_LEFT);

        // Standard
        Label stdVariantLabel = new Label("Standard:");
        stdVariantLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbStd = new CFXBreadcrumb();
        breadcrumbStd.setVariant(BreadcrumbVariant.STANDARD);
        breadcrumbStd.setPath("Home", "Shop", "Men", "Shirts");

        // Outlined
        Label outlinedLabel = new Label("Outlined:");
        outlinedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbOutlined = new CFXBreadcrumb();
        breadcrumbOutlined.setVariant(BreadcrumbVariant.OUTLINED);
        breadcrumbOutlined.setPath("Home", "Shop", "Men", "Shirts");

        // Filled
        Label filledLabel = new Label("Filled:");
        filledLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXBreadcrumb breadcrumbFilled = new CFXBreadcrumb();
        breadcrumbFilled.setVariant(BreadcrumbVariant.FILLED);
        breadcrumbFilled.setPath("Home", "Shop", "Men", "Shirts");

        variantsBox.getChildren().addAll(
                stdVariantLabel, breadcrumbStd,
                outlinedLabel, breadcrumbOutlined,
                filledLabel, breadcrumbFilled
        );

        // ====================================
        // With Icons
        // ====================================
        Label iconsLabel = new Label("Breadcrumb with Icons");
        iconsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXBreadcrumb breadcrumbWithIcons = new CFXBreadcrumb();

        BreadcrumbItem homeItem = new BreadcrumbItem("Home");
        homeItem.setIcon(createIcon(Color.web("#2196F3")));

        BreadcrumbItem productsItem = new BreadcrumbItem("Products");
        productsItem.setIcon(createIcon(Color.web("#4CAF50")));

        BreadcrumbItem categoryItem = new BreadcrumbItem("Category");
        categoryItem.setIcon(createIcon(Color.web("#FF9800")));

        BreadcrumbItem itemItem = new BreadcrumbItem("Item Details");
        itemItem.setIcon(createIcon(Color.web("#F44336")));

        breadcrumbWithIcons.getItems().addAll(homeItem, productsItem, categoryItem, itemItem);

        // ====================================
        // Collapsed (Max Items)
        // ====================================
        Label collapsedLabel = new Label("Collapsed Breadcrumb (Max 4 items)");
        collapsedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXBreadcrumb breadcrumbCollapsed = new CFXBreadcrumb();
        breadcrumbCollapsed.setMaxDisplayedItems(4);
        breadcrumbCollapsed.setPath("Home", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Current Page");

        // ====================================
        // Interactive Example
        // ====================================
        Label interactiveLabel = new Label("Interactive Navigation");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox interactiveBox = new VBox(15);
        interactiveBox.setPadding(new Insets(20));
        interactiveBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.2, 0, 2);"
        );

        Label navLabel = new Label("Click any breadcrumb item to navigate:");
        navLabel.setStyle("-fx-font-size: 14px;");

        CFXBreadcrumb interactiveBreadcrumb = new CFXBreadcrumb();
        interactiveBreadcrumb.setShowHomeIcon(true);
        interactiveBreadcrumb.setVariant(BreadcrumbVariant.FILLED);
        interactiveBreadcrumb.setPath("Home", "Account", "Settings", "Privacy");

        Label currentPageLabel = new Label("Current Page: Privacy");
        currentPageLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: #2196F3;");

        interactiveBreadcrumb.setOnItemClick(event -> {
            currentPageLabel.setText("Navigated to: " + event.getItem().getText());
            System.out.println("Navigation to: " + event.getItem().getText() + " (index: " + event.getIndex() + ")");
        });

        interactiveBox.getChildren().addAll(navLabel, interactiveBreadcrumb, currentPageLabel);

        // ====================================
        // Disabled Items
        // ====================================
        Label disabledLabel = new Label("With Disabled Items");
        disabledLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXBreadcrumb breadcrumbDisabled = new CFXBreadcrumb();
        BreadcrumbItem item1 = new BreadcrumbItem("Home");
        BreadcrumbItem item2 = new BreadcrumbItem("Restricted");
        item2.setDisabled(true);
        BreadcrumbItem item3 = new BreadcrumbItem("Area");
        item3.setDisabled(true);
        BreadcrumbItem item4 = new BreadcrumbItem("Current");

        breadcrumbDisabled.getItems().addAll(item1, item2, item3, item4);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                standardLabel,
                breadcrumb1,
                new Separator(),
                homeIconLabel,
                breadcrumb2,
                new Separator(),
                separatorsLabel,
                separatorsBox,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                iconsLabel,
                breadcrumbWithIcons,
                new Separator(),
                collapsedLabel,
                breadcrumbCollapsed,
                new Separator(),
                disabledLabel,
                breadcrumbDisabled,
                new Separator(),
                interactiveLabel,
                interactiveBox
        );

        Scene scene = new Scene(scrollPane, 1000, 1100);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Breadcrumb Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Circle createIcon(Color color) {
        Circle icon = new Circle(5, color);
        return icon;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
