// ============================================
// CompassFXPlayground.java - Main Demo Application
// src/main/java/com/compassfx/demo/CompassFXPlayground.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.*;
import com.compassfx.models.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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
    private Scene scene;

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
        navigationDrawer.setScrollable(true);

        settingsDrawer = createSettingsDrawer();

        notificationsDrawer = createNotificationsDrawer();

        actionsDrawer = createActionsDrawer();

        largeDrawer = createLargeDrawer();

        // Show welcome page
        showWelcomePage();

        // Root with drawer
        StackPane rootWithDrawer = new StackPane(root, navigationDrawer,
                settingsDrawer, notificationsDrawer, actionsDrawer, largeDrawer);

        scene = new Scene(rootWithDrawer, 1200, 800);
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
                createMenuItem("Accordions", this::showAccordionsPage),
                createMenuItem("👤 Avatars", this::showAvatarsPage),
//                createMenuItem("Autocomplete", this::showAutocompletePage),
                createMenuItem("Backdrop", this::showBackdropPage),
                createMenuItem("Badges", this::showBadgePage),
                createMenuItem("Breadcrumbs", this::showBreadcrumbsPage),
                createMenuItem("🔘 Buttons", this::showButtonsPage),
                createMenuItem("📦 Cards", this::showCardsPage),
                createMenuItem("Card Flip", this::showCardFlipPage),
                createMenuItem("🎠 Carousel", this::showCarouselPage),
                createMenuItem("Charts", this::showChartsPage),
                createMenuItem("☑️ Checkboxes", this::showCheckboxPage),
                createMenuItem("Chips", this::showChipPage),
                createMenuItem("ComboBoxes", this::showComboBoxesPage),
                createMenuItem("DateTimePickers", this::showDateTimePickersPage),
                createMenuItem("Dialog", this::showDialogPage),
                createMenuItem("Dock", this::showDockPage),
                createMenuItem("🗄️ Drawer", this::showDrawerPage),
                createMenuItem("📤 File Upload", this::showFileUploadPage),
                createMenuItem("FileTree", this::showFileTreePage),
                createMenuItem("📐 Grid", this::showGridPage),
                createMenuItem("Marquee", this::showMarqueePage),
                createMenuItem("🍔 Menus", this::showMenuPage),
                createMenuItem("MultiSelect", this::showMultiSelectPage),
                createMenuItem("📊 Progress", this::showProgressPage),
                createMenuItem("Skeleton", this::showSkeletonPage),
                createMenuItem("🎚️ Sliders", this::showSlidersPage),
                createMenuItem("Snackbar", this::showSnackbarPage),
                createMenuItem("SpeedDial", this::showSpeedDialPage),
                createMenuItem("Stepper", this::showStepperPage),
                createMenuItem("🗂️ Tabs", this::showTabsPage),
                createMenuItem("📋 Tables", this::showTablesPage),
                createMenuItem("TextArea", this::showTextAreaPage),
                createMenuItem("📝 Text Fields", this::showTextFieldPage),
                createMenuItem("Toggle", this::showTogglePage),
                createMenuItem("Tooltip", this::showTooltipPage),
                createMenuItem("Transfer Lists", this::showTransferListPage),
                createMenuItem("🎨 All Components", this::showAllComponents)
        );

        drawer.setContent(menu);

        return drawer;
    }

    private CFXDrawer createSettingsDrawer() {
        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.RIGHT);
        drawer.setSize(DrawerSize.MEDIUM);

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

    private CFXDrawer createActionsDrawer() {
        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.BOTTOM);
        drawer.setSize(DrawerSize.SMALL);

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
        drawer.setContent(actions);

        return drawer;
    }

    private CFXDrawer createLargeDrawer() {
        CFXDrawer drawer = new CFXDrawer();
        drawer.setPosition(DrawerPosition.RIGHT);
        drawer.setSize(DrawerSize.LARGE);

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

        drawer.setContent(content);
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

        VBox stat1 = createStatBox("40+", "Components");
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

    private void showAccordionsPage() {
        pageTitle.setText("Accordions");
        contentArea.getChildren().clear();
        AccordionDemo demo = new AccordionDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showButtonsPage() {
        pageTitle.setText("Buttons");
        contentArea.getChildren().clear();
        ButtonDemo demo = new ButtonDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showBreadcrumbsPage() {
        pageTitle.setText("Breadcrumbs");
        contentArea.getChildren().clear();
        BreadcrumbDemo demo = new BreadcrumbDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showCardsPage() {
        pageTitle.setText("Cards");
        contentArea.getChildren().clear();
        CardDemo demo = new CardDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showChartsPage() {
        pageTitle.setText("Charts");
        contentArea.getChildren().clear();
        ChartsDemo chartsDemo = new ChartsDemo();
        chartsDemo.showDemo(pageTitle, contentArea);
    }

    private void showCheckboxPage() {
        pageTitle.setText("Checkboxes & Radio Buttons");
        contentArea.getChildren().clear();
        CheckboxRadioDemo demo = new CheckboxRadioDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showComboBoxesPage() {
        pageTitle.setText("ComboBoxes");
        contentArea.getChildren().clear();
        ComboBoxDemo demo = new ComboBoxDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showProgressPage() {
        pageTitle.setText("Progress Indicators");
        contentArea.getChildren().clear();
        ProgressDemo demo = new ProgressDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTextFieldPage() {
        pageTitle.setText("Text Fields");
        contentArea.getChildren().clear();
        TextFieldDemo demo = new TextFieldDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showSlidersPage() {
        pageTitle.setText("Sliders");
        contentArea.getChildren().clear();
        SliderDemo demo = new SliderDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTransferListPage() {
        pageTitle.setText("Transfer List");
        contentArea.getChildren().clear();
        TransferListDemo demo = new TransferListDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTabsPage() {
        pageTitle.setText("Tabs");
        contentArea.getChildren().clear();
        TabsDemo demo = new TabsDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTablesPage() {
        pageTitle.setText("Tables");
        contentArea.getChildren().clear();
        TableDemo demo = new TableDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showGridPage() {
        pageTitle.setText("Grid Layout");
        contentArea.getChildren().clear();
        GridDemo demo = new GridDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showCarouselPage() {
        pageTitle.setText("Carousel");
        contentArea.getChildren().clear();
        CarouselDemo demo = new CarouselDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showFileUploadPage() {
        pageTitle.setText("File Upload");
        contentArea.getChildren().clear();
        FileUploaderDemo demo = new FileUploaderDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showAvatarsPage() {
        pageTitle.setText("Avatars");
        contentArea.getChildren().clear();
        AvatarDemo demo = new AvatarDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showDrawerPage() {
        pageTitle.setText("Drawer");
        contentArea.getChildren().clear();

        VBox content = new VBox(30);
        content.setPadding(new Insets(50));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("CompassFX Drawer Demo");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #212121;");

        Label subtitle = new Label("Click buttons to open drawers from different positions");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        VBox leftSection = new VBox(10);
        leftSection.setAlignment(Pos.CENTER);

        Label leftLabel = new Label("Left Drawer - Navigation Menu");
        leftLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openLeftBtn = new CFXButton("Open Left Drawer");
        openLeftBtn.setColor(ButtonColor.PRIMARY);
        openLeftBtn.setOnAction(e -> navigationDrawer.open());

        leftSection.getChildren().addAll(leftLabel, openLeftBtn);

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

        VBox bottomSection = new VBox(10);
        bottomSection.setAlignment(Pos.CENTER);

        Label bottomLabel = new Label("Bottom Drawer - Actions Menu");
        bottomLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openBottomBtn = new CFXButton("Open Bottom Drawer");
        openBottomBtn.setColor(ButtonColor.ERROR);
        openBottomBtn.setOnAction(e -> actionsDrawer.open());

        bottomSection.getChildren().addAll(bottomLabel, openBottomBtn);

        VBox largeSection = new VBox(10);
        largeSection.setAlignment(Pos.CENTER);

        Label largeLabel = new Label("Large Drawer - Document Editor");
        largeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton openLargeBtn = new CFXButton("Open Large Drawer");
        openLargeBtn.setColor(ButtonColor.SECONDARY);
        openLargeBtn.setOnAction(e -> largeDrawer.open());

        largeSection.getChildren().addAll(largeLabel, openLargeBtn);

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

        contentArea.getChildren().addAll(content);
    }

//    private void showAutocompletePage() {
//        pageTitle.setText("Autocomplete");
//        contentArea.getChildren().clear();
//        AutocompleteDemo demo = new AutocompleteDemo();
//        demo.showDemo(pageTitle, contentArea);
//    }

    private void showBackdropPage() {
        pageTitle.setText("Backdrops");
        contentArea.getChildren().clear();
        BackdropDemo demo = new BackdropDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showBadgePage() {
        pageTitle.setText("Badges");
        contentArea.getChildren().clear();
        BadgeDemo demo = new BadgeDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showCardFlipPage() {
        pageTitle.setText("CardFlip");
        contentArea.getChildren().clear();
        CardFlipDemo demo = new CardFlipDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showChipPage() {
        pageTitle.setText("Chip");
        contentArea.getChildren().clear();
        ChipDemo demo = new ChipDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showDateTimePickersPage() {
        pageTitle.setText("DateTimePickers");
        contentArea.getChildren().clear();
        DateTimePickersDemo demo = new DateTimePickersDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showDialogPage() {
        pageTitle.setText("Dialog");
        contentArea.getChildren().clear();
        DialogDemo demo = new DialogDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showDockPage() {
        pageTitle.setText("Dock");
        contentArea.getChildren().clear();
        DockDemo demo = new DockDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showFileTreePage() {
        pageTitle.setText("FileTree");
        contentArea.getChildren().clear();
        FileTreeDemo demo = new FileTreeDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showMarqueePage() {
        pageTitle.setText("Marquee");
        contentArea.getChildren().clear();
        MarqueeDemo demo = new MarqueeDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showMenuPage() {
        pageTitle.setText("Menu");
        contentArea.getChildren().clear();
        MenuDemo demo = new MenuDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showMultiSelectPage() {
        pageTitle.setText("MultiSelect");
        contentArea.getChildren().clear();
        MultiSelectDemo demo = new MultiSelectDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showSkeletonPage() {
        pageTitle.setText("Skeleton");
        contentArea.getChildren().clear();
        SkeletonDemo demo = new SkeletonDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showSnackbarPage() {
        pageTitle.setText("Snackbar");
        contentArea.getChildren().clear();
        SnackbarDemo demo = new SnackbarDemo();
        demo.showDemo(pageTitle, contentArea, scene);
    }

    private void showSpeedDialPage() {
        pageTitle.setText("SpeedDial");
        contentArea.getChildren().clear();
        SpeedDialDemo demo = new SpeedDialDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showStepperPage() {
        pageTitle.setText("Stepper");
        contentArea.getChildren().clear();
        StepperDemo demo = new StepperDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTextAreaPage() {
        pageTitle.setText("TextArea");
        contentArea.getChildren().clear();
        TextAreaDemo demo = new TextAreaDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTogglePage() {
        pageTitle.setText("Toggle");
        contentArea.getChildren().clear();
        ToggleDemo demo = new ToggleDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showTooltipPage() {
        pageTitle.setText("Tooltip");
        contentArea.getChildren().clear();
        TooltipDemo demo = new TooltipDemo();
        demo.showDemo(pageTitle, contentArea);
    }

    private void showAllComponents() {
        pageTitle.setText("All Components Overview");
        contentArea.getChildren().clear();

        Label info = new Label("Navigate through the menu to explore each component in detail!");
        info.setStyle("-fx-font-size: 18px; -fx-text-fill: #666; -fx-font-weight: 600;");

        VBox componentsList = new VBox(10);
        componentsList.setMaxWidth(600);

        String[] components = {
                "✓ Accordions - Expandable content panels",
                "✓ Autocomplete - Smart text suggestions",
                "✓ Avatars - User profile images",
                "✓ Backdrop - Modal overlays",
                "✓ Badges - Notification indicators",
                "✓ Breadcrumbs - Navigation trails",
                "✓ Buttons - Multiple variants and colors",
                "✓ Cards - Elevated, outlined, filled",
                "✓ Card Flip - 3D flip animations",
                "✓ Carousel - Image/content carousel",
                "✓ Charts - Data visualization",
                "✓ Checkboxes - Material Design checkboxes",
                "✓ Chips - Compact information",
                "✓ ComboBoxes - Dropdown selections",
                "✓ DateTimePickers - Date and time selection",
                "✓ Dialogs - Modal dialogs",
                "✓ Dock - macOS-style dock",
                "✓ Drawer - Sliding navigation panel",
                "✓ File Upload - Drag and drop upload",
                "✓ FileTree - Directory tree view",
                "✓ Grid - Responsive layout system",
                "✓ Marquee - Scrolling text",
                "✓ Menus - Navigation and context menus",
                "✓ MultiSelect - Multiple item selection",
                "✓ Progress Bars - Linear progress indicators",
                "✓ Progress Spinners - Circular loading",
                "✓ Radio Buttons - Grouped selections",
                "✓ Skeleton - Loading placeholders",
                "✓ Sliders - Value selection",
                "✓ Snackbar - Toast notifications",
                "✓ SpeedDial - Quick action menu",
                "✓ Stepper - Step-by-step navigation",
                "✓ Tables - Data tables with sorting",
                "✓ Tabs - Tabbed navigation",
                "✓ TextArea - Multi-line text input",
                "✓ Text Fields - Input components",
                "✓ Toggle - Switch controls",
                "✓ Tooltip - Helpful hints",
                "✓ Transfer List - Item transfer between lists"
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