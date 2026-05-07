<%-- 成績登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績登録完了</h2>
            <div class="px-4 mt-4">
                <p>以下の条件で成績を登録しました。</p>
                <table class="table table-bordered w-50">
                    <tr>
                        <th>入学年度</th>
                        <td>${ent_year}年度</td>
                    </tr>
                    <tr>
                        <th>クラス</th>
                        <td>${class_num}</td>
                    </tr>
                    <tr>
                        <th>科目</th>
                        <td>${subject.subjectName}</td>
                    </tr>
                    <tr>
                        <th>回数</th>
                        <td>第${no}回</td>
                    </tr>
                </table>
                <div class="mt-3">
                    <a href="TestRegist.action" class="btn btn-secondary me-2">続けて登録</a>
                    <a href="Menu.action" class="btn btn-outline-secondary">メニューへ戻る</a>
                </div>
            </div>
        </section>
    </c:param>
</c:import>
