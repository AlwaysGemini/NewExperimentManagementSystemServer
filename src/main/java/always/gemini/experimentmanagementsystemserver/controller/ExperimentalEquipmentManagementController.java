package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experimentalEquipmentManagement")
public class ExperimentalEquipmentManagementController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param experimental_equipment_id
     * @param experimental_equipment_name
     * @param value
     * @param laboratory_room_id
     * @param is_movable
     * @param procurement_time
     * @return
     */
    @PostMapping("insertData")
    private Response insertExperimentalEquipmentManagementData(@RequestParam String experimental_equipment_id,
                                                               @RequestParam String experimental_equipment_name,
                                                               @RequestParam String value,
                                                               @RequestParam String laboratory_room_id,
                                                               @RequestParam String is_movable,
                                                               @RequestParam String procurement_time) {
        Response response = new Response();
        String insertString = new SQLBuilder.InsertBuilder()
                .into("experimental_equipment")
                .addItem("experimental_equipment_id", experimental_equipment_id)
                .addItem("experimental_equipment_name", experimental_equipment_name)
                .addItem("value", value)
                .addItem("laboratory_room_id", laboratory_room_id)
                .addItem("is_movable", is_movable)
                .addItem("procurement_time", procurement_time)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from view_experimental_equipment where experimental_equipment_id = ?", experimental_equipment_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    /**
     * @return
     */
    @PostMapping("getQueryConditionList")
    public Response getExperimentalEquipmentManagementQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct teaching_experiment_center_name from view_experimental_equipment"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_name from view_experimental_equipment"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_compartment_name from view_experimental_equipment"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_room_name from view_experimental_equipment"));
        lists.add(jdbcTemplate.queryForList("select distinct is_movable from view_experimental_equipment"));
        lists.add(jdbcTemplate.queryForList("select distinct experimental_equipment_name from view_experimental_equipment"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param teaching_experiment_center_name
     * @param laboratory_name
     * @param laboratory_compartment_name
     * @param laboratory_room_name
     * @param is_movable
     * @param experimental_equipment_name
     * @return
     */
    @PostMapping("getData")
    public Response getExperimentalEquipmentManagementList(@RequestParam String teaching_experiment_center_name,
                                                           @RequestParam String laboratory_name,
                                                           @RequestParam String laboratory_compartment_name,
                                                           @RequestParam String laboratory_room_name,
                                                           @RequestParam String is_movable,
                                                           @RequestParam String experimental_equipment_name) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("teaching_experiment_center_name", teaching_experiment_center_name);
        queryUtils.setQueryCondition("laboratory_name", laboratory_name);
        queryUtils.setQueryCondition("laboratory_compartment_name", laboratory_compartment_name);
        queryUtils.setQueryCondition("laboratory_room_name", laboratory_room_name);
        queryUtils.setQueryCondition("is_movable", is_movable);
        queryUtils.setFuzzyQueryCondition("experimental_equipment_name", experimental_equipment_name);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_experimental_equipment" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
