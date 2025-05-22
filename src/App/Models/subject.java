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

    public subject(String id, String code, String description) {
        this.id = new SimpleStringProperty(id);
        this.code = new SimpleStringProperty(code);
        this.description = new SimpleStringProperty(description);
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
