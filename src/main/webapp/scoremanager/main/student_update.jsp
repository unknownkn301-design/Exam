<%-- 学生情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

            <%-- 全体エラー（学生未発見など）--%>
            <c:if test="${not empty errors.get('notFound')}">
                <div class="alert alert-danger mx-4">${errors.get('notFound')}</div>
            </c:if>

            <form action="StudentUpdateExecute.action" method="post" class="px-4">
                <%-- 学生番号は変更不可（主キー）のため hidden で引き回す --%>
                <input type="hidden" name="no" value="${student.studentNo}" />

                <%-- 学生番号（表示のみ）--%>
                <div class="mb-3">
                    <label class="form-label fw-semibold">学生番号</label>
                    <input type="text" class="form-control bg-light" value="${student.studentNo}" readonly />
                    <div class="form-text text-muted">学生番号は変更できません。</div>
                </div>

                <%-- 氏名 --%>
                <div class="mb-1">
                    <label class="form-label fw-semibold" for="name">氏名</label>
                    <input class="form-control <c:if test='${not empty errors.get(\"name\")}'>is-invalid</c:if>"
                           type="text" id="name" name="name"
                           value="${student.studentName}"
                           required maxlength="30"
                           placeholder="氏名を入力してください" />
                    <c:if test="${not empty errors.get('name')}">
                        <div class="invalid-feedback">${errors.get('name')}</div>
                    </c:if>
                </div>
                <div class="mb-3"></div>

                <%-- 入学年度 --%>
                <div class="mb-1">
                    <label class="form-label fw-semibold" for="ent_year">入学年度</label>
                    <select class="form-select <c:if test='${not empty errors.get(\"ent_year\")}'>is-invalid</c:if>"
                            id="ent_year" name="ent_year">
                        <option value="0">--------</option>
                        <c:forEach var="y" items="${ent_year_set}">
                            <option value="${y}" <c:if test="${y == student.entYear}">selected</c:if>>${y}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty errors.get('ent_year')}">
                        <div class="invalid-feedback">${errors.get('ent_year')}</div>
                    </c:if>
                </div>
                <div class="mb-3"></div>

                <%-- クラス --%>
                <div class="mb-1">
                    <label class="form-label fw-semibold" for="class_num">クラス</label>
                    <select class="form-select <c:if test='${not empty errors.get(\"class_num\")}'>is-invalid</c:if>"
                            id="class_num" name="class_num">
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty errors.get('class_num')}">
                        <div class="invalid-feedback">${errors.get('class_num')}</div>
                    </c:if>
                </div>
                <div class="mb-3"></div>

                <%-- 在学中フラグ --%>
                <div class="mb-4 form-check">
                    <input class="form-check-input" type="checkbox" id="is_attend" name="is_attend" value="t"
                           <c:if test="${student.isAttend()}">checked</c:if> />
                    <label class="form-check-label fw-semibold" for="is_attend">在学中</label>
                </div>

                <%-- ボタン --%>
                <div class="d-flex gap-3 align-items-center">
                    <button type="submit" class="btn btn-primary px-4">変更</button>
                    <a href="StudentList.action" class="btn btn-outline-secondary">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
