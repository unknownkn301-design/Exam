<%-- 学生情報変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="px-4">
			<%-- ① 画面タイトル --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

			<%-- ② 完了メッセージ --%>
			<p class="alert alert-success">変更が完了しました</p>

			<%-- ③ 科目一覧リンク --%>
			<a href="StudentList.action">学生一覧</a>

		</section>
	</c:param>
</c:import>
