package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import models.Subject;

public class UpdatingSubject {

    // FXML fields
    @FXML
    private TextField textFieldID, textFieldName, textFieldDescription, textFieldDuration;

    @FXML
    private TableView<Subject> tableViewSubjects; // TableView for Subject data
    @FXML
    private TableColumn<Subject, String> columnID; // Column for Subject ID
    @FXML
    private TableColumn<Subject, String> columnName; // Column for Subject Name
    @FXML
    private TableColumn<Subject, String> columnDescription; // Column for Subject Description
    @FXML
    private TableColumn<Subject, String> columnDuration; // Column for Subject Duration

    // Handle Logout Button Click
    @FXML
    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click OK to logout or Cancel to stay.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            navigateToFXML(event, "/views/loginpanel.fxml");  // Adjust path to your login page
        }
    }

    // Handle Update Button Click
    @FXML
    public void handleUpdateSubject(ActionEvent event) {
        String id = textFieldID.getText();
        String name = textFieldName.getText();
        String desc = textFieldDescription.getText();
        String duration = textFieldDuration.getText();

        // Validation check
        if (id.isEmpty() || name.isEmpty() || desc.isEmpty() || duration.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields must be filled!");
            return;
        }

        // Confirm before updating the subject
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Update");
        confirm.setHeaderText("Do you want to update this subject?");
        confirm.setContentText("ID: " + id + "\nName: " + name + "\nDescription: " + desc + "\nDuration: " + duration);

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Database operation
                Connection conn = DBConnector.getConnection();
                String sql = "UPDATE subject SET name = ?, description = ?, duration = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, desc);
                stmt.setString(3, duration);
                stmt.setString(4, id);
                stmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Subject updated successfully!");

                // Optionally reload the TableView data after update
                loadTableData();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update subject.");
            }
        }
    }

    // Handle TableRow Selection
    @FXML
    public void handleTableSelection() {
        Subject selectedSubject = tableViewSubjects.getSelectionModel().getSelectedItem();
        if (selectedSubject != null) {
            textFieldID.setText(String.valueOf(selectedSubject.getId()));
            textFieldName.setText(selectedSubject.getName());
            textFieldDescription.setText(selectedSubject.getDescription());
            textFieldDuration.setText(selectedSubject.getDuration());
        }
    }

    // Navigate to the Instructor Dashboard
    @FXML
    public void handleDashboard(ActionEvent event) {
        navigateToFXML(event, "/views/instructor/instructordashboard.fxml");  // Adjust path to your instructor dashboard
    }

    // Navigate to the Reports Page
    @FXML
    public void handleReports(ActionEvent event) {
        navigateToFXML(event, "/views/instructor/reports.fxml");  // Adjust path to your reports page
    }

    // Navigate to the Subject Management Page
    @FXML
    public void handleSubject(ActionEvent event) {
        navigateToFXML(event, "/views/instructor/subject.fxml");  // Adjust path to your subject page
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

    // Load Table Data (Load data from your database to populate the TableView)
    private void loadTableData() {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM subject";  // Adjust to your actual table and query
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            tableViewSubjects.getItems().clear();  // Clear existing data in TableView
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String duration = rs.getString("duration");
                
                // Adding the data to the TableView
                tableViewSubjects.getItems().add(new Subject(id, name, description, duration));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load subject data.");
        }
    }

    // Initialize the TableView Columns
    @FXML
    public void initialize() {
        columnID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        columnDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        columnDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()));
        
        loadTableData();  // Load data from the database
    }
}
