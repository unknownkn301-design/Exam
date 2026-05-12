package scoremanager.main;

import bean.Teacher;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        int no = Integer.parseInt(req.getParameter("no"));

        // teacherがnullの場合はエラー
        if (teacher == null) {
            req.setAttribute("error", "削除に失敗しました");
            req.getRequestDispatcher("TestList.action").forward(req, res);
            return;
        }

        TestDao testDao = new TestDao();
        testDao.delete(no, teacher.getSchool());

        // 削除完了後、完了画面へ
        req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
    }
}