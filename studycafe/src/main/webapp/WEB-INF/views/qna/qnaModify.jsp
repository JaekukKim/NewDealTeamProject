<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Q & A 등록</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/36.0.0/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

<link rel="stylesheet" type="text/css" href="/css/studyregistration.css">

<link
href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
rel="stylesheet"
integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
crossorigin="anonymous"
/>

</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

	<div class="main-wrap">
		<div class="index-ingredient">
			<form action="/qnaModifyPro" name="studymodify_form" method="post" enctype="multipart/form-data">
				<p>
					<label for="studytitle">제목</label>
					<input type="text" id="studyTitle" name="qnaTitle" value="${qnaEntity.qnaTitle}" placeholder="제목 입력">
				</p>

				<p>
					<label for="studyWriter">작성자</label>
					<input type="text" id="studyWriter" name="qnaWriter" value="${qnaEntity.qnaWriter}" readonly="readonly">
				</p>

				<div>
					<label for="studyContent">내용</label>
					<textarea id="studyContent" name="qnaContent" style="outline: none;">${qnaEntity.qnaContent}</textarea>
				</div>
				
				<!-- 이미지 첨부시 미리보기 -->
				<p>
					<label for="file">첨부파일</label>
					<input type="file" id="file" name="file" placeholder="파일등록">
					
				</p>
				
				<!-- 이미지 임시출력 -->
				<div class="select_img">
					<img src="" />
				</div>
				<!-- //이미지 첨부시 미리보기 -->
				
				
				
				<!-- hidden 데이터 -->
				
				<input type="hidden" id="dateTime" name="qnaDate" value="${qnaEntity.qnaDate}">
				<input type="hidden" id="studyNum" name="qnaNum" value="${qnaEntity.qnaNum}">
				
				
				<input type="hidden" id="studyNum" name="qnaNum" value="${qnaEntity.qnaNum}">
				<button class="btn btn-dark" id="registerBtn" type="button" onclick="regis_check();">수정</button>
			</form>
		</div>
	</div>
	
	 <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
      crossorigin="anonymous"
    ></script>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=59e92bd7943f48f31ff73b322a4e5603&libraries=services,clusterer,drawing"></script>

	<script src="/js/qnamodify.js"></script>
</body>
</html>