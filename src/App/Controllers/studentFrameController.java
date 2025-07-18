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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
    @FXML private TableColumn<viewStudentPerformance, String> taskSubCode, taskSubDes, taskTask, taskScore;
    public void loadMyTaskTable()throws Exception{
        taskSubCode.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        taskSubDes.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        taskTask.setCellValueFactory(cellData -> cellData.getValue().taskProperty());
        taskScore.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());
        loadPerformanceData();
        myTaskTable.setItems(taskList);
    }
    ObservableList<viewStudentPerformance> taskList = FXCollections.observableArrayList();
    public void loadPerformanceData() throws SQLException {
        taskList.clear();

        String sql = """
            SELECT s.code, s.description, t.task, p.score
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
                rs.getString("score")
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

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error uploading file.");
        }
    }

    private Timeline countdown;
    private int totalSeconds;

    @FXML private TextArea message;
    @FXML private Label task, description, duration, filePath;
    public void validateTaskCode() throws Exception{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Validate Task Code");
        dialog.setHeaderText("Enter Task Code");
        dialog.setContentText("Task Code:");

        task.setText("");
        description.setText("");
        duration.setText("");
        filePath.setText("");
        duration.setText("");
        
        dialog.showAndWait().ifPresent(inputCode -> {
            if (inputCode.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Empty Input", "Please enter a valid task code.");
                return;
            }

            try {
                PreparedStatement ps = dc.con.prepareStatement("SELECT * FROM tasks WHERE status = 'Pending' AND task_code = ?");
                ps.setString(1, inputCode.trim());
                taskCode=inputCode.trim();
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    ifOnSession=true;
                    taskID=rs.getString("task_id");
                    
                    // 2️⃣ Check in performance table if this student already submitted for this task
                    PreparedStatement checkPerformance = dc.con.prepareStatement(
                        "SELECT * FROM performance WHERE student_id = ? AND task_id = ?"
                    );
                    checkPerformance.setString(1, studentId);  // your logged-in student ID
                    checkPerformance.setString(2, taskID);

                    ResultSet rsCheck = checkPerformance.executeQuery();
                    if (rsCheck.next()) {
                        // 🚫 Student already submitted this task
                        showAlert(Alert.AlertType.WARNING, "Already Submitted",
                            "You have already submitted this task. You cannot do it again.");
                        rsCheck.close();
                        checkPerformance.close();
                        rs.close();
                        ps.close();
                        return; // stop here
                    }
                    rsCheck.close();
                    checkPerformance.close();

                    
                    
                    homePane.setVisible(false);
                    sessionPane.setVisible(true);
                    
                    task.setText(task.getText()+rs.getString("task"));
                    description.setText(description.getText()+rs.getString("description"));
                    String rawDuration = rs.getString("duration");
                    duration.setText(duration.getText()+rawDuration); // Initial display
                    
                    startCountdown(rawDuration);
//                    showAlert(Alert.AlertType.INFORMATION, "Success", "Task code is valid and exists in the database.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Not Found", "Task code does not exist.");
                }

                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred.");
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
