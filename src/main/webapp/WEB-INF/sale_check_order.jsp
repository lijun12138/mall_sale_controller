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
	function select_address(address_id,shjr){
		$("#check_order_div").html("收货地址：<input type='hidden' id='address' value='"+address_id+"' name='address_id'>"+shjr);
	}
	
	function save_order(){
		var address_id=$("#address").val();
		
		window.location.href="save_order.do?address_id="+address_id;
	}
	
</script>
<title>硅谷商城</title>
</head>
<body>
	<jsp:include page="sale_header.jsp"></jsp:include>
	
	<jsp:include page="sale_search_area.jsp"></jsp:include>
	
	<div class="message">
			<div class="msg_title">
				收货人信息
			</div>
			<div class="msg_addr">
			<c:forEach items="${addresses }" var="address">
				<div class="msg_addr">
					<span class="msg_left">
						姓名 ${address.shjr}
					</span>
					<span class="msg_right">
						<input type="radio" onclick="select_address(${address.id},'${address.shjr}${address.yh_dz}')" name="a" />${address.yh_dz}
					</span>
				</div>
			</c:forEach>
				
			</div><br>
			<span class="addrs">查看更多地址信息</span>
			<div class="msg_line"></div>
		
			<div class="msg_title">
				送货清单
			</div>
			<c:forEach items="${order.flow_list }" var="flow">
				<div class="msg_list">
				<div class="msg_list_left">
					配送方式
					<div class="left_title">
						${flow.psfsh }
					</div>
				</div>
				<c:forEach items="${flow.list_info }" var="info">
					<div class="msg_list_right">
						<div class="msg_img">
							<img width="70px" src="upload/image/${info.shp_tp }"/>
						</div>
						<div class="msg_name">
							${info.sku_mch }
						</div>
						<div class="msg_price">
							￥${info.sku_jg }
						</div>
						<div class="msg_mon">
							*${info.sku_shl }
						</div>
						<div class="msg_state">
							有货
						</div>
					</div>
				</c:forEach>	
				</div>	
			</c:forEach>
			<div class="msg_line"></div>
		
			<div class="msg_sub">
				<div class="msg_sub_tit">
					应付金额：
					<b>￥${order.zje }</b>
				</div>
				<div class="msg_sub_adds"  id="check_order_div">
				</div>
				<button class="msg_btn" onclick="save_order()">提交订单</button>
			</div>
		
		</div>
	
	<jsp:include page="sale_footer.jsp"></jsp:include>
	
</body>
</html>