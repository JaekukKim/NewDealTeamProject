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
    <link rel="stylesheet" type="text/css" href="/css/authregistration.css" />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>

    <div class="main-wrap">
      <div class="index-ingredient">
        <form action="/auth/registrationpro" name="resultAuthregistration_form" method="post" enctype="multipart/form-data">
          <p>
            <label for="resultAuthTitle">제목</label>
            <input type="text" id="resultAuthTitle" name="resultAuthTitle" placeholder="제목 입력"/>
          </p>

		  <p>
		  	<label for="resultAuthWriter">작성자</label>
			<input type="text" id="resultAuthWriter" name="resultAuthWriter" value="${member.nickName}" readonly="readonly">
		  </p>
         
          <div>
            <label for="resultAuthContent">내용</label>
            <textarea id="resultAuthContent" name="resultAuthContent" style="outline: none"></textarea>
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

          <button class="btn btn-dark" id="registerBtn" type="button" onclick="regis_check();">등록</button>
        </form>
      </div>
    </div>
    <script src="/js/authregistration.js"></script>
  </body>
</html>
