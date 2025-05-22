/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Blanc
 */

package App.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty taskId;
    private final StringProperty task;
    private final StringProperty status;
    private final StringProperty subjectId;
    private final StringProperty duration;
    private final StringProperty instructorId;
    private final StringProperty taskCode;
    private final StringProperty description;

    public Task(String taskId, String task, String status, String subjectId, String duration, String instructorId, String taskCode, String description) {
        this.taskId = new SimpleStringProperty(taskId);
        this.task = new SimpleStringProperty(task);
        this.status = new SimpleStringProperty(status);
        this.subjectId = new SimpleStringProperty(subjectId);
        this.duration = new SimpleStringProperty(duration);
        this.instructorId = new SimpleStringProperty(instructorId);
        this.taskCode = new SimpleStringProperty(taskCode);
        this.description = new SimpleStringProperty(description);
    }

    // Getters
    public String getTaskId() { return taskId.get(); }
    public String getTask() { return task.get(); }
    public String getStatus() { return status.get(); }
    public String getSubjectId() { return subjectId.get(); }
    public String getDuration() { return duration.get(); }
    public String getInstructorId() { return instructorId.get(); }
    public String getTaskCode() { return taskCode.get(); }
    public String getdescription() { return description.get(); }

    // Setters
    public void setTaskId(String value) { taskId.set(value); }
    public void setTask(String value) { task.set(value); }
    public void setStatus(String value) { status.set(value); }
    public void setSubjectId(String value) { subjectId.set(value); }
    public void setDuration(String value) { duration.set(value); }
    public void setInstructorId(String value) { instructorId.set(value); }
    public void setTaskCode(String value) { taskCode.set(value); }
    public void setdescription(String value) { description.set(value); }

    // Properties
    public StringProperty taskIdProperty() { return taskId; }
    public StringProperty taskProperty() { return task; }
    public StringProperty statusProperty() { return status; }
    public StringProperty subjectIdProperty() { return subjectId; }
    public StringProperty durationProperty() { return duration; }
    public StringProperty instructorIdProperty() { return instructorId; }
    public StringProperty taskCodeProperty() { return taskCode; }
    public StringProperty taskdescription() { return description; }
}


