package always.gemini.experimentmanagementsystemserver.bean.insertBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class InsertLaboratory {
    @DialogItem(id = 0, name = "实验室代码", type = DialogItem.TYPE_EditText)
    private String laboratory_id;

    @DialogItem(id = 1, name = "实验室名称", type = DialogItem.TYPE_EditText)
    private String laboratory_name;

    @DialogItem(id = 2, name = "隶属教学实验中心代码", type = DialogItem.TYPE_EditText)
    private String teaching_experiment_center_id;

    @DialogItem(id = 3, name = "实验室负责人", type = DialogItem.TYPE_EditText)
    private String laboratory_director;

    @DialogItem(id = 4, name = "规章制度", type = DialogItem.TYPE_EditText)
    private String rules_and_regulations;

    @DialogItem(id = 5, name = "备注", type = DialogItem.TYPE_EditText)
    private String remarks;

    @DialogItem(id = 6, name = "启用标志", type = DialogItem.TYPE_EditText)
    private String enable_flag;

    public String getLaboratory_id() {
        return laboratory_id;
    }

    public void setLaboratory_id(String laboratory_id) {
        this.laboratory_id = laboratory_id;
    }

    public String getLaboratory_name() {
        return laboratory_name;
    }

    public void setLaboratory_name(String laboratory_name) {
        this.laboratory_name = laboratory_name;
    }

    public String getTeaching_experiment_center_id() {
        return teaching_experiment_center_id;
    }

    public void setTeaching_experiment_center_id(String teaching_experiment_center_id) {
        this.teaching_experiment_center_id = teaching_experiment_center_id;
    }

    public String getLaboratory_director() {
        return laboratory_director;
    }

    public void setLaboratory_director(String laboratory_director) {
        this.laboratory_director = laboratory_director;
    }

    public String getRules_and_regulations() {
        return rules_and_regulations;
    }

    public void setRules_and_regulations(String rules_and_regulations) {
        this.rules_and_regulations = rules_and_regulations;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(String enable_flag) {
        this.enable_flag = enable_flag;
    }
}
