<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal.memberEntity" var="member" />
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<meta charset="UTF-8">
<title>Chatting</title>
<link rel="shortcut icon" href="#">
<!-- <link rel="stylesheet" href="/css/chat.css"> -->
<style type="text/css">
* {
	box-sizing: border-box;
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	height: 100vh;
	margin: 0;
}

p {
	margin: 0;
	padding: 10px;
	border-radius: 10px;
	display: flex;
	flex-direction: column;
}

p[class=others] {
	align-items: flex-start;
	background-color: #4f5458;
	color: white;
}

p[class=me] {
	align-items: flex-end;
	background-color: #fce92a;
	color: black;
}

h1 {
	margin-top: 0;
}

/* 화면전체 */
.outline-chat-box {
	width: 500px;
	border: 1px solid black;
	padding: 15px;
	border-radius: 10px;
}

.chating {
	display: flex;
	flex-direction: column;
	width: 100%;
	height: 400px;
	overflow: auto;
}

/* 오고가는 채팅박스 */
.my-chat-box, .another-chat-box {
	display: flex;
	flex-direction: column;
	margin-top: 10px;
}

label {
	font-size: 20px;
	font-weight: 600;
}

.my-chat-box {
	align-items: flex-end;
	float: right;
}

.another-chat-box {
	align-items: flex-start;
}

/* 채팅박스 내 메세지박스 */
.my-msg-box, .others-msg-box {
	max-width: 70%;
	margin-top: 5px;
	border-radius: 10px;
	word-break: break-all;
}

/* 메세지 전송 */
#yourMsg {
	width: 100%;
	display: flex;
	align-items: center;
	text-align: center;
}

#chatting {
	padding: 10px;
	margin-top: 20px;
	width: 100%;
	resize: none;
	margin-top: 20px;
	border-radius: 10px;
}

#sendBtn {
	margin: 20px 0 0 10px;
	padding: 10px;
	height: 60px;
}
</style>

</head>

<script src="/js/chat.js"></script>

<body>
	<div id="container" class="container">
		<h1>${room.roomName}</h1>
		<div class="outline-chat-box">
			<div id="chating" class="chating">
				<input type="text" id="username" value="${member.username}" style="display: none;">
				<input type="text" id="nickName" value="${member.nickName }" style="display: none;">
				<input type="text" id="roomIdx" value="${room.roomIdx}" style="display: none;">
				<input type="text" id="teamNumber" value="${member.teamNumber.teamNumber}" style="display: none;">
			</div>
		</div>

		<div id="yourMsg">
			<textarea id="chatting" cols="50" rows="3" placeholder="메세지를 입력하세요"></textarea>
			<button onclick="send()" id="sendBtn">Send</button>
		</div>

	</div>
</body>
</html>