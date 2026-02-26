package com.compassfx.skins;

import com.compassfx.controls.CFXRadarChart;
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

public class CFXRadarChartSkin extends SkinBase<CFXRadarChart> {

    private final CFXRadarChart chart;
    private final VBox container;
    private final Label titleLabel;
    private final Pane chartPane;
    private final FlowPane legend;
    private final Group chartGroup;

    private static final double SIZE = 350;
    private static final double PADDING = 60;

    public CFXRadarChartSkin(CFXRadarChart chart) {
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
        chartPane.setMinSize(SIZE, SIZE);
        chartPane.setPrefSize(SIZE, SIZE);

        chartGroup = new Group();
        chartPane.getChildren().add(chartGroup);

        legend = new FlowPane(10, 10);
        legend.setAlignment(Pos.CENTER);

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

        double centerX = SIZE / 2;
        double centerY = SIZE / 2;
        double radius = (SIZE - 2 * PADDING) / 2;

        int axisCount = chart.getSeries().get(0).getData().size();
        double angleStep = 360.0 / axisCount;

        // Draw grid
        if (chart.isShowGrid()) {
            for (int level = 1; level <= chart.getLevels(); level++) {
                Polygon gridPoly = new Polygon();
                double levelRadius = radius * level / chart.getLevels();

                for (int i = 0; i < axisCount; i++) {
                    double angle = Math.toRadians(i * angleStep - 90);
                    double x = centerX + levelRadius * Math.cos(angle);
                    double y = centerY + levelRadius * Math.sin(angle);
                    gridPoly.getPoints().addAll(x, y);
                }

                gridPoly.setFill(null);
                gridPoly.setStroke(Color.rgb(200, 200, 200, 0.5));
                gridPoly.setStrokeWidth(1);
                chartGroup.getChildren().add(gridPoly);
            }

            // Draw axes
            for (int i = 0; i < axisCount; i++) {
                double angle = Math.toRadians(i * angleStep - 90);
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle);

                Line axis = new Line(centerX, centerY, x, y);
                axis.setStroke(Color.rgb(150, 150, 150, 0.5));
                axis.setStrokeWidth(1);
                chartGroup.getChildren().add(axis);

                // Add label
                if (chart.isShowLabels()) {
                    ChartData firstData = chart.getSeries().get(0).getData().get(i);
                    Label label = new Label(firstData.getName());
                    label.setFont(Font.font("System", 10));

                    double labelX = centerX + (radius + 20) * Math.cos(angle);
                    double labelY = centerY + (radius + 20) * Math.sin(angle);
                    label.setLayoutX(labelX - 20);
                    label.setLayoutY(labelY - 10);
                    chartGroup.getChildren().add(label);
                }
            }
        }

        // Draw series
        double maxValue = chart.getSeries().stream()
                .flatMap(s -> s.getData().stream())
                .mapToDouble(ChartData::getValue)
                .max().orElse(100);

        for (int seriesIndex = 0; seriesIndex < chart.getSeries().size(); seriesIndex++) {
            SeriesData series = chart.getSeries().get(seriesIndex);
            Color color = series.getColor() != null ? series.getColor() :
                    ChartColorPalette.getColor(chart.getColorScheme(), seriesIndex);

            Polygon dataPolygon = new Polygon();

            for (int i = 0; i < series.getData().size(); i++) {
                ChartData data = series.getData().get(i);
                double angle = Math.toRadians(i * angleStep - 90);
                double dataRadius = radius * (data.getValue() / maxValue);
                double x = centerX + dataRadius * Math.cos(angle);
                double y = centerY + dataRadius * Math.sin(angle);
                dataPolygon.getPoints().addAll(x, y);
            }

            if (chart.isFilled()) {
                dataPolygon.setFill(Color.color(
                        color.getRed(), color.getGreen(), color.getBlue(), 0.3
                ));
            } else {
                dataPolygon.setFill(null);
            }
            dataPolygon.setStroke(color);
            dataPolygon.setStrokeWidth(2.5);
            chartGroup.getChildren().add(dataPolygon);

            Rectangle colorBox = new Rectangle(12, 12, color);
            Label nameLabel = new Label(series.getName());
            nameLabel.setFont(Font.font("System", 11));
            HBox legendItem = new HBox(8, colorBox, nameLabel);
            legendItem.setAlignment(Pos.CENTER_LEFT);
            legend.getChildren().add(legendItem);
        }
    }
}
