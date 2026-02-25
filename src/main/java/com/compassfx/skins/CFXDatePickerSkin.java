package com.compassfx.skins;

import com.compassfx.controls.CFXDatePicker;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class CFXDatePickerSkin extends SkinBase<CFXDatePicker> {

    private final CFXDatePicker datePicker;
    private final VBox container;
    private final HBox inputField;
    private final Label valueLabel;
    private final VBox calendarPopup;
    private final GridPane calendarGrid;
    private Label monthYearLabel;

    private YearMonth currentYearMonth;
    private boolean calendarVisible = false;

    public CFXDatePickerSkin(CFXDatePicker datePicker) {
        super(datePicker);
        this.datePicker = datePicker;
        this.currentYearMonth = YearMonth.from(datePicker.getValue() != null ?
                datePicker.getValue() : LocalDate.now());

        container = new VBox(5);
        container.setAlignment(Pos.TOP_LEFT);

        // Input field
        inputField = new HBox(10);
        inputField.setAlignment(Pos.CENTER_LEFT);
        inputField.setPadding(new Insets(12, 16, 12, 16));
        inputField.getStyleClass().add("date-input-field");
        inputField.setCursor(Cursor.HAND);

        // Calendar icon
        Label calendarIcon = new Label("📅");
        calendarIcon.setStyle("-fx-font-size: 18px;");

        // Value label
        valueLabel = new Label();
        valueLabel.getStyleClass().add("date-value-label");
        updateValueLabel();
        HBox.setHgrow(valueLabel, Priority.ALWAYS);

        // Dropdown arrow
        Label arrow = new Label("▼");
        arrow.getStyleClass().add("dropdown-arrow");

        inputField.getChildren().addAll(calendarIcon, valueLabel, arrow);
        inputField.setOnMouseClicked(e -> toggleCalendar());

        // Calendar popup
        calendarPopup = new VBox(10);
        calendarPopup.getStyleClass().add("calendar-popup");
        calendarPopup.setPadding(new Insets(15));
        calendarPopup.setVisible(false);
        calendarPopup.setManaged(false);
        calendarPopup.setMaxHeight(0);

        // Calendar header
        HBox calendarHeader = createCalendarHeader();

        // Day names
        GridPane dayNamesGrid = createDayNamesGrid();

        // Calendar grid
        calendarGrid = new GridPane();
        calendarGrid.setHgap(5);
        calendarGrid.setVgap(5);
        calendarGrid.getStyleClass().add("calendar-grid");

        calendarPopup.getChildren().addAll(calendarHeader, dayNamesGrid, calendarGrid);

        container.getChildren().addAll(inputField, calendarPopup);
        getChildren().add(container);

        // Update calendar
        updateCalendar();

        // Listen for value changes
        datePicker.valueProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                currentYearMonth = YearMonth.from(newVal);
                updateValueLabel();
                updateCalendar();
            }
        });
    }

    private void updateValueLabel() {
        if (datePicker.getValue() != null) {
            valueLabel.setText(datePicker.getFormattedValue());
            valueLabel.getStyleClass().remove("prompt");
        } else {
            valueLabel.setText(datePicker.getPromptText());
            valueLabel.getStyleClass().add("prompt");
        }
    }

    private HBox createCalendarHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(5, 0, 10, 0));

        // Previous month button
        Button prevBtn = new Button("◀");
        prevBtn.getStyleClass().add("nav-button");
        prevBtn.setOnAction(e -> changeMonth(-1));

        // Month/Year label
        monthYearLabel = new Label();
        monthYearLabel.getStyleClass().add("month-year-label");
        monthYearLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        HBox.setHgrow(monthYearLabel, Priority.ALWAYS);
        monthYearLabel.setAlignment(Pos.CENTER);
        monthYearLabel.setMaxWidth(Double.MAX_VALUE);

        // Next month button
        Button nextBtn = new Button("▶");
        nextBtn.getStyleClass().add("nav-button");
        nextBtn.setOnAction(e -> changeMonth(1));

        header.getChildren().addAll(prevBtn, monthYearLabel, nextBtn);
        updateMonthYearLabel();

        return header;
    }

    private GridPane createDayNamesGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.getStyleClass().add("day-names-grid");

        String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(dayNames[i]);
            dayLabel.getStyleClass().add("day-name-label");
            dayLabel.setFont(Font.font("System", FontWeight.BOLD, 11));
            dayLabel.setMinWidth(40);
            dayLabel.setAlignment(Pos.CENTER);
            grid.add(dayLabel, i, 0);
        }

        return grid;
    }

    private void updateCalendar() {
        calendarGrid.getChildren().clear();

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7; // Sunday = 0
        int daysInMonth = currentYearMonth.lengthOfMonth();

        LocalDate today = LocalDate.now();
        LocalDate selectedDate = datePicker.getValue();

        int row = 0;
        int col = dayOfWeek;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentYearMonth.atDay(day);
            StackPane dayCell = createDayCell(date, date.equals(today), date.equals(selectedDate));
            calendarGrid.add(dayCell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        updateMonthYearLabel();
    }

    private StackPane createDayCell(LocalDate date, boolean isToday, boolean isSelected) {
        StackPane cell = new StackPane();
        cell.setMinSize(40, 40);
        cell.setPrefSize(40, 40);
        cell.getStyleClass().add("day-cell");
        cell.setCursor(Cursor.HAND);

        Label dayLabel = new Label(String.valueOf(date.getDayOfMonth()));
        dayLabel.getStyleClass().add("day-label");

        if (isToday) {
            Circle todayIndicator = new Circle(18);
            todayIndicator.getStyleClass().add("today-indicator");
            cell.getChildren().add(todayIndicator);
        }

        if (isSelected) {
            Circle selectedIndicator = new Circle(18);
            selectedIndicator.getStyleClass().add("selected-indicator");
            cell.getChildren().add(selectedIndicator);
            dayLabel.getStyleClass().add("selected-day");
        }

        cell.getChildren().add(dayLabel);

        // Disable if out of range
        if (!datePicker.isDateInRange(date)) {
            cell.setDisable(true);
            cell.setOpacity(0.3);
        }

        // Click handler
        cell.setOnMouseClicked(e -> {
            datePicker.setValue(date);
            hideCalendar();
        });

        // Hover effect
        cell.setOnMouseEntered(e -> {
            if (!cell.isDisabled() && !isSelected) {
                cell.getStyleClass().add("hover");
            }
        });

        cell.setOnMouseExited(e -> {
            cell.getStyleClass().remove("hover");
        });

        return cell;
    }

    private void changeMonth(int delta) {
        currentYearMonth = currentYearMonth.plusMonths(delta);

        if (datePicker.isAnimated()) {
            FadeTransition fade = new FadeTransition(Duration.millis(150), calendarGrid);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> {
                updateCalendar();
                FadeTransition fadeIn = new FadeTransition(Duration.millis(150), calendarGrid);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fade.play();
        } else {
            updateCalendar();
        }
    }

    private void updateMonthYearLabel() {
        String month = currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        monthYearLabel.setText(month + " " + currentYearMonth.getYear());
    }

    private void toggleCalendar() {
        if (calendarVisible) {
            hideCalendar();
        } else {
            showCalendar();
        }
    }

    private void showCalendar() {
        calendarVisible = true;
        calendarPopup.setVisible(true);
        calendarPopup.setManaged(true);

        if (datePicker.isAnimated()) {
            calendarPopup.setOpacity(0);
            calendarPopup.setScaleY(0.8);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(calendarPopup.opacityProperty(), 0),
                            new KeyValue(calendarPopup.scaleYProperty(), 0.8),
                            new KeyValue(calendarPopup.maxHeightProperty(), 0)
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(calendarPopup.opacityProperty(), 1),
                            new KeyValue(calendarPopup.scaleYProperty(), 1.0),
                            new KeyValue(calendarPopup.maxHeightProperty(), 400)
                    )
            );
            timeline.play();
        } else {
            calendarPopup.setMaxHeight(Region.USE_COMPUTED_SIZE);
        }
    }

    private void hideCalendar() {
        calendarVisible = false;

        if (datePicker.isAnimated()) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(calendarPopup.opacityProperty(), 1),
                            new KeyValue(calendarPopup.scaleYProperty(), 1.0)
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(calendarPopup.opacityProperty(), 0),
                            new KeyValue(calendarPopup.scaleYProperty(), 0.8),
                            new KeyValue(calendarPopup.maxHeightProperty(), 0)
                    )
            );
            timeline.setOnFinished(e -> {
                calendarPopup.setVisible(false);
                calendarPopup.setManaged(false);
            });
            timeline.play();
        } else {
            calendarPopup.setVisible(false);
            calendarPopup.setManaged(false);
        }
    }
}
