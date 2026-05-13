package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

/**
 * 科目別成績一覧取得 DAO (GRMR002)
 *
 * <p>入学年度・クラス・科目・学校を指定して、対象クラス全員の成績を
 * {@link TestListSubject} リストとして返す。
 * 試験回数（1回・2回）は、生徒ごとに {@code points} Map へまとめてセットする。
 */
public class TestListSubjectDao extends Dao {

    /**
     * 基底 SQL。
     * LEFT JOIN で未登録学生も含め、試験回数単位で1行ずつ取得する。
     */
    private String baseSql =
        "SELECT s.student_no, s.student_name, s.class_num, s.ent_year, " +
        "       t.point, t.no " +
        "FROM student s " +
        "LEFT JOIN test t " +
        "  ON s.student_no = t.student_no " +
        "  AND t.subject_cd = ? " +
        "  AND t.school_cd = ? " +
        "WHERE s.school_cd = ? " +
        "  AND s.ent_year = ? " +
        "  AND s.class_num = ? " +
        "  AND s.is_attend = true " +
        "ORDER BY s.student_no ASC, t.no ASC";

    /**
     * ResultSet を {@link TestListSubject} リストに変換する。
     * 同一学生の複数回分を1つの Bean にまとめる。
     *
     * @param rSet 実行済み ResultSet
     * @return TestListSubject のリスト
     * @throws Exception SQL 例外など
     */
    protected List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        Map<String, TestListSubject> map = new LinkedHashMap<>();

        while (rSet.next()) {
            String studentNo = rSet.getString("student_no");

            // 初出の学生は Bean を生成してマップへ登録
            if (!map.containsKey(studentNo)) {
                TestListSubject item = new TestListSubject();
                item.setStudentNo(studentNo);
                item.setStudentName(rSet.getString("student_name"));
                item.setClassNum(rSet.getString("class_num"));
                item.setEntYear(rSet.getInt("ent_year"));
                map.put(studentNo, item);
            }

            // 試験回数と得点をマップへ追加（no が null = 未登録の場合はスキップ）
            int no = rSet.getInt("no");
            if (!rSet.wasNull()) {
                Object pointObj = rSet.getObject("point");
                if (pointObj != null) {
                    map.get(studentNo).putPoint(no, rSet.getInt("point"));
                }
            }
        }

        list.addAll(map.values());
        return list;
    }

    /**
     * 入学年度・クラス・科目・学校を指定して全学生の成績を取得する。
     *
     * @param entYear  入学年度
     * @param classNum クラス番号
     * @param subject  対象科目
     * @param school   所属学校
     * @return TestListSubject のリスト（学生なしの場合は空リスト）
     * @throws Exception SQL 例外など
     */
    public List<TestListSubject> filter(int entYear, String classNum,
                                        Subject subject, School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(baseSql);
            statement.setString(1, subject.getSubjectCd());
            statement.setString(2, school.getSchoolCd());
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, entYear);
            statement.setString(5, classNum);

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
