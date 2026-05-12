<%-- 学生登録JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績情報削除</h2>
			
			<p class="alert alert-success py-1 text-center">削除が完了しました</p>
			<br>
				<br>
				<br>
			<a href="TestList.action">成績一覧</a>
		</section>
	</c:param>
</c:import>