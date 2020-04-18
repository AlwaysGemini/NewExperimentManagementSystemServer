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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rulesOfSelectingCourses")
public class RulesOfSelectingCoursesController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("insertData")
    public Response insertData(@RequestParam String year,
                               @RequestParam String semester,
                               @RequestParam Timestamp start_time,
                               @RequestParam Timestamp end_time,
                               @RequestParam int is_withdrawable,
                               @RequestParam int is_reelect,
                               @RequestParam int is_selective_capacity,
                               @RequestParam int is_click_to_run) {
        jdbcTemplate.execute("insert into rules_of_selecting_courses(year," +
                "semester," +
                "start_time," +
                "end_time," +
                "is_withdrawable," +
                "is_reelect," +
                "is_selective_capacity," +
                "is_click_to_run) value(?,?,?,?,?,?,?,?)", new PreparedStatementCallback<String>() {
            @Override
            public String doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, year);
                ps.setString(2, semester);
                ps.setTimestamp(3, start_time);
                ps.setTimestamp(4, end_time);
                ps.setInt(5, is_withdrawable);
                ps.setInt(6, is_reelect);
                ps.setInt(7, is_selective_capacity);
                ps.setInt(8, is_click_to_run);
                ps.execute();
                return null;
            }
        });
        Response response = new Response();
        if (jdbcTemplate.queryForList("select * from rules_of_selecting_courses where year= ? and semester = ?", year, semester).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    @PostMapping("getQueryConditionList")
    public Response getQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct year from view_rules_of_selecting_courses"));
        lists.add(jdbcTemplate.queryForList("select distinct semester from view_rules_of_selecting_courses"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    @PostMapping("getData")
    public Response getList(@RequestParam String year,
                            @RequestParam String semester) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("year", year);
        queryUtils.setQueryCondition("semester", semester);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_rules_of_selecting_courses" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
