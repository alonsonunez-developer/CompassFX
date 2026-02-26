package com.compassfx.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 * Represents a series of data points in a chart (for line/bar charts)
 */
public class SeriesData {

    private final StringProperty name;
    private final ObservableList<ChartData> data;
    private final ObjectProperty<Color> color;

    public SeriesData(String name) {
        this(name, null);
    }

    public SeriesData(String name, Color color) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.data = FXCollections.observableArrayList();
        this.color = new SimpleObjectProperty<>(this, "color", color);
    }

    // Name
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    // Data
    public ObservableList<ChartData> getData() { return data; }

    // Color
    public Color getColor() { return color.get(); }
    public void setColor(Color color) { this.color.set(color); }
    public ObjectProperty<Color> colorProperty() { return color; }

    // Helper methods
    public void addData(String name, double value) {
        data.add(new ChartData(name, value));
    }

    public void addData(ChartData chartData) {
        data.add(chartData);
    }

    @Override
    public String toString() {
        return name.get() + " (" + data.size() + " points)";
    }
}
