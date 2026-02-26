package com.compassfx.controls;

import com.compassfx.enums.ChartColor;
import com.compassfx.models.SeriesData;
import com.compassfx.skins.CFXRadarChartSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Radar Chart - A Material Design inspired radar/spider chart
 */
public class CFXRadarChart extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-radar-chart";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-chart.css";

    // Properties
    private final ObservableList<SeriesData> series;
    private final ObjectProperty<ChartColor> colorScheme;
    private final BooleanProperty showLegend;
    private final BooleanProperty showLabels;
    private final BooleanProperty showGrid;
    private final BooleanProperty filled;
    private final BooleanProperty animated;
    private final StringProperty title;
    private final IntegerProperty levels;

    public CFXRadarChart() {
        this.series = FXCollections.observableArrayList();
        this.colorScheme = new SimpleObjectProperty<>(this, "colorScheme", ChartColor.MATERIAL);
        this.showLegend = new SimpleBooleanProperty(this, "showLegend", true);
        this.showLabels = new SimpleBooleanProperty(this, "showLabels", true);
        this.showGrid = new SimpleBooleanProperty(this, "showGrid", true);
        this.filled = new SimpleBooleanProperty(this, "filled", true);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.title = new SimpleStringProperty(this, "title", "");
        this.levels = new SimpleIntegerProperty(this, "levels", 5);

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
        colorScheme.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(colorScheme.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXRadarChartSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addSeries(SeriesData seriesData) {
        series.add(seriesData);
    }

    // Getters and Setters
    public ObservableList<SeriesData> getSeries() { return series; }

    public ChartColor getColorScheme() { return colorScheme.get(); }
    public void setColorScheme(ChartColor colorScheme) { this.colorScheme.set(colorScheme); }
    public ObjectProperty<ChartColor> colorSchemeProperty() { return colorScheme; }

    public boolean isShowLegend() { return showLegend.get(); }
    public void setShowLegend(boolean showLegend) { this.showLegend.set(showLegend); }
    public BooleanProperty showLegendProperty() { return showLegend; }

    public boolean isShowLabels() { return showLabels.get(); }
    public void setShowLabels(boolean showLabels) { this.showLabels.set(showLabels); }
    public BooleanProperty showLabelsProperty() { return showLabels; }

    public boolean isShowGrid() { return showGrid.get(); }
    public void setShowGrid(boolean showGrid) { this.showGrid.set(showGrid); }
    public BooleanProperty showGridProperty() { return showGrid; }

    public boolean isFilled() { return filled.get(); }
    public void setFilled(boolean filled) { this.filled.set(filled); }
    public BooleanProperty filledProperty() { return filled; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    public int getLevels() { return levels.get(); }
    public void setLevels(int levels) { this.levels.set(levels); }
    public IntegerProperty levelsProperty() { return levels; }
}
