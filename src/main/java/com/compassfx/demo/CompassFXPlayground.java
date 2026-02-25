// ============================================
// CompassFXPlayground.java - Main Demo Application
// src/main/java/com/compassfx/demo/CompassFXPlayground.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.*;
import com.compassfx.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * CompassFX Playground - Single Page Application
 * Interactive showcase of all CompassFX components
 */
public class CompassFXPlayground extends Application {

    private VBox contentArea;
    private ScrollPane contentScroll;
    private CFXDrawer navigationDrawer;
    private Label pageTitle;

    @Override
    public void start(Stage primaryStage) {
        // Main container
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // ====================================
        // Top Bar
        // ====================================
        HBox topBar = createTopBar();
        root.setTop(topBar);

        // ====================================
        // Content Area
        // ====================================
        VBox contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(30));
        contentContainer.setAlignment(Pos.TOP_CENTER);

        pageTitle = new Label("Welcome to CompassFX");
        pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        contentArea = new VBox(30);
        contentArea.setAlignment(Pos.TOP_CENTER);

        contentContainer.getChildren().addAll(pageTitle, contentArea);

        contentScroll = new ScrollPane(contentContainer);
        contentScroll.setFitToWidth(true);
        contentScroll.setStyle("-fx-background: #F5F5F5; -fx-background-color: #F5F5F5;");

        root.setCenter(contentScroll);

        // ====================================
        // Navigation Drawer
        // ====================================
        navigationDrawer = createNavigationDrawer();

        // Show welcome page
        showWelcomePage();

        // Root with drawer
        StackPane rootWithDrawer = new StackPane(root, navigationDrawer);

        Scene scene = new Scene(rootWithDrawer, 1200, 800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Playground");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0;");

        // Menu button
        CFXButton menuBtn = new CFXButton("☰");
        menuBtn.setOnAction(e -> navigationDrawer.toggle());

        // Logo/Title
        Label logo = new Label("CompassFX Playground");
        logo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Theme toggle
        CFXButton themeBtn = new CFXButton("🌙 Dark Mode");
        themeBtn.setColor(ButtonColor.SECONDARY);

        topBar.getChildren().addAll(menuBtn, logo, spacer, themeBtn);

