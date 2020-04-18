package always.gemini.experimentmanagementsystemserver.bean.insertBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class InsertExperimentalEquipment {
    @DialogItem(id = 0, name = "实验仪器代码", type = DialogItem.TYPE_EditText)
    private String id;

    @DialogItem(id = 1, name = "实验仪器名称", type = DialogItem.TYPE_EditText)
    private String experimental_equipment_name;

    @DialogItem(id = 2, name = "价值", type = DialogItem.TYPE_EditText)
    private String value;

    @DialogItem(id = 3, name = "实验房间代码", type = DialogItem.TYPE_EditText)
    private String laboratory_room_name;

    @DialogItem(id = 4, name = "是否可搬动", type = DialogItem.TYPE_EditText)
    private String is_movable;

    @DialogItem(id = 5, name = "采购时间", type = DialogItem.TYPE_EditText)
    private String procurement_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExperimental_equipment_name() {
        return experimental_equipment_name;
    }

    public void setExperimental_equipment_name(String experimental_equipment_name) {
        this.experimental_equipment_name = experimental_equipment_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getProcurement_time() {
        return procurement_time;
    }

    public void setProcurement_time(String procurement_time) {
        this.procurement_time = procurement_time;
    }
}
