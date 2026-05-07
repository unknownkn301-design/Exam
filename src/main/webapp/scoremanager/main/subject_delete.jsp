<%-- 科目情報削除JSP --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

           <p>「${subject_name}(${subject_cd})」を削除してもよろしいですか</p>
	           <form action="SubjectDeleteExecute.action" method="post" class="px-4">
	           <input type="hidden" name="subject_cd" value="${subject_cd}">
	           <input type="hidden" name="subject_name" value="${subject_name}">
           
                <%-- ボタン --%>
                <div class="mx-auto py-2">
					<button type="submit" class="btn btn-danger">削除</button>
				</div>
			</form>
			<br>
			<br>
			<a href="SubjectList.action">戻る</a>
        </section>
    </c:param>
</c:import>
