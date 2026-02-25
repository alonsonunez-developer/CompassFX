package com.compassfx.controls;

import com.compassfx.enums.GridAlignment;
import com.compassfx.enums.GridJustify;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * CompassFX Grid - A responsive grid layout system
 * Inspired by CSS Grid and Bootstrap
 */
public class CFXGrid extends Region {

    private static final String DEFAULT_STYLE_CLASS = "cfx-grid";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-grid.css";

    // Properties
    private final IntegerProperty columns;
    private final DoubleProperty hgap;
    private final DoubleProperty vgap;
    private final ObjectProperty<GridAlignment> alignment;
    private final ObjectProperty<GridJustify> justify;
    private final BooleanProperty responsive;
    private final ObservableList<Node> items;

    public CFXGrid() {
        this(12); // Default 12-column grid like Bootstrap
    }

    public CFXGrid(int columns) {
        this.columns = new SimpleIntegerProperty(this, "columns", columns);
        this.hgap = new SimpleDoubleProperty(this, "hgap", 16);
        this.vgap = new SimpleDoubleProperty(this, "vgap", 16);
        this.alignment = new SimpleObjectProperty<>(this, "alignment", GridAlignment.START);
        this.justify = new SimpleObjectProperty<>(this, "justify", GridJustify.START);
        this.responsive = new SimpleBooleanProperty(this, "responsive", true);
        this.items = FXCollections.observableArrayList();

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

        // Listen for children changes
        items.addListener((javafx.collections.ListChangeListener.Change<? extends Node> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    getChildren().addAll(c.getAddedSubList());
                }
                if (c.wasRemoved()) {
                    getChildren().removeAll(c.getRemoved());
                }
            }
            requestLayout();
        });

        // Re-layout when properties change
        columns.addListener((obs, old, newVal) -> requestLayout());
        hgap.addListener((obs, old, newVal) -> requestLayout());
        vgap.addListener((obs, old, newVal) -> requestLayout());

        // Listen for width changes for responsive behavior
        widthProperty().addListener((obs, old, newVal) -> {
            if (responsive.get()) {
                requestLayout();
            }
        });
    }

    @Override
    protected void layoutChildren() {
        double width = getWidth();
        double height = getHeight();

        if (items.isEmpty()) {
            return;
        }

        int cols = columns.get();
        double horizontalGap = hgap.get();
        double verticalGap = vgap.get();

        // Calculate column width
        double totalGapWidth = horizontalGap * (cols - 1);
        double availableWidth = width - totalGapWidth;
        double columnWidth = availableWidth / cols;

        // Layout children
        int row = 0;
        int col = 0;
        double currentY = 0;
        double rowHeight = 0;

        for (Node child : items) {
            if (!child.isManaged()) {
                continue;
            }

            // Get column span for this child
            Integer colSpan = getColumnSpan(child);
            if (colSpan == null) {
                colSpan = 1;
            }

            // Get row span for this child
            Integer rowSpan = getRowSpan(child);
            if (rowSpan == null) {
                rowSpan = 1;
            }

            // Check if we need to move to next row
            if (col + colSpan > cols) {
                col = 0;
                row++;
                currentY += rowHeight + verticalGap;
                rowHeight = 0;
            }

            // Calculate position and size
            double x = col * (columnWidth + horizontalGap);
            double childWidth = (columnWidth * colSpan) + (horizontalGap * (colSpan - 1));

            // Resize child
            child.resize(childWidth, USE_COMPUTED_SIZE);

            // Position child
            child.relocate(x, currentY);

            // Track row height
            rowHeight = Math.max(rowHeight, child.getLayoutBounds().getHeight());

            // Move to next column
            col += colSpan;
        }
    }

    @Override
    protected double computePrefWidth(double height) {
        return 600; // Default preferred width
    }

    @Override
    protected double computePrefHeight(double width) {
        if (items.isEmpty()) {
            return 0;
        }

        int cols = columns.get();
        double horizontalGap = hgap.get();
        double verticalGap = vgap.get();

        double totalGapWidth = horizontalGap * (cols - 1);
        double availableWidth = width - totalGapWidth;
        double columnWidth = availableWidth / cols;

        double totalHeight = 0;
        double rowHeight = 0;
        int col = 0;

        for (Node child : items) {
            if (!child.isManaged()) {
                continue;
            }

            Integer colSpan = getColumnSpan(child);
            if (colSpan == null) {
                colSpan = 1;
            }

            if (col + colSpan > cols) {
                col = 0;
                totalHeight += rowHeight + verticalGap;
                rowHeight = 0;
            }

            double childWidth = (columnWidth * colSpan) + (horizontalGap * (colSpan - 1));
            child.resize(childWidth, USE_COMPUTED_SIZE);
            rowHeight = Math.max(rowHeight, child.getLayoutBounds().getHeight());

            col += colSpan;
        }

        totalHeight += rowHeight;
        return totalHeight;
    }

    // Static methods for setting grid properties on nodes
    public static void setColumnSpan(Node node, int span) {
        node.getProperties().put("grid-column-span", span);
    }

    public static Integer getColumnSpan(Node node) {
        Object value = node.getProperties().get("grid-column-span");
        return value instanceof Integer ? (Integer) value : null;
    }

    public static void setRowSpan(Node node, int span) {
        node.getProperties().put("grid-row-span", span);
    }

    public static Integer getRowSpan(Node node) {
        Object value = node.getProperties().get("grid-row-span");
        return value instanceof Integer ? (Integer) value : null;
    }

    public static void setColumn(Node node, int column) {
        node.getProperties().put("grid-column", column);
    }

    public static Integer getColumn(Node node) {
        Object value = node.getProperties().get("grid-column");
        return value instanceof Integer ? (Integer) value : null;
    }

    public static void setRow(Node node, int row) {
        node.getProperties().put("grid-row", row);
    }

    public static Integer getRow(Node node) {
        Object value = node.getProperties().get("grid-row");
        return value instanceof Integer ? (Integer) value : null;
    }

    // Helper methods
    public void addItem(Node node) {
        items.add(node);
    }

    public void addItem(Node node, int columnSpan) {
        setColumnSpan(node, columnSpan);
        items.add(node);
    }

    public void removeItem(Node node) {
        items.remove(node);
    }

    // Getters and Setters
    public ObservableList<Node> getItems() { return items; }

    public int getColumns() { return columns.get(); }
    public void setColumns(int columns) { this.columns.set(columns); }
    public IntegerProperty columnsProperty() { return columns; }

    public double getHgap() { return hgap.get(); }
    public void setHgap(double hgap) { this.hgap.set(hgap); }
    public DoubleProperty hgapProperty() { return hgap; }

    public double getVgap() { return vgap.get(); }
    public void setVgap(double vgap) { this.vgap.set(vgap); }
    public DoubleProperty vgapProperty() { return vgap; }

    public GridAlignment getAlignment() { return alignment.get(); }
    public void setAlignment(GridAlignment alignment) { this.alignment.set(alignment); }
    public ObjectProperty<GridAlignment> alignmentProperty() { return alignment; }

    public GridJustify getJustify() { return justify.get(); }
    public void setJustify(GridJustify justify) { this.justify.set(justify); }
    public ObjectProperty<GridJustify> justifyProperty() { return justify; }

    public boolean isResponsive() { return responsive.get(); }
    public void setResponsive(boolean responsive) { this.responsive.set(responsive); }
    public BooleanProperty responsiveProperty() { return responsive; }
}