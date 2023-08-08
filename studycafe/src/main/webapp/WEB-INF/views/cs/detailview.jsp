<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- 로그인 한 회원 정보 사용 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberEntity" var="member" />
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${csPost.csTitle }</title>
<link rel="stylesheet" type="text/css" href="/css/detailview.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/36.0.0/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

	<div class="main-wrap">
		<div class="index-ingredient">
			<form method="post" enctype="multipart/form-data">
				<p>
					<label for="team-title">제목</label>
					<input type="text" id="team-title" name="csTitle" value="${csPost.csTitle }" readonly="readonly">
					<input type="text" id="idx" name="idx" value="${csPost.idx}" hidden="hidden">
				</p>

				<p>
					<label for="team-head">작성자(팀장)</label>
					<input type="text" id="team-head" name="csWriter" value="${csPost.csWriter }" readonly="readonly">
				</p>

				<p>
					<label for="team-head">작성날짜</label>
					<input type="text" id="team-head" name="createDate" value=${csPost.createDate } readonly="readonly">
				</p>

				<div>
					<label for="content">내용</label>
					<textarea id="content" name="csContent" readonly="readonly">
						${csPost.csContent }
					</textarea>
				</div>

				<p>
					<label for="file">첨부파일</label>

					<c:choose>
						<c:when test="${csPost.fileKey eq 'none.png' }">
							<span>첨부파일 없음</span>
						</c:when>
						<c:otherwise>
							<img class="file-img" src="${csPost.filePath }" />
						</c:otherwise>
					</c:choose>
				</p>

				<c:if test="${member.nickName.equals(csPost.csWriter) }">
					<p class="modify-delete-box">
						<a type="submit" id="modify-btn" href="${contextPath }/cs/modifyview/${csPost.idx}">수정</a> <a type="submit" id="delete-btn"
							href="javascript:removePost('${csPost.idx}','cs')">삭제</a>
					</p>
				</c:if>
			</form>

			<hr>

			<div class="content-wrap">
				<p>
					<label>댓글</label>
				</p>
				<div>
					<ul class="reply-list" id="reply-list"></ul>
					<c:choose>
						<c:when test="${member.role == 'ROLE_ADMIN'}">
							<c:if test="${not empty member.nickName}">
								<div>
									<input type="hidden" id="nickName" value="${member.nickName}">
									<textarea class="comment" id="comment" rows="5" placeholder="코멘트 달기"></textarea>
									<button class="btn btn-dark" id="commentBtn" type="button" onclick="csReplyInsert();">작성</button>
								</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<div class="content-wrap" align="center">
								<h2>관리자만 답변 작성이 가능합니다.</h2>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
	<script src="/js/detailview.js"></script>
</body>
</html>