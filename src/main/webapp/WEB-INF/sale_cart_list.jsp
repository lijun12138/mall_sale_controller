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
<link rel="stylesheet" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function change_status(checked,sku_id){
		var shfxz='0';
		if(checked){
			shfxz='1';
		}
		$.ajax({
			url : "change_status.do",
			type : "post",
			data : {shfxz:shfxz,sku_id:sku_id},
			success : function(data){
				$("#cart_list").html(data);
			}
		})
	}
	function delete_cart(sku_mch,sku_id){
		if(confirm("确认删除"+sku_mch+"吗？")){
			$.ajax({
				url : "delete_cart.do",
				type : "post",
				data : {sku_id:sku_id},
				success : function(data){
					$("#cart_list").html(data);
				}
			})
		};
		
	}
	
	function change_tjshl(tjshl,sku_id){
		$.ajax({
			url : "change_tjshl.do",
			type : "post",
			data : {tjshl:tjshl,sku_id:sku_id},
			success : function(data){
				$("#cart_list").html(data);
			}
		})
	}
	
</script>
<title>硅谷商城</title>
</head>
<body>

	<jsp:include page="sale_header.jsp"></jsp:include>
	
	<jsp:include page="sale_search_area.jsp"></jsp:include>
	
	<div id="cart_list" class="Cbox">
		<jsp:include page="sale_cart_list_inner.jsp"></jsp:include>
	</div>
</body>
</html>