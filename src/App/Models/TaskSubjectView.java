/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Models;

/**
 *
 * @author Blanc
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskSubjectView {

    private final StringProperty taskId;
    private final StringProperty task;
    private final StringProperty taskDescription;
    private final StringProperty subjectCode;
    private final StringProperty subjectDescription;
    private final StringProperty duration;
    private final StringProperty status;
    private final StringProperty dateSub;
    private final StringProperty points;

    public TaskSubjectView(String taskId, String task, String taskDescription,
                           String subjectCode, String subjectDescription,
                           String duration, String status, String dateSub
                            , String points) {
        this.taskId = new SimpleStringProperty(taskId);
        this.task = new SimpleStringProperty(task);
        this.taskDescription = new SimpleStringProperty(taskDescription);
        this.subjectCode = new SimpleStringProperty(subjectCode);
        this.subjectDescription = new SimpleStringProperty(subjectDescription);
        this.duration = new SimpleStringProperty(duration);
        this.status = new SimpleStringProperty(status);
        this.dateSub = new SimpleStringProperty(dateSub);
        this.points = new SimpleStringProperty(points);
    }

    // Getters
    public String getDateSub() {
        return dateSub.get();
    }
    
    public String getTaskId() {
        return taskId.get();
    }

    public String getTask() {
        return task.get();
    }

    public String getTaskDescription() {
        return taskDescription.get();
    }

    public String getSubjectCode() {
        return subjectCode.get();
    }

    public String getSubjectDescription() {
        return subjectDescription.get();
    }

    public String getDuration() {
        return duration.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getPoints() {
        return points.get();
    }

    // Setters
    
    
    public void setDateSub(String id) {
        this.dateSub.set(id);
    }
    
    public void setTaskId(String value) {
        taskId.set(value);
    }

    public void setTask(String value) {
        task.set(value);
    }

    public void setTaskDescription(String value) {
        taskDescription.set(value);
    }

    public void setSubjectCode(String value) {
        subjectCode.set(value);
    }

    public void setSubjectDescription(String value) {
        subjectDescription.set(value);
    }

    public void setDuration(String value) {
        duration.set(value);
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public void setPoints(String value) {
        points.set(value);
    }

    // Property accessors (for TableView binding)
    
    public StringProperty dateSubProperty() {
        return dateSub;
    }
    
    public StringProperty taskIdProperty() {
        return taskId;
    }

    public StringProperty taskProperty() {
        return task;
    }

    public StringProperty taskDescriptionProperty() {
        return taskDescription;
    }

    public StringProperty subjectCodeProperty() {
        return subjectCode;
    }

    public StringProperty subjectDescriptionProperty() {
        return subjectDescription;
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty pointsProperty() {
        return points;
    }
}

