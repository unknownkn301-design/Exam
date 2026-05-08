package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
 
public class TestDao extends Dao {
 
    /**
     * 学生・科目・回数で成績を1件取得する
     */
    public Test get(Student student, Subject subject, int no) throws Exception {
 
        Test score = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
 
        try {
            statement = connection.prepareStatement(
                "select * from test where student_no = ? and subject_cd = ? and no = ? and school_cd = ?");
            statement.setString(1, student.getStudentNo());
            statement.setString(2, subject.getSubjectCd());
            statement.setInt(3, no);
            statement.setString(4, student.getSchool().getSchoolCd());
            ResultSet rSet = statement.executeQuery();
 
            if (rSet.next()) {
                score = new Test();
                score.setStudent(student);
                score.setSubject(subject);
                score.setPoint(rSet.getInt("point"));
                score.setNo(rSet.getInt("no"));
                score.setRegistered(true);
            }
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
 
        return score;
    }
 
    /**
     * 入学年度・クラス・科目・回数で学生一覧と成績をまとめて取得する
     * 未登録の学生も含めてLEFT JOINで返す
     */
    public List<Test> filter(School school, int entYear, String classNum, Subject subject, int no) throws Exception {
 
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
 
        try {
            statement = connection.prepareStatement(
                "select s.student_no, s.student_name, s.class_num, " +
                "t.point, t.no " +
                "from student s " +
                "left join test t " +
                "  on s.student_no = t.student_no " +
                "  and t.subject_cd = ? " +
                "  and t.no = ? " +
                "  and t.school_cd = ? " +
                "where s.school_cd = ? " +
                "  and s.ent_year = ? " +
                "  and s.class_num = ? " +
                "  and s.is_attend = true " +
                "order by s.student_no asc");
            statement.setString(1, subject.getSubjectCd());
            statement.setInt(2, no);
            statement.setString(3, school.getSchoolCd());
            statement.setString(4, school.getSchoolCd());
            statement.setInt(5, entYear);
            statement.setString(6, classNum);
 
            ResultSet rSet = statement.executeQuery();
 
            while (rSet.next()) {
                Test score = new Test();
 
                Student student = new Student();
                student.setStudentNo(rSet.getString("student_no"));
                student.setStudentName(rSet.getString("student_name"));
                student.setClassNum(rSet.getString("class_num"));
                student.setSchool(school);
 
                score.setStudent(student);
                score.setSubject(subject);
                score.setNo(no);
 
                if (rSet.getObject("point") != null) {
                    score.setPoint(rSet.getInt("point"));
                    score.setRegistered(true);
                } else {
                    score.setPoint(0);
                    score.setRegistered(false);
                }
 
                list.add(score);
            }
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
 
    /**
     * 成績を1件登録・更新する
     */
    public boolean save(Test score) throws Exception {
 
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
 
        try {
            Test old = get(score.getStudent(), score.getSubject(), score.getNo());
 
            if (old == null) {
                statement = connection.prepareStatement(
                    "insert into test(student_no, school_cd, subject_cd, no, point, class_num) " +
                    "values(?, ?, ?, ?, ?, ?)");
                statement.setString(1, score.getStudent().getStudentNo());
                statement.setString(2, score.getStudent().getSchool().getSchoolCd());
                statement.setString(3, score.getSubject().getSubjectCd());
                statement.setInt(4, score.getNo());
                statement.setInt(5, score.getPoint());
                statement.setString(6, score.getStudent().getClassNum());
            } else {
                statement = connection.prepareStatement(
                    "update test set point = ? " +
                    "where student_no = ? and subject_cd = ? and no = ? and school_cd = ?");
                statement.setInt(1, score.getPoint());
                statement.setString(2, score.getStudent().getStudentNo());
                statement.setString(3, score.getSubject().getSubjectCd());
                statement.setInt(4, score.getNo());
                statement.setString(5, score.getStudent().getSchool().getSchoolCd());
            }
 
            count = statement.executeUpdate();
 
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
 
        return count > 0;
    }
    public List<Test> filter(Student student, School school) throws Exception {
 
        List<Test> list = new ArrayList<>();
 
        Connection connection = getConnection();
 
        PreparedStatement statement = connection.prepareStatement(
        	    "select t.*, s.subject_name as subject_name " +
        	    "from test t " +
        	    "join subject s " +
        	    "on t.subject_cd = s.subject_cd " +
        	    "where t.student_no = ? and t.school_cd = ? " +
        	    "order by t.no asc"
        	);
 
        statement.setString(1, student.getStudentNo());
        statement.setString(2, school.getSchoolCd());
 
        ResultSet rSet = statement.executeQuery();
 
        while (rSet.next()) {
 
            Test score = new Test();
 
            score.setStudent(student);
            score.setNo(rSet.getInt("no"));
            score.setPoint(rSet.getInt("point"));
 
            Subject subject = new Subject();
            subject.setSubjectCd(rSet.getString("subject_cd"));
            subject.setSubjectName(rSet.getString("subject_name"));
 
            score.setSubject(subject);
 
            list.add(score);
        }
 
        statement.close();
        connection.close();
 
        return list;
    }
}