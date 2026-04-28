package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目一覧表示アクション (画面項目②〜⑨)
 * URL: SubjectList.action
 */
public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao subjectDao = new SubjectDao();

        // DBからデータ取得 3
        // ログインユーザーの学校コードに紐づく科目一覧を取得
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // レスポンス値をセット 6
        req.setAttribute("subjects", subjects);

        // JSPへフォワード 7
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
