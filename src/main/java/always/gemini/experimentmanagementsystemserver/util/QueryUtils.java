package always.gemini.experimentmanagementsystemserver.util;

/**
 * @version V1.0
 * @Description:查询工具类，用于字符串的拼接
 * @author: 周清
 * @date: 2020-02-07 21:38
 */
public class QueryUtils {

    private int count;
    private String condition;

    /**
     * @Description: 初始化
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @param:
     * @return:
     */
    public QueryUtils() {
        this.count = 0;
        this.condition = "";
    }

    /**
     * @param fieldValue 字段值
     * @Description: 设置普通查询条件
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @param: fieldName 字段名
     * @return:
     */
    public void setQueryCondition(String fieldName, String fieldValue) {
        if (!fieldValue.equals("全部")) {
            if (count == 0) {
                condition += " where " + fieldName + " = '" + fieldValue + "'";
            } else {
                condition += " and " + fieldName + " = '" + fieldValue + "'";
            }
            count++;
        }
    }

    /**
     * @param fieldValue 字段值
     * @Description: 设置模糊查询
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @param: fieldName 字段名
     * @return:
     */
    public void setFuzzyQueryCondition(String fieldName, String fieldValue) {
        if (!fieldValue.equals("")) {
            if (count == 0) {
                condition += " where " + fieldName + " like '%" + fieldValue + "%'";
            } else {
                condition += " and " + fieldName + " like '%" + fieldValue + "%'";
            }
            count++;
        }
    }

    /**
     * @param fieldValue 字段值
     * @Description: 设置混合模糊查询，用于or的情形
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @param: fieldName 字段名
     * @return:
     */
    public void setHybridFuzzyQuery(String fieldName, String fieldValue) {
        if (!fieldValue.equals("")) {
            if (count == 0) {
                condition += " where " + fieldName + " like '%" + fieldValue + "%'";
            } else {
                condition += " or " + fieldName + " like '%" + fieldValue + "%'";
            }
            count++;
        }
    }

    /**
     * @Description: 获得最终的查询条件
     * @auther: 周清
     * @date: 22:05 2020/2/7
     * @return: condition
     */
    public String getCondition() {
        return condition;
    }

}
