$(document).ready(function() {
	studyReplyList();
});
/** 
 * 게시글 삭제 Ajax
 * */
function studyDelete() {

	var jsonData = {
		id: $('#qnaNum').val()
	};

	$.ajax({
		url: "/qnaDelete"
		, contentType: "application/json; charset=utf-8"
		, type: 'POST'
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			if (data.status === 'ok') {
				alert(jsonData.id + "번 게시글을 삭제했습니다.");
				location.href = "/qna";
			} else {
				alert("접근 경로가 잘 못 되었습니다.");
			}
		},
		error: function(errer) {
			console.log("에러 : " + error);
		}
	});
}

/** 
 * 댓글 입력, 삭제, 수정 Ajax
 * */

var comment_counts = {}; // 대댓글 클릭 카운트
var modify_counts = {}; // 댓글 수정 클릭 카운트

function studyReplyInsert(index, ref) {
	var jsonData = {};

	if (ref != null) { // 대댓글
		jsonData = {
			qnaEntity: {
				qnaNum: $('#qnaNum').val()
			},
			qnaReWriter: $('#nickName').val(),
			qnaReContent: $('#re_comment_' + index).val(),
			qnaReDepth: 1,
			qnaReRef: ref
		};

		alert("대댓글" + JSON.stringify(jsonData));
	} else { // 일반 댓글
		jsonData = {
			qnaEntity: {
				qnaNum: $('#qnaNum').val()
			},
			qnaReWriter: $('#nickName').val(),
			qnaReContent: $('#comment').val(),
			qnaReDepth: 0
		};
	}

	$.ajax({
		url: "/qnaReInsert"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			if (data.status === "ok") {
				modify_counts[index]++;
				comment_counts[index]++;
				$('#comment').val('');
				studyReplyList();
			} else {
				alert("접근 경로가 잘 못 되었습니다.");
			}
		},
		error: function(errer) {
			console.log("에러 : " + errer);
		}
	});
}

function studyReplyDelete(id) {

	var jsonData = {
		qnaReNum: id
	};

	alert("댓글댓글" + JSON.stringify(jsonData));

	$.ajax({
		url: "/qnaReDelete"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			if (data.status === "ok") {
				studyReplyList();
			} else {
				alert("접근 경로가 잘 못 되었습니다.");
			}
		},
		error: function(errer) {
			console.log("에러 : " + errer);
		}
	});
}

function studyModify(index) {
	var jsonData = {
		qnaEntity: {
			qnaNum: $('#qnaNum').val()
		},
		qnaReNum: $('#replyNum_' + index).val(),
		qnaReContent: $('#modify_comment_' + index).val(),
		qnaReWriter: $('#studyReplyWriter_' + index).val(),
		qnaReRef: $('#studyReplyRef_' + index).val(),
		qnaReDepth: $('#studyReplyDepth_' + index).val()
	};

	alert("댓글댓글" + JSON.stringify(jsonData));


	$.ajax({
		url: "/qnaReModify"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			if (data.status === "ok") {
				modify_counts[index]++;
				comment_counts[index]++;
				studyReplyList();
			} else {
				alert("접근 경로가 잘 못 되었습니다.");
			}
		},
		error: function(errer) {
			console.log("에러 : " + errer);
		}
	});
}


