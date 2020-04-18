package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
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
@RequestMapping("courseExperimentProject")
public class CourseExperimentProjectController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @return
     */
    @PostMapping("getQueryConditionList")
    public Response getQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct college from view_course"));
        lists.add(jdbcTemplate.queryForList("select distinct course_category from view_course"));
        lists.add(jdbcTemplate.queryForList("select distinct course_assignment from view_course"));
        lists.add(jdbcTemplate.queryForList("select distinct enabling_grade from view_course"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param college
     * @param course_category
     * @param course_assignment
     * @param enabling_grade
     * @param course
     * @return
     */
    @PostMapping("getData")
    public Response getCourseExperimentProjectList(@RequestParam String college,
                                                   @RequestParam String course_category,
                                                   @RequestParam String course_assignment,
                                                   @RequestParam String enabling_grade,
                                                   @RequestParam String course) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("college", college);
        queryUtils.setQueryCondition("course_category", course_category);
        queryUtils.setQueryCondition("course_assignment", course_assignment);
        queryUtils.setQueryCondition("enabling_grade", enabling_grade);
        queryUtils.setFuzzyQueryCondition("course_chinese_name", course);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_course" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }

}
