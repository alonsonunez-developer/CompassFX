package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXCheckbox;
import com.compassfx.controls.CFXRadioButton;
import com.compassfx.enums.CheckboxVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Demo application showcasing CFXCheckbox and CFXRadioButton features
 */
public class CheckboxRadioDemo extends Application {

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
        Label title = new Label("CompassFX Checkbox & RadioButton Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ===== CHECKBOX COLOR VARIANTS =====
        Label variantsLabel = new Label("Checkbox Color Variants");
        variantsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox variantsSection = new VBox(15);
        variantsSection.setAlignment(Pos.CENTER_LEFT);
        variantsSection.setMaxWidth(400);

        Label variantsDesc = new Label("Different colors for different contexts:");
        variantsDesc.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox defaultCheck = new CFXCheckbox("Default (Dark Gray)");
        defaultCheck.setVariant(CheckboxVariant.DEFAULT);
        defaultCheck.setSelected(true);

        CFXCheckbox primaryCheck = new CFXCheckbox("Primary (Blue) - Default");
        primaryCheck.setVariant(CheckboxVariant.PRIMARY);
        primaryCheck.setSelected(true);

        CFXCheckbox secondaryCheck = new CFXCheckbox("Secondary (Purple)");
        secondaryCheck.setVariant(CheckboxVariant.SECONDARY);
        secondaryCheck.setSelected(true);

        CFXCheckbox successCheck = new CFXCheckbox("Success (Green) - I agree");
        successCheck.setVariant(CheckboxVariant.SUCCESS);
        successCheck.setSelected(true);

        CFXCheckbox warningCheck = new CFXCheckbox("Warning (Orange) - Important");
        warningCheck.setVariant(CheckboxVariant.WARNING);
        warningCheck.setSelected(true);

        CFXCheckbox errorCheck = new CFXCheckbox("Error (Red) - Delete permanently");
        errorCheck.setVariant(CheckboxVariant.ERROR);
        errorCheck.setSelected(true);

        variantsSection.getChildren().addAll(
                variantsDesc,
                defaultCheck,
                primaryCheck,
                secondaryCheck,
                successCheck,
                warningCheck,
                errorCheck
        );

        // ===== CHECKBOXES SECTION =====
        Label checkboxLabel = new Label("Checkboxes");
        checkboxLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox checkboxSection = new VBox(15);
        checkboxSection.setAlignment(Pos.CENTER_LEFT);
        checkboxSection.setMaxWidth(400);

        // Basic checkboxes
        Label basicCheckboxLabel = new Label("Basic Checkboxes:");
        basicCheckboxLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox checkbox1 = new CFXCheckbox("Accept terms and conditions");
        CFXCheckbox checkbox2 = new CFXCheckbox("Subscribe to newsletter");
        CFXCheckbox checkbox3 = new CFXCheckbox("Enable notifications");
        checkbox3.setSelected(true);

        // Indeterminate checkbox
        Label indeterminateLabel = new Label("Indeterminate State:");
        indeterminateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox parentCheckbox = new CFXCheckbox("Select all items");
        CFXCheckbox childCheckbox1 = new CFXCheckbox("  Item 1");
        CFXCheckbox childCheckbox2 = new CFXCheckbox("  Item 2");
        CFXCheckbox childCheckbox3 = new CFXCheckbox("  Item 3");

        // Update parent checkbox based on children
        childCheckbox1.setSelected(true);
        updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3);

