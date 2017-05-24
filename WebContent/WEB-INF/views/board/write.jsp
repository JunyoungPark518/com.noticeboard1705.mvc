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
<div ><h1>작성 페이지</h1></div>
<div class="row">
	<form action="${context}/board.do" name="insert_item" method="post" ENCTYPE="multipart/form-data" >
		<input type="text" name="writer" style="margin-top:20px"  class="form-control" placeholder="작성자 이름" />
		<input type="text" name="title" style="margin-top:20px" class="form-control"  placeholder="제목" /><br />
	    <div class="row">
		    <div style="width:97%; margin:10px auto" >
	      		<textarea class="form-control" rows="10" id="comment"></textarea>
		    </div>
	    </div>
	    <a href="#test-form"><input type="button" class="btn btn-warning" style="float:right;width:100px;" value="FILE"/></a>
	    <input type="reset" class="btn btn-danger" style="float:right;width:100px;margin-right:10px" value="CANCEL"/>
	    <input type="submit" class="btn btn-primary" id="file-upload" style="float:right;width:100px;margin-right:10px" value="SUBMIT"/>
	    <input type="hidden" name="action" value="upload" />
    </form>
</div>
</div>
<form id="modal" class="mfp-hide white-popup">

	<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAjCAYAAADmOUiuAAADhklEQVR42u2YSWgUQRSGE/cFcUlc8CBuKIgoSkT04sEEUYmiBwVxARc8ikY96GECIoKCh0GQQXEyg7NvDKMRFJkMKmqC5uIhigtCcI0bxiUaM/4lr8Kz0tU43T0ZBRse3V2v+tXXtfz1usvK/h//5lH+V9GEQqHJsVjMFY1Gm2GduM7D3uM6F4/H96VSqTElAcvn8+UAOQj7RFDCemBf2L2wDsBu6Vc4NDgQDZ9nUAnY8kwmM4L8o9F7tTg3MdD6foHzeDyD0XhYNAqAD5FIZIVZfdTbDesiyK1FhwNUkhp7C9Aqg94dopah3nZ65k3R5qRo2AwOZXWw58IP30P07Gbl+av0bJ3jcG63eygCX6AGXgNgAV8saNzD5ppcND2ot4G9wHoqb3IUrrGx8Te4cDg8n8MBQsJ9Buhq6q0DNEfvyLrBYHCiHGYnh3U47DI19gIw86TP5XINQPk5avQjhrTaAKZDeVFR1lUUONgcDgfYBgkH3zJlURynuZhj8aZQ/Ze24fx+/0gEylLAZxxO0cA+cCg7Sr5v3Ice3kYve8k2HBPYdsy5WQpcSGogbIkCd0IOI3zreI/jvpV8Oy3DpdPpUQhwzQhOaCDKIoXCka+efE/F1LEDd0MGwvyZrhPoRCKxSAFwk+8rhrLWQB/FdtiNGDWW4IS6I8At9pbTuAbiPmUi0L1w8K3iPsDuJ7gfiLHDMpxIlaiRR7ifqhNo9NxCJZs5LTUQtlKBO8R6zhpcMpmsQIAWCSekQPq8Xu+wPxVoXFcrEnWYfN2WEwSCkyvrAYfjGih0i8ORBp4lX6cKxxaELbgJEg4g90VW7IRAo+wI+b7Dt9EO3D0CaONwXANVONJAn8nuccw2HB6cJOHEWcBaEWicl+o0UGQtlj9uxHAawQkNRA9ct7h7nNQJdEHJJgLcpUCtyDYqdQINmZmh7B4JpoGLdRqINtbYyUz2UKDHHI408LaRQJul96SBp3QCXfAhtY5nuKpAq7sHytIsg64y0UB7cAT461s1EAiMZb2aMxJodfdQNRBlZ3QCbRvQ5/NVMMArYrEYCPRFAnjF4ShOg+NwuiEuJIOmNL6S9tY+GuhE+t67SHA9XpOkauFYnBr06txifdM2E8AT2CYBChuHsrVMvNtRNrskP3toF2lRfuxwa0OdmSX9VZbNZgdhHu4SH88iS4G9A9RN5G57LafiNo6fpCgIPXePxwYAAAAASUVORK5CYII=" alt="" />
	
	<h4 style="display:inline">ATTACH FILES</h4> <span style="margin-left:100px"><a class="popup-modal-dismiss" href="#">Close</a></span>	
	<input type="file" style="margin:10px auto"/>
<input type="submit" class="btn btn-primary" style="float:right;width:100px;margin-right:10px" value="SUBMIT"/>
</form>
</body>
<script>
$('.btn-warning').magnificPopup({
	type: 'inline',
	preloader: false,
	focus: '#name',
	items: {src: '#modal'},
	modal: true
});
$(document).on('click', '.popup-modal-dismiss', function (e) {
	e.preventDefault();
	$.magnificPopup.close();
});
$(document).on('click', '#file-upload', function (e) {
	e.preventDefault();
	$.magnificPopup.close();
});

</script>
</html>