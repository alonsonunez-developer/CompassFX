package com.compassfx.skins;

import com.compassfx.controls.CFXTimePicker;
import com.compassfx.enums.TimeFormat;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.time.LocalTime;

public class CFXTimePickerSkin extends SkinBase<CFXTimePicker> {

    private final CFXTimePicker timePicker;
    private final VBox container;
    private final HBox inputField;
    private final Label valueLabel;
    private final VBox clockPopup;
    private final Pane clockFace;

    private boolean clockVisible = false;
    private boolean selectingHour = true;
    private final Circle hourHand;
    private final Circle minuteHand;

    private static final double CLOCK_RADIUS = 120;
    private static final double CENTER_X = 150;
    private static final double CENTER_Y = 150;

    public CFXTimePickerSkin(CFXTimePicker timePicker) {
        super(timePicker);
        this.timePicker = timePicker;

        container = new VBox(5);
        container.setAlignment(Pos.TOP_LEFT);

        // Input field
        inputField = new HBox(10);
        inputField.setAlignment(Pos.CENTER_LEFT);
        inputField.setPadding(new Insets(12, 16, 12, 16));
        inputField.getStyleClass().add("time-input-field");
        inputField.setCursor(Cursor.HAND);

        // Clock icon
        Label clockIcon = new Label("🕐");
        clockIcon.setStyle("-fx-font-size: 18px;");

        // Value label
        valueLabel = new Label();
        valueLabel.getStyleClass().add("time-value-label");
        updateValueLabel();
        HBox.setHgrow(valueLabel, Priority.ALWAYS);

        // Dropdown arrow
        Label arrow = new Label("▼");
        arrow.getStyleClass().add("dropdown-arrow");

        inputField.getChildren().addAll(clockIcon, valueLabel, arrow);
        inputField.setOnMouseClicked(e -> toggleClock());

        // Clock popup
        clockPopup = new VBox(15);
        clockPopup.getStyleClass().add("clock-popup");
        clockPopup.setPadding(new Insets(20));
        clockPopup.setVisible(false);
        clockPopup.setManaged(false);
        clockPopup.setMaxHeight(0);

        // Time display
        HBox timeDisplay = createTimeDisplay();

        // Clock face
        clockFace = new Pane();
        clockFace.setMinSize(300, 300);
        clockFace.setPrefSize(300, 300);
        clockFace.getStyleClass().add("clock-face");

        // Clock circle
        Circle clockCircle = new Circle(CENTER_X, CENTER_Y, CLOCK_RADIUS);
        clockCircle.getStyleClass().add("clock-circle");
        clockFace.getChildren().add(clockCircle);

        // Center dot
        Circle centerDot = new Circle(CENTER_X, CENTER_Y, 6);
        centerDot.getStyleClass().add("clock-center");
        clockFace.getChildren().add(centerDot);

        // Hour/Minute hands (initially hidden, will be created dynamically)
        hourHand = new Circle(5);
        hourHand.getStyleClass().add("clock-hand");
        minuteHand = new Circle(5);
        minuteHand.getStyleClass().add("clock-hand");

        // Draw clock numbers
        drawClockNumbers();

        // AM/PM toggle (only for 12h format)
        HBox amPmToggle = createAmPmToggle();

        clockPopup.getChildren().addAll(timeDisplay, clockFace, amPmToggle);

        container.getChildren().addAll(inputField, clockPopup);
        getChildren().add(container);

        // Listen for value changes
        timePicker.valueProperty().addListener((obs, old, newVal) -> updateValueLabel());
        timePicker.timeFormatProperty().addListener((obs, old, newVal) -> {
            updateValueLabel();
            updateClockDisplay();
        });
    }

    private void updateValueLabel() {
        if (timePicker.getValue() != null) {
            valueLabel.setText(timePicker.getFormattedValue());
            valueLabel.getStyleClass().remove("prompt");
        } else {
            valueLabel.setText(timePicker.getPromptText());
            valueLabel.getStyleClass().add("prompt");
        }
    }

    private HBox createTimeDisplay() {
        HBox display = new HBox(5);
        display.setAlignment(Pos.CENTER);
        display.getStyleClass().add("time-display");

        Label hourLabel = new Label();
        hourLabel.textProperty().bind(timePicker.valueProperty().asString("%tH"));
        hourLabel.getStyleClass().add("time-digit");
        hourLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        hourLabel.setCursor(Cursor.HAND);
        hourLabel.setOnMouseClicked(e -> switchToHourSelection());

        Label separator = new Label(":");
        separator.getStyleClass().add("time-separator");
        separator.setFont(Font.font("System", FontWeight.BOLD, 36));

        Label minuteLabel = new Label();
        minuteLabel.textProperty().bind(timePicker.valueProperty().asString("%tM"));
        minuteLabel.getStyleClass().add("time-digit");
        minuteLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        minuteLabel.setCursor(Cursor.HAND);
        minuteLabel.setOnMouseClicked(e -> switchToMinuteSelection());

        display.getChildren().addAll(hourLabel, separator, minuteLabel);
        return display;
    }

