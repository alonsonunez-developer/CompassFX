package com.compassfx.skins;

import com.compassfx.controls.CFXPieChart;
import com.compassfx.models.ChartData;
import com.compassfx.utils.ChartColorPalette;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class CFXPieChartSkin extends SkinBase<CFXPieChart> {

    private final CFXPieChart chart;
    private final VBox container;
    private final Label titleLabel;
    private final Pane chartPane;
    private final FlowPane legend;
    private final Group pieGroup;

    private static final double DEFAULT_SIZE = 300;
    private static final double HOVER_SCALE = 1.05;

    public CFXPieChartSkin(CFXPieChart chart) {
        super(chart);
        this.chart = chart;

        // Container
        container = new VBox(15);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(20));

        // Title
        titleLabel = new Label();
        titleLabel.textProperty().bind(chart.titleProperty());
        titleLabel.getStyleClass().add("chart-title");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        // Chart pane
        chartPane = new Pane();
        chartPane.setMinSize(DEFAULT_SIZE, DEFAULT_SIZE);
        chartPane.setPrefSize(DEFAULT_SIZE, DEFAULT_SIZE);

        // Pie group
        pieGroup = new Group();
        chartPane.getChildren().add(pieGroup);

        // Legend
        legend = new FlowPane(10, 10);
        legend.setAlignment(Pos.CENTER);
        legend.getStyleClass().add("chart-legend");

        // Add to container
        if (chart.getTitle() != null && !chart.getTitle().isEmpty()) {
            container.getChildren().add(titleLabel);
        }
        container.getChildren().add(chartPane);
        if (chart.isShowLegend()) {
            container.getChildren().add(legend);
        }

        getChildren().add(container);

        // Listen for data changes
        chart.getData().addListener((ListChangeListener.Change<? extends ChartData> c) -> {
            updateChart();
        });

        // Listen for property changes
        chart.titleProperty().addListener((obs, old, newVal) -> {
            if (newVal != null && !newVal.isEmpty() && !container.getChildren().contains(titleLabel)) {
                container.getChildren().add(0, titleLabel);
            } else if ((newVal == null || newVal.isEmpty()) && container.getChildren().contains(titleLabel)) {
                container.getChildren().remove(titleLabel);
            }
        });

        chart.showLegendProperty().addListener((obs, old, newVal) -> {
            if (newVal && !container.getChildren().contains(legend)) {
                container.getChildren().add(legend);
            } else if (!newVal && container.getChildren().contains(legend)) {
                container.getChildren().remove(legend);
            }
        });

        chart.colorSchemeProperty().addListener((obs, old, newVal) -> updateChart());
        chart.showLabelsProperty().addListener((obs, old, newVal) -> updateChart());

        updateChart();
    }

    private void updateChart() {
        pieGroup.getChildren().clear();
        legend.getChildren().clear();

        if (chart.getData().isEmpty()) {
            return;
        }

        // Calculate total
        double total = chart.getData().stream()
                .mapToDouble(ChartData::getValue)
                .sum();

        if (total == 0) {
            return;
        }

        double centerX = DEFAULT_SIZE / 2;
        double centerY = DEFAULT_SIZE / 2;
        double radius = Math.min(DEFAULT_SIZE, DEFAULT_SIZE) / 2 - 20;

        double currentAngle = chart.getStartAngle();

        for (int i = 0; i < chart.getData().size(); i++) {
            ChartData data = chart.getData().get(i);
            double value = data.getValue();
            double sweepAngle = (value / total) * 360;

            // Get color
            Color color = data.getColor() != null ?
                    data.getColor() :
                    ChartColorPalette.getColor(chart.getColorScheme(), i);

            // Create arc
            Arc arc = new Arc(centerX, centerY, radius, radius, currentAngle, sweepAngle);
            arc.setType(ArcType.ROUND);
            arc.setFill(color);
            arc.setStroke(Color.WHITE);
            arc.setStrokeWidth(2);
            arc.getStyleClass().add("pie-slice");

            // Add hover effect
            arc.setOnMouseEntered(e -> {
                arc.setScaleX(HOVER_SCALE);
                arc.setScaleY(HOVER_SCALE);
                arc.setEffect(new javafx.scene.effect.DropShadow(10, Color.rgb(0, 0, 0, 0.3)));
            });

            arc.setOnMouseExited(e -> {
                arc.setScaleX(1.0);
                arc.setScaleY(1.0);
                arc.setEffect(null);
            });

            pieGroup.getChildren().add(arc);

            // Add label if enabled
            if (chart.isShowLabels() && sweepAngle > 15) {
                double labelAngle = Math.toRadians(currentAngle + sweepAngle / 2);
                double labelRadius = radius * 0.7;
                double labelX = centerX + labelRadius * Math.cos(labelAngle);
                double labelY = centerY + labelRadius * Math.sin(labelAngle);

                double percentage = (value / total) * 100;
                Label label = new Label(String.format("%.1f%%", percentage));
                label.setTextFill(Color.WHITE);
                label.setFont(Font.font("System", FontWeight.BOLD, 12));
                label.setLayoutX(labelX - 20);
                label.setLayoutY(labelY - 10);
                label.setMouseTransparent(true);
                label.getStyleClass().add("pie-label");

                pieGroup.getChildren().add(label);
            }

            // Add to legend
            HBox legendItem = createLegendItem(data.getName(), color, value, total);
            legend.getChildren().add(legendItem);

            // Animate if enabled
            if (chart.isAnimated()) {
                arc.setLength(0);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(arc.lengthProperty(), 0)),
                        new KeyFrame(Duration.millis(500 + i * 50), new KeyValue(arc.lengthProperty(), sweepAngle))
                );
                timeline.play();
            }

            currentAngle += sweepAngle;
        }
    }

    private HBox createLegendItem(String name, Color color, double value, double total) {
        Circle colorBox = new Circle(6);
        colorBox.setFill(color);

        double percentage = (value / total) * 100;
        Label nameLabel = new Label(name + String.format(" (%.1f%%)", percentage));
        nameLabel.setFont(Font.font("System", 11));
        nameLabel.getStyleClass().add("legend-label");

        HBox item = new HBox(8, colorBox, nameLabel);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("legend-item");

        return item;
    }
}
