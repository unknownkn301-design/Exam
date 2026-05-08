<%-- 科目別成績一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
    <c:param name="title">得点管理システム</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>

            <%-- 科目情報フィルタ --%>
            <form action="TestListSubjectExecute.action" method="get" class="px-4 mb-3">
                <div class="row border mx-0 mb-3 py-2 align-items-center rounded">
                    <div class="col-auto fw-semibold">科目情報</div>

                    <%-- 入学年度 --%>
                    <div class="col-auto">
                        <label class="form-label mb-0" for="f1">入学年度</label>
                        <select class="form-select form-select-sm" id="f1" name="f1">
                            <option value="0">----</option>
                            <c:forEach var="y" items="${ent_year_set}">
                                <option value="${y}" <c:if test="${y == f1}">selected</c:if>>${y}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- クラス --%>
                    <div class="col-auto">
                        <label class="form-label mb-0" for="f2">クラス</label>
                        <select class="form-select form-select-sm" id="f2" name="f2">
                            <option value="0">----</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 科目 --%>
                    <div class="col-auto">
                        <label class="form-label mb-0" for="f3">科目</label>
                        <select class="form-select form-select-sm" id="f3" name="f3">
                            <option value="0">----</option>
                            <c:forEach var="sub" items="${subject_set}">
                                <option value="${sub.subjectCd}" <c:if test="${sub.subjectCd == f3}">selected</c:if>>${sub.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 検索ボタン --%>
                    <div class="col-auto align-self-end">
                        <button type="submit" class="btn btn-secondary btn-sm" id="subject-search-btn">検索</button>
                    </div>
                </div>
            </form>

            <%-- エラーメッセージ --%>
            <c:if test="${not empty errorMsg}">
                <div class="px-4 mb-3 text-warning">${errorMsg}</div>
            </c:if>

            <%-- 検索結果テーブル --%>
            <c:if test="${not empty tests1}">
                <%-- ① 科目名ラベル --%>
                <div class="px-4 mb-1 fw-semibold">科目：${subject.subjectName}</div>

                <%-- ② 成績一覧テーブル --%>
                <table class="table table-hover mx-3" style="width:calc(100% - 2rem);">
                    <tr>
                        <%-- ③ ④ ⑤ ⑥ ⑦ ⑧ ヘッダ行 --%>
                        <th>入学年度</th>
                        <th>クラス</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>1回</th>
                        <th>2回</th>
                    </tr>

                    <%-- ⑨〜⑭ データ行：tests1 と tests2 を同じインデックスで表示 --%>
                    <c:forEach var="t1" items="${tests1}" varStatus="status">
                        <c:set var="t2" value="${tests2[status.index]}" />
                        <tr>
                            <td>${t1.student.entYear}</td>
                            <td>${t1.student.classNum}</td>
                            <td>${t1.student.studentNo}</td>
                            <td>${t1.student.studentName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${t1.registered}">${t1.point}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${t2.registered}">${t2.point}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

        </section>
    </c:param>
</c:import>
