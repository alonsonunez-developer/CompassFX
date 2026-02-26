package com.compassfx.skins;

import com.compassfx.controls.CFXTextField;
import javafx.animation.TranslateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Custom skin for CFXTextField with Material Design floating label
 */
public class CFXTextFieldSkin extends TextFieldSkin {

    private final CFXTextField textField;
    private final VBox container;
    private final StackPane inputContainer;
    private final HBox iconContainer;
    private final Label floatingLabel;
    private final Label helperLabel;
    private boolean isLabelFloating = false;

    public CFXTextFieldSkin(CFXTextField textField) {
        super(textField);
        this.textField = textField;

        // Create container structure
        this.container = new VBox(4);
        this.inputContainer = new StackPane();
        this.iconContainer = new HBox(8);
        this.floatingLabel = new Label();
        this.helperLabel = new Label();

        setupComponents();
        setupListeners();
        updateFloatingLabel();
    }

    private void setupComponents() {
        // Setup floating label
        floatingLabel.getStyleClass().add("cfx-textfield-label");
        floatingLabel.textProperty().bind(textField.labelProperty());
        floatingLabel.setMouseTransparent(true);

        // Add required indicator
        textField.requiredProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal && !textField.getLabel().isEmpty()) {
                floatingLabel.setText(textField.getLabel() + " *");
            } else {
                floatingLabel.setText(textField.getLabel());
            }
        });

        // Setup helper text
        helperLabel.getStyleClass().add("cfx-textfield-helper");
        helperLabel.managedProperty().bind(helperLabel.visibleProperty());

        // Bind helper/error text
        textField.errorProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal && !textField.getErrorText().isEmpty()) {
                helperLabel.setText(textField.getErrorText());
                helperLabel.getStyleClass().removeAll("cfx-textfield-helper");
                helperLabel.getStyleClass().add("cfx-textfield-error");
                helperLabel.setVisible(true);
            } else if (!textField.getHelperText().isEmpty()) {
                helperLabel.setText(textField.getHelperText());
                helperLabel.getStyleClass().removeAll("cfx-textfield-error");
                helperLabel.getStyleClass().add("cfx-textfield-helper");
                helperLabel.setVisible(true);
            } else {
                helperLabel.setVisible(false);
            }
        });

        textField.helperTextProperty().addListener((obs, oldVal, newVal) -> {
            if (!textField.isError()) {
                helperLabel.setText(newVal);
                helperLabel.setVisible(newVal != null && !newVal.isEmpty());
            }
        });

        // Setup icon container
        iconContainer.setAlignment(Pos.CENTER_LEFT);
        iconContainer.setPickOnBounds(false);

        // Leading icon
        textField.leadingIconProperty().addListener((obs, oldVal, newVal) -> {
            iconContainer.getChildren().clear();
            if (newVal != null) {
                newVal.getStyleClass().add("cfx-textfield-leading-icon");
                iconContainer.getChildren().add(newVal);
            }
        });

        // Trailing icon
        textField.trailingIconProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                newVal.getStyleClass().add("cfx-textfield-trailing-icon");
                if (!iconContainer.getChildren().contains(newVal)) {
                    iconContainer.getChildren().add(newVal);
                }
            }
        });

        // Position label and icons
        inputContainer.getChildren().addAll(iconContainer, floatingLabel);
        StackPane.setAlignment(floatingLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(iconContainer, Pos.CENTER_LEFT);

        // Add padding for icons
        if (textField.getLeadingIcon() != null) {
            textField.setPadding(new Insets(8, 12, 8, 40));
        }
        if (textField.getTrailingIcon() != null) {
            textField.setPadding(new Insets(8, 40, 8, 12));
        }
    }

    private void setupListeners() {
        // Float label on focus or text input
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            updateFloatingLabel();
        });

        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateFloatingLabel();
        });

        // Update label text
        textField.labelProperty().addListener((obs, oldVal, newVal) -> {
            if (textField.isRequired()) {
                floatingLabel.setText(newVal + " *");
            } else {
                floatingLabel.setText(newVal);
            }
        });
    }

    private void updateFloatingLabel() {
        if (!textField.isFloatingLabel() || textField.getLabel().isEmpty()) {
            floatingLabel.setVisible(false);
            return;
        }

        floatingLabel.setVisible(true);
        boolean shouldFloat = textField.isFocused() ||
                !textField.getText().isEmpty() ||
                textField.getPromptText() != null;

        if (shouldFloat && !isLabelFloating) {
            // Float up
            animateLabel(true);
            isLabelFloating = true;
        } else if (!shouldFloat && isLabelFloating) {
            // Float down
            animateLabel(false);
            isLabelFloating = false;
        }
    }

    private void animateLabel(boolean floatUp) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(200), floatingLabel);
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), floatingLabel);

        if (floatUp) {
            translate.setToY(-24);
            scale.setToX(0.85);
            scale.setToY(0.85);
            floatingLabel.getStyleClass().add("floating");
        } else {
            translate.setToY(0);
            scale.setToX(1.0);
            scale.setToY(1.0);
            floatingLabel.getStyleClass().remove("floating");
        }

        translate.play();
        scale.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (container != null) {
            container.getChildren().clear();
        }
    }
}