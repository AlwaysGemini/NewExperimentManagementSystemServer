package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.bean.insertBean.InsertCourseExperimentOutline;
import always.gemini.experimentmanagementsystemserver.bean.queryBean.QueryCourseExperimentOutline;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("courseExperimentOutline")
public class CourseExperimentOutlineController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param insertCourseExperimentOutline
     * @return
     */
    @PostMapping("insertData")
    private Response insertCourseExperimentOutlineData(@RequestBody InsertCourseExperimentOutline insertCourseExperimentOutline) {
        Response response = new Response();
        String insertString = new SQLBuilder
                .InsertBuilder()
                .into("course_experiment_outline")
                .addItem("course_experiment_outline_id",insertCourseExperimentOutline.getCourse_experiment_outline_id())
                .addItem("allocation_of_courses_id", insertCourseExperimentOutline.getAllocation_of_courses_id())
                .addItem("proportion_of_experimental_results", insertCourseExperimentOutline.getProportion_of_experimental_results())
                .addItem("experiment_item_id", insertCourseExperimentOutline.getExperiment_item_id())
                .create();
        jdbcTemplate.execute(insertString);
        if (jdbcTemplate.queryForList("select * from course_experiment_outline where allocation_of_courses_id= ?", insertCourseExperimentOutline.getAllocation_of_courses_id()).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("getData")
    public Response getList(@RequestBody QueryCourseExperimentOutline queryCourseExperimentOutline) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setHybridFuzzyQuery("course_id", queryCourseExperimentOutline.getCourse());
        queryUtils.setHybridFuzzyQuery("course_name", queryCourseExperimentOutline.getCourse());
        List<Map<String, Object>> list = new ArrayList<>();
        if (queryCourseExperimentOutline.getCourse().equals("")) {
            list = jdbcTemplate.queryForList("select * from view_course_experiment_outline");
        } else {
            list = jdbcTemplate.queryForList("select * from view_course_experiment_outline" + queryUtils.getCondition());
        }
        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return new Response(200,"获取成功",list);
    }
}
