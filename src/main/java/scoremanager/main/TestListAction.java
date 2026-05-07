package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Score;
import bean.Student;
import bean.Teacher;
import dao.ScoreDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 成績参照アクション
 */
public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        StudentDao studentDao = new StudentDao();
        ScoreDao scoreDao = new ScoreDao();
        Map<String, String> errors = new HashMap<>();

        // リクエストパラメーターの取得 2
        String studentNo = req.getParameter("student_no");

        // DBからデータ取得 3
        List<Student> students = studentDao.filter(teacher.getSchool(), true);

        // ビジネスロジック 4
        List<Score> scores = null;
        Student selectedStudent = null;

        if (studentNo != null && !studentNo.trim().isEmpty()) {
            selectedStudent = studentDao.get(studentNo);
            if (selectedStudent == null) {
                errors.put("student_no", "指定された学生が存在しません");
            } else {
                scores = scoreDao.filter(selectedStudent, teacher.getSchool());
            }
        }

        // レスポンス値をセット 6
        req.setAttribute("students", students);
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
