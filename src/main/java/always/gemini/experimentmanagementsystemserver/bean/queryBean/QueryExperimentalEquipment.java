package always.gemini.experimentmanagementsystemserver.bean.queryBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class QueryExperimentalEquipment {
    @DialogItem(id = 0, name = "教学实验中心", type = DialogItem.TYPE_Spinner)
    private String teaching_experiment_center_name;

    @DialogItem(id = 1, name = "实验室", type = DialogItem.TYPE_Spinner)
    private String laboratory_name;

    @DialogItem(id = 2, name = "实验分室", type = DialogItem.TYPE_Spinner)
    private String experimental_compartment_name;

    @DialogItem(id = 3, name = "实验房间", type = DialogItem.TYPE_Spinner)
    private String laboratory_room_name;

    @DialogItem(id = 4, name = "是否可搬动", type = DialogItem.TYPE_Spinner)
    private String is_movable;

    @DialogItem(id = 5, name = "实验仪器名称", type = DialogItem.TYPE_EditText)
    private String experimental_equipment_name;

    public String getTeaching_experiment_center_name() {
        return teaching_experiment_center_name;
    }

    public void setTeaching_experiment_center_name(String teaching_experiment_center_name) {
        this.teaching_experiment_center_name = teaching_experiment_center_name;
    }

    public String getLaboratory_name() {
        return laboratory_name;
    }

    public void setLaboratory_name(String laboratory_name) {
        this.laboratory_name = laboratory_name;
    }

    public String getExperimental_compartment_name() {
        return experimental_compartment_name;
    }

    public void setExperimental_compartment_name(String experimental_compartment_name) {
        this.experimental_compartment_name = experimental_compartment_name;
    }

    public String getLaboratory_room_name() {
        return laboratory_room_name;
    }

    public void setLaboratory_room_name(String laboratory_room_name) {
        this.laboratory_room_name = laboratory_room_name;
    }

    public String getIs_movable() {
        return is_movable;
    }

    public void setIs_movable(String is_movable) {
        this.is_movable = is_movable;
    }

    public String getExperimental_equipment_name() {
        return experimental_equipment_name;
    }

    public void setExperimental_equipment_name(String experimental_equipment_name) {
        this.experimental_equipment_name = experimental_equipment_name;
    }
}
