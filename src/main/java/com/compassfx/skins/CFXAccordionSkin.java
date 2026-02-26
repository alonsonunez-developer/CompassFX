package com.compassfx.skins;

import com.compassfx.controls.CFXAccordion;
import com.compassfx.controls.CFXAccordionItem;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class CFXAccordionSkin extends SkinBase<CFXAccordion> {

    private final VBox container;
    private final CFXAccordion accordion;

    public CFXAccordionSkin(CFXAccordion accordion) {
        super(accordion);
        this.accordion = accordion;
        this.container = new VBox();

        container.getStyleClass().add("accordion-container");

        getChildren().add(container);

        // Listen for item changes
        accordion.getItems().addListener((ListChangeListener.Change<? extends CFXAccordionItem> c) -> {
            rebuildAccordion();
        });

        rebuildAccordion();
    }

    private void rebuildAccordion() {
        container.getChildren().clear();

        for (int i = 0; i < accordion.getItems().size(); i++) {
            CFXAccordionItem item = accordion.getItems().get(i);
            VBox itemContainer = createAccordionItemNode(item, i);
            container.getChildren().add(itemContainer);
        }
    }

    private VBox createAccordionItemNode(CFXAccordionItem item, int index) {
        VBox itemBox = new VBox();
        itemBox.getStyleClass().add("accordion-item");

        // Header
        HBox header = new HBox(10);
        header.getStyleClass().add("accordion-header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(12, 16, 12, 16));

        // Icon (if present)
        if (item.getIcon() != null) {
            HBox iconContainer = new HBox(item.getIcon());
            iconContainer.getStyleClass().add("accordion-icon-container");
            header.getChildren().add(iconContainer);
        }

        // Title
        Label titleLabel = new Label();
        titleLabel.textProperty().bind(item.titleProperty());
        titleLabel.getStyleClass().add("accordion-title");
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        header.getChildren().add(titleLabel);

        // Expand/Collapse indicator
        SVGPath expandIcon = createExpandIcon();
        expandIcon.getStyleClass().add("accordion-expand-icon");
        StackPane expandIconContainer = new StackPane(expandIcon);
        expandIconContainer.getStyleClass().add("accordion-expand-icon-container");
        header.getChildren().add(expandIconContainer);

        // Content container
        VBox contentContainer = new VBox();
        contentContainer.getStyleClass().add("accordion-content");
        contentContainer.setManaged(false);
        contentContainer.setVisible(false);

        if (item.getContent() != null) {
            VBox contentWrapper = new VBox(item.getContent());
            contentWrapper.setPadding(new Insets(16));
            contentContainer.getChildren().add(contentWrapper);
        }

        // Bind content to item's content property
        item.contentProperty().addListener((obs, oldContent, newContent) -> {
            contentContainer.getChildren().clear();
            if (newContent != null) {
                VBox contentWrapper = new VBox(newContent);
                contentWrapper.setPadding(new Insets(16));
                contentContainer.getChildren().add(contentWrapper);
            }
        });

        // Handle expansion
        item.expandedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                expandItem(contentContainer, expandIcon);
            } else {
                collapseItem(contentContainer, expandIcon);
            }
        });

        // Handle disabled state
        item.disabledProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                itemBox.getStyleClass().add("disabled");
                header.setDisable(true);
            } else {
                itemBox.getStyleClass().remove("disabled");
                header.setDisable(false);
            }
        });

        // Click handler
        header.setOnMouseClicked(event -> {
            if (!item.isDisabled()) {
                item.toggle();
            }
        });

        // Add hover effect
        header.setOnMouseEntered(event -> {
            if (!item.isDisabled()) {
                header.getStyleClass().add("hover");
            }
        });

        header.setOnMouseExited(event -> {
            header.getStyleClass().remove("hover");
        });

        itemBox.getChildren().addAll(header, contentContainer);

        // Initialize expanded state
        if (item.isExpanded()) {
            contentContainer.setVisible(true);
            contentContainer.setManaged(true);
            expandIcon.setRotate(180);
        }

        // Initialize disabled state
        if (item.isDisabled()) {
            itemBox.getStyleClass().add("disabled");
            header.setDisable(true);
        }

        return itemBox;
    }

    private SVGPath createExpandIcon() {
        SVGPath path = new SVGPath();
        path.setContent("M7 10l5 5 5-5z"); // Simple chevron down
        return path;
    }

    private void expandItem(VBox contentContainer, SVGPath expandIcon) {
        contentContainer.setVisible(true);
        contentContainer.setManaged(true);

        if (accordion.isAnimated()) {
            // Rotate icon animation
            RotateTransition iconRotate = new RotateTransition(Duration.millis(200), expandIcon);
            iconRotate.setToAngle(180);
            iconRotate.play();

            // Content fade in
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), contentContainer);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Content expand animation
            contentContainer.setMaxHeight(0);
            Timeline expandTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(contentContainer.maxHeightProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(contentContainer.maxHeightProperty(), Region.USE_COMPUTED_SIZE))
            );

            fadeIn.play();
            expandTimeline.play();
        } else {
            expandIcon.setRotate(180);
            contentContainer.setOpacity(1.0);
            contentContainer.setMaxHeight(Region.USE_COMPUTED_SIZE);
        }
    }

    private void collapseItem(VBox contentContainer, SVGPath expandIcon) {
        if (accordion.isAnimated()) {
            // Rotate icon animation
            RotateTransition iconRotate = new RotateTransition(Duration.millis(200), expandIcon);
            iconRotate.setToAngle(0);
            iconRotate.play();

            // Content fade out
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), contentContainer);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Content collapse animation
            Timeline collapseTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(contentContainer.maxHeightProperty(), contentContainer.getHeight())),
                    new KeyFrame(Duration.millis(200), new KeyValue(contentContainer.maxHeightProperty(), 0))
            );

            collapseTimeline.setOnFinished(event -> {
                contentContainer.setVisible(false);
                contentContainer.setManaged(false);
            });

            fadeOut.play();
            collapseTimeline.play();
        } else {
            expandIcon.setRotate(0);
            contentContainer.setVisible(false);
            contentContainer.setManaged(false);
        }
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        container.resizeRelocate(contentX, contentY, contentWidth, contentHeight);
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.minWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.minHeight(width) + topInset + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.prefWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.prefHeight(width) + topInset + bottomInset;
    }
}
