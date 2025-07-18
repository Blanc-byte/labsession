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

public class subject {
    private final StringProperty id;
    private final StringProperty code;
    private final StringProperty description;
    private final StringProperty sem;
    private final StringProperty year;

    public subject(String id, String code, String description, String sem, String year) {
        this.id = new SimpleStringProperty(id);
        this.code = new SimpleStringProperty(code);
        this.description = new SimpleStringProperty(description);
        this.sem = new SimpleStringProperty(sem);
        this.year = new SimpleStringProperty(year);
    }
    // ID
    public String getYear() {
        return year.get();
    }

    public void setYear(String value) {
        year.set(value);
    }

    public StringProperty yearProperty() {
        return year;
    }
    
    // ID
    public String getSem() {
        return sem.get();
    }

    public void setSem(String value) {
        sem.set(value);
    }

    public StringProperty semProperty() {
        return sem;
    }

    // ID
    public String getId() {
        return id.get();
    }

    public void setId(String value) {
        id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }

    // Code
    public String getCode() {
        return code.get();
    }

    public void setCode(String value) {
        code.set(value);
    }

    public StringProperty codeProperty() {
        return code;
    }

    // Description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}
