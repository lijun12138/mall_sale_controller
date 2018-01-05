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
function search_attr_up(shxm_id,shxzh_id,shxm_mch,shxzh,shxzh_mch){
	
		var a = " <span id='search_shxmch_"+shxm_id+"' style='cursor:pointer'>";
		var b ="<input type='hidden' name='attr_param' value='{\"shxm_id\":"+shxm_id+",\"shxzh_id\":"+shxzh_id+"}'></input>"
		var c ="<a href='javascript:search_attr_down("+shxm_id+");'>"+shxm_mch+":"+shxzh+shxzh_mch+"</a> ";
		var d = "</span>";
		
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
	
	
	function select_match(attr_id){
		$(":input[name='box']").each(function(i,n){
			$(this).attr("style","display:none");
		})
		$("."+attr_id).attr("style","");
	}
	
	function abolish(attr_id){
		$("."+attr_id).removeAttr("checked");
		$("."+attr_id).attr("style","display:none");
	}
	
	function submit_much_attr_val(shxm_mch,shxm_id){
		var tmp="";
		var attr_val="";
		
		
		$("input:checked").each(function(i,n){
			var shxzh_id = $(this).val();
			var string = shxzh_id.split("_");
			tmp+=string[2]+string[1]+" ";
		});
		
		var a = " <span id='search_shxmch_"+shxm_id+"' style='cursor:pointer'>";
		var b ="<input type='hidden' name='attr_param' ></input>";
		
		var c ="<a href='javascript:search_attr_down("+shxm_id+");'>"+shxm_mch+":"+tmp+"</a> ";
		var d = "</span>";
		
		$("#select_attr").append(a+b+c+d);
		$("#search_id_"+shxm_id).hide();
		
		var class_2_id=${class_2_id};
		var param={class_2_id:class_2_id};
		$.ajax({
			url : "sale_search_attr.do",
			type : "post",
			data : param,
			success : function(data){
				$("#sku_list").html(data);
			}
			
		});
		
	}
	function search_attr_much(){
		
	}
</script>
<title>硅谷商城</title>
</head>
<body>
	
	
	${class_2_id }${class_2_name }商品属性列表区<br>
	<hr>
	品牌:<c:forEach items="${sku_spu_tm_value}" var="sku_spu_tm">
			${sku_spu_tm.tm.ppmch}
	</c:forEach>
	<div id="select_attr">
		
	</div>
	<br>
	<c:forEach items="${ attr_list}" var="attr">
		<div id="search_id_${attr.id }">
			${attr.shxm_mch } : 
			<c:forEach items="${ attr.attr_value_list}" var="attr_value">
			 	<input name="box" value="${attr_value.id }_${attr_value.shxzh_mch }_${attr_value.shxzh }" class="${attr.id }" type="checkbox" style="display: none" ><a id="${attr_value.id }" href="javascript:search_attr_up(${attr.id },${attr_value.id},'${attr.shxm_mch }','${attr_value.shxzh }','${attr_value.shxzh_mch }');">${attr_value.shxzh }${attr_value.shxzh_mch }</a>
			</c:forEach>
			<a href="javascript:select_match(${attr.id });">多选</a><br>
			<button name="box" onclick="submit_much_attr_val('${attr.shxm_mch }',${attr.id })" class="${attr.id }" style="display: none">确定</button><button name="box" onclick="abolish(${attr.id })" class="${attr.id }" style="display: none">取消</button>
		 </div>
	</c:forEach>
</body>
</html>