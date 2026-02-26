package com.compassfx.controls;

import com.compassfx.enums.StepStatus;
import com.compassfx.enums.StepperOrientation;
import com.compassfx.enums.StepperVariant;
import com.compassfx.models.StepperItem;
import com.compassfx.skins.CFXStepperSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Stepper - A Material Design inspired stepper component
 */
public class CFXStepper extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-stepper";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-stepper.css";

    // Properties
    private final ObservableList<StepperItem> steps;
    private final IntegerProperty currentStep;
    private final ObjectProperty<StepperOrientation> orientation;
    private final ObjectProperty<StepperVariant> variant;
    private final BooleanProperty linear;
    private final BooleanProperty showStepNumbers;
    private final BooleanProperty animated;
    private final BooleanProperty showContent;

    // Event handlers
    private ObjectProperty<EventHandler<StepEvent>> onStepChange;
    private ObjectProperty<EventHandler<StepEvent>> onStepComplete;

    public CFXStepper() {
        this.steps = FXCollections.observableArrayList();
        this.currentStep = new SimpleIntegerProperty(this, "currentStep", 0);
        this.orientation = new SimpleObjectProperty<>(this, "orientation", StepperOrientation.HORIZONTAL);
        this.variant = new SimpleObjectProperty<>(this, "variant", StepperVariant.STANDARD);
        this.linear = new SimpleBooleanProperty(this, "linear", true);
        this.showStepNumbers = new SimpleBooleanProperty(this, "showStepNumbers", true);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.showContent = new SimpleBooleanProperty(this, "showContent", true);
        this.onStepChange = new SimpleObjectProperty<>(this, "onStepChange", null);
        this.onStepComplete = new SimpleObjectProperty<>(this, "onStepComplete", null);

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

        updateStyleClasses();
        orientation.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Update step statuses when current step changes
        currentStep.addListener((obs, oldVal, newVal) -> {
            updateStepStatuses();

            if (onStepChange.get() != null && newVal.intValue() >= 0 && newVal.intValue() < steps.size()) {
                StepEvent event = new StepEvent(steps.get(newVal.intValue()), newVal.intValue());
                onStepChange.get().handle(event);
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(orientation.get().getStyleClass());
        getStyleClass().add(variant.get().getStyleClass());
    }

    private void updateStepStatuses() {
        for (int i = 0; i < steps.size(); i++) {
            StepperItem step = steps.get(i);

            if (step.getStatus() == StepStatus.ERROR || step.getStatus() == StepStatus.WARNING) {
                continue; // Don't override error/warning states
            }

            if (i < currentStep.get()) {
                step.setStatus(StepStatus.COMPLETED);
            } else if (i == currentStep.get()) {
                step.setStatus(StepStatus.ACTIVE);
            } else {
                step.setStatus(StepStatus.PENDING);
            }
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXStepperSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addStep(String title) {
        steps.add(new StepperItem(title));
    }

    public void addStep(String title, String description) {
        steps.add(new StepperItem(title, description));
    }

    public void addStep(StepperItem step) {
        steps.add(step);
    }

    public void next() {
        if (canGoNext()) {
            currentStep.set(currentStep.get() + 1);
        }
    }

    public void previous() {
        if (canGoPrevious()) {
            currentStep.set(currentStep.get() - 1);
        }
    }

    public void goToStep(int stepIndex) {
        if (stepIndex >= 0 && stepIndex < steps.size()) {
            if (!linear.get() || stepIndex <= getFirstIncompleteStep()) {
                currentStep.set(stepIndex);
            }
        }
    }

    public void completeCurrentStep() {
        if (currentStep.get() >= 0 && currentStep.get() < steps.size()) {
            StepperItem step = steps.get(currentStep.get());
            step.setStatus(StepStatus.COMPLETED);

            if (onStepComplete.get() != null) {
                StepEvent event = new StepEvent(step, currentStep.get());
                onStepComplete.get().handle(event);
            }

            if (canGoNext()) {
                next();
            }
        }
    }

    public void reset() {
        currentStep.set(0);
        for (StepperItem step : steps) {
            step.setStatus(StepStatus.PENDING);
        }
        updateStepStatuses();
    }

    public boolean canGoNext() {
        return currentStep.get() < steps.size() - 1;
    }

    public boolean canGoPrevious() {
        return currentStep.get() > 0;
    }

    public boolean isComplete() {
        return steps.stream().allMatch(step -> step.getStatus() == StepStatus.COMPLETED);
    }

    private int getFirstIncompleteStep() {
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getStatus() != StepStatus.COMPLETED) {
                return i;
            }
        }
        return steps.size() - 1;
    }

    // Getters and Setters
    public ObservableList<StepperItem> getSteps() { return steps; }

    public int getCurrentStep() { return currentStep.get(); }
    public void setCurrentStep(int currentStep) { this.currentStep.set(currentStep); }
    public IntegerProperty currentStepProperty() { return currentStep; }

    public StepperOrientation getOrientation() { return orientation.get(); }
    public void setOrientation(StepperOrientation orientation) { this.orientation.set(orientation); }
    public ObjectProperty<StepperOrientation> orientationProperty() { return orientation; }

    public StepperVariant getVariant() { return variant.get(); }
    public void setVariant(StepperVariant variant) { this.variant.set(variant); }
    public ObjectProperty<StepperVariant> variantProperty() { return variant; }

    public boolean isLinear() { return linear.get(); }
    public void setLinear(boolean linear) { this.linear.set(linear); }
    public BooleanProperty linearProperty() { return linear; }

    public boolean isShowStepNumbers() { return showStepNumbers.get(); }
    public void setShowStepNumbers(boolean showStepNumbers) { this.showStepNumbers.set(showStepNumbers); }
    public BooleanProperty showStepNumbersProperty() { return showStepNumbers; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public boolean isShowContent() { return showContent.get(); }
    public void setShowContent(boolean showContent) { this.showContent.set(showContent); }
    public BooleanProperty showContentProperty() { return showContent; }

    public EventHandler<StepEvent> getOnStepChange() { return onStepChange.get(); }
    public void setOnStepChange(EventHandler<StepEvent> handler) { this.onStepChange.set(handler); }
    public ObjectProperty<EventHandler<StepEvent>> onStepChangeProperty() { return onStepChange; }

    public EventHandler<StepEvent> getOnStepComplete() { return onStepComplete.get(); }
    public void setOnStepComplete(EventHandler<StepEvent> handler) { this.onStepComplete.set(handler); }
    public ObjectProperty<EventHandler<StepEvent>> onStepCompleteProperty() { return onStepComplete; }

    // Step Event Class
    public static class StepEvent extends ActionEvent {
        private final StepperItem step;
        private final int index;

        public StepEvent(StepperItem step, int index) {
            super();
            this.step = step;
            this.index = index;
        }

        public StepperItem getStep() { return step; }
        public int getIndex() { return index; }
    }
}
