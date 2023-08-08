<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="/css/loginform.css">
<script src="/js/loginForm.js"></script>
</head>
<body>
	<div class="container">
		<div class="main-form">
			<div>
				<a href="/"> <img alt="로고" src="/img/logo.png" width="250" height="80">
				</a>
			</div>
			<h2>DSSEAD에 오신것을 환영합니다.</h2>
			<h4>개발자를 위한 스터디 사이트입니다.</h4>
			<form id="loginForm" method="post" action="/loginform">
				<div class="input-box">
					<div class="username-box">
						<input type="text" id="username" name="username" class="insert" maxlength="30" placeholder="아이디">
					</div>
					<div class="password-box">
						<input type="password" id="password" name="password" class="insert" maxlength="20" placeholder="비밀번호">
					</div>
				</div>
				<div class="login-join-box">
					<div class="remember-me">
						<label for="remember-me"> 계정 정보 저장하기 </label>
						<input type="checkbox" id="remember-me" name="remember-me" />
					</div>

					<div class="login-div">
						<button type="submit" class="btn-login">로그인</button>
					</div>
					<div align="right" class="join-find-div">
						회원 정보를 잊으셨나요? <a href="javascript:findAccount()">회원정보 찾기</a>
					</div>
					<div align="right" class="join-find-div">
						아직 회원이 아니신가요? <a href="${contextPath}/joinform">회원가입 바로가기</a>
					</div>
				</div>

				<fieldset>
					<legend align="left">소셜로그인</legend>
					<div class="sns-login">
						<div id="kakao_id_login">
							<img src="/img/kakao-logo.png" width="30" height="30"> <a href="/oauth2/authorization/kakao"> Kakao Login </a>
						</div>
						<div id="git_id_login">
							<img src="/img/github.png" width="30" height="30"> <a href="/oauth2/authorization/github"> GitHub Login </a>
						</div>
						<div id="google_id_login">
							<img src="/img/google-logo.png" width="30" height="30"> <a href="/oauth2/authorization/google" class="btn btn-success active"
								role="button">Google Login</a>
						</div>
					</div>
				</fieldset>

			</form>
		</div>
	</div>

</body>
</html>