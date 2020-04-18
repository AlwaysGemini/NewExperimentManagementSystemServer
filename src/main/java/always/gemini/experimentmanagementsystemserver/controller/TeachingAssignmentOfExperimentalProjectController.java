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
@RequestMapping("teachingAssignmentOfExperimentalProject")
public class TeachingAssignmentOfExperimentalProjectController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("getQueryConditionList")
    public Response getQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct year from view_teaching_assignment_of_experimental_project"));
        lists.add(jdbcTemplate.queryForList("select distinct semester from view_teaching_assignment_of_experimental_project"));
        lists.add(jdbcTemplate.queryForList("select distinct college from view_teaching_assignment_of_experimental_project"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    @PostMapping("getData")
    public Response getData(@RequestParam String year,
                            @RequestParam String semester,
                            @RequestParam String college,
                            @RequestParam String course) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("year", year);
        queryUtils.setQueryCondition("semester", semester);
        queryUtils.setQueryCondition("college", college);
        queryUtils.setHybridFuzzyQuery("course_id", course);
        queryUtils.setHybridFuzzyQuery("course_chinese_name", course);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_teaching_assignment_of_experimental_project" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
