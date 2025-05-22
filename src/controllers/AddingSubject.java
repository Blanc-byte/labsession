package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AddingSubject {

    // FXML fields
    @FXML
    private TextField textFieldID, textFieldName, textFieldDescription, textFieldDuration;

    // Handle Logout Button Click
    @FXML
    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click OK to logout or Cancel to stay.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            navigateToFXML(event, "/views/loginpanel.fxml");  // Adjust the path for your login page
        }
    }

    // Handle Add Subject Button Click
    @FXML
    public void handleAddSubject(ActionEvent event) {
        String id = textFieldID.getText();
        String name = textFieldName.getText();
        String description = textFieldDescription.getText();
        String duration = textFieldDuration.getText();

        // Validation check
        if (id.isEmpty() || name.isEmpty() || description.isEmpty() || duration.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields must be filled!");
            return;
        }

        // Confirm before adding the subject
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Add Subject");
        confirm.setHeaderText("Do you want to add this subject?");
        confirm.setContentText("ID: " + id + "\nName: " + name + "\nDescription: " + description + "\nDuration: " + duration);

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Database operation
                Connection conn = DBConnector.getConnection();
                if (conn == null) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to the database.");
                    return;
                }

                // Check if subject with same ID already exists
                String checkQuery = "SELECT COUNT(*) FROM subject WHERE id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setString(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert(Alert.AlertType.WARNING, "Duplicate Error", "Subject with this ID already exists.");
                    return;
                }

                // Insert new subject
                String sql = "INSERT INTO subject (id, name, description, duration) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setString(3, description);
                stmt.setString(4, duration);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Subject added successfully!");
                    navigateToFXML(event, "/views/instructor/subject.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the subject.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add subject. Error: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Unknown Error", "An unexpected error occurred.");
            }
        }
    }

    // Navigate to the Instructor Dashboard
    @FXML
    public void handleDashboard(ActionEvent event) {
        navigateToFXML(event, "/views/instructor/instructordashboard.fxml");  // Adjust the path for your instructor dashboard
    }

    // Navigate to the Reports Page
    @FXML
    public void handleReports(ActionEvent event) {
        navigateToFXML(event, "/views/instructor/reports.fxml");  // Adjust the path for your reports page
    }

    // Navigate to the Subject Management Page
    @FXML
    public void handleSubject(ActionEvent event) {
        navigateToFXML(event, "/views/instructor/subject.fxml");  // Adjust the path for your subject page
    }

    // Utility method for navigating to different FXML views
    private void navigateToFXML(ActionEvent event, String fxmlPath) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load view: " + fxmlPath);
        }
    }

    // Utility method to show different types of alerts
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
