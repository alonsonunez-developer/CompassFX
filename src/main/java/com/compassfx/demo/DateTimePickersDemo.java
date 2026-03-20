package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXDatePicker;
import com.compassfx.controls.CFXTimePicker;
import com.compassfx.enums.TimeFormat;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimePickersDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // DATE PICKERS
        // ====================================
        Label dateLabel = new Label("Date Picker");
        dateLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox dateSection = new VBox(20);
        dateSection.setAlignment(Pos.TOP_LEFT);
        dateSection.setMaxWidth(600);

        // Standard Date Picker
        Label standardLabel = new Label("Standard Date Picker:");
        standardLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");

        CFXDatePicker datePicker1 = new CFXDatePicker();
        datePicker1.setPromptText("Select a date");
        datePicker1.setMaxWidth(Double.MAX_VALUE);

        // Date Picker with Range
        Label rangeLabel = new Label("Date Picker with Range (Next 30 days):");
        rangeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");

        CFXDatePicker datePicker2 = new CFXDatePicker(LocalDate.now());
        datePicker2.setMinDate(LocalDate.now());
        datePicker2.setMaxDate(LocalDate.now().plusDays(30));
        datePicker2.setMaxWidth(Double.MAX_VALUE);

        // Date Picker with Custom Format
        Label formatLabel = new Label("Custom Format (dd/MM/yyyy):");
        formatLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");

        CFXDatePicker datePicker3 = new CFXDatePicker();
        datePicker3.setFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        datePicker3.setMaxWidth(Double.MAX_VALUE);

        // Selected Date Display
        Label selectedDateLabel = new Label("Selected Date: None");
        selectedDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-padding: 10 0 0 0;");

        datePicker1.valueProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedDateLabel.setText("Selected Date: " + newVal.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")));
            }
        });

        dateSection.getChildren().addAll(
                standardLabel, datePicker1,
                rangeLabel, datePicker2,
                formatLabel, datePicker3,
                selectedDateLabel
        );

        // ====================================
        // TIME PICKERS
        // ====================================
        Label timeLabel = new Label("Time Picker");
        timeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox timeSection = new VBox(20);
        timeSection.setAlignment(Pos.TOP_LEFT);
        timeSection.setMaxWidth(600);

        // 12-Hour Format Time Picker
        Label hour12Label = new Label("12-Hour Format (Analog Clock):");
        hour12Label.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");

        CFXTimePicker timePicker1 = new CFXTimePicker();
        timePicker1.setTimeFormat(TimeFormat.HOUR_12);
        timePicker1.setPromptText("Select time (12h)");
        timePicker1.setMaxWidth(Double.MAX_VALUE);

        // 24-Hour Format Time Picker
        Label hour24Label = new Label("24-Hour Format:");
        hour24Label.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");

        CFXTimePicker timePicker2 = new CFXTimePicker(LocalTime.now());
        timePicker2.setTimeFormat(TimeFormat.HOUR_24);
        timePicker2.setMaxWidth(Double.MAX_VALUE);

        // Custom Time Picker
        Label customTimeLabel = new Label("Custom Initial Time (3:30 PM):");
        customTimeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500;");

        CFXTimePicker timePicker3 = new CFXTimePicker(LocalTime.of(15, 30));
        timePicker3.setTimeFormat(TimeFormat.HOUR_12);
        timePicker3.setMaxWidth(Double.MAX_VALUE);

        // Selected Time Display
        Label selectedTimeLabel = new Label("Selected Time: None");
        selectedTimeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-padding: 10 0 0 0;");

        timePicker1.valueProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedTimeLabel.setText("Selected Time: " + newVal.format(DateTimeFormatter.ofPattern("hh:mm a")));
            }
        });

        timeSection.getChildren().addAll(
                hour12Label, timePicker1,
                hour24Label, timePicker2,
                customTimeLabel, timePicker3,
                selectedTimeLabel
        );

        // ====================================
        // COMBINED EXAMPLE
        // ====================================
        Label combinedLabel = new Label("Complete DateTime Example");
        combinedLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        VBox combinedSection = new VBox(15);
        combinedSection.setAlignment(Pos.TOP_LEFT);
        combinedSection.setMaxWidth(600);
        combinedSection.setPadding(new Insets(20));
        combinedSection.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.2, 0, 2);"
        );

        Label appointmentLabel = new Label("Schedule Appointment");
        appointmentLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        Label dateFieldLabel = new Label("Date:");
        dateFieldLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXDatePicker appointmentDate = new CFXDatePicker(LocalDate.now().plusDays(1));
        appointmentDate.setMinDate(LocalDate.now());
        appointmentDate.setMaxWidth(Double.MAX_VALUE);

        Label timeFieldLabel = new Label("Time:");
        timeFieldLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        CFXTimePicker appointmentTime = new CFXTimePicker(LocalTime.of(9, 0));
        appointmentTime.setMaxWidth(Double.MAX_VALUE);

        Label appointmentSummary = new Label();
        appointmentSummary.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill: #2196F3; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 10 0 0 0;"
        );
        appointmentSummary.setWrapText(true);

        // Update summary
        Runnable updateSummary = () -> {
            if (appointmentDate.getValue() != null && appointmentTime.getValue() != null) {
                String dateStr = appointmentDate.getValue().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));
                String timeStr = appointmentTime.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));
                appointmentSummary.setText("Appointment scheduled for: " + dateStr + " at " + timeStr);
            }
        };

        appointmentDate.valueProperty().addListener((obs, old, newVal) -> updateSummary.run());
        appointmentTime.valueProperty().addListener((obs, old, newVal) -> updateSummary.run());
        updateSummary.run();

        combinedSection.getChildren().addAll(
                appointmentLabel,
                dateFieldLabel, appointmentDate,
                timeFieldLabel, appointmentTime,
                appointmentSummary
        );

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                dateLabel,
                dateSection,
                new Separator(),
                timeLabel,
                timeSection,
                new Separator(),
                combinedLabel,
                combinedSection
        );
    }

}
