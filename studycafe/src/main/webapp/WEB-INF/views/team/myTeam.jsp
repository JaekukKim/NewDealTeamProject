<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- 로그인 한 회원 정보 사용 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberEntity" var="member" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${team.teamName}</title>
<link rel="stylesheet" href="/css/myteam.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="main-wrap">
		<div class="team-name-div">
			<h1>${team.teamName}</h1>
			<input type="hidden" id="teamNumber" value="${team.teamNumber }">
		</div>
		<div class="team-member-div">
			<div class="member-info">
				<div class="team-name">
					<b>아이디</b>
				</div>
				<div>
					<b>이름</b>
				</div>
				<div class="team-name">
					<b>닉네임</b>
				</div>
				<c:if test="${team.teamHead eq member.username }">
					<div>
						<b>관리</b>
					</div>
				</c:if>
			</div>

			<c:forEach items="${teamMember}" var="teamMember">

				<div class="member-info">
					<div class="team-name" title="${teamMember.username}">${teamMember.username}</div>
					<div>${teamMember.name}</div>
					<div class="team-name" title="${teamMember.nickName}">${teamMember.nickName}</div>

					<c:if test="${team.teamHead eq member.username}">
						<div>
							<button onclick="getOutTeam('${teamMember.username}');">내보내기</button>
						</div>
					</c:if>
				</div>

			</c:forEach>
		</div>
	</div>
	<input id="teamHead" type="hidden" value="${team.teamHead }">
	<script src="/js/myTeam.js"></script>
</body>
</html>