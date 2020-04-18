package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.bean.StudentSelectionTable;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experimentScheduling")
public class ExperimentSchedulingController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("getFreeTimeData")
    public Response getFreeTimeData() {
        int[][][] freeTime = new int[20][7][12];
        List<StudentSelectionTable> list = jdbcTemplate.query("select * from view_student_selection_table", new BeanPropertyRowMapper<>(StudentSelectionTable.class));
        for (StudentSelectionTable studentSelectionTable : list) {
            for (int week = studentSelectionTable.getWeek_of_origin() - 1; week < studentSelectionTable.getWeek_of_origin() + studentSelectionTable.getWeek_of_end() - 1; week++) {
                for (int courseTime = studentSelectionTable.getStart_time() - 1; courseTime < studentSelectionTable.getLength(); courseTime++) {
                    freeTime[week][studentSelectionTable.getDay() - 1][courseTime]++;
                }
            }
        }
        Response response = new Response();
        response.setSuccess("获取查询条件成功", freeTime);
        return response;
    }

    @PostMapping("getLaboratoryRoomList")
    public Response getLaboratoryRoomList() {
        Response response = new Response();
        List<List<Map<String, Object>>> lists = new ArrayList<>();
        lists.add(jdbcTemplate.queryForList("select distinct laboratory_room_name from laboratory_room"));
        response.setSuccess("获取查询条件成功", lists);
        return response;
    }

    @PostMapping("getUnAllocationData")
    public Response getUnAllocationData(@RequestParam String teacher_id) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from query_experiment_item where teacher_id = ?", teacher_id);
        Response response = new Response();
        response.setSuccess("获取查询条件成功", list);
        return response;
    }

    @PostMapping("insertData")
    public Response insertData(@RequestParam String experiment_scheduling_id,
                               @RequestParam String instructor_id,
                               @RequestParam String laboratory_room_id,
                               @RequestParam int week,
                               @RequestParam int day,
                               @RequestParam int start_time,
                               @RequestParam int length) {

        jdbcTemplate.update("update experiment_scheduling set instructor_id = ?,laboratory_room_id=?,week = ?,day = ?,start_time=?,length=? where experiment_scheduling_id = ?",
                instructor_id,
                laboratory_room_id,
                week,
                day,
                start_time,
                length,
                experiment_scheduling_id);
        Response response = new Response();
        response.setSuccess("插入成功", new ArrayList<>());
        return response;
    }
}
