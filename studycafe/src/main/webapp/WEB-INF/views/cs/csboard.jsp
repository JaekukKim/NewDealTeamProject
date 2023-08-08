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
<title>고객센터</title>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" href="/css/teamboard.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />

</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

	<div class="main-wrap">
		<div class="index-ingredient">
			<div class="index main-regteam">

				<sec:authorize access="isAuthenticated()">
					<div class="team-regist-div">
						<button class="team-regist" onclick="location.href = '${contextPath}/cs/csregispage'">문의하기</button>
					</div>
				</sec:authorize>

				<sec:authorize access="isAnonymous()">
					<div class="team-regist-div" align="right">문의는 로그인 시 이용 가능합니다.</div>
				</sec:authorize>

				<label> 문의 게시판 </label>
				<c:forEach items="${csBoardList.content }" var="list">
					<div>
						<c:choose>
							<c:when test="${list.secret == true}">
								<c:choose>
									<c:when test="${list.username eq member.username || member.role eq 'ROLE_ADMIN'}">
										<i class="fas fa-lock"></i>
										&emsp;
										<a href="${contextPath }/cs/csboard/${list.idx}">${list.csTitle }</a>
									</c:when>
									<c:otherwise>
										<i class="fas fa-lock"></i>
										&emsp;
										<a class="secret-list">비밀글입니다</a>
									</c:otherwise>
								</c:choose>
							</c:when>
							
							<c:otherwise>
								<i class="fas fa-lock-open"></i>
								&emsp;
								<a href="${contextPath }/cs/csboard/${list.idx}">${list.csTitle }</a>
							</c:otherwise>
						</c:choose>

						<span>
							<font size="2">${list.csWriter }</font>
							<br>
							<font size="2"><fmt:formatDate value="${list.createDate }" pattern="yyyy-MM-dd" /></font>
						</span>

					</div>
				</c:forEach>
			</div>
		</div>
		<div>
			<!-- 페이징 -->
			<p class="pagination" align="center">

				<!-- first -->
				<c:if test="${page.first == false }">
					<span class="page-bool">
						<a class="page-link" href="?page=0" title="처음 페이지로">First</a>
					</span>
				</c:if>

				<!-- prev -->
				<c:if test="${page.previous == true }">
					<span class="page-bool">
						<a class="page-link" href="?page=${(page.startPage < 10) ? 0 : (page.startPage - page.totalPagesInEachScreen - 1)}" title="이전 페이지로">Previous</a>
					</span>
				</c:if>

				<!-- paging -->
				<c:forEach var="i" begin="${page.startPage }" end="${page.endPage}">
					<c:choose>
						<c:when test="${page.number != i }">
							<span class="page-item">
								<a class="page-link" href="?page=${i-1}">${i}</a>
							</span>
						</c:when>
						<c:when test="${page.number == i }">
							<span class="page-item">
								<b class="selected-number" style="color: #0b7fd3;">${i }</b>
							</span>
						</c:when>
					</c:choose>
				</c:forEach>

				<!-- next -->
				<c:if test="${page.next == true }">
					<span class="page-bool">
						<a class="page-link" href="?page=${page.startPage + page.totalPagesInEachScreen - 1 }" title="다음 페이지로">Next</a>
					</span>
				</c:if>

				<!-- last -->
				<c:if test="${page.last == false }">
					<span class="page-bool">
						<a class="page-link" href="?page=${page.totalPages-1 }" title="마지막 페이지로">Last</a>
					</span>
				</c:if>
			</p>
			<!-- // 페이징 -->
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
	<script type="text/javascript">
		
	</script>
</body>
</html>