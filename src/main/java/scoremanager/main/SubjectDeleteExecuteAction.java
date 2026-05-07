package scoremanager.main;

import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectCd = req.getParameter("subject_cd");

        // teacherまたはsubjectCdがnullの場合はエラーにする
        if (subjectCd == null || teacher == null) {
            req.setAttribute("error", "削除に失敗しました");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        SubjectDao subjectDao = new SubjectDao();
        subjectDao.delete(subjectCd, teacher.getSchool());

        // 削除完了後、完了画面へ
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}