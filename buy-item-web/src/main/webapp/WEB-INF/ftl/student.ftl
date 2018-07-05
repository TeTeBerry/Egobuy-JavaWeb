<html>
<head><title>Freemarker</title></head>
<body>
	<h1>学生信息</h1>
	<ul>
		<li>学号:${stu.id}</li>
		<li>学号:${stu.name}</li>
		<li>学号:${stu.tel}</li>
	</ul>
	<p>学生列表</p>
	<table border=1 width="300" style="border:1px solid">
		<tr>
			<th>序号</th>
			<th>编号</th>
			<th>姓名</th>
			<th>电话</th>
		</tr>
		<#list sts as st>
			<#if st_index%2 == 0>
				<tr style="background-color:red">
			<#else>
				<tr style="background-color:green">
			</#if>
				<td>${st_index}</td>
				<td>${st.id}</td>
				<td>${st.name}</td>
				<td>${st.tel}</td>
			</tr>
		</#list>
		
	</table>
	
	<p>日期的显示</p>
		<ul>
			<li>当前系统日期:${date?date}</li>
			<li>当前系统日期:${date?time}</li>
			<li>当前系统日期:${date?datetime}</li>
			<li>当前系统日期:${date?string('yyyy年MM月dd日 HH:mm:ss')}</li>
		</ul>
	<p>null值处理</p>
	<ul>
		<li>默认值为空串：${val!}</li>
		<li>给了默认值：${val!"你真美!"}</li>
		<li>if判断:<br/>
			<#if val??>
				val不为null
			<#else>
				val为null
			</#if>
		</li>
	</ul>
	<p>包含include</p>
	<#include "hello.ftl">

</body>
</html>