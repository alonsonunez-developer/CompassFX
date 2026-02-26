package com.compassfx.skins;

import com.compassfx.controls.CFXMenuBar;
import com.compassfx.models.MenuItem;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CFXMenuBarSkin extends SkinBase<CFXMenuBar> {

    private final CFXMenuBar menuBar;
    private final HBox container;
    private StackPane currentOpenMenu;

    public CFXMenuBarSkin(CFXMenuBar menuBar) {
        super(menuBar);
        this.menuBar = menuBar;

        container = new HBox();
        container.getStyleClass().add("menubar-container");
        container.setAlignment(Pos.CENTER_LEFT);

        getChildren().add(container);

        // Listen for menu changes
        menuBar.getMenus().addListener((ListChangeListener.Change<? extends MenuItem> c) -> {
            updateMenuBar();
        });

        updateMenuBar();
    }

    private void updateMenuBar() {
        container.getChildren().clear();

        for (MenuItem menu : menuBar.getMenus()) {
            StackPane menuButton = createMenuButton(menu);
            container.getChildren().add(menuButton);
        }
    }

    private StackPane createMenuButton(MenuItem menu) {
        StackPane menuStack = new StackPane();

        HBox menuButton = new HBox(6);
        menuButton.getStyleClass().add("menu-button");
        menuButton.setAlignment(Pos.CENTER);
        menuButton.setPadding(new Insets(10, 16, 10, 16));

        // Icon
        if (menu.getGraphic() != null) {
            menuButton.getChildren().add(menu.getGraphic());
        }

        // Text
        Label menuLabel = new Label();
        menuLabel.textProperty().bind(menu.textProperty());
        menuLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        menuButton.getChildren().add(menuLabel);

        // Dropdown indicator if has sub-items
        if (menu.hasSubItems()) {
            Label arrow = new Label("▼");
            arrow.getStyleClass().add("menu-arrow");
            arrow.setStyle("-fx-font-size: 10px;");
            menuButton.getChildren().add(arrow);
        }

        menuButton.setCursor(Cursor.HAND);

        // Create dropdown
        VBox dropdown = null;
        if (menu.hasSubItems()) {
            dropdown = createDropdown(menu);
            StackPane.setAlignment(dropdown, Pos.TOP_LEFT);
            dropdown.setVisible(false);
            dropdown.setManaged(false);
        }

        menuStack.getChildren().add(menuButton);
        if (dropdown != null) {
            menuStack.getChildren().add(dropdown);
        }

        final VBox finalDropdown = dropdown;

        // Mouse events
        menuButton.setOnMouseEntered(e -> {
            menuButton.getStyleClass().add("hover");
        });

        menuButton.setOnMouseExited(e -> {
            menuButton.getStyleClass().remove("hover");
        });

        menuButton.setOnMouseClicked(e -> {
            if (finalDropdown != null) {
                if (finalDropdown.isVisible()) {
                    hideDropdown(finalDropdown);
                    currentOpenMenu = null;
                } else {
                    // Close any other open menu
                    if (currentOpenMenu != null) {
                        VBox otherDropdown = (VBox) currentOpenMenu.getChildren().get(1);
                        hideDropdown(otherDropdown);
                    }

                    showDropdown(finalDropdown);
                    currentOpenMenu = menuStack;
                }
            } else if (menu.getOnAction() != null) {
                menu.getOnAction().handle(new javafx.event.ActionEvent());
            }
        });

        return menuStack;
    }

    private VBox createDropdown(MenuItem menu) {
        VBox dropdown = new VBox();
        dropdown.getStyleClass().add("menu-dropdown");
        dropdown.setPadding(new Insets(8, 0, 8, 0));
        dropdown.setMinWidth(200);

        for (MenuItem item : menu.getItems()) {
            if (item.isSeparator()) {
                javafx.scene.control.Separator sep = new javafx.scene.control.Separator();
                sep.getStyleClass().add("menu-separator");
                sep.setPadding(new Insets(4, 0, 4, 0));
                dropdown.getChildren().add(sep);
            } else {
                HBox menuItem = createMenuItem(item, dropdown);
                dropdown.getChildren().add(menuItem);
            }
        }

        return dropdown;
    }

    private HBox createMenuItem(MenuItem item, VBox parentDropdown) {
        HBox menuItem = new HBox(10);
        menuItem.getStyleClass().add("menu-item");
        menuItem.setAlignment(Pos.CENTER_LEFT);
        menuItem.setPadding(new Insets(10, 16, 10, 16));

        // Icon
        if (item.getGraphic() != null) {
            StackPane iconContainer = new StackPane(item.getGraphic());
            iconContainer.setMinWidth(20);
            menuItem.getChildren().add(iconContainer);
        }

        // Text
        Label label = new Label();
        label.textProperty().bind(item.textProperty());
        label.setFont(Font.font("System", 14));
        HBox.setHgrow(label, Priority.ALWAYS);
        menuItem.getChildren().add(label);

        // Accelerator
        if (item.getAccelerator() != null) {
            Label accelLabel = new Label(item.getAccelerator().getDisplayText());
            accelLabel.getStyleClass().add("accelerator-label");
            accelLabel.setFont(Font.font("System", 12));
            menuItem.getChildren().add(accelLabel);
        }

        // Submenu arrow
        if (item.hasSubItems()) {
            Label arrow = new Label("›");
            arrow.getStyleClass().add("submenu-arrow");
            menuItem.getChildren().add(arrow);
        }

        menuItem.setCursor(Cursor.HAND);

        // Mouse events
        menuItem.setOnMouseEntered(e -> {
            menuItem.getStyleClass().add("hover");
        });

        menuItem.setOnMouseExited(e -> {
            menuItem.getStyleClass().remove("hover");
        });

        menuItem.setOnMouseClicked(e -> {
            if (item.getOnAction() != null) {
                item.getOnAction().handle(new javafx.event.ActionEvent());
            }

            // Close dropdown
            hideDropdown(parentDropdown);
            currentOpenMenu = null;
        });

        if (item.isDisabled()) {
            menuItem.setDisable(true);
            menuItem.setOpacity(0.5);
        }

        return menuItem;
    }

    private void showDropdown(VBox dropdown) {
        dropdown.setVisible(true);
        dropdown.setManaged(true);
        dropdown.setOpacity(0);
        dropdown.setScaleY(0.8);
        dropdown.setTranslateY(-10);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), dropdown);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), dropdown);
        scaleIn.setFromY(0.8);
        scaleIn.setToY(1.0);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(150), dropdown);
        slideIn.setFromY(-10);
        slideIn.setToY(0);

        ParallelTransition transition = new ParallelTransition(fadeIn, scaleIn, slideIn);
        transition.play();
    }

    private void hideDropdown(VBox dropdown) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), dropdown);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), dropdown);
        scaleOut.setFromY(1.0);
        scaleOut.setToY(0.8);

        ParallelTransition transition = new ParallelTransition(fadeOut, scaleOut);
        transition.setOnFinished(e -> {
            dropdown.setVisible(false);
            dropdown.setManaged(false);
        });
        transition.play();
    }
}