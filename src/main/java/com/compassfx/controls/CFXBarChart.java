package com.compassfx.controls;

import com.compassfx.enums.ChartColor;
import com.compassfx.models.SeriesData;
import com.compassfx.skins.CFXBarChartSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Bar Chart - A Material Design inspired bar chart
 */
public class CFXBarChart extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-bar-chart";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-chart.css";

    // Properties
    private final ObservableList<SeriesData> series;
    private final ObjectProperty<ChartColor> colorScheme;
    private final BooleanProperty showLegend;
    private final BooleanProperty showGrid;
    private final BooleanProperty showValues;
    private final BooleanProperty horizontal;
    private final BooleanProperty animated;
    private final StringProperty title;
    private final StringProperty xAxisLabel;
    private final StringProperty yAxisLabel;
    private final DoubleProperty barGap;
    private final DoubleProperty categoryGap;

    public CFXBarChart() {
        this.series = FXCollections.observableArrayList();
        this.colorScheme = new SimpleObjectProperty<>(this, "colorScheme", ChartColor.MATERIAL);
        this.showLegend = new SimpleBooleanProperty(this, "showLegend", true);
        this.showGrid = new SimpleBooleanProperty(this, "showGrid", true);
        this.showValues = new SimpleBooleanProperty(this, "showValues", false);
        this.horizontal = new SimpleBooleanProperty(this, "horizontal", false);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.title = new SimpleStringProperty(this, "title", "");
        this.xAxisLabel = new SimpleStringProperty(this, "xAxisLabel", "");
        this.yAxisLabel = new SimpleStringProperty(this, "yAxisLabel", "");
        this.barGap = new SimpleDoubleProperty(this, "barGap", 4);
        this.categoryGap = new SimpleDoubleProperty(this, "categoryGap", 10);

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
        horizontal.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(colorScheme.get().getStyleClass());

        if (horizontal.get()) {
            getStyleClass().add("horizontal");
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXBarChartSkin(this);
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

    public boolean isShowValues() { return showValues.get(); }
    public void setShowValues(boolean showValues) { this.showValues.set(showValues); }
    public BooleanProperty showValuesProperty() { return showValues; }

    public boolean isHorizontal() { return horizontal.get(); }
    public void setHorizontal(boolean horizontal) { this.horizontal.set(horizontal); }
    public BooleanProperty horizontalProperty() { return horizontal; }

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

    public double getBarGap() { return barGap.get(); }
    public void setBarGap(double barGap) { this.barGap.set(barGap); }
    public DoubleProperty barGapProperty() { return barGap; }

    public double getCategoryGap() { return categoryGap.get(); }
    public void setCategoryGap(double categoryGap) { this.categoryGap.set(categoryGap); }
    public DoubleProperty categoryGapProperty() { return categoryGap; }
}