    private void drawClockNumbers() {
        if (selectingHour) {
            // Draw hour numbers
            int maxHour = timePicker.getTimeFormat() == TimeFormat.HOUR_24 ? 24 : 12;
            for (int i = 1; i <= (maxHour == 24 ? 24 : 12); i++) {
                double angle = Math.toRadians((i * 30) - 90);
                double radius = CLOCK_RADIUS - 25;
                double x = CENTER_X + radius * Math.cos(angle);
                double y = CENTER_Y + radius * Math.sin(angle);

                StackPane numberPane = createClockNumber(i, x, y, true);
                clockFace.getChildren().add(numberPane);
            }
        } else {
            // Draw minute numbers (every 5 minutes)
            for (int i = 0; i < 60; i += 5) {
                double angle = Math.toRadians((i * 6) - 90);
                double radius = CLOCK_RADIUS - 25;
                double x = CENTER_X + radius * Math.cos(angle);
                double y = CENTER_Y + radius * Math.sin(angle);

                StackPane numberPane = createClockNumber(i, x, y, false);
                clockFace.getChildren().add(numberPane);
            }
        }
    }

    private StackPane createClockNumber(int number, double x, double y, boolean isHour) {
        StackPane pane = new StackPane();
        pane.setMinSize(32, 32);
        pane.setPrefSize(32, 32);
        pane.setLayoutX(x - 16);
        pane.setLayoutY(y - 16);
        pane.getStyleClass().add("clock-number");
        pane.setCursor(Cursor.HAND);

        Label label = new Label(String.valueOf(number == 0 ? (isHour ? 12 : 0) : number));
        label.getStyleClass().add("clock-number-label");
        label.setFont(Font.font("System", FontWeight.NORMAL, 14));

        Circle bg = new Circle(16);
        bg.getStyleClass().add("clock-number-bg");
        bg.setVisible(false);

        pane.getChildren().addAll(bg, label);

        pane.setOnMouseClicked(e -> {
            if (isHour) {
                LocalTime current = timePicker.getValue();
                int newHour = number;
                if (timePicker.getTimeFormat() == TimeFormat.HOUR_12 && timePicker.isPM() && newHour != 12) {
                    newHour += 12;
                } else if (timePicker.getTimeFormat() == TimeFormat.HOUR_12 && !timePicker.isPM() && newHour == 12) {
                    newHour = 0;
                }
                timePicker.setValue(LocalTime.of(newHour % 24, current != null ? current.getMinute() : 0));
                switchToMinuteSelection();
            } else {
                LocalTime current = timePicker.getValue();
                timePicker.setValue(LocalTime.of(current != null ? current.getHour() : 0, number));
                hideClock();
            }
        });

        pane.setOnMouseEntered(e -> bg.setVisible(true));
        pane.setOnMouseExited(e -> bg.setVisible(false));

        return pane;
    }

    private HBox createAmPmToggle() {
        HBox toggle = new HBox(10);
        toggle.setAlignment(Pos.CENTER);
        toggle.getStyleClass().add("ampm-toggle");

        Button amBtn = new Button("AM");
        amBtn.getStyleClass().add("ampm-button");
        amBtn.setOnAction(e -> setAmPm(false));

        Button pmBtn = new Button("PM");
        pmBtn.getStyleClass().add("ampm-button");
        pmBtn.setOnAction(e -> setAmPm(true));

        toggle.getChildren().addAll(amBtn, pmBtn);

        // Hide for 24h format
        toggle.visibleProperty().bind(timePicker.timeFormatProperty().isEqualTo(TimeFormat.HOUR_12));
        toggle.managedProperty().bind(toggle.visibleProperty());

        return toggle;
    }

    private void setAmPm(boolean pm) {
        LocalTime current = timePicker.getValue();
        if (current == null) return;

        int hour = current.getHour();
        if (pm && hour < 12) {
            timePicker.setValue(current.plusHours(12));
        } else if (!pm && hour >= 12) {
            timePicker.setValue(current.minusHours(12));
        }
    }

    private void switchToHourSelection() {
        selectingHour = true;
        updateClockDisplay();
    }

    private void switchToMinuteSelection() {
        selectingHour = false;
        updateClockDisplay();
    }

    private void updateClockDisplay() {
        clockFace.getChildren().removeIf(node -> node.getStyleClass().contains("clock-number"));
        drawClockNumbers();
    }

    private void toggleClock() {
        if (clockVisible) {
            hideClock();
        } else {
            showClock();
        }
    }

    private void showClock() {
        clockVisible = true;
        selectingHour = true;
        updateClockDisplay();

        clockPopup.setVisible(true);
        clockPopup.setManaged(true);

        if (timePicker.isAnimated()) {
            clockPopup.setOpacity(0);
            clockPopup.setScaleY(0.8);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(clockPopup.opacityProperty(), 0),
                            new KeyValue(clockPopup.scaleYProperty(), 0.8),
                            new KeyValue(clockPopup.maxHeightProperty(), 0)
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(clockPopup.opacityProperty(), 1),
                            new KeyValue(clockPopup.scaleYProperty(), 1.0),
                            new KeyValue(clockPopup.maxHeightProperty(), 500)
                    )
            );
            timeline.play();
        } else {
            clockPopup.setMaxHeight(Region.USE_COMPUTED_SIZE);
        }
    }

    private void hideClock() {
        clockVisible = false;

        if (timePicker.isAnimated()) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(clockPopup.opacityProperty(), 1),
                            new KeyValue(clockPopup.scaleYProperty(), 1.0)
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(clockPopup.opacityProperty(), 0),
                            new KeyValue(clockPopup.scaleYProperty(), 0.8),
                            new KeyValue(clockPopup.maxHeightProperty(), 0)
                    )
            );
            timeline.setOnFinished(e -> {
                clockPopup.setVisible(false);
                clockPopup.setManaged(false);
            });
            timeline.play();
        } else {
            clockPopup.setVisible(false);
            clockPopup.setManaged(false);
        }
    }
}
