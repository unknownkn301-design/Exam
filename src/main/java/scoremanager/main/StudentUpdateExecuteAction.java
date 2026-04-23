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
 * 学生情報変更実行アクション
 * フォームから送信された値でバリデーションを行い、
 * 問題なければ StudentDao#save() で UPDATE を実行する。
 * エラー時は変更フォームへ、成功時は完了画面へフォワードする。
 */
public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        Map<String, String> errors = new HashMap<>();
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        // リクエストパラメーターの取得 2
        String studentNo   = req.getParameter("no");
        String studentName = req.getParameter("name");
        String classNum    = req.getParameter("class_num");
        String entYearStr  = req.getParameter("ent_year");
        String isAttendStr = req.getParameter("is_attend");

        // DBからデータ取得 3
        Student student = studentDao.get(studentNo);

        // ビジネスロジック 4 ― バリデーション
        if (student == null) {
            // 対象学生が見つからない場合（不正リクエスト等）
            errors.put("notFound", "指定された学生が存在しません。");
        }

        int entYear = 0;
        if (entYearStr == null || entYearStr.isEmpty() || entYearStr.equals("0")) {
            errors.put("ent_year", "入学年度を選択してください。");
        } else {
            entYear = Integer.parseInt(entYearStr);
        }

        if (studentName == null || studentName.trim().isEmpty()) {
            errors.put("name", "氏名を入力してください。");
        } else if (studentName.length() > 30) {
            errors.put("name", "氏名は30文字以内で入力してください。");
        }

        if (classNum == null || classNum.trim().isEmpty()) {
            errors.put("class_num", "クラスを選択してください。");
        }

        // エラーがある場合は変更フォームへ戻す
        if (!errors.isEmpty()) {
            // 入力値を保持したまま画面を再表示するため student に入力値を反映
            if (student != null) {
                student.setStudentName(studentName);
                student.setEntYear(entYear);
                student.setClassNum(classNum);
                student.setAttend(isAttendStr != null);
            }

            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i < year + 11; i++) {
                entYearSet.add(i);
            }
            List<String> classNumList = classNumDao.filter(teacher.getSchool());

            req.setAttribute("errors", errors);
            req.setAttribute("student", student);
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearSet);
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        // 5 ― DB更新
        student.setStudentName(studentName.trim());
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttendStr != null);  // チェックボックスが送信されていれば在学中

        studentDao.save(student);

        // レスポンス値をセット 6
        req.setAttribute("student", student);

        // JSPへフォワード 7
        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}
