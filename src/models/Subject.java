package models;

public class Subject {
    private int id;
    private String name;
    private String description;
    private String duration;

    public Subject(int id, String name, String description, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDuration() { return duration; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getid() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
