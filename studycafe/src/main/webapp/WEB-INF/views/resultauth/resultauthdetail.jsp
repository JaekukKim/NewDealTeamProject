<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- 로그인 한 회원 정보 사용 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberEntity" var="member" />
</sec:authorize>

<fmt:parseDate var="date" value="${studyEntity.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"/>
<fmt:formatDate  var="dateTime" value="${date}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${studyEntity.studyTitle}</title>
<link rel="stylesheet" type="text/css" href="/css/authdetail.css">
<!-- 폰트어썸 아이콘 https://fontawesome.com/ -->
<script src="https://kit.fontawesome.com/b780cabc8c.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/36.0.0/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>
	
	<div class="main-wrap">
		<div class="index-ingredient">
			<form class="main-form" method="post" enctype="multipart/form-data">
				<p>
					<label for="studyTitle">제목</label>
					<input type="text" id="studyTitle" name="studyTitle" value="${studyEntity.studyTitle}" readonly="readonly"></p>
				<p>
					<label for="studyWriter">작성자</label>
					<input type="text" id="studyWriter" name="studyWriter" value="${studyEntity.studyWriter}" readonly="readonly"></p>
				<p>
					<label for="dateTime">작성날짜</label>
					<input type="text" id="dateTime" name="dateTime" value="${dateTime}" readonly="readonly"></p>
				<div>
					<label for="studyContent">내용</label>
					<textarea id="studyContent" name="studyContent" readonly="readonly">${studyEntity.studyContent}</textarea></div>

				<button class="btn btn-dark" id="listBtn" type="button" onclick="location.href='/study/''">목록</button>

				<c:set var="nickName" value="${member.nickName}" />
				<c:set var="writer" value="${studyEntity.studyWriter}" />
				
				<c:choose>
					<c:when test="${nickName eq writer}">
						<button class="btn btn-dark" id="modifyBtn" type="button" onclick="location.href='/studymodify/${studyEntity.studyNum}'">수정</button>
						<button class="btn btn-dark" id="deleteBtn" type="button" onclick="studyDelete()">삭제</button>
					</c:when>
				</c:choose>
			</form>
			<div class="content-wrap">
				<p><label>댓글</label></p>
				<div>
					<ul class="reply-list" id="reply-list"></ul>
					<c:choose>
						<c:when test="${not empty nickName}">
						<div class="flex items-center">평가</div>
						<div class="star-rating mx-auto">
						  <input type="radio" id="5-stars" name="rating" value="5" v-model="ratings"/>
						  <label for="5-stars" class="star">★</label>
						  <input type="radio" id="4-stars" name="rating" value="4" v-model="ratings"/>
						  <label for="4-stars" class="star">★</label>
						  <input type="radio" id="3-stars" name="rating" value="3" v-model="ratings"/>
						  <label for="3-stars" class="star">★</label>
						  <input type="radio" id="2-stars" name="rating" value="2" v-model="ratings"/>
						  <label for="2-stars" class="star">★</label>
						  <input type="radio" id="1-star" name="rating" value="1" v-model="ratings" />
						  <label for="1-star" class="star">★</label>
						</div>
						<div>
							<input type="hidden" id="nickName" value="${member.nickName}">
							<textarea class="comment" id="comment" rows="5" placeholder="코멘트 달기"></textarea>
							<button class="btn btn-dark" id="commentBtn" type="button" onclick="studyReplyInsert();">작성</button>
						</div>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
 	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=59e92bd7943f48f31ff73b322a4e5603&libraries=services,clusterer,drawing"></script>
	<script src="/js/studydetail.js"></script>
	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
	<script>
		$(document).ready(function () {
			studyReplyList();
		});
	</script>
</body>
</html>