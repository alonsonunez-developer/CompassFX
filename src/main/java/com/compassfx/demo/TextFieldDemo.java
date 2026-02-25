package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXTextField;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.TextFieldVariant;
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

/**
 * Demo application showcasing CFXTextField features
 */
public class TextFieldDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FFFFFF;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        // Title
        Label title = new Label("CompassFX TextField Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Outlined TextFields Section
        Label outlinedLabel = new Label("Outlined TextFields");
        outlinedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox outlinedFields = new VBox(20);
        outlinedFields.setAlignment(Pos.CENTER_LEFT);
        outlinedFields.setMaxWidth(400);

        CFXTextField outlined1 = new CFXTextField();
        outlined1.setVariant(TextFieldVariant.OUTLINED);
        outlined1.setLabel("Email");
        outlined1.setHelperText("Enter your email address");

        CFXTextField outlined2 = new CFXTextField();
        outlined2.setVariant(TextFieldVariant.OUTLINED);
        outlined2.setLabel("Password");
        outlined2.setHelperText("Must be at least 8 characters");

        CFXTextField outlined3 = new CFXTextField();
        outlined3.setVariant(TextFieldVariant.OUTLINED);
        outlined3.setLabel("Username");
        outlined3.setRequired(true);

        outlinedFields.getChildren().addAll(outlined1, outlined2, outlined3);

        // Filled TextFields Section
        Label filledLabel = new Label("Filled TextFields");
        filledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox filledFields = new VBox(20);
        filledFields.setAlignment(Pos.CENTER_LEFT);
        filledFields.setMaxWidth(400);

        CFXTextField filled1 = new CFXTextField();
        filled1.setVariant(TextFieldVariant.FILLED);
        filled1.setLabel("First Name");

        CFXTextField filled2 = new CFXTextField();
        filled2.setVariant(TextFieldVariant.FILLED);
        filled2.setLabel("Last Name");

        CFXTextField filled3 = new CFXTextField();
        filled3.setVariant(TextFieldVariant.FILLED);
        filled3.setLabel("Phone Number");
        filled3.setHelperText("+1 (555) 123-4567");

        filledFields.getChildren().addAll(filled1, filled2, filled3);

        // Error State Section
        Label errorLabel = new Label("Error State");
        errorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox errorFields = new VBox(20);
        errorFields.setAlignment(Pos.CENTER_LEFT);
        errorFields.setMaxWidth(400);

        CFXTextField errorField1 = new CFXTextField();
        errorField1.setVariant(TextFieldVariant.OUTLINED);
        errorField1.setLabel("Email");
        errorField1.setText("invalid-email");
        errorField1.setErrorText("Please enter a valid email address");

        CFXTextField errorField2 = new CFXTextField();
        errorField2.setVariant(TextFieldVariant.FILLED);
        errorField2.setLabel("Password");
        errorField2.setText("123");
        errorField2.setErrorText("Password must be at least 8 characters");

        errorFields.getChildren().addAll(errorField1, errorField2);

        // Disabled State Section
        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox disabledFields = new VBox(20);
        disabledFields.setAlignment(Pos.CENTER_LEFT);
        disabledFields.setMaxWidth(400);

        CFXTextField disabled1 = new CFXTextField();
        disabled1.setVariant(TextFieldVariant.OUTLINED);
        disabled1.setLabel("Disabled Field");
        disabled1.setText("This field is disabled");
        disabled1.setDisable(true);

        CFXTextField disabled2 = new CFXTextField();
        disabled2.setVariant(TextFieldVariant.FILLED);
        disabled2.setLabel("Disabled Filled");
        disabled2.setText("Cannot edit this");
        disabled2.setDisable(true);

        disabledFields.getChildren().addAll(disabled1, disabled2);

        // Form Example Section
        Label formLabel = new Label("Complete Form Example");
        formLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox formFields = new VBox(15);
        formFields.setAlignment(Pos.CENTER_LEFT);
        formFields.setMaxWidth(400);

        CFXTextField formName = new CFXTextField();
        formName.setVariant(TextFieldVariant.OUTLINED);
        formName.setLabel("Full Name");
        formName.setRequired(true);

        CFXTextField formEmail = new CFXTextField();
        formEmail.setVariant(TextFieldVariant.OUTLINED);
        formEmail.setLabel("Email Address");
        formEmail.setRequired(true);
        formEmail.setHelperText("We'll never share your email");

        CFXTextField formPhone = new CFXTextField();
        formPhone.setVariant(TextFieldVariant.OUTLINED);
        formPhone.setLabel("Phone Number");
        formPhone.setHelperText("Optional");

        CFXTextField formMessage = new CFXTextField();
        formMessage.setVariant(TextFieldVariant.OUTLINED);
        formMessage.setLabel("Message");
        formMessage.setPrefHeight(80);

        HBox formButtons = new HBox(10);
        formButtons.setAlignment(Pos.CENTER_RIGHT);

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);

        CFXButton submitBtn = new CFXButton("Submit");
        submitBtn.setOnAction(e -> {
            // Simple validation example
            boolean hasError = false;

            if (formName.getText().isEmpty()) {
                formName.setErrorText("Name is required");
                hasError = true;
            } else {
                formName.setErrorText("");
            }

            if (formEmail.getText().isEmpty()) {
                formEmail.setErrorText("Email is required");
                hasError = true;
            } else if (!formEmail.getText().contains("@")) {
                formEmail.setErrorText("Please enter a valid email");
                hasError = true;
            } else {
                formEmail.setErrorText("");
            }

            if (!hasError) {
                System.out.println("Form submitted!");
                System.out.println("Name: " + formName.getText());
                System.out.println("Email: " + formEmail.getText());
                System.out.println("Phone: " + formPhone.getText());
                System.out.println("Message: " + formMessage.getText());
            }
        });

        formButtons.getChildren().addAll(cancelBtn, submitBtn);
        formFields.getChildren().addAll(formName, formEmail, formPhone, formMessage, formButtons);

        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                outlinedLabel,
                outlinedFields,
                new Separator(),
                filledLabel,
                filledFields,
                new Separator(),
                errorLabel,
                errorFields,
                new Separator(),
                disabledLabel,
                disabledFields,
                new Separator(),
                formLabel,
                formFields
        );

        // Create scene and apply theme
        Scene scene = new Scene(scrollPane, 1200, 1800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX TextField Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}