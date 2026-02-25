package com.compassfx.skins;

import com.compassfx.controls.CFXBarChart;
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

public class CFXBarChartSkin extends SkinBase<CFXBarChart> {

    private final CFXBarChart chart;
    private final VBox container;
    private final Label titleLabel;
    private final Pane chartPane;
    private final FlowPane legend;
    private final Group chartGroup;

    private static final double CHART_WIDTH = 500;
    private static final double CHART_HEIGHT = 300;
    private static final double PADDING = 40;

    public CFXBarChartSkin(CFXBarChart chart) {
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

        double maxValue = chart.getSeries().stream()
                .flatMap(s -> s.getData().stream())
                .mapToDouble(ChartData::getValue)
                .max().orElse(100);

        int maxCategories = chart.getSeries().get(0).getData().size();
        int seriesCount = chart.getSeries().size();

        double availableWidth = CHART_WIDTH - 2 * PADDING;
        double categoryWidth = availableWidth / maxCategories;
        double barWidth = (categoryWidth - chart.getCategoryGap()) / seriesCount - chart.getBarGap();

        for (int seriesIndex = 0; seriesIndex < chart.getSeries().size(); seriesIndex++) {
            SeriesData series = chart.getSeries().get(seriesIndex);
            Color color = series.getColor() != null ? series.getColor() :
                    ChartColorPalette.getColor(chart.getColorScheme(), seriesIndex);

            for (int dataIndex = 0; dataIndex < series.getData().size(); dataIndex++) {
                ChartData data = series.getData().get(dataIndex);

                double barHeight = (data.getValue() / maxValue) * (CHART_HEIGHT - 2 * PADDING);
                double x = PADDING + dataIndex * categoryWidth + seriesIndex * (barWidth + chart.getBarGap());
                double y = CHART_HEIGHT - PADDING - barHeight;

                Rectangle bar = new Rectangle(x, y, barWidth, barHeight);
                bar.setFill(color);
                bar.setArcWidth(4);
                bar.setArcHeight(4);
                bar.getStyleClass().add("bar");

                chartGroup.getChildren().add(bar);
            }

            Rectangle colorBox = new Rectangle(12, 12, color);
            Label nameLabel = new Label(series.getName());
            nameLabel.setFont(Font.font("System", 11));
            HBox legendItem = new HBox(8, colorBox, nameLabel);
            legendItem.setAlignment(Pos.CENTER_LEFT);
            legend.getChildren().add(legendItem);
        }
    }
}
