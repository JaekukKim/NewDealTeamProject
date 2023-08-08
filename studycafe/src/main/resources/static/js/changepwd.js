function changePassword() {
	var password = $("#password").val();
	var rePassword = $("#rePassword").val();
	var oneraepassword = $("#oneraepassword").val();
	var passwordRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
	if (password == "" || password.trim() == "") {
		alert("비밀번호를 입력해주세요.");
		return false;
	} else if (password != rePassword) {
		alert("비밀번호가 다릅니다 다시 입력해주세요.");
		return false;
	} else if (!passwordRegExp.test(password)) {
		alert("8~15자의 영문,숫자,특수문자 조합해서 설정해주세요.");
		return false;
	}

	$.ajax({
		type: "post",
		url: "/checkPassword",
		data: {
			"oneraepassword" : oneraepassword,
		},
		dataType: "json",
		success: function(data) {
			
			if (data == true) {
				$.ajax({
					type:"post",
					url:"/changePassword",
					data:{
						"password":password
					},
					dataType:"json",
					success: function(data){
						if (data == true){
							alert("비밀번호가 변경되었습니다.");
							location.href="/logout";
						} else{
							alert("기존 비밀번호와 같습니다. 다르게 설정해주세요.");
						}
					}
				})
			} else {
				alert("현재 비밀번호가 다릅니다. 다시 한 번 확인해주세요.");
			}
		}
	})
}