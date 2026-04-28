package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao subjectDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        // パラメータ取得
        String subjectCd   = req.getParameter("subject_cd");
        String subjectName = req.getParameter("subject_name");

        // 科目コードの重複チェック用
        Subject existing = null;
        if (subjectCd != null && !subjectCd.trim().isEmpty()) {
            existing = subjectDao.get(subjectCd.trim());
        }

        // バリデーション
        if (subjectCd == null || subjectCd.trim().isEmpty()) {
            errors.put("1", "科目コードを入力してください");
        } else if (existing != null &&
                existing.getSchool().getSchoolCd()
                        .equals(teacher.getSchool().getSchoolCd())) {
            errors.put("1", "その科目コードはすでに登録されています");
        }

        if (subjectName == null || subjectName.trim().isEmpty()) {
            errors.put("2", "科目名を入力してください");
        }

        // 入力値を戻す
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("subject_name", subjectName);

        if (errors.isEmpty()) {
            Subject subject = new Subject();
            subject.setSubjectCd(subjectCd.trim());
            subject.setSubjectName(subjectName.trim());
            subject.setSchool(teacher.getSchool());

            subjectDao.save(subject);

            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
        } else {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        }
    }
}