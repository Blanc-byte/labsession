package models;

public class Performance {
    private String student;
    private String score;

    public Performance(String student, String score) {
        this.student = student;
        this.score = score;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
