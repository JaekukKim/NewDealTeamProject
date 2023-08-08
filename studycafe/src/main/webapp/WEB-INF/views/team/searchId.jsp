<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID 찾기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="/css/searchId.css">
<script src="/js/searchId.js"></script>
</head>
<body>
	<jsp:include page="../member/findinfo-header.jsp"></jsp:include>

	<div class="find-by-id">
		<p>회원 가입 시 등록한 이메일을 입력해주세요</p>
		<input type="email" id="email" name="email" placeholder="이메일을 입력해주세요." />
		<button type="button" onclick="searchId()">찾기</button>
	</div>
</body>
</html>