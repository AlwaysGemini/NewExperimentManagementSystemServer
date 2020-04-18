package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("maintenanceOfTeachingExperimentalClass")
public class MaintenanceOfTeachingExperimentalClassController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param experimental_teaching_class_id
     * @param experimental_teaching_class_name
     * @param year
     * @param semester
     * @param composition_of_teaching_class
     * @param course_experiment_outline_id
     * @return
     */
    @PostMapping("insertData")
    private Response insertMaintenanceOfTeachingExperimentalClassData(@RequestParam String experimental_teaching_class_id,
                                                                      @RequestParam String experimental_teaching_class_name,
                                                                      @RequestParam String year,
                                                                      @RequestParam String semester,
                                                                      @RequestParam String composition_of_teaching_class,
                                                                      @RequestParam String course_experiment_outline_id) {
        Response response = new Response();
        jdbcTemplate.execute("insert into experimental_teaching_class(experimental_teaching_class_id," +
                "experimental_teaching_class_name," +
                "year," +
                "semester," +
                "composition_of_teaching_class," +
                "course_experiment_outline_id) value(?,?,?,?,?,?)", new PreparedStatementCallback<String>() {
            @Override
            public String doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, experimental_teaching_class_id);
                ps.setString(2, experimental_teaching_class_name);
                ps.setString(3, year);
                ps.setString(4, semester);
                ps.setString(5, composition_of_teaching_class);
                ps.setString(6, course_experiment_outline_id);
                ps.execute();
                return null;
            }
        });
        if (jdbcTemplate.queryForList("select * from view_experimental_teaching_class where experimental_teaching_class_id= ?", experimental_teaching_class_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("getQueryConditionList")
    public Response getMaintenanceOfTeachingExperimentalClassQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct year from view_experimental_teaching_class"));
        lists.add(jdbcTemplate.queryForList("select distinct semester from view_experimental_teaching_class"));
        lists.add(jdbcTemplate.queryForList("select distinct college from view_experimental_teaching_class"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param year
     * @param semester
     * @param college
     * @param experimental_teaching_class_name
     * @param course
     * @return
     */
    @PostMapping("getData")
    public Response getMaintenanceOfTeachingExperimentalClassList(@RequestParam String year,
                                                                  @RequestParam String semester,
                                                                  @RequestParam String college,
                                                                  @RequestParam String experimental_teaching_class_name,
                                                                  @RequestParam String course) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("year", year);
        queryUtils.setQueryCondition("semester", semester);
        queryUtils.setQueryCondition("college", college);
        queryUtils.setFuzzyQueryCondition("experimental_teaching_class_name", experimental_teaching_class_name);
        queryUtils.setHybridFuzzyQuery("course_id", course);
        queryUtils.setHybridFuzzyQuery("course_chinese_name", course);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_experimental_teaching_class" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
