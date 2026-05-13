<%-- 成績参照 統合JSP (GRMR001/002/003) --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>

            <%-- ===== 科目情報フィルタ ===== --%>
            <form action="TestListSubjectExecute.action" method="get" class="px-4 mb-2">
                <div class="row border mx-0 mb-2 py-2 align-items-end rounded">
                    <div class="col-auto fw-semibold align-self-center">科目情報</div>

                    <div class="col-auto">
                        <label class="form-label mb-0" for="f1">入学年度</label>
                        <select class="form-select form-select-sm" id="f1" name="f1">
                            <option value="0">----</option>
                            <c:forEach var="y" items="${ent_year_set}">
                                <option value="${y}" <c:if test="${y == f1}">selected</c:if>>${y}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-auto">
                        <label class="form-label mb-0" for="f2">クラス</label>
                        <select class="form-select form-select-sm" id="f2" name="f2">
                            <option value="0">----</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-auto">
                        <label class="form-label mb-0" for="f3">科目</label>
                        <select class="form-select form-select-sm" id="f3" name="f3">
                            <option value="0">----</option>
                            <c:forEach var="sub" items="${subject_set}">
                                <option value="${sub.subjectCd}" <c:if test="${sub.subjectCd == f3}">selected</c:if>>${sub.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary btn-sm" id="subject-search-btn">検索</button>
                    </div>
                </div>
            </form>

            <%-- ===== 学生情報フィルタ ===== --%>
            <form action="TestList.action" method="get" class="px-4 mb-3">
                <div class="row border mx-0 mb-2 py-2 align-items-end rounded">
                    <div class="col-auto fw-semibold align-self-center">学生情報</div>

                    <div class="col-auto">
                        <label class="form-label mb-0" for="student_no">学生番号</label>
                        <input class="form-control form-control-sm" type="text"
                               id="student_no" name="student_no"
                               value="${student_no}"
                               placeholder="学生番号を入力してください" />
                    </div>

                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary btn-sm" id="student-search-btn">検索</button>
                    </div>
                </div>
                <c:if test="${not empty errors.get('student_no')}">
                    <div class="text-warning px-2">${errors.get('student_no')}</div>
                </c:if>
            </form>

            <%-- ===== エラーメッセージ（科目検索） ===== --%>
            <c:if test="${not empty errorMsg}">
                <div class="px-4 mb-3 text-warning">${errorMsg}</div>
            </c:if>

            <%-- ===== 科目別成績テーブル (GRMR002) ===== --%>
            <c:if test="${not empty testListSubjects}">
                <div class="px-4 mb-1 fw-semibold">科目：${subject.subjectName}</div>
                <table class="table table-hover mx-3" style="width:calc(100% - 2rem);">
                    <tr>
                        <th>入学年度</th>
                        <th>クラス</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>1回</th>
                        <th>2回</th>
                    </tr>
                    <c:forEach var="row" items="${testListSubjects}">
                        <tr>
                            <td>${row.entYear}</td>
                            <td>${row.classNum}</td>
                            <td>${row.studentNo}</td>
                            <td>${row.studentName}</td>
                            <td><c:choose>
                                <c:when test="${not empty row.points[1]}">${row.points[1]}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose></td>
                            <td><c:choose>
                                <c:when test="${not empty row.points[2]}">${row.points[2]}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <%-- ===== 学生別成績テーブル (GRMR003) ===== --%>
            <c:if test="${selected_student != null}">
                <div class="px-4">
                    <h5>${selected_student.studentName}（${selected_student.studentNo}）の成績</h5>
                    <c:choose>
                        <c:when test="${scores != null && scores.size() > 0}">
                            <table class="table table-hover mt-2">
                                <tr>
                                    <th>科目コード</th>
                                    <th>科目名</th>
                                    <th class="text-end">回数</th>
                                    <th class="text-end">得点</th>
                                    <th></th>
                                </tr>
                                <c:forEach var="score" items="${scores}">
                                    <tr>
                                        <td>${score.subjectCd}</td>
                                        <td>${score.subjectName}</td>
                                        <td class="text-end">${score.num}回</td>
                                        <td class="text-end">${score.point} 点</td>
                                        <td><a href="TestRegist.action?student_no=${selected_student.studentNo}&subject_cd=${score.subjectCd}">修正</a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted">成績が登録されていません。</p>
                        </c:otherwise>
                    </c:choose>
                    <a href="TestRegist.action" class="btn btn-secondary btn-sm mt-2">成績を登録する</a>
                </div>
            </c:if>

        </section>
    </c:param>
</c:import>
