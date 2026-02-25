package com.compassfx.skins;

import com.compassfx.controls.CFXDonutChart;
import com.compassfx.models.ChartData;
import com.compassfx.utils.ChartColorPalette;
import javafx.animation.*;
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
import javafx.util.Duration;

public class CFXDonutChartSkin extends SkinBase<CFXDonutChart> {

    private final CFXDonutChart chart;
    private final VBox container;
    private final Label titleLabel;
    private final StackPane chartPane;
    private final FlowPane legend;
    private final Group donutGroup;
    private final Label centerLabel;

    private static final double DEFAULT_SIZE = 300;

    public CFXDonutChartSkin(CFXDonutChart chart) {
        super(chart);
        this.chart = chart;

        container = new VBox(15);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(20));

        titleLabel = new Label();
        titleLabel.textProperty().bind(chart.titleProperty());
        titleLabel.getStyleClass().add("chart-title");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        chartPane = new StackPane();
        chartPane.setMinSize(DEFAULT_SIZE, DEFAULT_SIZE);
        chartPane.setPrefSize(DEFAULT_SIZE, DEFAULT_SIZE);

        donutGroup = new Group();

        centerLabel = new Label();
        centerLabel.textProperty().bind(chart.centerTextProperty());
        centerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        centerLabel.getStyleClass().add("donut-center-label");

        chartPane.getChildren().addAll(donutGroup, centerLabel);

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

        chart.getData().addListener((ListChangeListener.Change<? extends ChartData> c) -> updateChart());
        chart.colorSchemeProperty().addListener((obs, old, newVal) -> updateChart());
        chart.innerRadiusProperty().addListener((obs, old, newVal) -> updateChart());

        updateChart();
    }

    private void updateChart() {
        donutGroup.getChildren().clear();
        legend.getChildren().clear();

        if (chart.getData().isEmpty()) return;

        double total = chart.getData().stream().mapToDouble(ChartData::getValue).sum();
        if (total == 0) return;

        double centerX = DEFAULT_SIZE / 2;
        double centerY = DEFAULT_SIZE / 2;
        double outerRadius = Math.min(DEFAULT_SIZE, DEFAULT_SIZE) / 2 - 20;
        double innerRadius = outerRadius * chart.getInnerRadius();

        double currentAngle = chart.getStartAngle();

        for (int i = 0; i < chart.getData().size(); i++) {
            ChartData data = chart.getData().get(i);
            double sweepAngle = (data.getValue() / total) * 360;

            Color color = data.getColor() != null ? data.getColor() :
                    ChartColorPalette.getColor(chart.getColorScheme(), i);

            Path donutSlice = createDonutSlice(centerX, centerY, innerRadius, outerRadius,
                    currentAngle, sweepAngle);
            donutSlice.setFill(color);
            donutSlice.setStroke(Color.WHITE);
            donutSlice.setStrokeWidth(2);

            donutGroup.getChildren().add(donutSlice);

            Circle colorBox = new Circle(6, color);
            double percentage = (data.getValue() / total) * 100;
            Label nameLabel = new Label(data.getName() + String.format(" (%.1f%%)", percentage));
            nameLabel.setFont(Font.font("System", 11));
            HBox legendItem = new HBox(8, colorBox, nameLabel);
            legendItem.setAlignment(Pos.CENTER_LEFT);
            legend.getChildren().add(legendItem);

            if (chart.isAnimated()) {
                donutSlice.setScaleX(0);
                donutSlice.setScaleY(0);
                ScaleTransition st = new ScaleTransition(Duration.millis(500 + i * 50), donutSlice);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            }

            currentAngle += sweepAngle;
        }
    }

    private Path createDonutSlice(double centerX, double centerY, double innerRadius,
                                  double outerRadius, double startAngle, double sweepAngle) {
        Path path = new Path();

        double startRad = Math.toRadians(startAngle);
        double endRad = Math.toRadians(startAngle + sweepAngle);

        double x1 = centerX + outerRadius * Math.cos(startRad);
        double y1 = centerY + outerRadius * Math.sin(startRad);
        double x2 = centerX + outerRadius * Math.cos(endRad);
        double y2 = centerY + outerRadius * Math.sin(endRad);
        double x3 = centerX + innerRadius * Math.cos(endRad);
        double y3 = centerY + innerRadius * Math.sin(endRad);
        double x4 = centerX + innerRadius * Math.cos(startRad);
        double y4 = centerY + innerRadius * Math.sin(startRad);

        boolean largeArc = sweepAngle > 180;

        path.getElements().addAll(
                new MoveTo(x1, y1),
                new ArcTo(outerRadius, outerRadius, 0, x2, y2, largeArc, true),
                new LineTo(x3, y3),
                new ArcTo(innerRadius, innerRadius, 0, x4, y4, largeArc, false),
                new ClosePath()
        );

        return path;
    }
}
