package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目別成績一覧 検索実行アクション (GRMR002)
 * URL: TestListSubjectExecute.action
 * パラメーター: f1=入学年度, f2=クラス番号, f3=科目コード
 *
 * <p>リファクタリング: TestDao の直接利用（tests1/tests2 の二重取得）を廃止し、
 * {@link TestListSubjectDao} + {@link bean.TestListSubject} を使用するよう変更。
 * 試験回数は TestListSubject.points に集約されるため、DB アクセスが1回に削減される。
 */
public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        // リクエストパラメーターの取得 2
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス番号
        String f3 = req.getParameter("f3"); // 科目コード

        // DBからデータ取得（ドロップダウン用） 3
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        // 入学年度リストを生成
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // レスポンスにフォーム選択値を保持 6
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);

        // ビジネスロジック 4 ─ バリデーション
        // f1 が null = パラメータ未送信（直接 URL アクセス / 初回表示）
        // f1 が "0" 等 = フォーム送信済みだが未選択
        boolean submitted = (f1 != null); // フォームが送信されたかどうか

        if (!submitted) {
            // 初回アクセス：エラーなしでフォームだけ表示
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        boolean f1Empty = (f1.trim().isEmpty() || f1.equals("0"));
        boolean f2Empty = (f2 == null || f2.trim().isEmpty() || f2.equals("0"));
        boolean f3Empty = (f3 == null || f3.trim().isEmpty() || f3.equals("0"));

        if (f1Empty || f2Empty || f3Empty) {
            // フォーム送信後に未選択項目がある場合
            req.setAttribute("errorMsg", "入学年度とクラスと科目を選択してください。");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(f1);

        // 科目情報を取得
        Subject subject = subjectDao.get(f3);

        // 科目別成績一覧を取得（TestListSubjectDao で1回のDB呼び出しにまとめる）
        List<TestListSubject> testListSubjects =
            testListSubjectDao.filter(entYear, f2, subject, teacher.getSchool());

        // 学生が存在しない場合
        if (testListSubjects.isEmpty()) {
            req.setAttribute("errorMsg", "学生情報が存在しませんでした。");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // レスポンス値をセット 6
        req.setAttribute("testListSubjects", testListSubjects);
        req.setAttribute("subject", subject);
        req.setAttribute("entYear", entYear);

        // JSPへフォワード 7
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
