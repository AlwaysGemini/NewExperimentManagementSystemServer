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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experimentProjectInstruction")
public class ExperimentProjectInstructionController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/getExperimentProjectInstructionState")
    public Response getExperimentProjectInstructionState(@RequestParam String user_id) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!user_id.equals("")) {
            list = jdbcTemplate.queryForList("select * " +
                    "from view_experiment_project_instruction " +
                    "where user_id = ?", user_id);

        } else {
            list = jdbcTemplate.queryForList("select * " +
                    "from view_experiment_project_instruction ");
        }
        return new Response(200, "获取成功", list);
    }

    /**
     * 文件上传具体实现方法;
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Response upload(@RequestParam("file") MultipartFile file,
                           @RequestParam int experiment_project_instruction_id) {
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
        jdbcTemplate.update("update experiment_project_instruction " +
                "set state = '待审核',file_name = " + "'" + fileName + "'" +
                "where experiment_project_instruction_id = " + experiment_project_instruction_id);
        return new Response(200, "上传成功", null);
    }

    @PostMapping("/examining")
    public Response examining(@RequestParam int experiment_project_instruction_id,
                              @RequestParam String state) {
        jdbcTemplate.update("update experiment_project_instruction " +
                "set state = '" + state + "'" +
                "where experiment_project_instruction_id = " + experiment_project_instruction_id);
        return new Response(200, "审核成功", null);
    }
}
