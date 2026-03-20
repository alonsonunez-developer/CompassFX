package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXAvatar;
import com.compassfx.controls.CFXBadge;
import com.compassfx.controls.CFXButton;
import com.compassfx.enums.AvatarSize;
import com.compassfx.enums.BadgeColor;
import com.compassfx.enums.BadgePosition;
import com.compassfx.enums.BadgeVariant;
import com.compassfx.enums.ButtonVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class BadgeDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // Standalone Pills (números solos)
        // ====================================
        Label pillsLabel = new Label("Standalone Badge Pills");
        pillsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox pillsBox = new HBox(15);
        pillsBox.setAlignment(Pos.CENTER);

        pillsBox.getChildren().addAll(
                createStandaloneBadge("4", BadgeColor.DEFAULT),
                createStandaloneBadge("3", BadgeColor.PRIMARY),
                createStandaloneBadge("7", BadgeColor.SECONDARY),
                createStandaloneBadge("5", BadgeColor.SUCCESS),
                createStandaloneBadge("12", BadgeColor.WARNING),
                createStandaloneBadge("2", BadgeColor.ERROR),
                createStandaloneBadge("9", BadgeColor.INFO)
        );

        // ====================================
        // Text Pills
        // ====================================
        Label textPillsLabel = new Label("Text Badge Pills");
        textPillsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox textPillsBox = new HBox(15);
        textPillsBox.setAlignment(Pos.CENTER);

        textPillsBox.getChildren().addAll(
                createStandaloneBadge("NEW", BadgeColor.PRIMARY),
                createStandaloneBadge("HOT", BadgeColor.ERROR),
                createStandaloneBadge("SALE", BadgeColor.WARNING),
                createStandaloneBadge("BETA", BadgeColor.INFO),
                createStandaloneBadge("PRO", BadgeColor.SUCCESS)
        );

        // ====================================
        // Badges on Icons (con emojis)
        // ====================================
        Label iconsLabel = new Label("Badges on Icons");
        iconsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox iconsBox = new HBox(40);
        iconsBox.setAlignment(Pos.CENTER);

        // Mail icon with badge
        Label mailIcon = new Label("📧");
        mailIcon.setStyle("-fx-font-size: 48px;");
        CFXBadge mailBadge = new CFXBadge(mailIcon);
        mailBadge.setValue(5);
        mailBadge.setColor(BadgeColor.ERROR);

        // Bell icon with badge
        Label bellIcon = new Label("🔔");
        bellIcon.setStyle("-fx-font-size: 48px;");
        CFXBadge bellBadge = new CFXBadge(bellIcon);
        bellBadge.setValue(15);
        bellBadge.setColor(BadgeColor.WARNING);

        // Cart icon with badge
        Label cartIcon = new Label("🛒");
        cartIcon.setStyle("-fx-font-size: 48px;");
        CFXBadge cartBadge = new CFXBadge(cartIcon);
        cartBadge.setValue(3);
        cartBadge.setColor(BadgeColor.SUCCESS);

        // Message icon with dot
        Label msgIcon = new Label("💬");
        msgIcon.setStyle("-fx-font-size: 48px;");
        CFXBadge msgBadge = new CFXBadge(msgIcon);
        msgBadge.setVariant(BadgeVariant.DOT);
        msgBadge.setColor(BadgeColor.PRIMARY);

        iconsBox.getChildren().addAll(mailBadge, bellBadge, cartBadge, msgBadge);

        // ====================================
        // Badges on Avatars
        // ====================================
        Label avatarsLabel = new Label("Badges on Avatars");
        avatarsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox avatarsBox = new HBox(40);
        avatarsBox.setAlignment(Pos.CENTER);

        // Avatar with number badge
        CFXAvatar avatar1 = new CFXAvatar("JD");
        avatar1.setAvatarSize(AvatarSize.LARGE);
        avatar1.setColor(Color.web("#2196F3"));
        CFXBadge avatarBadge1 = new CFXBadge(avatar1);
        avatarBadge1.setValue(9);
        avatarBadge1.setColor(BadgeColor.ERROR);

        // Avatar with dot badge (online status)
        CFXAvatar avatar2 = new CFXAvatar("AS");
        avatar2.setAvatarSize(AvatarSize.LARGE);
        avatar2.setColor(Color.web("#4CAF50"));
        CFXBadge avatarBadge2 = new CFXBadge(avatar2);
        avatarBadge2.setVariant(BadgeVariant.DOT);
        avatarBadge2.setColor(BadgeColor.SUCCESS);
        avatarBadge2.setPosition(BadgePosition.BOTTOM_RIGHT);

        // Avatar with text badge
        CFXAvatar avatar3 = new CFXAvatar("MK");
        avatar3.setAvatarSize(AvatarSize.LARGE);
        avatar3.setColor(Color.web("#FF9800"));
        CFXBadge avatarBadge3 = new CFXBadge(avatar3);
        avatarBadge3.setText("VIP");
        avatarBadge3.setColor(BadgeColor.WARNING);

        avatarsBox.getChildren().addAll(avatarBadge1, avatarBadge2, avatarBadge3);

        // ====================================
        // Badges on Buttons
        // ====================================
        Label buttonsLabel = new Label("Badges on Buttons");
        buttonsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox buttonsBox = new HBox(30);
        buttonsBox.setAlignment(Pos.CENTER);

        // Messages button with badge
        CFXButton messagesBtn = new CFXButton("Messages");
        messagesBtn.setVariant(ButtonVariant.CONTAINED);
        CFXBadge messagesBadge = new CFXBadge(messagesBtn);
        messagesBadge.setValue(7);
        messagesBadge.setColor(BadgeColor.ERROR);

        // Notifications button with badge
        CFXButton notifBtn = new CFXButton("Notifications");
        notifBtn.setVariant(ButtonVariant.OUTLINED);
        CFXBadge notifBadge = new CFXBadge(notifBtn);
        notifBadge.setValue(25);
        notifBadge.setColor(BadgeColor.WARNING);

        // Cart button with badge
        CFXButton cartBtn = new CFXButton("Cart");
        CFXBadge cartBtnBadge = new CFXBadge(cartBtn);
        cartBtnBadge.setValue(3);
        cartBtnBadge.setColor(BadgeColor.SUCCESS);

        buttonsBox.getChildren().addAll(messagesBadge, notifBadge, cartBtnBadge);

        // ====================================
        // Different Positions
        // ====================================
        Label positionsLabel = new Label("Badge Positions");
        positionsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox positionsBox = new HBox(40);
        positionsBox.setAlignment(Pos.CENTER);

        Circle circle1 = createCircle(Color.web("#2196F3"));
        CFXBadge topRight = new CFXBadge(circle1);
        topRight.setValue(1);
        topRight.setPosition(BadgePosition.TOP_RIGHT);
        topRight.setColor(BadgeColor.PRIMARY);

        Circle circle2 = createCircle(Color.web("#4CAF50"));
        CFXBadge topLeft = new CFXBadge(circle2);
        topLeft.setValue(2);
        topLeft.setPosition(BadgePosition.TOP_LEFT);
        topLeft.setColor(BadgeColor.SUCCESS);

        Circle circle3 = createCircle(Color.web("#FF9800"));
        CFXBadge bottomRight = new CFXBadge(circle3);
        bottomRight.setValue(3);
        bottomRight.setPosition(BadgePosition.BOTTOM_RIGHT);
        bottomRight.setColor(BadgeColor.WARNING);

        Circle circle4 = createCircle(Color.web("#F44336"));
        CFXBadge bottomLeft = new CFXBadge(circle4);
        bottomLeft.setValue(4);
        bottomLeft.setPosition(BadgePosition.BOTTOM_LEFT);
        bottomLeft.setColor(BadgeColor.ERROR);

        positionsBox.getChildren().addAll(topRight, topLeft, bottomRight, bottomLeft);

        // ====================================
        // Max Value Display (99+)
        // ====================================
        Label maxLabel = new Label("Max Value Display (99+)");
        maxLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox maxBox = new HBox(15);
        maxBox.setAlignment(Pos.CENTER);

        maxBox.getChildren().addAll(
                createStandaloneBadge("50", BadgeColor.WARNING, 50),
                createStandaloneBadge("99", BadgeColor.ERROR, 99),
                createStandaloneBadge("99+", BadgeColor.ERROR, 150),
                createStandaloneBadge("99+", BadgeColor.SECONDARY, 999)
        );

        // ====================================
        // Interactive Demo
        // ====================================
        Label interactiveLabel = new Label("Interactive Badge Counter");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox interactiveBox = new VBox(20);
        interactiveBox.setAlignment(Pos.CENTER);

        Label counterIcon = new Label("📬");
        counterIcon.setStyle("-fx-font-size: 64px;");
        CFXBadge counterBadge = new CFXBadge(counterIcon);
        counterBadge.setValue(0);
        counterBadge.setColor(BadgeColor.ERROR);
        counterBadge.setShowZero(false);

        HBox controlsBox = new HBox(10);
        controlsBox.setAlignment(Pos.CENTER);

        CFXButton addBtn = new CFXButton("+ Add");
        addBtn.setVariant(ButtonVariant.CONTAINED);
        addBtn.setOnAction(e -> counterBadge.setValue(counterBadge.getValue() + 1));

        CFXButton removeBtn = new CFXButton("− Remove");
        removeBtn.setVariant(ButtonVariant.OUTLINED);
        removeBtn.setOnAction(e -> {
            if (counterBadge.getValue() > 0) {
                counterBadge.setValue(counterBadge.getValue() - 1);
            }
        });

        CFXButton resetBtn = new CFXButton("Reset");
        resetBtn.setVariant(ButtonVariant.TEXT);
        resetBtn.setOnAction(e -> counterBadge.setValue(0));

        controlsBox.getChildren().addAll(addBtn, removeBtn, resetBtn);
        interactiveBox.getChildren().addAll(counterBadge, controlsBox);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                pillsLabel,
                pillsBox,
                new Separator(),
                textPillsLabel,
                textPillsBox,
                new Separator(),
                iconsLabel,
                iconsBox,
                new Separator(),
                avatarsLabel,
                avatarsBox,
                new Separator(),
                buttonsLabel,
                buttonsBox,
                new Separator(),
                positionsLabel,
                positionsBox,
                new Separator(),
                maxLabel,
                maxBox,
                new Separator(),
                interactiveLabel,
                interactiveBox
        );
    }

    // Helper: Create standalone badge pill (just the badge, no content)
    private StackPane createStandaloneBadge(String text, BadgeColor color) {
        StackPane pill = new StackPane();
        pill.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-padding: 4px 10px; " +
                        "-fx-min-width: 24px; " +
                        "-fx-min-height: 24px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.15), 3, 0.2, 0, 1);",
                getColorHex(color)
        ));

        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: 600;");

        pill.getChildren().add(label);
        return pill;
    }

    private StackPane createStandaloneBadge(String text, BadgeColor color, int value) {
        return createStandaloneBadge(text, color);
    }

    private Circle createCircle(Color color) {
        Circle circle = new Circle(30);
        circle.setFill(color);
        return circle;
    }

    private String getColorHex(BadgeColor color) {
        switch (color) {
            case PRIMARY: return "#2196F3";
            case SECONDARY: return "#9C27B0";
            case SUCCESS: return "#4CAF50";
            case WARNING: return "#FF9800";
            case ERROR: return "#F44336";
            case INFO: return "#00BCD4";
            case DEFAULT:
            default: return "#616161";
        }
    }

}