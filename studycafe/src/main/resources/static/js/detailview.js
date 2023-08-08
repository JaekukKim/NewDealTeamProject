$(document).ready(function() {
	csReplyList();
});

/**
 *  팀 등록 글 조회
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
		const toolbarElement = editor.ui.view.toolbar.element;

		toolbarElement.style.display = 'none';

		editor.enableReadOnlyMode('#content');
	})
	.catch(error => {
		console.error(error);
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

// 게시글 삭제
function removePost(idx, identifier) {

	let askRemove = confirm("게시글을 정말 삭제하시겠습니까?");

	if (askRemove == true) {

		if (identifier === "tb") {
			$.ajax({
				url: identifying(identifier).tb,
				type: "DELETE",
				data: {
					"idx": idx
				},
				dataType: "json",

				success: function(result) {

					if (result == true) {
						alert("삭제가 완료되었습니다.");

						location.href = identifying(identifier).tbHref;
					}
				},
				error: function(error) {

					alert("에러가 발생하였습니다.");
				}
			})

		} else if (identifier === "cs") {

			$.ajax({
				url: identifying(identifier).cs,
				type: "DELETE",
				data: {
					"idx": idx
				},
				dataType: "json",

				success: function(result) {

					if (result == true) {
						alert("삭제가 완료되었습니다.");

						location.href = identifying(identifier).csHref;
					}
				},
				error: function(error) {

					alert("에러가 발생하였습니다.");
				}
			})
		}

	}

}

function identifying(identifier) {

	let tbArr = {
		tb: "/team/removepost",
		tbHref: "/team/teamboards?page=0"
	};

	let csArr = {
		cs: "/cs/removepost",
		csHref: "/cs/csboard?page=0"
	};

	if (identifier === "tb") {
		return tbArr;

	} else if (identifier === "cs") {
		return csArr;
	}
}

/** 
 * 댓글 입력, 삭제, 수정 Ajax
 * */

var comment_counts = {}; // 대댓글 클릭 카운트
var modify_counts = {}; // 댓글 수정 클릭 카운트

function csReplyInsert(index, ref) {
	var jsonData = {};

	if (ref != null) { // 대댓글
		jsonData = {
			csEntity: {
				idx: $("#idx").val()
			},
			csReplyWriter: $('#nickName').val(),
			csReplyContent: $('#re_comment_' + index).val(),
			csReplyDepth: 1,
			csReplyRef: ref
		};
	} else { // 일반 댓글
		jsonData = {
			csEntity: {
				idx: $('#idx').val()
			},
			csReplyWriter: $('#nickName').val(),
			csReplyContent: $('#comment').val(),
			csReplyDepth: 0
		};
	}

	$.ajax({
		url: "/csReplyInsert"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			if (data.status === "ok") {
				modify_counts[index]++;
				comment_counts[index]++;
				$('#comment').val('');
				csReplyList();
			} else {
				alert("접근 경로가 잘 못 되었습니다.");
			}
		},
		error: function(errer) {
			console.log("에러 : " + errer);
		}
	});
}

function csReplyDelete(id) {

	var jsonData = {
		csReplyNum: id
	};

	$.ajax({
		url: "/csReplyDelete"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			if (data.status === "ok") {
				alert("댓을을 삭제했습니다.");
				csReplyList();
			} else {
				alert("접근 경로가 잘 못 되었습니다.");
			}
		},
		error: function(errer) {
			console.log("에러 : " + errer);
		}
	});
}

function csReplyList() {
	var jsonData = {
		csEntity: {
			idx: $('#idx').val()
		}
	};

	$.ajax({
		url: "/csReplyList"
		, contentType: "application/json; charset=utf-8"
		, type: "POST"
		, data: JSON.stringify(jsonData)
		, success: function(data) {
			var postList = $('#reply-list');

			postList.empty(); // #reply-list 비우기

			// 댓글이 있을 경우
			if (data.length) {
				$.each(data, function(index, post) {
					var day = new Date(post.csReplyDate);

					if (post.csEntity.csWriter === post.csReplyWriter) {
						img = "/img/user_writer.png"; // 글쓴이 이미지
					} else {
						img = "/img/user.png"; // 일반 이미지
					}

					// 댓글 깊이 (대댓글 일 경우)
					if (post.csReplyDepth === 1) {
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
						'<li class="nickname" id="nickname_' + index + '">' + post.csReplyWriter + '</li>' +
						'<li class="date">&nbsp;&nbsp;' + dateFormat(day) + '</li>' +
						'</ul>' +
						'<p class="text" id="reply_content_' + index + '">' + post.csReplyContent + '</p>' +
						'<ul class="control">'
					if ($('#nickName').val() != null)
						htmls += !!post.csReplyDepth ? '' : '<li><a href="#" onclick="re_comment_open(' + index + ', ' + post.csReplyRef + '); return false;" class="link_reply"><i class="fa fa-comment"></i>답변달기</a>';
					if ($('#nickName').val() === post.csReplyWriter)
						htmls += '<li><a href="#" onclick="comment_modify_open(' + index + ', ' + post.csReplyRef + '); return false;" class="link_reply"><i class="fa-solid fa-pencil"></i>수정</a>' +
							'<li><a href="#" onclick="csReplyDelete(' + post.csReplyNum + '); return false;" class="link_reply"><i class="fa-solid fa-trash-can"></i>삭제</a>'
					htmls += '</ul>' + 
						'<input type="hidden" id="replyNum_' + index + '" value="' + post.csReplyNum + '">' +
						'<input type="hidden" id="csReplyWriter_' + index + '" value="' + post.csReplyWriter + '">' +
						'<input type="hidden" id="csReplyRef_' + index + '" value="' + post.csReplyRef + '">' +
						'<input type="hidden" id="csReplyDepth_' + index + '" value="' + post.csReplyDepth + '">' +
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
			'<button class="btn btn-dark" id="re_commentBtn_' + index + '" type="button" onclick="csReplyInsert(' + index + ', ' + ref + ');">작성</button>'
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
			'<button class="btn btn-dark" id="modify_commentBtn_' + index + '" type="button" onclick="csModify(' + index + ')">수정</button>'
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
