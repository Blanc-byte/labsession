package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Task;
import models.Attendance;
import models.Performance;
import utils.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FacultyDashboardController {

    @FXML private TableView<Task> tasksTableView;
    @FXML private TableColumn<Task, String> taskColumn;
    @FXML private TableColumn<Task, String> statusColumn;

    @FXML private TableView<Attendance> attendanceTableView;
    @FXML private TableColumn<Attendance, String> studentColumn;
    @FXML private TableColumn<Attendance, String> attendanceColumn;

    @FXML private TableView<Performance> performanceTableView;
    @FXML private TableColumn<Performance, String> performanceStudentColumn;
    @FXML private TableColumn<Performance, String> scoreColumn;

    @FXML private Button logoutButton;

    @FXML
    public void initialize() {
        // Setup columns
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        studentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendance"));

        performanceStudentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Load data
        loadTasksData();
        loadAttendanceData();
        loadPerformanceData();
    }

    private void loadTasksData() {
        String query = "SELECT task, status FROM tasks";
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                tasksTableView.getItems().add(new Task(rs.getString("task"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAttendanceData() {
        String query = "SELECT student_name, attendance FROM attendance";
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                attendanceTableView.getItems().add(new Attendance(rs.getString("student_name"), rs.getString("attendance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPerformanceData() {
        String query = "SELECT student_name, score FROM performance";
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                performanceTableView.getItems().add(new Performance(rs.getString("student_name"), rs.getString("score")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click 'Yes' to log out, or 'Cancel' to stay.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    Stage stage = (Stage) logoutButton.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/views/login/LoginPanel.fxml"));
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Log Out Failed", "Unable to load the login screen.");
                }
            }
        });
    }

    @FXML
    private void navigateToReports() {
        loadView("/views/instructor/reports.fxml");
    }

    @FXML
    private void navigateToSubject() {
        loadView("/views/instructor/subject.fxml");
    }

    // Helper method to load FXML views
    private void loadView(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) logoutButton.getScene().getWindow(); // You can change to a more relevant button if needed
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Navigation Error", "Failed to load view: " + fxmlPath);
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
