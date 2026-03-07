package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.CFXAutocomplete;
import com.compassfx.controls.CFXButton;
import com.compassfx.controls.CFXComboBox;
import com.compassfx.controls.CFXTextField;
import com.compassfx.enums.ComboBoxVariant;
import com.compassfx.enums.TextFieldVariant;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * Demo application showcasing CFXComboBox features
 */
public class ComboBoxDemo {

    public static class User {
        private final String name;
        private final String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return name + " <" + email + ">";
        }
    }

    public void showDemo(Label title, VBox root){
        Label outlinedLabel = new Label("Outlined ComboBoxes");
        outlinedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox outlinedSection = new VBox(20);
        outlinedSection.setAlignment(Pos.CENTER_LEFT);
        outlinedSection.setMaxWidth(400);

        // Basic outlined combobox
        CFXComboBox<String> countryBox = new CFXComboBox<>();
        countryBox.setVariant(ComboBoxVariant.OUTLINED);
        countryBox.setLabel("Country");
        countryBox.setHelperText("Select your country");
        countryBox.setItems(FXCollections.observableArrayList(
                "United States", "Canada", "Mexico", "United Kingdom",
                "Germany", "France", "Spain", "Italy", "Japan", "China"
        ));

        // Outlined with prompt text
        CFXComboBox<String> languageBox = new CFXComboBox<>();
        languageBox.setVariant(ComboBoxVariant.OUTLINED);
        languageBox.setLabel("Language");
        languageBox.setPromptText("Choose a language");
        languageBox.setItems(FXCollections.observableArrayList(
                "English", "Spanish", "French", "German", "Chinese",
                "Japanese", "Portuguese", "Russian", "Arabic"
        ));

        // Outlined with preselected value
        CFXComboBox<String> timezoneBox = new CFXComboBox<>();
        timezoneBox.setVariant(ComboBoxVariant.OUTLINED);
        timezoneBox.setLabel("Timezone");
        timezoneBox.setItems(FXCollections.observableArrayList(
                "UTC-8:00 (Pacific)", "UTC-7:00 (Mountain)", "UTC-6:00 (Central)",
                "UTC-5:00 (Eastern)", "UTC+0:00 (GMT)", "UTC+1:00 (CET)"
        ));
        timezoneBox.setValue("UTC-5:00 (Eastern)");

        outlinedSection.getChildren().addAll(countryBox, languageBox, timezoneBox);

        // ===== FILLED COMBOBOXES SECTION =====
        Label filledLabel = new Label("Filled ComboBoxes");
        filledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox filledSection = new VBox(20);
        filledSection.setAlignment(Pos.CENTER_LEFT);
        filledSection.setMaxWidth(400);

        // Basic filled combobox
        CFXComboBox<String> categoryBox = new CFXComboBox<>();
        categoryBox.setVariant(ComboBoxVariant.FILLED);
        categoryBox.setLabel("Category");
        categoryBox.setItems(FXCollections.observableArrayList(
                "Electronics", "Clothing", "Books", "Home & Garden",
                "Sports", "Toys", "Food & Beverage"
        ));

        // Filled with helper text
        CFXComboBox<String> priorityBox = new CFXComboBox<>();
        priorityBox.setVariant(ComboBoxVariant.FILLED);
        priorityBox.setLabel("Priority");
        priorityBox.setHelperText("Set task priority level");
        priorityBox.setItems(FXCollections.observableArrayList(
                "Low", "Medium", "High", "Critical"
        ));
        priorityBox.setValue("Medium");

        // Filled with status
        CFXComboBox<String> statusBox = new CFXComboBox<>();
        statusBox.setVariant(ComboBoxVariant.FILLED);
        statusBox.setLabel("Status");
        statusBox.setItems(FXCollections.observableArrayList(
                "Active", "Pending", "Completed", "Cancelled", "On Hold"
        ));

        filledSection.getChildren().addAll(categoryBox, priorityBox, statusBox);

        // ===== ERROR STATE SECTION =====
        Label errorLabel = new Label("Error State");
        errorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox errorSection = new VBox(20);
        errorSection.setAlignment(Pos.CENTER_LEFT);
        errorSection.setMaxWidth(400);

        CFXComboBox<String> errorBox1 = new CFXComboBox<>();
        errorBox1.setVariant(ComboBoxVariant.OUTLINED);
        errorBox1.setLabel("Required Field");
        errorBox1.setErrorText("Please select an option");
        errorBox1.setItems(FXCollections.observableArrayList(
                "Option 1", "Option 2", "Option 3"
        ));

        CFXComboBox<String> errorBox2 = new CFXComboBox<>();
        errorBox2.setVariant(ComboBoxVariant.FILLED);
        errorBox2.setLabel("Payment Method");
        errorBox2.setErrorText("Payment method is required");
        errorBox2.setItems(FXCollections.observableArrayList(
                "Credit Card", "Debit Card", "PayPal", "Bank Transfer"
        ));

        errorSection.getChildren().addAll(errorBox1, errorBox2);

        // ===== DISABLED STATE SECTION =====
        Label disabledLabel = new Label("Disabled State");
        disabledLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox disabledSection = new VBox(20);
        disabledSection.setAlignment(Pos.CENTER_LEFT);
        disabledSection.setMaxWidth(400);

        CFXComboBox<String> disabledBox1 = new CFXComboBox<>();
        disabledBox1.setVariant(ComboBoxVariant.OUTLINED);
        disabledBox1.setLabel("Disabled Outlined");
        disabledBox1.setItems(FXCollections.observableArrayList(
                "Option A", "Option B", "Option C"
        ));
        disabledBox1.setValue("Option A");
        disabledBox1.setDisable(true);

        CFXComboBox<String> disabledBox2 = new CFXComboBox<>();
        disabledBox2.setVariant(ComboBoxVariant.FILLED);
        disabledBox2.setLabel("Disabled Filled");
        disabledBox2.setItems(FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3"
        ));
        disabledBox2.setDisable(true);

        disabledSection.getChildren().addAll(disabledBox1, disabledBox2);

        // ===== CUSTOM ITEMS SECTION =====
        Label customLabel = new Label("Custom Items (Objects)");
        customLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox customSection = new VBox(20);
        customSection.setAlignment(Pos.CENTER_LEFT);
        customSection.setMaxWidth(400);

        // ComboBox with custom objects
        CFXComboBox<User> userBox = new CFXComboBox<>();
        userBox.setVariant(ComboBoxVariant.OUTLINED);
        userBox.setLabel("Assign to");
        userBox.setHelperText("Select a team member");
        userBox.setItems(FXCollections.observableArrayList(
                new User("John Doe", "john@example.com"),
                new User("Jane Smith", "jane@example.com"),
                new User("Bob Johnson", "bob@example.com"),
                new User("Alice Williams", "alice@example.com")
        ));

        userBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected user: " + newVal.getName() + " (" + newVal.getEmail() + ")");
            }
        });

        customSection.getChildren().add(userBox);

        // ===== FORM EXAMPLE SECTION =====
        Label formLabel = new Label("Complete Form Example");
        formLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 600;");

        VBox formSection = new VBox(15);
        formSection.setAlignment(Pos.CENTER_LEFT);
        formSection.setMaxWidth(400);
        formSection.setPadding(new Insets(15));
        formSection.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 1px; -fx-border-radius: 8px;");

        Label formTitle = new Label("User Registration");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 600;");

        CFXTextField nameField = new CFXTextField();
        nameField.setVariant(TextFieldVariant.OUTLINED);
        nameField.setLabel("Full Name");

        CFXTextField emailField = new CFXTextField();
        emailField.setVariant(TextFieldVariant.OUTLINED);
        emailField.setLabel("Email");

        CFXComboBox<String> roleBox = new CFXComboBox<>();
        roleBox.setVariant(ComboBoxVariant.OUTLINED);
        roleBox.setLabel("Role");
        roleBox.setHelperText("Select user role");
        roleBox.setItems(FXCollections.observableArrayList(
                "Administrator", "Manager", "Developer", "Designer", "User"
        ));

        CFXComboBox<String> departmentBox = new CFXComboBox<>();
        departmentBox.setVariant(ComboBoxVariant.OUTLINED);
        departmentBox.setLabel("Department");
        departmentBox.setItems(FXCollections.observableArrayList(
                "Engineering", "Marketing", "Sales", "HR", "Finance"
        ));

        CFXComboBox<String> locationBox = new CFXComboBox<>();
        locationBox.setVariant(ComboBoxVariant.OUTLINED);
        locationBox.setLabel("Office Location");
        locationBox.setItems(FXCollections.observableArrayList(
                "New York", "San Francisco", "London", "Tokyo", "Remote"
        ));

        HBox formButtons = new HBox(10);
        formButtons.setAlignment(Pos.CENTER_RIGHT);

        CFXButton submitBtn = new CFXButton("Register");
        submitBtn.setOnAction(e -> {
            // Simple validation
            boolean hasError = false;

            if (nameField.getText().isEmpty()) {
                nameField.setErrorText("Name is required");
                hasError = true;
            } else {
                nameField.setErrorText("");
            }

            if (emailField.getText().isEmpty()) {
                emailField.setErrorText("Email is required");
                hasError = true;
            } else {
                emailField.setErrorText("");
            }

            if (roleBox.getValue() == null) {
                roleBox.setErrorText("Please select a role");
                hasError = true;
            } else {
                roleBox.setErrorText("");
            }

            if (!hasError) {
                System.out.println("=== Registration Submitted ===");
                System.out.println("Name: " + nameField.getText());
                System.out.println("Email: " + emailField.getText());
                System.out.println("Role: " + roleBox.getValue());
                System.out.println("Department: " + departmentBox.getValue());
                System.out.println("Location: " + locationBox.getValue());
            }
        });

        formButtons.getChildren().add(submitBtn);

        formSection.getChildren().addAll(
                formTitle,
                new Separator(),
                nameField,
                emailField,
                roleBox,
                departmentBox,
                locationBox,
                formButtons
        );

        // ====================================
        // Countries Autocomplete
        // ====================================
        Label countriesLabel = new Label("Search Countries");
        countriesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXAutocomplete<String> countriesAuto = new CFXAutocomplete<>();
        countriesAuto.setLabel("Country");
        countriesAuto.setPromptText("Type to search...");
        countriesAuto.setPrefWidth(400);
        countriesAuto.setVariant(ComboBoxVariant.OUTLINED);

        List<String> countries = Arrays.asList(
                "United States", "Canada", "Mexico", "Brazil", "Argentina",
                "United Kingdom", "France", "Germany", "Italy", "Spain",
                "Portugal", "Netherlands", "Belgium", "Switzerland", "Austria",
                "Japan", "China", "South Korea", "India", "Thailand",
                "Australia", "New Zealand", "Russia", "Poland", "Sweden",
                "Norway", "Denmark", "Finland", "Greece", "Turkey"
        );
        countriesAuto.setAllItems(FXCollections.observableArrayList(countries));

        Label countriesResult = new Label("Selected: None");
        countriesResult.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        countriesAuto.valueProperty().addListener((obs, old, newVal) -> {
            countriesResult.setText("Selected: " + (newVal != null ? newVal : "None"));
        });

        // ====================================
        // Programming Languages
        // ====================================
        Label languagesLabel = new Label("Search Programming Languages");
        languagesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXAutocomplete<String> languagesAuto = new CFXAutocomplete<>();
        languagesAuto.setLabel("Programming Language");
        languagesAuto.setPromptText("Start typing...");
        languagesAuto.setPrefWidth(400);
        languagesAuto.setVariant(ComboBoxVariant.FILLED);
        languagesAuto.setHelperText("Type to filter languages");

        List<String> languages = Arrays.asList(
                "Java", "JavaScript", "Python", "C++", "C#", "Ruby", "Go",
                "Rust", "TypeScript", "Swift", "Kotlin", "PHP", "Scala",
                "Dart", "R", "Perl", "Haskell", "Lua", "Julia", "Elixir",
                "Clojure", "F#", "OCaml", "Erlang", "Groovy", "COBOL"
        );
        languagesAuto.setAllItems(FXCollections.observableArrayList(languages));

        Label languagesResult = new Label("Selected: None");
        languagesResult.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        languagesAuto.valueProperty().addListener((obs, old, newVal) -> {
            languagesResult.setText("Selected: " + (newVal != null ? newVal : "None"));
        });

        // ====================================
        // Cities with variant PRIMARY
        // ====================================
        Label citiesLabel = new Label("Search Cities (Primary Variant)");
        citiesLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXAutocomplete<String> citiesAuto = new CFXAutocomplete<>();
        citiesAuto.setLabel("City");
        citiesAuto.setPromptText("Search cities...");
        citiesAuto.setPrefWidth(400);
        citiesAuto.setVariant(ComboBoxVariant.PRIMARY);
        citiesAuto.setMaxSuggestions(5); // Show max 5 results

        List<String> cities = Arrays.asList(
                "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
                "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
                "London", "Paris", "Tokyo", "Berlin", "Madrid", "Rome",
                "Barcelona", "Amsterdam", "Vienna", "Prague", "Budapest",
                "Sydney", "Melbourne", "Toronto", "Vancouver", "Montreal"
        );
        citiesAuto.setAllItems(FXCollections.observableArrayList(cities));

        Label citiesResult = new Label("Selected: None");
        citiesResult.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        citiesAuto.valueProperty().addListener((obs, old, newVal) -> {
            citiesResult.setText("Selected: " + (newVal != null ? newVal : "None"));
        });

        // ====================================
        // Case Sensitive Search
        // ====================================
        Label caseSensitiveLabel = new Label("Case Sensitive Search");
        caseSensitiveLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXAutocomplete<String> caseSensitiveAuto = new CFXAutocomplete<>();
        caseSensitiveAuto.setLabel("Brands");
        caseSensitiveAuto.setPromptText("Case sensitive search...");
        caseSensitiveAuto.setPrefWidth(400);
        caseSensitiveAuto.setVariant(ComboBoxVariant.SUCCESS);
        caseSensitiveAuto.setCaseSensitive(true);
        caseSensitiveAuto.setHelperText("Search is case-sensitive");

        List<String> brands = Arrays.asList(
                "Apple", "Microsoft", "Google", "Amazon", "Meta",
                "Tesla", "NVIDIA", "AMD", "Intel", "IBM",
                "Oracle", "Salesforce", "Adobe", "Netflix", "Spotify"
        );
        caseSensitiveAuto.setAllItems(FXCollections.observableArrayList(brands));

        // ====================================
        // Email Autocomplete
        // ====================================
        Label emailLabel = new Label("Email Autocomplete");
        emailLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXAutocomplete<String> emailAuto = new CFXAutocomplete<>();
        emailAuto.setLabel("Email Domain");
        emailAuto.setPromptText("@example.com");
        emailAuto.setPrefWidth(400);
        emailAuto.setVariant(ComboBoxVariant.OUTLINED);
        emailAuto.setHelperText("Common email providers");

        List<String> domains = Arrays.asList(
                "@gmail.com", "@yahoo.com", "@outlook.com", "@hotmail.com",
                "@icloud.com", "@protonmail.com", "@aol.com", "@mail.com",
                "@zoho.com", "@yandex.com", "@gmx.com"
        );
        emailAuto.setAllItems(FXCollections.observableArrayList(domains));



        // Add all sections to root
        root.getChildren().addAll(
                title,
                new Separator(),
                outlinedLabel,
                outlinedSection,
                new Separator(),
                filledLabel,
                filledSection,
                new Separator(),
                errorLabel,
                errorSection,
                new Separator(),
                disabledLabel,
                disabledSection,
                new Separator(),
                customLabel,
                customSection,
                new Separator(),
                formLabel,
                formSection,
                new Separator(),
                countriesLabel,
                countriesAuto,
                countriesResult,
                new Separator(),
                languagesLabel,
                languagesAuto,
                languagesResult,
                new Separator(),
                citiesLabel,
                citiesAuto,
                citiesResult,
                new Separator(),
                caseSensitiveLabel,
                caseSensitiveAuto,
                new Separator(),
                emailLabel,
                emailAuto
        );
    }

}