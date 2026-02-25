package com.compassfx.skins;

import com.compassfx.controls.CFXBreadcrumb;
import com.compassfx.models.BreadcrumbItem;
import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CFXBreadcrumbSkin extends SkinBase<CFXBreadcrumb> {

    private final CFXBreadcrumb breadcrumb;
    private final HBox container;

    public CFXBreadcrumbSkin(CFXBreadcrumb breadcrumb) {
        super(breadcrumb);
        this.breadcrumb = breadcrumb;

        container = new HBox(8);
        container.setAlignment(Pos.CENTER_LEFT);
        container.getStyleClass().add("breadcrumb-container");

        getChildren().add(container);

        // Listen for item changes
        breadcrumb.getItems().addListener((ListChangeListener.Change<? extends BreadcrumbItem> c) -> {
            updateBreadcrumb();
        });

        // Listen for property changes
        breadcrumb.separatorProperty().addListener((obs, old, newVal) -> updateBreadcrumb());
        breadcrumb.showHomeIconProperty().addListener((obs, old, newVal) -> updateBreadcrumb());
        breadcrumb.maxDisplayedItemsProperty().addListener((obs, old, newVal) -> updateBreadcrumb());

        updateBreadcrumb();
    }

    private void updateBreadcrumb() {
        container.getChildren().clear();

        if (breadcrumb.getItems().isEmpty()) {
            return;
        }

        int itemCount = breadcrumb.getItems().size();
        int maxDisplayed = breadcrumb.getMaxDisplayedItems();
        boolean shouldCollapse = maxDisplayed > 0 && itemCount > maxDisplayed;

        // Add home icon if enabled
        if (breadcrumb.isShowHomeIcon() && itemCount > 0) {
            Label homeIcon = new Label("🏠");
            homeIcon.getStyleClass().add("breadcrumb-home-icon");
            homeIcon.setStyle("-fx-font-size: 16px;");

            if (breadcrumb.isInteractive()) {
                homeIcon.setCursor(Cursor.HAND);
                homeIcon.setOnMouseClicked(e -> breadcrumb.navigateToIndex(0));

                homeIcon.setOnMouseEntered(e -> homeIcon.getStyleClass().add("hover"));
                homeIcon.setOnMouseExited(e -> homeIcon.getStyleClass().remove("hover"));
            }

            container.getChildren().add(homeIcon);

            if (itemCount > 1) {
                container.getChildren().add(createSeparator());
            }
        }

        if (shouldCollapse) {
            // Show first item
            addBreadcrumbItem(breadcrumb.getItems().get(0), 0, false);
            container.getChildren().add(createSeparator());

            // Add ellipsis
            Label ellipsis = new Label("...");
            ellipsis.getStyleClass().add("breadcrumb-ellipsis");
            ellipsis.setFont(Font.font("System", FontWeight.BOLD, 14));
            container.getChildren().add(ellipsis);
            container.getChildren().add(createSeparator());

            // Show last (maxDisplayed - 1) items
            int startIndex = itemCount - (maxDisplayed - 1);
            for (int i = startIndex; i < itemCount; i++) {
                boolean isLast = i == itemCount - 1;
                addBreadcrumbItem(breadcrumb.getItems().get(i), i, isLast);

                if (!isLast) {
                    container.getChildren().add(createSeparator());
                }
            }
        } else {
            // Show all items
            for (int i = 0; i < itemCount; i++) {
                boolean isLast = i == itemCount - 1;

                // Skip first item if home icon is shown
                if (breadcrumb.isShowHomeIcon() && i == 0) {
                    continue;
                }

                addBreadcrumbItem(breadcrumb.getItems().get(i), i, isLast);

                if (!isLast) {
                    container.getChildren().add(createSeparator());
                }
            }
        }
    }

    private void addBreadcrumbItem(BreadcrumbItem item, int index, boolean isLast) {
        HBox itemBox = new HBox(6);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        itemBox.getStyleClass().add("breadcrumb-item");

        // Add icon if present
        if (item.getIcon() != null) {
            StackPane iconContainer = new StackPane(item.getIcon());
            iconContainer.getStyleClass().add("breadcrumb-icon");
            itemBox.getChildren().add(iconContainer);
        }

        // Add text
        Label textLabel = new Label();
        textLabel.textProperty().bind(item.textProperty());
        textLabel.getStyleClass().add("breadcrumb-text");

        if (isLast) {
            textLabel.getStyleClass().add("current");
            textLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        } else {
            textLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        }

        itemBox.getChildren().add(textLabel);

        // Make interactive if not last and not disabled
        if (!isLast && breadcrumb.isInteractive() && !item.isDisabled()) {
            itemBox.setCursor(Cursor.HAND);
            itemBox.getStyleClass().add("clickable");

            itemBox.setOnMouseClicked(e -> breadcrumb.navigateToIndex(index));

            itemBox.setOnMouseEntered(e -> {
                itemBox.getStyleClass().add("hover");
                textLabel.getStyleClass().add("hover");
            });

            itemBox.setOnMouseExited(e -> {
                itemBox.getStyleClass().remove("hover");
                textLabel.getStyleClass().remove("hover");
            });
        }

        // Handle disabled state
        if (item.isDisabled()) {
            itemBox.setOpacity(0.5);
            itemBox.setDisable(true);
        }

        // Add with animation if enabled
        if (breadcrumb.isAnimated()) {
            itemBox.setOpacity(0);
            FadeTransition fade = new FadeTransition(Duration.millis(200), itemBox);
            fade.setFromValue(0);
            fade.setToValue(item.isDisabled() ? 0.5 : 1.0);
            fade.play();
        }

        container.getChildren().add(itemBox);
    }

    private Label createSeparator() {
        Label separator = new Label(breadcrumb.getSeparator().getSymbol());
        separator.getStyleClass().add("breadcrumb-separator");
        separator.setFont(Font.font("System", FontWeight.NORMAL, 14));
        return separator;
    }
}
