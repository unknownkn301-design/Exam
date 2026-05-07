package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目情報変更フォーム表示アクション
 * URL: SubjectUpdate.action?cd=XXX
 * 科目一覧画面から科目コードを受け取り、変更フォームを表示する。
 */
public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao subjectDao = new SubjectDao();

        // リクエストパラメーターの取得 2
        String cd = req.getParameter("cd");

        // DBからデータ取得 3
        Subject subject = subjectDao.get(cd);

        // ビジネスロジック 4
        // 対象科目が存在しない場合はエラーメッセージをセット
        if (subject == null) {
            req.setAttribute("errorMsg", "科目が存在していません");
        }

        // レスポンス値をセット 6
        // cdは常にセット（科目が削除済みでもコードは表示する）
        req.setAttribute("cd", cd);
        if (subject != null) {
            req.setAttribute("name", subject.getSubjectName());
        }

        // JSPへフォワード 7
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
