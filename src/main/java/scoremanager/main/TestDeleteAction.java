package scoremanager.main;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // パラメータ取得
        int no = Integer.parseInt(req.getParameter("no"));

        // DAO
        TestDao testDao = new TestDao();

        // 成績取得（noで検索）
        Test test = testDao.get(no);

        // 存在しない場合
        if (test == null) {
            req.setAttribute("error", "成績が存在しません");
            req.getRequestDispatcher("TestList.action").forward(req, res);
            return;
        }

        // JSP に渡す
        req.setAttribute("test", test);

        // 削除確認画面へ
        req.getRequestDispatcher("test_delete.jsp").forward(req, res);
    }
}