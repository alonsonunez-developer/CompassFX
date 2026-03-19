package com.compassfx.demo;

import com.compassfx.controls.CFXFileUploader;
import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.FileType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class FileUploaderDemo {

    public void showDemo(Label title, VBox root) {
        // ====================================
        // Image Uploader
        // ====================================
        Label imageLabel = new Label("Image Uploader");
        imageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader imageUploader = new CFXFileUploader();
        imageUploader.setAcceptedFileType(FileType.IMAGE);
        imageUploader.setButtonText("Choose Images");
        imageUploader.setDragText("or drag and drop images here");
        imageUploader.setMultiple(true);
        imageUploader.setMaxWidth(700);
        imageUploader.setMaxFiles(5);

        imageUploader.setOnFilesSelected(event -> {
            System.out.println("Image selected: " + event.getFile().getFileName());
        });

        imageUploader.setOnUploadComplete(event -> {
            System.out.println("Upload complete: " + event.getFile().getFileName());
        });

        // ====================================
        // Document Uploader
        // ====================================
        Label docLabel = new Label("Document Uploader");
        docLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader docUploader = new CFXFileUploader();
        docUploader.setAcceptedFileType(FileType.DOCUMENT);
        docUploader.setButtonText("Upload Documents");
        docUploader.setButtonColor(ButtonColor.SUCCESS);
        docUploader.setMultiple(true);
        docUploader.setMaxWidth(700);

        // ====================================
        // Single File Uploader
        // ====================================
        Label singleLabel = new Label("Single File Uploader");
        singleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader singleUploader = new CFXFileUploader();
        singleUploader.setAcceptedFileType(FileType.ALL);
        singleUploader.setButtonText("Select File");
        singleUploader.setButtonColor(ButtonColor.SECONDARY);
        singleUploader.setMultiple(false);
        singleUploader.setMaxWidth(700);

        // ====================================
        // No Drag and Drop
        // ====================================
        Label noDragLabel = new Label("Without Drag & Drop");
        noDragLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader noDragUploader = new CFXFileUploader();
        noDragUploader.setDragAndDrop(false);
        noDragUploader.setButtonText("Click to Upload");
        noDragUploader.setButtonColor(ButtonColor.WARNING);
        noDragUploader.setMaxWidth(700);

        // ====================================
        // Compact Uploader (No File List)
        // ====================================
        Label compactLabel = new Label("Compact Uploader (No File List)");
        compactLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader compactUploader = new CFXFileUploader();
        compactUploader.setShowFileList(false);
        compactUploader.setButtonText("Quick Upload");
        compactUploader.setButtonColor(ButtonColor.ERROR);
        compactUploader.setMaxWidth(700);

        Label compactInfo = new Label("Files are uploaded immediately without showing the list");
        compactInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-style: italic;");

        compactUploader.setOnFilesSelected(event -> {
            System.out.println("Quick upload: " + event.getFile().getFileName());
        });

        // ====================================
        // Video Uploader
        // ====================================
        Label videoLabel = new Label("Video Uploader");
        videoLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #424242;");

        CFXFileUploader videoUploader = new CFXFileUploader();
        videoUploader.setAcceptedFileType(FileType.VIDEO);
        videoUploader.setButtonText("Upload Videos");
        videoUploader.setDragText("Drag videos here (max 50MB each)");
        videoUploader.setMaxFileSize(50 * 1024 * 1024); // 50MB
        videoUploader.setMaxWidth(700);

        // ====================================
        // Add all to root
        // ====================================
        root.getChildren().addAll(
                title,
                new Separator(),
                imageLabel,
                imageUploader,
                new Separator(),
                docLabel,
                docUploader,
                new Separator(),
                singleLabel,
                singleUploader,
                new Separator(),
                noDragLabel,
                noDragUploader,
                new Separator(),
                compactLabel,
                compactUploader,
                compactInfo,
                new Separator(),
                videoLabel,
                videoUploader
        );
    }
}