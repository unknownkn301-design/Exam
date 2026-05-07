<%-- 科目情報変更JSP --%>
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
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<form action="SubjectUpdateExecute.action" method="post">

				<%-- hidden: 科目コードをPOSTに含める --%>
				<input type="hidden" name="cd" value="${cd}">

				<%-- ② 科目コード ラベル --%>
				<div class="mt-3">
					<label>科目コード</label><br>
					<%-- ③ 科目コード表示テキスト (readonly) --%>
					<input class="form-control w-auto" type="text" name="cd_disp"
						value="${cd}" readonly>
				</div>

				<%-- エラーメッセージ表示（別画面から削除された場合） --%>
				<c:if test="${not empty errorMsg}">
					<div class="mt-2 text-warning">${errorMsg}</div>
				</c:if>

				<%-- ④ 科目名 ラベル --%>
				<div class="mt-3">
					<label for="name">科目名</label><br>
					<%-- ⑤ 科目名入力テキスト (必須・最大20文字) --%>
					<input class="form-control" type="text" id="name" name="name"
						value="${name}"
						maxlength="20"
						required
						placeholder="科目名を入力してください">
				</div>

				<%-- ⑥ 変更ボタン --%>
				<div class="mt-3">
					<button type="submit" class="btn btn-primary">変更</button>
				</div>

			</form>

			<%-- ⑦ 戻るリンク → 科目管理一覧画面 --%>
			<a href="SubjectList.action" class="d-inline-block mt-2">戻る</a>

		</section>
	</c:param>
</c:import>
