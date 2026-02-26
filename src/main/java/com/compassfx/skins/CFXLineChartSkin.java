package com.compassfx.skins;

import com.compassfx.controls.CFXLineChart;
import com.compassfx.models.ChartData;
import com.compassfx.models.SeriesData;
import com.compassfx.utils.ChartColorPalette;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CFXLineChartSkin extends SkinBase<CFXLineChart> {

    private final CFXLineChart chart;
    private final VBox container;
    private final Label titleLabel;
    private final Pane chartPane;
    private final FlowPane legend;
    private final Group chartGroup;

    private static final double CHART_WIDTH = 500;
    private static final double CHART_HEIGHT = 300;
    private static final double PADDING = 40;

    public CFXLineChartSkin(CFXLineChart chart) {
        super(chart);
        this.chart = chart;

        container = new VBox(15);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(20));

        titleLabel = new Label();
        titleLabel.textProperty().bind(chart.titleProperty());
        titleLabel.getStyleClass().add("chart-title");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        chartPane = new Pane();
        chartPane.setMinSize(CHART_WIDTH, CHART_HEIGHT);
        chartPane.setPrefSize(CHART_WIDTH, CHART_HEIGHT);

        chartGroup = new Group();
        chartPane.getChildren().add(chartGroup);

        legend = new FlowPane(10, 10);
        legend.setAlignment(Pos.CENTER);
        legend.getStyleClass().add("chart-legend");

        if (chart.getTitle() != null && !chart.getTitle().isEmpty()) {
            container.getChildren().add(titleLabel);
        }
        container.getChildren().add(chartPane);
        if (chart.isShowLegend()) {
            container.getChildren().add(legend);
        }

        getChildren().add(container);

        chart.getSeries().addListener((ListChangeListener.Change<? extends SeriesData> c) -> updateChart());
        chart.colorSchemeProperty().addListener((obs, old, newVal) -> updateChart());

        updateChart();
    }

    private void updateChart() {
        chartGroup.getChildren().clear();
        legend.getChildren().clear();

        if (chart.getSeries().isEmpty()) return;

        // Draw grid
        if (chart.isShowGrid()) {
            drawGrid();
        }

        // Find min/max values
        double maxValue = chart.getSeries().stream()
                .flatMap(s -> s.getData().stream())
                .mapToDouble(ChartData::getValue)
                .max().orElse(100);

        double minValue = chart.getSeries().stream()
                .flatMap(s -> s.getData().stream())
                .mapToDouble(ChartData::getValue)
                .min().orElse(0);

        int maxDataPoints = chart.getSeries().stream()
                .mapToInt(s -> s.getData().size())
                .max().orElse(1);

        double xStep = (CHART_WIDTH - 2 * PADDING) / Math.max(1, maxDataPoints - 1);
        double yRange = maxValue - minValue;
        double yScale = (CHART_HEIGHT - 2 * PADDING) / (yRange == 0 ? 1 : yRange);

        // Draw each series
        for (int seriesIndex = 0; seriesIndex < chart.getSeries().size(); seriesIndex++) {
            SeriesData series = chart.getSeries().get(seriesIndex);
            Color color = series.getColor() != null ? series.getColor() :
                    ChartColorPalette.getColor(chart.getColorScheme(), seriesIndex);

            if (series.getData().isEmpty()) continue;

            Path line = new Path();

            for (int i = 0; i < series.getData().size(); i++) {
                ChartData data = series.getData().get(i);
                double x = PADDING + i * xStep;
                double y = CHART_HEIGHT - PADDING - (data.getValue() - minValue) * yScale;

                if (i == 0) {
                    line.getElements().add(new MoveTo(x, y));
                } else {
                    line.getElements().add(new LineTo(x, y));
                }

                // Draw point
                if (chart.isShowPoints()) {
                    Circle point = new Circle(x, y, 4);
                    point.setFill(color);
                    point.setStroke(Color.WHITE);
                    point.setStrokeWidth(2);
                    chartGroup.getChildren().add(point);
                }
            }

            line.setStroke(color);
            line.setStrokeWidth(2.5);
            line.setFill(null);
            chartGroup.getChildren().add(0, line);

            // Add to legend
            Rectangle colorBox = new Rectangle(12, 12, color);
            Label nameLabel = new Label(series.getName());
            nameLabel.setFont(Font.font("System", 11));
            HBox legendItem = new HBox(8, colorBox, nameLabel);
            legendItem.setAlignment(Pos.CENTER_LEFT);
            legend.getChildren().add(legendItem);
        }
    }

    private void drawGrid() {
        for (int i = 0; i <= 5; i++) {
            double y = PADDING + i * (CHART_HEIGHT - 2 * PADDING) / 5;
            Line gridLine = new Line(PADDING, y, CHART_WIDTH - PADDING, y);
            gridLine.setStroke(Color.rgb(200, 200, 200, 0.5));
            gridLine.setStrokeWidth(1);
            gridLine.getStyleClass().add("grid-line");
            chartGroup.getChildren().add(gridLine);
        }
    }
}
