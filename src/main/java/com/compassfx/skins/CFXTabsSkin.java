// ============================================
// CFXTabsSkin.java - Tabs Skin
// src/main/java/com/compassfx/skins/CFXTabsSkin.java
// ============================================
package com.compassfx.skins;

import com.compassfx.controls.CFXTabs;
import com.compassfx.enums.TabPosition;
import com.compassfx.enums.TabVariant;
import com.compassfx.models.Tab;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CFXTabsSkin extends SkinBase<CFXTabs> {

    private final CFXTabs tabs;
    private final BorderPane container;
    private final HBox tabBar;
    private final StackPane contentArea;
    private final StackPane activeIndicator;

    public CFXTabsSkin(CFXTabs tabs) {
        super(tabs);
        this.tabs = tabs;

        container = new BorderPane();
        container.getStyleClass().add("tabs-container");

        // Tab Bar
        tabBar = new HBox();
        tabBar.getStyleClass().add("tab-bar");
        tabBar.setAlignment(Pos.CENTER_LEFT);

        // Active indicator (animated underline/highlight)
        activeIndicator = new StackPane();
        activeIndicator.getStyleClass().add("tab-indicator");
        activeIndicator.setManaged(false);

        // Content Area
        contentArea = new StackPane();
        contentArea.getStyleClass().add("tab-content");

        // Position tab bar based on position property
        positionTabBar();

        getChildren().add(container);

        // Listen for changes
        tabs.getTabs().addListener((ListChangeListener.Change<? extends Tab> c) -> {
            updateTabs();
        });

        tabs.selectedIndexProperty().addListener((obs, old, newVal) -> {
            updateSelection();
        });

        tabs.tabPositionProperty().addListener((obs, old, newVal) -> {
            positionTabBar();
        });

        updateTabs();
    }

    private void positionTabBar() {
        container.getChildren().clear();

        TabPosition position = tabs.getTabPosition();

        switch (position) {
            case TOP:
                VBox topBox = new VBox(tabBar);
                topBox.getChildren().add(activeIndicator);
                container.setTop(topBox);
                container.setCenter(contentArea);
                tabBar.setAlignment(Pos.CENTER_LEFT);
                break;
            case BOTTOM:
                VBox bottomBox = new VBox(activeIndicator, tabBar);
                container.setBottom(bottomBox);
                container.setCenter(contentArea);
                tabBar.setAlignment(Pos.CENTER_LEFT);
                break;
            case LEFT:
                VBox leftBox = new VBox(5);
                leftBox.getChildren().addAll(tabs.getTabs().stream()
                        .map(this::createVerticalTab)
                        .toArray(Node[]::new));
                container.setLeft(leftBox);
                container.setCenter(contentArea);
                break;
            case RIGHT:
                VBox rightBox = new VBox(5);
                rightBox.getChildren().addAll(tabs.getTabs().stream()
                        .map(this::createVerticalTab)
                        .toArray(Node[]::new));
                container.setRight(rightBox);
                container.setCenter(contentArea);
                break;
        }
    }

    private void updateTabs() {
        tabBar.getChildren().clear();

        for (int i = 0; i < tabs.getTabs().size(); i++) {
            Tab tab = tabs.getTabs().get(i);
            HBox tabButton = createTabButton(tab, i);
            tabBar.getChildren().add(tabButton);
        }

        updateSelection();
    }

    private HBox createTabButton(Tab tab, int index) {
        HBox tabButton = new HBox(8);
        tabButton.getStyleClass().add("tab-button");
        tabButton.setAlignment(Pos.CENTER);
        tabButton.setPadding(new Insets(12, 20, 12, 20));

        // Icon
        if (tab.getGraphic() != null) {
            StackPane iconContainer = new StackPane(tab.getGraphic());
            iconContainer.getStyleClass().add("tab-icon");
            tabButton.getChildren().add(iconContainer);
        }

        // Text
        Label textLabel = new Label();
        textLabel.textProperty().bind(tab.textProperty());
        textLabel.getStyleClass().add("tab-text");
        textLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        tabButton.getChildren().add(textLabel);

        // Close button
        if (tab.isClosable()) {
            Button closeBtn = new Button("×");
            closeBtn.getStyleClass().add("tab-close-button");
            closeBtn.setOnAction(e -> {
                tabs.removeTab(tab);
                e.consume();
            });
            tabButton.getChildren().add(closeBtn);
        }

        tabButton.setCursor(Cursor.HAND);

        // Click handler
        tabButton.setOnMouseClicked(e -> {
            if (!tab.isDisabled()) {
                tabs.selectTab(index);
            }
        });

        // Hover effects
        tabButton.setOnMouseEntered(e -> {
            if (!tab.isDisabled() && index != tabs.getSelectedIndex()) {
                tabButton.getStyleClass().add("hover");
            }
        });

        tabButton.setOnMouseExited(e -> {
            tabButton.getStyleClass().remove("hover");
        });

        if (tab.isDisabled()) {
            tabButton.setDisable(true);
            tabButton.setOpacity(0.5);
        }

        return tabButton;
    }

    private Node createVerticalTab(Tab tab) {
        // Similar to horizontal but vertical layout
        VBox tabButton = new VBox(5);
        tabButton.getStyleClass().add("tab-button");
        tabButton.setAlignment(Pos.CENTER);
        tabButton.setPadding(new Insets(15, 20, 15, 20));

        if (tab.getGraphic() != null) {
            tabButton.getChildren().add(tab.getGraphic());
        }

        Label textLabel = new Label();
        textLabel.textProperty().bind(tab.textProperty());
        textLabel.getStyleClass().add("tab-text");
        tabButton.getChildren().add(textLabel);

        tabButton.setCursor(Cursor.HAND);
        tabButton.setOnMouseClicked(e -> tabs.selectTab(tab));

        return tabButton;
    }

    private void updateSelection() {
        int selectedIndex = tabs.getSelectedIndex();

        if (selectedIndex < 0 || selectedIndex >= tabs.getTabs().size()) {
            contentArea.getChildren().clear();
            return;
        }

        // Update tab button styles
        for (int i = 0; i < tabBar.getChildren().size(); i++) {
            Node tabButton = tabBar.getChildren().get(i);
            tabButton.getStyleClass().remove("selected");

            if (i == selectedIndex) {
                tabButton.getStyleClass().add("selected");
            }
        }

        // Update active indicator position
        if (!tabBar.getChildren().isEmpty() && selectedIndex < tabBar.getChildren().size()) {
            Node selectedTabButton = tabBar.getChildren().get(selectedIndex);

            tabBar.layout();

            double x = selectedTabButton.getLayoutX();
            double width = selectedTabButton.getLayoutBounds().getWidth();

            if (tabs.getVariant() == TabVariant.UNDERLINE || tabs.getVariant() == TabVariant.STANDARD) {
                activeIndicator.setPrefWidth(width);
                activeIndicator.setMinHeight(3);
                activeIndicator.setPrefHeight(3);

                if (tabs.isAnimated()) {
                    TranslateTransition transition = new TranslateTransition(Duration.millis(250), activeIndicator);
                    transition.setToX(x);
                    transition.play();
                } else {
                    activeIndicator.setTranslateX(x);
                }
            }
        }

        // Update content
        Tab selectedTab = tabs.getTabs().get(selectedIndex);
        if (selectedTab.getContent() != null) {
            if (tabs.isAnimated()) {
                // Fade out old content
                if (!contentArea.getChildren().isEmpty()) {
                    Node oldContent = contentArea.getChildren().get(0);
                    FadeTransition fadeOut = new FadeTransition(Duration.millis(150), oldContent);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setOnFinished(e -> {
                        contentArea.getChildren().clear();

                        // Fade in new content
                        Node newContent = selectedTab.getContent();
                        newContent.setOpacity(0);
                        contentArea.getChildren().add(newContent);

                        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), newContent);
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    });
                    fadeOut.play();
                } else {
                    // First tab, just fade in
                    Node newContent = selectedTab.getContent();
                    newContent.setOpacity(0);
                    contentArea.getChildren().add(newContent);

                    FadeTransition fadeIn = new FadeTransition(Duration.millis(150), newContent);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                }
            } else {
                contentArea.getChildren().clear();
                contentArea.getChildren().add(selectedTab.getContent());
            }
        }
    }
}