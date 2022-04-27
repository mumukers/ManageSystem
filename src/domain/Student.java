package domain;

import java.util.Objects;

//数据层用来定义数据类型
public class Student {
    private String id;
    private String name;
    private String gender;
    private String course;
    private double score;

    public Student(String id, String name, String gender, String course, double score) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.course = course;
        this.score = score;
    }

    @Override
    public String toString() {
        return
                id + ',' +
                name + ',' +
                gender + ',' +
                 course + ',' +
                score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
