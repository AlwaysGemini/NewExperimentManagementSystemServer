package always.gemini.experimentmanagementsystemserver.controller;

import always.gemini.experimentmanagementsystemserver.bean.Response;
import always.gemini.experimentmanagementsystemserver.util.QueryUtils;
import always.gemini.experimentmanagementsystemserver.util.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @return
     * @RequestBody user
     */
    @ResponseBody
    @PostMapping("login")
    public Response login(@RequestParam String user_account,
                          @RequestParam String user_password) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_all_user where user_account = ? and user_password = ?", user_account, user_password);
        Response response = new Response();
        if (list.size() != 0) {
            response.setSuccess("登陆成功", list.get(0));
        } else {
            response.setFail(201, "登陆失败", null);
        }
        return response;
    }

    @ResponseBody
    @PostMapping("changePassword")
    public Response changePassword(@RequestParam String account, @RequestParam String oldPassword, @RequestParam String newPassword) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from view_all_user where user_account = ? and user_password = ?", account, oldPassword);
        Response response = new Response();
        if (list.size() == 0) {
            response.setFail(201, "密码修改失败", null);
            return response;
        }

        jdbcTemplate.update("update view_all_user set user_password = ? where user_account = ? and user_password = ?", newPassword, account, oldPassword);

        response.setSuccess("密码修改成功", null);
        return response;
    }
}
