package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experimentCourseMatch")
public class ExperimentCourseMatchController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @return
     */
    @PostMapping("getUnmatchedList")
    public Response getUnmatchedList() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_unmatched_of_experiment_course");
        return new Response(200, "获取未配课列表成功", list);
    }

    /**
     * @return
     */
    @PostMapping("getMatchedList")
    public Response getMatchedList() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_matched_of_experiment_course");
        return new Response(200, "获取已配课列表成功", list);
    }

    /**
     * @return
     */
    @PostMapping("match")
    public Response match(String experimental_teaching_class_id, String capacity) {
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("experiment_course_match")
                .addItem("experimental_teaching_class_id", experimental_teaching_class_id)
                .addItem("capacity", capacity)
                .create();
        jdbcTemplate.execute(insertString);
        return new Response(200, "生成配课成功", new ArrayList<>());
    }
}
