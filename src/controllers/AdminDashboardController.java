package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Course;
import models.Faculty;
import models.Student;
import utils.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminDashboardController {

    @FXML private TableView<Student> student;
    @FXML private TableView<Faculty> instructor;
    @FXML private TableView<Course> course;
    @FXML private AnchorPane mainContent;

    public void initialize() {
        loadStudentData();
        loadFacultyData();
        loadCourseData();
    }

    private void loadStudentData() {
        ObservableList<Student> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnector.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT studentID, lastname, firstname FROM student");

            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("studentID"),
                    rs.getString("lastname"),
                    rs.getString("firstname")
                ));
            }

            student.getColumns().clear();

            TableColumn<Student, Integer> colID = new TableColumn<>("Student ID");
            colID.setCellValueFactory(new PropertyValueFactory<>("studentID"));

            TableColumn<Student, String> colLast = new TableColumn<>("Last Name");
            colLast.setCellValueFactory(new PropertyValueFactory<>("lastname"));

            TableColumn<Student, String> colFirst = new TableColumn<>("First Name");
            colFirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));

            student.getColumns().addAll(colID, colLast, colFirst);
            student.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


private void loadFacultyData() {
    ObservableList<Faculty> list = FXCollections.observableArrayList();

    try {
        Connection conn = DBConnector.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT facultyID, fullname, department FROM faculty");

        while (rs.next()) {
            list.add(new Faculty(
                rs.getInt("facultyID"),  // Faculty ID
                rs.getString("fullname"), // Full Name
                rs.getString("department") // Department
            ));
        }

        instructor.getColumns().clear();  // Clear existing columns before adding new ones

        // Faculty ID column
        TableColumn<Faculty, Integer> colID = new TableColumn<>("Faculty ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("facultyID"));

        // Full Name column
        TableColumn<Faculty, String> colName = new TableColumn<>("Full Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("fullname"));

        // Department column
        TableColumn<Faculty, String> colDept = new TableColumn<>("Department");
        colDept.setCellValueFactory(new PropertyValueFactory<>("department"));

        // Add columns to TableView
        instructor.getColumns().addAll(colID, colName, colDept);

        // Set items to the TableView
        instructor.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCourseData() {
        ObservableList<Course> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnector.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT course, acronym FROM courses");

            while (rs.next()) {
                list.add(new Course(
                    rs.getString("course"),
                    rs.getString("acronym")
                ));
            }

            course.getColumns().clear();

            TableColumn<Course, String> colCourse = new TableColumn<>("Course");
            colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

            TableColumn<Course, String> colAcronym = new TableColumn<>("Acronym");
            colAcronym.setCellValueFactory(new PropertyValueFactory<>("acronym"));

            course.getColumns().addAll(colCourse, colAcronym);
            course.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle button actions to load respective views

    @FXML
    private void handleAddFaculty() {
        loadView("/views/admin/addfaculty.fxml");
    }

    @FXML
    private void handleAddStudent() {
        loadView("/views/admin/addstudent.fxml");
    }

    @FXML
    private void handleAddCourse() {
        loadView("/views/admin/addcourses.fxml");
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click OK to log out, or Cancel to stay.");

        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/loginpanel.fxml"));
                    Stage stage = (Stage) mainContent.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 🔁 This method replaces content inside mainContent AnchorPane
    private void loadView(String fxmlPath) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContent.getChildren().clear();
            mainContent.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
