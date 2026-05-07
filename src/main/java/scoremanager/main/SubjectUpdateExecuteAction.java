package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目情報変更実行アクション
 * URL: SubjectUpdateExecute.action (POST)
 * フォームから送信された科目名でバリデーションを行い、
 * 問題なければ SubjectDao#save() で UPDATE を実行する。
 * エラー時は変更フォームへ、成功時は完了画面へフォワードする。
 */
public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao subjectDao = new SubjectDao();

        // リクエストパラメーターの取得 2
        String cd   = req.getParameter("cd");
        String name = req.getParameter("name");

        // DBからデータ取得 3
        Subject subject = subjectDao.get(cd);

        // レスポンスに入力値を常にセット（エラー時の再表示に使う）
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);

        // ビジネスロジック 4 ― 対象科目の存在チェック
        if (subject == null) {
            // 変更中に別画面から削除された場合
            req.setAttribute("errorMsg", "科目が存在していません");
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // バリデーション ― 科目名は必須・最大20文字
        if (name == null || name.trim().isEmpty()) {
            // required属性でブラウザ側でも弾くが、サーバー側でも確認する
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 5 ― DB更新
        subject.setSubjectName(name.trim());
        subjectDao.save(subject);

        // レスポンス値をセット 6
        req.setAttribute("subject", subject);

        // JSPへフォワード 7
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}
