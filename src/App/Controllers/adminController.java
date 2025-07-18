/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Controllers;

import App.DatabaseConnection;
import App.Models.FacultyView;
import App.Models.StudentView;
import App.Models.TaskSubjectView;
import App.Models.subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

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
        ComboBox<String> gender = new ComboBox<>();
        gender.getItems().addAll("Male", "Female");
        gender.setValue("Male");

        DatePicker birthday = new DatePicker();
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
        grid.addRow(3, new Label("Birthday:"), birthday);
        grid.addRow(4, new Label("Address:"), address);
        grid.addRow(5, new Label("Email:"), email);
        grid.addRow(6, new Label("Contact:"), contact);
        grid.addRow(7, new Label("Username:"), username);
        grid.addRow(8, new Label("Password:"), password);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                // Basic checks
                if (firstname.getText().isEmpty() || lastname.getText().isEmpty() ||
                    username.getText().isEmpty() || password.getText().isEmpty()) {
                    showAlert("Please fill in all required fields (First Name, Last Name, Username, Password).");
                    return;
                }

                if (birthday.getValue() == null) {
                    showAlert("Please select a birthday.");
                    return;
                }

                if (!isValidPhilippineContact(contact.getText())) {
                    showAlert("Please enter a valid Philippine contact number.");
                    return;
                }

                if (!isValidEmail(email.getText())) {
                    showAlert("Please enter a valid email address.");
                    return;
                }

                // ✅ Check if username already exists
                try {
                    PreparedStatement checkUser = dc.con.prepareStatement(
                        "SELECT * FROM student WHERE username = ?"
                    );
                    checkUser.setString(1, username.getText().trim());
                    ResultSet rs = checkUser.executeQuery();
                    if (rs.next()) {
                        showAlert("Username already exists. Please choose a different one.");
                        rs.close();
                        checkUser.close();
                        return;
                    }
                    rs.close();
                    checkUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Database error while checking username.");
                    return;
                }

                // ✅ Validate password: max 8 chars and at least one uppercase letter
                String pwd = password.getText().trim();
                if (pwd.length() > 8 || !pwd.matches(".*[A-Z].*")) {
                    showAlert("Password must have at least one capital letter and be maximum 8 characters long.");
                    return;
                }

                // ✅ Calculate age
                int age = Period.between(birthday.getValue(), LocalDate.now()).getYears();

                try {
                    PreparedStatement insert = dc.con.prepareStatement(
                        "INSERT INTO student " +
                        "(lastname, firstname, gender, age, birthday, address, email, contact, username, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    );

                    insert.setString(1, lastname.getText());
                    insert.setString(2, firstname.getText());
                    insert.setString(3, gender.getValue());
                    insert.setInt(4, age);
                    insert.setDate(5, Date.valueOf(birthday.getValue()));
                    insert.setString(6, address.getText());
                    insert.setString(7, email.getText());
                    insert.setString(8, contact.getText());
                    insert.setString(9, username.getText());
                    insert.setString(10, pwd);

                    int rows = insert.executeUpdate();
                    insert.close();

                    if (rows > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student added successfully.");
                        alert.showAndWait();
                        loadStudents();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Failed to add student.\n" + ex.getMessage());
                }
            }
        });
    }


    // ✅ Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }

    // ✅ Philippine contact: starts with 09 or +639, must be 11 or 13 chars
    private boolean isValidPhilippineContact(String contact) {
        return contact.matches("^(09\\d{9}|\\+639\\d{9})$");
    }

    // ✅ Simple email pattern (basic)
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
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
    @FXML private TableColumn<FacultyView, String> facFullname, facEmail, facPassword, facUsername,status;
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
                    rs.getString("password"),
                    rs.getString("status")
            ));
        }

        rs.close();
        ps.close();

        // Set columns
        facFullname.setCellValueFactory(data -> data.getValue().fullnameProperty());
        facEmail.setCellValueFactory(data -> data.getValue().emailProperty());
        facUsername.setCellValueFactory(data -> data.getValue().usernameProperty());
        facPassword.setCellValueFactory(data -> data.getValue().passwordProperty());
        status.setCellValueFactory(data -> data.getValue().statusProperty());

        // Set actions column
        facActions.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("EDIT");
            private final Button deleteBtn = new Button("DELETE");
            private final Button viewBtn = new Button("SUBJECTS");
            private final HBox btnBox = new HBox(5, editBtn, deleteBtn, viewBtn);

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
                
                viewBtn.setOnAction(e -> {
                    FacultyView selected = getTableView().getItems().get(getIndex());
                    if (selected == null) return;

                    Dialog<Void> dialog = new Dialog<>();
                    dialog.setTitle("Subjects of " + selected.getFullname());

                    // Close Button
                    ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().add(closeButtonType);

                    TableView<subject> subjectTable = new TableView<>();
                    subjectTable.setPrefWidth(400);

                    TableColumn<subject, String> codeCol = new TableColumn<>("Code");
                    codeCol.setCellValueFactory(data -> data.getValue().codeProperty());

                    TableColumn<subject, String> descCol = new TableColumn<>("Description");
                    descCol.setCellValueFactory(data -> data.getValue().descriptionProperty());

                    TableColumn<subject, String> semCol = new TableColumn<>("Semester");
                    semCol.setCellValueFactory(data -> data.getValue().semProperty());

                    TableColumn<subject, String> yearCol = new TableColumn<>("Year");
                    yearCol.setCellValueFactory(data -> data.getValue().yearProperty());

                    TableColumn<subject, Void> actionCol = new TableColumn<>("Actions");
                    actionCol.setCellFactory(col -> new TableCell<>() {
                        private final Button taskBtn = new Button("View Tasks");

                        {
                            taskBtn.setOnAction(evt -> {
                                subject subj = getTableView().getItems().get(getIndex());
                                showTasksDialog(subj); // helper method to display tasks
                            });
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(taskBtn);
                            }
                        }
                    });

                    subjectTable.getColumns().addAll(codeCol, descCol, semCol, yearCol, actionCol);

                    // Load subjects from database
                    ObservableList<subject> subjectList = FXCollections.observableArrayList();
                    try {
                        PreparedStatement stmt = dc.con.prepareStatement("SELECT id, code, description, instructor_id, sem, year FROM subject WHERE instructor_id = ?");
                        stmt.setString(1, selected.getFacultyID());
                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                            subject s = new subject(
                                rs.getString("id"),
                                rs.getString("code"),
                                rs.getString("description"),
                                rs.getString("sem"),
                                rs.getString("year")
                            );
                            subjectList.add(s);
                        }

                        rs.close();
                        stmt.close();
//                        dc.con.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    subjectTable.setItems(subjectList);
                    subjectTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    VBox content = new VBox(10, subjectTable);
                    content.setPadding(new Insets(10));
                    dialog.getDialogPane().setContent(content);
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.showAndWait();
                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnBox);
            }
        });
        facultyTable.setRowFactory(tv -> new TableRow<FacultyView>() {
            @Override
            protected void updateItem(FacultyView item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    String status = item.getStatus();
                    if ("Active".equalsIgnoreCase(status)) {
                        setStyle("-fx-background-color: #d4f8d4;"); // Light green
                    } else if ("Inactive".equalsIgnoreCase(status)) {
                        setStyle("-fx-background-color: #f8d4d4;"); // Light red
                    } else {
                        setStyle(""); // Reset style
                    }
                }
            }
        });
        facultyTable.setItems(facultyList);
    }
    
    private void showTasksDialog(subject subj) {
        Dialog<Void> taskDialog = new Dialog<>();
        taskDialog.setTitle("Tasks for Subject: " + subj.getCode());

        ButtonType closeBtn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        taskDialog.getDialogPane().getButtonTypes().add(closeBtn);

        TableView<TaskSubjectView> taskTable = new TableView<>();
        taskTable.setPrefWidth(1920 * 0.75);
        taskTable.setPrefHeight(1080 * 0.5);

        TableColumn<TaskSubjectView, String> taskCol = new TableColumn<>("Task");
        taskCol.setCellValueFactory(data -> data.getValue().taskProperty());
        
        TableColumn<TaskSubjectView, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> data.getValue().taskDescriptionProperty());
        descCol.setPrefWidth(400);

        TableColumn<TaskSubjectView, String> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(data -> data.getValue().durationProperty());

        TableColumn<TaskSubjectView, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());

        TableColumn<TaskSubjectView, String> dateCol = new TableColumn<>("Date Submitted");
        dateCol.setCellValueFactory(data -> data.getValue().dateSubProperty());

        taskTable.getColumns().addAll(taskCol, descCol, durationCol, statusCol, dateCol);

        ObservableList<TaskSubjectView> tasks = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = dc.con.prepareStatement(
                "SELECT task_id, task, status, subject_id, duration, instructor_id, task_code, description, sem, school_year, date_sub FROM tasks WHERE subject_id = ?"
            );
            stmt.setString(1, subj.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TaskSubjectView tsv = new TaskSubjectView(
                    rs.getString("task_id"),
                    rs.getString("task"),
                    rs.getString("description"),
                    subj.getCode(),
                    subj.getDescription(),
                    rs.getString("duration"),
                    rs.getString("status"),
                    rs.getString("date_sub")
                );
                tasks.add(tsv);
            }

            rs.close();
            stmt.close();
//            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        taskTable.setItems(tasks);
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox taskBox = new VBox(10, taskTable);
        taskBox.setPadding(new Insets(10));
        taskDialog.getDialogPane().setContent(taskBox);
        taskDialog.initModality(Modality.APPLICATION_MODAL);
        taskDialog.showAndWait();
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
