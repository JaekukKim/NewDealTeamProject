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

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>스터디 게시판</title>
	<link rel="stylesheet" type="text/css" href="/css/studylist.css">
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>
	<div class="main-wrap">
		<section class="notice">
		  <div class="page-title">
		        <div class="container">
		            <h3>스터디 모집 게시판</h3>
		        </div>
		    </div>
		
		    <!-- board seach area -->
		    <div id="board-search">
		        <div class="container">
		            <div class="search-window">
	                    <div class="search-wrap">
	                        <label for="search" class="blind">스터디 게시판 제목 검색</label>
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
						            <col width="15%">
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

					<c:set var="nickName" value="${member.nickName}" />
					
					<c:choose>
						<c:when test="${not empty nickName}">
							<button type="button" id="registerBtn" class="btn btn-dark" onclick="location.href='studyregistration'">글쓰기</button>
						</c:when>
					</c:choose>
		        </div>
		    </div>
		</section>
	</div>
	<script>
		$('#search').keydown(function(key) {
			if (key.keyCode == 13) {
				$('#searchBtn').click();
			}
		});
		
		$(document).ready(function () {
			loadPosts($('#search').val(), $(this).data('page'));
	
		    $('#searchBtn').on('click', function () {
		        loadPosts($('#search').val(), 0); // 검색어가 바뀌면 첫 페이지부터 검색 결과를 보여줍니다.
		    });
	
		    $(document).on('click', '.pagination-link', function () {
		        loadPosts($('#search').val(), $(this).data('page'));
		    });
		});
	</script>
	<script src="/js/studylist.js"></script>
	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
</body>
</html>