package com.compassfx.controls;

import com.compassfx.enums.DialogSize;
import javafx.scene.Node;

/**
 * Builder class for creating CFXDialog instances
 */
public class CFXDialogBuilder {

    private final CFXDialog dialog;

    public CFXDialogBuilder() {
        this.dialog = new CFXDialog();
    }

    public CFXDialogBuilder title(String title) {
        dialog.setTitle(title);
        return this;
    }

    public CFXDialogBuilder content(Node content) {
        dialog.setContent(content);
        return this;
    }

    public CFXDialogBuilder size(DialogSize size) {
        dialog.setSize(size);
        return this;
    }

    public CFXDialogBuilder closeOnBackdropClick(boolean closeOnBackdropClick) {
        dialog.setCloseOnBackdropClick(closeOnBackdropClick);
        return this;
    }

    public CFXDialogBuilder showCloseButton(boolean showCloseButton) {
        dialog.setShowCloseButton(showCloseButton);
        return this;
    }

    public CFXDialogBuilder headerGraphic(Node headerGraphic) {
        dialog.setHeaderGraphic(headerGraphic);
        return this;
    }

    public CFXDialogBuilder actions(Node... buttons) {
        dialog.addActions(buttons);
        return this;
    }

    public CFXDialogBuilder onClose(Runnable onClose) {
        dialog.setOnClose(onClose);
        return this;
    }

    public CFXDialog build() {
        return dialog;
    }

    public void show() {
        dialog.show();
    }
}