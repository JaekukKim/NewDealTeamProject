/**
 * 
 */

// Enter 키 방지
function preventSubmit(event) {
    if (event.key === "Enter") {
        searchPlaces();
        return false;
    }
}

// 유효성 검사
function regis_check() {
	var studytitle = $('#studyTitle');
	var studyContent = myEditor.getData();
	
	
	if (studytitle.val() == "") {
		alert("제목을 입력해주세요.");
		studytitle.focus();
		return false;
	}
	
	if (!studyContent.trim()) {
        alert('내용을 입력해주세요.');
        myEditor.focus();
        return;
    }
    
	
	
	document.studymodify_form.submit();
}

// ck에디터
var myEditor;
ClassicEditor
	.create(document.querySelector('#studyContent'), {
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
	});

// 이미지 미리보기 아직안씀
$("#file").change(function() {
	if (this.files && this.files[0]) {
		var reader = new FileReader;
		reader.onload = function(data) {
			$(".select_img img").attr("src", data.target.result).width(350).height(350);
		}
		reader.readAsDataURL(this.files[0]);
	}
});

// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center: new kakao.maps.LatLng($('#latitude').val(), $('#longitude').val()), // 지도의 중심좌표
		level: 3
		// 지도의 확대 레벨
	};

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({
	zIndex: 1
});

let selectLong, selectLat;

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

	var keyword = document.getElementById('keyword').value;

	if (!keyword.replace(/^\s+|\s+$/g, '')) {
		alert('키워드를 입력해주세요!');
		return false;
	}

	// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
	ps.keywordSearch(keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
	if (status === kakao.maps.services.Status.OK) {

		// 정상적으로 검색이 완료됐으면
		// 검색 목록과 마커를 표출합니다
		displayPlaces(data);

		// 페이지 번호를 표출합니다
		displayPagination(pagination);

	} else if (status === kakao.maps.services.Status.ZERO_RESULT) {

		alert('검색 결과가 존재하지 않습니다.');
		return;

	} else if (status === kakao.maps.services.Status.ERROR) {

		alert('검색 결과 중 오류가 발생했습니다.');
		return;

	}

}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

	var listEl = document.getElementById('placesList'), menuEl = document
		.getElementById('menu_wrap'), fragment = document
			.createDocumentFragment(), bounds = new kakao.maps.LatLngBounds(), listStr = '';

	// 검색 결과 목록에 추가된 항목들을 제거합니다
	removeAllChildNods(listEl);

	// 지도에 표시되고 있는 마커를 제거합니다
	removeMarker();

	for (var i = 0; i < places.length; i++) {

		// 마커를 생성하고 지도에 표시합니다
		var placePosition = new kakao.maps.LatLng(places[i].y,
			places[i].x), marker = addMarker(placePosition, i), itemEl = getListItem(
				i, places[i]); // 검색 결과 항목 Element를 생성합니다

		// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		// LatLngBounds 객체에 좌표를 추가합니다
		bounds.extend(placePosition);

		// 마커와 검색결과 항목에 mouseover 했을때
		// 해당 장소에 인포윈도우에 장소명을 표시합니다
		// mouseout 했을 때는 인포윈도우를 닫습니다
		(function(marker, title) {
			kakao.maps.event.addListener(marker, 'mouseover',
				function() {
					displayInfowindow(marker, title);
				});

			kakao.maps.event.addListener(marker, 'mouseout',
				function() {
					infowindow.close();
				});

			kakao.maps.event.addListener(marker, 'click',
				function() {
					searchDetailAddrFromCoords(marker.getPosition(), function(result, status) {
						let markerCoordinate = marker.getPosition();
						let lat = markerCoordinate.getLat();
						let lng = markerCoordinate.getLng();
		
						if (status === kakao.maps.services.Status.OK) {
							document.getElementById('latitude').value = lat; // 위도
							document.getElementById('longitude').value = lng; // 경도
							document.getElementById('address_name').value = result[0].address.address_name; // 지번주소
						}
						
						road_address = !!result[0].road_address ?
							'<p><label for="road_address_name">도로명 주소</label>' + 
							'<input type="text" id="road_address_name" value="'+ result[0].road_address.address_name + '" readonly="readonly"><br></p>' : '';
				
						var resultDiv = document.getElementById('road_address');
						
						resultDiv.innerHTML = road_address;
						
						selectLong = lng;
						selectLat = lat;
						
						myMarker.setMap(null); // 선택한 마커 삭제
					});
				});

			itemEl.onmouseover = function() {
				displayInfowindow(marker, title);
			};

			// item list 클릭시 정보 출력
			itemEl.onclick = function() {
				searchDetailAddrFromCoords(marker.getPosition(), function(result, status) {
					let lat = marker.getPosition().getLat(); // 위도
	        		let lng = marker.getPosition().getLng(); // 경도
	
					if (status === kakao.maps.services.Status.OK) {
						document.getElementById('latitude').value = lat; // 위도
						document.getElementById('longitude').value = lng; // 경도
						document.getElementById('address_name').value = result[0].address.address_name; // 지번주소
					}
					
					road_address = !!result[0].road_address ?
						'<p><label for="road_address_name">도로명 주소</label>' + 
						'<input type="text" id="road_address_name" value="'+ result[0].road_address.address_name + '" readonly="readonly"><br></p>' : '';
			
					var resultDiv = document.getElementById('road_address');
					
					resultDiv.innerHTML = road_address;
					
					selectLong = lng;
					selectLat = lat;
				
				
					myMarker.setMap(null); // 선택한 마커 삭제
				});
			};

			itemEl.onmouseout = function() {
				infowindow.close();
			};
		})(marker, places[i].place_name);

		fragment.appendChild(itemEl);
	}

	// 검색결과 항목들을 검색결과 목록 Element에 추가합니다
	listEl.appendChild(fragment);
	menuEl.scrollTop = 0;

	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
	map.setBounds(bounds);
}

