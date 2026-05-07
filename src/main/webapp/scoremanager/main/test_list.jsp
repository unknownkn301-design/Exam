<%-- 成績参照JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<form method="get" class="px-4 mb-4">
				<div class="row align-items-end">
					<div class="col-6">
						<label for="student_no" class="form-label">学生</label>
						<select class="form-select" id="student_no" name="student_no">
							<option value="">-- 学生を選択してください --</option>
							<c:forEach var="student" items="${students}">
								<option value="${student.studentNo}"
									<c:if test="${student.studentNo == student_no}">selected</c:if>>
									${student.studentNo} - ${student.studentName}
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<button type="submit" class="btn btn-secondary">表示</button>
					</div>
				</div>
				<div class="mt-2 text-warning">${errors.get("student_no")}</div>
			</form>

			<c:if test="${selected_student != null}">
				<div class="px-4">
					<h5>${selected_student.studentName}（${selected_student.studentNo}）の成績</h5>
					<c:choose>
						<c:when test="${scores != null && scores.size() > 0}">
							<table class="table table-hover mt-2">
								<tr>
									<th>科目コード</th>
									<th>科目名</th>
									<th class="text-end">得点</th>
									<th></th>
								</tr>
								<c:forEach var="score" items="${scores}">
									<tr>
										<td>${score.subject.subjectCd}</td>
										<td>${score.subject.subjectName}</td>
										<td class="text-end">${score.point} 点</td>
										<td>
											<a href="TestRegist.action?student_no=${score.student.studentNo}&subject_cd=${score.subject.subjectCd}">修正</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<c:otherwise>
							<p class="text-muted">成績が登録されていません。</p>
						</c:otherwise>
					</c:choose>
					<a href="TestRegist.action" class="btn btn-secondary btn-sm mt-2">成績を登録する</a>
				</div>
			</c:if>

			<div class="px-4 mt-3">
				<a href="Menu.action">メニューへ戻る</a>
			</div>
		</section>
	</c:param>
</c:import>
