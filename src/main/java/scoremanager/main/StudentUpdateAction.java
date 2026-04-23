package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

/**
 * 学生情報変更フォーム表示アクション
 * URLパラメーター "no" に学生番号を受け取り、
 * 該当学生の現在情報をフォームに表示する。
 */
public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        ClassNumDao classNumDao = new ClassNumDao();
        StudentDao studentDao = new StudentDao();
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        Map<String, String> errors = new HashMap<>();

        // リクエストパラメーターの取得 2
        String studentNo = req.getParameter("no");

        // DBからデータ取得 3
        Student student = studentDao.get(studentNo);
        List<String> classNumList = classNumDao.filter(teacher.getSchool());

        // ビジネスロジック 4
        // 学生が存在しない場合はエラー
        if (student == null) {
            errors.put("notFound", "指定された学生が存在しません。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("StudentList.action").forward(req, res);
            return;
        }

        // 入学年度の選択肢を生成（10年前〜10年後）
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 11; i++) {
            entYearSet.add(i);
        }

        // レスポンス値をセット 6
        req.setAttribute("student", student);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("ent_year_set", entYearSet);

        // JSPへフォワード 7
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
