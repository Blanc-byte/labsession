package models;

public class Course {
    private String course;
    private String acronym;

    public Course(String course, String acronym) {
        this.course = course;
        this.acronym = acronym;
    }

    public String getCourse() { return course; }
    public String getAcronym() { return acronym; }
}
