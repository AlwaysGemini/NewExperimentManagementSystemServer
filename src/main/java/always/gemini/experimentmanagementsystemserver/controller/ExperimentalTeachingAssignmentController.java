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
@RequestMapping("experimentalTeachingAssignment")
public class ExperimentalTeachingAssignmentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param experimental_teaching_class_id
     * @param teacher_id
     * @param laboratory_room_id
     * @return
     */
    @PostMapping("insertData")
    private Response insertExperimentalTeachingAssignmentData(@RequestParam String experimental_teaching_class_id,
                                                              @RequestParam String teacher_id,
                                                              @RequestParam String laboratory_room_id) {
        Response response = new Response();
        int count = jdbcTemplate.queryForList("select * from experiment_scheduling where experimental_teaching_class_id = ?", experimental_teaching_class_id).size();
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("experiment_scheduling")
                .addItem("experimental_teaching_class_id", experimental_teaching_class_id)
                .addItem("teacher_id", teacher_id)
                .addItem("laboratory_room_id", laboratory_room_id)
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from experiment_scheduling where experimental_teaching_class_id = ?", experimental_teaching_class_id).size() == count + 1) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("getQueryConditionList")
    public Response getExperimentalTeachingAssignmentQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct year from view_experiment_teaching_assignment"));
        lists.add(jdbcTemplate.queryForList("select distinct semester from view_experiment_teaching_assignment"));
        lists.add(jdbcTemplate.queryForList("select distinct college from view_experiment_teaching_assignment"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param year
     * @param semester
     * @param college
     * @param course
     * @return
     */
    @PostMapping("getData")
    public Response getExperimentalTeachingAssignmentList(@RequestParam String year,
                                                          @RequestParam String semester,
                                                          @RequestParam String college,
                                                          @RequestParam String course) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("year", year);
        queryUtils.setQueryCondition("semester", semester);
        queryUtils.setQueryCondition("college", college);
        queryUtils.setHybridFuzzyQuery("course_id", course);
        queryUtils.setHybridFuzzyQuery("course_chinese_name", course);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_experiment_teaching_assignment" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
