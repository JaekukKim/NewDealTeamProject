
document.addEventListener("keyup", function() {
	var username = $("#username").val();

	var usernameRegExp = /^[a-zA-z0-9]{6,12}$/;

	// 아이디 중복체크 버튼
	if (username == "" || username.trim() == "" || !usernameRegExp.test(username)) {
		$("#idCheckBtn").attr("disabled", true);
	} else {
		$("#idCheckBtn").attr("disabled", false);
	}

	// 닉네임 중복체크 버튼
	if ($("#nickName").val().trim() != "") {
		$("#nickCheckBtn").attr("disabled", false);
	} else {
		$("#nickCheckBtn").attr("disabled", true);
	}
	// 이메일 본인인증 버튼
	if ($("#email1").val().trim() != "") {
		$("#emailCheckBtn").attr("disabled", false);
	} else {
		$("#emailCheckBtn").attr("disabled", true);
	}

});
// 아이디 중복 체크
function idCheck() {
	var username = $("#username").val();
	$("#notUsername").css("display", "none");
	$("#notvaliUsername").css("display", "none");
	$("#notIdCheck").css("display", "none");
	$("#useUsername").css("display", "none");
	$.ajax({
		type: "post",
		url: "/idCheck",
		data: {
			"username": username
		},
		dataType: "json",
		success: function(e) {
			if (e == true) {
				$("#useUsername").css("display", "none");
				$("#notUseUsername").css("display", "block");
			} else {
				$("#useUsername").css("display", "block");
				$("#notUseUsername").css("display", "none");
				$("#idCheck").val("1");
			}
		},
		error: function() {
			alert("알수없는에러");
		}
	});
}
// 닉네임 중복 체크
function nickCheck() {
	var nickName = $("#nickName").val();
	$("#notUsername").css("display", "none");
	$("#notvaliUsername").css("display", "none");
	$("#notIdCheck").css("display", "none");
	$.ajax({
		type: "post",
		url: "/nickCheck",
		data: {
			"nickName": nickName
		},
		dataType: "json",
		success: function(e) {
			if (e == true) {
				$("#useNick").css("display", "none");
				$("#notUseNick").css("display", "block");
			} else {
				$("#notNickCheck").css("display", "none");
				$("#useNick").css("display", "block");
				$("#notUseNick").css("display", "none");
				$("#nickCheck").val("1");
			}
		},
		error: function() {
			alert("알수없는에러");
		}
	});
}
// 이메일 인증번호 전송
function sendEmail() {
	var email = $("#email1").val() + $("#email2").val();

	if (emailCheck(email)) {
		alert("이미 등록되어 있는 이메일입니다. 다른 이메일을 입력해주세요");
		return false;
	}

	alert(email + "로 이메일이 발송되었습니다");
	$("#notEmailAuth").css("display", "none");
	$("#emailCheckBtn").css("display", "none");
	$("#emailCheck").css("display", "block");
	$("#emailReCheckBtn").css("display", "block");

	$.ajax({
		type: "POST",
		url: "/send",
		data: {
			"toEmail": email
		},
		success: function(data) {
			if (data != null) {
				$("#emailReCheckBtn").on("click", function() {
					var emailCheck = $("#emailCheck").val();
					if (emailCheck == data) {
						alert("인증에 성공하였습니다.");
						$("#emailCheck").css("display", "none");
						$("#emailReCheckBtn").css("display", "none");
						$("#emailAuth").val("1");
					} else {
						alert("인증에 실패하였습니다.");
					}
				});
			}
		},
		error: function(error) {
			alert("메세지 전송 실패: " + error);
		}
	});
}

function emailCheck(email) {

	$.ajax({
		type: "post",
		url: "emailCheck",
		data: {
			"email": email
		},
		dataType: "json",
		succeess: function(data) {
			if (data) {
				return true;
			}
			return false;
		}
	})
}
// 회원가입
function join() {

	var username = $("#username").val();
	var password = $("#password").val();
	var email = $("#email1").val() + $("#email2").val();
	var nickName = $("#nickName").val();
	var name = $("#name").val();
	var zipcode = $("#zipcode").val();
	var address1 = $("#address1").val();
	var address2 = $("#address2").val();
	var joinMethod = $("#joinMethod").val();

	var memberEntity = { username, password, email, nickName, name, joinMethod };

	var memberAddressEntity = { zipcode, address1, address2 };

	var data = {
		memberEntity: memberEntity,
		memberAddressEntity: memberAddressEntity
	};
	console.log(data);
	$.ajax({
		type: "post",
		url: "/joinPro",
		contentType: 'application/json',
		data: JSON.stringify(data),
		dataType: "json",
		success: function(e) {
			if (e == true) {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}
		},
		error: function() {
			alert("회원가입 실패");
		},
	});
}

