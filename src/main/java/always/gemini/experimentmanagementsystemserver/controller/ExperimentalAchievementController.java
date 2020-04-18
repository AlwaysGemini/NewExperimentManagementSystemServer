package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experimentalAchievement")
public class ExperimentalAchievementController {
    static private Workbook wb;
    static private Sheet sheet;
    static private Row row;
    static private Cell cell;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("getExperimentalAchievementSummary")
    public Response getExperimentalAchievementSummary(@RequestParam String user_id) {
        Response response = new Response();
        String queryStringForUserRole = new SQLBuilder.QueryBuilder()
                .addQueryItem("user_role")
                .fromTable("all_user")
                .addWhereEqualTo("user_id", user_id)
                .create();
        String user_role = jdbcTemplate.queryForObject(queryStringForUserRole, String.class);
        System.out.println(user_role);
        List<Map<String, Object>> list = new ArrayList<>();
        switch (user_role) {
            case "教师":
                String queryStringForTeacher = new SQLBuilder.QueryBuilder()
                        .addQueryItem("distinct experimental_teaching_class.year")
                        .addQueryItem("experimental_teaching_class.semester")
                        .addQueryItem("experiment_course_match.experiment_achievement_table_state")
                        .addQueryItem("course.college")
                        .addQueryItem("course.course_id")
                        .addQueryItem("course.course_chinese_name")
                        .addQueryItem("experimental_teaching_class.experimental_teaching_class_name")
                        .addQueryItem("experiment_course_match.experiment_course_match_id")
                        .fromTable("experimental_teaching_class")
                        .fromTable("course")
                        .fromTable("allocation_of_courses")
                        .fromTable("course_experiment_outline")
                        .fromTable("experiment_course_match")
                        .fromTable("all_user")
                        .join("course.course_id", "allocation_of_courses.course_id")
                        .join("allocation_of_courses.allocation_of_courses_id", "course_experiment_outline.allocation_of_courses_id")
                        .join("course_experiment_outline.course_experiment_outline_id", "experimental_teaching_class.course_experiment_outline_id")
                        .join("experimental_teaching_class.experimental_teaching_class_id", "experiment_course_match.experimental_teaching_class_id")
                        .join("experimental_teaching_class.teacher_id", "user_id")
                        .create();
                list = jdbcTemplate.queryForList(queryStringForTeacher);
                break;
            case "管理员":
            case "管理员+教师":
                String queryStringForManager = new SQLBuilder.QueryBuilder()
                        .addQueryItem("distinct experimental_teaching_class.year")
                        .addQueryItem("experimental_teaching_class.semester")
                        .addQueryItem("experiment_course_match.experiment_achievement_table_state")
                        .addQueryItem("course.college")
                        .addQueryItem("course.course_id")
                        .addQueryItem("course.course_chinese_name")
                        .addQueryItem("experimental_teaching_class.experimental_teaching_class_name")
                        .addQueryItem("experiment_course_match.experiment_course_match_id")
                        .fromTable("experimental_teaching_class")
                        .fromTable("course")
                        .fromTable("allocation_of_courses")
                        .fromTable("course_experiment_outline")
                        .fromTable("experiment_course_match")
                        .join("course.course_id", "allocation_of_courses.course_id")
                        .join("allocation_of_courses.allocation_of_courses_id", "course_experiment_outline.allocation_of_courses_id")
                        .join("course_experiment_outline.course_experiment_outline_id", "experimental_teaching_class.course_experiment_outline_id")
                        .join("experimental_teaching_class.experimental_teaching_class_id", "experiment_course_match.experimental_teaching_class_id")
                        .create();
                list = jdbcTemplate.queryForList(queryStringForManager);
                break;
        }
        response.setSuccess("查询成功", list);
        return response;
    }

    @PostMapping("test")
    public Response test() {
        File file = null;
        try {
            file = ResourceUtils.getFile("上机实验3项目指导书.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            InputStream is = new FileInputStream(file);
            // 针对2007 Excel 文件
            wb = new XSSFWorkbook(is);
            sheet = wb.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();// 得到总行数
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        return new Response(200, "行数:" + rowNum + " 列数:" + colNum, new ArrayList<>());
    }

}
