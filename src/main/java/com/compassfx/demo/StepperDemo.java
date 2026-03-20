package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXStepper;
import com.compassfx.enums.StepStatus;
import com.compassfx.enums.StepperOrientation;
import com.compassfx.enums.StepperVariant;
import com.compassfx.models.StepperItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class StepperDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // Basic Horizontal Stepper
        // ====================================
        Label basicLabel = new Label("Basic Horizontal Stepper");
        basicLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper basicStepper = new CFXStepper();
        basicStepper.addStep("Select Campaign", "Choose your marketing campaign");
        basicStepper.addStep("Create Ad", "Design your advertisement");
        basicStepper.addStep("Target Audience", "Define your target audience");
        basicStepper.addStep("Review & Launch", "Review and publish");
        basicStepper.setShowContent(false);

        HBox basicControls = createControls(basicStepper);

        // ====================================
        // Vertical Stepper
        // ====================================
        Label verticalLabel = new Label("Vertical Stepper");
        verticalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper verticalStepper = new CFXStepper();
        verticalStepper.setOrientation(StepperOrientation.VERTICAL);
        verticalStepper.addStep("Account Information", "Enter your personal details");
        verticalStepper.addStep("Billing Address", "Provide billing information");
        verticalStepper.addStep("Payment Method", "Select payment method");
        verticalStepper.addStep("Confirmation", "Review and confirm");
        verticalStepper.setShowContent(false);
        verticalStepper.setMaxWidth(500);

        HBox verticalControls = createControls(verticalStepper);

        // ====================================
        // Outlined Variant
        // ====================================
        Label outlinedLabel = new Label("Outlined Variant");
        outlinedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper outlinedStepper = new CFXStepper();
        outlinedStepper.setVariant(StepperVariant.OUTLINED);
        outlinedStepper.addStep("Step 1");
        outlinedStepper.addStep("Step 2");
        outlinedStepper.addStep("Step 3");
        outlinedStepper.addStep("Step 4");
        outlinedStepper.setShowContent(false);

        // ====================================
        // Dots Variant
        // ====================================
        Label dotsLabel = new Label("Dots Variant (Minimal)");
        dotsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper dotsStepper = new CFXStepper();
        dotsStepper.setVariant(StepperVariant.DOTS);
        dotsStepper.setShowStepNumbers(false);
        dotsStepper.addStep("Introduction");
        dotsStepper.addStep("Features");
        dotsStepper.addStep("Pricing");
        dotsStepper.addStep("Contact");
        dotsStepper.setShowContent(false);

        // ====================================
        // With Error and Warning
        // ====================================
        Label statusLabel = new Label("With Error and Warning States");
        statusLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper statusStepper = new CFXStepper();

        StepperItem step1 = new StepperItem("Completed Step");
        step1.setStatus(StepStatus.COMPLETED);

        StepperItem step2 = new StepperItem("Error Step", "Something went wrong");
        step2.setStatus(StepStatus.ERROR);

        StepperItem step3 = new StepperItem("Warning Step", "Please review");
        step3.setStatus(StepStatus.WARNING);

        StepperItem step4 = new StepperItem("Pending Step");

        statusStepper.getSteps().addAll(step1, step2, step3, step4);
        statusStepper.setCurrentStep(1);
        statusStepper.setShowContent(false);

        // ====================================
        // Non-Linear Stepper
        // ====================================
        Label nonLinearLabel = new Label("Non-Linear Stepper (Click Any Step)");
        nonLinearLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper nonLinearStepper = new CFXStepper();
        nonLinearStepper.setLinear(false);
        nonLinearStepper.addStep("Profile");
        nonLinearStepper.addStep("Preferences");
        nonLinearStepper.addStep("Notifications");
        nonLinearStepper.addStep("Security");
        nonLinearStepper.setShowContent(false);

        Label clickInfo = new Label("Click any step to jump to it");
        clickInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-style: italic;");

        // ====================================
        // Interactive Stepper with Content
        // ====================================
        Label interactiveLabel = new Label("Interactive Stepper with Content");
        interactiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper interactiveStepper = new CFXStepper();

        // Step 1: Personal Info
        StepperItem personalInfo = new StepperItem("Personal Info", "Enter your details");
        VBox personalContent = new VBox(10);
        personalContent.getChildren().addAll(
                new Label("Name:"),
                new TextField(),
                new Label("Email:"),
                new TextField()
        );
        personalInfo.setContent(personalContent);

        // Step 2: Address
        StepperItem address = new StepperItem("Address", "Enter your address");
        VBox addressContent = new VBox(10);
        addressContent.getChildren().addAll(
                new Label("Street:"),
                new TextField(),
                new Label("City:"),
                new TextField()
        );
        address.setContent(addressContent);

        // Step 3: Preferences
        StepperItem preferences = new StepperItem("Preferences", "Optional settings");
        preferences.setOptional(true);
        VBox preferencesContent = new VBox(10);
        preferencesContent.getChildren().addAll(
                new Label("Bio:"),
                new TextArea()
        );
        preferences.setContent(preferencesContent);

        // Step 4: Review
        StepperItem review = new StepperItem("Review", "Confirm your information");
        VBox reviewContent = new VBox(10);
        Label reviewLabel = new Label("Please review all information before submitting.");
        reviewLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        reviewLabel.setWrapText(true);
        reviewContent.getChildren().add(reviewLabel);
        review.setContent(reviewContent);

        interactiveStepper.getSteps().addAll(personalInfo, address, preferences, review);

        HBox interactiveControls = new HBox(10);
        interactiveControls.setAlignment(Pos.CENTER);

        CFXButton backBtn = new CFXButton("Back");
        backBtn.setOnAction(e -> interactiveStepper.previous());
        backBtn.disableProperty().bind(
                interactiveStepper.currentStepProperty().isEqualTo(0)
        );

        CFXButton nextBtn = new CFXButton("Next");
        nextBtn.setOnAction(e -> interactiveStepper.next());

        CFXButton completeBtn = new CFXButton("Complete Step");
        completeBtn.setOnAction(e -> interactiveStepper.completeCurrentStep());

        CFXButton resetBtn = new CFXButton("Reset");
        resetBtn.setOnAction(e -> interactiveStepper.reset());

        interactiveControls.getChildren().addAll(backBtn, nextBtn, completeBtn, resetBtn);

        Label statusLabelInteractive = new Label();
        statusLabelInteractive.setStyle("-fx-font-size: 14px; -fx-text-fill: #2196F3; -fx-padding: 10 0 0 0;");

        interactiveStepper.setOnStepChange(event -> {
            statusLabelInteractive.setText("Current Step: " + event.getStep().getTitle());
        });

        interactiveStepper.setOnStepComplete(event -> {
            System.out.println("Completed: " + event.getStep().getTitle());
        });

        VBox interactiveBox = new VBox(15);
        interactiveBox.setAlignment(Pos.TOP_CENTER);
        interactiveBox.getChildren().addAll(interactiveStepper, interactiveControls, statusLabelInteractive);

        // ====================================
        // With Icons
        // ====================================
        Label iconsLabel = new Label("Stepper with Custom Icons");
        iconsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXStepper iconsStepper = new CFXStepper();
        iconsStepper.setShowStepNumbers(false);

        StepperItem iconStep1 = new StepperItem("Planning");
        iconStep1.setIcon(new Circle(6, Color.web("#2196F3")));

        StepperItem iconStep2 = new StepperItem("Development");
        iconStep2.setIcon(new Circle(6, Color.web("#4CAF50")));

        StepperItem iconStep3 = new StepperItem("Testing");
        iconStep3.setIcon(new Circle(6, Color.web("#FF9800")));

        StepperItem iconStep4 = new StepperItem("Deployment");
        iconStep4.setIcon(new Circle(6, Color.web("#F44336")));

        iconsStepper.getSteps().addAll(iconStep1, iconStep2, iconStep3, iconStep4);
        iconsStepper.setShowContent(false);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicStepper,
                basicControls,
                new Separator(),
                outlinedLabel,
                outlinedStepper,
                new Separator(),
                dotsLabel,
                dotsStepper,
                new Separator(),
                statusLabel,
                statusStepper,
                new Separator(),
                nonLinearLabel,
                nonLinearStepper,
                clickInfo,
                new Separator(),
                iconsLabel,
                iconsStepper,
                new Separator(),
                verticalLabel,
                verticalStepper,
                verticalControls,
                new Separator(),
                interactiveLabel,
                interactiveBox
        );
    }


    private HBox createControls(CFXStepper stepper) {
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10, 0, 0, 0));

        CFXButton prevBtn = new CFXButton("Previous");
        prevBtn.setOnAction(e -> stepper.previous());
        prevBtn.disableProperty().bind(stepper.currentStepProperty().isEqualTo(0));

        CFXButton nextBtn = new CFXButton("Next");
        nextBtn.setOnAction(e -> stepper.next());
        nextBtn.disableProperty().bind(
                stepper.currentStepProperty().greaterThanOrEqualTo(stepper.getSteps().size() - 1)
        );

        controls.getChildren().addAll(prevBtn, nextBtn);
        return controls;
    }

}
