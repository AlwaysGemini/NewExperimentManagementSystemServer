package always.gemini.experimentmanagementsystemserver.bean.insertBean;


import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class InsertExperimentalConsumablesManagement {
    @DialogItem(id = 0, name = "实验耗材代码", type = DialogItem.TYPE_EditText)
    private String experimental_consumables_id;

    @DialogItem(id = 1, name = "实验耗材名称", type = DialogItem.TYPE_EditText)
    private String experimental_consumables_name;

    @DialogItem(id = 2, name = "当前库存量", type = DialogItem.TYPE_EditText)
    private String current_inventory;

    @DialogItem(id = 3, name = "最大库存量", type = DialogItem.TYPE_EditText)
    private String maximum_inventory;

    @DialogItem(id = 4, name = "最小库存量", type = DialogItem.TYPE_EditText)
    private String minimum_inventory;

    @DialogItem(id = 5, name = "型号规格", type = DialogItem.TYPE_EditText)
    private String model_and_specification;

    @DialogItem(id = 6, name = "单位", type = DialogItem.TYPE_EditText)
    private String unit;

    @DialogItem(id = 7, name = "单价", type = DialogItem.TYPE_EditText)
    private String unit_price;

    @DialogItem(id = 8, name = "实验房间代码", type = DialogItem.TYPE_EditText)
    private String laboratory_room_id;

    public String getExperimental_consumables_id() {
        return experimental_consumables_id;
    }

    public void setExperimental_consumables_id(String experimental_consumables_id) {
        this.experimental_consumables_id = experimental_consumables_id;
    }

    public String getExperimental_consumables_name() {
        return experimental_consumables_name;
    }

    public void setExperimental_consumables_name(String experimental_consumables_name) {
        this.experimental_consumables_name = experimental_consumables_name;
    }

    public String getCurrent_inventory() {
        return current_inventory;
    }

    public void setCurrent_inventory(String current_inventory) {
        this.current_inventory = current_inventory;
    }

    public String getMaximum_inventory() {
        return maximum_inventory;
    }

    public void setMaximum_inventory(String maximum_inventory) {
        this.maximum_inventory = maximum_inventory;
    }

    public String getMinimum_inventory() {
        return minimum_inventory;
    }

    public void setMinimum_inventory(String minimum_inventory) {
        this.minimum_inventory = minimum_inventory;
    }

    public String getModel_and_specification() {
        return model_and_specification;
    }

    public void setModel_and_specification(String model_and_specification) {
        this.model_and_specification = model_and_specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getLaboratory_room_id() {
        return laboratory_room_id;
    }

    public void setLaboratory_room_id(String laboratory_room_id) {
        this.laboratory_room_id = laboratory_room_id;
    }
}
