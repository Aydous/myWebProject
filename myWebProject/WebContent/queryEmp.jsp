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
Ա������
<hr width="160" >
</caption>
<!-- ��ѯ���� -->
<tr>
<td colspan="4">��ѯ����</td>
</tr>
<tr>
	<td>����</td>
	<td><e:text name="qaab102" value="${param.qaab102 }"/></td>
	<td>���</td>
	<td><e:text name="qaab103" value="${param.qaab103 }"/></td>
</tr>
<tr>
	<td>�Ա�</td>
	<td><e:radio name="qaab105" 
	value="��:1,Ů:2,��ȷ��:3,���޶�:''" defval="${param.qaab105 }"/></td>
	<td>����</td>
	<td><e:select name="qaab106" header="true" defval="${param.qaab106 }"
	value="����:01,����:02,����:03,����:04,��������:05"/></td>
</tr>
<tr>
	<td>����[B]</td>
	<td><e:date name="baab104" value="${param.baab104 }"/></td>
	<td>����[E]</td>
	<td><e:date name="eaab104" value="${param.eaab104 }"/></td>
</tr>
</table>
<table border="1" width="95%" align="center">
<!-- ��ѯ������ʾ�� -->
<tr>
	<td></td>
	<td>���</td>
	<td>����</td>
	<td>���</td>
	<td>����</td>
	<td>�Ա�</td>
	<td>����</td>
	<td>�ֻ�����</td>
	<td>�����ʼ�</td>
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
<td><a href="#" onclick="onDel('${ins.aab101}')">ɾ��</a></td>
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
<!-- ��ť�� -->
</table>
<table border="1" width="95%" align="center">
<tr >
<td align="center">
<input type="submit" name="next" value="��ѯ">
<input type="submit" name="next" value="���" 
formaction="<%=path%>/addEmp.jsp">
<input type="submit" id="del" name="next" value="ɾ��"
 formaction="<%=path %>/delEmp.html" disabled="disabled">
</td>
</tr>
</table>
</form>
</body>
</html>