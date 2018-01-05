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
	$(function(){
		$.getJSON("js/json2/class_1.js",function(data){
			$.each(data,function(i,json){
				$("#class_1_select_spu2").append("<li onmouseover='spu_get_class_22(this.value)' value="+json.id+" ><a href=''>"+json.flmch1+"</a></li>");
			})
		})
		
	});
	
	
	function spu_get_class_22(class1){
		$.getJSON("js/json2/class_2_"+class1+".js",function(data){
			$("#class_2_select_spu2").empty();
			$.each(data,function(i,json){
				$("#class_2_select_spu2").append("<li value="+json.id+" ><a href='goto_class_search.do?class_2_id="+json.id+"&class_2_name="+json.flmch2+"'>"+json.flmch2+"</a></li>");
			});
		});
	}
	
</script>
<title>硅谷商城</title>
</head>
<body>
	
	<div class="menu">
		<div class="nav">
			<div class="navs">
				<div class="left_nav">
					全部商品分类
					<div class="nav_mini">
						<ul id="class_1_select_spu2" name="flbh1">
							<li>
								<div id="class_2_select_spu2" name="flbh2" class="two_nav">
								
								</div>
							</li>
						</ul>
					</div>
				</div>
				<ul>
					<li><a href="">服装城</a></li>
					<li><a href="">美妆馆</a></li>
					<li><a href="">超市</a></li>
					<li><a href="">全球购</a></li>
					<li><a href="">闪购</a></li>
					<li><a href="">团购</a></li>
					<li><a href="">拍卖</a></li>
					<li><a href="">金融</a></li>
					<li><a href="">智能</a></li>
				</ul>
			</div>
		</div>
	</div>
	
</body>
</html>