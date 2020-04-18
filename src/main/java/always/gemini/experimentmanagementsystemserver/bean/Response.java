package always.gemini.experimentmanagementsystemserver.bean;

/**
 * @Description: 工程统一的返回包
 * @auther: 周清
 * @date: 22:05 2020/2/7
 */
public class Response {
    private int code;
    private String msg;
    private Object data;

    public Response() {
    }

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * @Description: 返回成功
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @param:
     * @return:
     */
    public void setSuccess(String msg, Object data) {
        code = 200;
        this.msg = msg;
        this.data = data;
    }

    /**
     * @Description: 返回失败
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @param:
     * @return:
     */
    public void setFail(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
