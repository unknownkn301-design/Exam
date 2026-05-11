package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 成績参照アクション（学生番号検索 / 初期表示）
 * URL: TestList.action
 */
public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        StudentDao studentDao = new StudentDao();
        TestDao scoreDao = new TestDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        // リクエストパラメーターの取得 2
        String studentNo = req.getParameter("student_no");

        // DBからデータ取得 3
        // 科目フィルタ用ドロップダウンデータ
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // ビジネスロジック 4 - 学生番号検索
        List<Test> scores = null;
        Student selectedStudent = null;

        if (studentNo != null && !studentNo.trim().isEmpty()) {
            selectedStudent = studentDao.get(studentNo.trim());
            if (selectedStudent == null) {
                errors.put("student_no", "指定された学生が存在しません");
            } else {
                scores = scoreDao.filter(selectedStudent, teacher.getSchool());
            }
        }

        // レスポンス値をセット 6
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("selected_student", selectedStudent);
        req.setAttribute("scores", scores);
        req.setAttribute("student_no", studentNo);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
        }

        // JSPへフォワード 7
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
