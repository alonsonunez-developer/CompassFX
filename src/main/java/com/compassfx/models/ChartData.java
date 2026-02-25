package com.compassfx.models;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

/**
 * Represents a single data point in a chart
 */
public class ChartData {

    private final StringProperty name;
    private final DoubleProperty value;
    private final ObjectProperty<Color> color;
    private final ObjectProperty<Object> extraData;

    public ChartData(String name, double value) {
        this(name, value, null);
    }

    public ChartData(String name, double value, Color color) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.value = new SimpleDoubleProperty(this, "value", value);
        this.color = new SimpleObjectProperty<>(this, "color", color);
        this.extraData = new SimpleObjectProperty<>(this, "extraData", null);
    }

    // Name
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    // Value
    public double getValue() { return value.get(); }
    public void setValue(double value) { this.value.set(value); }
    public DoubleProperty valueProperty() { return value; }

    // Color
    public Color getColor() { return color.get(); }
    public void setColor(Color color) { this.color.set(color); }
    public ObjectProperty<Color> colorProperty() { return color; }

    // Extra data (for custom use)
    public Object getExtraData() { return extraData.get(); }
    public void setExtraData(Object extraData) { this.extraData.set(extraData); }
    public ObjectProperty<Object> extraDataProperty() { return extraData; }

    @Override
    public String toString() {
        return name.get() + ": " + value.get();
    }
}
