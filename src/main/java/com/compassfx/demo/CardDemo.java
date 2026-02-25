package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.CardElevation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Demo application showcasing CFXCard features
 */
public class CardDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        // Title
        Label title = new Label("CompassFX Card Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ===== ELEVATION LEVELS SECTION =====
        Label elevationLabel = new Label("Elevation Levels");
        elevationLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox elevationSection = new HBox(20);
        elevationSection.setAlignment(Pos.CENTER);

        CFXCard noneCard = createSimpleCard("No Elevation", "This card has no shadow");
        noneCard.setElevation(CardElevation.NONE);

        CFXCard lowCard = createSimpleCard("Low Elevation", "Subtle shadow for slight depth");
        lowCard.setElevation(CardElevation.LOW);

        CFXCard mediumCard = createSimpleCard("Medium Elevation", "Default shadow level");
        mediumCard.setElevation(CardElevation.MEDIUM);

        CFXCard highCard = createSimpleCard("High Elevation", "Prominent shadow for emphasis");
        highCard.setElevation(CardElevation.HIGH);

        elevationSection.getChildren().addAll(noneCard, lowCard, mediumCard, highCard);

        // ===== BASIC CARDS SECTION =====
        Label basicLabel = new Label("Basic Cards");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox basicSection = new HBox(20);
        basicSection.setAlignment(Pos.CENTER);

        // Simple card
        CFXCard simpleCard = new CFXCard();
        simpleCard.setPrefWidth(250);
        Label simpleContent = new Label("This is a simple card with just content. It uses the default medium elevation.");
        simpleContent.setWrapText(true);
        simpleCard.setContent(simpleContent);

        // Card with header
        CFXCard headerCard = new CFXCard();
        headerCard.setPrefWidth(250);
        Label headerTitle = new Label("Card Title");
        headerCard.setHeader(headerTitle);
        Label headerContent = new Label("This card has a header section with a title.");
        headerContent.setWrapText(true);
        headerCard.setContent(headerContent);

        // Card with footer
        CFXCard footerCard = new CFXCard();
        footerCard.setPrefWidth(250);
        Label footerContent = new Label("This card includes action buttons in the footer.");
        footerContent.setWrapText(true);
        footerCard.setContent(footerContent);
        HBox footerButtons = new HBox(10);
        footerButtons.setAlignment(Pos.CENTER_RIGHT);
        CFXButton btn1 = new CFXButton("Cancel");
        btn1.setVariant(ButtonVariant.TEXT);
        CFXButton btn2 = new CFXButton("OK");
        btn2.setVariant(ButtonVariant.TEXT);
        footerButtons.getChildren().addAll(btn1, btn2);
        footerCard.setFooter(footerButtons);

        basicSection.getChildren().addAll(simpleCard, headerCard, footerCard);

        // ===== CARD WITH MEDIA SECTION =====
        Label mediaLabel = new Label("Cards with Media");
        mediaLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox mediaSection = new HBox(20);
        mediaSection.setAlignment(Pos.CENTER);

        // Card with colored rectangle as media
        CFXCard mediaCard1 = createMediaCard(
                Color.rgb(33, 150, 243),
                "Blue Project",
                "This card uses a colored rectangle to simulate an image or graphic element.",
                "View Details"
        );

        CFXCard mediaCard2 = createMediaCard(
                Color.rgb(156, 39, 176),
                "Purple Design",
                "The media section appears at the top with rounded corners.",
                "Learn More"
        );

        CFXCard mediaCard3 = createMediaCard(
                Color.rgb(76, 175, 80),
                "Green Initiative",
                "Perfect for showcasing images, charts, or other visual content.",
                "Explore"
        );

        mediaSection.getChildren().addAll(mediaCard1, mediaCard2, mediaCard3);

        // ===== HOVERABLE CARDS SECTION =====
        Label hoverLabel = new Label("Hoverable Cards (Clickable)");
        hoverLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox hoverSection = new HBox(20);
        hoverSection.setAlignment(Pos.CENTER);

        CFXCard hoverCard1 = createHoverCard("Product 1", "$29.99", "Premium quality product with excellent features.");
        hoverCard1.setOnMouseClicked(e -> System.out.println("Product 1 clicked!"));

        CFXCard hoverCard2 = createHoverCard("Product 2", "$49.99", "Professional grade solution for your needs.");
        hoverCard2.setOnMouseClicked(e -> System.out.println("Product 2 clicked!"));

        CFXCard hoverCard3 = createHoverCard("Product 3", "$79.99", "Enterprise level features and support.");
        hoverCard3.setOnMouseClicked(e -> System.out.println("Product 3 clicked!"));

        hoverSection.getChildren().addAll(hoverCard1, hoverCard2, hoverCard3);

        // ===== COMPLEX CARD EXAMPLE =====
        Label complexLabel = new Label("Complex Card Example");
        complexLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXCard complexCard = createComplexCard();
        complexCard.setMaxWidth(600);

        // ===== DASHBOARD EXAMPLE =====
        Label dashboardLabel = new Label("Dashboard Cards");
        dashboardLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox dashboardSection = new HBox(20);
        dashboardSection.setAlignment(Pos.CENTER);

        CFXCard statsCard1 = createStatsCard("Total Users", "1,234", "+12%", Color.rgb(33, 150, 243));
        CFXCard statsCard2 = createStatsCard("Revenue", "$45.2K", "+8%", Color.rgb(76, 175, 80));
        CFXCard statsCard3 = createStatsCard("Orders", "567", "-3%", Color.rgb(255, 152, 0));
        CFXCard statsCard4 = createStatsCard("Sessions", "8.9K", "+15%", Color.rgb(156, 39, 176));

        dashboardSection.getChildren().addAll(statsCard1, statsCard2, statsCard3, statsCard4);

        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                elevationLabel,
                elevationSection,
                new Separator(),
                basicLabel,
                basicSection,
                new Separator(),
                mediaLabel,
                mediaSection,
                new Separator(),
                hoverLabel,
                hoverSection,
                new Separator(),
                complexLabel,
                complexCard,
                new Separator(),
                dashboardLabel,
                dashboardSection
        );

        // Create scene and apply theme
        Scene scene = new Scene(scrollPane, 1200, 1800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Card Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create a simple card with title and description
     */
    CFXCard createSimpleCard(String titleText, String description) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(200);

        VBox content = new VBox(8);
        Label title = new Label(titleText);
        title.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");
        Label desc = new Label(description);
        desc.setWrapText(true);
        desc.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(0, 0, 0, 0.6);");
        content.getChildren().addAll(title, desc);

        card.setContent(content);
        return card;
    }

    /**
     * Create a card with media (colored rectangle)
     */
    CFXCard createMediaCard(Color color, String title, String description, String buttonText) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(250);

        // Media (colored rectangle)
        Rectangle media = new Rectangle(250, 140);
        media.setFill(color);
        card.setMedia(media);

        // Header
        Label headerLabel = new Label(title);
        card.setHeader(headerLabel);

        // Content
        Label content = new Label(description);
        content.setWrapText(true);
        card.setContent(content);

        // Footer
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);
        CFXButton button = new CFXButton(buttonText);
        button.setVariant(ButtonVariant.TEXT);
        footer.getChildren().add(button);
        card.setFooter(footer);

        return card;
    }

    /**
     * Create a hoverable card
     */
    CFXCard createHoverCard(String title, String price, String description) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(250);
        card.setHoverable(true);

        VBox content = new VBox(12);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: 600; -fx-font-size: 18px;");

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 13px;");

        content.getChildren().addAll(titleLabel, priceLabel, descLabel);
        card.setContent(content);

        return card;
    }

    /**
     * Create a complex card with all sections
     */
    CFXCard createComplexCard() {
        CFXCard card = new CFXCard();

        // Header with title and subtitle
        VBox header = new VBox(4);
        Label headerTitle = new Label("User Profile");
        headerTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");
        Label headerSubtitle = new Label("Manage your account settings");
        headerSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(0, 0, 0, 0.6);");
        header.getChildren().addAll(headerTitle, headerSubtitle);
        card.setHeader(header);

        // Content with form fields
        VBox content = new VBox(15);

        CFXTextField nameField = new CFXTextField();
        nameField.setPromptText("Full Name");
        nameField.setText("John Doe");

        CFXTextField emailField = new CFXTextField();
        emailField.setPromptText("Email");
        emailField.setText("john.doe@example.com");

        CFXComboBox<String> roleCombo = new CFXComboBox<>();
        roleCombo.setPromptText("Select Role");
        roleCombo.getItems().addAll("Administrator", "Manager", "User");
        roleCombo.setValue("User");

        CFXToggle notifications = new CFXToggle("Enable notifications");
        notifications.setSelected(true);

        content.getChildren().addAll(nameField, emailField, roleCombo, notifications);
        card.setContent(content);

        // Footer with buttons
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER_RIGHT);
        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);
        CFXButton saveBtn = new CFXButton("Save Changes");
        footer.getChildren().addAll(cancelBtn, saveBtn);
        card.setFooter(footer);

        return card;
    }

    /**
     * Create a stats dashboard card
     */
    CFXCard createStatsCard(String label, String value, String change, Color accentColor) {
        CFXCard card = new CFXCard();
        card.setPrefWidth(200);
        card.setElevation(CardElevation.LOW);

        VBox content = new VBox(8);

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(0, 0, 0, 0.6);");

        Label valueText = new Label(value);
        valueText.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        valueText.setTextFill(accentColor);

        Label changeText = new Label(change);
        boolean positive = change.startsWith("+");
        changeText.setStyle("-fx-font-size: 12px; -fx-font-weight: 600;");
        changeText.setTextFill(positive ? Color.rgb(76, 175, 80) : Color.rgb(244, 67, 54));

        content.getChildren().addAll(labelText, valueText, changeText);
        card.setContent(content);

        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}