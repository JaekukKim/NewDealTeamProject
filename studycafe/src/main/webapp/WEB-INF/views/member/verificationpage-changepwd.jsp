<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 재설정</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="/js/changepwd.js"></script>
<link rel="stylesheet" href="/css/verification-pwd.css">
<style type="text/css">
</style>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../member/verificationpage-aside.jsp"></jsp:include>
		<div class="password-wrapper">
			<h2>비밀번호 변경</h2>
			
			<label>현재 비밀번호</label>
			<input type="password" id="oneraepassword" name="oneraepassword"/>
			
			<label>새로운 비밀번호</label>
			<input type="password" id="password" name="password" placeholder="영문,숫자,특수문자 조합 (8~15자)" />

			<label>새로운 비밀번호 확인</label>
			<input type="password" id="rePassword" name="rePassword" placeholder="비밀번호를 재확인" />
			<div class="btn-box">
				<button type="button" onclick="changePassword()">다음</button>
			</div>
		</div>
	</div>
</body>
</html>