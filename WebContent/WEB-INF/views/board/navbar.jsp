<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button type="button" class="home btn btn-default" aria-label="Left Align" style="width:50px;">
<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
</button>
<script>
document.querySelector(".home").addEventListener('click',function(){
	location.href='<%=application.getContextPath()%>/board.do';
});
</script>
      

  
