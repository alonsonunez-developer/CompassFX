package com.compassfx.models;

import com.compassfx.enums.SortOrder;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.util.Callback;

import java.util.Comparator;

/**
 * Represents a column in the CFXTable
 */
public class TableColumn<T> {

    private final StringProperty header;
    private final StringProperty propertyName;
    private final DoubleProperty width;
    private final DoubleProperty minWidth;
    private final DoubleProperty maxWidth;
    private final BooleanProperty sortable;
    private final BooleanProperty resizable;
    private final BooleanProperty visible;
    private final ObjectProperty<SortOrder> sortOrder;
    private final ObjectProperty<Callback<T, String>> cellValueFactory;
    private final ObjectProperty<Callback<T, Node>> cellFactory;
    private final ObjectProperty<Comparator<T>> comparator;

    public TableColumn(String header, String propertyName) {
        this(header, propertyName, 150);
    }

    public TableColumn(String header, String propertyName, double width) {
        this.header = new SimpleStringProperty(this, "header", header);
        this.propertyName = new SimpleStringProperty(this, "propertyName", propertyName);
        this.width = new SimpleDoubleProperty(this, "width", width);
        this.minWidth = new SimpleDoubleProperty(this, "minWidth", 50);
        this.maxWidth = new SimpleDoubleProperty(this, "maxWidth", Double.MAX_VALUE);
        this.sortable = new SimpleBooleanProperty(this, "sortable", true);
        this.resizable = new SimpleBooleanProperty(this, "resizable", true);
        this.visible = new SimpleBooleanProperty(this, "visible", true);
        this.sortOrder = new SimpleObjectProperty<>(this, "sortOrder", SortOrder.NONE);
        this.cellValueFactory = new SimpleObjectProperty<>(this, "cellValueFactory", null);
        this.cellFactory = new SimpleObjectProperty<>(this, "cellFactory", null);
        this.comparator = new SimpleObjectProperty<>(this, "comparator", null);
    }

    // Header
    public String getHeader() { return header.get(); }
    public void setHeader(String header) { this.header.set(header); }
    public StringProperty headerProperty() { return header; }

    // Property Name
    public String getPropertyName() { return propertyName.get(); }
    public void setPropertyName(String propertyName) { this.propertyName.set(propertyName); }
    public StringProperty propertyNameProperty() { return propertyName; }

    // Width
    public double getWidth() { return width.get(); }
    public void setWidth(double width) { this.width.set(width); }
    public DoubleProperty widthProperty() { return width; }

    // Min Width
    public double getMinWidth() { return minWidth.get(); }
    public void setMinWidth(double minWidth) { this.minWidth.set(minWidth); }
    public DoubleProperty minWidthProperty() { return minWidth; }

    // Max Width
    public double getMaxWidth() { return maxWidth.get(); }
    public void setMaxWidth(double maxWidth) { this.maxWidth.set(maxWidth); }
    public DoubleProperty maxWidthProperty() { return maxWidth; }

    // Sortable
    public boolean isSortable() { return sortable.get(); }
    public void setSortable(boolean sortable) { this.sortable.set(sortable); }
    public BooleanProperty sortableProperty() { return sortable; }

    // Resizable
    public boolean isResizable() { return resizable.get(); }
    public void setResizable(boolean resizable) { this.resizable.set(resizable); }
    public BooleanProperty resizableProperty() { return resizable; }

    // Visible
    public boolean isVisible() { return visible.get(); }
    public void setVisible(boolean visible) { this.visible.set(visible); }
    public BooleanProperty visibleProperty() { return visible; }

    // Sort Order
    public SortOrder getSortOrder() { return sortOrder.get(); }
    public void setSortOrder(SortOrder sortOrder) { this.sortOrder.set(sortOrder); }
    public ObjectProperty<SortOrder> sortOrderProperty() { return sortOrder; }

    // Cell Value Factory
    public Callback<T, String> getCellValueFactory() { return cellValueFactory.get(); }
    public void setCellValueFactory(Callback<T, String> cellValueFactory) {
        this.cellValueFactory.set(cellValueFactory);
    }
    public ObjectProperty<Callback<T, String>> cellValueFactoryProperty() { return cellValueFactory; }

    // Cell Factory
    public Callback<T, Node> getCellFactory() { return cellFactory.get(); }
    public void setCellFactory(Callback<T, Node> cellFactory) {
        this.cellFactory.set(cellFactory);
    }
    public ObjectProperty<Callback<T, Node>> cellFactoryProperty() { return cellFactory; }

    // Comparator
    public Comparator<T> getComparator() { return comparator.get(); }
    public void setComparator(Comparator<T> comparator) { this.comparator.set(comparator); }
    public ObjectProperty<Comparator<T>> comparatorProperty() { return comparator; }

    @Override
    public String toString() {
        return header.get();
    }
}
