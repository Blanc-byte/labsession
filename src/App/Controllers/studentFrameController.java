/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Controllers;

import App.DatabaseConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import App.Models.Task;
import App.Models.viewStudentPerformance;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 *
 * @author Blanc
 */
public class studentFrameController {
    String studentId = "";
    String taskCode="", taskID="";
    boolean ifOnSession=false;
    DatabaseConnection dc = new DatabaseConnection();
    @FXML private Label user;
    public void initialize()throws Exception{
        dc.connect();
        File read = new File("studentID.txt");
        Scanner sc = new Scanner(read);
        while(sc.hasNextLine()){
            studentId=sc.nextLine();
            System.out.println("studentId: "+studentId);
        }
        String sql = "SELECT * FROM student WHERE studentID = '"+studentId+"'";
        PreparedStatement ps = dc.con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            user.setText("Welcome "+rs.getString("firstname")+" "+rs.getString("lastname"));
        }
        
    }
    @FXML private TableView<viewStudentPerformance> myTaskTable;
    @FXML private TableColumn<viewStudentPerformance, String> taskSubCode, taskSubDes, taskTask, taskScore, fileName;
    @FXML private TableColumn<viewStudentPerformance, Void> action;

    public void loadMyTaskTable()throws Exception{
        taskSubCode.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        taskSubDes.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        taskTask.setCellValueFactory(cellData -> cellData.getValue().taskProperty());
        taskScore.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());
        fileName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        loadPerformanceData();
        myTaskTable.setItems(taskList);
        action.setCellFactory(col -> new TableCell<>() {
        private final Button updateBtn = new Button("Update");

            {
                updateBtn.setOnAction(event -> {
                    viewStudentPerformance selected = getTableView().getItems().get(getIndex());
//                    System.out.println(selected.gettask());
                    if (selected != null) {
                        System.out.println("Click");
                        try {
                            handleUpdateFile(selected);
                        } catch (Exception ex) {
                            Logger.getLogger(studentFrameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateBtn);
                }
            }
        });

    }
    private void handleUpdateFile(viewStudentPerformance task) throws Exception{
        String sql = "SELECT * FROM tasks WHERE status != 'Pending' AND task_id = '"+task.gettaskId()+"'";
        PreparedStatement ps = dc.con.prepareStatement(sql);
        ResultSet rss = ps.executeQuery();
        boolean True=true;
        while (rss.next()) {
            True=false;
        }
        if(True){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose New File");
            File newFile = fileChooser.showOpenDialog(myTaskTable.getScene().getWindow());

            if (newFile == null) return; // User canceled

            try (FileInputStream fis = new FileInputStream(newFile)) {

                // Get the task_id first (assuming code & task are unique per student)
                String getIdSQL = """
                    SELECT task_id FROM tasks t
                    JOIN subject s ON t.subject_id = s.id
                    WHERE s.code = ? AND t.task = ?
                """;
                PreparedStatement ps1 = dc.con.prepareStatement(getIdSQL);
                ps1.setString(1, task.getcode());
                ps1.setString(2, task.gettask());
                ResultSet rs = ps1.executeQuery();

                if (rs.next()) {
                    int taskId = rs.getInt("task_id");

                    String updateSQL = """
                        UPDATE performance
                        SET file = ?, file_path = ?, date_sub = NOW()
                        WHERE student_id = ? AND task_id = ?
                    """;
                    PreparedStatement ps2 = dc.con.prepareStatement(updateSQL);
                    ps2.setBlob(1, fis);
                    ps2.setString(2, newFile.getName());
                    ps2.setString(3, studentId);
                    ps2.setInt(4, taskId);

                    int rowsUpdated = ps2.executeUpdate();
                    if (rowsUpdated > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "File updated successfully!");
                        loadPerformanceData(); // refresh table
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Update failed.");
                    }

                    ps2.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Task not found for update.");
                }

                rs.close();
                ps1.close();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Error updating file: " + e.getMessage());
            }
        }else{
            showAlert(Alert.AlertType.INFORMATION, "Failed", "Task Closed.");
        }
        
    }

    ObservableList<viewStudentPerformance> taskList = FXCollections.observableArrayList();
    public void loadPerformanceData() throws SQLException {
        taskList.clear();

        String sql = """
            SELECT s.code, s.description, t.task, p.score, p.file_path, t.task_id
            FROM tasks t 
            JOIN subject s ON t.subject_id = s.id
            JOIN performance p ON p.task_id = t.task_id
            JOIN student st ON st.studentID = p.student_id WHERE p.student_id='"""+studentId+"'";

        PreparedStatement ps = dc.con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            taskList.add(new viewStudentPerformance(
                rs.getString("code"),
                rs.getString("description"),
                rs.getString("task"),
                rs.getString("score"),
                rs.getString("file_path"),
                rs.getString("task_id")
            ));
            System.out.println(rs.getString("code"));
        }

        rs.close();
        ps.close();
    }
    

    private File selectedFile;
    @FXML
    public void handleUploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePath.setText(selectedFile.getAbsolutePath());  // Label to show selected file
