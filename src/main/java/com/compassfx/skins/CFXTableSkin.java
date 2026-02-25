package com.compassfx.skins;

import com.compassfx.controls.CFXTable;
import com.compassfx.enums.SortOrder;
import com.compassfx.enums.TableDensity;
import com.compassfx.enums.TableSelectionMode;
import com.compassfx.models.TableColumn;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFXTableSkin<T> extends SkinBase<CFXTable<T>> {

    private final CFXTable<T> table;
    private final VBox container;
    private final HBox headerRow;
    private final VBox bodyContainer;
    private final HBox paginationContainer;
    private final Label emptyStateLabel;
    private final Map<T, HBox> rowCache;

    public CFXTableSkin(CFXTable<T> table) {
        super(table);
        this.table = table;
        this.rowCache = new HashMap<>();

        container = new VBox();
        container.getStyleClass().add("table-container");

        // Header
        headerRow = new HBox();
        headerRow.getStyleClass().add("table-header");

        // Body
        bodyContainer = new VBox();
        bodyContainer.getStyleClass().add("table-body");

        // Empty state
        emptyStateLabel = new Label();
        emptyStateLabel.textProperty().bind(table.emptyMessageProperty());
        emptyStateLabel.getStyleClass().add("table-empty-state");
        emptyStateLabel.setFont(Font.font("System", 16));

        // Pagination
        paginationContainer = new HBox(10);
        paginationContainer.getStyleClass().add("table-pagination");
        paginationContainer.setAlignment(Pos.CENTER);
        paginationContainer.setPadding(new Insets(15));

        // Assemble
        if (table.isShowHeader()) {
            container.getChildren().add(headerRow);
        }
        container.getChildren().add(bodyContainer);
        if (table.isShowPagination()) {
            container.getChildren().add(paginationContainer);
        }

        getChildren().add(container);

        // Listeners
        table.getColumns().addListener((ListChangeListener.Change<? extends TableColumn<T>> c) -> {
            updateHeader();
            updateBody();
        });

        table.getFilteredItems().addListener((ListChangeListener.Change<? extends T> c) -> {
            updateBody();
            updatePagination();
        });

        table.currentPageProperty().addListener((obs, old, newVal) -> {
            updateBody();
            updatePagination();
        });

        table.showHeaderProperty().addListener((obs, old, newVal) -> {
            if (newVal && !container.getChildren().contains(headerRow)) {
                container.getChildren().add(0, headerRow);
            } else if (!newVal) {
                container.getChildren().remove(headerRow);
            }
        });

        table.showPaginationProperty().addListener((obs, old, newVal) -> {
            if (newVal && !container.getChildren().contains(paginationContainer)) {
                container.getChildren().add(paginationContainer);
            } else if (!newVal) {
                container.getChildren().remove(paginationContainer);
            }
        });

        // Initialize
        updateHeader();
        updateBody();
        updatePagination();
    }

    private void updateHeader() {
        headerRow.getChildren().clear();

        for (TableColumn<T> column : table.getColumns()) {
            if (!column.isVisible()) continue;

            HBox headerCell = new HBox(5);
            headerCell.getStyleClass().add("table-header-cell");
            headerCell.setAlignment(Pos.CENTER_LEFT);
            headerCell.setPrefWidth(column.getWidth());
            headerCell.setMinWidth(column.getMinWidth());
            headerCell.setMaxWidth(column.getMaxWidth());

            Label headerLabel = new Label();
            headerLabel.textProperty().bind(column.headerProperty());
            headerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

            HBox.setHgrow(headerLabel, Priority.ALWAYS);
            headerCell.getChildren().add(headerLabel);

            // Sort indicator
            if (column.isSortable()) {
                Label sortIcon = new Label();
                sortIcon.getStyleClass().add("sort-icon");

                column.sortOrderProperty().addListener((obs, old, newVal) -> {
                    sortIcon.setText(newVal.getSymbol());
                });
                sortIcon.setText(column.getSortOrder().getSymbol());

                headerCell.getChildren().add(sortIcon);
                headerCell.setCursor(Cursor.HAND);
                headerCell.setOnMouseClicked(e -> {
                    SortOrder newOrder = column.getSortOrder().toggle();
                    table.sort(column, newOrder);
                });
            }

            // Column resizing
            if (column.isResizable()) {
                Region resizeHandle = new Region();
                resizeHandle.getStyleClass().add("resize-handle");
                resizeHandle.setPrefWidth(4);
                resizeHandle.setCursor(Cursor.H_RESIZE);

                final double[] dragStart = new double[1];
                final double[] initialWidth = new double[1];

                resizeHandle.setOnMousePressed(e -> {
                    dragStart[0] = e.getScreenX();
                    initialWidth[0] = column.getWidth();
                });

                resizeHandle.setOnMouseDragged(e -> {
                    double delta = e.getScreenX() - dragStart[0];
                    double newWidth = Math.max(column.getMinWidth(),
                            Math.min(column.getMaxWidth(), initialWidth[0] + delta));
                    column.setWidth(newWidth);
                    headerCell.setPrefWidth(newWidth);
                });

                headerCell.getChildren().add(resizeHandle);
            }

            headerRow.getChildren().add(headerCell);
        }
    }

    private void updateBody() {
        bodyContainer.getChildren().clear();
        rowCache.clear();

        List<T> items = table.getCurrentPageItems();

        if (items.isEmpty()) {
            bodyContainer.getChildren().add(emptyStateLabel);
            return;
        }

        for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
            T item = items.get(rowIndex);
            HBox row = createRow(item, rowIndex);
            rowCache.put(item, row);
            bodyContainer.getChildren().add(row);
        }
    }

    private HBox createRow(T item, int rowIndex) {
        HBox row = new HBox();
        row.getStyleClass().add("table-row");

        if (rowIndex % 2 == 1 && table.isStriped()) {
            row.getStyleClass().add("striped");
        }

        // Selection indicator
        if (table.isSelected(item)) {
            row.getStyleClass().add("selected");
        }

        // Add cells
        for (TableColumn<T> column : table.getColumns()) {
            if (!column.isVisible()) continue;

            StackPane cell = new StackPane();
            cell.getStyleClass().add("table-cell");
            cell.setPrefWidth(column.getWidth());
            cell.setMinWidth(column.getMinWidth());
            cell.setMaxWidth(column.getMaxWidth());
            cell.setAlignment(Pos.CENTER_LEFT);

            // Cell content
            if (column.getCellFactory() != null) {
                Node content = column.getCellFactory().call(item);
                cell.getChildren().add(content);
            } else {
                String value = getCellValue(item, column);
                Label label = new Label(value);
                label.getStyleClass().add("cell-label");
                label.setMaxWidth(Double.MAX_VALUE);
                cell.getChildren().add(label);
            }

            // Update cell width when column width changes
            column.widthProperty().addListener((obs, old, newVal) -> {
                cell.setPrefWidth(newVal.doubleValue());
            });

            row.getChildren().add(cell);
        }

        // Row selection
        if (table.getSelectionMode() != TableSelectionMode.NONE) {
            row.setCursor(Cursor.HAND);
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1) {
                    table.toggleSelection(item);

                    // Update visual selection state
                    if (table.isSelected(item)) {
                        if (!row.getStyleClass().contains("selected")) {
                            row.getStyleClass().add("selected");
                        }
                    } else {
                        row.getStyleClass().remove("selected");
                    }

                    if (table.getOnRowClick() != null) {
                        table.getOnRowClick().handle(new CFXTable.RowEvent<>(item));
                    }
                } else if (e.getClickCount() == 2) {
                    if (table.getOnRowDoubleClick() != null) {
                        table.getOnRowDoubleClick().handle(new CFXTable.RowEvent<>(item));
                    }
                }
            });
        }

        return row;
    }

    private String getCellValue(T item, TableColumn<T> column) {
        if (column.getCellValueFactory() != null) {
            return column.getCellValueFactory().call(item);
        }

        try {
            String propertyName = column.getPropertyName();
            java.lang.reflect.Method method = item.getClass().getMethod(
                    "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1)
            );
            Object value = method.invoke(item);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private void updatePagination() {
        paginationContainer.getChildren().clear();

        if (!table.isShowPagination()) {
            return;
        }

        int totalPages = table.getTotalPages();
        int currentPage = table.getCurrentPage();

        if (totalPages <= 1) {
            return;
        }

        // First button
        Button firstBtn = new Button("⟨⟨");
        firstBtn.getStyleClass().add("pagination-button");
        firstBtn.setDisable(currentPage == 0);
        firstBtn.setOnAction(e -> table.setCurrentPage(0));

        // Previous button
        Button prevBtn = new Button("‹");
        prevBtn.getStyleClass().add("pagination-button");
        prevBtn.setDisable(currentPage == 0);
        prevBtn.setOnAction(e -> table.setCurrentPage(currentPage - 1));

        paginationContainer.getChildren().addAll(firstBtn, prevBtn);

        // Page numbers
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);

        for (int i = startPage; i <= endPage; i++) {
            final int pageIndex = i;
            Button pageBtn = new Button(String.valueOf(i + 1));
            pageBtn.getStyleClass().add("pagination-button");

            if (i == currentPage) {
                pageBtn.getStyleClass().add("active");
            }

            pageBtn.setOnAction(e -> table.setCurrentPage(pageIndex));
            paginationContainer.getChildren().add(pageBtn);
        }

        // Next button
        Button nextBtn = new Button("›");
        nextBtn.getStyleClass().add("pagination-button");
        nextBtn.setDisable(currentPage >= totalPages - 1);
        nextBtn.setOnAction(e -> table.setCurrentPage(currentPage + 1));

        // Last button
        Button lastBtn = new Button("⟩⟩");
        lastBtn.getStyleClass().add("pagination-button");
        lastBtn.setDisable(currentPage >= totalPages - 1);
        lastBtn.setOnAction(e -> table.setCurrentPage(totalPages - 1));

        paginationContainer.getChildren().addAll(nextBtn, lastBtn);

        // Page info
        Label pageInfo = new Label(String.format("Page %d of %d", currentPage + 1, totalPages));
        pageInfo.getStyleClass().add("page-info");
        pageInfo.setStyle("-fx-padding: 0 0 0 15;");
        paginationContainer.getChildren().add(pageInfo);
    }
}