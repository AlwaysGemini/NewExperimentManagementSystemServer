package always.gemini.experimentmanagementsystemserver.bean.insertBean;

import always.gemini.experimentmanagementsystemserver.util.DialogItem;

public class InsertUnmatchedOfExperimentCourse {
    @DialogItem(id = 0, name = "容量", type = DialogItem.TYPE_EditText)
    private String capacity;

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
