<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>최초접속페이지</title>
<link rel="stylesheet" href="/css/index.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/pageingredient/header.jsp"></jsp:include>
	<div class="main-wrap">
		<div class="main-ranking">
			<h1>Ranking</h1>
			<table>
				<thead>
					<tr>
						<th>순위</th>
						<th>팀이름</th>
						<th>인증횟수</th>
						<th>포인트</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td class="team-name">여기를</td>
						<td class="auth-count">1</td>
						<td class="team-point">1</td>
					</tr>
					<tr>
						<td>2</td>
						<td class="team-name">디자인</td>
						<td class="auth-count">2</td>
						<td class="team-point">2</td>
					</tr>
					<tr>
						<td>3</td>
						<td class="team-name">해야합니다</td>
						<td class="auth-count">3</td>
						<td class="team-point">3</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="index-ingredient">
			<div class="index main-qna">
				<label>
					<a href="/qna">Q &amp; A</a>
				</label>
				<c:forEach items="${qnaList }" var="qnaList" begin="0" end="4">
					<div>
						<a href="/studydetail/${qnaList.qnaNum }">${qnaList.qnaTitle }</a>
						<span style="float: right;">
							<font size="2"><fmt:formatDate value="${qnaList.qnaDate }" pattern="yyyy-MM-dd" /></font>
						</span>
					</div>
				</c:forEach>
			</div>

			<div class="index main-study">
				<label>
					<a href="/study">스터디모집</a>
				</label>
				<c:forEach items="${studyList }" var="study" begin="0" end="4">
					<div>
						<a href="/studydetail/${study.studyNum }">${study.studyTitle }</a>
						<span style="float: right;">
							<font size="2">
							<fmt:parseDate var="date" value="${study.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" />
							<fmt:formatDate var="dateTime" value="${date}" type="DATE" pattern="yyyy-MM-dd" />
							${dateTime }
							</font>
						</span>
					</div>
				</c:forEach>
			</div>

			<div class="index main-team">
				<label>
					<a href="/team/teamboards">팀 신청</a>
				</label>
				<c:forEach items="${teamBoardList }" var="list" begin="0" end="4">
					<div>
						<a href="${contextPath }/team/teamboard/${list.teamBoardNum}">${list.teamBoardTitle }</a>
						<span style="float: right;">
							<font size="2"><fmt:formatDate value="${list.createDate }" pattern="yyyy-MM-dd" /></font>
						</span>
					</div>
				</c:forEach>
			</div>

			<div class="index main-auth">
				<label>
					<a href="">인증게시판</a>
				</label>
				<div>
					<a href="#">팀등록 1</a>
				</div>
				<div>
					<a href="#">팀등록 1</a>
				</div>
				<div>
					<a href="#">팀등록 1</a>
				</div>
				<div>
					<a href="#">팀등록 1</a>
				</div>
			</div>

		</div>
	</div>
	<jsp:include page="/WEB-INF/views/pageingredient/footer.jsp"></jsp:include>
</body>
</html>