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

public class StudentView {

    private final StringProperty studentID;
    private final StringProperty lastname;
    private final StringProperty firstname;
    private final StringProperty gender;
    private final StringProperty age;
    private final StringProperty address;
    private final StringProperty email;
    private final StringProperty contact;
    private final StringProperty username;
    private final StringProperty password;

    public StudentView(String studentID, String lastname, String firstname, String gender, String age,
                       String address, String email, String contact, String username, String password) {
        this.studentID = new SimpleStringProperty(studentID);
        this.lastname = new SimpleStringProperty(lastname);
        this.firstname = new SimpleStringProperty(firstname);
        this.gender = new SimpleStringProperty(gender);
        this.age = new SimpleStringProperty(age);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.contact = new SimpleStringProperty(contact);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getStudentID() {
        return studentID.get();
    }

    public void setStudentID(String value) {
        studentID.set(value);
    }

    public StringProperty studentIDProperty() {
        return studentID;
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String value) {
        lastname.set(value);
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String value) {
        firstname.set(value);
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getAge() {
        return age.get();
    }

    public void setAge(String value) {
        age.set(value);
    }

    public StringProperty ageProperty() {
        return age;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String value) {
        contact.set(value);
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String value) {
        username.set(value);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String value) {
        password.set(value);
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
