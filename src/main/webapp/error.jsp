<%-- エラーページ --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム - エラー</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="px-4 py-5 text-center">
            <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                エラーが発生しました
            </h2>
            <p class="text-muted mb-4">
                操作を正しく処理できませんでした。<br>
                お手数ですが、メニューに戻って再度お試しください。
            </p>
            <a href="scoremanager/main/Menu.action" class="btn btn-secondary">メニューへ戻る</a>
        </section>
    </c:param>
</c:import>
