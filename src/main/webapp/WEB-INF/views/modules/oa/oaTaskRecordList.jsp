<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>回复信息管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaTaskRecord/">回复信息列表</a></li>
		<shiro:hasPermission name="oa:oaTaskRecord:edit"><li><a href="${ctx}/oa/oaTaskRecord/form">回复信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaTaskRecord" action="${ctx}/oa/oaTaskRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>任务ID：</label>
				<form:input path="oaTask.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>任务标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>发送人：</label>
				<form:input path="sendUser.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>接受人：</label>
				<sys:treeselect id="receUser" name="receUser.id" value="${oaTaskRecord.receUser.id}" labelName="receUser.name" labelValue="${oaTaskRecord.receUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>阅读标记：</label>
				<form:input path="readFlag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>是否是转发：</label>
				<form:input path="forwardFlag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>任务完成标记，&lsquo;0&rsquo;，未完成，&lsquo;1&rsquo;完成：</label>
				<form:input path="completeFlag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>年：</label>
				<form:input path="year" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>月：</label>
				<form:input path="month" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>日：</label>
				<form:input path="day" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发送人</th>
				<th>接受人</th>
				<th>发送时间</th>
				<th>是否是转发</th>
				<th>备注信息</th>
				<th>任务完成标记，&lsquo;0&rsquo;，未完成，&lsquo;1&rsquo;完成</th>
				<shiro:hasPermission name="oa:oaTaskRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaTaskRecord">
			<tr>
				<td><a href="${ctx}/oa/oaTaskRecord/form?id=${oaTaskRecord.id}">
					${oaTaskRecord.sendUser.id}
				</a></td>
				<td>
					${oaTaskRecord.receUser.name}
				</td>
				<td>
					<fmt:formatDate value="${oaTaskRecord.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaTaskRecord.forwardFlag}
				</td>
				<td>
					${oaTaskRecord.remarks}
				</td>
				<td>
					${oaTaskRecord.completeFlag}
				</td>
				<shiro:hasPermission name="oa:oaTaskRecord:edit"><td>
    				<a href="${ctx}/oa/oaTaskRecord/form?id=${oaTaskRecord.id}">修改</a>
					<a href="${ctx}/oa/oaTaskRecord/delete?id=${oaTaskRecord.id}" onclick="return confirmx('确认要删除该回复信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>