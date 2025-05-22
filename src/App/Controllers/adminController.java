/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Controllers;

import App.DatabaseConnection;
import App.Models.FacultyView;
import App.Models.StudentView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Blanc
 */

public class adminController {
    DatabaseConnection dc = new DatabaseConnection();
    public void initialize()throws Exception{
        dc.connect();
        loadFacultyTable();
        loadStudents();
    }
    
    public void handleAddStudent() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Student");

        // Fields for student info
        TextField firstname = new TextField();
        TextField lastname = new TextField();
        TextField gender = new TextField();
        TextField age = new TextField();
        TextField address = new TextField();
        TextField email = new TextField();
        TextField contact = new TextField();
        TextField username = new TextField();
        PasswordField password = new PasswordField();

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("First Name:"), firstname);
        grid.addRow(1, new Label("Last Name:"), lastname);
        grid.addRow(2, new Label("Gender:"), gender);
        grid.addRow(3, new Label("Age:"), age);
        grid.addRow(4, new Label("Address:"), address);
        grid.addRow(5, new Label("Email:"), email);
        grid.addRow(6, new Label("Contact:"), contact);
        grid.addRow(7, new Label("Username:"), username);
        grid.addRow(8, new Label("Password:"), password);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Show the dialog and process result
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    PreparedStatement insert = dc.con.prepareStatement(
                        "INSERT INTO student (lastname, firstname, gender, age, address, email, contact, username, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    );

                    insert.setString(1, lastname.getText());
                    insert.setString(2, firstname.getText());
                    insert.setString(3, gender.getText());
                    insert.setString(4, age.getText());
                    insert.setString(5, address.getText());
                    insert.setString(6, email.getText());
                    insert.setString(7, contact.getText());
                    insert.setString(8, username.getText());
                    insert.setString(9, password.getText());

                    int rows = insert.executeUpdate();
                    insert.close();

                    if (rows > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student added successfully.");
                        alert.showAndWait();
                        loadStudents();  // refresh table
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add student.");
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML private TableView<StudentView> studentTable;
    @FXML private TableColumn<StudentView, String> fname, lname, email, user, pass;
    @FXML private TableColumn<StudentView, Void> actions;
    
    ObservableList<StudentView> studentList = FXCollections.observableArrayList();
    public void loadStudents() throws SQLException {
        studentList.clear();

        fname.setCellValueFactory(data -> data.getValue().firstnameProperty());
        lname.setCellValueFactory(data -> data.getValue().lastnameProperty());
        email.setCellValueFactory(data -> data.getValue().emailProperty());
        user.setCellValueFactory(data -> data.getValue().usernameProperty());
        pass.setCellValueFactory(data -> data.getValue().passwordProperty());
        
        PreparedStatement stmt = dc.con.prepareStatement("SELECT * FROM student");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            studentList.add(new StudentView(
                rs.getString("studentID"),
                rs.getString("lastname"),
                rs.getString("firstname"),
                rs.getString("gender"),
                rs.getString("age"),
                rs.getString("address"),
                rs.getString("email"),
                rs.getString("contact"),
                rs.getString("username"),
                rs.getString("password")
            ));
        }
        actions.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox actionBox = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> {
                    StudentView student = getTableView().getItems().get(getIndex());
                    showEditDialog(student);
                });

                deleteBtn.setOnAction(e -> {
                    StudentView student = getTableView().getItems().get(getIndex());
                    deleteStudent(student.getStudentID());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });

        rs.close();
        stmt.close();

        studentTable.setItems(studentList);
    }
    private void showEditDialog(StudentView student) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");

        // Create fields
        TextField firstname = new TextField(student.getFirstname());
        TextField lastname = new TextField(student.getLastname());
        TextField email = new TextField(student.getEmail());
        TextField username = new TextField(student.getUsername());
        PasswordField password = new PasswordField();
        password.setText(student.getPassword());

        VBox content = new VBox(10,
            new Label("First Name:"), firstname,
            new Label("Last Name:"), lastname,
            new Label("Email:"), email,
            new Label("Username:"), username,
            new Label("Password:"), password
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                
                String updateQuery = "UPDATE student SET firstname=?, lastname=?, email=?, username=?, password=? WHERE studentID=?";
                PreparedStatement stmt;
                try {
                    stmt = dc.con.prepareStatement(updateQuery);
                    stmt.setString(1, firstname.getText());
                    stmt.setString(2, lastname.getText());
                    stmt.setString(3, email.getText());
                    stmt.setString(4, username.getText());
                    stmt.setString(5, password.getText());
                    stmt.setString(6, student.getStudentID());

                    stmt.executeUpdate();
                    stmt.close();
                    loadStudents(); // Refresh table
                } catch (SQLException ex) {
                    Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    private void deleteStudent(String studentID) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this student?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try{
                    String deleteQuery = "DELETE FROM student WHERE studentID=?";
                    PreparedStatement stmt = dc.con.prepareStatement(deleteQuery);
                    stmt.setString(1, studentID);
                    stmt.executeUpdate();
                    stmt.close();
                    loadStudents(); // Refresh table
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    
    //----------------------- INSTRUCTOR -----------------------//
    
    @FXML private TableView<FacultyView> facultyTable;
    @FXML private TableColumn<FacultyView, String> facFullname, facEmail, facPassword, facUsername;
    @FXML private TableColumn<FacultyView, Void> facActions;
    
    ObservableList<FacultyView> facultyList = FXCollections.observableArrayList();
    public void loadFacultyTable() throws Exception {
        facultyList.clear();

        // Sample query – use your actual database access here
        PreparedStatement ps = dc.con.prepareStatement("SELECT * FROM faculty");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            facultyList.add(new FacultyView(
                    rs.getString("facultyID"),
                    rs.getString("fullname"),
                    rs.getString("gender"),
                    rs.getString("age"),
                    rs.getString("email"),
                    rs.getString("contact"),
                    rs.getString("datehired"),
                    rs.getString("username"),
                    rs.getString("password")
            ));
        }

        rs.close();
        ps.close();

        // Set columns
        facFullname.setCellValueFactory(data -> data.getValue().fullnameProperty());
        facEmail.setCellValueFactory(data -> data.getValue().emailProperty());
        facUsername.setCellValueFactory(data -> data.getValue().usernameProperty());
        facPassword.setCellValueFactory(data -> data.getValue().passwordProperty());

        // Set actions column
        facActions.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("EDIT");
            private final Button deleteBtn = new Button("DELETE");
            private final HBox btnBox = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> {
                    FacultyView selected = getTableView().getItems().get(getIndex());
                    try {
                        showEditDialog(selected);
                    } catch (Exception ex) {
                        Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                deleteBtn.setOnAction(e -> {
                    FacultyView selected = getTableView().getItems().get(getIndex());
                    try {
                        deleteFaculty(selected.getFacultyID());
                        loadFacultyTable(); // Refresh table
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnBox);
            }
        });

        facultyTable.setItems(facultyList);
    }
    public void showEditDialog(FacultyView faculty)throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Faculty Info");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField fullnameField = new TextField(faculty.getFullname());
        TextField emailField = new TextField(faculty.getEmail());
        TextField usernameField = new TextField(faculty.getUsername());
        PasswordField passwordField = new PasswordField();
        passwordField.setText(faculty.getPassword());

        grid.addRow(0, new Label("Full Name:"), fullnameField);
        grid.addRow(1, new Label("Email:"), emailField);
        grid.addRow(2, new Label("Username:"), usernameField);
        grid.addRow(3, new Label("Password:"), passwordField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                PreparedStatement ps = dc.con.prepareStatement(
                    "UPDATE faculty SET fullname=?, email=?, username=?, password=? WHERE facultyID=?"
                );
                ps.setString(1, fullnameField.getText());
                ps.setString(2, emailField.getText());
                ps.setString(3, usernameField.getText());
                ps.setString(4, passwordField.getText());
                ps.setString(5, faculty.getFacultyID());

                ps.executeUpdate();
                ps.close();

                loadFacultyTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteFaculty(String facultyID) throws SQLException {
        PreparedStatement ps = dc.con.prepareStatement("DELETE FROM faculty WHERE facultyID=?");
        ps.setString(1, facultyID);
        ps.executeUpdate();
        ps.close();
    }
    public void showAddFacultyDialog() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Faculty");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField fullnameField = new TextField();
        TextField genderField = new TextField();
        TextField ageField = new TextField();
        TextField emailField = new TextField();
        TextField contactField = new TextField();
        DatePicker dateHiredPicker = new DatePicker();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        grid.addRow(0, new Label("Full Name:"), fullnameField);
        grid.addRow(1, new Label("Gender:"), genderField);
        grid.addRow(2, new Label("Age:"), ageField);
        grid.addRow(3, new Label("Email:"), emailField);
        grid.addRow(4, new Label("Contact:"), contactField);
        grid.addRow(5, new Label("Date Hired:"), dateHiredPicker);
        grid.addRow(6, new Label("Username:"), usernameField);
        grid.addRow(7, new Label("Password:"), passwordField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                PreparedStatement ps = dc.con.prepareStatement(
                    "INSERT INTO faculty (fullname, gender, age, email, contact, datehired, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                );

                ps.setString(1, fullnameField.getText());
                ps.setString(2, genderField.getText());
                ps.setString(3, ageField.getText());
                ps.setString(4, emailField.getText());
                ps.setString(5, contactField.getText());
                ps.setDate(6, java.sql.Date.valueOf(dateHiredPicker.getValue()));
                ps.setString(7, usernameField.getText());
                ps.setString(8, passwordField.getText());

                int rowsInserted = ps.executeUpdate();
                ps.close();

                if (rowsInserted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Faculty added successfully.");
                    alert.showAndWait();
                    loadFacultyTable(); // Refresh the table
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add faculty.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).showAndWait();
            }
        }
    }
    
    @FXML private Pane studentPane, facultyPane;
    public void studentClick()throws Exception{
        studentPane.setVisible(true);
        facultyPane.setVisible(false);
        loadStudents();
    }
    public void facultyClick()throws Exception{
        studentPane.setVisible(false);
        facultyPane.setVisible(true);
        loadFacultyTable();
    }
}
