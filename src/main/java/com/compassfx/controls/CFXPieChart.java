package com.compassfx.controls;

import com.compassfx.enums.ChartColor;
import com.compassfx.models.ChartData;
import com.compassfx.skins.CFXPieChartSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Pie Chart - A Material Design inspired pie chart
 */
public class CFXPieChart extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-pie-chart";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-chart.css";

    // Properties
    private final ObservableList<ChartData> data;
    private final ObjectProperty<ChartColor> colorScheme;
    private final BooleanProperty showLegend;
    private final BooleanProperty showLabels;
    private final BooleanProperty animated;
    private final StringProperty title;
    private final DoubleProperty startAngle;

    public CFXPieChart() {
        this.data = FXCollections.observableArrayList();
        this.colorScheme = new SimpleObjectProperty<>(this, "colorScheme", ChartColor.MATERIAL);
        this.showLegend = new SimpleBooleanProperty(this, "showLegend", true);
        this.showLabels = new SimpleBooleanProperty(this, "showLabels", true);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.title = new SimpleStringProperty(this, "title", "");
        this.startAngle = new SimpleDoubleProperty(this, "startAngle", 0);

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
        return new CFXPieChartSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addData(String name, double value) {
        data.add(new ChartData(name, value));
    }

    public void addData(ChartData chartData) {
        data.add(chartData);
    }

    // Getters and Setters
    public ObservableList<ChartData> getData() { return data; }

    public ChartColor getColorScheme() { return colorScheme.get(); }
    public void setColorScheme(ChartColor colorScheme) { this.colorScheme.set(colorScheme); }
    public ObjectProperty<ChartColor> colorSchemeProperty() { return colorScheme; }

    public boolean isShowLegend() { return showLegend.get(); }
    public void setShowLegend(boolean showLegend) { this.showLegend.set(showLegend); }
    public BooleanProperty showLegendProperty() { return showLegend; }

    public boolean isShowLabels() { return showLabels.get(); }
    public void setShowLabels(boolean showLabels) { this.showLabels.set(showLabels); }
    public BooleanProperty showLabelsProperty() { return showLabels; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    public double getStartAngle() { return startAngle.get(); }
    public void setStartAngle(double startAngle) { this.startAngle.set(startAngle); }
    public DoubleProperty startAngleProperty() { return startAngle; }
}
