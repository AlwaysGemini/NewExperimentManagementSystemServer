package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experimentAchievement")
public class ExperimentAchievementController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("getExperimentAchievementTableSummary")
    public Response getExperimentAchievementTableSummary(@RequestParam String user_id) {
        String queryString = new SQLBuilder
                .QueryBuilder()
                .addQueryItem("distinct experimental_teaching_class.year")
                .addQueryItem("experimental_teaching_class.semester")
                .addQueryItem("experiment_course_match.experiment_achievement_table_state")
                .addQueryItem("course.college")
                .addQueryItem("course.course_id")
                .addQueryItem("course.course_chinese_name")
                .addQueryItem("experimental_teaching_class.experimental_teaching_class_name")
                .addQueryItem("experiment_course_match.experiment_course_match_id")
                .fromTable("experiment_course_match")
                .fromTable("course_experiment_outline")
                .fromTable("experimental_teaching_class")
                .fromTable("allocation_of_courses")
                .fromTable("course")
                .join("course_experiment_outline.course_experiment_outline_id", "experimental_teaching_class.course_experiment_outline_id")
                .join("course_experiment_outline.allocation_of_courses_id", "allocation_of_courses.allocation_of_courses_id")
                .join("allocation_of_courses.course_id", "course.course_id")
                .join("experiment_course_match.experimental_teaching_class_id", "experimental_teaching_class.experimental_teaching_class_id")
                .addWhereEqualTo("teacher_id", user_id)
                .create();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(queryString);
        return new Response(200, "获取实验项目成绩名单成功", list);
    }

    /**
     * @return
     */
    @PostMapping("getTemplate")
    public Response getTemplate(@RequestParam String experiment_course_match_id) {
        String queryString = new SQLBuilder
                .QueryBuilder()
                .addQueryItem("*")
                .fromTable("template_experiment_item_achievement")
                .addWhereEqualTo("experiment_course_match_id", experiment_course_match_id)
                .create();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(queryString);
        return new Response(200, "获取实验项目成绩模板成功", list);
    }

    @PostMapping("upload")
    public Response upload(@RequestParam("file") MultipartFile file,
                           @RequestParam String experiment_course_match_id) {
        if (file.isEmpty()) {
            return new Response(201, "上传失败!", null);
        }
        String fileName = file.getOriginalFilename();
        String filePath = "/always/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String updateString = new SQLBuilder.UpdateBuilder()
                .from("experiment_course_match")
                .set("experiment_achievement_table_url", fileName)
                .set("experiment_achievement_table_state", "待审核")
                .addWhereEqualTo("experiment_course_match_id", experiment_course_match_id)
                .create();
        jdbcTemplate.execute(updateString);
        return new Response(200, "上传成功", null);
    }

    @PostMapping("examining")
    public Response examining(@RequestParam int experiment_course_match_id,
                              @RequestParam String experiment_achievement_table_state) {
        String updateString = new SQLBuilder.UpdateBuilder()
                .from("experiment_course_match")
                .set("experiment_achievement_table_state", experiment_achievement_table_state)
                .addWhereEqualTo("experiment_course_match_id", experiment_course_match_id)
                .create();
        jdbcTemplate.execute(updateString);
        return new Response(200, "审核成功", new ArrayList<>());
    }

    @PostMapping("importExperimentAchievement")
    public Response importExperimentAchievement(@RequestBody JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String experimental_course_selection_id = jsonObject.getString("experimental_course_selection_id");
            String experimental_item_score = jsonObject.getString("experimental_item_score");
            String updateString = new SQLBuilder.UpdateBuilder()
                    .from("experimental_item_achievement")
                    .set("experimental_item_score", experimental_item_score)
                    .addWhereEqualTo("experimental_course_selection_id", experimental_course_selection_id)
                    .create();
            jdbcTemplate.update(updateString);
        }
        return new Response(200, "插入成功", new ArrayList<>());
    }
}
