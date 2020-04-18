package always.gemini.experimentmanagementsystemserver.bean.queryBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class QueryCourseExperimentOutline {
    @DialogItem(id = 0, name = "课程", type = DialogItem.TYPE_EditText, hint = "按课程代码或名称模糊查询")
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
