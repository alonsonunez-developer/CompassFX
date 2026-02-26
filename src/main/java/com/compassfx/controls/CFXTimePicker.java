package com.compassfx.controls;

import com.compassfx.enums.TimeFormat;
import com.compassfx.enums.TimePickerVariant;
import com.compassfx.skins.CFXTimePickerSkin;
import javafx.beans.property.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * CompassFX Time Picker - A Material Design inspired time picker with analog clock
 */
public class CFXTimePicker extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-time-picker";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-time-picker.css";

    // Properties
    private final ObjectProperty<LocalTime> value;
    private final ObjectProperty<TimePickerVariant> variant;
    private final ObjectProperty<TimeFormat> timeFormat;
    private final BooleanProperty animated;
    private final StringProperty promptText;
    private final ObjectProperty<DateTimeFormatter> formatter;

    public CFXTimePicker() {
        this(LocalTime.now());
    }

    public CFXTimePicker(LocalTime initialTime) {
        this.value = new SimpleObjectProperty<>(this, "value", initialTime);
        this.variant = new SimpleObjectProperty<>(this, "variant", TimePickerVariant.ANALOG);
        this.timeFormat = new SimpleObjectProperty<>(this, "timeFormat", TimeFormat.HOUR_12);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.promptText = new SimpleStringProperty(this, "promptText", "Select time");
        this.formatter = new SimpleObjectProperty<>(this, "formatter", DateTimeFormatter.ofPattern("hh:mm a"));

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
        timeFormat.addListener((obs, oldVal, newVal) -> {
            updateStyleClasses();
            updateFormatter();
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(variant.get().getStyleClass());
        getStyleClass().add(timeFormat.get().getStyleClass());
    }

    private void updateFormatter() {
        if (timeFormat.get() == TimeFormat.HOUR_24) {
            formatter.set(DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            formatter.set(DateTimeFormatter.ofPattern("hh:mm a"));
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXTimePickerSkin(this);
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

    public int getHour12() {
        if (value.get() == null) return 12;
        int hour = value.get().getHour();
        if (hour == 0) return 12;
        if (hour > 12) return hour - 12;
        return hour;
    }

    public boolean isPM() {
        if (value.get() == null) return false;
        return value.get().getHour() >= 12;
    }

    // Getters and Setters
    public LocalTime getValue() { return value.get(); }
    public void setValue(LocalTime value) { this.value.set(value); }
    public ObjectProperty<LocalTime> valueProperty() { return value; }

    public TimePickerVariant getVariant() { return variant.get(); }
    public void setVariant(TimePickerVariant variant) { this.variant.set(variant); }
    public ObjectProperty<TimePickerVariant> variantProperty() { return variant; }

    public TimeFormat getTimeFormat() { return timeFormat.get(); }
    public void setTimeFormat(TimeFormat timeFormat) { this.timeFormat.set(timeFormat); }
    public ObjectProperty<TimeFormat> timeFormatProperty() { return timeFormat; }

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
