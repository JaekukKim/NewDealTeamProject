<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authentication property="principal.memberEntity" var="member" />

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 이름 추가</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="/css/teamregis.css">

</head>
<body>
	<div class="main-wrap">
		<div class="index-ingredient">
			<div>
				<div>
                    <div>
                        <label for="userName">아이디를 검색하세요</label>
                    </div>
					<input type="text" id="userName" name="userName" placeholder="유저 검색" />
                </div>
				<button type="button" id="userNameCheck" name="userNameCheck" onclick="searchMember()" disabled>검색</button>

                <table id="user-table">
                    <tbody>
                        <tr>
                            <th>
                                아이디
                            </th>
                            <th>
                                닉네임
                            </th>
                            <th>
                                팀
                            </th>
                            <th>
                                추가
                            </th>
                        </tr>
                    </tbody>
                </table>
                <div id="userSelectDiv">
                    <div>
                        <label for="userName">선택된 아이디</label>
                    </div>
					<input type="text" id="userSelect" name="userSelect" placeholder="유저 선택" readonly="readonly" />
                    <div>
                        <button type="button" id="addUserBtn" name="addUserBtn" onclick="addUser()">유저 추가하기</button>
                    </div>
                </div>
				
                
			</div>

			
		</div>
	</div>
	<script src="/js/teamMemberRegis.js"></script>
</body>
</html>