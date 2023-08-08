<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- 로그인 한 회원 정보 사용 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberEntity" var="member" />
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>스터디 모집 등록</title>
 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/36.0.0/classic/ckeditor.js"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>
	<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous"
  />
    <link rel="stylesheet" type="text/css" href="/css/studyregistration.css" />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

    <div class="main-wrap">
      <div class="index-ingredient">
        <form
          action="/studyregistrationpro"
          name="studyregistration_form"
          method="post"
          enctype="multipart/form-data"
        >
          <p>
            <label for="studytitle">제목</label>
            <input
              type="text"
              id="studyTitle"
              name="studyTitle"
              value=""
              placeholder="제목 입력"
            />
          </p>
			<p>
				<label for="studyWriter">작성자</label>
				<input type="text" id="studyWriter" name="studyWriter" value="${member.nickName}" readonly="readonly">
			</p>
          <div>
            <label for="studyContent">내용</label>
            <textarea
              id="studyContent"
              name="studyContent"
              style="outline: none"
            ></textarea>
          </div>

          <!-- 이미지 첨부시 미리보기 -->
          <p>
            <label for="file">첨부파일</label>
            <input type="file" id="file" name="file" placeholder="파일등록" />
          </p>

          <!-- 이미지 임시출력 -->
          <div class="select_img">
            <img src="" />
          </div>
          <!-- //이미지 첨부시 미리보기 -->

          <p>
            <label for="address_name">지번 주소</label>
            <input
              type="text"
              id="address_name"
              name="address_name"
              readonly="readonly"
              placeholder="지도를 클릭해주세요."
            />
          </p>

          <!--  도로명 주소 -->
          <div id="road_address"></div>

          <!-- 카카오 맵 지도 -->
          <div class="map_wrap" align="center">
            <div
              id="map"
              style="
                width: 100%;
                height: 500px;
                position: relative;
                overflow: hidden;
              "
            ></div>
            <div id="menu_wrap" class="bg_white">
              <div class="option">
                <div>
                  <input
                    type="text"
                    id="keyword"
                    size="15"
                    placeholder="검색어를 입력해주세요."
                    onkeydown="return preventSubmit(event)"
                  />
                  <button
                    type="button"
                    id="map_btn"
                    onclick="searchPlaces(); return false;"
                  >
                    검색하기
                  </button>
                </div>
              </div>
              <hr />
              <ul id="placesList"></ul>
              <div id="pagination"></div>
            </div>
            <div class="map_detail">
              <div id="clickLatlng-marker"></div>
            </div>
          </div>
          <br />
          <p>
            <label for="reserveDate">예약 날짜</label>
            <input
              type="date"
              id="reserveDate"
              name="reserveDate"
              min="2023-07-01"
              onchange="selectDateHandler(this.value)"
            />
            <!-- action="action_page.php?reserve=2023-06-16" -->
            <!-- <button
              type="button"
              class="btn btn-dark"
              id="reserveBtn"
              onclick="location.href='action_page.php?' + $('#reserve').val()"
            >
              조회
            </button> -->
          </p>
          <div class="reserve-info">
            <table
              id="reserve-info-table"
              align="center"
              cellpadding="3"
              border="1"
            >
              <tbody>
                <tr>
                  <th>시간</th>
                  <th>예약 정보</th>
                </tr>
                <tr class="enabled" data-time="08">
                  <td>08:00 ~ 11:00</td>
                  <td>가능</td>
                </tr>
                <tr class="enabled" data-time="11">
                  <td>11:00 ~ 14:00</td>
                  <td>가능</td>
                </tr>
                <tr class="enabled" data-time="14">
                  <td>14:00 ~ 17:00</td>
                  <td>가능</td>
                </tr>
                <tr class="enabled" data-time="17">
                  <td>17:00 ~ 20:00</td>
                  <td>가능</td>
                </tr>
                <tr class="enabled" data-time="20">
                  <td>20:00 ~ 23:00</td>
                  <td>가능</td>
                </tr>
              </tbody>
            </table>
            <div id="accordion">
              <div class="card">
                <div class="card-header" id="headingOne">
                  <h5 class="mb-0">
                    <button
                      type="button"
                      class="btn"
                      data-toggle="collapse"
                      data-target="#collapse1"
                      aria-expanded="true"
                      aria-controls="collapseOne"
                    >
                      날짜
                    </button>
                  </h5>
                </div>
                <div
                  id="collapse1"
                  class="collapse show"
                  data-parent="#accordion"
                >
                  <div class="card-body">
                    <div id="accordion-date"></div>
                  </div>
                </div>
                <div class="card-header" id="headingOne">
                  <h5 class="mb-0">
                    <button
                      type="button"
                      class="btn"
                      data-toggle="collapse"
                      data-target="#collapse2"
                      aria-expanded="true"
                      aria-controls="collapseOne"
                    >
                      시간 선택
                    </button>
                  </h5>
                </div>
                <div
                  id="collapse2"
                  class="collapse show"
                  data-parent="#accordion"
                >
                  <div class="card-body">
                    <div class="d-flex flex-wrap gap-3">
                      <div class="accordion-study-time enabled" data-time="08">
                        08:00 ~ 11:00
                      </div>
                      <div class="accordion-study-time enabled" data-time="11">
                        11:00 ~ 14:00
                      </div>
                      <div class="accordion-study-time enabled" data-time="14">
                        14:00 ~ 17:00
                      </div>
                      <div class="accordion-study-time enabled" data-time="17">
                        17:00 ~ 20:00
                      </div>
                      <div class="accordion-study-time enabled" data-time="20">
                        20:00 ~ 23:00
                      </div>
                    </div>
                  </div>
                </div>
                <div class="card-header" id="headingTwo">
                  <h5 class="mb-0">
                    <button
                      type="button"
                      class="btn collapsed"
                      data-toggle="collapse"
                      data-target="#collapse3"
                      aria-expanded="false"
                      aria-controls="collapseTwo"
                    >
                      인원
                    </button>
                  </h5>
                </div>
                <div
                  id="collapse3"
                  class="collapse show"
                  data-parent="#accordion"
                >
                  <div class="card-body">
                    <div class="d-flex justify-content-between">
                      <label for="numOfMember"> 인원 선택 </label>
                      <div>
                        <input
                          type="number"
                          id="numOfMember"
                          name="numOfMember"
                          min="2"
                          max="8"
                          value="2"
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- hidden 데이터 -->
          <input type="hidden" id="latitude" name="latitude" />
          <input type="hidden" id="longitude" name="longitude" />
          <input type="hidden" id="reserveTime" name="reserveTime" />
          <button
            class="btn btn-dark"
            id="registerBtn"
            type="button"
            onclick="regis_check();"
          >
            등록
          </button>
        </form>
      </div>
    </div>

    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=59e92bd7943f48f31ff73b322a4e5603&libraries=services,clusterer,drawing"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
      crossorigin="anonymous"
    ></script>
    <script src="/js/studyregistration.js"></script>
  </body>
</html>
