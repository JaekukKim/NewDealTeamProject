document.addEventListener("keyup", function () {
  var teamname = $("#userName").val();
  // 아이디 중복체크 버튼
  if (teamname == "" || teamname.trim() == "") {
    $("#userNameCheck").attr("disabled", true);
  } else {
    $("#userNameCheck").attr("disabled", false);
  }
});

// 유저 검색
function searchMember() {
  var username = $("#userName").val();
  $.ajax({
    type: "post",
    url: "/member/searchUser",
    data: {
      username: username,
    },
    dataType: "json",
    success: function (data) {
      var table = $("#user-table").find("tbody");
      table.find("tr:gt(0)").remove();
      if (data.length > 0) {
        data.forEach((element) => {
		  var string;
		  if(element.teamNumber === 0){
			 string = `<tr><td>${element.username}</td><td>${element.nickName}</td><td>${element.teamNumber}</td><td class="success-box"><span>가능</span></td></tr>`;
			 var dom = $("<div/>").html(string).contents();
		 
			 dom.on("click", function () {
			   $("#userSelectDiv").show();
			   $("#userSelect").val($(this).find("td:first-child").text());
			 });
		  }
		  else {
			 string = `<tr><td>${element.username}</td><td>${element.nickName}</td><td>${element.teamNumber}</td><td class="error-box"><span>불가능</span></td></tr>`;
			 var dom = $("<div/>").html(string).contents();
		  }
        
          table.append(dom);
        });
      } else {
        table.append(`<tr><td colspan="3">검색결과가 없습니다.</td></tr>`);
      }
    },
    error: function () {
      alert("알수없는에러");
    },
  });
}

function addUser() {
  var memberName = $("#userSelect").val();
  var memEl = opener.document.getElementById("team-member");
  var tokens = memEl.value.split(",");
  var exists = false;
  tokens.forEach((token) => {
    if (token.trim() === memberName) {
      alert("이미 추가된 유저입니다.");
      exists = true;
    }
  });
  if (!exists) {
    memEl.value += ", " + memberName;
    window.close();
  }
}
