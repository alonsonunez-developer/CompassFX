package com.compassfx.demo;

import com.compassfx.controls.*;
import com.compassfx.enums.ChartColor;
import com.compassfx.models.SeriesData;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChartsDemo {

    public void showDemo(Label title, VBox content) {
        // ====================================
        // PIE CHART
        // ====================================
        Label pieLabel = new Label("Pie Chart");
        pieLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox pieBox = new HBox(20);
        pieBox.setAlignment(Pos.CENTER);

        CFXPieChart pieChart1 = new CFXPieChart();
        pieChart1.setTitle("Market Share");
        pieChart1.setColorScheme(ChartColor.MATERIAL);
        pieChart1.addData("Product A", 35);
        pieChart1.addData("Product B", 25);
        pieChart1.addData("Product C", 20);
        pieChart1.addData("Product D", 15);
        pieChart1.addData("Others", 5);

        CFXPieChart pieChart2 = new CFXPieChart();
        pieChart2.setTitle("Revenue Distribution");
        pieChart2.setColorScheme(ChartColor.PASTEL);
        pieChart2.setShowLabels(false);
        pieChart2.addData("Q1", 28);
        pieChart2.addData("Q2", 32);
        pieChart2.addData("Q3", 25);
        pieChart2.addData("Q4", 15);

        pieBox.getChildren().addAll(pieChart1, pieChart2);

        // ====================================
        // DONUT CHART
        // ====================================
        Label donutLabel = new Label("Donut Chart");
        donutLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        HBox donutBox = new HBox(20);
        donutBox.setAlignment(Pos.CENTER);

        CFXDonutChart donutChart1 = new CFXDonutChart();
        donutChart1.setTitle("Project Status");
        donutChart1.setCenterText("100%");
        donutChart1.setColorScheme(ChartColor.VIBRANT);
        donutChart1.addData("Completed", 60);
        donutChart1.addData("In Progress", 25);
        donutChart1.addData("Pending", 15);

        CFXDonutChart donutChart2 = new CFXDonutChart();
        donutChart2.setTitle("Budget Allocation");
        donutChart2.setCenterText("$500K");
        donutChart2.setColorScheme(ChartColor.MATERIAL);
        donutChart2.setInnerRadius(0.7);
        donutChart2.addData("Development", 200);
        donutChart2.addData("Marketing", 150);
        donutChart2.addData("Sales", 100);
        donutChart2.addData("Operations", 50);

        donutBox.getChildren().addAll(donutChart1, donutChart2);

        // ====================================
        // LINE CHART
        // ====================================
        Label lineLabel = new Label("Line Chart");
        lineLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXLineChart lineChart = new CFXLineChart();
        lineChart.setTitle("Monthly Sales Trend");
        lineChart.setColorScheme(ChartColor.MATERIAL);
        lineChart.setShowGrid(true);
        lineChart.setShowPoints(true);

        SeriesData series1 = new SeriesData("Product A");
        series1.addData("Jan", 65);
        series1.addData("Feb", 59);
        series1.addData("Mar", 80);
        series1.addData("Apr", 81);
        series1.addData("May", 56);
        series1.addData("Jun", 55);

        SeriesData series2 = new SeriesData("Product B");
        series2.addData("Jan", 28);
        series2.addData("Feb", 48);
        series2.addData("Mar", 40);
        series2.addData("Apr", 19);
        series2.addData("May", 86);
        series2.addData("Jun", 27);

        SeriesData series3 = new SeriesData("Product C");
        series3.addData("Jan", 45);
        series3.addData("Feb", 25);
        series3.addData("Mar", 60);
        series3.addData("Apr", 55);
        series3.addData("May", 70);
        series3.addData("Jun", 80);

        lineChart.addSeries(series1);
        lineChart.addSeries(series2);
        lineChart.addSeries(series3);

        // ====================================
        // BAR CHART
        // ====================================
        Label barLabel = new Label("Bar Chart");
        barLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXBarChart barChart = new CFXBarChart();
        barChart.setTitle("Quarterly Performance");
        barChart.setColorScheme(ChartColor.VIBRANT);
        barChart.setShowGrid(true);

        SeriesData barSeries1 = new SeriesData("Sales");
        barSeries1.addData("Q1", 420);
        barSeries1.addData("Q2", 550);
        barSeries1.addData("Q3", 630);
        barSeries1.addData("Q4", 700);

        SeriesData barSeries2 = new SeriesData("Expenses");
        barSeries2.addData("Q1", 280);
        barSeries2.addData("Q2", 320);
        barSeries2.addData("Q3", 380);
        barSeries2.addData("Q4", 420);

        barChart.addSeries(barSeries1);
        barChart.addSeries(barSeries2);

        // ====================================
        // RADAR CHART
        // ====================================
        Label radarLabel = new Label("Radar Chart");
        radarLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXRadarChart radarChart = new CFXRadarChart();
        radarChart.setTitle("Skills Assessment");
        radarChart.setColorScheme(ChartColor.MATERIAL);
        radarChart.setFilled(true);
        radarChart.setLevels(5);

        SeriesData radarSeries1 = new SeriesData("Developer A");
        radarSeries1.addData("Java", 90);
        radarSeries1.addData("JavaScript", 70);
        radarSeries1.addData("Python", 60);
        radarSeries1.addData("CSS", 50);
        radarSeries1.addData("SQL", 85);
        radarSeries1.addData("Docker", 75);

        SeriesData radarSeries2 = new SeriesData("Developer B");
        radarSeries2.addData("Java", 65);
        radarSeries2.addData("JavaScript", 90);
        radarSeries2.addData("Python", 80);
        radarSeries2.addData("CSS", 85);
        radarSeries2.addData("SQL", 70);
        radarSeries2.addData("Docker", 60);

        radarChart.addSeries(radarSeries1);
        radarChart.addSeries(radarSeries2);

        // ====================================
        // Add all to root
        // ====================================
        content.getChildren().addAll(
                title,
                new Separator(),
                pieLabel,
                pieBox,
                new Separator(),
                donutLabel,
                donutBox,
                new Separator(),
                lineLabel,
                lineChart,
                new Separator(),
                barLabel,
                barChart,
                new Separator(),
                radarLabel,
                radarChart
        );
    }
}