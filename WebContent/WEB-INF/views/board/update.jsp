<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="context" value="<%=application.getContextPath() %>"/>
<!doctype html>
<html lang="en">
<head>
	<title>Board</title>
	<meta charset="UTF-8" />
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel= "stylesheet" href= "https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.0.0/magnific-popup.min.css" />
	<style>
	.white-popup {position: relative;background: #FFF;padding: 10px;width:360px;height:150px; max-width: 500px;margin: 20px auto;}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src= "https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.0.0/jquery.magnific-popup.min.js" ></script >
</head>
<body>
<div class="container-fluid" style="width:80%">
<div><h1>수정 페이지</h1></div>
		<input type="text" name="writer" style="margin-top:20px"  class="form-control" placeholder="작성자 이름" />
		<input type="text" name="title" style="margin-top:20px" class="form-control"  placeholder="제목" /><br />
	    <div class="row">
		    <div style="width:97%; margin:10px auto" >
	      		<textarea class="form-control" rows="20" id="comment"></textarea>
		    </div>
	    </div>
	   
	<a href="${context}/board.do?action=move&pageName=upload"><input type="submit" class="btn btn-warning" style="float:right;width:100px;margin-right:10px" value="UPLOAD"/></a>
	<a href="${context}/board.do?action=update"><input type="submit" class="btn btn-primary" style="float:right;width:100px;margin-right:10px" value="UPDATE"/></a>
</div>

</body>
</html>