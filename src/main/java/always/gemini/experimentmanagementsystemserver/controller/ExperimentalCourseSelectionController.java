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
@RequestMapping("experimentalCourseSelection")
public class ExperimentalCourseSelectionController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @return
     */
    @PostMapping("getExperimentalCourseList")
    public Response getExperimentalCourseList() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_matched_of_experiment_course");
        return new Response(200, "获取查询条件成功", list);
    }

    @PostMapping("select")
    public Response select(@RequestParam String student_id,
                           @RequestParam String experiment_course_match_id) {
        jdbcTemplate.execute(new SQLBuilder.InsertBuilder()
                .into("experimental_course_selection")
                .addItem("experiment_course_match_id", experiment_course_match_id)
                .addItem("student_id", student_id)
                .create());
        return new Response(200, "选课成功", new ArrayList<>());
    }
}
