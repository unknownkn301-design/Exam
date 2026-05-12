<%-- 成績登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <%-- 検索フォーム（基本フロー③） --%>
            <form method="get" action="TestRegist.action" class="border rounded mx-3 mb-3 p-3">
                <div class="row g-2 align-items-end">
                    <div class="col-3">
                        <label class="form-label">入学年度</label>
                        <select class="form-select" name="ent_year">
                            <option value="">--------</option>
                            <c:forEach var="y" items="${ent_year_set}">
                                <option value="${y}" <c:if test="${y == ent_year}">selected</c:if>>${y}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="class_num">
                            <option value="">--------</option>
                            <c:forEach var="cn" items="${class_num_set}">
                                <option value="${cn}" <c:if test="${cn == class_num}">selected</c:if>>${cn}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-3">
                        <label class="form-label">科目</label>
                        <select class="form-select" name="subject_cd">
                            <option value="">--------</option>
                            <c:forEach var="sub" items="${subjects}">
                                <option value="${sub.subjectCd}" <c:if test="${sub.subjectCd == subject_cd}">selected</c:if>>${sub.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label">回数</label>
                        <select class="form-select" name="no">
                            <option value="">--------</option>
                            <c:forEach begin="1" end="10" var="n">
                                <option value="${n}" <c:if test="${n == no}">selected</c:if>>${n}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <button type="submit" class="btn btn-secondary w-100">検索</button>
                    </div>
                </div>
                <%-- 代替フロー①-1：未選択エラー --%>
                <c:if test="${not empty error_search}">
                    <div class="mt-2 text-warning">${error_search}</div>
                </c:if>
            </form>

            <%-- scores が null でなければ入力エリアを表示（基本フロー④⑤） --%>
            <c:if test="${scores != null}">

                <%-- 科目名（回数）の表示 --%>
                <div class="mx-3 mb-2">
                    科目：${subject.subjectName}（${no}回）
                </div>

                <c:choose>
                    <c:when test="${scores.size() > 0}">
                        <form method="post" action="TestRegistExecute.action">
                            <input type="hidden" name="ent_year"   value="${ent_year}">
                            <input type="hidden" name="class_num"  value="${class_num}">
                            <input type="hidden" name="subject_cd" value="${subject_cd}">
                            <input type="hidden" name="no"         value="${no}">

                            <div class="mx-3">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>入学年度</th>
                                            <th>クラス</th>
                                            <th>学生番号</th>
                                            <th>氏名</th>
                                            <th>点数</th>
                                            <th></th> 
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="score" items="${scores}">
                                            <tr>
                                                <td>${ent_year}</td>
                                                <td>${score.student.classNum}</td>
                                                <td>${score.student.studentNo}</td>
                                                <td>${score.student.studentName}</td>
                                                <td>
                                                    <input type="number"
                                                        class="form-control form-control-sm"
                                                        style="width: 110px;"
                                                        name="point_${score.student.studentNo}"
                                                        min="0" max="100"
                                                        value="${score.registered ? score.point : ''}">
                                                    <%-- 代替フロー②-1：入力欄の下にエラーメッセージ --%>
                                                    <c:if test="${not empty point_errors[score.student.studentNo]}">
                                                        <div class="text-warning small mt-1">
                                                            ${point_errors[score.student.studentNo]}
                                                        </div>
                                                    </c:if>
                                                </td>
                                                 <td>
									                <c:if test="${score.registered}">
									                    <a href="TestDelete.action?no=${score.no}"
									                       class="btn btn-danger btn-sm">削除</a>
									                </c:if>
									            </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="mt-2">
                                    <button type="submit" class="btn btn-secondary">登録して終了</button>
                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="mx-3 text-muted">該当する学生が存在しませんでした。</div>
                    </c:otherwise>
                </c:choose>
            </c:if>

        </section>
    </c:param>
</c:import>
