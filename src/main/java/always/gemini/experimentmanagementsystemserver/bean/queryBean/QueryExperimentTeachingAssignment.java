package always.gemini.experimentmanagementsystemserver.bean.queryBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class QueryExperimentTeachingAssignment {
    @DialogItem(id = 0, name = "学年", type = DialogItem.TYPE_Spinner)
    private String year;

    @DialogItem(id = 1, name = "学期", type = DialogItem.TYPE_Spinner)
    private String semester;

    @DialogItem(id = 2, name = "开课学院", type = DialogItem.TYPE_Spinner)
    private String college;

    @DialogItem(id = 3, name = "课程", type = DialogItem.TYPE_EditText, hint = "按课程代码、名称模糊查询")
    private String course;
}
