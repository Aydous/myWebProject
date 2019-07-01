<%@ page language="java" import="java.util.*"  pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%String path=request.getContextPath();%>
<html>
<head>
<title>Insert title here</title>
<style type="text/css">
tr{height:25px}
</style>
<script type="text/javascript">
var count=0;
function onSelect(vardel) 
{
	vardel?count++:count--;
	var del=document.getElementById("del");
	del.disabled=(count==0);
}
function onEdit(vaab101)
{
	var vform=document.getElementById("myform");
	vform.action="<%=path%>/findEmpById.html?aab101="+vaab101;
	vform.submit();
}
function onDel(vaab101)
{
	var vform=document.getElementById("myform");
	vform.action="<%=path%>/delEmpById.html?aab101="+vaab101;
	vform.submit();
}
</script>
</head>
${msg}
<body>
<br>
<br>
<form id="myform" action="<%=path %>/queryEmp.html" method="post">

<table border="1" width="95%" align="center">
<caption>
员工管理
<hr width="160" >
</caption>
<!-- 查询填充块 -->
<tr>
<td colspan="4">查询条件</td>
</tr>
<tr>
	<td>姓名</td>
	<td><e:text name="qaab102" value="${param.qaab102 }"/></td>
	<td>编号</td>
	<td><e:text name="qaab103" value="${param.qaab103 }"/></td>
</tr>
<tr>
	<td>性别</td>
	<td><e:radio name="qaab105" 
	value="男:1,女:2,不确定:3,不限定:''" defval="${param.qaab105 }"/></td>
	<td>民族</td>
	<td><e:select name="qaab106" header="true" defval="${param.qaab106 }"
	value="汉族:01,满族:02,蒙族:03,藏族:04,哈萨克族:05"/></td>
</tr>
<tr>
	<td>生日[B]</td>
	<td><e:date name="baab104" value="${param.baab104 }"/></td>
	<td>生日[E]</td>
	<td><e:date name="eaab104" value="${param.eaab104 }"/></td>
</tr>
</table>
<table border="1" width="95%" align="center">
<!-- 查询数据显示块 -->
<tr>
	<td></td>
	<td>序号</td>
	<td>姓名</td>
	<td>编号</td>
	<td>生日</td>
	<td>性别</td>
	<td>民族</td>
	<td>手机号码</td>
	<td>电子邮件</td>
	<td></td>
</tr>
<c:choose>
<c:when test="${rows!=null}">
<c:forEach items="${rows }" var="ins" varStatus="vs">
<tr>
<td><input type="checkbox" name="idlist" 
value="${ins.aab101}" onclick="onSelect(this.checked)"></td>
<td>${vs.count}</td>
<td><a href="#" onclick="onEdit('${ins.aab101}')">${ins.aab102 }</a></td>
<td>${ins.aab103 }</td>
<td>${ins.aab104 }</td>
<td>${ins.cnaab105 }</td>
<td>${ins.cnaab106 }</td>
<td>${ins.aab108 }</td>
<td>${ins.aab109 }</td>
<td><a href="#" onclick="onDel('${ins.aab101}')">删除</a></td>
</tr>
</c:forEach>
<c:forEach begin="${fn:length(rows)+1 }" end="15" step="1">
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<c:forEach begin="1" end="15" step="1">
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
</c:forEach>
</c:otherwise>

</c:choose>
<!-- 按钮块 -->
</table>
<table border="1" width="95%" align="center">
<tr >
<td align="center">
<input type="submit" name="next" value="查询">
<input type="submit" name="next" value="添加" 
formaction="<%=path%>/addEmp.jsp">
<input type="submit" id="del" name="next" value="删除"
 formaction="<%=path %>/delEmp.html" disabled="disabled">
</td>
</tr>
</table>
</form>
</body>
</html>