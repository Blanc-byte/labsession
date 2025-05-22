package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import utils.DBConnector;

public class AddFacultyController {

    @FXML private TextField textFieldFacultyId;
    @FXML private TextField textFieldFullName;
    @FXML private TextField textFieldGender;
    @FXML private TextField textFieldAge;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldContact;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldPassword;
    @FXML private ComboBox<String> comboBoxCourses;

    @FXML private Button buttonAdd;
    @FXML private Button buttonDashboard;
    @FXML private Button buttonAddInstructor;
    @FXML private Button buttonAddStudent;
    @FXML private Button buttonAddCourses;
    @FXML private Button buttonLogOut;
    @FXML private ImageView backButton;

    private Connection conn;

    public AddFacultyController() throws SQLException {
    conn = DBConnector.getConnection();  // Initialize connection here
    if (conn == null) {
        showAlert("Connection Error", "Failed to connect to the database.", Alert.AlertType.ERROR);
    }
}

    @FXML
    private void initialize() {
        loadCourses();  // Load available courses into the combo box
    }

    private void loadCourses() {
        try {
            String sql = "SELECT acronym FROM courses";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comboBoxCourses.getItems().add(rs.getString("acronym"));
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to load courses: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddFaculty(ActionEvent event) {
        String facultyID = textFieldFacultyId.getText();
        String fullname = textFieldFullName.getText();
        String gender = textFieldGender.getText();
        String ageStr = textFieldAge.getText();
        String email = textFieldEmail.getText();
        String contact = textFieldContact.getText();
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        String course = comboBoxCourses.getValue();
        String dateHired = LocalDate.now().toString();

        // Validate that all fields are filled out
        if (facultyID.isEmpty() || fullname.isEmpty() || gender.isEmpty() || ageStr.isEmpty() ||
            email.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty() || course == null) {
            showAlert("Validation Error", "Please fill out all fields.", Alert.AlertType.WARNING);
            return;
        }

        // Validate that age is a number
        if (!ageStr.matches("\\d+")) {
            showAlert("Validation Error", "Age must be a number.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Check if Faculty ID or Username already exists
            String checkSql = "SELECT * FROM faculty WHERE facultyID = ? OR username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, facultyID);
            checkStmt.setString(2, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                showAlert("Duplicate Entry", "Faculty ID or Username already exists.", Alert.AlertType.WARNING);
                return;
            }

            // Insert the new faculty into the database
            String insertSql = "INSERT INTO faculty (facultyID, fullname, gender, age, department, email, contact, datehired, username, password) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, facultyID);
            insertStmt.setString(2, fullname);
            insertStmt.setString(3, gender);
            insertStmt.setInt(4, Integer.parseInt(ageStr));
            insertStmt.setString(5, course);  // Assuming 'course' is stored as department for now
            insertStmt.setString(6, email);
            insertStmt.setString(7, contact);
            insertStmt.setString(8, dateHired);
            insertStmt.setString(9, username);
            insertStmt.setString(10, password);

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                showAlert("Success", "Faculty added successfully.", Alert.AlertType.INFORMATION);
                loadFXML("/views/admin/admindashboard.fxml", event); // Navigate to admin dashboard after success
            }

        } catch (Exception e) {
            showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Handle navigation to different views
    @FXML
    private void handleDashboard(ActionEvent event) {
        loadFXML("/views/admin/admindashboard.fxml", event);
    }

    @FXML
    private void handleAddInstructor(ActionEvent event) {
        loadFXML("/views/admin/addfaculty.fxml", event);
    }

    @FXML
    private void handleAddStudent(ActionEvent event) {
        loadFXML("/views/admin/addstudent.fxml", event);
    }

    @FXML
    private void handleAddCourses(ActionEvent event) {
        loadFXML("/views/admin/addcourses.fxml", event);
    }

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


    private void loadFXML(String path, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showAlert("Navigation Error", "Unable to load view: " + path + "\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
