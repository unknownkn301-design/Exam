<%-- 科目登録完了JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目登録完了</h2>
			<div class="px-4 mt-3">
				<p>以下の科目を登録しました。</p>
				<table class="table table-bordered w-50">
					<tr>
						<th>科目コード</th>
						<td>${subject_cd}</td>
					</tr>
					<tr>
						<th>科目名</th>
						<td>${subject_name}</td>
					</tr>
				</table>
				<div class="mt-3">
					<a href="SubjectCreate.action" class="me-4">続けて登録する</a>
					<a href="SubjectList.action">科目一覧へ</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>
