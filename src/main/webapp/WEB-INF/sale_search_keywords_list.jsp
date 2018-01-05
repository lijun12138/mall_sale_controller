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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function b(){}
</script>
<title>硅谷商城</title>
</head>
<body>
	
	
	
	<div class="Sbox">
		<c:forEach items="${list_sku}" var="sku_spu_tm">
			<div class="list">
				<div class="img"><img src="upload/image/${sku_spu_tm.shp_tp}" alt="" width="280px" height="150px"></div>
				<div class="price">¥${sku_spu_tm.jg}</div>
				<div class="price">¥${sku_spu_tm.kc}</div>
				<div class="price">¥${sku_spu_tm.sku_xl}</div>
				<div class="title"><a href="sku_detail.do?sku_id=${sku_spu_tm.id}&spu_id=${sku_spu_tm.shp_id}">库存名称：${sku_spu_tm.sku_mch}</a></div>
			</div>
		</c:forEach>
	</div>
	
</body>
</html>