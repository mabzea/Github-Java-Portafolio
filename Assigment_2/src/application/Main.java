package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            showCalculatorDialog();   // Launches the custom calculator dialog
            primaryStage.close();     // Close the default empty stage since we only use dialogs
        } catch (Exception e) {
            e.printStackTrace();      // Print errors if something goes wrong
        }
    }

    // Method to create and display the calculator dialog
    private void showCalculatorDialog() {
        // Create a dialog box for input and results
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Wire Resistance Calculator");       // Title of the dialog
        dialog.setHeaderText("Enter wire properties below."); // Header text inside dialog
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL); // Add OK and Cancel buttons

        // GridPane → allows arranging controls in a grid (rows and columns)
        GridPane grid = new GridPane();
        grid.setHgap(10); // Horizontal gap between columns
        grid.setVgap(10); // Vertical gap between rows
        grid.setPadding(new Insets(20, 150, 10, 10)); // Padding around grid 

        // Create text fields for user input with placeholder text
        TextField resistivityField = new TextField();
        resistivityField.setPromptText("e.g., 1.78e-8"); 
        TextField lengthField = new TextField();
        lengthField.setPromptText("in meters");
        TextField diameterField = new TextField();
        diameterField.setPromptText("in millimeters");
        TextField currentField = new TextField();
        currentField.setPromptText("in Amperes");

        // Add input labels and fields to grid
        grid.add(new Label("Resistivity (ρ) in Ωm:"), 0, 0);
        grid.add(resistivityField, 1, 0);
        grid.add(new Label("Length (L) in m:"), 0, 1);
        grid.add(lengthField, 1, 1);
        grid.add(new Label("Diameter (D) in mm:"), 0, 2);
        grid.add(diameterField, 1, 2);
        grid.add(new Label("Current (I) in A:"), 0, 3);
        grid.add(currentField, 1, 3);

        // Result display section
        Label resultLabel = new Label("Results:");
        TextArea resultArea = new TextArea();   
        resultArea.setEditable(false);          
        resultArea.setPrefWidth(400);           
        resultArea.setPrefHeight(100);          
        resultArea.setWrapText(true);           
        grid.add(resultLabel, 0, 4);
        grid.add(resultArea, 1, 4);

        // Clear button → resets all fields and results
        Button clearButton = new Button("Clear");
        grid.add(clearButton, 1, 5); // Add to grid
        clearButton.setOnAction(e -> { // When clicked, clear everything
            resistivityField.clear();
            lengthField.clear();
            diameterField.clear();
            currentField.clear();
            resultArea.clear();
        });

        // Add grid layout to dialog content
        dialog.getDialogPane().setContent(grid);

        // Get the OK button from the dialog and attach custom logic
        var okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                // Parse user input into numbers
                double p = Double.parseDouble(resistivityField.getText());
                double l = Double.parseDouble(lengthField.getText());
                double d = Double.parseDouble(diameterField.getText());
                double i = Double.parseDouble(currentField.getText());

                // Validate → all values must be positive
                if (p <= 0 || l <= 0 || d <= 0 || i <= 0) throw new NumberFormatException();

                // Convert diameter from mm to meters and calculate radius
                double diameterMeters = d / 1000.0;
                double radiusMeters = diameterMeters / 2.0;

                // Calculate cross-sectional area (πr²)
                double area = Math.PI * Math.pow(radiusMeters, 2);

                // Calculate resistance R = ρ * (L / A)
                double resistance = p * (l / area);

                // Calculate potential difference U = R * I
                double potentialDifference = resistance * i;

                // Format results into a readable string
                String resultMessage = String.format(
                        "Wire cross-section area: %.8f m²\n" +
                        "Electrical Resistance (R): %.6f Ω\n" +
                        "Potential Difference (U): %.4f V",
                        area, resistance, potentialDifference
                );

                // Display results in text area
                resultArea.setText(resultMessage);

                // Prevent the dialog from closing after clicking OK
                event.consume();

            } catch (NumberFormatException e) {
                // If parsing fails → show error message
                event.consume(); // Stop dialog from closing
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Please enter valid positive numbers for all fields.");
                alert.showAndWait();

                // Reset input and results if invalid
                resistivityField.clear();
                lengthField.clear();
                diameterField.clear();
                currentField.clear();
                resultArea.clear();
            }
        });

        // Show dialog and wait for user interaction
        dialog.showAndWait();
    }

    // Main method → launches JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}