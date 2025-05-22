package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonType;

public class AddStudentController {

    // FXML Components
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldFirstName;
    @FXML private TextField textFieldLastName;
    @FXML private TextField textFieldAge;
    @FXML private TextField textFieldGender;
    @FXML private TextField textFieldAddress;
    @FXML private TextField textFieldContact;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldPassword;
    @FXML private Button buttonAdd;
    @FXML private Button buttonLogOut;
    @FXML private Button buttonDashboard;
    @FXML private Button buttonAddStudent;
    @FXML private Button buttonAddInstructor;
    @FXML private Button buttonAddCourses;

    // Method to handle Add Student logic
    @FXML
    private void handleAddStudent(ActionEvent event) {
        // Get the data from the input fields
        String studentId = textFieldStudentId.getText();  // studentID
        String firstName = textFieldFirstName.getText();  // first name
        String lastName = textFieldLastName.getText();    // last name
        String gender = textFieldGender.getText();
        String age = textFieldAge.getText();
        String address = textFieldAddress.getText();       // Using Contact as Address
        String email = textFieldEmail.getText();           // Email
        String contact = textFieldContact.getText();       // Contact
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        // Validation to ensure all fields are filled
        if (studentId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() ||
            age.isEmpty() || address.isEmpty() || email.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error", "Please fill in all fields.");
            return;
        }

        // Insert the student into the database
        String insertQuery = "INSERT INTO `student`(`studentID`, `lastname`, `firstname`, `gender`, `age`, `address`, `email`, `contact`, `username`, `password`) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, Integer.parseInt(studentId));   // studentID
            pstmt.setString(2, lastName);                    // lastname
            pstmt.setString(3, firstName);                   // firstname
            pstmt.setString(4, gender);                      // gender
            pstmt.setInt(5, Integer.parseInt(age));          // age
            pstmt.setString(6, address);                     // address
            pstmt.setString(7, email);                       // email
            pstmt.setString(8, contact);                     // contact
            pstmt.setString(9, username);                    // username
            pstmt.setString(10, password);                   // password

            int result = pstmt.executeUpdate();
            if (result > 0) {
                // Successful insert, navigate to Admin Dashboard
                showAlert(AlertType.INFORMATION, "Success", "Student added successfully.");
                try {
                    navigateToAdminDashboard();  // Navigate to the Admin Dashboard after success
                } catch (Exception ex) {
                    Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                showAlert(AlertType.ERROR, "Database Error", "Failed to add student.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Error adding student to database: " + e.getMessage());
        }
    }

    // Handle LogOut with confirmation
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
            // User clicked Yes - proceed to login panel
            Stage stage = (Stage) buttonLogOut.getScene().getWindow();
            stage.close(); // Close current window

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginpanel.fxml"));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loader.load()));
            loginStage.show();
        }
        // Else: user clicked Cancel - do nothing
    }

    // Method to navigate back to Dashboard
    @FXML
    private void handleDashboard(ActionEvent event) throws Exception {
        navigateToAdminDashboard();
    }

    // Navigate to Admin Dashboard FXML
    private void navigateToAdminDashboard() throws Exception {
        Stage stage = (Stage) buttonAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/admindashboard.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    // Show alert message
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigation Button to Add Student Screen
    @FXML
    private void handleAddStudentNavigation(ActionEvent event) throws Exception {
        navigateToScreen("/views/admin/addstudent.fxml");
    }

    // Navigation Button to Add Instructor Screen
    @FXML
    private void handleAddInstructorNavigation(ActionEvent event) throws Exception {
        navigateToScreen("/views/admin/addfaculty.fxml");
    }

    // Navigation Button to Add Courses Screen
    @FXML
    private void handleAddCoursesNavigation(ActionEvent event) throws Exception {
        navigateToScreen("/views/admin/addcourses.fxml");
    }

    // General method for screen navigation
    private void navigateToScreen(String fxmlPath) throws Exception {
        Stage stage = (Stage) buttonAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
