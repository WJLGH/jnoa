<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务账户管理</title>
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
		<li class="active"><a href="${ctx}/account/finAccount/">财务账户列表</a></li>
		<shiro:hasPermission name="account:finAccount:edit"><li><a href="${ctx}/account/finAccount/form">财务账户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="finAccount" action="${ctx}/account/finAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>uuid默认36位：</label>
				<form:input path="id" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>类型：：</label>
				<form:input path="acType" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>卡号：</label>
				<form:input path="cardNum" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="acName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>剩余金额：</label>
				<form:input path="amount" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>uuid默认36位</th>
				<th>类型：</th>
				<th>卡号</th>
				<th>名称</th>
				<th>剩余金额</th>
				<shiro:hasPermission name="account:finAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="finAccount">
			<tr>
				<td><a href="${ctx}/account/finAccount/form?id=${finAccount.id}">
					${finAccount.id}
				</a></td>
				<td>
					${finAccount.acType}
				</td>
				<td>
					${finAccount.cardNum}
				</td>
				<td>
					${finAccount.acName}
				</td>
				<td>
					${finAccount.amount}
				</td>
				<shiro:hasPermission name="account:finAccount:edit"><td>
    				<a href="${ctx}/account/finAccount/form?id=${finAccount.id}">修改</a>
					<a href="${ctx}/account/finAccount/delete?id=${finAccount.id}" onclick="return confirmx('确认要删除该财务账户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>