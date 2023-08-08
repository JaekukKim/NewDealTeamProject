/**
 *  팀 등록 글 작성 js
 */
// ck에디터
var myEditor;
ClassicEditor
	.create(document.querySelector('#content'), {
		ckfinder: {
			uploadUrl: '/ck/teamregisimgupload' // 내가 지정한 업로드 url (post로 요청감)
		},
		removePlugins: ['Heading'],
		language: "ko"
	})
	.then(editor => {
		console.log('Editor was initialized', editor);
		myEditor = editor;
	})
	.catch(error => {
		console.error(error);
		alert("2mb 이하의 파일만 업로드 가능합니다.");
	});

// 이미지 미리보기
$("#file").change(function() {
	if (this.files && this.files[0]) {
		var reader = new FileReader;
		reader.onload = function(data) {
			$(".select_img img").attr("src", data.target.result);
		}
		reader.readAsDataURL(this.files[0]);
	}
});

function teamNameAdd() {
	window.open("/team/teamNameRegis", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
	
}
function teamMemberAdd() {
	window.open("/team/teamMemberRegis", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");

}


function validPost(){
	
	// let errorMsg = document.querySelectorAll(".error-box > span");
	
	// if(errorMsg != null || errorMsg != ""){
	// 	for(let i = 0; i < errorMsg.length; i++){
	// 		console.log(errorMsg[i].value);
	// 		alert(errorMsg[i].value);
	// 	}
	// }

	var teamTitle = $('#team-title');
	var teamName = $('#team-name');
	var teamMember = $('#team-member');

	
	if (teamTitle.val() == "") {
		alert("제목을 입력해주세요.");
		teamTitle.focus();
		return false;
	}
    
	if (teamName.val() == "") {
		alert("팀 이름을 입력해주세요.");
		teamName.focus();
		return false;
	}
	
	if (teamMember.val() == "") {
		alert("팀원을 추가해주세요.");
		teamMember.focus();
		return false;
	}
	if(teamMember.val().split(",").length < 2) {
		alert("팀을 만들기 위해서 최소한 2명의 팀원이 필요합니다.");
		teamMember.focus();
		return false;
	}

	return true;
}