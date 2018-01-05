<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<link rel="stylesheet" type="text/css" href="css/css.css">	
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function show_cart(){
		
		$.ajax({
			url : "mini_cart.do",
			type : "post",
			data : {},
			success : function(data){
				$("#mini_cart").html(data);
			}
			
		})
		
		$("#mini_cart").show();
		
	}
	function hide_cart(){
		$("#mini_cart").hide();
		
	}
</script>
<title>硅谷商城</title>
</head>
<body>


	<div class="card">
		<a href="goto_cart_list.do" onmouseover="show_cart()" onmouseout="hide_cart()">购物车<div class="num">${list_cart.size()}</div></a>
		<!--购物车商品-->
		<div id="mini_cart">
		</div>
	</div>
	
		
</body>
</html>