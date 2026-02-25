// ============================================
// CompassFXPlayground.java - Main Demo Application
// src/main/java/com/compassfx/demo/CompassFXPlayground.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.*;
import com.compassfx.models.*;
import com.compassfx.models.Tab;
import com.compassfx.models.TableColumn;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

import static com.compassfx.demo.AvatarDemo.createLabeledAvatar;
import static com.compassfx.demo.AvatarDemo.createUserItem;
import static com.compassfx.demo.DrawerDemo.*;

/**
 * CompassFX Playground - Single Page Application
 * Interactive showcase of all CompassFX components
 */
public class CompassFXPlayground extends Application {

    private VBox contentArea;
    private ScrollPane contentScroll;
    private CFXDrawer navigationDrawer;
    private CFXDrawer settingsDrawer;
    private CFXDrawer notificationsDrawer;
    private CFXDrawer actionsDrawer;
    private CFXDrawer largeDrawer;
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

        settingsDrawer = createSettingsDrawer();

        notificationsDrawer = createNotificationsDrawer();

        // Show welcome page
        showWelcomePage();

        // Root with drawer
        StackPane rootWithDrawer = new StackPane(root, navigationDrawer, settingsDrawer, notificationsDrawer);

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

    private CFXDrawer createSettingsDrawer() {
        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.RIGHT);
        drawer.setSize(DrawerSize.SMALL);

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

        drawer.setContent(form);

