// ============================================
// SkeletonDemo.java - Skeleton Loader Demo
// src/main/java/com/compassfx/demo/SkeletonDemo.java
// ============================================
package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXSkeleton;
import com.compassfx.enums.SkeletonAnimation;
import com.compassfx.enums.SkeletonVariant;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SkeletonDemo {

    public void showDemo(Label title, VBox root) {

        // ====================================
        // Basic Variants
        // ====================================
        Label variantsLabel = new Label("Skeleton Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox variantsBox = new VBox(20);
        variantsBox.setAlignment(Pos.CENTER_LEFT);
        variantsBox.setMaxWidth(600);

        Label textLabel = new Label("Text Skeleton:");
        textLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXSkeleton textSkeleton = CFXSkeleton.text(400);

        Label circularLabel = new Label("Circular Skeleton:");
        circularLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXSkeleton circularSkeleton = CFXSkeleton.circle(60);

        Label rectangularLabel = new Label("Rectangular Skeleton:");
        rectangularLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXSkeleton rectangularSkeleton = CFXSkeleton.rectangle(400, 200);

        Label roundedLabel = new Label("Rounded Skeleton:");
        roundedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");
        CFXSkeleton roundedSkeleton = CFXSkeleton.rounded(400, 150);

        variantsBox.getChildren().addAll(
                textLabel, textSkeleton,
                circularLabel, circularSkeleton,
                rectangularLabel, rectangularSkeleton,
                roundedLabel, roundedSkeleton
        );

        // ====================================
        // Animation Types
        // ====================================
        Label animLabel = new Label("Animation Types");
        animLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox animBox = new HBox(30);
        animBox.setAlignment(Pos.CENTER);

        VBox waveBox = new VBox(10);
        waveBox.setAlignment(Pos.CENTER);
        Label waveLabel = new Label("Wave");
        CFXSkeleton waveSkeleton = CFXSkeleton.rectangle(150, 100);
        waveSkeleton.setAnimation(SkeletonAnimation.WAVE);
        waveBox.getChildren().addAll(waveSkeleton, waveLabel);

        VBox pulseBox = new VBox(10);
        pulseBox.setAlignment(Pos.CENTER);
        Label pulseLabel = new Label("Pulse");
        CFXSkeleton pulseSkeleton = CFXSkeleton.rectangle(150, 100);
        pulseSkeleton.setAnimation(SkeletonAnimation.PULSE);
        pulseBox.getChildren().addAll(pulseSkeleton, pulseLabel);

        VBox noneBox = new VBox(10);
        noneBox.setAlignment(Pos.CENTER);
        Label noneLabel = new Label("None");
        CFXSkeleton noneSkeleton = CFXSkeleton.rectangle(150, 100);
        noneSkeleton.setAnimation(SkeletonAnimation.NONE);
        noneBox.getChildren().addAll(noneSkeleton, noneLabel);

        animBox.getChildren().addAll(waveBox, pulseBox, noneBox);

        // ====================================
        // Text Lines Example
        // ====================================
        Label linesLabel = new Label("Multiple Text Lines");
        linesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox linesBox = new VBox(10);
        linesBox.setMaxWidth(500);

        linesBox.getChildren().addAll(
                CFXSkeleton.text(500),
                CFXSkeleton.text(480),
                CFXSkeleton.text(450),
                CFXSkeleton.text(420)
        );

        // ====================================
        // Card Skeleton Example
        // ====================================
        Label cardLabel = new Label("Card Skeleton Example");
        cardLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox cardSkeleton = createCardSkeleton();

        // ====================================
        // User List Skeleton Example
        // ====================================
        Label userListLabel = new Label("User List Skeleton");
        userListLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox userListSkeleton = new VBox(15);
        userListSkeleton.setMaxWidth(400);

        for (int i = 0; i < 3; i++) {
            userListSkeleton.getChildren().add(createUserItemSkeleton());
        }

        // ====================================
        // Interactive Loading Demo
        // ====================================
        Label interactiveLabel = new Label("Interactive Loading Demo");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox interactiveBox = new VBox(15);
        interactiveBox.setMaxWidth(400);
        interactiveBox.setAlignment(Pos.CENTER);

        VBox contentArea = new VBox(10);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER_LEFT);
        contentArea.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 12px;"
        );

        // Start with skeleton
        VBox initialSkeleton = new VBox(10);
        initialSkeleton.getChildren().addAll(
                CFXSkeleton.text(350),
                CFXSkeleton.text(300),
                CFXSkeleton.text(330)
        );
        contentArea.getChildren().add(initialSkeleton);

        CFXButton loadBtn = new CFXButton("Load Content");
        loadBtn.setOnAction(e -> {
            contentArea.getChildren().clear();

            VBox skeleton = new VBox(10);
            skeleton.getChildren().addAll(
                    CFXSkeleton.text(350),
                    CFXSkeleton.text(300),
                    CFXSkeleton.text(330)
            );
            contentArea.getChildren().add(skeleton);

            // Simulate loading
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                contentArea.getChildren().clear();

                Label loadedTitle = new Label("Content Loaded!");
                loadedTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

                Label loadedDesc = new Label(
                        "This content was loaded after 2 seconds. " +
                                "The skeleton loader provided a nice placeholder while loading."
                );
                loadedDesc.setWrapText(true);
                loadedDesc.setStyle("-fx-text-fill: #666;");

                contentArea.getChildren().addAll(loadedTitle, loadedDesc);
            });
            pause.play();
        });

        interactiveBox.getChildren().addAll(contentArea, loadBtn);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                animLabel,
                animBox,
                new Separator(),
                linesLabel,
                linesBox,
                new Separator(),
                cardLabel,
                cardSkeleton,
                new Separator(),
                userListLabel,
                userListSkeleton,
                new Separator(),
                interactiveLabel,
                interactiveBox
        );
    }

    private VBox createCardSkeleton() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setMaxWidth(350);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 12px;"
        );

        CFXSkeleton imageSkeleton = CFXSkeleton.rounded(350, 200);
        CFXSkeleton titleSkeleton = CFXSkeleton.text(280);
        CFXSkeleton descLine1 = CFXSkeleton.text(330);
        CFXSkeleton descLine2 = CFXSkeleton.text(300);

        card.getChildren().addAll(imageSkeleton, titleSkeleton, descLine1, descLine2);

        return card;
    }

    private HBox createUserItemSkeleton() {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 8px;"
        );

        CFXSkeleton avatar = CFXSkeleton.circle(40);

        VBox textInfo = new VBox(5);
        textInfo.getChildren().addAll(
                CFXSkeleton.text(150),
                CFXSkeleton.text(120)
        );

        item.getChildren().addAll(avatar, textInfo);

        return item;
    }

}