<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberEntity" var="member" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q & A 게시판</title>
<link rel="stylesheet" type="text/css" href="/css/studylist.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>
	<div class="main-wrap">
		<section class="notice">
			<div class="page-title">
				<div class="container">
					<h3>Q&A 게시판</h3>
				</div>
			</div>
			<input type="text" id="username" value="${member.username}" hidden="hidden" />
			<!-- board seach area -->
			<div id="board-search">
				<div class="container">
					<div class="search-window">
						<div class="search-wrap">
							<label for="search" class="blind">Q&A 게시판 제목 검색</label>
							<input id="search" type="search" name="search" placeholder="검색어를 입력해주세요.">
							<button type="button" id="searchBtn" class="btn btn-dark">검색</button>
						</div>
					</div>
				</div>
			</div>

			<!-- board list area -->
			<div id="board-list">
				<div class="container">
					<table class="board-table">
						<colgroup>
							<col width="10%">
							<col width="60%">
							<col width="15%">
							<col width="15">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" class="th-num">번호</th>
								<th scope="col" class="th-title">제목</th>
								<th scope="col" class="th-write">작성자</th>
								<th scope="col" class="th-date">등록일</th>
							</tr>
						</thead>
						<!-- 게시글 리스트 출력 -->
						<tbody id="postList" />
					</table>

					<!-- 페이징 처리 출력 -->
					<div id="pagination"></div>
					<button type="button" id="registerBtn" class="btn btn-dark" onclick="qnaRegister();">글쓰기</button>
				</div>
			</div>
		</section>
	</div>
	<script src="/js/qnaList.js"></script>
	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
</body>
</html>