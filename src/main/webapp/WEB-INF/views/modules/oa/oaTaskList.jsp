<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/oaTask/">任务信息列表</a></li>
		<shiro:hasPermission name="oa:oaTask:edit"><li><a href="${ctx}/oa/oaTask/form">任务信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaTask" action="${ctx}/oa/oaTask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>任务标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务标题</th>
				<th>任务内容</th>
				<th>是否是转发，0：否，1，是</th>
				<th>任务完成标记，&lsquo;0&rsquo;，未完成，&lsquo;1&rsquo;完成</th>
				<shiro:hasPermission name="oa:oaTask:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaTask">
			<tr>
				<td><a href="${ctx}/oa/oaTask/form?id=${oaTask.id}">
					${oaTask.title}
				</a></td>
				<td>
					${oaTask.content}
				</td>
				<td>
					${oaTask.forwardFlag}
				</td>
				<td>
					${oaTask.completeFlag}
				</td>
				<shiro:hasPermission name="oa:oaTask:edit"><td>
    				<a href="${ctx}/oa/oaTask/form?id=${oaTask.id}">修改</a>
					<a href="${ctx}/oa/oaTask/delete?id=${oaTask.id}" onclick="return confirmx('确认要删除该任务信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>