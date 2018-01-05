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
<link rel="stylesheet" type="text/css" href="css/css.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">

	$(function(){
		var yh_nch = get_cookie_value("yh_nch");
		$("#cookie").html(yh_nch);
	})
	
	function get_cookie_value(key){
		var cookies = document.cookie;
		cookies=cookies.replace(/\s/,"");
		cookies=cookies.split(";");
		for(i=0;i<cookies.length;i++){
			var cookie=cookies[i].split("=");
			if(cookie[0]==key){
				return decodeURIComponent(cookie[1]);
			}
		}
	}
</script>
<title>硅谷商城</title>
</head>
<body>

	<div class="top">
		<div class="top_text">
			<c:if test="${empty user }">
				<a><span id="cookie" style="color: red">${yh_nch }</span></a><a href="to_login.do">请登录</a>
			</c:if>
			<c:if test="${not empty user }">
				<a>${user.yh_nch }欢迎你 </a><a href="logout.do">退出系统</a>
			</c:if>
			<a href="">用户注册</a>
			<a href="">供应商登录</a>
			<a href="">供应商注册</a>
		</div>
	</div>
	
</body>
</html>