package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXAccordion;
import com.compassfx.controls.CFXAccordionItem;
import com.compassfx.controls.CFXButton;
import com.compassfx.enums.AccordionColor;
import com.compassfx.enums.AccordionVariant;
import com.compassfx.enums.ButtonVariant;
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

public class AccordionDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FAFAFA;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        Label title = new Label("CompassFX Accordion Demo");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        // ====================================
        // Standard Variant
        // ====================================
        Label standardLabel = new Label("Standard Accordion");
        standardLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXAccordion standardAccordion = new CFXAccordion();
        standardAccordion.setMaxWidth(600);

        CFXAccordionItem item1 = new CFXAccordionItem("General Information");
        item1.setContent(createSampleContent(
                "This is a standard accordion component that follows Material Design principles. " +
                        "It provides a clean and intuitive way to organize content in collapsible sections."
        ));

        CFXAccordionItem item2 = new CFXAccordionItem("Features");
        VBox featuresContent = new VBox(10);
        featuresContent.getChildren().addAll(
                createBulletPoint("Smooth animations"),
                createBulletPoint("Multiple variants and colors"),
                createBulletPoint("Single or multiple expansion modes"),
                createBulletPoint("Customizable icons"),
                createBulletPoint("Dark theme support")
        );
        item2.setContent(featuresContent);

        CFXAccordionItem item3 = new CFXAccordionItem("Usage");
        item3.setContent(createSampleContent(
                "Simply create a CFXAccordion instance, add CFXAccordionItem objects to it, " +
                        "and customize the appearance using variants and colors."
        ));

        standardAccordion.getItems().addAll(item1, item2, item3);

        // ====================================
        // Outlined Variant
        // ====================================
        Label outlinedLabel = new Label("Outlined Accordion");
        outlinedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXAccordion outlinedAccordion = new CFXAccordion();
        outlinedAccordion.setVariant(AccordionVariant.OUTLINED);
        outlinedAccordion.setMaxWidth(600);

        CFXAccordionItem outItem1 = new CFXAccordionItem("Section 1");
        outItem1.setContent(createSampleContent("This accordion uses the outlined variant with a border around the container."));

        CFXAccordionItem outItem2 = new CFXAccordionItem("Section 2");
        outItem2.setContent(createSampleContent("The outlined variant provides better visual separation from the background."));

        outlinedAccordion.getItems().addAll(outItem1, outItem2);

        // ====================================
        // Filled Variant with Primary Color
        // ====================================
        Label filledLabel = new Label("Filled Accordion (Primary Color)");
        filledLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXAccordion filledAccordion = new CFXAccordion();
        filledAccordion.setVariant(AccordionVariant.FILLED);
        filledAccordion.setColor(AccordionColor.PRIMARY);
        filledAccordion.setMaxWidth(600);

        CFXAccordionItem fillItem1 = new CFXAccordionItem("Configuration");
        fillItem1.setContent(createSampleContent("The filled variant provides a subtle background color to each accordion item."));

        CFXAccordionItem fillItem2 = new CFXAccordionItem("Advanced Options");
        fillItem2.setContent(createSampleContent("You can customize colors, enable/disable animations, and control expansion behavior."));

        filledAccordion.getItems().addAll(fillItem1, fillItem2);

        // ====================================
        // Multiple Expansion Mode
        // ====================================
        Label multipleLabel = new Label("Multiple Expansion Allowed");
        multipleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXAccordion multipleAccordion = new CFXAccordion();
        multipleAccordion.setAllowMultipleExpanded(true);
        multipleAccordion.setColor(AccordionColor.SECONDARY);
        multipleAccordion.setMaxWidth(600);

        CFXAccordionItem multiItem1 = new CFXAccordionItem("Panel A");
        multiItem1.setContent(createSampleContent("You can expand multiple panels at once in this accordion."));

        CFXAccordionItem multiItem2 = new CFXAccordionItem("Panel B");
        multiItem2.setContent(createSampleContent("Try expanding both panels simultaneously."));

        CFXAccordionItem multiItem3 = new CFXAccordionItem("Panel C");
        multiItem3.setContent(createSampleContent("This is useful when you need to compare content across sections."));

        multipleAccordion.getItems().addAll(multiItem1, multiItem2, multiItem3);

        // ====================================
        // With Icons and Disabled Items
        // ====================================
        Label iconsLabel = new Label("With Icons and Disabled State");
        iconsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXAccordion iconsAccordion = new CFXAccordion();
        iconsAccordion.setVariant(AccordionVariant.OUTLINED);
        iconsAccordion.setMaxWidth(600);

        CFXAccordionItem iconItem1 = new CFXAccordionItem("Active Item");
        iconItem1.setIcon(createCircleIcon(Color.web("#4CAF50")));
        iconItem1.setContent(createSampleContent("This item has a green icon and is enabled."));

        CFXAccordionItem iconItem2 = new CFXAccordionItem("Disabled Item");
        iconItem2.setIcon(createCircleIcon(Color.web("#9E9E9E")));
        iconItem2.setContent(createSampleContent("This content cannot be accessed."));
        iconItem2.setDisabled(true);

        CFXAccordionItem iconItem3 = new CFXAccordionItem("Another Active Item");
        iconItem3.setIcon(createCircleIcon(Color.web("#2196F3")));
        iconItem3.setContent(createSampleContent("This item has a blue icon."));

        iconsAccordion.getItems().addAll(iconItem1, iconItem2, iconItem3);

        // ====================================
        // Control Buttons
        // ====================================
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        CFXButton expandAllBtn = new CFXButton("Expand All (Multiple Mode)");
        expandAllBtn.setVariant(ButtonVariant.OUTLINED);
        expandAllBtn.setOnAction(e -> multipleAccordion.expandAll());

        CFXButton collapseAllBtn = new CFXButton("Collapse All");
        collapseAllBtn.setVariant(ButtonVariant.OUTLINED);
        collapseAllBtn.setOnAction(e -> {
            standardAccordion.collapseAll();
            outlinedAccordion.collapseAll();
            filledAccordion.collapseAll();
            multipleAccordion.collapseAll();
            iconsAccordion.collapseAll();
        });

        controls.getChildren().addAll(expandAllBtn, collapseAllBtn);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                standardLabel,
                standardAccordion,
                new Separator(),
                outlinedLabel,
                outlinedAccordion,
                new Separator(),
                filledLabel,
                filledAccordion,
                new Separator(),
                multipleLabel,
                multipleAccordion,
                new Separator(),
                iconsLabel,
                iconsAccordion,
                new Separator(),
                controls
        );

        Scene scene = new Scene(scrollPane, 900, 1000);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Accordion Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSampleContent(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #616161;");

        VBox container = new VBox(label);
        container.setPadding(new Insets(0));
        return container;
    }

    private HBox createBulletPoint(String text) {
        Label bullet = new Label("•");
        bullet.setStyle("-fx-font-size: 16px; -fx-text-fill: #616161; -fx-padding: 0 8 0 0;");

        Label content = new Label(text);
        content.setWrapText(true);
        content.setStyle("-fx-font-size: 14px; -fx-text-fill: #616161;");

        HBox box = new HBox(bullet, content);
        box.setAlignment(Pos.TOP_LEFT);
        return box;
    }

    private Circle createCircleIcon(Color color) {
        Circle circle = new Circle(6);
        circle.setFill(color);
        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
