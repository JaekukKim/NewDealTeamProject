window.resizeTo(1000,600);

function getOutTeam(data) {

	let teamNumber = $("#teamNumber").val();
	
	let teamHead = $("#teamHead").val();
	
	if(teamHead === data){
		alert ("팀장은 내보낼 수 없습니다.");
		
		return false;
	}
	
	console.log(teamNumber);
	$.ajax({
		type: "post",
		url: "/team/getoutteam",
		data: {
			"username": data,
			"teamNumber": teamNumber
		},
		dataType: "json",
		success: function(data) {
			alert("내보내기 성공");
			location.reload();
		}
	})
}