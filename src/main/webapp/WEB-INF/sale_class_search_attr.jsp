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
function search_attr_up(shxm_id,shxzh_id,shxm_mch,shxzh,shxzh_mch){
	
		var a = "<div id='search_shxmch_"+shxm_id+"'> <span class='gt'>&gt</span> <div  class='filter_div'> ";
		var b ="<input type='hidden' name='attr_param' value='{\"shxm_id\":"+shxm_id+",\"shxzh_id\":"+shxzh_id+"}'></input>"
		var c ="<a href='javascript:search_attr_down("+shxm_id+");'>"+shxm_mch+":"+shxzh+shxzh_mch+"</a> ";
		var d = "</div> </div>";
		
		$("#select_attr").append(a+b+c+d);
		$("#search_id_"+shxm_id).hide();
		search_attr();
	}
	
	function search_attr_down(shxm_id){
		$("#search_shxmch_"+shxm_id).remove();
		$("#search_id_"+shxm_id).show();
		$("."+shxm_id).removeAttr("checked");
		search_attr();
	}
	
	
	function search_attr(){
		var class_2_id=${class_2_id};
		var param={class_2_id:class_2_id};
		$(":input[name='attr_param']").each(function(i,json){
			var obj = $.parseJSON(json.value);
			param["list_av["+i+"].shxm_id"]=obj.shxm_id;
			param["list_av["+i+"].shxzh_id"]=obj.shxzh_id;
		});
		
		$.ajax({
			url : "sale_search_attr.do",
			type : "post",
			data : param,
			success : function(data){
				$("#sku_list").html(data);
			}
			
		});
	}
	
	
</script>
<title>硅谷商城</title>
</head>
<body>

	<div class="filter">
		<div class="filter_div">
			${keywords }
		</div>
		<span class="gt">&gt</span>
		<div id="select_attr">
		
		</div>
		<div class="filter_clear">
			清空筛选
		</div>
				
	</div>
	
	<div class="Sscreen">
		<div class="title">
			${keywords } 商品筛选 共${list_sku.size() }个商品
		</div>
		<div class="list">
			<span>品牌：</span>
			<c:forEach items="${sku_spu_tm_value}" var="sku_spu_tm">
				<a href="">${sku_spu_tm.tm.ppmch}</a>
			</c:forEach>
		</div>
		<div class="list">
			<c:forEach items="${ attr_list}" var="attr">
				<div id="search_id_${attr.id }">
					<span>${attr.shxm_mch }：</span> 
					<c:forEach items="${ attr.attr_value_list}" var="attr_value">
					 	<a id="${attr_value.id }" href="javascript:search_attr_up(${attr.id },${attr_value.id},'${attr.shxm_mch }','${attr_value.shxzh }','${attr_value.shxzh_mch }');">${attr_value.shxzh }${attr_value.shxzh_mch }</a>
					</c:forEach>
				 </div>
			</c:forEach>
		</div>
		
		<div class="list">
			<span class="list_span" id="list_beas">销量</span>
			<span class="list_span">价格</span>
			<span class="list_span">评论数</span>
			<span class="list_span">上架时间</span>
		</div>
	</div>
	
	
</body>
</html>