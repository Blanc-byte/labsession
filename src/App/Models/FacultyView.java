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

public class FacultyView {
    private final StringProperty facultyID;
    private final StringProperty fullname;
    private final StringProperty gender;
    private final StringProperty age;
    private final StringProperty email;
    private final StringProperty contact;
    private final StringProperty dateHired;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty status;

    public FacultyView(String facultyID, String fullname, String gender, String age,
                       String email, String contact, String dateHired,
                       String username, String password, String status) {
        this.facultyID = new SimpleStringProperty(facultyID);
        this.fullname = new SimpleStringProperty(fullname);
        this.gender = new SimpleStringProperty(gender);
        this.age = new SimpleStringProperty(age);
        this.email = new SimpleStringProperty(email);
        this.contact = new SimpleStringProperty(contact);
        this.dateHired = new SimpleStringProperty(dateHired);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
    }

    // Getters
    public String getFacultyID() { return facultyID.get(); }
    public String getFullname() { return fullname.get(); }
    public String getGender() { return gender.get(); }
    public String getAge() { return age.get(); }
    public String getEmail() { return email.get(); }
    public String getContact() { return contact.get(); }
    public String getDateHired() { return dateHired.get(); }
    public String getUsername() { return username.get(); }
    public String getPassword() { return password.get(); }
    public String getStatus() { return status.get(); }

    // Properties
    public StringProperty facultyIDProperty() { return facultyID; }
    public StringProperty fullnameProperty() { return fullname; }
    public StringProperty genderProperty() { return gender; }
    public StringProperty ageProperty() { return age; }
    public StringProperty emailProperty() { return email; }
    public StringProperty contactProperty() { return contact; }
    public StringProperty dateHiredProperty() { return dateHired; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty passwordProperty() { return password; }
    public StringProperty statusProperty() { return status; }
}

