package always.gemini.experimentmanagementsystemserver.bean;

import java.io.Serializable;

public class StudentSelectionTable implements Serializable {
    private String experiment_name;
    private int week_of_origin;
    private int week_of_end;
    private int day;
    private int start_time;
    private int length;
    private String class_name;
    private String teacher_name;
    private String student;

    public String getExperiment_name() {
        return experiment_name;
    }

    public void setExperiment_name(String experiment_name) {
        this.experiment_name = experiment_name;
    }

    public int getWeek_of_origin() {
        return week_of_origin;
    }

    public void setWeek_of_origin(int week_of_origin) {
        this.week_of_origin = week_of_origin;
    }

    public int getWeek_of_end() {
        return week_of_end;
    }

    public void setWeek_of_end(int week_of_end) {
        this.week_of_end = week_of_end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
