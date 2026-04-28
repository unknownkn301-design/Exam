<%-- 科目登録JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目登録</h2>
			<form action="SubjectCreateExecute.action" method="get">
				<div class="mt-3">
					<label for="subject_cd">科目コード</label><br>
					<input class="form-control" type="text" id="subject_cd" name="subject_cd"
						value="${subject_cd}" required maxlength="10"
						placeholder="科目コードを入力してください" />
				</div>
				<div class="mt-2 text-warning">${errors.get("1")}</div>

				<div class="mt-3">
					<label for="subject_name">科目名</label><br>
					<input class="form-control" type="text" id="subject_name" name="subject_name"
						value="${subject_name}" required maxlength="50"
						placeholder="科目名を入力してください" />
				</div>
				<div class="mt-2 text-warning">${errors.get("2")}</div>

				<div class="mx-auto py-3">
					<button type="submit" class="btn btn-secondary">登録して終了</button>
				</div>
			</form>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>
