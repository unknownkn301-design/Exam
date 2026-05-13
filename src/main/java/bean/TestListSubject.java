package bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 科目別成績一覧表示用 Bean クラス (GRMR002)
 * 科目1つに対して、ある生徒の入学年度・学籍番号・氏名・クラスと、
 * 試験回数をキー、得点を値とした Map を保持する。
 */
public class TestListSubject implements Serializable {

    /** 入学年度 */
    private int entYear;

    /** 学籍番号 */
    private String studentNo;

    /** 氏名 */
    private String studentName;

    /** クラス番号 */
    private String classNum;

    /**
     * 試験回数ごとの得点マップ。
     * key   : 試験回数 (1, 2, ...)
     * value : 得点。未登録の場合は null。
     */
    private Map<Integer, Integer> points = new LinkedHashMap<>();

    // --- getter / setter ---

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    /**
     * 指定した試験回数の得点を取得する。未登録の場合は null を返す。
     *
     * @param key 試験回数
     * @return 得点、または null
     */
    public Integer getPoint(int key) {
        return points.get(key);
    }

    /**
     * 指定した試験回数の得点を登録する。
     *
     * @param key   試験回数
     * @param value 得点
     */
    public void putPoint(int key, int value) {
        points.put(key, value);
    }
}
