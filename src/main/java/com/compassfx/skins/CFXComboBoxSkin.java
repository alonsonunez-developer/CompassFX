package com.compassfx.skins;

import com.compassfx.controls.CFXComboBox;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Custom skin for CFXComboBox with Material Design floating label
 */
public class CFXComboBoxSkin<T> extends ComboBoxListViewSkin<T> {

    private final CFXComboBox<T> comboBox;
    private final VBox container;
    private final StackPane inputContainer;
    private final Label floatingLabel;
    private final Label helperLabel;
    private boolean isLabelFloating = false;

    public CFXComboBoxSkin(CFXComboBox<T> comboBox) {
        super(comboBox);
        this.comboBox = comboBox;

        // Create container structure
        this.container = new VBox(4);
        this.inputContainer = new StackPane();
        this.floatingLabel = new Label();
        this.helperLabel = new Label();

        setupComponents();
        setupListeners();
        updateFloatingLabel();
    }

    private void setupComponents() {
        // Setup floating label
        floatingLabel.getStyleClass().add("cfx-combobox-label");
        floatingLabel.textProperty().bind(comboBox.labelProperty());
        floatingLabel.setMouseTransparent(true);
        floatingLabel.setPadding(new Insets(0, 0, 0, 12));

        // Setup helper text
        helperLabel.getStyleClass().add("cfx-combobox-helper");
        helperLabel.managedProperty().bind(helperLabel.visibleProperty());

        // Bind helper/error text
        comboBox.errorProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal && !comboBox.getErrorText().isEmpty()) {
                helperLabel.setText(comboBox.getErrorText());
                helperLabel.getStyleClass().removeAll("cfx-combobox-helper");
                helperLabel.getStyleClass().add("cfx-combobox-error");
                helperLabel.setVisible(true);
            } else if (!comboBox.getHelperText().isEmpty()) {
                helperLabel.setText(comboBox.getHelperText());
                helperLabel.getStyleClass().removeAll("cfx-combobox-error");
                helperLabel.getStyleClass().add("cfx-combobox-helper");
                helperLabel.setVisible(true);
            } else {
                helperLabel.setVisible(false);
            }
        });

        comboBox.helperTextProperty().addListener((obs, oldVal, newVal) -> {
            if (!comboBox.isError()) {
                helperLabel.setText(newVal);
                helperLabel.setVisible(newVal != null && !newVal.isEmpty());
            }
        });
    }

    private void setupListeners() {
        // Float label on focus or selection
        comboBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
            updateFloatingLabel();
        });

        comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateFloatingLabel();
        });

        // Update label text
        comboBox.labelProperty().addListener((obs, oldVal, newVal) -> {
            floatingLabel.setText(newVal);
        });
    }

    private void updateFloatingLabel() {
        if (!comboBox.isFloatingLabel() || comboBox.getLabel().isEmpty()) {
            floatingLabel.setVisible(false);
            return;
        }

        floatingLabel.setVisible(true);
        boolean shouldFloat = comboBox.isFocused() ||
                comboBox.getValue() != null ||
                comboBox.getPromptText() != null;

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