        childCheckbox1.selectedProperty().addListener((obs, oldVal, newVal) ->
                updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3));
        childCheckbox2.selectedProperty().addListener((obs, oldVal, newVal) ->
                updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3));
        childCheckbox3.selectedProperty().addListener((obs, oldVal, newVal) ->
                updateParentCheckbox(parentCheckbox, childCheckbox1, childCheckbox2, childCheckbox3));

        // Parent checkbox controls all children
        parentCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (!parentCheckbox.isIndeterminate()) {
                childCheckbox1.setSelected(newVal);
                childCheckbox2.setSelected(newVal);
                childCheckbox3.setSelected(newVal);
            }
        });

        // Disabled checkbox
        Label disabledCheckboxLabel = new Label("Disabled State:");
        disabledCheckboxLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXCheckbox disabledCheckbox1 = new CFXCheckbox("Disabled unchecked");
        disabledCheckbox1.setDisable(true);

        CFXCheckbox disabledCheckbox2 = new CFXCheckbox("Disabled checked");
        disabledCheckbox2.setSelected(true);
        disabledCheckbox2.setDisable(true);

        checkboxSection.getChildren().addAll(
                basicCheckboxLabel,
                checkbox1, checkbox2, checkbox3,
                new Separator(),
                indeterminateLabel,
                parentCheckbox,
                childCheckbox1, childCheckbox2, childCheckbox3,
                new Separator(),
                disabledCheckboxLabel,
                disabledCheckbox1, disabledCheckbox2
        );

        // ===== RADIO BUTTONS SECTION =====
        Label radioLabel = new Label("Radio Buttons");
        radioLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox radioSection = new VBox(15);
        radioSection.setAlignment(Pos.CENTER_LEFT);
        radioSection.setMaxWidth(400);

        // Basic radio buttons
        Label basicRadioLabel = new Label("Select Payment Method:");
        basicRadioLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        ToggleGroup paymentGroup = new ToggleGroup();

        CFXRadioButton creditCard = new CFXRadioButton("Credit Card");
        creditCard.setToggleGroup(paymentGroup);
        creditCard.setSelected(true);

        CFXRadioButton debitCard = new CFXRadioButton("Debit Card");
        debitCard.setToggleGroup(paymentGroup);

        CFXRadioButton paypal = new CFXRadioButton("PayPal");
        paypal.setToggleGroup(paymentGroup);

        CFXRadioButton bankTransfer = new CFXRadioButton("Bank Transfer");
        bankTransfer.setToggleGroup(paymentGroup);

        // Another radio group
        Label shippingLabel = new Label("Select Shipping Option:");
        shippingLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        ToggleGroup shippingGroup = new ToggleGroup();

        CFXRadioButton standard = new CFXRadioButton("Standard (5-7 days) - Free");
        standard.setToggleGroup(shippingGroup);
        standard.setSelected(true);

        CFXRadioButton express = new CFXRadioButton("Express (2-3 days) - $10");
        express.setToggleGroup(shippingGroup);

        CFXRadioButton overnight = new CFXRadioButton("Overnight (1 day) - $25");
        overnight.setToggleGroup(shippingGroup);

        // Disabled radio buttons
        Label disabledRadioLabel = new Label("Disabled State:");
        disabledRadioLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        ToggleGroup disabledGroup = new ToggleGroup();

        CFXRadioButton disabledRadio1 = new CFXRadioButton("Disabled unselected");
        disabledRadio1.setToggleGroup(disabledGroup);
        disabledRadio1.setDisable(true);

        CFXRadioButton disabledRadio2 = new CFXRadioButton("Disabled selected");
        disabledRadio2.setToggleGroup(disabledGroup);
        disabledRadio2.setSelected(true);
        disabledRadio2.setDisable(true);

        radioSection.getChildren().addAll(
                basicRadioLabel,
                creditCard, debitCard, paypal, bankTransfer,
                new Separator(),
                shippingLabel,
                standard, express, overnight,
                new Separator(),
                disabledRadioLabel,
                disabledRadio1, disabledRadio2
        );

        // ===== FORM EXAMPLE =====
        Label formLabel = new Label("Complete Form Example");
        formLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox formSection = new VBox(15);
        formSection.setAlignment(Pos.CENTER_LEFT);
        formSection.setMaxWidth(500);
        formSection.setPadding(new Insets(15));
        formSection.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 1px; -fx-border-radius: 8px;");

        Label formTitle = new Label("Account Preferences");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");

        // Notification preferences
        Label notifLabel = new Label("Notifications:");
        notifLabel.setStyle("-fx-font-weight: 500;");

        CFXCheckbox emailNotif = new CFXCheckbox("Email notifications");
        emailNotif.setSelected(true);

        CFXCheckbox pushNotif = new CFXCheckbox("Push notifications");

        CFXCheckbox smsNotif = new CFXCheckbox("SMS notifications");

        // Theme preference
        Label themeLabel = new Label("Theme:");
        themeLabel.setStyle("-fx-font-weight: 500;");

        ToggleGroup themeGroup = new ToggleGroup();

        CFXRadioButton lightTheme = new CFXRadioButton("Light");
        lightTheme.setToggleGroup(themeGroup);
        lightTheme.setSelected(true);

        CFXRadioButton darkTheme = new CFXRadioButton("Dark");
        darkTheme.setToggleGroup(themeGroup);

        CFXRadioButton autoTheme = new CFXRadioButton("Auto (system)");
        autoTheme.setToggleGroup(themeGroup);

        // Terms with success variant
        CFXCheckbox termsCheckbox = new CFXCheckbox("I agree to the terms and conditions");
        termsCheckbox.setVariant(CheckboxVariant.SUCCESS);

        // Submit button
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        CFXButton saveButton = new CFXButton("Save Preferences");
        saveButton.setOnAction(e -> {
            System.out.println("=== Preferences Saved ===");
            System.out.println("Email: " + emailNotif.isSelected());
            System.out.println("Push: " + pushNotif.isSelected());
            System.out.println("SMS: " + smsNotif.isSelected());
            System.out.println("Theme: " +
                    (lightTheme.isSelected() ? "Light" :
                            darkTheme.isSelected() ? "Dark" : "Auto"));
            System.out.println("Terms accepted: " + termsCheckbox.isSelected());
        });

        buttonBox.getChildren().add(saveButton);

        formSection.getChildren().addAll(
                formTitle,
                new Separator(),
                notifLabel,
                emailNotif, pushNotif, smsNotif,
                new Separator(),
                themeLabel,
                lightTheme, darkTheme, autoTheme,
                new Separator(),
                termsCheckbox,
                buttonBox
        );

        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                variantsLabel,
                variantsSection,
                new Separator(),
                checkboxLabel,
                checkboxSection,
                new Separator(),
                radioLabel,
                radioSection,
                new Separator(),
                formLabel,
                formSection
        );

        // Create scene and apply theme
        Scene scene = new Scene(scrollPane, 1200, 1800);
        CompassFX.applyLightTheme(scene);

        primaryStage.setTitle("CompassFX Checkbox & RadioButton Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Update parent checkbox based on child checkboxes
     */
    void updateParentCheckbox(CFXCheckbox parent, CFXCheckbox... children) {
        int selectedCount = 0;
        for (CFXCheckbox child : children) {
            if (child.isSelected()) {
                selectedCount++;
            }
        }

        if (selectedCount == 0) {
            parent.setIndeterminate(false);
            parent.setSelected(false);
        } else if (selectedCount == children.length) {
            parent.setIndeterminate(false);
            parent.setSelected(true);
        } else {
            parent.setIndeterminate(true);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}