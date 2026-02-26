package com.compassfx.controls;

import com.compassfx.enums.ChartColor;
import com.compassfx.models.SeriesData;
import com.compassfx.skins.CFXLineChartSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Line Chart - A Material Design inspired line chart
 */
public class CFXLineChart extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-line-chart";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-chart.css";

    // Properties
    private final ObservableList<SeriesData> series;
    private final ObjectProperty<ChartColor> colorScheme;
    private final BooleanProperty showLegend;
    private final BooleanProperty showGrid;
    private final BooleanProperty showPoints;
    private final BooleanProperty smooth;
    private final BooleanProperty animated;
    private final StringProperty title;
    private final StringProperty xAxisLabel;
    private final StringProperty yAxisLabel;

    public CFXLineChart() {
        this.series = FXCollections.observableArrayList();
        this.colorScheme = new SimpleObjectProperty<>(this, "colorScheme", ChartColor.MATERIAL);
        this.showLegend = new SimpleBooleanProperty(this, "showLegend", true);
        this.showGrid = new SimpleBooleanProperty(this, "showGrid", true);
        this.showPoints = new SimpleBooleanProperty(this, "showPoints", true);
        this.smooth = new SimpleBooleanProperty(this, "smooth", false);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.title = new SimpleStringProperty(this, "title", "");
        this.xAxisLabel = new SimpleStringProperty(this, "xAxisLabel", "");
        this.yAxisLabel = new SimpleStringProperty(this, "yAxisLabel", "");

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
        return new CFXLineChartSkin(this);
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

    public boolean isShowGrid() { return showGrid.get(); }
    public void setShowGrid(boolean showGrid) { this.showGrid.set(showGrid); }
    public BooleanProperty showGridProperty() { return showGrid; }

    public boolean isShowPoints() { return showPoints.get(); }
    public void setShowPoints(boolean showPoints) { this.showPoints.set(showPoints); }
    public BooleanProperty showPointsProperty() { return showPoints; }

    public boolean isSmooth() { return smooth.get(); }
    public void setSmooth(boolean smooth) { this.smooth.set(smooth); }
    public BooleanProperty smoothProperty() { return smooth; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    public String getXAxisLabel() { return xAxisLabel.get(); }
    public void setXAxisLabel(String xAxisLabel) { this.xAxisLabel.set(xAxisLabel); }
    public StringProperty xAxisLabelProperty() { return xAxisLabel; }

    public String getYAxisLabel() { return yAxisLabel.get(); }
    public void setYAxisLabel(String yAxisLabel) { this.yAxisLabel.set(yAxisLabel); }
    public StringProperty yAxisLabelProperty() { return yAxisLabel; }
}
