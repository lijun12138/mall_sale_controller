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
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/css.css"/>
		<link rel="stylesheet" type="text/css" href="css/finishCart.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function b(){}
</script>
<title>硅谷商城</title>
</head>
<body>
	<jsp:include page="sale_header.jsp"></jsp:include>
	
	<jsp:include page="sale_search_area.jsp"></jsp:include>
	
	
	<div class="cartPro">
		<span class="dec">该商品已加入购物车</span>
	</div>
		
</body>
</html>