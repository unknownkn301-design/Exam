package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

/**
 * 科目情報 DAO クラス
 */
public class SubjectDao extends Dao {

    /**
     * 科目コードで1件取得
     *
     * @param subjectCd 科目コード
     * @return Subject インスタンス（存在しない場合は null）
     * @throws Exception
     */
    public Subject get(String subjectCd) throws Exception {

        Subject subject = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM subject WHERE subject_cd = ?");
            statement.setString(1, subjectCd);
            ResultSet resultSet = statement.executeQuery();

            SchoolDao schoolDao = new SchoolDao();

            if (resultSet.next()) {
                subject = new Subject();
                subject.setSubjectCd(resultSet.getString("subject_cd"));
                subject.setSubjectName(resultSet.getString("subject_name"));
                subject.setSchool(schoolDao.get(resultSet.getString("school_cd")));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return subject;
    }

    /**
     * ログインユーザーの学校コードに紐づく科目一覧を取得
     *
     * @param school 学校インスタンス
     * @return Subject のリスト
     * @throws Exception
     */
    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM subject WHERE school_cd = ? ORDER BY subject_cd ASC");
            statement.setString(1, school.getSchoolCd());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setSubjectCd(resultSet.getString("subject_cd"));
                subject.setSubjectName(resultSet.getString("subject_name"));
                subject.setSchool(school);
                list.add(subject);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return list;
    }

    /**
     * 科目情報を登録または更新する（INSERT / UPDATE）
     *
     * @param subject 科目インスタンス
     * @return 成功した場合 true
     * @throws Exception
     */
    public boolean save(Subject subject) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Subject old = get(subject.getSubjectCd());

            if (old == null) {
                // 新規登録
                statement = connection.prepareStatement(
                        "INSERT INTO subject(subject_cd, subject_name, school_cd) VALUES(?, ?, ?)");
                statement.setString(1, subject.getSubjectCd());
                statement.setString(2, subject.getSubjectName());
                statement.setString(3, subject.getSchool().getSchoolCd());
            } else {
                // 更新
                statement = connection.prepareStatement(
                        "UPDATE subject SET subject_name = ? WHERE subject_cd = ? AND school_cd = ?");
                statement.setString(1, subject.getSubjectName());
                statement.setString(2, subject.getSubjectCd());
                statement.setString(3, subject.getSchool().getSchoolCd());
            }

            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return count > 0;
    }

    /**
     * 科目を削除する
     *
     * @param subjectCd 科目コード
     * @param school    学校インスタンス（不正削除防止）
     * @return 成功した場合 true
     * @throws Exception
     */
    public boolean delete(String subjectCd, School school) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                    "DELETE FROM subject WHERE subject_cd = ? AND school_cd = ?");
            statement.setString(1, subjectCd);
            statement.setString(2, school.getSchoolCd());
            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return count > 0;
    }
}
