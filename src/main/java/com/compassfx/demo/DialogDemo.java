package com.compassfx.demo;

import com.compassfx.controls.*;
import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.DialogSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Demo application showcasing CFXDialog features
 */
public class DialogDemo {

    public void showDemo(Label title, VBox root) {
        // ===== BASIC DIALOGS SECTION =====
        Label basicLabel = new Label("Basic Dialogs");
        basicLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox basicButtons = new HBox(10);
        basicButtons.setAlignment(Pos.CENTER);

        // Simple alert dialog
        CFXButton simpleBtn = new CFXButton("Simple Alert");
        simpleBtn.setOnAction(e -> {
            Label content = new Label("This is a simple alert dialog with a message.");
            content.setWrapText(true);

            CFXButton okBtn = new CFXButton("OK");

            CFXDialog dialog = new CFXDialogBuilder()
                    .title("Alert")
                    .content(content)
                    .size(DialogSize.SMALL)
                    .actions(okBtn)
                    .build();

            okBtn.setOnAction(ev -> dialog.close());
            dialog.show();
        });

        // Confirmation dialog
        CFXButton confirmBtn = new CFXButton("Confirmation");
        confirmBtn.setOnAction(e -> {
            Label content = new Label("Are you sure you want to delete this item? This action cannot be undone.");
            content.setWrapText(true);

            CFXButton cancelBtn = new CFXButton("Cancel");
            cancelBtn.setVariant(ButtonVariant.TEXT);

            CFXButton deleteBtn = new CFXButton("Delete");
            deleteBtn.setColor(ButtonColor.ERROR);

            CFXDialog dialog = new CFXDialogBuilder()
                    .title("Confirm Delete")
                    .content(content)
                    .size(DialogSize.SMALL)
                    .actions(cancelBtn, deleteBtn)
                    .build();

            cancelBtn.setOnAction(ev -> dialog.close());
            deleteBtn.setOnAction(ev -> {
                System.out.println("Item deleted!");
                dialog.close();
            });

            dialog.show();
        });

        // Info dialog with icon
        CFXButton infoBtn = new CFXButton("Info with Icon");
        infoBtn.setOnAction(e -> {
            Circle icon = new Circle(20);
            icon.setFill(Color.rgb(33, 150, 243));

            Label content = new Label("This dialog includes an icon in the header for visual emphasis.");
            content.setWrapText(true);

            CFXButton gotItBtn = new CFXButton("Got it");

            CFXDialog dialog = new CFXDialogBuilder()
                    .title("Information")
                    .headerGraphic(icon)
                    .content(content)
                    .size(DialogSize.SMALL)
                    .actions(gotItBtn)
                    .build();

            gotItBtn.setOnAction(ev -> dialog.close());
            dialog.show();
        });

        basicButtons.getChildren().addAll(simpleBtn, confirmBtn, infoBtn);

        // ===== SIZE VARIANTS SECTION =====
        Label sizeLabel = new Label("Dialog Sizes");
        sizeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox sizeButtons = new HBox(10);
        sizeButtons.setAlignment(Pos.CENTER);

        // Small dialog
        CFXButton smallBtn = new CFXButton("Small");
        smallBtn.setOnAction(e -> showSizeDialog(DialogSize.SMALL));

        // Medium dialog
        CFXButton mediumBtn = new CFXButton("Medium");
        mediumBtn.setOnAction(e -> showSizeDialog(DialogSize.MEDIUM));

        // Large dialog
        CFXButton largeBtn = new CFXButton("Large");
        largeBtn.setOnAction(e -> showSizeDialog(DialogSize.LARGE));

        // Extra Large dialog
        CFXButton xlBtn = new CFXButton("Extra Large");
        xlBtn.setOnAction(e -> showSizeDialog(DialogSize.EXTRA_LARGE));

        sizeButtons.getChildren().addAll(smallBtn, mediumBtn, largeBtn, xlBtn);

        // ===== FORM DIALOG SECTION =====
        Label formLabel = new Label("Form Dialog");
        formLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        CFXButton formBtn = new CFXButton("Open Form Dialog");
        formBtn.setOnAction(e -> showFormDialog());

        // ===== OPTIONS SECTION =====
        Label optionsLabel = new Label("Dialog Options");
        optionsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox optionsButtons = new HBox(10);
        optionsButtons.setAlignment(Pos.CENTER);

        // No close button
        CFXButton noCloseBtn = new CFXButton("No Close Button");
        noCloseBtn.setOnAction(e -> {
            Label content = new Label("This dialog doesn't have a close button. You must click an action button.");
            content.setWrapText(true);

            CFXButton okBtn = new CFXButton("OK");

            CFXDialog dialog = new CFXDialogBuilder()
                    .title("Important")
                    .content(content)
                    .showCloseButton(false)
                    .actions(okBtn)
                    .build();

            okBtn.setOnAction(ev -> dialog.close());
            dialog.show();
        });

        // No backdrop close
        CFXButton noBackdropBtn = new CFXButton("No Backdrop Close");
        noBackdropBtn.setOnAction(e -> {
            Label content = new Label("This dialog won't close when clicking the backdrop. Use the buttons.");
            content.setWrapText(true);

            CFXButton closeBtn = new CFXButton("Close");

            CFXDialog dialog = new CFXDialogBuilder()
                    .title("Modal Dialog")
                    .content(content)
                    .closeOnBackdropClick(false)
                    .actions(closeBtn)
                    .build();

            closeBtn.setOnAction(ev -> dialog.close());
            dialog.show();
        });

        // Scrollable content
        CFXButton scrollableBtn = new CFXButton("Scrollable Content");
        scrollableBtn.setOnAction(e -> showScrollableDialog());

        optionsButtons.getChildren().addAll(noCloseBtn, noBackdropBtn, scrollableBtn);

        // ===== CUSTOM STYLED DIALOG =====
        Label customLabel = new Label("Custom Dialogs");
        customLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        HBox customButtons = new HBox(10);
        customButtons.setAlignment(Pos.CENTER);

        // Success dialog
        CFXButton successBtn = new CFXButton("Success Message");
        successBtn.setColor(ButtonColor.SUCCESS);
        successBtn.setOnAction(e -> showSuccessDialog());

        // Warning dialog
        CFXButton warningBtn = new CFXButton("Warning Message");
        warningBtn.setColor(ButtonColor.WARNING);
        warningBtn.setOnAction(e -> showWarningDialog());

        // Error dialog
        CFXButton errorBtn = new CFXButton("Error Message");
        errorBtn.setColor(ButtonColor.ERROR);
        errorBtn.setOnAction(e -> showErrorDialog());

        customButtons.getChildren().addAll(successBtn, warningBtn, errorBtn);

        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                basicLabel,
                basicButtons,
                new Separator(),
                sizeLabel,
                sizeButtons,
                new Separator(),
                formLabel,
                formBtn,
                new Separator(),
                optionsLabel,
                optionsButtons,
                new Separator(),
                customLabel,
                customButtons
        );
    }

    /**
     * Show dialog with different sizes
     */
    private void showSizeDialog(DialogSize size) {
        Label content = new Label("This is a " + size.toString().toLowerCase().replace("_", " ") +
                " dialog with width of " + (int)size.getWidth() + "px.");
        content.setWrapText(true);

        CFXButton closeBtn = new CFXButton("Close");

        CFXDialog dialog = new CFXDialogBuilder()
                .title(size.toString().replace("_", " ") + " Dialog")
                .content(content)
                .size(size)
                .actions(closeBtn)
                .build();

        closeBtn.setOnAction(e -> dialog.close());
        dialog.show();
    }

    /**
     * Show form dialog
     */
    private void showFormDialog() {
        VBox formContent = new VBox(15);
        formContent.setPadding(new Insets(10));

        CFXTextField nameField = new CFXTextField();
        nameField.setPromptText("Enter your name");

        CFXTextField emailField = new CFXTextField();
        emailField.setPromptText("Enter your email");

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Enter your message");
        messageArea.setPrefRowCount(4);
        messageArea.setWrapText(true);

        formContent.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Email:"), emailField,
                new Label("Message:"), messageArea
        );

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);

        CFXButton submitBtn = new CFXButton("Submit");

        CFXDialog dialog = new CFXDialogBuilder()
                .title("Contact Form")
                .content(formContent)
                .size(DialogSize.MEDIUM)
                .actions(cancelBtn, submitBtn)
                .build();

        cancelBtn.setOnAction(e -> dialog.close());
        submitBtn.setOnAction(e -> {
            System.out.println("Form submitted!");
            System.out.println("Name: " + nameField.getText());
            System.out.println("Email: " + emailField.getText());
            System.out.println("Message: " + messageArea.getText());
            dialog.close();
        });

        dialog.show();
    }

    /**
     * Show scrollable content dialog
     */
    private void showScrollableDialog() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        for (int i = 1; i <= 20; i++) {
            content.getChildren().add(new Label("Item " + i + ": This is a scrollable item with content."));
        }

        CFXButton closeBtn = new CFXButton("Close");

        CFXDialog dialog = new CFXDialogBuilder()
                .title("Scrollable Content")
                .content(content)
                .size(DialogSize.MEDIUM)
                .actions(closeBtn)
                .build();

        closeBtn.setOnAction(e -> dialog.close());
        dialog.show();
    }

    /**
     * Show success dialog
     */
    private void showSuccessDialog() {
        Circle icon = new Circle(25);
        icon.setFill(Color.rgb(76, 175, 80));

        Label content = new Label("Your changes have been saved successfully!");
        content.setWrapText(true);
        content.setStyle("-fx-font-size: 14px;");

        CFXButton okBtn = new CFXButton("OK");
        okBtn.setColor(ButtonColor.SUCCESS);

        CFXDialog dialog = new CFXDialogBuilder()
                .title("Success")
                .headerGraphic(icon)
                .content(content)
                .size(DialogSize.SMALL)
                .actions(okBtn)
                .build();

        okBtn.setOnAction(e -> dialog.close());
        dialog.show();
    }

    /**
     * Show warning dialog
     */
    private void showWarningDialog() {
        Circle icon = new Circle(25);
        icon.setFill(Color.rgb(255, 152, 0));

        Label content = new Label("This action may have unintended consequences. Please review before proceeding.");
        content.setWrapText(true);
        content.setStyle("-fx-font-size: 14px;");

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);

        CFXButton proceedBtn = new CFXButton("Proceed Anyway");
        proceedBtn.setColor(ButtonColor.WARNING);

        CFXDialog dialog = new CFXDialogBuilder()
                .title("Warning")
                .headerGraphic(icon)
                .content(content)
                .size(DialogSize.MEDIUM)
                .actions(cancelBtn, proceedBtn)
                .build();

        cancelBtn.setOnAction(e -> dialog.close());
        proceedBtn.setOnAction(e -> {
            System.out.println("User proceeded despite warning");
            dialog.close();
        });

        dialog.show();
    }

    /**
     * Show error dialog
     */
    private void showErrorDialog() {
        Circle icon = new Circle(25);
        icon.setFill(Color.rgb(244, 67, 54));

        VBox content = new VBox(10);
        Label message = new Label("An error occurred while processing your request.");
        message.setWrapText(true);
        message.setStyle("-fx-font-size: 14px; -fx-font-weight: 600;");

        Label details = new Label("Error code: ERR_NETWORK_TIMEOUT\nPlease check your connection and try again.");
        details.setWrapText(true);
        details.setStyle("-fx-font-size: 12px; -fx-text-fill: rgba(0, 0, 0, 0.6);");

        content.getChildren().addAll(message, details);

        CFXButton retryBtn = new CFXButton("Retry");
        retryBtn.setColor(ButtonColor.ERROR);

        CFXButton cancelBtn = new CFXButton("Cancel");
        cancelBtn.setVariant(ButtonVariant.TEXT);

        CFXDialog dialog = new CFXDialogBuilder()
                .title("Error")
                .headerGraphic(icon)
                .content(content)
                .size(DialogSize.MEDIUM)
                .actions(cancelBtn, retryBtn)
                .build();

        cancelBtn.setOnAction(e -> dialog.close());
        retryBtn.setOnAction(e -> {
            System.out.println("Retrying...");
            dialog.close();
        });

        dialog.show();
    }
}