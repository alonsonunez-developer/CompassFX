package com.compassfx.demo;

import com.compassfx.controls.*;
import com.compassfx.enums.SnackbarPosition;
import com.compassfx.enums.SnackbarType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Demo application showcasing CFXSnackbar features
 */
public class SnackbarDemo {

    private Scene scene;

    public void showDemo(Label title, VBox root, Scene scene) {
        this.scene = scene;

        Label subtitle = new Label("Click buttons to show snackbar notifications");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: rgba(0, 0, 0, 0.6);");

        // ===== BASIC SNACKBARS SECTION =====
        Label basicLabel = new Label("Basic Snackbars");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox basicSection = new HBox(15);
        basicSection.setAlignment(Pos.CENTER);

        CFXButton simpleBtn = new CFXButton("Show Simple");
        simpleBtn.setOnAction(e -> {
            System.out.println("Button clicked - showing snackbar");
            CFXSnackbar snackbar = new CFXSnackbar("This is a simple snackbar message");
            System.out.println("Snackbar created, calling show()");
            snackbar.show(scene);
            System.out.println("Show() called");
        });

        CFXButton withActionBtn = new CFXButton("With Action");
        withActionBtn.setOnAction(e -> {
            new CFXSnackbar("Message sent successfully")
                    .withAction("UNDO", () -> System.out.println("Action clicked!"))
                    .show(scene);
        });

        CFXButton longMessageBtn = new CFXButton("Long Message");
        longMessageBtn.setOnAction(e -> {
            new CFXSnackbar("This is a longer message that demonstrates how snackbars handle text wrapping when the content exceeds the available width.")
                    .withDuration(Duration.seconds(5))
                    .show(scene);
        });

        basicSection.getChildren().addAll(simpleBtn, withActionBtn, longMessageBtn);

        // ===== TYPE VARIANTS SECTION =====
        Label typeLabel = new Label("Type Variants");
        typeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox typeSection = new HBox(15);
        typeSection.setAlignment(Pos.CENTER);

        CFXButton defaultBtn = new CFXButton("Default");
        defaultBtn.setOnAction(e -> {
            new CFXSnackbar("This is a default snackbar")
                    .withType(SnackbarType.DEFAULT)
                    .show(scene);
        });

        CFXButton successBtn = new CFXButton("Success");
        successBtn.setOnAction(e -> {
            CFXSnackbar.success("Operation completed successfully!")
                    .withAction("VIEW", () -> System.out.println("View clicked"))
                    .show(scene);
        });

        CFXButton warningBtn = new CFXButton("Warning");
        warningBtn.setOnAction(e -> {
            CFXSnackbar.warning("Your session will expire in 5 minutes")
                    .withAction("EXTEND", () -> System.out.println("Session extended"))
                    .show(scene);
        });

        CFXButton errorBtn = new CFXButton("Error");
        errorBtn.setOnAction(e -> {
            CFXSnackbar.error("Failed to save changes")
                    .withAction("RETRY", () -> System.out.println("Retrying..."))
                    .show(scene);
        });

        CFXButton infoBtn = new CFXButton("Info");
        infoBtn.setOnAction(e -> {
            CFXSnackbar.info("New version available")
                    .withAction("UPDATE", () -> System.out.println("Updating..."))
                    .show(scene);
        });

        typeSection.getChildren().addAll(defaultBtn, successBtn, warningBtn, errorBtn, infoBtn);

        // ===== POSITION VARIANTS SECTION =====
        Label positionLabel = new Label("Position Variants");
        positionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox positionSection = new VBox(15);
        positionSection.setAlignment(Pos.CENTER);

        // Top positions
        Label topLabel = new Label("Top Positions:");
        topLabel.setStyle("-fx-font-weight: 500;");

        HBox topRow = new HBox(15);
        topRow.setAlignment(Pos.CENTER);

        CFXButton topLeftBtn = new CFXButton("Top Left");
        topLeftBtn.setOnAction(e -> {
            new CFXSnackbar("Notification from top left")
                    .withPosition(SnackbarPosition.TOP_LEFT)
                    .show(scene);
        });

        CFXButton topCenterBtn = new CFXButton("Top Center");
        topCenterBtn.setOnAction(e -> {
            new CFXSnackbar("Notification from top center")
                    .withPosition(SnackbarPosition.TOP_CENTER)
                    .show(scene);
        });

        CFXButton topRightBtn = new CFXButton("Top Right");
        topRightBtn.setOnAction(e -> {
            new CFXSnackbar("Notification from top right")
                    .withPosition(SnackbarPosition.TOP_RIGHT)
                    .show(scene);
        });

        topRow.getChildren().addAll(topLeftBtn, topCenterBtn, topRightBtn);

        // Bottom positions
        Label bottomLabel = new Label("Bottom Positions:");
        bottomLabel.setStyle("-fx-font-weight: 500;");

        HBox bottomRow = new HBox(15);
        bottomRow.setAlignment(Pos.CENTER);

        CFXButton bottomLeftBtn = new CFXButton("Bottom Left");
        bottomLeftBtn.setOnAction(e -> {
            new CFXSnackbar("Notification from bottom left")
                    .withPosition(SnackbarPosition.BOTTOM_LEFT)
                    .show(scene);
        });

        CFXButton bottomCenterBtn = new CFXButton("Bottom Center");
        bottomCenterBtn.setOnAction(e -> {
            new CFXSnackbar("Notification from bottom center")
                    .withPosition(SnackbarPosition.BOTTOM_CENTER)
                    .show(scene);
        });

        CFXButton bottomRightBtn = new CFXButton("Bottom Right");
        bottomRightBtn.setOnAction(e -> {
            new CFXSnackbar("Notification from bottom right")
                    .withPosition(SnackbarPosition.BOTTOM_RIGHT)
                    .show(scene);
        });

        bottomRow.getChildren().addAll(bottomLeftBtn, bottomCenterBtn, bottomRightBtn);

        positionSection.getChildren().addAll(topLabel, topRow, bottomLabel, bottomRow);

        // ===== DURATION VARIANTS SECTION =====
        Label durationLabel = new Label("Duration Variants");
        durationLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox durationSection = new HBox(15);
        durationSection.setAlignment(Pos.CENTER);

        CFXButton shortBtn = new CFXButton("Short (1s)");
        shortBtn.setOnAction(e -> {
            new CFXSnackbar("Quick notification")
                    .withDuration(Duration.seconds(1))
                    .show(scene);
        });

        CFXButton mediumBtn = new CFXButton("Medium (3s)");
        mediumBtn.setOnAction(e -> {
            new CFXSnackbar("Standard notification")
                    .withDuration(Duration.seconds(3))
                    .show(scene);
        });

        CFXButton longBtn = new CFXButton("Long (6s)");
        longBtn.setOnAction(e -> {
            new CFXSnackbar("This notification stays longer")
                    .withDuration(Duration.seconds(6))
                    .show(scene);
        });

        CFXButton persistentBtn = new CFXButton("Persistent");
        persistentBtn.setOnAction(e -> {
            new CFXSnackbar("Click X to close this notification")
                    .withDuration(Duration.INDEFINITE)
                    .show(scene);
        });

        durationSection.getChildren().addAll(shortBtn, mediumBtn, longBtn, persistentBtn);

        // ===== REAL-WORLD EXAMPLES SECTION =====
        Label examplesLabel = new Label("Real-World Examples");
        examplesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox examplesSection = new VBox(15);
        examplesSection.setAlignment(Pos.CENTER);

        // Save example
        HBox saveExample = new HBox(10);
        saveExample.setAlignment(Pos.CENTER);

        CFXTextField saveField = new CFXTextField();
        saveField.setPromptText("Enter some text...");
        saveField.setPrefWidth(300);

        CFXButton saveExampleBtn = new CFXButton("Save");
        saveExampleBtn.setOnAction(e -> {
            if (saveField.getText().isEmpty()) {
                CFXSnackbar.error("Please enter some text")
                        .show(scene);
            } else {
                CFXSnackbar.success("Changes saved successfully")
                        .withAction("UNDO", () -> {
                            saveField.clear();
                            new CFXSnackbar("Changes undone").show(scene);
                        })
                        .show(scene);
            }
        });

        saveExample.getChildren().addAll(saveField, saveExampleBtn);

        // Delete example
        CFXButton deleteExample = new CFXButton("Delete Item");
        deleteExample.setOnAction(e -> {
            CFXSnackbar.warning("Item will be deleted in 5 seconds")
                    .withAction("CANCEL", () -> {
                        CFXSnackbar.info("Deletion cancelled").show(scene);
                    })
                    .withDuration(Duration.seconds(5))
                    .show(scene);
        });

        // Copy example
        CFXButton copyExample = new CFXButton("Copy to Clipboard");
        copyExample.setOnAction(e -> {
            CFXSnackbar.success("Copied to clipboard")
                    .withDuration(Duration.seconds(2))
                    .show(scene);
        });

        // Network example
        CFXButton networkExample = new CFXButton("Simulate Network Error");
        networkExample.setOnAction(e -> {
            CFXSnackbar.error("Network connection lost")
                    .withAction("RETRY", () -> {
                        CFXSnackbar.info("Reconnecting...").show(scene);
                    })
                    .withPosition(SnackbarPosition.TOP_CENTER)
                    .show(scene);
        });

        HBox exampleButtons = new HBox(10);
        exampleButtons.setAlignment(Pos.CENTER);
        exampleButtons.getChildren().addAll(deleteExample, copyExample, networkExample);

        examplesSection.getChildren().addAll(saveExample, exampleButtons);

        // ===== USAGE TIPS SECTION =====
        Label tipsLabel = new Label("Usage Tips");
        tipsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXCard tipsCard = new CFXCard();
        tipsCard.setMaxWidth(600);

        VBox tipsContent = new VBox(10);

        Label tip1 = new Label("• Use SUCCESS for completed actions");
        Label tip2 = new Label("• Use ERROR for failed operations with RETRY action");
        Label tip3 = new Label("• Use WARNING for important notifications");
        Label tip4 = new Label("• Use INFO for general information");
        Label tip5 = new Label("• Keep messages concise and actionable");
        Label tip6 = new Label("• Use actions for reversible operations (UNDO)");

        tip1.setStyle("-fx-font-size: 13px;");
        tip2.setStyle("-fx-font-size: 13px;");
        tip3.setStyle("-fx-font-size: 13px;");
        tip4.setStyle("-fx-font-size: 13px;");
        tip5.setStyle("-fx-font-size: 13px;");
        tip6.setStyle("-fx-font-size: 13px;");

        tipsContent.getChildren().addAll(tip1, tip2, tip3, tip4, tip5, tip6);
        tipsCard.setContent(tipsContent);

        // Add all sections to root
        root.getChildren().addAll(
                title,
                subtitle,
                new Separator(),
                basicLabel,
                basicSection,
                new Separator(),
                typeLabel,
                typeSection,
                new Separator(),
                positionLabel,
                positionSection,
                new Separator(),
                durationLabel,
                durationSection,
                new Separator(),
                examplesLabel,
                examplesSection,
                new Separator(),
                tipsLabel,
                tipsCard
        );
    }
}