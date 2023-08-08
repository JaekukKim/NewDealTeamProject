/**
 * 헤더 js
 */
function restrictGPT() {
	alert("Chat GPT 이용은 로그인 한 회원만 이용이 가능합니다.");
	return false;
}

function openTeamChat(teamNum) {
	var username = $("#username").val();
	sessionStorage.setItem("username", username);
	console.log(username);
	window.open("/chatRoom/moveChating?teamNumber=" + teamNum,
		"top=50, left=50", "toolbar=no,menubar=no");
}

function alertToSocial(){
	alert("소셜 로그인 회원은 연동하신 사이트에서 정보수정 시 \n자동으로 업데이트 됩니다.");
	return false;
}

function openMyTeam(teamNum) {
	console.log(username);
	
	window.open("/myteam/" + teamNum,
		"top=50, left=50", "toolbar=no,menubar=no");
}