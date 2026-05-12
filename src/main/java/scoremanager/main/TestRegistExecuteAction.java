package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
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
 * 成績登録実行アクション
 * 基本フロー⑤⑥⑦ / 代替フロー②
 */
public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        TestDao scoreDao = new TestDao();
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao classNumDao = new ClassNumDao();

        // リクエストパラメーターの取得 2
        String entYearStr = req.getParameter("ent_year");
        String classNum   = req.getParameter("class_num");
        String subjectCd  = req.getParameter("subject_cd");
        String noStr      = req.getParameter("no");

        int entYear = Integer.parseInt(entYearStr);
        int no      = Integer.parseInt(noStr);
        Subject subject = subjectDao.get(subjectCd);

        // 対象学生一覧を取得（成績リストはScoreDaoから）
        List<Test> scores = scoreDao.filter(school, entYear, classNum, subject, no);

        // バリデーション 4：各学生の点数を個別にチェック（代替フロー②）
        // エラーはstudentNo→エラーメッセージのMapで管理
        Map<String, String> pointErrors = new HashMap<>();

        for (Test score : scores) {
            String studentNo = score.getStudent().getStudentNo();
            String pointStr  = req.getParameter("point_" + studentNo);

            // 未入力もエラーにする
            if (pointStr == null || pointStr.trim().isEmpty()) {
                pointErrors.put(studentNo, "0〜100の数字を入力してください");
                continue;
            }

            try {
                int point = Integer.parseInt(pointStr.trim());
                if (point < 0 || point > 100) {
                    pointErrors.put(studentNo, "0〜100の範囲で入力してください");
                }
            } catch (NumberFormatException e) {
                pointErrors.put(studentNo, "0〜100の範囲で入力してください");
            }
        }


        if (!pointErrors.isEmpty()) {
            // 代替フロー②-1：エラーがある入力欄の横にメッセージを表示
            // 入力値を保持したままJSPに戻す（代替フロー②-2：基本フロー⑤へ）

            // 入力値をscoresに上書き（入力値を保持して再表示）
            for (Test score : scores) {
                String studentNo = score.getStudent().getStudentNo();
                String pointStr  = req.getParameter("point_" + studentNo);
                if (pointStr != null && !pointStr.trim().isEmpty()) {
                    try {
                        score.setPoint(Integer.parseInt(pointStr.trim()));
                        score.setRegistered(true); // 値があるので表示する
                    } catch (NumberFormatException e) {
                        // パース失敗の場合はそのまま
                    }
                }
            }

            // 入学年度の選択肢を再生成
            int year = java.time.LocalDate.now().getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i <= year; i++) {
                entYearSet.add(i);
            }

            req.setAttribute("scores", scores);
            req.setAttribute("subject", subject);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("class_num_set", classNumDao.filter(school));
            req.setAttribute("subjects", subjectDao.filter(school));
            req.setAttribute("ent_year", entYearStr);
            req.setAttribute("class_num", classNum);
            req.setAttribute("subject_cd", subjectCd);
            req.setAttribute("no", noStr);
            req.setAttribute("point_errors", pointErrors); // 各入力欄のエラーMap
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // DBへ登録 5：エラーがなければ全学生の点数を一括保存（基本フロー⑦）
        for (Test score : scores) {
            String studentNo = score.getStudent().getStudentNo();
            String pointStr  = req.getParameter("point_" + studentNo);

            if (pointStr == null || pointStr.trim().isEmpty()) {
                continue; // 未入力はスキップ
            }

            int point = Integer.parseInt(pointStr.trim());
            score.setPoint(point);
            scoreDao.save(score);
        }

        // 完了画面へフォワード 7
        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject", subject);
        req.setAttribute("no", no);
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}
