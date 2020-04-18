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
@RequestMapping("experimentalOrganization")
public class ExperimentalOrganizationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param teaching_experiment_center_id
     * @param teaching_experiment_center_name
     * @param laboratory_type
     * @param subordinate_unit
     * @param subordinate_discipline
     * @param year_of_establishment
     * @param remarks
     * @param enable_flag
     * @return
     */
    @PostMapping("teachingExperimentCenter/insertData")
    private Response insertTeachingExperimentCenterData(@RequestParam String teaching_experiment_center_id,
                                                        @RequestParam String teaching_experiment_center_name,
                                                        @RequestParam String laboratory_type,
                                                        @RequestParam String subordinate_unit,
                                                        @RequestParam String subordinate_discipline,
                                                        @RequestParam String year_of_establishment,
                                                        @RequestParam String remarks,
                                                        @RequestParam String enable_flag) {
        Response response = new Response();
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("teaching_experiment_center")
                .addItem("teaching_experiment_center_id", teaching_experiment_center_id)
                .addItem("teaching_experiment_center_name", teaching_experiment_center_name)
                .addItem("laboratory_type", laboratory_type)
                .addItem("subordinate_unit", subordinate_unit)
                .addItem("subordinate_discipline", subordinate_discipline)
                .addItem("year_of_establishment", year_of_establishment)
                .addItem("remarks", remarks)
                .addItem("enable_flag", enable_flag)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from view_teaching_experiment_center where teaching_experiment_center_id = ?", teaching_experiment_center_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("teachingExperimentCenter/getQueryConditionList")
    public Response getTeachingExperimentCenterQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_type from view_teaching_experiment_center"));
        lists.add(jdbcTemplate.queryForList("select distinct enable_flag from view_teaching_experiment_center"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param laboratory_type
     * @param enable_flag
     * @return
     */
    @PostMapping("teachingExperimentCenter/getData")
    private Response getTeachingExperimentCenterList(@RequestParam String laboratory_type,
                                                     @RequestParam String enable_flag) {
        Response response = new Response();

        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("laboratory_type", laboratory_type);
        queryUtils.setQueryCondition("enable_flag", enable_flag);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_teaching_experiment_center" + queryUtils.getCondition());
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }

    @PostMapping("teachingExperimentCenter/delete")
    public Response delete(@RequestParam String teaching_experiment_center_id){
        String deleteString = new SQLBuilder.DeleteBuilder()
                .from("teaching_experiment_center")
                .addWhereEqualTo("teaching_experiment_center_id",teaching_experiment_center_id)
                .create();
        jdbcTemplate.execute(deleteString);
        return new Response(200,"删除成功",null);
    }

    /**
     * @param laboratory_id
     * @param laboratory_name
     * @param teaching_experiment_center_id
     * @param laboratory_director
     * @param rules_and_regulations
     * @param remarks
     * @param enable_flag
     * @return
     */
    @PostMapping("laboratory/insertData")
    public Response insertLaboratoryData(@RequestParam String laboratory_id,
                                         @RequestParam String laboratory_name,
                                         @RequestParam String teaching_experiment_center_id,
                                         @RequestParam String laboratory_director,
                                         @RequestParam String rules_and_regulations,
                                         @RequestParam String remarks,
                                         @RequestParam String enable_flag) {
        Response response = new Response();
        if (jdbcTemplate.queryForList("select * from view_laboratory where teaching_experiment_center_id = " + "'" + teaching_experiment_center_id + "'").size() == 0) {
            response.setFail(201, "隶属实验中心不存在", new ArrayList<>());
            return response;
        }
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("laboratory")
                .addItem("laboratory_id", laboratory_id)
                .addItem("laboratory_name", laboratory_name)
                .addItem("teaching_experiment_center_id", teaching_experiment_center_id)
                .addItem("laboratory_director", laboratory_director)
                .addItem("rules_and_regulations", rules_and_regulations)
                .addItem("remarks", remarks)
                .addItem("enable_flag", enable_flag)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from view_laboratory where laboratory_code = ?", laboratory_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    /**
     * @return
     */
    @PostMapping("laboratory/getQueryConditionList")
    public Response getLaboratoryQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct teaching_experiment_center_name from view_laboratory"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_name from view_laboratory"));
        lists.add(jdbcTemplate.queryForList("select distinct enable_flag from view_laboratory"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param teaching_experiment_center_name
     * @param laboratory_name
     * @param enable_flag
     * @return
     */
    @PostMapping("laboratory/getData")
    private Response getTeachingExperimentCenterList(@RequestParam String teaching_experiment_center_name,
                                                     @RequestParam String laboratory_name,
                                                     @RequestParam String enable_flag) {
        Response response = new Response();

        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("teaching_experiment_center_name", teaching_experiment_center_name);
        queryUtils.setQueryCondition("laboratory_name", laboratory_name);
        queryUtils.setQueryCondition("enable_flag", enable_flag);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_laboratory" + queryUtils.getCondition());
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }

    /**
     * @param laboratory_compartment_id
     * @param laboratory_compartment_name
     * @param laboratory_id
     * @param remarks
     * @param enable_flag
     * @return
     */
    @PostMapping("experimentalCompartment/insertData")
    private Response insertExperimentalCompartmentData(@RequestParam String laboratory_compartment_id,
                                                       @RequestParam String laboratory_compartment_name,
                                                       @RequestParam String laboratory_id,
                                                       @RequestParam String remarks,
                                                       @RequestParam String enable_flag) {
        Response response = new Response();
        if (jdbcTemplate.queryForList("select * from view_laboratory where laboratory_id = " + "'" + laboratory_id + "'").size() == 0) {
            response.setFail(201, "隶属实验分室不存在", new ArrayList<>());
            return response;
        }
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("laboratory_compartment")
                .addItem("laboratory_compartment_id", laboratory_compartment_id)
                .addItem("laboratory_compartment_name", laboratory_compartment_name)
                .addItem("laboratory_id", laboratory_id)
                .addItem("remarks", remarks)
                .addItem("enable_flag", enable_flag)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from laboratory_compartment where laboratory_compartment_id = ?", laboratory_compartment_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    /**
     * @return
     */
    @PostMapping("experimentalCompartment/getQueryConditionList")
    public Response getExperimentalCompartmentQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct teaching_experiment_center_name from view_laboratory_compartment"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_name from view_laboratory_compartment"));
        lists.add(jdbcTemplate.queryForList("select distinct enable_flag from view_laboratory_compartment"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param teaching_experiment_center_name
     * @param laboratory_name
     * @param enable_flag
     * @return
     */
    @PostMapping("experimentalCompartment/getData")
    private Response getExperimentalCompartmentList(@RequestParam String teaching_experiment_center_name,
                                                    @RequestParam String laboratory_name,
                                                    @RequestParam String enable_flag) {
        Response response = new Response();

        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("teaching_experiment_center_name", teaching_experiment_center_name);
        queryUtils.setQueryCondition("laboratory_name", laboratory_name);
        queryUtils.setQueryCondition("enable_flag", enable_flag);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_laboratory_compartment" + queryUtils.getCondition());
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }

    /**
     * @param laboratory_room_id
     * @param laboratory_room_name
     * @param laboratory_compartment_id
     * @param nature_of_experimental_site
     * @param category_of_scientific_research_base
     * @param person_in_charge_of_the_experimental_room
     * @param status_of_joint_construction
     * @param campus
     * @param capacity
     * @param remarks
     * @param enable_flag
     * @return
     */
    @PostMapping("laboratoryRoom/insertData")
    private Response insertLaboratoryRoomData(@RequestParam String laboratory_room_id,
                                              @RequestParam String laboratory_room_name,
                                              @RequestParam String laboratory_compartment_id,
                                              @RequestParam String nature_of_experimental_site,
                                              @RequestParam String category_of_scientific_research_base,
                                              @RequestParam String person_in_charge_of_the_experimental_room,
                                              @RequestParam String status_of_joint_construction,
                                              @RequestParam String campus,
                                              @RequestParam String capacity,
                                              @RequestParam String remarks,
                                              @RequestParam String enable_flag) {
        Response response = new Response();
        if (jdbcTemplate.queryForList("select * from view_laboratory_compartment where laboratory_compartment_id = " + "'" + laboratory_compartment_id + "'").size() == 0) {
            response.setFail(201, "隶属实验分室不存在", new ArrayList<>());
            return response;
        }
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("laboratory_room")
                .addItem("laboratory_room_id", laboratory_room_id)
                .addItem("laboratory_room_name", laboratory_room_name)
                .addItem("laboratory_compartment_id", laboratory_compartment_id)
                .addItem("nature_of_experimental_site", nature_of_experimental_site)
                .addItem("category_of_scientific_research_base", category_of_scientific_research_base)
                .addItem("person_in_charge_of_the_experimental_room", person_in_charge_of_the_experimental_room)
                .addItem("status_of_joint_construction", status_of_joint_construction)
                .addItem("campus", campus)
                .addItem("capacity", capacity)
                .addItem("remarks", remarks)
                .addItem("enable_flag", enable_flag)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from laboratory_room where laboratory_room_id = ?", laboratory_room_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    /**
     * @return
     */
    @PostMapping("laboratoryRoom/getQueryConditionList")
    public Response getLaboratoryRoomQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct teaching_experiment_center_name from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_name from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_compartment_name from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct nature_of_experimental_site from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct category_of_scientific_research_base from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct status_of_joint_construction from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct campus from view_laboratory_room"));
        lists.add(jdbcTemplate.queryForList("select distinct enable_flag from view_laboratory_room"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param teaching_experiment_center_name
     * @param laboratory_name
     * @param laboratory_compartment_name
     * @param nature_of_experimental_site
     * @param category_of_scientific_research_base
     * @param status_of_joint_construction
     * @param campus
     * @param enable_flag
     * @return
     */
    @PostMapping("laboratoryRoom/getData")
    private Response getLaboratoryRoomList(@RequestParam String teaching_experiment_center_name,
                                           @RequestParam String laboratory_name,
                                           @RequestParam String laboratory_compartment_name,
                                           @RequestParam String nature_of_experimental_site,
                                           @RequestParam String category_of_scientific_research_base,
                                           @RequestParam String status_of_joint_construction,
                                           @RequestParam String campus,
                                           @RequestParam String enable_flag) {
        Response response = new Response();

        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("teaching_experiment_center", teaching_experiment_center_name);
        queryUtils.setQueryCondition("laboratory_name", laboratory_name);
        queryUtils.setQueryCondition("laboratory_compartment_name", laboratory_compartment_name);
        queryUtils.setQueryCondition("nature_of_experimental_site", nature_of_experimental_site);
        queryUtils.setQueryCondition("category_of_scientific_research_base", category_of_scientific_research_base);
        queryUtils.setQueryCondition("status_of_joint_construction", status_of_joint_construction);
        queryUtils.setQueryCondition("campus", campus);
        queryUtils.setQueryCondition("enable_flag", enable_flag);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_laboratory_room" + queryUtils.getCondition());
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
