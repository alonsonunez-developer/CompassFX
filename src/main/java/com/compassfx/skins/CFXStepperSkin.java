package com.compassfx.skins;

import com.compassfx.controls.CFXStepper;
import com.compassfx.enums.StepStatus;
import com.compassfx.enums.StepperOrientation;
import com.compassfx.models.StepperItem;
import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CFXStepperSkin extends SkinBase<CFXStepper> {

    private final CFXStepper stepper;
    private final Pane container;
    private final VBox contentContainer;

    public CFXStepperSkin(CFXStepper stepper) {
        super(stepper);
        this.stepper = stepper;

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);

        // Steps container
        if (stepper.getOrientation() == StepperOrientation.HORIZONTAL) {
            container = new HBox();
            ((HBox) container).setAlignment(Pos.CENTER);
        } else {
            container = new VBox(10);
            ((VBox) container).setAlignment(Pos.TOP_LEFT);
        }
        container.getStyleClass().add("stepper-container");

        // Content container
        contentContainer = new VBox(15);
        contentContainer.getStyleClass().add("step-content-container");
        contentContainer.setPadding(new Insets(20));

        mainContainer.getChildren().add(container);

        if (stepper.isShowContent()) {
            mainContainer.getChildren().add(contentContainer);
        }

        getChildren().add(mainContainer);

        // Listen for changes
        stepper.getSteps().addListener((ListChangeListener.Change<? extends StepperItem> c) -> {
            updateStepper();
        });

        stepper.currentStepProperty().addListener((obs, old, newVal) -> {
            updateStepper();
            updateContent();
        });

        stepper.orientationProperty().addListener((obs, old, newVal) -> updateStepper());
        stepper.showContentProperty().addListener((obs, old, newVal) -> {
            if (newVal && !mainContainer.getChildren().contains(contentContainer)) {
                mainContainer.getChildren().add(contentContainer);
            } else if (!newVal && mainContainer.getChildren().contains(contentContainer)) {
                mainContainer.getChildren().remove(contentContainer);
            }
        });

        updateStepper();
        updateContent();
    }

    private void updateStepper() {
        container.getChildren().clear();

        if (stepper.getSteps().isEmpty()) {
            return;
        }

        boolean isHorizontal = stepper.getOrientation() == StepperOrientation.HORIZONTAL;

        for (int i = 0; i < stepper.getSteps().size(); i++) {
            StepperItem step = stepper.getSteps().get(i);
            boolean isLast = i == stepper.getSteps().size() - 1;

            // Create step node
            Pane stepNode = createStepNode(step, i);
            container.getChildren().add(stepNode);

            // Add connector line (except for last step)
            if (!isLast) {
                Pane connector = createConnector(step, stepper.getSteps().get(i + 1), isHorizontal);
                container.getChildren().add(connector);
            }
        }
    }

    private Pane createStepNode(StepperItem step, int index) {
        VBox stepBox = new VBox(8);
        stepBox.setAlignment(Pos.TOP_CENTER);
        stepBox.getStyleClass().add("step-node");

        if (stepper.getOrientation() == StepperOrientation.HORIZONTAL) {
            stepBox.setMinWidth(120);
            stepBox.setMaxWidth(150);
        } else {
            HBox.setHgrow(stepBox, Priority.ALWAYS);
        }

        // Step indicator (circle with number/icon/checkmark)
        StackPane indicator = createIndicator(step, index);

        // Step label
        VBox labelBox = new VBox(4);
        labelBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(step.titleProperty());
        titleLabel.getStyleClass().add("step-title");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(stepper.getOrientation() == StepperOrientation.HORIZONTAL ? 150 : Double.MAX_VALUE);

        labelBox.getChildren().add(titleLabel);

        // Description (optional)
        if (step.getDescription() != null && !step.getDescription().isEmpty()) {
            Label descLabel = new Label();
            descLabel.textProperty().bind(step.descriptionProperty());
            descLabel.getStyleClass().add("step-description");
            descLabel.setFont(Font.font("System", 12));
            descLabel.setWrapText(true);
            descLabel.setMaxWidth(stepper.getOrientation() == StepperOrientation.HORIZONTAL ? 150 : Double.MAX_VALUE);
            labelBox.getChildren().add(descLabel);
        }

        // Optional badge
        if (step.isOptional()) {
            Label optionalLabel = new Label("Optional");
            optionalLabel.getStyleClass().add("step-optional");
            optionalLabel.setFont(Font.font("System", 11));
            labelBox.getChildren().add(optionalLabel);
        }

        if (stepper.getOrientation() == StepperOrientation.HORIZONTAL) {
            stepBox.getChildren().addAll(indicator, labelBox);
        } else {
            HBox hbox = new HBox(15);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(indicator, labelBox);
            stepBox.getChildren().add(hbox);
        }

        // Make clickable if not linear or if step is accessible
        boolean canClick = !stepper.isLinear() || index <= stepper.getCurrentStep();

        if (canClick && index != stepper.getCurrentStep()) {
            stepBox.setCursor(Cursor.HAND);
            stepBox.setOnMouseClicked(e -> stepper.goToStep(index));

            stepBox.setOnMouseEntered(e -> stepBox.getStyleClass().add("hover"));
            stepBox.setOnMouseExited(e -> stepBox.getStyleClass().remove("hover"));
        }

        // Update styles based on status
        updateStepNodeStyles(stepBox, step);
        step.statusProperty().addListener((obs, old, newVal) -> updateStepNodeStyles(stepBox, step));

        return stepBox;
    }

    private StackPane createIndicator(StepperItem step, int index) {
        StackPane indicator = new StackPane();
        indicator.setMinSize(40, 40);
        indicator.setPrefSize(40, 40);
        indicator.setMaxSize(40, 40);
        indicator.getStyleClass().add("step-indicator");

        Circle circle = new Circle(20);
        circle.getStyleClass().add("step-circle");

        Label label = new Label();
        label.getStyleClass().add("step-label");
        label.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Update indicator content based on status
        updateIndicatorContent(step, index, label, circle);
        step.statusProperty().addListener((obs, old, newVal) ->
                updateIndicatorContent(step, index, label, circle));

        indicator.getChildren().addAll(circle, label);

        return indicator;
    }

    private void updateIndicatorContent(StepperItem step, int index, Label label, Circle circle) {
        StepStatus status = step.getStatus();

        circle.getStyleClass().removeAll("pending", "active", "completed", "error", "warning");
        circle.getStyleClass().add(status.getStyleClass());

        if (step.getIcon() != null && status == StepStatus.ACTIVE) {
            label.setGraphic(step.getIcon());
            label.setText("");
        } else if (status == StepStatus.COMPLETED) {
            label.setGraphic(null);
            label.setText("✓");
        } else if (status == StepStatus.ERROR) {
            label.setGraphic(null);
            label.setText("✕");
        } else if (status == StepStatus.WARNING) {
            label.setGraphic(null);
            label.setText("!");
        } else if (stepper.isShowStepNumbers()) {
            label.setGraphic(null);
            label.setText(String.valueOf(index + 1));
        } else {
            label.setGraphic(null);
            label.setText("");
        }
    }

    private void updateStepNodeStyles(VBox stepBox, StepperItem step) {
        stepBox.getStyleClass().removeAll("pending", "active", "completed", "error", "warning");
        stepBox.getStyleClass().add(step.getStatus().getStyleClass());
    }

    private Pane createConnector(StepperItem fromStep, StepperItem toStep, boolean isHorizontal) {
        Pane connectorPane = new Pane();
        connectorPane.getStyleClass().add("step-connector");

        Line line = new Line();
        line.getStyleClass().add("connector-line");
        line.setStrokeWidth(2);

        if (isHorizontal) {
            connectorPane.setMinWidth(30);
            connectorPane.setPrefWidth(30);
            line.setStartX(0);
            line.setStartY(20);
            line.setEndX(30);
            line.setEndY(20);
        } else {
            connectorPane.setMinHeight(30);
            connectorPane.setPrefHeight(30);
            line.setStartX(20);
            line.setStartY(0);
            line.setEndX(20);
            line.setEndY(30);
        }

        // Update connector color based on status
        updateConnectorStyle(line, fromStep);
        fromStep.statusProperty().addListener((obs, old, newVal) -> updateConnectorStyle(line, fromStep));

        connectorPane.getChildren().add(line);

        if (isHorizontal) {
            HBox.setHgrow(connectorPane, Priority.ALWAYS);
        }

        return connectorPane;
    }

    private void updateConnectorStyle(Line line, StepperItem step) {
        line.getStyleClass().removeAll("pending", "active", "completed", "error", "warning");
        line.getStyleClass().add(step.getStatus().getStyleClass());
    }

    private void updateContent() {
        contentContainer.getChildren().clear();

        if (!stepper.isShowContent()) {
            return;
        }

        int current = stepper.getCurrentStep();
        if (current >= 0 && current < stepper.getSteps().size()) {
            StepperItem step = stepper.getSteps().get(current);

            if (step.getContent() != null) {
                contentContainer.getChildren().add(step.getContent());

                if (stepper.isAnimated()) {
                    FadeTransition fade = new FadeTransition(Duration.millis(300), step.getContent());
                    fade.setFromValue(0);
                    fade.setToValue(1);
                    fade.play();
                }
            }
        }
    }
}
