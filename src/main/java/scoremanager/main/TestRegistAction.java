package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.TestDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 成績登録画面表示アクション
 * 基本フロー②③④ / 代替フロー①
 */
public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao scoreDao = new TestDao();

        // 入学年度の選択肢を生成（10年前〜今年）
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // DBからデータ取得 3
        List<String> classNumSet = classNumDao.filter(school);
        List<Subject> subjects = subjectDao.filter(school);

        // リクエストパラメーターの取得 2
        String entYearStr = req.getParameter("ent_year");
        String classNum   = req.getParameter("class_num");
        String subjectCd  = req.getParameter("subject_cd");
        String noStr      = req.getParameter("no");

        // 検索ボタンが押されたかどうか（ent_yearパラメーターの存在で判定）
        boolean searched = entYearStr != null;

        // レスポンス値をセット 6
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjects", subjects);
        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("no", noStr);

        if (!searched) {
            // 初期表示：検索フォームのみ表示
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // ビジネスロジック 4：検索条件が全て揃っているか確認
        boolean allSelected = !entYearStr.isEmpty()
                           && classNum != null && !classNum.isEmpty()
                           && subjectCd != null && !subjectCd.isEmpty()
                           && noStr != null && !noStr.isEmpty();

        if (!allSelected) {
            // 代替フロー①-1：未選択エラー
            req.setAttribute("error_search", "入学年度とクラスと科目と回数を選択してください");
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // 全条件が揃っている場合：学生一覧と入力欄を表示（基本フロー④）
        int entYear = Integer.parseInt(entYearStr);
        int no      = Integer.parseInt(noStr);
        Subject subject = subjectDao.get(subjectCd);

        // scoresは必ずセット（空リストでもJSPで入力エリアを表示するため）
        List<Test> scores = new ArrayList<>();
        if (subject != null) {
            scores = scoreDao.filter(school, entYear, classNum, subject, no);
        }
        req.setAttribute("scores", scores);
        req.setAttribute("subject", subject);

        // JSPへフォワード 7
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
