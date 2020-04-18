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
@RequestMapping("experimentalConsumables")
public class ExperimentalConsumablesController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param experimental_consumables_id
     * @param experimental_consumables_name
     * @param current_inventory
     * @param maximum_inventory
     * @param minimum_inventory
     * @param model_specification
     * @param unit
     * @param unit_price
     * @param laboratory_room_id
     * @return
     */
    @PostMapping("insertData")
    private Response insertExperimentalConsumablesData(@RequestParam String experimental_consumables_id,
                                                       @RequestParam String experimental_consumables_name,
                                                       @RequestParam String current_inventory,
                                                       @RequestParam String maximum_inventory,
                                                       @RequestParam String minimum_inventory,
                                                       @RequestParam String model_specification,
                                                       @RequestParam String unit,
                                                       @RequestParam String unit_price,
                                                       @RequestParam String laboratory_room_id) {
        Response response = new Response();
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("experimental_consumables")
                .addItem("id", experimental_consumables_id)
                .addItem("experimental_consumables_name", experimental_consumables_name)
                .addItem("current_inventory", current_inventory)
                .addItem("maximum_inventory", maximum_inventory)
                .addItem("minimum_inventory", minimum_inventory)
                .addItem("model_specification", model_specification)
                .addItem("unit", unit)
                .addItem("unit_price", unit_price)
                .addItem("laboratory_room_id", laboratory_room_id)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from view_experimental_consumables where experimental_consumables_id= ?", experimental_consumables_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("getQueryConditionList")
    public Response getExperimentalConsumablesQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct teaching_experiment_center_name from view_experimental_consumables"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_name from view_experimental_consumables"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_compartment_name from view_experimental_consumables"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_room_name from view_experimental_consumables"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param teaching_experiment_center_name
     * @param laboratory_name
     * @param laboratory_compartment_name
     * @param laboratory_room_name
     * @param model_specification
     * @param experimental_consumables_name
     * @return
     */
    @PostMapping("getData")
    public Response getExperimentalConsumablesList(@RequestParam String teaching_experiment_center_name,
                                                   @RequestParam String laboratory_name,
                                                   @RequestParam String laboratory_compartment_name,
                                                   @RequestParam String laboratory_room_name,
                                                   @RequestParam String model_specification,
                                                   @RequestParam String experimental_consumables_name) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("teaching_experiment_center_name", teaching_experiment_center_name);
        queryUtils.setQueryCondition("laboratory_name", laboratory_name);
        queryUtils.setQueryCondition("laboratory_compartment_name", laboratory_compartment_name);
        queryUtils.setQueryCondition("laboratory_room_name", laboratory_room_name);
        queryUtils.setFuzzyQueryCondition("model_specification", model_specification);
        queryUtils.setFuzzyQueryCondition("experimental_consumables_name", experimental_consumables_name);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_experimental_consumables" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
