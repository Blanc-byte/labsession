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

public class viewStudentPerformance {
    private final StringProperty code;
    private final StringProperty description;
    private final StringProperty task;
    private final StringProperty score;

    public viewStudentPerformance(String code, String description, String task, String score) {
        this.code = new SimpleStringProperty(code);
        this.description = new SimpleStringProperty(description);
        this.task = new SimpleStringProperty(task);
        this.score = new SimpleStringProperty(score);
    }

    // Getters
    public String getcode() {
        return code.get();
    }

    public String getdescription() {
        return description.get();
    }

    public String gettask() {
        return task.get();
    }

    public String getscore() {
        return score.get();
    }

    // Properties
    public StringProperty codeProperty() {
        return code;
    }


    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty taskProperty() {
        return task;
    }

    public StringProperty scoreProperty() {
        return score;
    }

    // Optional: Setters if needed
    public void setcode(String id) {
        this.code.set(id);
    }

    public void setdescription(String studentName) {
        this.description.set(studentName);
    }

    public void settask(String file) {
        this.task.set(file);
    }

    public void setscore(String message) {
        this.score.set(message);
    }
}

