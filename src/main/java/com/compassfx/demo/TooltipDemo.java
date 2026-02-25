package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.TooltipPosition;
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

/**
 * Demo application showcasing CFXTooltip features
 */
public class TooltipDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FFFFFF;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        // Title
        Label title = new Label("CompassFX Tooltip Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label("Hover over elements to see tooltips");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: rgba(0, 0, 0, 0.6);");

        // ===== BASIC TOOLTIPS SECTION =====
        Label basicLabel = new Label("Basic Tooltips on Buttons");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox basicSection = new HBox(15);
        basicSection.setAlignment(Pos.CENTER);

        CFXButton saveBtn = new CFXButton("Save");
        CFXTooltip.install(saveBtn, "Save your changes");

        CFXButton deleteBtn = new CFXButton("Delete");
        CFXTooltip.install(deleteBtn, "Delete this item permanently");

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);
        CFXTooltip.install(cancelBtn, "Cancel and go back");

        basicSection.getChildren().addAll(saveBtn, deleteBtn, cancelBtn);

        // ===== POSITION VARIANTS SECTION =====
        Label positionLabel = new Label("Tooltip Positions");
        positionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox positionSection = new VBox(30);
        positionSection.setAlignment(Pos.CENTER);

        // Top position
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        CFXButton topBtn = new CFXButton("Hover Me");
        CFXTooltip.install(topBtn, "Tooltip appears on top", TooltipPosition.TOP);
        topBox.getChildren().add(topBtn);

        // Bottom position
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        CFXButton bottomBtn = new CFXButton("Hover Me");
        CFXTooltip.install(bottomBtn, "Tooltip appears on bottom", TooltipPosition.BOTTOM);
        bottomBox.getChildren().add(bottomBtn);

        // Left and Right positions
        HBox sideBox = new HBox(100);
        sideBox.setAlignment(Pos.CENTER);

        CFXButton leftBtn = new CFXButton("Hover Me");
        CFXTooltip.install(leftBtn, "Tooltip on left", TooltipPosition.LEFT);

        CFXButton rightBtn = new CFXButton("Hover Me");
        CFXTooltip.install(rightBtn, "Tooltip on right", TooltipPosition.RIGHT);

        sideBox.getChildren().addAll(leftBtn, rightBtn);

        positionSection.getChildren().addAll(topBox, sideBox, bottomBox);

        // ===== TOOLTIPS ON VARIOUS COMPONENTS SECTION =====
        Label componentsLabel = new Label("Tooltips on Various Components");
        componentsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox componentsSection = new VBox(15);
        componentsSection.setAlignment(Pos.CENTER_LEFT);
        componentsSection.setMaxWidth(400);

        // TextField with tooltip
        CFXTextField nameField = new CFXTextField();
        nameField.setPromptText("Enter your name");
        CFXTooltip.install(nameField, "Please enter your full name");

        // ComboBox with tooltip
        CFXComboBox<String> countryBox = new CFXComboBox<>();
        countryBox.setPromptText("Select country");
        countryBox.getItems().addAll("United States", "Canada", "Mexico");
        CFXTooltip.install(countryBox, "Select your country of residence");

        // Checkbox with tooltip
        CFXCheckbox termsCheckbox = new CFXCheckbox("I agree to terms and conditions");
        CFXTooltip.install(termsCheckbox, "You must agree to continue");

        // Toggle with tooltip
        CFXToggle notificationsToggle = new CFXToggle("Enable notifications");
        CFXTooltip.install(notificationsToggle, "Receive email and push notifications");

        componentsSection.getChildren().addAll(nameField, countryBox, termsCheckbox, notificationsToggle);

        // ===== ICON BUTTONS WITH TOOLTIPS SECTION =====
        Label iconLabel = new Label("Icon Buttons with Tooltips");
        iconLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox iconSection = new HBox(15);
        iconSection.setAlignment(Pos.CENTER);

        // Simulate icon buttons with colored circles
        VBox editBox = createIconButton(Color.rgb(33, 150, 243), "✎", "Edit");
        VBox deleteBox = createIconButton(Color.rgb(244, 67, 54), "✖", "Delete");
        VBox shareBox = createIconButton(Color.rgb(76, 175, 80), "⤴", "Share");
        VBox downloadBox = createIconButton(Color.rgb(255, 152, 0), "↓", "Download");
        VBox settingsBox = createIconButton(Color.rgb(156, 39, 176), "⚙", "Settings");

        iconSection.getChildren().addAll(editBox, deleteBox, shareBox, downloadBox, settingsBox);

        // ===== LONG TEXT TOOLTIP SECTION =====
        Label longTextLabel = new Label("Long Text Tooltip");
        longTextLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton infoBtn = new CFXButton("Hover for More Info");
        CFXTooltip longTooltip = new CFXTooltip(
                "This is a longer tooltip that demonstrates text wrapping. " +
                        "Tooltips automatically wrap text when it exceeds the maximum width. " +
                        "This helps maintain readability for longer descriptions."
        );
        longTooltip.install(infoBtn);

        // ===== CARD WITH TOOLTIPS SECTION =====
        Label cardLabel = new Label("Interactive Card with Tooltips");
        cardLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXCard interactiveCard = new CFXCard();
        interactiveCard.setMaxWidth(500);

        VBox cardContent = new VBox(15);

        Label cardTitle = new Label("User Profile");
        cardTitle.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");

        CFXTextField emailField = new CFXTextField();
        emailField.setPromptText("Email");
        emailField.setText("user@example.com");
        CFXTooltip.install(emailField, "Your primary email address");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        CFXButton cardSaveBtn = new CFXButton("Save");
        CFXTooltip.install(cardSaveBtn, "Save profile changes");

        CFXButton cardCancelBtn = new CFXButton("Cancel");
        cardCancelBtn.setVariant(ButtonVariant.TEXT);
        CFXTooltip.install(cardCancelBtn, "Discard changes");

        buttonBox.getChildren().addAll(cardCancelBtn, cardSaveBtn);

        cardContent.getChildren().addAll(cardTitle, emailField, buttonBox);
        interactiveCard.setContent(cardContent);

        // ===== QUICK ACTIONS TOOLBAR =====
        Label toolbarLabel = new Label("Quick Actions Toolbar");
        toolbarLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox toolbar = new HBox(5);
        toolbar.setAlignment(Pos.CENTER);
        toolbar.setStyle("-fx-background-color: #F5F5F5; -fx-padding: 10px; -fx-background-radius: 4px;");

        String[] actions = {"New", "Open", "Save", "Print", "Undo", "Redo", "Cut", "Copy", "Paste"};
        String[] tooltips = {
                "Create new document (Ctrl+N)",
                "Open existing file (Ctrl+O)",
                "Save current file (Ctrl+S)",
                "Print document (Ctrl+P)",
                "Undo last action (Ctrl+Z)",
                "Redo last action (Ctrl+Y)",
                "Cut selection (Ctrl+X)",
                "Copy selection (Ctrl+C)",
                "Paste from clipboard (Ctrl+V)"
        };

        for (int i = 0; i < actions.length; i++) {
            CFXButton actionBtn = new CFXButton(actions[i]);
            actionBtn.setVariant(ButtonVariant.TEXT);
            CFXTooltip.install(actionBtn, tooltips[i]);
            toolbar.getChildren().add(actionBtn);
        }

        // Add all sections to root
        root.getChildren().addAll(
                title,
                subtitle,
                new Separator(),
                basicLabel,
                basicSection,
                new Separator(),
                positionLabel,
                positionSection,
                new Separator(),
                componentsLabel,
                componentsSection,
                new Separator(),
                iconLabel,
                iconSection,
                new Separator(),
                longTextLabel,
                infoBtn,
                new Separator(),
                cardLabel,
                interactiveCard,
                new Separator(),
                toolbarLabel,
                toolbar
        );

        // Create scene and apply theme
        Scene scene = new Scene(scrollPane, 1200, 1800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Tooltip Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create an icon button with tooltip
     */
    private VBox createIconButton(Color color, String icon, String tooltipText) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        Circle iconCircle = new Circle(20);
        iconCircle.setFill(color);
        iconCircle.setStyle("-fx-cursor: hand;");

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-cursor: hand;");
        iconLabel.setMouseTransparent(true);

        VBox iconContainer = new VBox();
        iconContainer.setAlignment(Pos.CENTER);
        iconContainer.getChildren().addAll(iconCircle, iconLabel);
        iconContainer.setTranslateY(20);

        CFXTooltip.install(iconCircle, tooltipText);

        container.getChildren().add(iconContainer);
        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }
}