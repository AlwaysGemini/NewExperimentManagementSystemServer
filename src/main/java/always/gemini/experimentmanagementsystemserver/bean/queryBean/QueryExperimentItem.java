package always.gemini.experimentmanagementsystemserver.bean.queryBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class QueryExperimentItem {
    @DialogItem(id = 0, name = "实验属性", type = DialogItem.TYPE_Spinner)
    private String experiment_attribute;

    @DialogItem(id = 1, name = "实验类别", type = DialogItem.TYPE_Spinner)
    private String experiment_type;

    @DialogItem(id = 2, name = "实验类型", type = DialogItem.TYPE_Spinner)
    private String experiment_category;

    @DialogItem(id = 3, name = "实验项目名称", type = DialogItem.TYPE_EditText)
    private String experiment_item_name;

    public String getExperiment_attribute() {
        return experiment_attribute;
    }

    public void setExperiment_attribute(String experiment_attribute) {
        this.experiment_attribute = experiment_attribute;
    }

    public String getExperiment_type() {
        return experiment_type;
    }

    public void setExperiment_type(String experiment_type) {
        this.experiment_type = experiment_type;
    }

    public String getExperiment_category() {
        return experiment_category;
    }

    public void setExperiment_category(String experiment_category) {
        this.experiment_category = experiment_category;
    }

    public String getExperiment_item_name() {
        return experiment_item_name;
    }

    public void setExperiment_item_name(String experiment_item_name) {
        this.experiment_item_name = experiment_item_name;
    }
}
