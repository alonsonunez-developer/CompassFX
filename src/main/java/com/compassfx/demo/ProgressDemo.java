package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ProgressColor;
import com.compassfx.enums.ProgressSize;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Demo application showcasing CFXProgressBar and CFXProgressSpinner features
 */
public class ProgressDemo {

    public void showDemo(Label title, VBox content){
        Label sizeLabel = new Label("ProgressBar - Sizes");
        sizeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox sizeSection = new VBox(15);
        sizeSection.setAlignment(Pos.CENTER_LEFT);
        sizeSection.setMaxWidth(500);

        VBox smallBox = createLabeledProgressBar("Small", 0.6, ProgressSize.SMALL, ProgressColor.PRIMARY);
        VBox mediumBox = createLabeledProgressBar("Medium (Default)", 0.6, ProgressSize.MEDIUM, ProgressColor.PRIMARY);
        VBox largeBox = createLabeledProgressBar("Large", 0.6, ProgressSize.LARGE, ProgressColor.PRIMARY);

        sizeSection.getChildren().addAll(smallBox, mediumBox, largeBox);

        // ===== PROGRESS BAR - COLORS SECTION =====
        Label colorLabel = new Label("ProgressBar - Colors");
        colorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox colorSection = new VBox(15);
        colorSection.setAlignment(Pos.CENTER_LEFT);
        colorSection.setMaxWidth(500);

        VBox primaryBox = createLabeledProgressBar("Primary", 0.75, ProgressSize.MEDIUM, ProgressColor.PRIMARY);
        VBox secondaryBox = createLabeledProgressBar("Secondary", 0.75, ProgressSize.MEDIUM, ProgressColor.SECONDARY);
        VBox successBox = createLabeledProgressBar("Success", 0.75, ProgressSize.MEDIUM, ProgressColor.SUCCESS);
        VBox warningBox = createLabeledProgressBar("Warning", 0.75, ProgressSize.MEDIUM, ProgressColor.WARNING);
        VBox errorBox = createLabeledProgressBar("Error", 0.75, ProgressSize.MEDIUM, ProgressColor.ERROR);

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

        VBox smallSpinner = createLabeledSpinner("Small", 0.7, ProgressSize.SMALL, ProgressColor.PRIMARY, false);
        VBox mediumSpinner = createLabeledSpinner("Medium", 0.7, ProgressSize.MEDIUM, ProgressColor.PRIMARY, false);
        VBox largeSpinner = createLabeledSpinner("Large", 0.7, ProgressSize.LARGE, ProgressColor.PRIMARY, false);

        spinnerSizeSection.getChildren().addAll(smallSpinner, mediumSpinner, largeSpinner);

        // ===== PROGRESS SPINNER - COLORS SECTION =====
        Label spinnerColorLabel = new Label("ProgressSpinner - Colors");
        spinnerColorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox spinnerColorSection = new HBox(30);
        spinnerColorSection.setAlignment(Pos.CENTER);

        VBox primarySpinner = createLabeledSpinner("Primary", 0.7, ProgressSize.MEDIUM, ProgressColor.PRIMARY, false);
        VBox secondarySpinner = createLabeledSpinner("Secondary", 0.7, ProgressSize.MEDIUM, ProgressColor.SECONDARY, false);
        VBox successSpinner = createLabeledSpinner("Success", 0.7, ProgressSize.MEDIUM, ProgressColor.SUCCESS, false);
        VBox warningSpinner = createLabeledSpinner("Warning", 0.7, ProgressSize.MEDIUM, ProgressColor.WARNING, false);
        VBox errorSpinner = createLabeledSpinner("Error", 0.7, ProgressSize.MEDIUM, ProgressColor.ERROR, false);

        spinnerColorSection.getChildren().addAll(primarySpinner, secondarySpinner, successSpinner, warningSpinner, errorSpinner);

        // ===== PROGRESS SPINNER - INDETERMINATE SECTION =====
        Label spinnerIndeterminateLabel = new Label("Indeterminate Spinners");
        spinnerIndeterminateLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox spinnerIndeterminateSection = new HBox(30);
        spinnerIndeterminateSection.setAlignment(Pos.CENTER);

        VBox loadingSpinner1 = createLabeledSpinner("Loading...", 0, ProgressSize.SMALL, ProgressColor.PRIMARY, true);
        VBox loadingSpinner2 = createLabeledSpinner("Loading...", 0, ProgressSize.MEDIUM, ProgressColor.SUCCESS, true);
        VBox loadingSpinner3 = createLabeledSpinner("Loading...", 0, ProgressSize.LARGE, ProgressColor.SECONDARY, true);

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
        content.getChildren().addAll(
                title,
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
    /**
     * Create a labeled progress bar
     */
    public static VBox createLabeledProgressBar(String label, double progress,
                                          ProgressSize size, ProgressColor color) {
        VBox container = new VBox(8);
        container.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500;");

        CFXProgressBar progressBar = new CFXProgressBar(progress);
        progressBar.setSize(size);
        progressBar.setColor(color);
        progressBar.setPrefWidth(500);

        container.getChildren().addAll(titleLabel, progressBar);
        return container;
    }

    /**
     * Create a labeled spinner
     */
    static VBox createLabeledSpinner(String label, double progress,
                                     ProgressSize size, ProgressColor color, boolean indeterminate) {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        CFXProgressSpinner spinner = new CFXProgressSpinner(progress, indeterminate);
        spinner.setSize(size);
        spinner.setColor(color);

        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-size: 12px;");

        container.getChildren().addAll(spinner, titleLabel);
        return container;
    }
}