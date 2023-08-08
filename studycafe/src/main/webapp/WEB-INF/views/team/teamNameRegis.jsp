<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<sec:authentication property="principal.memberEntity" var="member" />

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 이름 추가</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="/css/teamregis.css">

</head>
<body>
	<div class="main-wrap">
		<div class="index-ingredient">
			<div>
				<span class="ps_box int_id">
					<input type="text" id="teamName" pattern="[A-Za-z0-9\uAC00-\uD79D]{3,}" name="teamName" placeholder="팀 이름"/>
				</span>
				<button type="button" id="teamNameCheck" name="teamNameCheck" onclick="nameCheck()" disabled>중복체크</button>
				<div>
					<input type="text" style="display: none;" value="0" id="idCheck" />
					<span id="teamNamePass" style="display: none; color: blue;">사용 가능한 팀이름 입니다.</span>
					<span id="teamNameInUse" style="display: none; color: red;">이미 존재하는 팀이름 입니다.</span>
					<span id="notTeamName" style="display: none; color: red;">팀이름을 입력해주세요.</span>
					<span id="notNameCheck" style="display: none; color: red;">팀이름 중복체크를 해주세요.</span>
					<span id="notValid" style="display: none; color: red;">팀이름은 최소한 세글자 이상의 숫자와 알파벳, 한글로만 이루어져야 합니다.</span>
				</div>
				<div>
					<button type="button" id="useTeamName" name="useTeamName" onclick="useTeamName()" disabled>사용하기</button>
				</div>
			</div>

			
		</div>
	</div>
	<script src="/js/teamNameRegis.js"></script>
</body>
</html>