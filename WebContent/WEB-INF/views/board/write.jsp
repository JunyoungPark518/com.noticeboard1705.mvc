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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid" style="width:80%">
<div ><h1>작성 페이지</h1></div>
<div class="row">
	<form action="${context}/control/item.do" name="insert_item" method="post">
		<input type="text" name="writer" style="margin-top:20px"  class="form-control" placeholder="작성자 이름" />
		<input type="text" name="title" style="margin-top:20px" class="form-control"  placeholder="제목" /><br />
	    <div class="row">
		    <div class="col-sm-3 col-md-6 col-lg-4" >
		      
	      		<textarea class="form-control" rows="10" id="comment"></textarea>
		    </div>
		    <div class="col-sm-9 col-md-6 col-lg-8" >
		    	<div style="width:100%;height:200px;border:1px solid gray"></div>
				<input type="file" />		      
		    </div>
	    </div>
	    <button onclick="moveTo('write')" class="btn btn-danger" style="float:right;width:100px">CANCEL</button>
		<button onclick="moveTo('write')" class="btn btn-primary" style="float:right;width:100px;margin-right:50px">SUBMIT</button>
		<input type="hidden" name="action" value="write" />
		<input type="hidden" name="page" value="detail"/>
    </form>
</div>
</div>
</body>
<script>
function write(){
	
}
function fileUpload(){
	
}
</script>
</html>