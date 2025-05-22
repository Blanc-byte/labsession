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

    public TaskSubjectView(String taskId, String task, String taskDescription,
                           String subjectCode, String subjectDescription,
                           String duration, String status) {
        this.taskId = new SimpleStringProperty(taskId);
        this.task = new SimpleStringProperty(task);
        this.taskDescription = new SimpleStringProperty(taskDescription);
        this.subjectCode = new SimpleStringProperty(subjectCode);
        this.subjectDescription = new SimpleStringProperty(subjectDescription);
        this.duration = new SimpleStringProperty(duration);
        this.status = new SimpleStringProperty(status);
    }

    // Getters
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

    // Setters
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

    // Property accessors (for TableView binding)
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
}

