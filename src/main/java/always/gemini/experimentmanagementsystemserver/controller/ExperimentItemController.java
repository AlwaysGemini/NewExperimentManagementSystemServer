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
@RequestMapping("experimentalProject")
public class ExperimentItemController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param experiment_item_id
     * @param experiment_item_name
     * @param experiment_content
     * @param experiment_hours
     * @param experiment_credits
     * @param experiment_attribute
     * @param experiment_type
     * @param experiment_category
     * @param subordinate_unit
     * @param subordinate_discipline
     * @param experiment_requirements
     * @return
     */
    @PostMapping("insertData")
    private Response insertExperimentalProjectData(@RequestParam String experiment_item_id,
                                                   @RequestParam String experiment_item_name,
                                                   @RequestParam String experiment_content,
                                                   @RequestParam String experiment_hours,
                                                   @RequestParam String experiment_credits,
                                                   @RequestParam String experiment_attribute,
                                                   @RequestParam String experiment_type,
                                                   @RequestParam String experiment_category,
                                                   @RequestParam String subordinate_unit,
                                                   @RequestParam String subordinate_discipline,
                                                   @RequestParam String experiment_requirements) {
        Response response = new Response();
        jdbcTemplate.execute("insert into experimental_project(experiment_item_id," +
                "experiment_item_name," +
                "experiment_content," +
                "experiment_hours," +
                "experiment_credits," +
                "experiment_attribute," +
                "experiment_type," +
                "experiment_category," +
                "subordinate_unit," +
                "subordinate_discipline," +
                "experiment_requirements) value(?,?,?,?,?,?,?,?,?,?,?)", new PreparedStatementCallback<String>() {
            @Override
            public String doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, experiment_item_id);
                ps.setString(2, experiment_item_name);
                ps.setString(3, experiment_content);
                ps.setString(4, experiment_hours);
                ps.setString(5, experiment_credits);
                ps.setString(6, experiment_attribute);
                ps.setString(7, experiment_type);
                ps.setString(8, experiment_category);
                ps.setString(9, subordinate_unit);
                ps.setString(10, subordinate_discipline);
                ps.setString(11, experiment_requirements);
                ps.execute();
                return null;
            }
        });
        if (jdbcTemplate.queryForList("select * from experimental_project where experiment_item_id= ?", experiment_item_id).size() != 0) {
            response.setSuccess("插入成功", new ArrayList<>());
        } else {
            response.setFail(201, "插入失败", new ArrayList<>());
        }
        return response;
    }

    /**
     * @return
     */
    @PostMapping("getQueryConditionList")
    public Response getExperimentalProjectQueryConditionList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct experiment_attribute from experiment_item"));
        lists.add(jdbcTemplate.queryForList("select distinct experiment_type from experiment_item"));
        lists.add(jdbcTemplate.queryForList("select distinct experiment_category from experiment_item"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    /**
     * @param experiment_attribute
     * @param experiment_type
     * @param experiment_category
     * @param experiment_item_name
     * @return
     */
    @PostMapping("getData")
    public Response getExperimentalProjectList(@RequestParam String experiment_attribute,
                                               @RequestParam String experiment_type,
                                               @RequestParam String experiment_category,
                                               @RequestParam String experiment_item_name) {
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.setQueryCondition("experiment_attribute", experiment_attribute);
        queryUtils.setQueryCondition("experiment_type", experiment_type);
        queryUtils.setQueryCondition("experiment_category", experiment_category);
        queryUtils.setFuzzyQueryCondition("experiment_item_name", experiment_item_name);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from experiment_item" + queryUtils.getCondition());

        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("获取成功", list);
        } else {
            response.setFail(201, "获取失败", list);
        }
        return response;
    }
}
