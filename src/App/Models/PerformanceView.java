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

public class PerformanceView {
    private final StringProperty id;
    private final StringProperty studentName;
    private final StringProperty file;
    private final StringProperty message;
    private final StringProperty score;
    private final StringProperty fileName;
    private final StringProperty dateSub;

    public PerformanceView(String id, String studentName, String file, String message, String score, String fileName, String dateSub) {
        this.id = new SimpleStringProperty(id);
        this.studentName = new SimpleStringProperty(studentName);
        this.file = new SimpleStringProperty(file);
        this.message = new SimpleStringProperty(message);
        this.score = new SimpleStringProperty(score);
        this.fileName = new SimpleStringProperty(fileName);
        this.dateSub = new SimpleStringProperty(dateSub);
    }

    
    public String getDateSub() {
        return dateSub.get();
    }
    public StringProperty dateSubProperty() {
        return dateSub;
    }
    public void setDateSub(String id) {
        this.dateSub.set(id);
    }
    // Getters
    
    
    public String getId() {
        return id.get();
    }
    
    public String getfileName() {
        return fileName.get();
    }

    public String getStudentName() {
        return studentName.get();
    }

    public String getFile() {
        return file.get();
    }

    public String getMessage() {
        return message.get();
    }
    
    public String getScore() {
        return score.get();
    }

    // Properties
    
    public StringProperty idProperty() {
        return id;
    }
    
    public StringProperty fileNameProperty() {
        return fileName;
    }


    public StringProperty studentNameProperty() {
        return studentName;
    }

    public StringProperty fileProperty() {
        return file;
    }

    public StringProperty messageProperty() {
        return message;
    }
    
    public StringProperty scoreProperty() {
        return score;
    }

    // Optional: Setters if needed
    
    public void setId(String id) {
        this.id.set(id);
    }
    
    public void setfileName(String fileName) {
        this.fileName.set(fileName);
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public void setFile(String file) {
        this.file.set(file);
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
    
    public void setScore(String score) {
        this.score.set(score);
    }
}