// 유효성 검사
function checkAll() {

	var username = $("#username").val();
	var idCheck = $("#idCheck").val();
	var password = $("#password").val();
	var rePassword = $("#rePassword").val();
	var email = $("#email1").val() + $("#email2").val();
	var nickName = $("#nickName").val();
	var nickCheck = $("#nickCheck").val();
	var name = $("#name").val();
	var emailAuth = $("#emailAuth").val();
	var zipcode = $("#zipcode").val();
	var address1 = $("#address1").val();
	var joinMethod = $("#joinMethod").val();

	if (joinMethod === "NORMAL") {
		if (!checkUsername(username, idCheck)) {
			return false;
		} else if (!checkPassword(username, password, rePassword)) {
			return false;
		} else if (!checkNickName(nickName, nickCheck)) {
			return false;
		} else if (!checkName(name)) {
			return false;
		} else if (!checkMail(email)) {
			return false;
		} else if (!checkEmailAuth(emailAuth)) {
			return false;
		} else if (!checkAddress(zipcode, address1)) {
			return false;
		}

	} else {
		
		if (!checkPassword(username, password, rePassword)) {
			return false;
		} else if (!checkNickName(nickName, nickCheck)) {
			return false;
		} else if (!checkName(name)) {
			return false;
		} else if (!checkAddress(zipcode, address1)) {
			return false;
		}
	}

	return join();
}
function checkUsername(username, idCheck) {
	$("#notUsername").css("display", "none");
	$("#notvaliUsername").css("display", "none");
	$("#notIdCheck").css("display", "none");
	$("#useUsername").css("display", "none");
	if (username == "" || username.trim() == "") {
		$("#notUsername").css("display", "block");
		return false;
	}

	var usernameRegExp = /^[a-zA-z0-9]{6,12}$/;

	if (!usernameRegExp.test(username)) {
		$("#notvaliUsername").css("display", "block");
		return false;
	}
	if (idCheck != "1") {
		$("#notIdCheck").css("display", "block");
		return false;
	}
	return true;
}

function checkPassword(username, password, rePassword) {
	$("#notPwd").css("display", "none");
	$("#notValiPwd").css("display", "none");
	$("#notEqualPwd").css("display", "none");
	$("#equalUsername").css("display", "none");
	if (password == "" || password.trim() == "") {
		$("#notPwd").css("display", "block");
		return false;
	}

	var passwordRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

	if (password != rePassword || rePassword == "" || rePassword.trim() == "") {
		$("#notEqualPwd").css("display", "block");
		return false;
	}
	if (!passwordRegExp.test(password)) {
		$("#notValiPwd").css("display", "block");
		return false;
	}
	if (username == password) {
		$("#equalUsername").css("display", "block");
		return false;
	}

	return true;
}

function checkNickName(nickName, nickCheck) {
	$("#notNickName").css("display", "none");
	$("#notValiNickName").css("display", "none");
	$("#notNickCheck").css("display", "none");
	if (nickName == "") {
		$("#notNickName").css("display", "block");
		return false;
	}
	var nickNameRegExp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,12}$/;

	if (!nickNameRegExp.test(nickName)) {
		$("#notValiNickName").css("display", "block");
		return false;
	}

	if (nickCheck != "1") {
		$("#notNickCheck").css("display", "block");
		return false;
	}
	return true;
}

function checkName(name) {
	$("#notName").css("display", "none");
	$("#notValiName").css("display", "none");

	if (name == "" || name.trim() == "") {
		$("#notName").css("display", "block");
		return false;
	}
	var nameRegExp = /^[가-힣]{2,4}$/;
	if (!nameRegExp.test(name)) {
		$("#notValiName").css("display", "block");
		return false;
	}
	return true;
}

function checkMail(email) {
	$("#notEmail").css("display", "none");

	var emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

	if (!emailRegExp.test(email) || email == "" || email.trim() == "") {
		$("#notEmail").css("display", "block");

		return false;
	}

	return true;
}

function checkEmailAuth(emailAuth) {
	$("#notEmailAuth").css("display", "none");
	if (emailAuth != "1") {
		$("#notEmailAuth").css("display", "block");
		return false;
	}
	return true;
}
function checkAddress(zipcode, address1) {
	$("#notAddress").css("display", "none");
	if (zipcode == "" || zipcode.trim() == "") {
		$("#notAddress").css("display", "block");
		return false;
	} else if (address1 == "" || address1.trim() == "") {
		$("#notAddress").css("display", "block");
		return false;
	}
	return true;
}

function kakaoPost() {
	new daum.Postcode({
		oncomplete: function(data) {
			document.querySelector("#zipcode").value = data.zonecode;
			document.querySelector("#address1").value = data.address
			document.getElementById("address2").focus();
		}
	}).open();

}

function zip() {

}

