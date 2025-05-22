package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import utils.DBConnector;

public class AddCourseController {

    // FXML Fields
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldEmail;  // FULL COURSE
    @FXML private TextField textFieldContact; // ACRONYM
    @FXML private Button buttonAdd;
    @FXML private Button buttonLogOut;
    @FXML private Button buttonDashboard;
    @FXML private Button buttonAddStudent;
    @FXML private Button buttonAddInstructor;
    @FXML private Button buttonAddCourses;
    @FXML private ImageView imageViewLogo;

    // Initialize method for setup
    @FXML
    public void initialize() {
        imageViewLogo.setImage(new Image(getClass().getResourceAsStream("/images/logo2.png")));
    }

    // Handle the "ADD" button click to add a course
    @FXML
    private void handleAddCourse(ActionEvent event) {
        String fullCourse = textFieldEmail.getText();
        String acronym = textFieldContact.getText();

        if (fullCourse.isEmpty() || acronym.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Both fields are required!");
        } else {
            // Insert the course into the database
            insertCourseIntoDatabase(fullCourse, acronym);
        }
    }

    // Insert the course into the database
    private void insertCourseIntoDatabase(String courseName, String acronym) {
        String query = "INSERT INTO courses (course, acronym) VALUES (?, ?)";

        try (Connection connection = DBConnector.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the parameters for the query
            statement.setString(1, courseName);
            statement.setString(2, acronym);

            // Execute the update query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course successfully added!");
                // Clear fields and navigate to Admin Dashboard
                textFieldEmail.clear();
                textFieldContact.clear();
                navigateToAdminDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error: " + e.getMessage());
        }
    }

    // Show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigate to Admin Dashboard
    private void navigateToAdminDashboard() {
        loadView("/views/admin/admindashboard.fxml");
    }

    // General method to load views
    private void loadView(String viewPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Parent root = loader.load();
            Stage stage = (Stage) buttonAdd.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle Log Out
@FXML
private void handleLogOut(ActionEvent event) throws Exception {
    // Create a confirmation alert
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Logout");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to log out?");

    // Show the alert and wait for user response
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        // User confirmed logout
        Stage stage = (Stage) buttonLogOut.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginpanel.fxml"));
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(loader.load()));
        loginStage.show();
    }
    // If user canceled, do nothing
}


    // Handle navigation to Add Student
    @FXML
    private void handleAddStudent(ActionEvent event) {
        loadView("/views/admin/addstudent.fxml");
    }

    // Handle navigation to Add Instructor
    @FXML
    private void handleAddInstructor(ActionEvent event) {
        loadView("/views/admin/addfaculty.fxml");
    }

    // Handle navigation to Admin Dashboard
    @FXML
    private void handleDashboard(ActionEvent event) {
        loadView("/views/admin/admindashboard.fxml");
    }
}
