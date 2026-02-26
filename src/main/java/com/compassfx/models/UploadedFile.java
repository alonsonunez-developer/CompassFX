// ============================================
// UploadedFile.java - Uploaded File Model
// src/main/java/com/compassfx/models/UploadedFile.java
// ============================================
package com.compassfx.models;

import javafx.beans.property.*;
import java.io.File;

/**
 * Represents an uploaded file with progress tracking
 */
public class UploadedFile {

    private final File file;
    private final StringProperty fileName;
    private final LongProperty fileSize;
    private final DoubleProperty progress;
    private final ObjectProperty<UploadStatus> status;
    private final StringProperty errorMessage;

    public enum UploadStatus {
        PENDING, UPLOADING, COMPLETED, ERROR
    }

    public UploadedFile(File file) {
        this.file = file;
        this.fileName = new SimpleStringProperty(this, "fileName", file.getName());
        this.fileSize = new SimpleLongProperty(this, "fileSize", file.length());
        this.progress = new SimpleDoubleProperty(this, "progress", 0.0);
        this.status = new SimpleObjectProperty<>(this, "status", UploadStatus.PENDING);
        this.errorMessage = new SimpleStringProperty(this, "errorMessage", null);
    }

    public File getFile() { return file; }

    public String getFileName() { return fileName.get(); }
    public StringProperty fileNameProperty() { return fileName; }

    public long getFileSize() { return fileSize.get(); }
    public LongProperty fileSizeProperty() { return fileSize; }

    public double getProgress() { return progress.get(); }
    public void setProgress(double progress) { this.progress.set(progress); }
    public DoubleProperty progressProperty() { return progress; }

    public UploadStatus getStatus() { return status.get(); }
    public void setStatus(UploadStatus status) { this.status.set(status); }
    public ObjectProperty<UploadStatus> statusProperty() { return status; }

    public String getErrorMessage() { return errorMessage.get(); }
    public void setErrorMessage(String errorMessage) { this.errorMessage.set(errorMessage); }
    public StringProperty errorMessageProperty() { return errorMessage; }

    public String getFormattedSize() {
        long size = fileSize.get();
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        if (size < 1024 * 1024 * 1024) return String.format("%.1f MB", size / (1024.0 * 1024));
        return String.format("%.1f GB", size / (1024.0 * 1024 * 1024));
    }
}