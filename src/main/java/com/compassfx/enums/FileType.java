// ============================================
// FileType.java
// src/main/java/com/compassfx/enums/FileType.java
// ============================================
package com.compassfx.enums;

public enum FileType {
    IMAGE("image", "*.png;*.jpg;*.jpeg;*.gif;*.bmp;*.svg", "📷"),
    DOCUMENT("document", "*.pdf;*.doc;*.docx;*.txt;*.xls;*.xlsx;*.ppt;*.pptx", "📄"),
    VIDEO("video", "*.mp4;*.avi;*.mov;*.mkv;*.wmv", "🎥"),
    AUDIO("audio", "*.mp3;*.wav;*.ogg;*.m4a;*.flac", "🎵"),
    ALL("all", "*.*", "📁");

    private final String name;
    private final String extensions;
    private final String icon;

    FileType(String name, String extensions, String icon) {
        this.name = name;
        this.extensions = extensions;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getExtensions() {
        return extensions;
    }

    public String getIcon() {
        return icon;
    }
}