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
<link rel="stylesheet" type="text/css" href="/css/studydetail.css">
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
				<p>
					<label for="dateTime">예약 시간</label>
					<input type="text" id="dateTime" name="dateTime" value="${studyEntity.reserveTime}" readonly="readonly"></p>
				<div>
					<label for="studyContent">내용</label>
					<textarea id="studyContent" name="studyContent" readonly="readonly">${studyEntity.studyContent}</textarea></div>
				<p>
					<label for="dateTime">지번 주소</label>
					<input type="text" id="address_name" name="address_name" readonly="readonly"></p>
				<!-- 도로명 주소 출력 -->
				<div id="road_address"></div>
				
				<div class="map_wrap" align="center">
					<div id="map"
						style="width: 100%; height: 500px; position: relative; overflow: hidden;"></div>
					<input type="hidden" id="lat" value="${studyEntity.latitude}">
					<input type="hidden" id="lng" value="${studyEntity.longitude}">
					<input type="hidden" id="studyNum" value="${studyEntity.studyNum}">
				</div>
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