package models;

public class Faculty {
    private int facultyID;
    private String fullname;
    private String department;

    public Faculty(int facultyID, String fullname, String department) {
        this.facultyID = facultyID;
        this.fullname = fullname;
        this.department = department;
    }

    public int getFacultyID() { return facultyID; }
    public String getFullname() { return fullname; }
    public String getDepartment() { return department; }
}
