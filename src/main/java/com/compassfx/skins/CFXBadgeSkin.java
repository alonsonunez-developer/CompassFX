package com.compassfx.skins;

import com.compassfx.controls.CFXBadge;
import com.compassfx.enums.BadgePosition;
import com.compassfx.enums.BadgeVariant;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;

public class CFXBadgeSkin extends SkinBase<CFXBadge> {

    private final StackPane container;
    private final StackPane contentContainer;
    private final StackPane badgeContainer;
    private final Label badgeLabel;
    private final CFXBadge badge;

    public CFXBadgeSkin(CFXBadge badge) {
        super(badge);
        this.badge = badge;

        // Container holds both content and badge
        this.container = new StackPane();
        container.getStyleClass().add("badge-container");

        // Content container
        this.contentContainer = new StackPane();
        contentContainer.getStyleClass().add("badge-content-container");

        // Badge container
        this.badgeContainer = new StackPane();
        badgeContainer.getStyleClass().add("badge-indicator");
        badgeContainer.setMouseTransparent(true);
        badgeContainer.setPickOnBounds(false);  // Don't let badge affect layout

        // Badge label
        this.badgeLabel = new Label();
        badgeLabel.getStyleClass().add("badge-label");
        badgeContainer.getChildren().add(badgeLabel);

        container.getChildren().addAll(contentContainer, badgeContainer);
        getChildren().add(container);

        // Bind content
        if (badge.getContent() != null) {
            contentContainer.getChildren().add(badge.getContent());
        }

        badge.contentProperty().addListener((obs, oldContent, newContent) -> {
            contentContainer.getChildren().clear();
            if (newContent != null) {
                contentContainer.getChildren().add(newContent);
            }
        });

        // Update badge text
        updateBadgeText();
        badge.textProperty().addListener((obs, old, newVal) -> updateBadgeText());
        badge.valueProperty().addListener((obs, old, newVal) -> updateBadgeText());
        badge.maxProperty().addListener((obs, old, newVal) -> updateBadgeText());
        badge.variantProperty().addListener((obs, old, newVal) -> updateBadgeText());

        // Update badge visibility
        updateBadgeVisibility();
        badge.badgeVisibleProperty().addListener((obs, old, newVal) -> updateBadgeVisibility());

        // Update badge position when content size changes
        contentContainer.boundsInParentProperty().addListener((obs, old, newVal) -> updateBadgePosition());
        badge.positionProperty().addListener((obs, old, newVal) -> updateBadgePosition());
        badge.offsetXProperty().addListener((obs, old, newVal) -> updateBadgePosition());
        badge.offsetYProperty().addListener((obs, old, newVal) -> updateBadgePosition());

        // Initial position update
        updateBadgePosition();
    }

    private void updateBadgeText() {
        String text = badge.getBadgeText();
        badgeLabel.setText(text);

        // For dot variant, hide the label
        if (badge.getVariant() == BadgeVariant.DOT) {
            badgeLabel.setVisible(false);
            badgeLabel.setManaged(false);
        } else {
            badgeLabel.setVisible(true);
            badgeLabel.setManaged(true);
        }
    }

    private void updateBadgeVisibility() {
        badgeContainer.setVisible(badge.isBadgeVisible());
        badgeContainer.setManaged(badge.isBadgeVisible());
    }

    private void updateBadgePosition() {
        if (contentContainer.getChildren().isEmpty()) {
            return;
        }

        // Get content bounds
        Bounds contentBounds = contentContainer.getBoundsInLocal();
        double contentWidth = contentBounds.getWidth();
        double contentHeight = contentBounds.getHeight();

        // Get badge bounds
        badgeContainer.applyCss();
        badgeContainer.layout();
        Bounds badgeBounds = badgeContainer.getBoundsInLocal();
        double badgeWidth = badgeBounds.getWidth();
        double badgeHeight = badgeBounds.getHeight();

        double translateX = 0;
        double translateY = 0;

        BadgePosition position = badge.getPosition();

        switch (position) {
            case TOP_RIGHT:
                translateX = (contentWidth / 2) - (badgeWidth / 2);
                translateY = -(contentHeight / 2) + (badgeHeight / 2);
                break;
            case TOP_LEFT:
                translateX = -(contentWidth / 2) + (badgeWidth / 2);
                translateY = -(contentHeight / 2) + (badgeHeight / 2);
                break;
            case BOTTOM_RIGHT:
                translateX = (contentWidth / 2) - (badgeWidth / 2);
                translateY = (contentHeight / 2) - (badgeHeight / 2);
                break;
            case BOTTOM_LEFT:
                translateX = -(contentWidth / 2) + (badgeWidth / 2);
                translateY = (contentHeight / 2) - (badgeHeight / 2);
                break;
        }

        // Apply offsets
        translateX += badge.getOffsetX();
        translateY += badge.getOffsetY();

        badgeContainer.setTranslateX(translateX);
        badgeContainer.setTranslateY(translateY);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        container.resizeRelocate(contentX, contentY, contentWidth, contentHeight);
        updateBadgePosition();
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        // Only size based on content, not the badge
        return contentContainer.minWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        // Only size based on content, not the badge
        return contentContainer.minHeight(width) + topInset + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        // Only size based on content, not the badge
        return contentContainer.prefWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        // Only size based on content, not the badge
        return contentContainer.prefHeight(width) + topInset + bottomInset;
    }
}