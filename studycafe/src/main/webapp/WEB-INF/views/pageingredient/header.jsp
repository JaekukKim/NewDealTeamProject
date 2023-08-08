<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- 로그인 한 회원 정보 사용 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberEntity" var="member" />
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="header-body">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>헤더</title>
<link rel="stylesheet" href="/css/header.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body class="header-body">
	<div class="wrapper">
		<nav>
			<input type="checkbox" id="show-search">
			<input type="checkbox" id="show-menu">
			<label for="show-menu" class="menu-icon">
				<i class="fas fa-bars"></i>
			</label>

			<div class="content">
				<a href="/" class="logo"> <img alt="로고" src="/img/logo.png" width="200" height="50">
				</a>

				<ul class="links">
					<li>
						<sec:authorize access="isAnonymous()">
							<a href="javascript:restrictGPT()">Chat GPT</a>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<a href="/gpt/opengpt" target="_blank">Chat GPT</a>
						</sec:authorize>
					</li>

					<li>
						<a href="/qna">Q &amp; A</a>
					</li>

					<li>
						<a href="" class="desktop-link">스터디</a>
						<input type="checkbox" id="show-features">
						<label for="show-features">스터디</label>
						<ul>
							<li>
								<a href="/study">스터디모집</a>
							</li>
							<li>
								<a href="${contextPath}/team/teamboards">팀신청</a>
							</li>
							<li>
								<a href="#">인증게시판</a>
							</li>
						</ul>
					</li>

					<li>
						<a href="#" class="desktop-link">팀관리</a>
						<input type="checkbox" id="show-services">
						<label for="show-services">팀관리</label>
						<ul>
							<sec:authorize access="isAnonymous()">
								<li>
									<a>로그인해주세요</a>
								</li>
							</sec:authorize>
							<sec:authorize access="isAuthenticated()">
								<c:choose>
									<c:when test="${member.teamNumber == null }">
										<li>
											<a>팀 등록 후 이용 가능합니다</a>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="javascript:openMyTeam(${member.teamNumber.teamNumber})">나의팀관리</a>
										</li><%-- ${contextPath}/myteam/${member.teamNumber.teamNumber} --%>
										<li>
											<input type="hidden">
											<a href="javascript:openTeamChat(${member.teamNumber.teamNumber})">팀채팅방</a>
										</li>
										<input type="text" id="username" value="${member.username}" hidden="hidden" />
									</c:otherwise>
								</c:choose>
							</sec:authorize>
						</ul>
					</li>

					<li>
						<a href="${contextPath}/cs/csboard">고객센터</a>
					</li>
				</ul>

				<ul class="links member-ul">
					<li>
						<sec:authorize access="isAuthenticated()">
							<a href="#" class="desktop-link"> 안녕하세요 <span style="color: yellow;">${member.name}</span>님
							</a>
							<input type="checkbox" id="show-memberInfo">
							<label for="show-memberInfo">${member.nickName}</label>
						</sec:authorize>

						<sec:authorize access="isAnonymous()">
							<a href="${contextPath}/loginform" class="desktop-link">로그인</a>
							<input type="checkbox" id="show-memberInfo">
							<label for="show-memberInfo">로그인</label>
						</sec:authorize>
						<ul>
							<sec:authorize access="isAuthenticated()">

								<li>
									<a href="${contextPath}/logout">로그아웃</a>
								</li>

								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<li>
										<a href="/admin/home">관리자페이지</a>
									</li>
								</sec:authorize>

								<c:choose>
									<c:when test="${member.oauth2Path == null }">
										<li>
											<a href="/member/verificationpage">내정보관리</a>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="javascript:alertToSocial()">내정보관리</a>
										</li>
									</c:otherwise>
								</c:choose>

							</sec:authorize>
						</ul>
					</li>
				</ul>
			</div>
		</nav>
	</div>
	<script type="text/javascript" src="/js/header.js"></script>
</body>
</html>