        return drawer;
    }

    private CFXDrawer createNotificationsDrawer() {
        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.TOP);
        drawer.setSize(DrawerSize.SMALL);

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

        drawer.setContent(notifications);

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

        // ====================================
        // Standard Tabs
        // ====================================
        Label standardLabel = new Label("Standard Tabs");
        standardLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs standardTabs = new CFXTabs();
        standardTabs.setMaxWidth(800);
        standardTabs.setPrefHeight(300);

        standardTabs.addTab("Home", TabsDemo.createContent("Home Tab", "Welcome to the home page!"));
        standardTabs.addTab("Profile", TabsDemo.createContent("Profile Tab", "View and edit your profile here."));
        standardTabs.addTab("Settings", TabsDemo.createContent("Settings Tab", "Configure your settings."));
        standardTabs.addTab("Messages", TabsDemo.createContent("Messages Tab", "Check your messages."));

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
                TabsDemo.createContent("Home", "Home content with icon"));

        Tab profileTab = Tab.withIcon("Profile",
                new Circle(5, Color.web("#4CAF50")),
                TabsDemo.createContent("Profile", "Profile content with icon"));

        Tab settingsTab = Tab.withIcon("Settings",
                new Circle(5, Color.web("#FF9800")),
                TabsDemo.createContent("Settings", "Settings content with icon"));

        iconTabs.getTabs().addAll(homeTab, profileTab, settingsTab);

        // ====================================
        // Closable Tabs
        // ====================================
        Label closableLabel = new Label("Closable Tabs");
        closableLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs closableTabs = new CFXTabs();
        closableTabs.setMaxWidth(800);
        closableTabs.setPrefHeight(300);

        Tab tab1 = Tab.closable("Document 1", TabsDemo.createContent("Document 1", "This tab can be closed"));
        Tab tab2 = Tab.closable("Document 2", TabsDemo.createContent("Document 2", "Click the × to close"));
        Tab tab3 = Tab.closable("Document 3", TabsDemo.createContent("Document 3", "Closable tabs are useful"));

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

        filledTabs.addTab("Tab 1", TabsDemo.createContent("Filled 1", "Filled variant with background"));
        filledTabs.addTab("Tab 2", TabsDemo.createContent("Filled 2", "Selected tab has colored background"));
        filledTabs.addTab("Tab 3", TabsDemo.createContent("Filled 3", "Clean and modern look"));

        // Pills variant
        Label pillsLabel = new Label("Pills Variant:");
        pillsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXTabs pillsTabs = new CFXTabs();
        pillsTabs.setVariant(TabVariant.PILLS);
        pillsTabs.setMaxWidth(800);
        pillsTabs.setPrefHeight(250);

        pillsTabs.addTab("Overview", TabsDemo.createContent("Overview", "Rounded pill-style tabs"));
        pillsTabs.addTab("Analytics", TabsDemo.createContent("Analytics", "Modern rounded design"));
        pillsTabs.addTab("Reports", TabsDemo.createContent("Reports", "Great for dashboards"));

        // Underline variant
        Label underlineLabel = new Label("Underline Variant:");
        underlineLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXTabs underlineTabs = new CFXTabs();
        underlineTabs.setVariant(TabVariant.UNDERLINE);
        underlineTabs.setMaxWidth(800);
        underlineTabs.setPrefHeight(250);

        underlineTabs.addTab("Files", TabsDemo.createContent("Files", "Minimal underline design"));
        underlineTabs.addTab("Folders", TabsDemo.createContent("Folders", "Clean and simple"));
        underlineTabs.addTab("Shared", TabsDemo.createContent("Shared", "Perfect for content apps"));

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

        bottomTabs.addTab("Tab 1", TabsDemo.createContent("Bottom 1", "Tabs at the bottom"));
        bottomTabs.addTab("Tab 2", TabsDemo.createContent("Bottom 2", "Alternative layout"));
        bottomTabs.addTab("Tab 3", TabsDemo.createContent("Bottom 3", "Good for mobile-style UIs"));

        // ====================================
        // Disabled Tab
        // ====================================
        Label disabledLabel = new Label("With Disabled Tab");
        disabledLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs disabledTabs = new CFXTabs();
        disabledTabs.setMaxWidth(800);
        disabledTabs.setPrefHeight(250);

        Tab enabledTab = new Tab("Enabled", TabsDemo.createContent("Enabled", "This tab is active"));
        Tab disTab = new Tab("Disabled", TabsDemo.createContent("Disabled", "This tab is disabled"));
        disTab.setDisabled(true);
        Tab anotherTab = new Tab("Another", TabsDemo.createContent("Another", "This one works too"));

        disabledTabs.getTabs().addAll(enabledTab, disTab, anotherTab);

        // ====================================
        // Dynamic Tabs Example
        // ====================================
        Label dynamicLabel = new Label("Dynamic Tabs");
        dynamicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTabs dynamicTabs = new CFXTabs();
        dynamicTabs.setMaxWidth(800);
        dynamicTabs.setPrefHeight(300);

        dynamicTabs.addTab("Tab 1", TabsDemo.createContent("Dynamic 1", "Initial tab"));
        dynamicTabs.addTab("Tab 2", TabsDemo.createContent("Dynamic 2", "Another tab"));

        VBox dynamicContainer = new VBox(10);
        dynamicContainer.setAlignment(Pos.CENTER);

        javafx.scene.control.Button addTabBtn = new javafx.scene.control.Button("Add New Tab");
        addTabBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
        addTabBtn.setOnAction(e -> {
            int count = dynamicTabs.getTabs().size() + 1;
            Tab newTab = Tab.closable("Tab " + count,
                    TabsDemo.createContent("Tab " + count, "Dynamically added tab"));
            dynamicTabs.addTab(newTab);
            dynamicTabs.selectTab(newTab);
        });

        dynamicContainer.getChildren().addAll(dynamicTabs, addTabBtn);

        // ====================================
        // Add all to root
        // ====================================
        contentArea.getChildren().addAll(
                pageTitle,
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

        // ====================================
        // Basic Table with Sorting
        // ====================================
        Label basicLabel = new Label("Basic Table with Sorting");
        basicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTable<TableDemo.User> basicTable = TableDemo.createBasicTable();
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

        CFXTable<TableDemo.User> searchTable = TableDemo.createBasicTable();
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

        CFXTable<TableDemo.User> paginatedTable = TableDemo.createBasicTable();
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
        CFXTable<TableDemo.User> compactTable = TableDemo.createSmallTable();
        compactTable.setDensity(TableDensity.COMPACT);
        compactTable.setMaxWidth(900);

        Label comfortableLabel = new Label("Comfortable:");
        comfortableLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXTable<TableDemo.User> comfortableTable = TableDemo.createSmallTable();
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
        CFXTable<TableDemo.User> stripedTable = TableDemo.createSmallTable();
        stripedTable.setStriped(true);
        stripedTable.setMaxWidth(900);

        Label borderedLabel = new Label("Without Borders:");
        borderedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXTable<TableDemo.User> noBorderTable = TableDemo.createSmallTable();
        noBorderTable.setBordered(false);
        noBorderTable.setMaxWidth(900);

        variantsBox.getChildren().addAll(stripedLabel, stripedTable, borderedLabel, noBorderTable);

        // ====================================
        // Custom Cell Rendering
        // ====================================
        Label customLabel = new Label("Custom Cell Rendering");
        customLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXTable<TableDemo.User> customTable = new CFXTable<>();
        customTable.setMaxWidth(900);

        TableColumn<TableDemo.User> nameCol = new TableColumn<>("Name", "name", 200);
        TableColumn<TableDemo.User> emailCol = new TableColumn<>("Email", "email", 250);
        TableColumn<TableDemo.User> roleCol = new TableColumn<>("Role", "role", 150);

        // Custom status column with colored indicators
        TableColumn<TableDemo.User> statusCol = new TableColumn<>("Status", "status", 150);
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
        TableColumn<TableDemo.User> actionsCol = new TableColumn<>("Actions", "name", 150);
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
        customTable.setData(TableDemo.getSampleData());

        // ====================================
        // Interactive Example
        // ====================================
        Label interactiveLabel = new Label("Interactive Selection");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox interactiveBox = new VBox(15);
        interactiveBox.setAlignment(Pos.TOP_CENTER);

        CFXTable<TableDemo.User> interactiveTable = TableDemo.createBasicTable();
        interactiveTable.setSelectionMode(TableSelectionMode.MULTIPLE);
        interactiveTable.setMaxWidth(900);

        Label selectionLabel = new Label("Selected: None");
        selectionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2196F3; -fx-font-weight: 600;");

        interactiveTable.setOnSelectionChange(event -> {
            List<TableDemo.User> selected = event.getSelectedItems();
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
        contentArea.getChildren().addAll(
                pageTitle,
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

        // ====================================
        // Basic Text Carousel with Slide Transition
        // ====================================
        Label slideLabel = new Label("Text Carousel - Slide Transition");
        slideLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel slideCarousel = new CFXCarousel();
        slideCarousel.setTransition(CarouselTransition.SLIDE);
        slideCarousel.addTextSlide(
                "Welcome to CompassFX",
                "A modern JavaFX UI library with Material Design components"
        );
        slideCarousel.addTextSlide(
                "Beautiful Components",
                "Pre-built, customizable UI components for your applications"
        );
        slideCarousel.addTextSlide(
                "Easy to Use",
                "Simple API with comprehensive documentation and examples"
        );
        slideCarousel.addTextSlide(
                "Get Started Today",
                "Download now and start building amazing interfaces"
        );

        // ====================================
        // Fade Transition with Auto-Play
        // ====================================
        Label fadeLabel = new Label("Auto-Play Carousel - Fade Transition");
        fadeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel fadeCarousel = new CFXCarousel();
        fadeCarousel.setTransition(CarouselTransition.FADE);
        fadeCarousel.setAutoPlay(true);
        fadeCarousel.setAutoPlayDelay(Duration.seconds(2));

        fadeCarousel.addTextSlide("Slide 1", "This carousel auto-plays with fade transitions");
        fadeCarousel.addTextSlide("Slide 2", "Watch it automatically change every 2 seconds");
        fadeCarousel.addTextSlide("Slide 3", "Click the navigation buttons to control it manually");

        // ====================================
        // Scale Transition
        // ====================================
        Label scaleLabel = new Label("Scale Transition");
        scaleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel scaleCarousel = new CFXCarousel();
        scaleCarousel.setTransition(CarouselTransition.SCALE);

        scaleCarousel.addTextSlide("Feature 1", "Zoom in and out transitions");
        scaleCarousel.addTextSlide("Feature 2", "Smooth scaling animations");
        scaleCarousel.addTextSlide("Feature 3", "Modern visual effects");

        // ====================================
        // Different Indicator Styles
        // ====================================
        Label indicatorsLabel = new Label("Different Indicator Styles");
        indicatorsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox indicatorsBox = new VBox(20);
        indicatorsBox.setAlignment(Pos.CENTER);

        // Dots (default)
        Label dotsLabel = new Label("Dots Indicators:");
        dotsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCarousel dotsCarousel = new CFXCarousel();
        dotsCarousel.setIndicatorStyle(CarouselIndicatorStyle.DOTS);
        dotsCarousel.setItemHeight(250);
        dotsCarousel.addTextSlide("Slide 1", "Using dot indicators");
        dotsCarousel.addTextSlide("Slide 2", "Most common style");
        dotsCarousel.addTextSlide("Slide 3", "Clean and minimal");

        // Lines
        Label linesLabel = new Label("Line Indicators:");
        linesLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCarousel linesCarousel = new CFXCarousel();
        linesCarousel.setIndicatorStyle(CarouselIndicatorStyle.LINES);
        linesCarousel.setItemHeight(250);
        linesCarousel.addTextSlide("Slide 1", "Using line indicators");
        linesCarousel.addTextSlide("Slide 2", "Modern and sleek");
        linesCarousel.addTextSlide("Slide 3", "Great for minimal designs");

        // Numbers
        Label numbersLabel = new Label("Number Indicators:");
        numbersLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCarousel numbersCarousel = new CFXCarousel();
        numbersCarousel.setIndicatorStyle(CarouselIndicatorStyle.NUMBERS);
        numbersCarousel.setItemHeight(250);
        numbersCarousel.addTextSlide("Slide 1", "Using number indicators");
        numbersCarousel.addTextSlide("Slide 2", "Shows position clearly");
        numbersCarousel.addTextSlide("Slide 3", "Good for step-by-step content");

        indicatorsBox.getChildren().addAll(
                dotsLabel, dotsCarousel,
                linesLabel, linesCarousel,
                numbersLabel, numbersCarousel
        );

        // ====================================
        // Custom Content Carousel
        // ====================================
        Label customLabel = new Label("Carousel with Custom Content");
        customLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel customCarousel = new CFXCarousel();
        customCarousel.setItemHeight(300);

        // Create custom content slides
        for (int i = 1; i <= 4; i++) {
            VBox customContent = new VBox(20);
            customContent.setAlignment(Pos.CENTER);
            customContent.setPadding(new Insets(40));

            Rectangle colorBox = new Rectangle(200, 150);
            colorBox.setArcWidth(12);
            colorBox.setArcHeight(12);

            switch (i) {
                case 1: colorBox.setFill(Color.web("#2196F3")); break;
                case 2: colorBox.setFill(Color.web("#4CAF50")); break;
                case 3: colorBox.setFill(Color.web("#FF9800")); break;
                case 4: colorBox.setFill(Color.web("#F44336")); break;
            }

            Label label = new Label("Custom Slide " + i);
            label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            customContent.getChildren().addAll(colorBox, label);

            CarouselItem item = CarouselItem.createWithContent(customContent);
            customCarousel.addItem(item);
        }

        // ====================================
        // Minimal Carousel (No indicators, smaller)
        // ====================================
        Label minimalLabel = new Label("Minimal Carousel");
        minimalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel minimalCarousel = new CFXCarousel();
        minimalCarousel.setShowIndicators(false);
        minimalCarousel.setItemHeight(200);
        minimalCarousel.setItemWidth(400);

        minimalCarousel.addTextSlide("Quote 1", "\"Design is not just what it looks like. Design is how it works.\"");
        minimalCarousel.addTextSlide("Quote 2", "\"Simplicity is the ultimate sophistication.\"");
        minimalCarousel.addTextSlide("Quote 3", "\"Good design is obvious. Great design is transparent.\"");

        // ====================================
        // Interactive Controls Example
        // ====================================
        Label controlsLabel = new Label("Interactive Controls");
        controlsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXCarousel controlledCarousel = new CFXCarousel();
        controlledCarousel.addTextSlide("Step 1", "Use the controls below to navigate");
        controlledCarousel.addTextSlide("Step 2", "You can control the carousel programmatically");
        controlledCarousel.addTextSlide("Step 3", "Perfect for tutorials and onboarding");
        controlledCarousel.addTextSlide("Step 4", "Or let users navigate freely");

        VBox controlsBox = new VBox(15);
        controlsBox.setAlignment(Pos.CENTER);

        javafx.scene.layout.HBox buttonBox = new javafx.scene.layout.HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        CFXButton firstBtn = new CFXButton("First");
        firstBtn.setOnAction(e -> controlledCarousel.goToSlide(0));

        CFXButton prevBtn = new CFXButton("Previous");
        prevBtn.setOnAction(e -> controlledCarousel.previous());

        CFXButton nextBtn = new CFXButton("Next");
        nextBtn.setOnAction(e -> controlledCarousel.next());

        CFXButton lastBtn = new CFXButton("Last");
        lastBtn.setOnAction(e -> controlledCarousel.goToSlide(controlledCarousel.getItems().size() - 1));

        buttonBox.getChildren().addAll(firstBtn, prevBtn, nextBtn, lastBtn);

        Label currentLabel = new Label("Current Slide: 1");
        currentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2196F3;");

        controlledCarousel.setOnSlideChange(event -> {
            currentLabel.setText("Current Slide: " + (event.getIndex() + 1) + " - " + event.getItem().getTitle());
        });

        controlsBox.getChildren().addAll(controlledCarousel, buttonBox, currentLabel);

        // ====================================
        // Add all to root
        // ====================================
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                slideLabel,
                slideCarousel,
                new Separator(),
                fadeLabel,
                fadeCarousel,
                new Separator(),
                scaleLabel,
                scaleCarousel,
                new Separator(),
                indicatorsLabel,
                indicatorsBox,
                new Separator(),
                customLabel,
                customCarousel,
                new Separator(),
                minimalLabel,
                minimalCarousel,
                new Separator(),
                controlsLabel,
                controlsBox
        );
    }

    private void showFileUploadPage() {
        pageTitle.setText("File Upload");
        contentArea.getChildren().clear();

        // ====================================
        // Image Uploader
        // ====================================
        Label imageLabel = new Label("Image Uploader");
        imageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader imageUploader = new CFXFileUploader();
        imageUploader.setAcceptedFileType(FileType.IMAGE);
        imageUploader.setButtonText("Choose Images");
        imageUploader.setDragText("or drag and drop images here");
        imageUploader.setMultiple(true);
        imageUploader.setMaxWidth(700);
        imageUploader.setMaxFiles(5);

        imageUploader.setOnFilesSelected(event -> {
            System.out.println("Image selected: " + event.getFile().getFileName());
        });

        imageUploader.setOnUploadComplete(event -> {
            System.out.println("Upload complete: " + event.getFile().getFileName());
        });

        // ====================================
        // Document Uploader
        // ====================================
        Label docLabel = new Label("Document Uploader");
        docLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader docUploader = new CFXFileUploader();
        docUploader.setAcceptedFileType(FileType.DOCUMENT);
        docUploader.setButtonText("Upload Documents");
        docUploader.setButtonColor(ButtonColor.SUCCESS);
        docUploader.setMultiple(true);
        docUploader.setMaxWidth(700);

        // ====================================
        // Single File Uploader
        // ====================================
        Label singleLabel = new Label("Single File Uploader");
        singleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader singleUploader = new CFXFileUploader();
        singleUploader.setAcceptedFileType(FileType.ALL);
        singleUploader.setButtonText("Select File");
        singleUploader.setButtonColor(ButtonColor.SECONDARY);
        singleUploader.setMultiple(false);
        singleUploader.setMaxWidth(700);

        // ====================================
        // No Drag and Drop
        // ====================================
        Label noDragLabel = new Label("Without Drag & Drop");
        noDragLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader noDragUploader = new CFXFileUploader();
        noDragUploader.setDragAndDrop(false);
        noDragUploader.setButtonText("Click to Upload");
        noDragUploader.setButtonColor(ButtonColor.WARNING);
        noDragUploader.setMaxWidth(700);

        // ====================================
        // Compact Uploader (No File List)
        // ====================================
        Label compactLabel = new Label("Compact Uploader (No File List)");
        compactLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader compactUploader = new CFXFileUploader();
        compactUploader.setShowFileList(false);
        compactUploader.setButtonText("Quick Upload");
        compactUploader.setButtonColor(ButtonColor.ERROR);
        compactUploader.setMaxWidth(700);

        Label compactInfo = new Label("Files are uploaded immediately without showing the list");
        compactInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-style: italic;");

        compactUploader.setOnFilesSelected(event -> {
            System.out.println("Quick upload: " + event.getFile().getFileName());
        });

        // ====================================
        // Video Uploader
        // ====================================
        Label videoLabel = new Label("Video Uploader");
        videoLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader videoUploader = new CFXFileUploader();
        videoUploader.setAcceptedFileType(FileType.VIDEO);
        videoUploader.setButtonText("Upload Videos");
        videoUploader.setDragText("Drag videos here (max 50MB each)");
        videoUploader.setMaxFileSize(50 * 1024 * 1024); // 50MB
        videoUploader.setMaxWidth(700);

        // ====================================
        // Add all to root
        // ====================================
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                imageLabel,
                imageUploader,
                new Separator(),
                docLabel,
                docUploader,
                new Separator(),
                singleLabel,
                singleUploader,
                new Separator(),
                noDragLabel,
                noDragUploader,
                new Separator(),
                compactLabel,
                compactUploader,
                compactInfo,
                new Separator(),
                videoLabel,
                videoUploader
        );
    }

    private void showAvatarsPage() {
        pageTitle.setText("Avatars");
        contentArea.getChildren().clear();

        // ====================================
        // Sizes
        // ====================================
        Label sizesLabel = new Label("Avatar Sizes");
        sizesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox sizesBox = new HBox(20);
        sizesBox.setAlignment(Pos.CENTER);

        VBox xsBox = createLabeledAvatar("XS", "AB", AvatarSize.EXTRA_SMALL);
        VBox smallBox = createLabeledAvatar("Small", "CD", AvatarSize.SMALL);
        VBox mediumBox = createLabeledAvatar("Medium", "EF", AvatarSize.MEDIUM);
        VBox largeBox = createLabeledAvatar("Large", "GH", AvatarSize.LARGE);
        VBox xlBox = createLabeledAvatar("XL", "IJ", AvatarSize.EXTRA_LARGE);
        VBox hugeBox = createLabeledAvatar("Huge", "KL", AvatarSize.HUGE);

        sizesBox.getChildren().addAll(xsBox, smallBox, mediumBox, largeBox, xlBox, hugeBox);

        // ====================================
        // Shapes
        // ====================================
        Label shapesLabel = new Label("Avatar Shapes");
        shapesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox shapesBox = new HBox(30);
        shapesBox.setAlignment(Pos.CENTER);

        VBox circleBox = new VBox(10);
        circleBox.setAlignment(Pos.CENTER);
        Label circleLabel = new Label("Circle");
        CFXAvatar circleAvatar = new CFXAvatar("JD");
        circleAvatar.setAvatarShape(AvatarShape.CIRCLE);
        circleAvatar.setAvatarSize(AvatarSize.LARGE);
        circleBox.getChildren().addAll(circleAvatar, circleLabel);

        VBox roundedBox = new VBox(10);
        roundedBox.setAlignment(Pos.CENTER);
        Label roundedLabel = new Label("Rounded");
        CFXAvatar roundedAvatar = new CFXAvatar("AS");
        roundedAvatar.setAvatarShape(AvatarShape.ROUNDED);
        roundedAvatar.setAvatarSize(AvatarSize.LARGE);
        roundedBox.getChildren().addAll(roundedAvatar, roundedLabel);

        VBox squareBox = new VBox(10);
        squareBox.setAlignment(Pos.CENTER);
        Label squareLabel = new Label("Square");
        CFXAvatar squareAvatar = new CFXAvatar("MK");
        squareAvatar.setAvatarShape(AvatarShape.SQUARE);
        squareAvatar.setAvatarSize(AvatarSize.LARGE);
        squareBox.getChildren().addAll(squareAvatar, squareLabel);

        shapesBox.getChildren().addAll(circleBox, roundedBox, squareBox);

        // ====================================
        // Variants
        // ====================================
        Label variantsLabel = new Label("Avatar Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox variantsBox = new HBox(30);
        variantsBox.setAlignment(Pos.CENTER);

        VBox filledBox = new VBox(10);
        filledBox.setAlignment(Pos.CENTER);
        Label filledLabel = new Label("Filled");
        CFXAvatar filledAvatar = new CFXAvatar("FI");
        filledAvatar.setVariant(AvatarVariant.FILLED);
        filledAvatar.setAvatarSize(AvatarSize.LARGE);
        filledBox.getChildren().addAll(filledAvatar, filledLabel);

        VBox outlinedBox = new VBox(10);
        outlinedBox.setAlignment(Pos.CENTER);
        Label outlinedLabel = new Label("Outlined");
        CFXAvatar outlinedAvatar = new CFXAvatar("OU");
        outlinedAvatar.setVariant(AvatarVariant.OUTLINED);
        outlinedAvatar.setAvatarSize(AvatarSize.LARGE);
        outlinedBox.getChildren().addAll(outlinedAvatar, outlinedLabel);

        VBox lightBox = new VBox(10);
        lightBox.setAlignment(Pos.CENTER);
        Label lightLabel = new Label("Light");
        CFXAvatar lightAvatar = new CFXAvatar("LI");
        lightAvatar.setVariant(AvatarVariant.LIGHT);
        lightAvatar.setAvatarSize(AvatarSize.LARGE);
        lightBox.getChildren().addAll(lightAvatar, lightLabel);

        variantsBox.getChildren().addAll(filledBox, outlinedBox, lightBox);

        // ====================================
        // Colors
        // ====================================
        Label colorsLabel = new Label("Avatar Colors");
        colorsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox colorsBox = new HBox(20);
        colorsBox.setAlignment(Pos.CENTER);

        CFXAvatar blueAvatar = new CFXAvatar("BL");
        blueAvatar.setColor(Color.web("#2196F3"));
        blueAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar greenAvatar = new CFXAvatar("GR");
        greenAvatar.setColor(Color.web("#4CAF50"));
        greenAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar orangeAvatar = new CFXAvatar("OR");
        orangeAvatar.setColor(Color.web("#FF9800"));
        orangeAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar redAvatar = new CFXAvatar("RD");
        redAvatar.setColor(Color.web("#F44336"));
        redAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar purpleAvatar = new CFXAvatar("PU");
        purpleAvatar.setColor(Color.web("#9C27B0"));
        purpleAvatar.setAvatarSize(AvatarSize.LARGE);

        CFXAvatar pinkAvatar = new CFXAvatar("PI");
        pinkAvatar.setColor(Color.web("#E91E63"));
        pinkAvatar.setAvatarSize(AvatarSize.LARGE);

        colorsBox.getChildren().addAll(
                blueAvatar, greenAvatar, orangeAvatar,
                redAvatar, purpleAvatar, pinkAvatar
        );

        // ====================================
        // With Status Indicator
        // ====================================
        Label statusLabel = new Label("With Status Indicator");
        statusLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox statusBox = new HBox(30);
        statusBox.setAlignment(Pos.CENTER);

        VBox onlineBox = new VBox(10);
        onlineBox.setAlignment(Pos.CENTER);
        Label onlineLabel = new Label("Online");
        CFXAvatar onlineAvatar = new CFXAvatar("ON");
        onlineAvatar.setAvatarSize(AvatarSize.LARGE);
        onlineAvatar.setShowStatus(true);
        onlineAvatar.setStatusColor(Color.web("#4CAF50"));
        onlineBox.getChildren().addAll(onlineAvatar, onlineLabel);

        VBox awayBox = new VBox(10);
        awayBox.setAlignment(Pos.CENTER);
        Label awayLabel = new Label("Away");
        CFXAvatar awayAvatar = new CFXAvatar("AW");
        awayAvatar.setAvatarSize(AvatarSize.LARGE);
        awayAvatar.setShowStatus(true);
        awayAvatar.setStatusColor(Color.web("#FF9800"));
        awayBox.getChildren().addAll(awayAvatar, awayLabel);

        VBox busyBox = new VBox(10);
        busyBox.setAlignment(Pos.CENTER);
        Label busyLabel = new Label("Busy");
        CFXAvatar busyAvatar = new CFXAvatar("BS");
        busyAvatar.setAvatarSize(AvatarSize.LARGE);
        busyAvatar.setShowStatus(true);
        busyAvatar.setStatusColor(Color.web("#F44336"));
        busyBox.getChildren().addAll(busyAvatar, busyLabel);

        VBox offlineBox = new VBox(10);
        offlineBox.setAlignment(Pos.CENTER);
        Label offlineLabel = new Label("Offline");
        CFXAvatar offlineAvatar = new CFXAvatar("OF");
        offlineAvatar.setAvatarSize(AvatarSize.LARGE);
        offlineAvatar.setShowStatus(true);
        offlineAvatar.setStatusColor(Color.web("#9E9E9E"));
        offlineBox.getChildren().addAll(offlineAvatar, offlineLabel);

        statusBox.getChildren().addAll(onlineBox, awayBox, busyBox, offlineBox);

        // ====================================
        // With Border
        // ====================================
        Label borderLabel = new Label("With Border");
        borderLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox borderBox = new HBox(30);
        borderBox.setAlignment(Pos.CENTER);

        CFXAvatar border1 = new CFXAvatar("B1");
        border1.setAvatarSize(AvatarSize.LARGE);
        border1.setShowBorder(true);
        border1.setBorderWidth(3);
        border1.setBorderColor(Color.WHITE);

        CFXAvatar border2 = new CFXAvatar("B2");
        border2.setAvatarSize(AvatarSize.LARGE);
        border2.setShowBorder(true);
        border2.setBorderWidth(4);
        border2.setBorderColor(Color.web("#FFD700"));
        border2.setColor(Color.web("#9C27B0"));

        CFXAvatar border3 = new CFXAvatar("B3");
        border3.setAvatarSize(AvatarSize.LARGE);
        border3.setShowBorder(true);
        border3.setBorderWidth(3);
        border3.setBorderColor(Color.web("#4CAF50"));
        border3.setColor(Color.web("#FF9800"));

        borderBox.getChildren().addAll(border1, border2, border3);

        // ====================================
        // User List Example
        // ====================================
        Label userListLabel = new Label("User List Example");
        userListLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox userList = new VBox(15);
        userList.setMaxWidth(400);
        userList.setStyle(
                "-fx-background-color: white; " +
                        "-fx-padding: 20px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 12px;"
        );

        userList.getChildren().addAll(
                createUserItem("John Doe", "john@example.com", Color.web("#2196F3"), true),
                createUserItem("Jane Smith", "jane@example.com", Color.web("#4CAF50"), true),
                createUserItem("Bob Johnson", "bob@example.com", Color.web("#FF9800"), false),
                createUserItem("Alice Williams", "alice@example.com", Color.web("#9C27B0"), true)
        );

        // ====================================
        // Add all to root
        // ====================================
        contentArea.getChildren().addAll(
                pageTitle,
                new Separator(),
                sizesLabel,
                sizesBox,
                new Separator(),
                shapesLabel,
                shapesBox,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                colorsLabel,
                colorsBox,
                new Separator(),
                statusLabel,
                statusBox,
                new Separator(),
                borderLabel,
                borderBox,
                new Separator(),
                userListLabel,
                userList
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

        VBox rightSection = new VBox(10);
        rightSection.setAlignment(Pos.CENTER);

        Label rightLabel = new Label("Right Drawer - Settings Form");
        rightLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openRightBtn = new CFXButton("Open Right Drawer");
        openRightBtn.setColor(ButtonColor.SUCCESS);
        openRightBtn.setOnAction(e -> settingsDrawer.open());

        rightSection.getChildren().addAll(rightLabel, openRightBtn);

        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);

        Label topLabel = new Label("Top Drawer - Notifications");
        topLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openTopBtn = new CFXButton("Open Top Drawer");
        openTopBtn.setColor(ButtonColor.WARNING);
        openTopBtn.setOnAction(e -> notificationsDrawer.open());

        topSection.getChildren().addAll(topLabel, openTopBtn);

        buttons.getChildren().addAll(openBtn, openRightBtn, openTopBtn);

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