// 마커 클릭시 스터디 모집 예약 시간을 확인하는 함수입니다.
function getStudyDateAvailability(lat, long, date){
		let httpRequest = new XMLHttpRequest();
		let result;
		if (!httpRequest) {
		alert("ajax 요청을 만드는데 실패하였습니다.");
			return false;
		}

		httpRequest.onreadystatechange = function () {
		try {
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (this.readyState == 4 && httpRequest.status === 200) {
				result = JSON.parse(httpRequest.responseText);
				updateTable(result);
			} else {
				alert("httpRequest 요청에 문제가 있습니다.");
			}
			}
		} catch (e) {
			alert(`에러가 발생하였습니다: ${e.description}`);
		}
		};
		
		let url = `/studyTime?lat=${lat}&long=${long}&date=${date}`;
		httpRequest.open("GET", url);
		httpRequest.send();
		
}

// 예약 날짜 선택시 핸들링 함수
function selectDateHandler(date) {
	if (!selectLat || !selectLong) {
		alert("먼저 장소를 선택해주세요");
		$('#reserveDate').val('');
		return;
	}
	else {
		getStudyDateAvailability(selectLat, selectLong, date );
		document.getElementById("accordion-date").innerHTML = date;
	}
	
}

// 예약 가능 테이블을 업데이트 하는 함수.
function updateTable(times){
	times.forEach((t)=>{
		const time = Number(t.getHours());
		const rows = document.querySelectorAll('#reserve-info-table tr');
		rows.forEach((row)=>{
			const data = parseInt(row.dataset.time);
			if(data === time){
				row.addClass("disabled");
				row.removeClass("enabled");
				row.children[1].innerHTML = "예약 불가능";
			}
		});
	} );
	document.getElementById("reserve-info-table").style.display = "block";
	document.getElementById("accordion").style.display = "block";
}



// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {

	var el = document.createElement('li'),
		itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>'
			+ '<div class="info">'
			+ '   <h5>' + places.place_name + '</h5>';

	if (places.road_address_name) {
		itemStr += '    <span>' + places.road_address_name + '</span>'
			+ '   <span class="jibun gray">' + places.address_name
			+ '</span>';
	} else {
		itemStr += '    <span>' + places.address_name + '</span>';
	}

	itemStr += '  <span class="tel">' + places.phone + '</span>'
		+ '</div>';

	el.innerHTML = itemStr;
	el.className = 'item';

	return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
	var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
		imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기

		imgOptions = {
			spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
			spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
			offset: new kakao.maps.Point(13, 37)
			// 마커 좌표에 일치시킬 이미지 내에서의 좌표
		}, markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize,
			imgOptions), marker = new kakao.maps.Marker({
				position: position, // 마커의 위치
				image: markerImage
			});

	marker.setMap(map); // 지도 위에 마커를 표출합니다
	markers.push(marker); // 배열에 생성된 마커를 추가합니다

	return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
	var paginationEl = document.getElementById('pagination'), fragment = document
		.createDocumentFragment(), i;

	// 기존에 추가된 페이지번호를 삭제합니다
	while (paginationEl.hasChildNodes()) {
		paginationEl.removeChild(paginationEl.lastChild);
	}

	for (i = 1; i <= pagination.last; i++) {
		var el = document.createElement('a');
		el.href = "#";
		el.innerHTML = i;

		if (i === pagination.current) {
			el.className = 'on';
		} else {
			el.onclick = (function(i) {
				return function() {
					pagination.gotoPage(i);
				}
			})(i);
		}

		fragment.appendChild(el);
	}
	paginationEl.appendChild(fragment);
}

// 테스트
// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(markers, title) {
	var content = '<div id="marker_div">' + title
		+ '</div>';

	// 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
	infowindow.setContent(content);
	infowindow.open(map, markers);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
	while (el.hasChildNodes()) {
		el.removeChild(el.lastChild);
	}
}

// 지도를 클릭한 위치에 표출할 마커입니다
var myMarker = new kakao.maps.Marker({
	// 지도 중심좌표에 마커를 생성합니다 
	position: map.getCenter()
});

// 지도에 마커를 표시합니다
myMarker.setMap(map);

searchDetailAddrFromCoords(myMarker.getPosition(), function(result, status) {
	markerCoordinate = myMarker.getPosition();
	selectLong = markerCoordinate.getLat();
	selectLat = markerCoordinate.getLng();
						
	if (status === kakao.maps.services.Status.OK) {
		document.getElementById('address_name').value = result[0].address.address_name; // 지번주소
	}
	
	road_address = !!result[0].road_address ?
		'<p><label for="road_address_name">도로명 주소</label>' + 
		'<input type="text" id="road_address_name" value="'+ result[0].road_address.address_name + '" readonly="readonly"><br></p>' : '';

	var resultDiv = document.getElementById('road_address');
	
	resultDiv.innerHTML = road_address;
});


kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
	searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
		// 지도에 마커를 표시합니다    
		myMarker.setMap(map);
		// 클릭한 위도, 경도 정보를 가져옵니다 
		var latlng = mouseEvent.latLng;

		// 마커 위치를 클릭한 위치로 옮깁니다
		myMarker.setPosition(latlng);

		let lat = latlng.getLat(); // 위도
		let lng = latlng.getLng(); // 경도

		if (status === kakao.maps.services.Status.OK) {
			document.getElementById('latitude').value = lat; // 위도
			document.getElementById('longitude').value = lng; // 경도
			document.getElementById('address_name').value = result[0].address.address_name; // 지번주소
		}
		
		road_address = !!result[0].road_address ?
			'<p><label for="road_address_name">도로명 주소</label>' + 
			'<input type="text" id="road_address_name" value="'+ result[0].road_address.address_name + '" readonly="readonly"><br></p>' : '';

		var resultDiv = document.getElementById('road_address');
		
		resultDiv.innerHTML = road_address;
		
		selectLong = lng;
		selectLat = lat;
		
		infowindow.close(); // 인포윈도우를 지도에서 제거합니다
	});
	
});

function searchDetailAddrFromCoords(coords, callback) {
	// 좌표로 법정동 상세 주소 정보를 요청합니다
	geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

document.addEventListener("DOMContentLoaded", function () {
	// 지난 날짜 선택 불가
	document.getElementById("reserveDate").min = new Date().toISOString().split('T')[0];
  
	// 스터디 타임 이벤트 리스너 추가
	var times = document.getElementsByClassName("accordion-study-time");
	for (var i = 0; i < times.length; i++) {
	  times[i].addEventListener("click", function(){
		  var selectedEl = document.querySelector(".accordion-study-time.selected");
		  if(selectedEl){
			  selectedEl.classList.remove("selected");
		  }
		  this.classList.add("selected");
		  var hour = this.dataset.time;
		  var selectDate = document.getElementById("reserveDate").value;
		  const date = new Date(selectDate);
		  date.setHours(hour);
		  document.getElementById("reserveTime").value = date.toISOString();
	  });
	  }	
  });