<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authentication property="principal.memberEntity" var="member" />

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 등록 글 작성</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/36.0.0/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

<link rel="stylesheet" href="/css/teamregis.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

	<div class="main-wrap">
		<div class="index-ingredient">

			<form action="/team/teamregis" method="post" onsubmit="return validPost()" enctype="multipart/form-data">
				<p>
					<label for="team-title">제목</label>
					<input type="text" id="team-title" name="teamBoardTitle" placeholder="제목 입력">
				</p>

				<p class="error-box">
					<label></label>
					<span>${valid_teamBoardTitle }</span>
				</p>

				<p>
					<label for="team-head">작성자(팀장)</label>
					<input type="text" id="team-head" name="teamBoardWriter" value="${member.nickName }" readonly="readonly">
				</p>
				

				<p>
					<label for="team-head">팀 이름</label>
					<input type="text" id="team-name" name="teamName"  readonly="readonly">
					<div>
						<button type="button" class="team-add-btn" onclick="teamNameAdd();">
							추가하기
						</button>
					</div>
				</p>
				<p class="error-box">
					<label></label>
					<span>${valid_teamName }</span>
				</p>
				<p>
					<label for="team-head">팀원</label>
					<input type="text" id="team-member" name="teamMember" value="${member.username}" readonly="readonly">
					<div>
						<button type="button" onclick="teamMemberAdd();" class="team-add-btn">
							추가하기
						</button>
					</div>
				</p>
				<p class="error-box">
					<label></label>
					<span>${valid_teamMember }</span>
				</p>

				<div>
					<label for="content">내용</label>
					<textarea id="content" name="teamBoardContent">
						<!-- 팀 이름&nbsp;&nbsp; :<br>
						팀원&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : 반드시 팀원의 "ID"를 입력해주세요! -->
						
					</textarea>
				</div>
				<p class="error-box">
					<label></label>
					<span>${valid_teamBoardContent }</span>
				</p>
				<button type="submit" class="regis-btn">팀 등록</button>
			</form>	
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
	<script src="/js/teamregis.js"></script>
</body>
</html>