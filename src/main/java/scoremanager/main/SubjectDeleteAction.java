package scoremanager.main;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // パラメータ取得
        String subjectCd = req.getParameter("cd");

        // DAO
        SubjectDao subjectDao = new SubjectDao();

        // 科目取得
        Subject subject = subjectDao.get(subjectCd);

        // 存在しない場合
        if (subject == null) {
            req.setAttribute("error", "科目が存在しません");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        // JSP に渡す
        req.setAttribute("subject_cd", subject.getSubjectCd());
        req.setAttribute("subject_name", subject.getSubjectName());

        // 削除確認画面へ
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
