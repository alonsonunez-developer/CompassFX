// ============================================
// CFXFileUploader.java - File Uploader Control
// src/main/java/com/compassfx/controls/CFXFileUploader.java
// ============================================
package com.compassfx.controls;

import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.FileType;
import com.compassfx.enums.ProgressColor;
import com.compassfx.models.UploadedFile;
import com.compassfx.skins.CFXFileUploaderSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.io.File;

/**
 * CompassFX FileUploader - A beautiful file upload component with progress tracking
 */
public class CFXFileUploader extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-file-uploader";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-file-uploader.css";

    // Properties
    private final ObservableList<UploadedFile> files;
    private final ObjectProperty<FileType> acceptedFileType;
    private final BooleanProperty multiple;
    private final BooleanProperty dragAndDrop;
    private final BooleanProperty showFileList;
    private final LongProperty maxFileSize;
    private final IntegerProperty maxFiles;
    private final StringProperty buttonText;
    private final StringProperty dragText;
    private final ObjectProperty<ButtonColor> buttonColor;

    // Event handlers
    private ObjectProperty<EventHandler<FileEvent>> onFilesSelected;
    private ObjectProperty<EventHandler<FileEvent>> onFileRemoved;
    private ObjectProperty<EventHandler<FileEvent>> onUploadComplete;

    public CFXFileUploader() {
        this.files = FXCollections.observableArrayList();
        this.acceptedFileType = new SimpleObjectProperty<>(this, "acceptedFileType", FileType.ALL);
        this.multiple = new SimpleBooleanProperty(this, "multiple", false);
        this.dragAndDrop = new SimpleBooleanProperty(this, "dragAndDrop", true);
        this.showFileList = new SimpleBooleanProperty(this, "showFileList", true);
        this.maxFileSize = new SimpleLongProperty(this, "maxFileSize", 10 * 1024 * 1024); // 10MB default
        this.maxFiles = new SimpleIntegerProperty(this, "maxFiles", 10);
        this.buttonText = new SimpleStringProperty(this, "buttonText", "Choose File");
        this.dragText = new SimpleStringProperty(this, "dragText", "or drag and drop files here");
        this.buttonColor = new SimpleObjectProperty<>(this, "buttonColor", ButtonColor.PRIMARY);
        this.onFilesSelected = new SimpleObjectProperty<>(this, "onFilesSelected", null);
        this.onFileRemoved = new SimpleObjectProperty<>(this, "onFileRemoved", null);
        this.onUploadComplete = new SimpleObjectProperty<>(this, "onUploadComplete", null);

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
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXFileUploaderSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addFile(File file) {
        if (files.size() >= maxFiles.get() && !multiple.get()) {
            System.err.println("Maximum number of files reached");
            return;
        }

        if (file.length() > maxFileSize.get()) {
            System.err.println("File size exceeds maximum: " + file.getName());
            return;
        }

        UploadedFile uploadedFile = new UploadedFile(file);
        files.add(uploadedFile);

        if (onFilesSelected.get() != null) {
            FileEvent event = new FileEvent(uploadedFile);
            onFilesSelected.get().handle(event);
        }
    }

    public void removeFile(UploadedFile file) {
        files.remove(file);

        if (onFileRemoved.get() != null) {
            FileEvent event = new FileEvent(file);
            onFileRemoved.get().handle(event);
        }
    }

    public void clearFiles() {
        files.clear();
    }

    public void simulateUpload(UploadedFile file) {
        file.setStatus(UploadedFile.UploadStatus.UPLOADING);

        // Simulate upload progress
        javafx.animation.Timeline timeline = new javafx.animation.Timeline();
        for (int i = 0; i <= 100; i++) {
            final double progress = i / 100.0;
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(
                            javafx.util.Duration.millis(i * 20),
                            e -> file.setProgress(progress)
                    )
            );
        }

        timeline.setOnFinished(e -> {
            file.setStatus(UploadedFile.UploadStatus.COMPLETED);
            if (onUploadComplete.get() != null) {
                FileEvent event = new FileEvent(file);
                onUploadComplete.get().handle(event);
            }
        });

        timeline.play();
    }

    // Getters and Setters
    public ObservableList<UploadedFile> getFiles() { return files; }

    public FileType getAcceptedFileType() { return acceptedFileType.get(); }
    public void setAcceptedFileType(FileType acceptedFileType) {
        this.acceptedFileType.set(acceptedFileType);
    }
    public ObjectProperty<FileType> acceptedFileTypeProperty() { return acceptedFileType; }

    public boolean isMultiple() { return multiple.get(); }
    public void setMultiple(boolean multiple) { this.multiple.set(multiple); }
    public BooleanProperty multipleProperty() { return multiple; }

    public boolean isDragAndDrop() { return dragAndDrop.get(); }
    public void setDragAndDrop(boolean dragAndDrop) { this.dragAndDrop.set(dragAndDrop); }
    public BooleanProperty dragAndDropProperty() { return dragAndDrop; }

    public boolean isShowFileList() { return showFileList.get(); }
    public void setShowFileList(boolean showFileList) { this.showFileList.set(showFileList); }
    public BooleanProperty showFileListProperty() { return showFileList; }

    public long getMaxFileSize() { return maxFileSize.get(); }
    public void setMaxFileSize(long maxFileSize) { this.maxFileSize.set(maxFileSize); }
    public LongProperty maxFileSizeProperty() { return maxFileSize; }

    public int getMaxFiles() { return maxFiles.get(); }
    public void setMaxFiles(int maxFiles) { this.maxFiles.set(maxFiles); }
    public IntegerProperty maxFilesProperty() { return maxFiles; }

    public String getButtonText() { return buttonText.get(); }
    public void setButtonText(String buttonText) { this.buttonText.set(buttonText); }
    public StringProperty buttonTextProperty() { return buttonText; }

    public String getDragText() { return dragText.get(); }
    public void setDragText(String dragText) { this.dragText.set(dragText); }
    public StringProperty dragTextProperty() { return dragText; }

    public ButtonColor getButtonColor() { return buttonColor.get(); }
    public void setButtonColor(ButtonColor buttonColor) { this.buttonColor.set(buttonColor); }
    public ObjectProperty<ButtonColor> buttonColorProperty() { return buttonColor; }

    public EventHandler<FileEvent> getOnFilesSelected() { return onFilesSelected.get(); }
    public void setOnFilesSelected(EventHandler<FileEvent> handler) {
        this.onFilesSelected.set(handler);
    }
    public ObjectProperty<EventHandler<FileEvent>> onFilesSelectedProperty() {
        return onFilesSelected;
    }

    public EventHandler<FileEvent> getOnFileRemoved() { return onFileRemoved.get(); }
    public void setOnFileRemoved(EventHandler<FileEvent> handler) {
        this.onFileRemoved.set(handler);
    }
    public ObjectProperty<EventHandler<FileEvent>> onFileRemovedProperty() { return onFileRemoved; }

    public EventHandler<FileEvent> getOnUploadComplete() { return onUploadComplete.get(); }
    public void setOnUploadComplete(EventHandler<FileEvent> handler) {
        this.onUploadComplete.set(handler);
    }
    public ObjectProperty<EventHandler<FileEvent>> onUploadCompleteProperty() {
        return onUploadComplete;
    }

    // File Event Class
    public static class FileEvent extends ActionEvent {
        private final UploadedFile file;

        public FileEvent(UploadedFile file) {
            super();
            this.file = file;
        }

        public UploadedFile getFile() { return file; }
    }
}