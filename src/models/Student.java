package models;

public class Student {
    private int studentID;
    private String lastname;
    private String firstname;

    public Student(int studentID, String lastname, String firstname) {
        this.studentID = studentID;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public int getStudentID() { return studentID; }
    public String getLastname() { return lastname; }
    public String getFirstname() { return firstname; }
}
