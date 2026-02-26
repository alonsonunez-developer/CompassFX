// ============================================
// CFXFileUploaderSkin.java - File Uploader Skin
// src/main/java/com/compassfx/skins/CFXFileUploaderSkin.java
// ============================================
package com.compassfx.skins;

import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXFileUploader;
import com.compassfx.controls.CFXProgressBar;
import com.compassfx.enums.ProgressColor;
import com.compassfx.models.UploadedFile;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class CFXFileUploaderSkin extends SkinBase<CFXFileUploader> {

    private final CFXFileUploader uploader;
    private final VBox container;
    private final VBox dropZone;
    private final VBox fileListContainer;

    public CFXFileUploaderSkin(CFXFileUploader uploader) {
        super(uploader);
        this.uploader = uploader;

        container = new VBox(20);
        container.getStyleClass().add("uploader-container");
        container.setAlignment(Pos.TOP_CENTER);

        // Drop Zone
        dropZone = createDropZone();

        // File List
        fileListContainer = new VBox(10);
        fileListContainer.getStyleClass().add("file-list-container");

        container.getChildren().add(dropZone);

        if (uploader.isShowFileList()) {
            container.getChildren().add(fileListContainer);
        }

        getChildren().add(container);

        // Listen for file changes
        uploader.getFiles().addListener((ListChangeListener.Change<? extends UploadedFile> c) -> {
            updateFileList();
        });

        uploader.showFileListProperty().addListener((obs, old, newVal) -> {
            if (newVal && !container.getChildren().contains(fileListContainer)) {
                container.getChildren().add(fileListContainer);
            } else if (!newVal) {
                container.getChildren().remove(fileListContainer);
            }
        });

        updateFileList();
    }

    private VBox createDropZone() {
        VBox zone = new VBox(15);
        zone.getStyleClass().add("drop-zone");
        zone.setAlignment(Pos.CENTER);
        zone.setPadding(new Insets(40));

        // Icon
        Label iconLabel = new Label(uploader.getAcceptedFileType().getIcon());
        iconLabel.setStyle("-fx-font-size: 48px;");

        // Button
        CFXButton selectButton = new CFXButton();
        selectButton.textProperty().bind(uploader.buttonTextProperty());
        selectButton.colorProperty().bind(uploader.buttonColorProperty());
        selectButton.setOnAction(e -> openFileChooser());

        // Drag text
        Label dragLabel = new Label();
        dragLabel.textProperty().bind(uploader.dragTextProperty());
        dragLabel.getStyleClass().add("drag-text");
        dragLabel.setFont(Font.font("System", 14));

        zone.getChildren().addAll(iconLabel, selectButton);

        if (uploader.isDragAndDrop()) {
            zone.getChildren().add(dragLabel);
            setupDragAndDrop(zone);
        }

        return zone;
    }

    private void setupDragAndDrop(VBox zone) {
        zone.setOnDragOver(event -> {
            if (event.getGestureSource() != zone && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
                zone.getStyleClass().add("drag-over");
            }
            event.consume();
        });

        zone.setOnDragExited(event -> {
            zone.getStyleClass().remove("drag-over");
            event.consume();
        });

        zone.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles()) {
                List<File> files = db.getFiles();

                if (uploader.isMultiple()) {
                    for (File file : files) {
                        uploader.addFile(file);
                    }
                } else if (!files.isEmpty()) {
                    uploader.addFile(files.get(0));
                }

                success = true;
            }

            zone.getStyleClass().remove("drag-over");
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files");

        // Set file extension filters
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                uploader.getAcceptedFileType().getName().toUpperCase() + " files",
                uploader.getAcceptedFileType().getExtensions().split(";")
        );
        fileChooser.getExtensionFilters().add(filter);

        if (uploader.isMultiple()) {
            List<File> files = fileChooser.showOpenMultipleDialog(uploader.getScene().getWindow());
            if (files != null) {
                for (File file : files) {
                    uploader.addFile(file);
                }
            }
        } else {
            File file = fileChooser.showOpenDialog(uploader.getScene().getWindow());
            if (file != null) {
                uploader.addFile(file);
            }
        }
    }

    private void updateFileList() {
        fileListContainer.getChildren().clear();

        for (UploadedFile file : uploader.getFiles()) {
            VBox fileItem = createFileItem(file);
            fileListContainer.getChildren().add(fileItem);
        }
    }

    private VBox createFileItem(UploadedFile file) {
        VBox fileBox = new VBox(8);
        fileBox.getStyleClass().add("file-item");
        fileBox.setPadding(new Insets(15));

        // Header with filename and remove button
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label(getFileIcon(file.getFileName()));
        iconLabel.setStyle("-fx-font-size: 20px;");

        VBox fileInfo = new VBox(3);

        Label fileName = new Label(file.getFileName());
        fileName.setFont(Font.font("System", FontWeight.BOLD, 14));
        fileName.setMaxWidth(Double.MAX_VALUE);

        Label fileSize = new Label(file.getFormattedSize());
        fileSize.getStyleClass().add("file-size");
        fileSize.setFont(Font.font("System", 12));

        fileInfo.getChildren().addAll(fileName, fileSize);
        HBox.setHgrow(fileInfo, Priority.ALWAYS);

        // Remove button
        CFXButton removeBtn = new CFXButton("×");
        removeBtn.getStyleClass().add("remove-button");
        removeBtn.setOnAction(e -> uploader.removeFile(file));

        header.getChildren().addAll(iconLabel, fileInfo, removeBtn);

        fileBox.getChildren().add(header);

        // Progress bar
        CFXProgressBar progressBar = new CFXProgressBar();
        progressBar.progressProperty().bind(file.progressProperty());
        progressBar.setPrefWidth(Double.MAX_VALUE);

        // Status label
        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("file-status");
        statusLabel.setFont(Font.font("System", 12));

        file.statusProperty().addListener((obs, old, newVal) -> {
            switch (newVal) {
                case PENDING:
                    statusLabel.setText("Pending...");
                    progressBar.setColor(ProgressColor.SECONDARY);
                    break;
                case UPLOADING:
                    statusLabel.setText("Uploading...");
                    progressBar.setColor(ProgressColor.PRIMARY);
                    break;
                case COMPLETED:
                    statusLabel.setText("✓ Upload complete");
                    progressBar.setColor(ProgressColor.SUCCESS);
                    break;
                case ERROR:
                    statusLabel.setText("✗ Upload failed: " + file.getErrorMessage());
                    progressBar.setColor(ProgressColor.ERROR);
                    break;
            }
        });

        // Initialize status
        statusLabel.setText("Pending...");

        fileBox.getChildren().addAll(progressBar, statusLabel);

        // Auto-start upload simulation
        if (file.getStatus() == UploadedFile.UploadStatus.PENDING) {
            javafx.application.Platform.runLater(() -> {
                uploader.simulateUpload(file);
            });
        }

        return fileBox;
    }

    private String getFileIcon(String fileName) {
        String lower = fileName.toLowerCase();

        if (lower.endsWith(".png") || lower.endsWith(".jpg") ||
                lower.endsWith(".jpeg") || lower.endsWith(".gif") ||
                lower.endsWith(".bmp")) {
            return "🖼️";
        } else if (lower.endsWith(".pdf")) {
            return "📕";
        } else if (lower.endsWith(".doc") || lower.endsWith(".docx")) {
            return "📘";
        } else if (lower.endsWith(".xls") || lower.endsWith(".xlsx")) {
            return "📊";
        } else if (lower.endsWith(".ppt") || lower.endsWith(".pptx")) {
            return "📙";
        } else if (lower.endsWith(".zip") || lower.endsWith(".rar")) {
            return "🗜️";
        } else if (lower.endsWith(".mp4") || lower.endsWith(".avi") ||
                lower.endsWith(".mov")) {
            return "🎬";
        } else if (lower.endsWith(".mp3") || lower.endsWith(".wav")) {
            return "🎵";
        }

        return "📄";
    }
}