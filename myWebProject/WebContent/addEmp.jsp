<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="e" uri="http://org.wangxg/jsp/extl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>myWebProject</title>
<style type="text/css">
td{height:30px}
msg{color:#ff0000}
</style>
</head>
<%String path=request.getContextPath(); %>
<body>
${msg}
<br>
<br>
<form action="<%=path%>/addEmp.html" method="post">
<table border="1" width="45%" align="center">
<caption>
员工${empty param.aab101?'添加':'修改' }
<hr width="160">
</caption>
<tr>
<td colspan="2">员工数据</td>
</tr>
<tr>
<td>姓名</td>
<td>
<e:text name="aab102" defval="${ins.aab102 }" required="true" autofocus="true"/> 
</td>
</tr>

<c:if test="${!empty param.aab101 }">
<tr>
<td>编码</td>
<td><input type="text" readonly="readonly" value="${ins.aab103}"></td>
</tr>
</c:if>

<tr>
<td>生日</td>
<td>
<e:date name="aab104" defval="${ins.aab104 }" required="true"/>
</td>
</tr>
<tr>
<td>性别</td>
<td>
<e:radio name="aab105" value="男:1,女:2,不确定:3" defval="${empty param.aab101?'1':ins.aab105 }"/>
</td>
</tr>
<tr>
<td>民族</td>
<td>
<e:select name="aab106" defval="${ins.aab106 }" value="汉族:01,满族:02,蒙族:03,藏族:04,哈萨克:05"/>
</td>
</tr>
<tr>
<td>籍贯</td>
<td>
<e:text name="aab107" defval="${ins.aab107 }" />
</td>
</tr>
<tr>
<td>手机号码</td>
<td>
<e:text name="aab108" defval="${ins.aab108 }"/>
</td>
</tr>
<tr>
<td>电子邮件</td>
<td>
<e:email name="aab109" defval="${ins.aab109 }"/>
</td>
</tr>
<tr>
<td>档案工资</td>
<td>
<e:number step="0.01" name="aab110" defval="${ins.aab110 }"/>
</td>
</tr>
<tr>
<td>技术特长</td>
<td>
<e:select name="aab111" value="mysql:01,Oracle:02,Java核心编程:03,架构设计:04,无:05" 
multiple="true" style="width:153px,height:120px" defval="${ins.aab111 }"/>
</td>
</tr>
<tr>
<td>爱好</td>
<td>
<e:groupbox name="aab112" value="C:1,C++:2,C++--:3,C#:4,Java:5,Python:6" defval="${ins.aab112 }"/>
</td>
</tr>
<tr>
<td>备注</td>
<td>
<e:textarea rows="5" cols="45" name="aab113" defval="${ins.aab113 }"/>
</td>
</tr>
<tr>
<td colspan="2" align="center">
<input type="submit" name="hop" value="${empty param.aab101?'添加':'更新' }"
 formaction="<%=path %>/${empty param.aab101?'add':'modify' }Emp.html">
<input type="submit" name="hop" value="返回" formnovalidate="formnovalidate"
formaction="<%=path%>/queryEmp.html">
</td>
</tr>
</table>
<input type="hidden" name="aab101" value="${param.aab101}">
<e:hidden name="qaab102" />
<e:hidden name="qaab103"/>
<e:hidden name="qaab105"/>
<e:hidden name="qaab106"/>
<e:hidden name="baab104"/>
<e:hidden name="eaab104"/>
</form>
</body>
</html>