package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Subject;
import utils.DBConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

public class AddSubject implements Initializable {

    @FXML private TableView<Subject> tableViewSubjects;
    @FXML private TableColumn<Subject, Integer> colId;
    @FXML private TableColumn<Subject, String> colName;
    @FXML private TableColumn<Subject, String> colDescription;
    @FXML private TableColumn<Subject, String> colDuration;

    @FXML private Button btnAddSubject, btnDelete, btnUpdate, btnCreateSession;
    @FXML private Button btnDashboard, btnReports, btnSubject, btnLogout;

    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conn = DBConnector.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AddSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (conn == null) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Connection to database failed.");
            return;
        }
        setupTable();
        loadSubjects();
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadSubjects() {
        tableViewSubjects.getItems().clear();
        String query = "SELECT * FROM subject";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("duration")
                );
                tableViewSubjects.getItems().add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Load Error", "Failed to load subjects from database.");
        }
    }

    @FXML
    private void handleAddSubject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addsubject.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Subject");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadSubjects(); // Refresh after closing form
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Subject selected = tableViewSubjects.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a subject to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Confirmation");
        confirm.setHeaderText("Are you sure you want to delete this subject?");
        confirm.setContentText(selected.getName());

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String query = "DELETE FROM subject WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, selected.getId());
                ps.executeUpdate();
                loadSubjects();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Delete Error", "Failed to delete subject.");
            }
        }
    }

    @FXML
    private void handleUpdate() {
         navigate("/views/instructor/UpdatingSubject.fxml");
    }

    @FXML private void handleDashboard() {
        navigate("/views/instructor/instructordashboard.fxml");
    }

    @FXML private void handleReports() {
        navigate("/views/instructor/reports.fxml");
    }
 @FXML
    public void handleAddSubject(ActionEvent event) {
        try {
            // Load the AddingSubject.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/instructor/AddingSubject.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the AddingSubject view
            Stage stage = new Stage();
            stage.setTitle("Adding Subject");
            stage.setScene(new Scene(root));
            stage.show();

            // Optionally, close the current window
            // ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click Yes to log out or Cancel to stay.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            navigate("/views/loginpanel.fxml");
        }
    }

    @FXML
    private void handleCreateSession() {
        Subject selectedSubject = tableViewSubjects.getSelectionModel().getSelectedItem();

        if (selectedSubject == null) {
            showAlert(Alert.AlertType.WARNING, "No Subject Selected", "Please select a subject from the table first.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Session Code");
        dialog.setHeaderText("Create Session for Subject: " + selectedSubject.getName());
        dialog.setContentText("Please enter the session code:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(code -> {
            if (code.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Code", "Session code cannot be empty.");
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/instructor/session.fxml"));
                    AnchorPane root = loader.load();

                    // Optionally, pass subject or session code to the next controller here

                    Stage stage = (Stage) tableViewSubjects.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load session.fxml");
                }
            }
        });
    }

    private void navigate(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane root = loader.load();
            Stage stage = (Stage) tableViewSubjects.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
