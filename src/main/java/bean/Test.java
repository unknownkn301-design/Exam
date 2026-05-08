package bean;

import java.io.Serializable;

/**
 * 成績情報を表す Bean クラス
 * テーブル: test (STUDENT_NO, SCHOOL_CD, SUBJECT_CD, NO, POINT, CLASS_NUM)
 */
public class Test implements Serializable {

    /** 回数（試験の第何回目か） */
    private int no;

    /** 学生 */
    private Student student;

    /** 科目 */
    private Subject subject;

    /** 得点 */
    private int point;

    /** 登録済みかどうか */
    private boolean registered;

    // --- getter / setter ---

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
