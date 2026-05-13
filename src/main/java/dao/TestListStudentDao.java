package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.TestListStudent;

/**
 * 学生別成績一覧取得 DAO (GRMR003)
 *
 * <p>ある学生の全科目・全回数の成績を {@link TestListStudent} のリストとして返す。
 * SQL は {@link #baseSql} を基底とし、{@link #filter(Student, School)} が
 * バインドパラメーターをセットして実行し、{@link #postFilter(ResultSet)} で
 * Bean に変換する。
 */
public class TestListStudentDao extends Dao {

    /**
     * 基底 SQL。
     * 学生番号と学校コードで絞り込み、科目名・回数・得点を取得する。
     */
    private String baseSql =
        "SELECT t.subject_cd, s.subject_name, t.no, t.point " +
        "FROM test t " +
        "JOIN subject s ON t.subject_cd = s.subject_cd " +
        "WHERE t.student_no = ? AND t.school_cd = ? " +
        "ORDER BY t.subject_cd ASC, t.no ASC";

    /**
     * ResultSet を {@link TestListStudent} リストに変換する。
     *
     * @param rSet 実行済み ResultSet
     * @return TestListStudent のリスト
     * @throws Exception SQL 例外など
     */
    protected List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        while (rSet.next()) {
            TestListStudent item = new TestListStudent();
            item.setSubjectCd(rSet.getString("subject_cd"));
            item.setSubjectName(rSet.getString("subject_name"));
            item.setNum(rSet.getInt("no"));
            item.setPoint(rSet.getInt("point"));
            list.add(item);
        }
        return list;
    }

    /**
     * 指定した学生の全成績を取得する。
     *
     * @param student 対象学生
     * @param school  所属学校
     * @return TestListStudent のリスト（成績なしの場合は空リスト）
     * @throws Exception SQL 例外など
     */
    public List<TestListStudent> filter(Student student, School school) throws Exception {

        List<TestListStudent> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(baseSql);
            statement.setString(1, student.getStudentNo());
            statement.setString(2, school.getSchoolCd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet);

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return list;
    }
}
