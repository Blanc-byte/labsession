package models;

public class Attendance {
    private String student;
    private String attendance;

    public Attendance(String student, String attendance) {
        this.student = student;
        this.attendance = attendance;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
