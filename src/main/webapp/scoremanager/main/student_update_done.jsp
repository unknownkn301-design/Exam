<%-- 学生情報変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">
        <section class="px-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

            <div class="alert alert-success" role="alert">
                変更が完了しました。
            </div>

            <table class="table table-bordered w-auto mb-4">
                <tr>
                    <th class="bg-light px-4">学生番号</th>
                    <td class="px-4">${student.studentNo}</td>
                </tr>
                <tr>
                    <th class="bg-light px-4">氏名</th>
                    <td class="px-4">${student.studentName}</td>
                </tr>
                <tr>
                    <th class="bg-light px-4">入学年度</th>
                    <td class="px-4">${student.entYear}</td>
                </tr>
                <tr>
                    <th class="bg-light px-4">クラス</th>
                    <td class="px-4">${student.classNum}</td>
                </tr>
                <tr>
                    <th class="bg-light px-4">在学中</th>
                    <td class="px-4">
                        <c:choose>
                            <c:when test="${student.isAttend()}">◯</c:when>
                            <c:otherwise>×</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>

            <div class="d-flex gap-3">
                <a href="StudentUpdate.action?no=${student.studentNo}" class="btn btn-outline-primary">続けて変更</a>
                <a href="StudentList.action" class="btn btn-secondary">学生一覧へ戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
