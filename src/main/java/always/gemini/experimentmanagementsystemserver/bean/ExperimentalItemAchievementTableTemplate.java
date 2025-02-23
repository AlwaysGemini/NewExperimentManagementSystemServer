package always.gemini.experimentmanagementsystemserver.bean;

import always.gemini.experimentmanagementsystemserver.util.TableColumn;

public class ExperimentalItemAchievementTableTemplate {
    private String experiment_course_match_id;

    //private String experimental_course_selection_id;

    private String experiment_item_id;

    //private String user_name;

    //private String student_id;

    private String experiment_item_name;

    private String experimental_item_score;

    @TableColumn(id = 1, name = "实验选课代码")
    private String experimental_course_selection_id;

    @TableColumn(id = 2, name = "实验教学班")
    private String experimental_teaching_class_name;

    @TableColumn(id = 3, name = "姓名")
    private String user_name;

    @TableColumn(id = 4, name = "学号")
    private String student_id;

    @TableColumn(id = 5, name = "成绩")
    private String score;

    public String getExperimental_course_selection_id() {
        return experimental_course_selection_id;
    }

    public void setExperimental_course_selection_id(String experimental_course_selection_id) {
        this.experimental_course_selection_id = experimental_course_selection_id;
    }

    public String getExperiment_item_id() {
        return experiment_item_id;
    }

    public void setExperiment_item_id(String experiment_item_id) {
        this.experiment_item_id = experiment_item_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getExperiment_item_name() {
        return experiment_item_name;
    }

    public void setExperiment_item_name(String experiment_item_name) {
        this.experiment_item_name = experiment_item_name;
    }

    public String getExperimental_item_score() {
        return experimental_item_score;
    }

    public void setExperimental_item_score(String experimental_item_score) {
        this.experimental_item_score = experimental_item_score;
    }

    public String getExperiment_course_match_id() {
        return experiment_course_match_id;
    }

    public void setExperiment_course_match_id(String experiment_course_match_id) {
        this.experiment_course_match_id = experiment_course_match_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExperimental_teaching_class_name() {
        return experimental_teaching_class_name;
    }

    public void setExperimental_teaching_class_name(String experimental_teaching_class_name) {
        this.experimental_teaching_class_name = experimental_teaching_class_name;
    }
}
