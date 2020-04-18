package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("laboratoryPersonnelManagement")
public class LaboratoryPersonnelManagementController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param job_number
     * @param title
     * @param laboratory_id
     * @param incumbency
     * @return
     */
    @PostMapping("insertData")
    private Response insertLaboratoryPersonnelManagementData(@RequestParam String job_number,
                                                             @RequestParam String title,
                                                             @RequestParam String laboratory_id,
                                                             @RequestParam String incumbency) {
        Response response = new Response();

        String user_id = "";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select user_id from all_user where user_account = " + "'" + job_number + "'");
        if (sqlRowSet.next()) {
            user_id = sqlRowSet.getString("user_id");
        } else {
            response.setFail(201, "用户不存在", new ArrayList<>());
            return response;
        }

        String insertString = new SQLBuilder.InsertBuilder()
                .into("laboratory_personnel")
                .addItem("user_id", user_id)
                .addItem("title", title)
                .addItem("laboratory_id", laboratory_id)
                .addItem("incumbency", incumbency)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from laboratory_personnel where user_id = ?", user_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("getQueryConditionList")
    public Response getTeachingExperimentCenterQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct teaching_experiment_center_name from view_laboratory_personnel"));
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_name from view_laboratory_personnel"));
        lists.add(jdbcTemplate.queryForList("select distinct incumbency from view_laboratory_personnel"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    @PostMapping("getData")
    public Response getLaboratoryPersonnelManagementList(@RequestParam String teaching_experiment_center_name,
                                                         @RequestParam String laboratory_name,
                                                         @RequestParam String incumbency,
                                                         @RequestParam String full_name) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("teaching_experiment_center_name", teaching_experiment_center_name);
        queryUtils.setQueryCondition("teaching_experiment_center_name", teaching_experiment_center_name);
        queryUtils.setQueryCondition("laboratory_name", laboratory_name);
        queryUtils.setQueryCondition("incumbency", incumbency);
        queryUtils.setFuzzyQueryCondition("name", full_name);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_laboratory_personnel" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
