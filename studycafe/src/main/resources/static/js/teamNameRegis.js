document.addEventListener("keyup", function () {
  var teamname = $("#teamName").val();
  // 아이디 중복체크 버튼
  if (teamname == "" || teamname.trim() == "") {
    $("#teamNameCheck").attr("disabled", true);
  } else {
    $("#teamNameCheck").attr("disabled", false);
  }
});

// 팀이름 중복 체크
function nameCheck() {
  var teamname = $("#teamName").val();
  var pattern = /[A-Za-z0-9\uAC00-\uD79D]{3,}/;
  if (pattern.test(teamname)) {
    $("#notValid").css("display", "none");
    $.ajax({
        type: "post",
        url: "/team/nameCheck",
        data: {
          teamName: teamname,
        },
        dataType: "json",
        success: function (e) {
          if (e == true) {
            $("#teamNamePass").css("display", "none");
            $("#teamNameInUse").css("display", "block");
          } else {
            $("#teamNamePass").css("display", "block");
            $("#teamNameInUse").css("display", "none");
            $("#useTeamName").attr("disabled", false);
            $("#idCheck").val("1");
          }
        },
        error: function () {
          alert("알수없는에러");
        },
      });
  } else {
        $("#notValid").css("display", "block");
  }


}

function useTeamName() {
  var teamname = $("#teamName").val();
    opener.document.getElementById("team-name").value = teamname;
    window.close();
  
}