//            showAlert(Alert.AlertType.INFORMATION, "File Selected", "Selected: " + selectedFile.getName());
        } else {
            showAlert(Alert.AlertType.WARNING, "No File", "Please select a file.");
        }
    }
    @FXML
    public void handleFinishSubmission() {
        if (selectedFile == null) {
            showAlert(Alert.AlertType.WARNING, "No File", "Please upload a file first.");
            return;
        }
        // Show preview/confirmation before upload
        boolean confirmed = showFileConfirmation(selectedFile);
        if (!confirmed) {
            return; // user cancelled
        }

        try (FileInputStream fis = new FileInputStream(selectedFile)) {
            PreparedStatement insert = dc.con.prepareStatement(
                "INSERT INTO performance (student_id, task_id, file, message, file_path) VALUES (?, ?, ?, ?, ?)"
            );

            insert.setString(1, studentId);  // student_id
            insert.setString(2, taskID);
            insert.setBlob(3, fis);  // file content as BLOB
            insert.setString(4, message.getText());
            insert.setString(5, selectedFile.getName());

            int rowsInserted = insert.executeUpdate();
            insert.close();

            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Submission successfully saved!");
                homePane.setVisible(true);
                sessionPane.setVisible(false);
                task.setText("");
                description.setText("");
                duration.setText("");
                filePath.setText("");
                duration.setText("");
                message.setText("");
                selectedFile = null;
                ifOnSession=false;
                // reset UI and variables here...
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Submission failed.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "File error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQL error: " + e.getMessage());
        }

    }
    private boolean showFileConfirmation(File file) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm File Upload");
        confirmAlert.setHeaderText("Please confirm your file before submission");

        // Optional preview for small text files
        String previewText = "";
        if (file.getName().toLowerCase().endsWith(".txt") ||
            file.getName().toLowerCase().endsWith(".java") ||
            file.getName().toLowerCase().endsWith(".csv")) {
            try {
                previewText = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (previewText.length() > 500) { // limit preview size
                    previewText = previewText.substring(0, 500) + "\n... (truncated)";
                }
            } catch (IOException e) {
                previewText = "(Unable to read file content)";
            }
        } else {
            previewText = "(Preview not available for this file type)";
        }

        confirmAlert.setContentText("File Name: " + file.getName() +
                "\nSize: " + file.length() + " bytes\n\nPreview:\n" + previewText);

        ButtonType yesBtn = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmAlert.getButtonTypes().setAll(yesBtn, cancelBtn);

        return confirmAlert.showAndWait().filter(response -> response == yesBtn).isPresent();
    }

    private Timeline countdown;
    private int totalSeconds;

    @FXML private TextArea message;
    @FXML private Label task, description, duration, filePath;
    public void validateTaskCode() throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Validate Task Code");
        dialog.setHeaderText("Enter Task Code");
        dialog.setContentText("Task Code:");

        // Clear any previous session info
        task.setText("");
        description.setText("");
        duration.setText("");
        filePath.setText("");

        dialog.showAndWait().ifPresent(inputCode -> {
            String code = inputCode.trim();
            if (code.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Empty Input", "Please enter a valid task code.");
                return;
            }

            try (
                PreparedStatement ps = dc.con.prepareStatement(
                    "SELECT * FROM tasks WHERE status = 'Pending' AND task_code = ?"
                )
            ) {
                ps.setString(1, code);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        showAlert(Alert.AlertType.ERROR, "Not Found", "Task code does not exist.");
                        return;
                    }

                    // 1️⃣ Read task's section & year
                    String taskSec  = rs.getString("sec");
                    String taskYear = rs.getString("year");
                    String taskId   = rs.getString("task_id");
                    taskID=taskId;
                    // 2️⃣ Fetch this student's sec & year
                    String studentSec, studentYear;
                    try (
                        PreparedStatement ps2 = dc.con.prepareStatement(
                            "SELECT sec, year FROM student WHERE studentID = ?"
                        )
                    ) {
                        ps2.setString(1, studentId);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (!rs2.next()) {
                                showAlert(Alert.AlertType.ERROR, "Error", "Cannot find your student record.");
                                return;
                            }
                            studentSec  = rs2.getString("sec");
                            studentYear = rs2.getString("year");
                        }
                    }

                    // 3️⃣ Validate match
                    if (!taskSec.equals(studentSec) || !taskYear.equals(studentYear)) {
                        showAlert(Alert.AlertType.WARNING,
                                  "Access Denied",
                                  "This task can`t be access!");
                        return;
                    }

                    // 4️⃣ Check previous submission
                    try (
                        PreparedStatement checkPerf = dc.con.prepareStatement(
                            "SELECT 1 FROM performance WHERE student_id = ? AND task_id = ?"
                        )
                    ) {
                        checkPerf.setString(1, studentId);
                        checkPerf.setString(2, taskId);
                        try (ResultSet rsCheck = checkPerf.executeQuery()) {
                            if (rsCheck.next()) {
                                showAlert(Alert.AlertType.WARNING,
                                          "Already Submitted",
                                          "You have already submitted this task.");
                                return;
                            }
                        }
                    }

                    // 5️⃣ All good—open session
                    homePane.setVisible(false);
                    sessionPane.setVisible(true);
                    task.setText(rs.getString("task"));
                    description.setText(rs.getString("description"));
                    String rawDuration = rs.getString("duration");
                    duration.setText(rawDuration);
                    startCountdown(rawDuration);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred:\n" + e.getMessage());
            }
        });
    }

    private void startCountdown(String durationStr) {
        // Stop previous timer if running
        if (countdown != null) {
            countdown.stop();
        }

        // Parse duration
        int hours = 0, minutes = 0;
        try {
            if (durationStr.contains(":")) {
                String[] parts = durationStr.split(":");
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);
            } else {
                hours = Integer.parseInt(durationStr);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Format", "Duration format is incorrect.");
            return;
        }

        totalSeconds = hours * 3600 + minutes * 60;

        countdown = new Timeline(
            new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                if (totalSeconds > 0) {
                    totalSeconds--;
                    int h = totalSeconds / 3600;
                    int m = (totalSeconds % 3600) / 60;
                    int s = totalSeconds % 60;
                    duration.setText(String.format("%02d:%02d:%02d", h, m, s));
                } else {
                    duration.setText("Time's up!");
                    homePane.setVisible(true);
                    sessionPane.setVisible(false);
                    duration.setText("");
                    message.setText("");    
                    try {
                        PreparedStatement insert = dc.con.prepareStatement(
                            "INSERT INTO performance (student_id, score, task_id, file, file_path, message) VALUES (?, NULL, ?, NULL, NULL, ?)"
                        );
                        insert.setString(1, studentId);
                        insert.setString(2, taskID);
                        insert.setString(3, message.getText());
                        insert.executeUpdate();
                        insert.close();

                        showAlert(Alert.AlertType.INFORMATION, "Submitted", "Performance record inserted.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Insert Error", "Failed to insert into performance.");
                    }
                    countdown.stop();
                }
            })
        );

        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    
    
    ObservableList<Task> tasks = FXCollections.observableArrayList();
    public void getTasks() throws Exception {
        Statement statement = dc.con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

        while (resultSet.next()) {
            String id = resultSet.getString("task_id");
            String task = resultSet.getString("task");
            String status = resultSet.getString("status");
            String subjectId = resultSet.getString("subject_id");
            String duration = resultSet.getString("duration");
            String instructorId = resultSet.getString("instructor_id");
            String taskCode = resultSet.getString("task_code");
            String description = resultSet.getString("description");
            tasks.add(new Task(id, task, status, subjectId, duration, instructorId, taskCode, description));
        }
    }
    
    @FXML private Pane sessionPane, homePane, tasksPane;
    public void homeClick()throws Exception{
        if(!ifOnSession){
            homePane.setVisible(true);
            tasksPane.setVisible(false);
        }
    }
    public void taskClick()throws Exception{
        if(!ifOnSession){
            homePane.setVisible(false);
            tasksPane.setVisible(true);
            loadMyTaskTable();
        }
        
    }
}
