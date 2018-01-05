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
	
</script>
<title>硅谷商城</title>
</head>
<style type="text/css">
	td{vertical-align: middle !important;}
	.form-group{padding: 5px 0;}
</style>
<body>

		<table class="table table-striped table-bordered table-hover">
		   <thead>
		     <tr>
		       <th>商品图片</th>
		       <th>商品名称</th>
		       <th>商品属性</th>
		       <th>商品价格</th>
		       <th>商品数量</th>
		       <th>操作</th>
		     </tr>
		   </thead>
		   <tbody>
		   <c:forEach items="${list_cart}" var="cart">
			    <tr>
			       <td><input type="checkbox" onclick="change_status(this.checked,${cart.sku_id})" ${cart.shfxz=='1'?'checked':'' } ><img src="upload/image/${cart.shp_tp }" alt="" style="width: 50px;height: 50px" ></td>
			       <td>${cart.sku_mch }</td>
			       <td>
			       		颜色：<span style='color:#ccc'>白色</span><br>
			       		尺码：<span style='color:#ccc'>L</span>
			       </td>
			       <td>${cart.sku_jg }</td>
			       <td><input type="text" name="min" value="${cart.tjshl }" onchange="change_tjshl(this.value,${cart.sku_id})" style="width:50px;text-align:center"></td>
			       <td><a href="javascript:delete_cart('${cart.sku_mch }',${cart.sku_id });">删除</a></td>
			     </tr>
		     </c:forEach>
		     <c:if test="${empty list_cart}">
				<label style="color: red">你的购物车空空如也。。。</label>
			</c:if>
		   </tbody>
	 	</table>

	
	<div class="Cprice">
		<div class="price">总价：
			${total_price }
		</div>
		<div class="jiesuan"><a href="goto_check_order.do?total_price=${total_price }">结算</a></div>
	</div>
	
	<jsp:include page="sale_footer.jsp"></jsp:include>
</body>
</html>