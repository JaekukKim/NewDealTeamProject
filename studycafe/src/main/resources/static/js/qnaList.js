function qnaRegister() {
	let username = $("#username").val();
	if (username == null || username == "") {
		alert("로그인 후 이용가능합니다.");
		return false;
	}
	location.href = "/qnaRegister"
}

$('#search').keydown(function(key) {
	if (key.keyCode == 13) {
		$('#searchBtn').click();
	}
});

$(document).ready(function() {

	loadPosts($('#search').val(), $(this).data('page'));

	$('#searchBtn').on('click', function() {
		loadPosts($('#search').val(), 0); // 검색어가 바뀌면 첫 페이지부터 검색 결과를 보여줍니다.
	});

	$(document).on('click', '.pagination-link', function() {
		loadPosts($('#search').val(), $(this).data('page'));
	});
});

// 게시글 리스트 불러오기
function loadPosts(keyword, page) {
	var paramData = {
		"keyword": keyword,
		"page": page
	};

	$.ajax({
		url: "/qnalist"
		, contentType: "charset=UTF-8"
		, type: 'GET'
		, data: paramData
		, success: function(data) {
			var postList = $('#postList');

			postList.empty(); // #postList 비우기

			// 게시글 내용이 없을 경우
			if (!data.content.length) {
				if (keyword) {
					postList.append(
						'<tr>' +
						'<td colspan="4">검색된 게시글이 없습니다.</td>' +
						'</tr>'
					);
				} else {
					postList.append(
						'<tr>' +
						'<td colspan="4">게시글이 없습니다.</td>' +
						'</tr>'
					);
				}
			} else {
				$.each(data.content, function(index, post) {
					var day = new Date(post.dateTime);

					postList.append(
						'<tr>' +
						'<td>' + post.qnaNum + '</td>' +
						'<th>' +
						'<a href="/qnaDetail/' + post.qnaNum + '">' + post.qnaTitle + '</a>' +
						'</th>' +
						'<td>' + post.qnaWriter + '</td>' +
						/*' <td>' + day.getFullYear() + '.' + (day.getMonth() + 1) + '.' + day.getDate() + '</td>' +*/
						' <td>' + formatDate(post.qnaDate) + '</td>' +
						'</tr>'
					);
				});
			}
			currentPage = data.number;
			renderPagination(data.totalPages);
		}
		, error: function(error) {
			console.log("에러 : " + error);
		}
	});
}

function formatDate(dateStr) {
	// dateStr을 Date 객체로 변환
	var date = new Date(dateStr);

	// 원하는 형식으로 날짜 정보 추출
	var year = date.getFullYear();
	var month = String(date.getMonth() + 1).padStart(2, '0');
	var day = String(date.getDate()).padStart(2, '0');

	// "YYYY-MM-DD" 형식으로 반환
	return year + '.' + month + '.' + day;
}

// 페이징 처리
function renderPagination(totalPages) {
	var pagination = $('#pagination');

	pagination.empty(); // #pagination 비워주기

	for (var i = 0; i < totalPages; i++) {
		var pageNum = i + 1;
		if (i === currentPage) {
			pagination.append('<span class="pagination-link current-page">' + pageNum + '</span>');
		} else {
			pagination.append('<span class="pagination-link" data-page="' + i + '">' + pageNum + '</span>');
		}
	}
}