        return topBar;
    }

    private CFXDrawer createNavigationDrawer() {
        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.LEFT);
        drawer.setSize(DrawerSize.SMALL);

        VBox menu = new VBox(5);
        menu.setPadding(new Insets(20));

        Label menuTitle = new Label("Components");
        menuTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        menuTitle.setPadding(new Insets(0, 0, 15, 0));

        menu.getChildren().addAll(
                menuTitle,
                new Separator(),
                createMenuItem("🏠 Welcome", this::showWelcomePage),
                createMenuItem("🔘 Buttons", this::showButtonsPage),
                createMenuItem("📦 Cards", this::showCardsPage),
                createMenuItem("☑️ Checkboxes", this::showCheckboxPage),
                createMenuItem("📊 Progress", this::showProgressPage),
                createMenuItem("📝 Text Fields", this::showTextFieldPage),
                createMenuItem("🎚️ Sliders", this::showSlidersPage),
                createMenuItem("🗂️ Tabs", this::showTabsPage),
                createMenuItem("🍔 Menus", this::showMenusPage),
                createMenuItem("📋 Tables", this::showTablesPage),
                createMenuItem("📐 Grid", this::showGridPage),
                createMenuItem("🎠 Carousel", this::showCarouselPage),
                createMenuItem("📤 File Upload", this::showFileUploadPage),
                createMenuItem("👤 Avatars", this::showAvatarsPage),
                createMenuItem("🗄️ Drawer", this::showDrawerPage),
                createMenuItem("🎨 All Components", this::showAllComponents)
        );

        drawer.setContent(menu);

        return drawer;
    }

    private HBox createMenuItem(String text, Runnable action) {
        HBox item = new HBox();
        item.setPadding(new Insets(12, 15, 12, 15));
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("menu-item");
        item.setCursor(javafx.scene.Cursor.HAND);

        Label label = new Label(text);
        label.setFont(Font.font("System", 14));

        item.getChildren().add(label);

        item.setOnMouseEntered(e -> {
            item.setStyle("-fx-background-color: rgba(33, 150, 243, 0.08); -fx-background-radius: 6px;");
        });
        item.setOnMouseExited(e -> {
            item.setStyle("");
        });
        item.setOnMouseClicked(e -> {
            action.run();
            navigationDrawer.close();
        });

        return item;
    }

    private void showWelcomePage() {
        pageTitle.setText("Welcome to CompassFX");
        contentArea.getChildren().clear();

        Label subtitle = new Label("A Modern Material Design Component Library for JavaFX");
        subtitle.setStyle("-fx-font-size: 18px; -fx-text-fill: #666;");

        VBox welcomeBox = new VBox(20);
        welcomeBox.setMaxWidth(800);
        welcomeBox.setPadding(new Insets(30));
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 12px;"
        );

        Label welcome = new Label("👋 Welcome!");
        welcome.setStyle("-fx-font-size: 48px;");

        Label description = new Label(
                "CompassFX is a comprehensive JavaFX component library inspired by Material Design.\n\n" +
                        "Explore beautiful, customizable components with smooth animations and modern styling.\n\n" +
                        "Use the menu (☰) to navigate through different component demos."
        );
        description.setWrapText(true);
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #424242; -fx-text-alignment: center;");

        HBox stats = new HBox(40);
        stats.setAlignment(Pos.CENTER);

        VBox stat1 = createStatBox("15+", "Components");
        VBox stat2 = createStatBox("100%", "Material Design");
        VBox stat3 = createStatBox("∞", "Possibilities");

        stats.getChildren().addAll(stat1, stat2, stat3);

        welcomeBox.getChildren().addAll(welcome, description, new Separator(), stats);

        contentArea.getChildren().add(welcomeBox);
    }

    private VBox createStatBox(String number, String label) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        Label num = new Label(number);
        num.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        box.getChildren().addAll(num, lbl);
        return box;
    }

    private void showButtonsPage() {
        pageTitle.setText("Buttons");
        contentArea.getChildren().clear();

        // Contained Buttons
        Label containedLabel = new Label("Contained Buttons");
        containedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox containedButtons = new HBox(10);
        containedButtons.setAlignment(Pos.CENTER);

        CFXButton primary = new CFXButton("Primary");
        CFXButton secondary = new CFXButton("Secondary");
        secondary.setColor(ButtonColor.SECONDARY);
        CFXButton success = new CFXButton("Success");
        success.setColor(ButtonColor.SUCCESS);
        CFXButton warning = new CFXButton("Warning");
        warning.setColor(ButtonColor.WARNING);
        CFXButton error = new CFXButton("Error");
        error.setColor(ButtonColor.ERROR);

        containedButtons.getChildren().addAll(primary, secondary, success, warning, error);

        // Outlined Buttons
        Label outlinedLabel = new Label("Outlined Buttons");
        outlinedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox outlinedButtons = new HBox(10);
        outlinedButtons.setAlignment(Pos.CENTER);

        CFXButton outlined1 = new CFXButton("Primary");
        outlined1.setVariant(ButtonVariant.OUTLINED);
        CFXButton outlined2 = new CFXButton("Success");
        outlined2.setVariant(ButtonVariant.OUTLINED);
        outlined2.setColor(ButtonColor.SUCCESS);

        outlinedButtons.getChildren().addAll(outlined1, outlined2);

        // Sizes
        Label sizesLabel = new Label("Button Sizes");
        sizesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox sizeButtons = new HBox(10);
        sizeButtons.setAlignment(Pos.CENTER);

        CFXButton small = new CFXButton("Small");
        small.setSize(ButtonSize.SMALL);
        CFXButton medium = new CFXButton("Medium");
        CFXButton large = new CFXButton("Large");
        large.setSize(ButtonSize.LARGE);

        sizeButtons.getChildren().addAll(small, medium, large);

        Label disabledLabel = new Label("Disabled Buttons");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");
        HBox disabledButtons = new HBox(10);
        disabledButtons.setAlignment(Pos.CENTER);
        CFXButton disabledContained = new CFXButton("Disabled");
        disabledContained.setDisable(true);
        CFXButton disabledOutlined = new CFXButton("Disabled");
        disabledOutlined.setVariant(ButtonVariant.OUTLINED);
        disabledOutlined.setDisable(true);

        disabledButtons.getChildren().addAll(disabledContained, disabledOutlined);

        // Full Width
        Label fullWidthLabel = new Label("Full Width Button");
        fullWidthLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton fullWidthBtn = new CFXButton("Full Width Button");
        fullWidthBtn.setFullWidth(true);

        contentArea.getChildren().addAll(
                containedLabel, containedButtons, new Separator(),
                outlinedLabel, outlinedButtons, new Separator(),
                sizesLabel, sizeButtons, new Separator(),
                disabledLabel, disabledButtons, new Separator(),
                fullWidthLabel, fullWidthBtn
        );
    }

    private void showCardsPage() {
        pageTitle.setText("Cards");
        contentArea.getChildren().clear();

        CardDemo cardDemo = new CardDemo();

        VBox cardsRow = new VBox(20);
        cardsRow.setAlignment(Pos.CENTER);

        Label elevationLabel = new Label("Elevation Levels");
        elevationLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox elevationSection = new HBox(20);
        elevationSection.setAlignment(Pos.CENTER);

        CFXCard noneCard = cardDemo.createSimpleCard("No Elevation", "This card has no shadow");
        noneCard.setElevation(CardElevation.NONE);

        CFXCard lowCard = cardDemo.createSimpleCard("Low Elevation", "Subtle shadow for slight depth");
        lowCard.setElevation(CardElevation.LOW);

        CFXCard mediumCard = cardDemo.createSimpleCard("Medium Elevation", "Default shadow level");
        mediumCard.setElevation(CardElevation.MEDIUM);

        CFXCard highCard = cardDemo.createSimpleCard("High Elevation", "Prominent shadow for emphasis");
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
        CFXCard mediaCard1 = cardDemo.createMediaCard(
                Color.rgb(33, 150, 243),
                "Blue Project",
                "This card uses a colored rectangle to simulate an image or graphic element.",
                "View Details"
        );

        CFXCard mediaCard2 = cardDemo.createMediaCard(
                Color.rgb(156, 39, 176),
                "Purple Design",
                "The media section appears at the top with rounded corners.",
                "Learn More"
        );

        CFXCard mediaCard3 = cardDemo.createMediaCard(
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

        CFXCard hoverCard1 = cardDemo.createHoverCard("Product 1", "$29.99", "Premium quality product with excellent features.");
        hoverCard1.setOnMouseClicked(e -> System.out.println("Product 1 clicked!"));

        CFXCard hoverCard2 = cardDemo.createHoverCard("Product 2", "$49.99", "Professional grade solution for your needs.");
        hoverCard2.setOnMouseClicked(e -> System.out.println("Product 2 clicked!"));

        CFXCard hoverCard3 = cardDemo.createHoverCard("Product 3", "$79.99", "Enterprise level features and support.");
        hoverCard3.setOnMouseClicked(e -> System.out.println("Product 3 clicked!"));

        hoverSection.getChildren().addAll(hoverCard1, hoverCard2, hoverCard3);

        // ===== COMPLEX CARD EXAMPLE =====
        Label complexLabel = new Label("Complex Card Example");
        complexLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXCard complexCard = cardDemo.createComplexCard();
        complexCard.setMaxWidth(600);

        // ===== DASHBOARD EXAMPLE =====
        Label dashboardLabel = new Label("Dashboard Cards");
        dashboardLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox dashboardSection = new HBox(20);
        dashboardSection.setAlignment(Pos.CENTER);

        CFXCard statsCard1 = cardDemo.createStatsCard("Total Users", "1,234", "+12%", Color.rgb(33, 150, 243));
        CFXCard statsCard2 = cardDemo.createStatsCard("Revenue", "$45.2K", "+8%", Color.rgb(76, 175, 80));
        CFXCard statsCard3 = cardDemo.createStatsCard("Orders", "567", "-3%", Color.rgb(255, 152, 0));
        CFXCard statsCard4 = cardDemo.createStatsCard("Sessions", "8.9K", "+15%", Color.rgb(156, 39, 176));

        dashboardSection.getChildren().addAll(statsCard1, statsCard2, statsCard3, statsCard4);

        // Add all sections to root
        cardsRow.getChildren().addAll(
                pageTitle,
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

        contentArea.getChildren().add(cardsRow);
    }

    private void showCheckboxPage() {
        pageTitle.setText("Checkboxes & Radio Buttons");
        contentArea.getChildren().clear();
        CheckboxRadioDemo checkboxRadioDemo = new CheckboxRadioDemo();

        Label checkLabel = new Label("Checkboxes");
        checkLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox checkboxes = new VBox(10);
        checkboxes.setAlignment(Pos.CENTER_LEFT);
        checkboxes.setMaxWidth(400);

        Label checkboxLabel = new Label("Checkboxes");
        checkboxLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        Label variantsLabel = new Label("Checkbox Color Variants");
        variantsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox variantsSection = new VBox(15);
        variantsSection.setAlignment(Pos.CENTER_LEFT);
        variantsSection.setMaxWidth(400);

        Label variantsDesc = new Label("Different colors for different contexts:");
        variantsDesc.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox defaultCheck = new CFXCheckbox("Default (Dark Gray)");
        defaultCheck.setVariant(CheckboxVariant.DEFAULT);
        defaultCheck.setSelected(true);

        CFXCheckbox primaryCheck = new CFXCheckbox("Primary (Blue) - Default");
        primaryCheck.setVariant(CheckboxVariant.PRIMARY);
        primaryCheck.setSelected(true);

        CFXCheckbox secondaryCheck = new CFXCheckbox("Secondary (Purple)");
        secondaryCheck.setVariant(CheckboxVariant.SECONDARY);
        secondaryCheck.setSelected(true);

        CFXCheckbox successCheck = new CFXCheckbox("Success (Green) - I agree");
        successCheck.setVariant(CheckboxVariant.SUCCESS);
        successCheck.setSelected(true);

        CFXCheckbox warningCheck = new CFXCheckbox("Warning (Orange) - Important");
        warningCheck.setVariant(CheckboxVariant.WARNING);
        warningCheck.setSelected(true);

        CFXCheckbox errorCheck = new CFXCheckbox("Error (Red) - Delete permanently");
        errorCheck.setVariant(CheckboxVariant.ERROR);
        errorCheck.setSelected(true);

        variantsSection.getChildren().addAll(
                variantsDesc,
                defaultCheck,
                primaryCheck,
                secondaryCheck,
                successCheck,
                warningCheck,
                errorCheck
        );

        VBox checkboxSection = new VBox(15);
        checkboxSection.setAlignment(Pos.CENTER_LEFT);
        checkboxSection.setMaxWidth(400);

        // Basic checkboxes
        Label basicCheckboxLabel = new Label("Basic Checkboxes:");
        basicCheckboxLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox checkbox1 = new CFXCheckbox("Accept terms and conditions");
        CFXCheckbox checkbox2 = new CFXCheckbox("Subscribe to newsletter");
        CFXCheckbox checkbox3 = new CFXCheckbox("Enable notifications");
        checkbox3.setSelected(true);

        // Indeterminate checkbox
        Label indeterminateLabel = new Label("Indeterminate State:");
        indeterminateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox parentCheckbox = new CFXCheckbox("Select all items");
        CFXCheckbox childCheckbox1 = new CFXCheckbox("  Item 1");
        CFXCheckbox childCheckbox2 = new CFXCheckbox("  Item 2");
        CFXCheckbox childCheckbox3 = new CFXCheckbox("  Item 3");

        // Update parent checkbox based on children
        childCheckbox1.setSelected(true);
        checkboxRadioDemo.updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3);

        childCheckbox1.selectedProperty().addListener((obs, oldVal, newVal) ->
                checkboxRadioDemo.updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3));
        childCheckbox2.selectedProperty().addListener((obs, oldVal, newVal) ->
                checkboxRadioDemo.updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3));
        childCheckbox3.selectedProperty().addListener((obs, oldVal, newVal) ->
                checkboxRadioDemo.updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3));

        // Parent checkbox controls all children
        parentCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (!parentCheckbox.isIndeterminate()) {
                childCheckbox1.setSelected(newVal);
                childCheckbox2.setSelected(newVal);
                childCheckbox3.setSelected(newVal);
            }
        });

        // Disabled checkbox
        Label disabledCheckboxLabel = new Label("Disabled State:");
        disabledCheckboxLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox disabledCheckbox1 = new CFXCheckbox("Disabled unchecked");
        disabledCheckbox1.setDisable(true);

        CFXCheckbox disabledCheckbox2 = new CFXCheckbox("Disabled checked");
        disabledCheckbox2.setSelected(true);
        disabledCheckbox2.setDisable(true);

        checkboxSection.getChildren().addAll(
                variantsLabel, variantsSection,
                new Separator(),
                basicCheckboxLabel,
                checkbox1, checkbox2, checkbox3,
                new Separator(),
                indeterminateLabel,
                parentCheckbox,
                childCheckbox1, childCheckbox2, childCheckbox3,
                new Separator(),
                disabledCheckboxLabel,
                disabledCheckbox1, disabledCheckbox2
        );

        // ===== RADIO BUTTONS SECTION =====
        Label radioLabel = new Label("Radio Buttons");
        radioLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox radioSection = new VBox(15);
        radioSection.setAlignment(Pos.CENTER_LEFT);
        radioSection.setMaxWidth(400);

        // Basic radio buttons
        Label basicRadioLabel = new Label("Select Payment Method:");
        basicRadioLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        ToggleGroup paymentGroup = new ToggleGroup();

        CFXRadioButton creditCard = new CFXRadioButton("Credit Card");
        creditCard.setToggleGroup(paymentGroup);
        creditCard.setSelected(true);

        CFXRadioButton debitCard = new CFXRadioButton("Debit Card");
        debitCard.setToggleGroup(paymentGroup);

        CFXRadioButton paypal = new CFXRadioButton("PayPal");
        paypal.setToggleGroup(paymentGroup);

        CFXRadioButton bankTransfer = new CFXRadioButton("Bank Transfer");
        bankTransfer.setToggleGroup(paymentGroup);

        // Another radio group
        Label shippingLabel = new Label("Select Shipping Option:");
        shippingLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        ToggleGroup shippingGroup = new ToggleGroup();

        CFXRadioButton standard = new CFXRadioButton("Standard (5-7 days) - Free");
        standard.setToggleGroup(shippingGroup);
        standard.setSelected(true);

        CFXRadioButton express = new CFXRadioButton("Express (2-3 days) - $10");
        express.setToggleGroup(shippingGroup);

        CFXRadioButton overnight = new CFXRadioButton("Overnight (1 day) - $25");
        overnight.setToggleGroup(shippingGroup);

        // Disabled radio buttons
        Label disabledRadioLabel = new Label("Disabled State:");
        disabledRadioLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        ToggleGroup disabledGroup = new ToggleGroup();

        CFXRadioButton disabledRadio1 = new CFXRadioButton("Disabled unselected");
        disabledRadio1.setToggleGroup(disabledGroup);
        disabledRadio1.setDisable(true);

        CFXRadioButton disabledRadio2 = new CFXRadioButton("Disabled selected");
        disabledRadio2.setToggleGroup(disabledGroup);
        disabledRadio2.setSelected(true);
        disabledRadio2.setDisable(true);

        radioSection.getChildren().addAll(
                basicRadioLabel,
                creditCard, debitCard, paypal, bankTransfer,
                new Separator(),
                shippingLabel,
                standard, express, overnight,
                new Separator(),
                disabledRadioLabel,
                disabledRadio1, disabledRadio2
        );

        // ===== FORM EXAMPLE =====
        Label formLabel = new Label("Complete Form Example");
        formLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox formSection = new VBox(15);
        formSection.setAlignment(Pos.CENTER_LEFT);
        formSection.setMaxWidth(500);
        formSection.setPadding(new Insets(15));
        formSection.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 1px; -fx-border-radius: 8px;");

        Label formTitle = new Label("Account Preferences");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");

        // Notification preferences
        Label notifLabel = new Label("Notifications:");
        notifLabel.setStyle("-fx-font-weight: 500;");

        CFXCheckbox emailNotif = new CFXCheckbox("Email notifications");
        emailNotif.setSelected(true);

        CFXCheckbox pushNotif = new CFXCheckbox("Push notifications");

        CFXCheckbox smsNotif = new CFXCheckbox("SMS notifications");

        // Theme preference
        Label themeLabel = new Label("Theme:");
        themeLabel.setStyle("-fx-font-weight: 500;");

        ToggleGroup themeGroup = new ToggleGroup();

        CFXRadioButton lightTheme = new CFXRadioButton("Light");
        lightTheme.setToggleGroup(themeGroup);
        lightTheme.setSelected(true);

        CFXRadioButton darkTheme = new CFXRadioButton("Dark");
        darkTheme.setToggleGroup(themeGroup);

        CFXRadioButton autoTheme = new CFXRadioButton("Auto (system)");
        autoTheme.setToggleGroup(themeGroup);

        // Terms
        CFXCheckbox termsCheckbox = new CFXCheckbox("I agree to the terms and conditions");

        // Submit button
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        CFXButton saveButton = new CFXButton("Save Preferences");
        saveButton.setOnAction(e -> {
            System.out.println("=== Preferences Saved ===");
            System.out.println("Email: " + emailNotif.isSelected());
            System.out.println("Push: " + pushNotif.isSelected());
            System.out.println("SMS: " + smsNotif.isSelected());
            System.out.println("Theme: " +
                    (lightTheme.isSelected() ? "Light" :
                            darkTheme.isSelected() ? "Dark" : "Auto"));
            System.out.println("Terms accepted: " + termsCheckbox.isSelected());
        });

        buttonBox.getChildren().add(saveButton);

        formSection.getChildren().addAll(
                formTitle,
                new Separator(),
                notifLabel,
                emailNotif, pushNotif, smsNotif,
                new Separator(),
                themeLabel,
                lightTheme, darkTheme, autoTheme,
                new Separator(),
                termsCheckbox,
                buttonBox
        );

        // Add all sections to root
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                checkboxLabel,
                checkboxSection,
                new Separator(),
                radioLabel,
                radioSection,
                new Separator(),
                formLabel,
                formSection
        );
    }

    private void showProgressPage() {
        pageTitle.setText("Progress Indicators");
        contentArea.getChildren().clear();

        Label barLabel = new Label("Progress Bars");
        barLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        // ===== PROGRESS BAR - SIZES SECTION =====
        Label sizeLabel = new Label("ProgressBar - Sizes");
        sizeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox sizeSection = new VBox(15);
        sizeSection.setAlignment(Pos.CENTER_LEFT);
        sizeSection.setMaxWidth(500);

        VBox smallBox = ProgressDemo.createLabeledProgressBar("Small", 0.6, ProgressSize.SMALL, ProgressColor.PRIMARY);
        VBox mediumBox = ProgressDemo.createLabeledProgressBar("Medium (Default)", 0.6, ProgressSize.MEDIUM, ProgressColor.PRIMARY);
        VBox largeBox = ProgressDemo.createLabeledProgressBar("Large", 0.6, ProgressSize.LARGE, ProgressColor.PRIMARY);

        sizeSection.getChildren().addAll(smallBox, mediumBox, largeBox);

        // ===== PROGRESS BAR - COLORS SECTION =====
        Label colorLabel = new Label("ProgressBar - Colors");
        colorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox colorSection = new VBox(15);
        colorSection.setAlignment(Pos.CENTER_LEFT);
        colorSection.setMaxWidth(500);

        VBox primaryBox = ProgressDemo.createLabeledProgressBar("Primary", 0.75, ProgressSize.MEDIUM, ProgressColor.PRIMARY);
        VBox secondaryBox = ProgressDemo.createLabeledProgressBar("Secondary", 0.75, ProgressSize.MEDIUM, ProgressColor.SECONDARY);
        VBox successBox = ProgressDemo.createLabeledProgressBar("Success", 0.75, ProgressSize.MEDIUM, ProgressColor.SUCCESS);
        VBox warningBox = ProgressDemo.createLabeledProgressBar("Warning", 0.75, ProgressSize.MEDIUM, ProgressColor.WARNING);
        VBox errorBox = ProgressDemo.createLabeledProgressBar("Error", 0.75, ProgressSize.MEDIUM, ProgressColor.ERROR);

        colorSection.getChildren().addAll(primaryBox, secondaryBox, successBox, warningBox, errorBox);

        // ===== PROGRESS BAR - INDETERMINATE SECTION =====
        Label indeterminateLabel = new Label("Indeterminate ProgressBar");
        indeterminateLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox indeterminateSection = new VBox(15);
        indeterminateSection.setAlignment(Pos.CENTER_LEFT);
        indeterminateSection.setMaxWidth(500);

        Label indeterminateDesc = new Label("Loading...");
        indeterminateDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: rgba(0, 0, 0, 0.6);");

        CFXProgressBar indeterminateBar = new CFXProgressBar();
        indeterminateBar.setProgress(-1); // Indeterminate
        indeterminateBar.setPrefWidth(500);

        indeterminateSection.getChildren().addAll(indeterminateDesc, indeterminateBar);

        // ===== PROGRESS BAR - ANIMATED SECTION =====
        Label animatedLabel = new Label("Animated ProgressBar");
        animatedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox animatedSection = new VBox(15);
        animatedSection.setAlignment(Pos.CENTER_LEFT);
        animatedSection.setMaxWidth(500);

        Label animatedDesc = new Label("Progress: 0%");
        animatedDesc.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");

        CFXProgressBar animatedBar = new CFXProgressBar(0);
        animatedBar.setPrefWidth(500);
        animatedBar.setColor(ProgressColor.SUCCESS);

        // Animate progress
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    animatedBar.setProgress(0);
                    animatedDesc.setText("Progress: 0%");
                }),
                new KeyFrame(Duration.seconds(3), e -> {
                    animatedBar.setProgress(1.0);
                    animatedDesc.setText("Progress: 100% - Complete!");
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Create smooth animation
        Timeline smoothTimeline = new Timeline();
        for (int i = 0; i <= 100; i++) {
            final int progress = i;
            smoothTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(i * 30), e -> {
                        animatedBar.setProgress(progress / 100.0);
                        animatedDesc.setText("Progress: " + progress + "%");
                    })
            );
        }
        smoothTimeline.setCycleCount(Timeline.INDEFINITE);
        smoothTimeline.play();

        HBox animatedControls = new HBox(10);
        animatedControls.setAlignment(Pos.CENTER_LEFT);

        CFXButton pauseBtn = new CFXButton("Pause");
        pauseBtn.setOnAction(e -> {
            if (smoothTimeline.getStatus() == Timeline.Status.RUNNING) {
                smoothTimeline.pause();
                pauseBtn.setText("Resume");
            } else {
                smoothTimeline.play();
                pauseBtn.setText("Pause");
            }
        });

        CFXButton restartBtn = new CFXButton("Restart");
        restartBtn.setOnAction(e -> {
            smoothTimeline.playFromStart();
            pauseBtn.setText("Pause");
        });

        animatedControls.getChildren().addAll(pauseBtn, restartBtn);

        animatedSection.getChildren().addAll(animatedDesc, animatedBar, animatedControls);

        // ===== PROGRESS SPINNER - SIZES SECTION =====
        Label spinnerSizeLabel = new Label("ProgressSpinner - Sizes");
        spinnerSizeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox spinnerSizeSection = new HBox(30);
        spinnerSizeSection.setAlignment(Pos.CENTER);

        VBox smallSpinner = ProgressDemo.createLabeledSpinner("Small", 0.7, ProgressSize.SMALL, ProgressColor.PRIMARY, false);
        VBox mediumSpinner = ProgressDemo.createLabeledSpinner("Medium", 0.7, ProgressSize.MEDIUM, ProgressColor.PRIMARY, false);
        VBox largeSpinner = ProgressDemo.createLabeledSpinner("Large", 0.7, ProgressSize.LARGE, ProgressColor.PRIMARY, false);

        spinnerSizeSection.getChildren().addAll(smallSpinner, mediumSpinner, largeSpinner);

        // ===== PROGRESS SPINNER - COLORS SECTION =====
        Label spinnerColorLabel = new Label("ProgressSpinner - Colors");
        spinnerColorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox spinnerColorSection = new HBox(30);
        spinnerColorSection.setAlignment(Pos.CENTER);

        VBox primarySpinner = ProgressDemo.createLabeledSpinner("Primary", 0.7, ProgressSize.MEDIUM, ProgressColor.PRIMARY, false);
        VBox secondarySpinner = ProgressDemo.createLabeledSpinner("Secondary", 0.7, ProgressSize.MEDIUM, ProgressColor.SECONDARY, false);
        VBox successSpinner = ProgressDemo.createLabeledSpinner("Success", 0.7, ProgressSize.MEDIUM, ProgressColor.SUCCESS, false);
        VBox warningSpinner = ProgressDemo.createLabeledSpinner("Warning", 0.7, ProgressSize.MEDIUM, ProgressColor.WARNING, false);
        VBox errorSpinner = ProgressDemo.createLabeledSpinner("Error", 0.7, ProgressSize.MEDIUM, ProgressColor.ERROR, false);

        spinnerColorSection.getChildren().addAll(primarySpinner, secondarySpinner, successSpinner, warningSpinner, errorSpinner);

        // ===== PROGRESS SPINNER - INDETERMINATE SECTION =====
        Label spinnerIndeterminateLabel = new Label("Indeterminate Spinners");
        spinnerIndeterminateLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox spinnerIndeterminateSection = new HBox(30);
        spinnerIndeterminateSection.setAlignment(Pos.CENTER);

        VBox loadingSpinner1 = ProgressDemo.createLabeledSpinner("Loading...", 0, ProgressSize.SMALL, ProgressColor.PRIMARY, true);
        VBox loadingSpinner2 = ProgressDemo.createLabeledSpinner("Loading...", 0, ProgressSize.MEDIUM, ProgressColor.SUCCESS, true);
        VBox loadingSpinner3 = ProgressDemo.createLabeledSpinner("Loading...", 0, ProgressSize.LARGE, ProgressColor.SECONDARY, true);

        spinnerIndeterminateSection.getChildren().addAll(loadingSpinner1, loadingSpinner2, loadingSpinner3);

        // ===== REAL-WORLD EXAMPLES SECTION =====
        Label examplesLabel = new Label("Real-World Examples");
        examplesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox examplesSection = new HBox(20);
        examplesSection.setAlignment(Pos.CENTER);

        // File upload card
        CFXCard uploadCard = new CFXCard();
        uploadCard.setPrefWidth(250);

        VBox uploadContent = new VBox(10);
        uploadContent.setAlignment(Pos.CENTER_LEFT);

        Label uploadTitle = new Label("Uploading file...");
        uploadTitle.setStyle("-fx-font-weight: 600;");

        Label uploadFilename = new Label("document.pdf (2.5 MB)");
        uploadFilename.setStyle("-fx-font-size: 12px; -fx-text-fill: rgba(0, 0, 0, 0.6);");

        CFXProgressBar uploadProgress = new CFXProgressBar(0.45);
        uploadProgress.setPrefWidth(230);
        uploadProgress.setColor(ProgressColor.PRIMARY);

        Label uploadPercent = new Label("45% complete");
        uploadPercent.setStyle("-fx-font-size: 12px;");

        uploadContent.getChildren().addAll(uploadTitle, uploadFilename, uploadProgress, uploadPercent);
        uploadCard.setContent(uploadContent);

        // Loading data card
        CFXCard loadingCard = new CFXCard();
        loadingCard.setPrefWidth(250);

        VBox loadingContent = new VBox(15);
        loadingContent.setAlignment(Pos.CENTER);

        CFXProgressSpinner loadingSpinner = new CFXProgressSpinner(0, true);
        loadingSpinner.setSize(ProgressSize.LARGE);
        loadingSpinner.setColor(ProgressColor.PRIMARY);

        Label loadingText = new Label("Loading data...");
        loadingText.setStyle("-fx-font-weight: 500;");

        loadingContent.getChildren().addAll(loadingSpinner, loadingText);
        loadingCard.setContent(loadingContent);

        // Task completion card
        CFXCard taskCard = new CFXCard();
        taskCard.setPrefWidth(250);

        VBox taskContent = new VBox(12);

        Label taskTitle = new Label("Project Progress");
        taskTitle.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");

        HBox task1 = new HBox(10);
        task1.setAlignment(Pos.CENTER_LEFT);
        Label task1Label = new Label("Design");
        task1Label.setPrefWidth(80);
        CFXProgressBar task1Progress = new CFXProgressBar(1.0);
        task1Progress.setColor(ProgressColor.SUCCESS);
        task1Progress.setPrefWidth(140);
        task1.getChildren().addAll(task1Label, task1Progress);

        HBox task2 = new HBox(10);
        task2.setAlignment(Pos.CENTER_LEFT);
        Label task2Label = new Label("Development");
        task2Label.setPrefWidth(80);
        CFXProgressBar task2Progress = new CFXProgressBar(0.65);
        task2Progress.setColor(ProgressColor.WARNING);
        task2Progress.setPrefWidth(140);
        task2.getChildren().addAll(task2Label, task2Progress);

        HBox task3 = new HBox(10);
        task3.setAlignment(Pos.CENTER_LEFT);
        Label task3Label = new Label("Testing");
        task3Label.setPrefWidth(80);
        CFXProgressBar task3Progress = new CFXProgressBar(0.2);
        task3Progress.setPrefWidth(140);
        task3.getChildren().addAll(task3Label, task3Progress);

        taskContent.getChildren().addAll(taskTitle, task1, task2, task3);
        taskCard.setContent(taskContent);

        examplesSection.getChildren().addAll(uploadCard, loadingCard, taskCard);

        // Add all sections to root
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                sizeLabel,
                sizeSection,
                new Separator(),
                colorLabel,
                colorSection,
                new Separator(),
                indeterminateLabel,
                indeterminateSection,
                new Separator(),
                animatedLabel,
                animatedSection,
                new Separator(),
                spinnerSizeLabel,
                spinnerSizeSection,
                new Separator(),
                spinnerColorLabel,
                spinnerColorSection,
                new Separator(),
                spinnerIndeterminateLabel,
                spinnerIndeterminateSection,
                new Separator(),
                examplesLabel,
                examplesSection
        );
    }

    private void showTextFieldPage() {
        pageTitle.setText("Text Fields");
        contentArea.getChildren().clear();

        // Outlined TextFields Section
        Label outlinedLabel = new Label("Outlined TextFields");
        outlinedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox outlinedFields = new VBox(20);
        outlinedFields.setAlignment(Pos.CENTER_LEFT);
        outlinedFields.setMaxWidth(400);

        CFXTextField outlined1 = new CFXTextField();
        outlined1.setVariant(TextFieldVariant.OUTLINED);
        outlined1.setLabel("Email");
        outlined1.setHelperText("Enter your email address");

        CFXTextField outlined2 = new CFXTextField();
        outlined2.setVariant(TextFieldVariant.OUTLINED);
        outlined2.setLabel("Password");
        outlined2.setHelperText("Must be at least 8 characters");

        CFXTextField outlined3 = new CFXTextField();
        outlined3.setVariant(TextFieldVariant.OUTLINED);
        outlined3.setLabel("Username");
        outlined3.setRequired(true);

        outlinedFields.getChildren().addAll(outlined1, outlined2, outlined3);

        // Filled TextFields Section
        Label filledLabel = new Label("Filled TextFields");
        filledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox filledFields = new VBox(20);
        filledFields.setAlignment(Pos.CENTER_LEFT);
        filledFields.setMaxWidth(400);

        CFXTextField filled1 = new CFXTextField();
        filled1.setVariant(TextFieldVariant.FILLED);
        filled1.setLabel("First Name");

        CFXTextField filled2 = new CFXTextField();
        filled2.setVariant(TextFieldVariant.FILLED);
        filled2.setLabel("Last Name");

        CFXTextField filled3 = new CFXTextField();
        filled3.setVariant(TextFieldVariant.FILLED);
        filled3.setLabel("Phone Number");
        filled3.setHelperText("+1 (555) 123-4567");

        filledFields.getChildren().addAll(filled1, filled2, filled3);

        // Error State Section
        Label errorLabel = new Label("Error State");
        errorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox errorFields = new VBox(20);
        errorFields.setAlignment(Pos.CENTER_LEFT);
        errorFields.setMaxWidth(400);

        CFXTextField errorField1 = new CFXTextField();
        errorField1.setVariant(TextFieldVariant.OUTLINED);
        errorField1.setLabel("Email");
        errorField1.setText("invalid-email");
        errorField1.setErrorText("Please enter a valid email address");

        CFXTextField errorField2 = new CFXTextField();
        errorField2.setVariant(TextFieldVariant.FILLED);
        errorField2.setLabel("Password");
        errorField2.setText("123");
        errorField2.setErrorText("Password must be at least 8 characters");

        errorFields.getChildren().addAll(errorField1, errorField2);

        // Disabled State Section
        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox disabledFields = new VBox(20);
        disabledFields.setAlignment(Pos.CENTER_LEFT);
        disabledFields.setMaxWidth(400);

        CFXTextField disabled1 = new CFXTextField();
        disabled1.setVariant(TextFieldVariant.OUTLINED);
        disabled1.setLabel("Disabled Field");
        disabled1.setText("This field is disabled");
        disabled1.setDisable(true);

        CFXTextField disabled2 = new CFXTextField();
        disabled2.setVariant(TextFieldVariant.FILLED);
        disabled2.setLabel("Disabled Filled");
        disabled2.setText("Cannot edit this");
        disabled2.setDisable(true);

        disabledFields.getChildren().addAll(disabled1, disabled2);

        // Form Example Section
        Label formLabel = new Label("Complete Form Example");
        formLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox formFields = new VBox(15);
        formFields.setAlignment(Pos.CENTER_LEFT);
        formFields.setMaxWidth(400);

        CFXTextField formName = new CFXTextField();
        formName.setVariant(TextFieldVariant.OUTLINED);
        formName.setLabel("Full Name");
        formName.setRequired(true);

        CFXTextField formEmail = new CFXTextField();
        formEmail.setVariant(TextFieldVariant.OUTLINED);
        formEmail.setLabel("Email Address");
        formEmail.setRequired(true);
        formEmail.setHelperText("We'll never share your email");

        CFXTextField formPhone = new CFXTextField();
        formPhone.setVariant(TextFieldVariant.OUTLINED);
        formPhone.setLabel("Phone Number");
        formPhone.setHelperText("Optional");

        CFXTextField formMessage = new CFXTextField();
        formMessage.setVariant(TextFieldVariant.OUTLINED);
        formMessage.setLabel("Message");
        formMessage.setPrefHeight(80);

        HBox formButtons = new HBox(10);
        formButtons.setAlignment(Pos.CENTER_RIGHT);

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);

        CFXButton submitBtn = new CFXButton("Submit");
        submitBtn.setOnAction(e -> {
            // Simple validation example
            boolean hasError = false;

            if (formName.getText().isEmpty()) {
                formName.setErrorText("Name is required");
                hasError = true;
            } else {
                formName.setErrorText("");
            }

            if (formEmail.getText().isEmpty()) {
                formEmail.setErrorText("Email is required");
                hasError = true;
            } else if (!formEmail.getText().contains("@")) {
                formEmail.setErrorText("Please enter a valid email");
                hasError = true;
            } else {
                formEmail.setErrorText("");
            }

            if (!hasError) {
                System.out.println("Form submitted!");
                System.out.println("Name: " + formName.getText());
                System.out.println("Email: " + formEmail.getText());
                System.out.println("Phone: " + formPhone.getText());
                System.out.println("Message: " + formMessage.getText());
            }
        });

        formButtons.getChildren().addAll(cancelBtn, submitBtn);
        formFields.getChildren().addAll(formName, formEmail, formPhone, formMessage, formButtons);

        // Add all sections to root
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                outlinedLabel,
                outlinedFields,
                new Separator(),
                filledLabel,
                filledFields,
                new Separator(),
                errorLabel,
                errorFields,
                new Separator(),
                disabledLabel,
                disabledFields,
                new Separator(),
                formLabel,
                formFields
        );

    }

    private void showSlidersPage() {
        pageTitle.setText("Sliders");
        contentArea.getChildren().clear();

        // ===== BASIC SLIDERS SECTION =====
        Label basicLabel = new Label("Basic Sliders");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox basicSection = new VBox(20);
        basicSection.setAlignment(Pos.CENTER_LEFT);
        basicSection.setMaxWidth(500);

        // Simple slider
        VBox simple = SliderDemo.createLabeledSlider("Simple Slider", 0, 100, 50);

        // Slider with value display
        VBox withValue = new VBox(8);
        Label valueLabel = new Label("Volume: 50");
        valueLabel.setStyle("-fx-font-weight: 500;");

        CFXSlider volumeSlider = new CFXSlider(0, 100, 50);
        volumeSlider.setPrefWidth(500);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            valueLabel.setText("Volume: " + Math.round(newVal.doubleValue()));
        });

        withValue.getChildren().addAll(valueLabel, volumeSlider);

        basicSection.getChildren().addAll(simple, withValue);

        // ===== COLOR VARIANTS SECTION =====
        Label colorLabel = new Label("Color Variants");
        colorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox colorSection = new VBox(20);
        colorSection.setAlignment(Pos.CENTER_LEFT);
        colorSection.setMaxWidth(500);

        VBox primary = SliderDemo.createColoredSlider("Primary", ProgressColor.PRIMARY, 60);
        VBox secondary = SliderDemo.createColoredSlider("Secondary", ProgressColor.SECONDARY, 60);
        VBox success = SliderDemo.createColoredSlider("Success", ProgressColor.SUCCESS, 60);
        VBox warning = SliderDemo.createColoredSlider("Warning", ProgressColor.WARNING, 60);
        VBox error = SliderDemo.createColoredSlider("Error", ProgressColor.ERROR, 60);

        colorSection.getChildren().addAll(primary, secondary, success, warning, error);

        // ===== DISCRETE SLIDERS SECTION =====
        Label discreteLabel = new Label("Discrete Sliders (Step Values)");
        discreteLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox discreteSection = new VBox(20);
        discreteSection.setAlignment(Pos.CENTER_LEFT);
        discreteSection.setMaxWidth(500);

        // Discrete slider with steps
        VBox discrete1 = new VBox(8);
        Label discrete1Label = new Label("Rating: 5");
        discrete1Label.setStyle("-fx-font-weight: 500;");

        CFXSlider ratingSlider = new CFXSlider(0, 10, 5);
        ratingSlider.setPrefWidth(500);
        ratingSlider.setMajorTickUnit(1);
        ratingSlider.setMinorTickCount(0);
        ratingSlider.setSnapToTicks(true);
        ratingSlider.setShowTickMarks(true);
        ratingSlider.setDiscrete(true);
        ratingSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            discrete1Label.setText("Rating: " + Math.round(newVal.doubleValue()));
        });

        discrete1.getChildren().addAll(discrete1Label, ratingSlider);

        // Discrete slider with labels
        VBox discrete2 = new VBox(8);
        Label discrete2Label = new Label("Temperature: 20°C");
        discrete2Label.setStyle("-fx-font-weight: 500;");

        CFXSlider tempSlider = new CFXSlider(15, 30, 20);
        tempSlider.setPrefWidth(500);
        tempSlider.setMajorTickUnit(5);
        tempSlider.setMinorTickCount(4);
        tempSlider.setShowTickMarks(true);
        tempSlider.setShowTickLabels(true);
        tempSlider.setDiscrete(true);
        tempSlider.setColor(ProgressColor.WARNING);
        tempSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            discrete2Label.setText("Temperature: " + Math.round(newVal.doubleValue()) + "°C");
        });

        discrete2.getChildren().addAll(discrete2Label, tempSlider);

        discreteSection.getChildren().addAll(discrete1, discrete2);

        // ===== VERTICAL SLIDERS SECTION =====
        Label verticalLabel = new Label("Vertical Sliders");
        verticalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox verticalSection = new HBox(40);
        verticalSection.setAlignment(Pos.CENTER);
        verticalSection.setPrefHeight(250);

        VBox vertical1 = SliderDemo.createVerticalSlider("Bass", ProgressColor.PRIMARY, 50);
        VBox vertical2 = SliderDemo.createVerticalSlider("Mid", ProgressColor.SUCCESS, 70);
        VBox vertical3 = SliderDemo.createVerticalSlider("Treble", ProgressColor.WARNING, 60);
        VBox vertical4 = SliderDemo.createVerticalSlider("Volume", ProgressColor.ERROR, 80);

        verticalSection.getChildren().addAll(vertical1, vertical2, vertical3, vertical4);

        // ===== DISABLED STATE SECTION =====
        CFXCard disabledCard = new CFXCard();
        disabledCard.setPrefWidth(600);

        VBox disabledSection = new VBox(20);
        disabledSection.setAlignment(Pos.CENTER_LEFT);
        disabledSection.setMaxWidth(500);

        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox disabledContent = new VBox(20);
        disabledContent.setAlignment(Pos.CENTER_LEFT);
        disabledContent.setMaxWidth(500);

        CFXSlider disabledSlider = new CFXSlider(0, 100, 40);
        disabledSlider.setPrefWidth(500);
        disabledSlider.setDisable(true);

        disabledContent.getChildren().addAll(disabledLabel, disabledSlider);
        disabledCard.setContent(disabledContent);
        disabledSection.getChildren().add(disabledCard);

        // ===== REAL-WORLD EXAMPLES SECTION =====
        Label examplesLabel = new Label("Real-World Examples");
        examplesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox examplesSection = new VBox(20);
        examplesSection.setAlignment(Pos.CENTER_LEFT);
        examplesSection.setMaxWidth(600);

        // Volume control example
        CFXCard volumeCard = new CFXCard();
        volumeCard.setPrefWidth(600);

        VBox volumeContent = new VBox(15);

        Label volumeTitle = new Label("Audio Settings");
        volumeTitle.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");

        Label masterLabel = new Label("Master Volume: 75%");
        masterLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider masterSlider = new CFXSlider(0, 100, 75);
        masterSlider.setPrefWidth(560);
        masterSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            masterLabel.setText("Master Volume: " + Math.round(newVal.doubleValue()) + "%");
        });

        Label bassLabel = new Label("Bass: 50%");
        bassLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider bassSlider = new CFXSlider(0, 100, 50);
        bassSlider.setPrefWidth(560);
        bassSlider.setColor(ProgressColor.SUCCESS);
        bassSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            bassLabel.setText("Bass: " + Math.round(newVal.doubleValue()) + "%");
        });

        Label trebleLabel = new Label("Treble: 50%");
        trebleLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider trebleSlider = new CFXSlider(0, 100, 50);
        trebleSlider.setPrefWidth(560);
        trebleSlider.setColor(ProgressColor.WARNING);
        trebleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            trebleLabel.setText("Treble: " + Math.round(newVal.doubleValue()) + "%");
        });

        volumeContent.getChildren().addAll(
                volumeTitle,
                masterLabel, masterSlider,
                bassLabel, bassSlider,
                trebleLabel, trebleSlider
        );
        volumeCard.setContent(volumeContent);

        // Brightness control example
        CFXCard brightnessCard = new CFXCard();
        brightnessCard.setPrefWidth(600);

        VBox brightnessContent = new VBox(15);

        Label brightnessTitle = new Label("Display Settings");
        brightnessTitle.setStyle("-fx-font-weight: 600; -fx-font-size: 16px;");

        Label brightnessLabel = new Label("Brightness: 70%");
        brightnessLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider brightnessSlider = new CFXSlider(0, 100, 70);
        brightnessSlider.setPrefWidth(560);
        brightnessSlider.setColor(ProgressColor.WARNING);
        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            brightnessLabel.setText("Brightness: " + Math.round(newVal.doubleValue()) + "%");
        });

        Label contrastLabel = new Label("Contrast: 50");
        contrastLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");
        CFXSlider contrastSlider = new CFXSlider(0, 100, 50);
        contrastSlider.setPrefWidth(560);
        contrastSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            contrastLabel.setText("Contrast: " + Math.round(newVal.doubleValue()));
        });

        brightnessContent.getChildren().addAll(
                brightnessTitle,
                brightnessLabel, brightnessSlider,
                contrastLabel, contrastSlider
        );
        brightnessCard.setContent(brightnessContent);

        examplesSection.getChildren().addAll(volumeCard, brightnessCard);

        // Add all sections to root
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                basicLabel,
                basicSection,
                new Separator(),
                colorLabel,
                colorSection,
                new Separator(),
                discreteLabel,
                discreteSection,
                new Separator(),
                verticalLabel,
                verticalSection,
                new Separator(),
                disabledLabel,
                disabledSection,
                new Separator(),
                examplesLabel,
                examplesSection
        );
    }

    private void showTabsPage() {
        pageTitle.setText("Tabs");
        contentArea.getChildren().clear();

        CFXTabs tabs = new CFXTabs();
        tabs.setMaxWidth(800);
        tabs.setPrefHeight(400);

        tabs.addTab("Home", createTabContent("Home", "Welcome to the home tab!"));
        tabs.addTab("Profile", createTabContent("Profile", "View and edit your profile."));
        tabs.addTab("Settings", createTabContent("Settings", "Manage your settings."));
        tabs.addTab("Messages", createTabContent("Messages", "Check your messages."));

        contentArea.getChildren().add(tabs);
    }

    private VBox createTabContent(String title, String description) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: #666;");

        TextArea area = new TextArea();
        area.setPromptText("Content area...");
        area.setPrefHeight(200);

        content.getChildren().addAll(titleLabel, descLabel, area);
        return content;
    }

    private void showMenusPage() {
        pageTitle.setText("Menus & MenuBar");
        contentArea.getChildren().clear();

        Label info = new Label("Menu functionality is best demonstrated in the navigation drawer!\nCheck the top-left menu button (☰) to see it in action.");
        info.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        info.setWrapText(true);
        info.setMaxWidth(600);

        CFXButton openMenuBtn = new CFXButton("Open Navigation Menu");
        openMenuBtn.setOnAction(e -> navigationDrawer.open());

        contentArea.getChildren().addAll(info, openMenuBtn);
    }

    private void showTablesPage() {
        pageTitle.setText("Tables");
        contentArea.getChildren().clear();

        Label info = new Label("Table component with sortable columns, pagination, and filtering.\nFull demo available in TableDemo.java");
        info.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        info.setWrapText(true);
        info.setMaxWidth(600);

        contentArea.getChildren().add(info);
    }

    private void showGridPage() {
        pageTitle.setText("Grid Layout");
        contentArea.getChildren().clear();

        CFXGrid grid = new CFXGrid(12);
        grid.setHgap(16);
        grid.setVgap(16);
        grid.setMaxWidth(800);

        for (int i = 1; i <= 12; i++) {
            StackPane item = new StackPane();
            item.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 8px;");
            item.setPrefHeight(60);
            Label label = new Label("Col " + i);
            label.setTextFill(Color.WHITE);
            label.setFont(Font.font("System", FontWeight.BOLD, 14));
            item.getChildren().add(label);
            grid.addItem(item, 1);
        }

        contentArea.getChildren().add(grid);
    }

    private void showCarouselPage() {
        pageTitle.setText("Carousel");
        contentArea.getChildren().clear();

        Label info = new Label("Image carousel with smooth transitions.\nFull demo available in CarouselDemo.java");
        info.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        info.setWrapText(true);
        info.setMaxWidth(600);

        contentArea.getChildren().add(info);
    }

    private void showFileUploadPage() {
        pageTitle.setText("File Upload");
        contentArea.getChildren().clear();

        CFXFileUploader uploader = new CFXFileUploader();
        uploader.setMaxWidth(700);
        uploader.setButtonText("Choose Files");
        uploader.setMultiple(true);

        contentArea.getChildren().add(uploader);
    }

    private void showAvatarsPage() {
        pageTitle.setText("Avatars");
        contentArea.getChildren().clear();

        Label sizesLabel = new Label("Different Sizes");
        sizesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox avatars = new HBox(20);
        avatars.setAlignment(Pos.CENTER);

        CFXAvatar av1 = new CFXAvatar("JD");
        av1.setAvatarSize(AvatarSize.SMALL);

        CFXAvatar av2 = new CFXAvatar("AS");
        av2.setAvatarSize(AvatarSize.MEDIUM);

        CFXAvatar av3 = new CFXAvatar("MK");
        av3.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar av4 = new CFXAvatar("LT");
        av4.setAvatarSize(AvatarSize.EXTRA_LARGE);

        avatars.getChildren().addAll(av1, av2, av3, av4);

        Label colorsLabel = new Label("Different Colors");
        colorsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox colorAvatars = new HBox(20);
        colorAvatars.setAlignment(Pos.CENTER);

        CFXAvatar c1 = new CFXAvatar("BL");
        c1.setColor(Color.web("#2196F3"));
        c1.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar c2 = new CFXAvatar("GR");
        c2.setColor(Color.web("#4CAF50"));
        c2.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar c3 = new CFXAvatar("OR");
        c3.setColor(Color.web("#FF9800"));
        c3.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar c4 = new CFXAvatar("RD");
        c4.setColor(Color.web("#F44336"));
        c4.setAvatarSize(AvatarSize.LARGE);

        colorAvatars.getChildren().addAll(c1, c2, c3, c4);

        contentArea.getChildren().addAll(
                sizesLabel, avatars, new Separator(),
                colorsLabel, colorAvatars
        );
    }

    private void showDrawerPage() {
        pageTitle.setText("Drawer");
        contentArea.getChildren().clear();

        Label info = new Label("You're already using a drawer! Click the menu button (☰) at the top to see it in action.");
        info.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        info.setWrapText(true);
        info.setMaxWidth(600);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        CFXButton openBtn = new CFXButton("Open Navigation Drawer");
        openBtn.setOnAction(e -> navigationDrawer.open());

        CFXButton closeBtn = new CFXButton("Close Navigation Drawer");
        closeBtn.setColor(ButtonColor.SECONDARY);
        closeBtn.setOnAction(e -> navigationDrawer.close());

        buttons.getChildren().addAll(openBtn, closeBtn);

        contentArea.getChildren().addAll(info, buttons);
    }

    private void showAllComponents() {
        pageTitle.setText("All Components Overview");
        contentArea.getChildren().clear();

        Label info = new Label("Navigate through the menu to explore each component in detail!");
        info.setStyle("-fx-font-size: 18px; -fx-text-fill: #666; -fx-font-weight: 600;");

        VBox componentsList = new VBox(10);
        componentsList.setMaxWidth(600);

        String[] components = {
                "✓ Buttons - Multiple variants and colors",
                "✓ Cards - Elevated, outlined, filled",
                "✓ Checkboxes - Material Design checkboxes",
                "✓ Radio Buttons - Grouped selections",
                "✓ Progress Bars - Linear progress indicators",
                "✓ Progress Spinners - Circular loading",
                "✓ Text Fields - Input components",
                "✓ Sliders - Value selection",
                "✓ Tabs - Tabbed navigation",
                "✓ Menus - Navigation and context menus",
                "✓ Tables - Data tables with sorting",
                "✓ Grid - Responsive layout system",
                "✓ Carousel - Image/content carousel",
                "✓ File Upload - Drag and drop upload",
                "✓ Avatars - User profile images",
                "✓ Drawer - Sliding navigation panel"
        };

        for (String component : components) {
            Label item = new Label(component);
            item.setStyle("-fx-font-size: 14px; -fx-padding: 8px;");
            componentsList.getChildren().add(item);
        }

        contentArea.getChildren().addAll(info, new Separator(), componentsList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}