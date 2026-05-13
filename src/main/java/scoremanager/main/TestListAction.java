package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 成績参照アクション（学生番号検索 / 初期表示）
 * URL: TestList.action
 *
 * <p>リファクタリング: TestDao の直接利用を廃止し、
 * {@link TestListStudentDao} + {@link bean.TestListStudent} を使用するよう変更。
 */
public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        StudentDao studentDao = new StudentDao();
        TestListStudentDao testListStudentDao = new TestListStudentDao();
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
        List<TestListStudent> scores = null;
        Student selectedStudent = null;

        if (studentNo != null && !studentNo.trim().isEmpty()) {
            selectedStudent = studentDao.get(studentNo.trim());
            if (selectedStudent == null) {
                errors.put("student_no", "指定された学生が存在しません");
            } else {
                // TestListStudentDao を使って学生別成績を取得
                scores = testListStudentDao.filter(selectedStudent, teacher.getSchool());
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
