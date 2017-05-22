<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="context" value="<%=application.getContextPath() %>"/>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8"/>
	<title>index</title>
	<style>
	.index-button{
	padding: 5px;
	background-color: #4c55ce;
	color: white;
	font-size: 10px;
	}
	</style>
</head>
<body>
	<a href="${context}/board.do?action=list&pageName=list&pageNumber=1">보드로 가기</a>
</body>
</html>