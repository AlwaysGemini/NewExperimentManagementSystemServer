package always.gemini.experimentmanagementsystemserver.bean.queryBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class QueryCourseExperimentProject {
    @DialogItem(name = "开课学院", id = 0, type = DialogItem.TYPE_Spinner)
    private String college;
    @DialogItem(name = "课程类别", id = 1, type = DialogItem.TYPE_Spinner)
    private String course_category;
    @DialogItem(name = "课程归属", id = 2, type = DialogItem.TYPE_Spinner)
    private String course_assignment;
    @DialogItem(name = "课程启用年级", id = 3, type = DialogItem.TYPE_Spinner)
    private String enabling_grade;
    @DialogItem(name = "课程", id = 4, type = DialogItem.TYPE_EditText, hint = "按课程代码、名称模糊查询")
    private String course;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse_category() {
        return course_category;
    }

    public void setCourse_category(String course_category) {
        this.course_category = course_category;
    }

    public String getCourse_assignment() {
        return course_assignment;
    }

    public void setCourse_assignment(String course_assignment) {
        this.course_assignment = course_assignment;
    }

    public String getEnabling_grade() {
        return enabling_grade;
    }

    public void setEnabling_grade(String enabling_grade) {
        this.enabling_grade = enabling_grade;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
