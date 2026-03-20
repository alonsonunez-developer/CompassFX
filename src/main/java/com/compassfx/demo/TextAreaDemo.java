package com.compassfx.demo;

import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXCheckbox;
import com.compassfx.controls.CFXTextArea;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.TextAreaVariant;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TextAreaDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // Auto-Resize Example
        // ====================================
        Label autoResizeLabel = new Label("Auto-Resize TextArea");
        autoResizeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTextArea autoResizeArea = new CFXTextArea();
        autoResizeArea.setPromptText("Type here and watch it grow...");
        autoResizeArea.setPrefWidth(600);
        autoResizeArea.setAutoResize(true);
        autoResizeArea.setMinRows(2);
        autoResizeArea.setMaxRows(8);
        autoResizeArea.setVariant(TextAreaVariant.OUTLINED);

        Label autoInfo = new Label("Min: 2 rows, Max: 8 rows - Try typing multiple lines!");
        autoInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        // Controls for auto-resize
        HBox autoControls = new HBox(20);
        autoControls.setAlignment(Pos.CENTER);

        CFXCheckbox enableAutoResize = new CFXCheckbox("Enable Auto-Resize");
        enableAutoResize.setSelected(true);
        enableAutoResize.selectedProperty().bindBidirectional(autoResizeArea.autoResizeProperty());

        CFXButton clearBtn1 = new CFXButton("Clear");
        clearBtn1.setVariant(ButtonVariant.TEXT);
        clearBtn1.setOnAction(e -> autoResizeArea.clear());

        autoControls.getChildren().addAll(enableAutoResize, clearBtn1);

        // ====================================
        // Fixed Size Example
        // ====================================
        Label fixedLabel = new Label("Fixed Size TextArea");
        fixedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTextArea fixedArea = new CFXTextArea();
        fixedArea.setPromptText("This textarea has a fixed size (5 rows)");
        fixedArea.setPrefWidth(600);
        fixedArea.setPrefRowCount(5);
        fixedArea.setVariant(TextAreaVariant.FILLED);
        fixedArea.setAutoResize(false);

        Label fixedInfo = new Label("Fixed at 5 rows - scroll appears when needed");
        fixedInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        CFXButton clearBtn2 = new CFXButton("Clear");
        clearBtn2.setVariant(ButtonVariant.TEXT);
        clearBtn2.setOnAction(e -> fixedArea.clear());

        // ====================================
        // Variants Comparison
        // ====================================
        Label variantsLabel = new Label("Style Variants");
        variantsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        HBox variantsBox = new HBox(20);
        variantsBox.setAlignment(Pos.CENTER);

        // Outlined
        VBox outlinedBox = new VBox(10);
        outlinedBox.setAlignment(Pos.TOP_CENTER);
        Label outlinedLabel = new Label("OUTLINED");
        outlinedLabel.setStyle("-fx-font-weight: 600;");
        CFXTextArea outlinedArea = new CFXTextArea();
        outlinedArea.setPromptText("Outlined variant");
        outlinedArea.setPrefWidth(280);
        outlinedArea.setPrefRowCount(4);
        outlinedArea.setVariant(TextAreaVariant.OUTLINED);
        outlinedBox.getChildren().addAll(outlinedLabel, outlinedArea);

        // Filled
        VBox filledBox = new VBox(10);
        filledBox.setAlignment(Pos.TOP_CENTER);
        Label filledLabel = new Label("FILLED");
        filledLabel.setStyle("-fx-font-weight: 600;");
        CFXTextArea filledArea = new CFXTextArea();
        filledArea.setPromptText("Filled variant");
        filledArea.setPrefWidth(280);
        filledArea.setPrefRowCount(4);
        filledArea.setVariant(TextAreaVariant.FILLED);
        filledBox.getChildren().addAll(filledLabel, filledArea);

        variantsBox.getChildren().addAll(outlinedBox, filledBox);

        // ====================================
        // Error State Example
        // ====================================
        Label errorLabel = new Label("Error State");
        errorLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTextArea errorArea = new CFXTextArea();
        errorArea.setPromptText("This field has an error");
        errorArea.setPrefWidth(600);
        errorArea.setPrefRowCount(3);
        errorArea.setVariant(TextAreaVariant.OUTLINED);
        errorArea.setError(true);
        errorArea.setText("This text contains an error!");

        Label errorInfo = new Label("Error message: Please correct the text above");
        errorInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #F44336; -fx-font-weight: 500;");

        CFXButton toggleError = new CFXButton("Toggle Error");
        toggleError.setVariant(ButtonVariant.OUTLINED);
        toggleError.setOnAction(e -> errorArea.setError(!errorArea.isError()));

        // ====================================
        // Character Counter Example
        // ====================================
        Label counterLabel = new Label("With Character Counter");
        counterLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTextArea counterArea = new CFXTextArea();
        counterArea.setPromptText("Max 200 characters");
        counterArea.setPrefWidth(600);
        counterArea.setPrefRowCount(4);
        counterArea.setVariant(TextAreaVariant.OUTLINED);

        Label charCount = new Label("0 / 200");
        charCount.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        counterArea.textProperty().addListener((obs, old, newText) -> {
            int length = newText != null ? newText.length() : 0;
            charCount.setText(length + " / 200");

            if (length > 200) {
                charCount.setStyle("-fx-font-size: 12px; -fx-text-fill: #F44336; -fx-font-weight: 600;");
                counterArea.setError(true);
            } else {
                charCount.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
                counterArea.setError(false);
            }
        });

        HBox counterBox = new HBox();
        counterBox.setAlignment(Pos.CENTER_RIGHT);
        counterBox.getChildren().add(charCount);

        // ====================================
        // Multi-line with Auto-Resize
        // ====================================
        Label multiLabel = new Label("Comment Box (Auto-Resize)");
        multiLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXTextArea commentArea = new CFXTextArea();
        commentArea.setPromptText("Write your comment here...\nIt will grow as you type!");
        commentArea.setPrefWidth(600);
        commentArea.setAutoResize(true);
        commentArea.setMinRows(3);
        commentArea.setMaxRows(12);
        commentArea.setVariant(TextAreaVariant.FILLED);

        CFXButton submitBtn = new CFXButton("Submit Comment");
        submitBtn.setVariant(ButtonVariant.CONTAINED);
        submitBtn.setOnAction(e -> {
            System.out.println("Comment: " + commentArea.getText());
            commentArea.clear();
        });

        // Add all to root
        root.getChildren().addAll(
                title,
                new Separator(),
                autoResizeLabel,
                autoResizeArea,
                autoInfo,
                autoControls,
                new Separator(),
                fixedLabel,
                fixedArea,
                fixedInfo,
                clearBtn2,
                new Separator(),
                variantsLabel,
                variantsBox,
                new Separator(),
                errorLabel,
                errorArea,
                errorInfo,
                toggleError,
                new Separator(),
                counterLabel,
                counterArea,
                counterBox,
                new Separator(),
                multiLabel,
                commentArea,
                submitBtn
        );
    }
}