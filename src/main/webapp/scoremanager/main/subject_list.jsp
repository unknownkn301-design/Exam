<%-- 科目一覧JSP --%>
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
            <%-- ① 画面タイトル --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>

            <%-- ② 新規登録リンク --%>
            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <%-- ③ 科目一覧テーブル --%>
            <table class="table table-hover mx-3" style="width:calc(100% - 2rem);">
                <tr>
                    <%-- ④ ヘッダ（科目コード） --%>
                    <th>科目コード</th>
                    <%-- ⑤ ヘッダ（科目名） --%>
                    <th>科目名</th>
                    <th></th>
                    <th></th>
                </tr>

                <c:choose>
                    <c:when test="${not empty subjects}">
                        <c:forEach var="subject" items="${subjects}">
                            <tr>
                                <%-- ⑥ 科目情報（科目コード） --%>
                                <td>${subject.subjectCd}</td>
                                <%-- ⑦ 科目情報（科目名） --%>
                                <td>${subject.subjectName}</td>
                                <%-- ⑧ 科目情報変更リンク --%>
                                <td><a href="SubjectUpdate.action?cd=${subject.subjectCd}">変更</a></td>
                                <%-- ⑨ 科目情報削除リンク --%>
                                <td><a href="SubjectDelete.action?cd=${subject.subjectCd}"
                                       onclick="return confirm('「${subject.subjectName}」を削除しますか？');">削除</a></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <%-- 科目が登録されていない時の表示 --%>
                        <tr>
                            <td colspan="4">科目情報が登録されていません。</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </section>
    </c:param>
</c:import>
