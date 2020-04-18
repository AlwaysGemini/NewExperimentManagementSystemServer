package always.gemini.experimentmanagementsystemserver.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: SQL语句生成类，函数式生成增删改查的SQL语句
 * @author: 周清
 * @date: 2020-04-11 9:40
 */
public class SQLBuilder {

    public static class QueryBuilder {
        int queryItemNum;
        int queryTableNum;
        int queryConditionNum;

        private StringBuffer queryString = new StringBuffer();

        public QueryBuilder() {
            queryItemNum = 0;
            queryTableNum = 0;
            queryConditionNum = 0;
            queryString.append("select ");
        }

        public QueryBuilder addQueryItem(String string) {
            if (queryItemNum == 0) {
                queryString.append(string);
            } else {
                queryString.append(",").append(string);
            }
            queryItemNum++;
            return this;
        }

        public QueryBuilder fromTable(String tableName) {
            if (queryTableNum == 0) {
                queryString.append(" from ").append(tableName);
            } else {
                queryString.append(",").append(tableName);
            }
            queryTableNum++;
            return this;
        }

        public QueryBuilder join(String fieldA, String fieldB) {
            if (queryConditionNum == 0) {
                queryString.append(" where ").append(fieldA).append(" = ").append(fieldB);
            } else {
                queryString.append(" and ").append(fieldA).append(" = ").append(fieldB);
            }
            queryConditionNum++;
            return this;
        }

        public QueryBuilder addWhereEqualTo(String fieldName, String value) {
            if (queryConditionNum == 0) {
                queryString.append(" where ").append(fieldName).append(" = '").append(value).append("'");
            } else {
                queryString.append(" and ").append(fieldName).append(" = '").append(value).append("'");
            }
            queryConditionNum++;
            return this;
        }

        public QueryBuilder addWhereEqualTo(String fieldName, int value) {
            if (queryConditionNum == 0) {
                queryString.append(" where ").append(fieldName).append(" = ").append(value);
            } else {
                queryString.append(" and ").append(fieldName).append(" = ").append(value);
            }
            queryConditionNum++;
            return this;
        }

        public QueryBuilder addWhereIn(String fieldName, String notInQueryString) {
            if (queryConditionNum == 0) {
                queryString.append(" where ").append(fieldName).append(" in(").append(notInQueryString).append(")");
            } else {
                queryString.append(" and ").append(fieldName).append(" in(").append(notInQueryString).append(")");
            }
            queryConditionNum++;
            return this;
        }

        public QueryBuilder addWhereNotIn(String fieldName, String notInQueryString) {
            if (queryConditionNum == 0) {
                queryString.append(" where ").append(fieldName).append(" not in(").append(notInQueryString).append(")");
            } else {
                queryString.append(" and ").append(fieldName).append(" not in(").append(notInQueryString).append(")");
            }
            queryConditionNum++;
            return this;
        }

        public QueryBuilder union(String unionQueryString) {
            queryString.append(" union ").append(unionQueryString);
            return this;
        }

        public String create() {
            System.out.println(queryString);
            return queryString.toString();
        }
    }

    public static class InsertBuilder {
        StringBuffer insertString = new StringBuffer();
        List<String> insertItems = new ArrayList<>();
        List<String> insertItemValues = new ArrayList<>();

        public InsertBuilder() {
            insertString.append("insert into ");
        }

        public InsertBuilder into(String tableName) {
            insertString.append(tableName).append("(");
            return this;
        }

        public InsertBuilder addItem(String itemName, String itemValue) {
            insertItems.add(itemName);
            insertItemValues.add(itemValue);
            return this;
        }

        public String create() {
            for (int i = 0; i < insertItems.size(); i++) {
                if (i == 0) {
                    insertString.append(insertItems.get(i));
                } else if (i < insertItems.size() - 1) {
                    insertString.append(",").append(insertItems.get(i));
                } else {
                    insertString.append(",").append(insertItems.get(i)).append(") ");
                }
            }
            for (int i = 0; i < insertItemValues.size(); i++) {
                if (i == 0) {
                    insertString.append("value('").append(insertItemValues.get(i)).append("'");
                } else if (i < insertItemValues.size() - 1) {
                    insertString.append(",'").append(insertItemValues.get(i)).append("'");
                } else {
                    insertString.append(",'").append(insertItemValues.get(i)).append("')");
                }
            }
            System.out.println(insertString);
            return insertString.toString();
        }
    }

    public static class UpdateBuilder {
        StringBuffer updateString = new StringBuffer();
        int updateItemNum;
        int updateConditionNum = 0;

        public UpdateBuilder() {
            updateItemNum = 0;
            updateString.append("update ");
        }

        public UpdateBuilder from(String tableName) {
            updateString.append(tableName).append(" ");
            return this;
        }

        public UpdateBuilder set(String itemName, String value) {
            if (updateItemNum == 0) {
                updateString.append("set ").append(itemName).append(" = '").append(value).append("'");
            } else {
                updateString.append(",").append(itemName).append(" = '").append(value).append("'");
            }
            updateItemNum++;
            return this;
        }

        public UpdateBuilder addWhereEqualTo(String fieldName, String value) {
            if (updateConditionNum == 0) {
                updateString.append(" where ").append(fieldName).append(" = '").append(value).append("'");
            } else {
                updateString.append(" and ").append(fieldName).append(" = '").append(value).append("'");
            }
            updateConditionNum++;
            return this;
        }

        public UpdateBuilder addWhereEqualTo(String fieldName, int value) {
            if (updateConditionNum == 0) {
                updateString.append(" where ").append(fieldName).append(" = ").append(value);
            } else {
                updateString.append(" and ").append(fieldName).append(" = ").append(value);
            }
            updateConditionNum++;
            return this;
        }

        public String create() {
            System.out.println(updateString);
            return updateString.toString();
        }
    }

    public static class DeleteBuilder{
        StringBuffer deleteString = new StringBuffer();
        int deleteConditionNum = 0;

        public DeleteBuilder(){
            deleteString.append("delete ");
        }

        public DeleteBuilder from(String tableName) {
            deleteString.append("from ").append(tableName).append(" ");
            return this;
        }

        public DeleteBuilder addWhereEqualTo(String fieldName, String value) {
            if (deleteConditionNum == 0) {
                deleteString.append(" where ").append(fieldName).append(" = '").append(value).append("'");
            } else {
                deleteString.append(" and ").append(fieldName).append(" = '").append(value).append("'");
            }
            deleteConditionNum++;
            return this;
        }

        public DeleteBuilder addWhereEqualTo(String fieldName, int value) {
            if (deleteConditionNum == 0) {
                deleteString.append(" where ").append(fieldName).append(" = ").append(value);
            } else {
                deleteString.append(" and ").append(fieldName).append(" = ").append(value);
            }
            deleteConditionNum++;
            return this;
        }

        public String create(){
            System.out.println(deleteString);
            return deleteString.toString();
        }
    }
}
