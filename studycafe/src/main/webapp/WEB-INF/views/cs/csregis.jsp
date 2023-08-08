<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal.memberEntity" var="member" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/36.0.0/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

<link rel="stylesheet" href="/css/teamregis.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

	<div class="main-wrap">
		<div class="index-ingredient">

			<form action="/cs/csregis" method="post" enctype="multipart/form-data">
				<p>
					<label for="team-title">제목</label>
					<input type="text" id="team-title" name="csTitle" placeholder="제목 입력">
				</p>
				
				<p class="error-box">
					<label></label>
					<span>${valid_csTitle }</span>
				</p>

				<p>
					<label for="team-head">작성자</label>
					<input type="text" id="team-head" name="csWriter" value="${member.nickName }" readonly="readonly">
					<input type="hidden" name="username" value="${member.username }" readonly="readonly">
				</p>

				<p>
					<label for="team-secret">비밀글 여부</label>
					<span>
						공개
						<input type="radio" class="secret-yn" id="team-secret" name="secret" value="0" checked="checked">
					</span>
					<span class="secret-yn">
						비공개
						<input type="radio" class="secret-yn" name="secret" value="1">
					</span>
				</p>

				<div>
					<label for="content">내용</label>
					<textarea id="content" name="csContent">
						문의 내역을 입력해주세요.
					</textarea>
				</div>
				
				<p class="error-box">
					<label></label>
					<span>${valid_csContent }</span>
				</p>

				<!-- 이미지 첨부시 미리보기 -->
				<p>
					<label for="file">첨부파일</label>
					<input type="file" id="file" name="file" placeholder="파일등록">

				</p>

				<div class="select_img">
					<img class="inner-img" src="" />
				</div>
				<!-- //이미지 첨부시 미리보기 -->

				<button class="regis-btn">문의하기</button>
			</form>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
	<script src="/js/teamregis.js"></script>
</body>
</html>