//댓글 리스트
function studyReplyList() {
	var jsonData = {
		qnaEntity: {
			qnaNum: $('#qnaNum').val()
		}
	};

	//alert(JSON.stringify(jsonData));
	//alert(jsonData.qnaEntity.qnaNum); // qnaNum 값만 확인
	//alert(jsonData.qnaEntity); // qnaEntity 객체 확인
	//alert(jsonData); // 전체 jsonData 확인

	$.ajax({
		url: "/qnaReList"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			var postList = $('#reply-list');

			postList.empty(); // #reply-list 비우기

			// 댓글이 있을 경우
			if (data.length) {
				$.each(data, function(index, post) {
					var day = new Date(post.qnaReDate);

					if (post.qnaEntity.qnaWriter === post.qnaReWriter) {
						img = "/img/user_writer.png"; // 글쓴이 이미지
					} else {
						img = "/img/user.png"; // 일반 이미지
					}

					// 댓글 깊이 (대댓글 일 경우)
					if (post.qnaReDepth === 1) {
						imgClass = "thumb_re" // 이미지 클래스
						contentClass = "reply-content reply-re_content"; // 댓글 클래스
					} else {
						imgClass = "thumb"
						contentClass = "reply-content";
					}
					htmls = '';
					htmls = '' +
						'<li>' +
						'<div class="' + imgClass + '">' +
						'<img src="' + img + '" width="48" height="48" class="">' +
						'</div>' +
						'<div class="' + contentClass + '">' +
						'<ul class="info">' +
						'<li class="nickname" id="nickname_' + index + '">' + post.qnaReWriter + '</li>' +
						'<li class="date">&nbsp;&nbsp;' + dateFormat(day) + '</li>' +
						'</ul>' +
						'<p class="text" id="reply_content_' + index + '">' + post.qnaReContent + '</p>' +
						'<ul class="control">'
					if ($('#nickName').val() != null)
						htmls += !!post.qnaReDepth ? '' : '<li><a href="#" onclick="re_comment_open(' + index + ', ' + post.qnaReRef + '); return false;" class="link_reply"><i class="fa fa-comment"></i>답변달기</a>';
					if ($('#nickName').val() === post.qnaReWriter)
						htmls += '<li><a href="#" onclick="comment_modify_open(' + index + ', ' + post.qnaReRef + '); return false;" class="link_reply"><i class="fa-solid fa-pencil"></i>수정</a>' +
							'<li><a href="#" onclick="studyReplyDelete(' + post.qnaReNum + '); return false;" class="link_reply"><i class="fa-solid fa-trash-can"></i>삭제</a>'
					htmls += '</ul>' +
						'<input type="hidden" id="replyNum_' + index + '" value="' + post.qnaReNum + '">' +
						'<input type="hidden" id="studyReplyWriter_' + index + '" value="' + post.qnaReWriter + '">' +
						'<input type="hidden" id="studyReplyRef_' + index + '" value="' + post.qnaReRef + '">' +
						'<input type="hidden" id="studyReplyDepth_' + index + '" value="' + post.qnaReDepth + '">' +
						'<div id="re_comment_open_' + index + '"></div>' +
						'<div id="comment_modify_open_' + index + '"></div>' +
						'</div>' +
						'</li>'
					postList.append(htmls);
				});
			}
		},
		error: function(errer) {
			console.log("에러 : " + errer);
		}
	});
}

// 대댓글 창
function re_comment_open(index, ref) {
	if (!comment_counts[index]) {
		comment_counts[index] = 0;
	}

	var comment = $('#re_comment_open_' + index);
	var modify = $('#comment_modify_open_' + index);

	if (comment_counts[index] % 2 === 0) {
		if (modify_counts[index] % 2 !== 0) {
			modify_counts[index] = 0;
			modify.empty(); // 댓글 수정 창 초기화
		}

		comment.append(
			'<textarea class="comment" id="re_comment_' + index + '" rows="5" placeholder="코멘트 달기"></textarea>' +
			'<button class="btn btn-dark" id="re_commentBtn_' + index + '" type="button" onclick="studyReplyInsert(' + index + ', ' + ref + ');">작성</button>'
		);
	} else {
		comment.empty();
	}

	comment_counts[index]++;
}

// 댓글 수정 창
function comment_modify_open(index, ref) {
	if (!modify_counts[index]) {
		modify_counts[index] = 0;
	}

	var comment = $('#re_comment_open_' + index);
	var modify = $('#comment_modify_open_' + index);

	if (modify_counts[index] % 2 === 0) {
		if (comment_counts[index] % 2 !== 0) {
			comment_counts[index] = 0;
			comment.empty(); // 대댓글 창 초기화
		}

		modify.append(
			'<textarea class="comment" id="modify_comment_' + index + '" rows="5" placeholder="' + $("#reply_content_" + index).text() + '"></textarea>' +
			'<button class="btn btn-dark" id="modify_commentBtn_' + index + '" type="button" onclick="studyModify(' + index + ')">수정</button>'
		);
	} else {
		modify.empty();
	}

	modify_counts[index]++;
}

function dateFormat(date) {
	let month = date.getMonth() + 1;
	let day = date.getDate();
	let hour = date.getHours();
	let minute = date.getMinutes();
	let second = date.getSeconds();

	month = month >= 10 ? month : '0' + month;
	day = day >= 10 ? day : '0' + day;
	hour = hour >= 10 ? hour : '0' + hour;
	minute = minute >= 10 ? minute : '0' + minute;
	second = second >= 10 ? second : '0' + second;

	return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute;
}

/** 
 * CK 에디터 
 * */

/* 이부분 불러오기 p태그 확인*/
var myEditor;

ClassicEditor
	.create(document.querySelector('#qnaContent'), {
		ckfinder: {
			uploadUrl: '/ck/teamregisimgupload' // 내가 지정한 업로드 url (post로 요청감)
		},
		removePlugins: ['Heading'],
		language: "ko"
	})
	.then(editor => {
		const toolbarElement = editor.ui.view.toolbar.element;

		toolbarElement.style.display = 'none';

		editor.enableReadOnlyMode('#qnaContent');
	})
	.catch(error => {
		console.error(error);
	});

// 이미지 미리보기
$("#file").change(function() {
	if (this.files && this.files[0]) {
		var reader = new FileReader;
		reader.onload = function(data) {
			$(".select_img img").attr("src", data.target.result).width(350).height(350);
		}
		reader.readAsDataURL(this.files[0]);
	}
});