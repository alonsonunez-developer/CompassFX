package com.compassfx.controls;

import com.compassfx.enums.DatePickerVariant;
import com.compassfx.skins.CFXDatePickerSkin;
import javafx.beans.property.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * CompassFX Date Picker - A Material Design inspired date picker with calendar
 */
public class CFXDatePicker extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-date-picker";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-date-picker.css";

    // Properties
    private final ObjectProperty<LocalDate> value;
    private final ObjectProperty<LocalDate> minDate;
    private final ObjectProperty<LocalDate> maxDate;
    private final ObjectProperty<DatePickerVariant> variant;
    private final BooleanProperty showWeekNumbers;
    private final BooleanProperty animated;
    private final StringProperty promptText;
    private final ObjectProperty<DateTimeFormatter> formatter;

    public CFXDatePicker() {
        this(LocalDate.now());
    }

    public CFXDatePicker(LocalDate initialDate) {
        this.value = new SimpleObjectProperty<>(this, "value", initialDate);
        this.minDate = new SimpleObjectProperty<>(this, "minDate", null);
        this.maxDate = new SimpleObjectProperty<>(this, "maxDate", null);
        this.variant = new SimpleObjectProperty<>(this, "variant", DatePickerVariant.STANDARD);
        this.showWeekNumbers = new SimpleBooleanProperty(this, "showWeekNumbers", false);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.promptText = new SimpleStringProperty(this, "promptText", "Select date");
        this.formatter = new SimpleObjectProperty<>(this, "formatter", DateTimeFormatter.ofPattern("MMM dd, yyyy"));

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("INFO: Cargado stylesheet: " + STYLESHEET);
            } else {
                System.err.println("ERROR: No se pudo encontrar el recurso en: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
            e.printStackTrace();
        }

        updateStyleClasses();
        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(variant.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXDatePickerSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public String getFormattedValue() {
        if (value.get() == null) return "";
        return value.get().format(formatter.get());
    }

    public boolean isDateInRange(LocalDate date) {
        if (date == null) return false;
        if (minDate.get() != null && date.isBefore(minDate.get())) return false;
        if (maxDate.get() != null && date.isAfter(maxDate.get())) return false;
        return true;
    }

    // Getters and Setters
    public LocalDate getValue() { return value.get(); }
    public void setValue(LocalDate value) { this.value.set(value); }
    public ObjectProperty<LocalDate> valueProperty() { return value; }

    public LocalDate getMinDate() { return minDate.get(); }
    public void setMinDate(LocalDate minDate) { this.minDate.set(minDate); }
    public ObjectProperty<LocalDate> minDateProperty() { return minDate; }

    public LocalDate getMaxDate() { return maxDate.get(); }
    public void setMaxDate(LocalDate maxDate) { this.maxDate.set(maxDate); }
    public ObjectProperty<LocalDate> maxDateProperty() { return maxDate; }

    public DatePickerVariant getVariant() { return variant.get(); }
    public void setVariant(DatePickerVariant variant) { this.variant.set(variant); }
    public ObjectProperty<DatePickerVariant> variantProperty() { return variant; }

    public boolean isShowWeekNumbers() { return showWeekNumbers.get(); }
    public void setShowWeekNumbers(boolean showWeekNumbers) { this.showWeekNumbers.set(showWeekNumbers); }
    public BooleanProperty showWeekNumbersProperty() { return showWeekNumbers; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public String getPromptText() { return promptText.get(); }
    public void setPromptText(String promptText) { this.promptText.set(promptText); }
    public StringProperty promptTextProperty() { return promptText; }

    public DateTimeFormatter getFormatter() { return formatter.get(); }
    public void setFormatter(DateTimeFormatter formatter) { this.formatter.set(formatter); }
    public ObjectProperty<DateTimeFormatter> formatterProperty() { return formatter; }
}
