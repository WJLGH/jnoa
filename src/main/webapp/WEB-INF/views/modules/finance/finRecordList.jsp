<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>明细记录管理</title>
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
		<li class="active"><a href="${ctx}/finance/finRecord/">明细记录列表</a></li>
		<shiro:hasPermission name="finance:finRecord:edit"><li><a href="${ctx}/finance/finRecord/form">明细记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="finRecord" action="${ctx}/finance/finRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>uuid默认36位：</label>
				<form:input path="id" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>记录类型：：</label>
				<form:input path="reType" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>交易类型：：</label>
				<form:input path="busType" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>交易金额：</label>
				<form:input path="amount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>描述：</label>
				<form:input path="description" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>收入账户：</label>
				<form:input path="inId" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>支出账户：</label>
				<form:input path="outId" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>记录时间：</label>
				<input name="noteDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${finRecord.noteDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>记录类型：</th>
				<th>交易类型：</th>
				<th>交易金额</th>
				<th>描述</th>
				<th>收入账户</th>
				<th>支出账户</th>
				<th>记录时间</th>
				<shiro:hasPermission name="finance:finRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="finRecord">
			<tr>
				<td><a href="${ctx}/finance/finRecord/form?id=${finRecord.id}">
					${finRecord.id}
				</a></td>
				<td>
					${finRecord.reType}
				</td>
				<td>
					${finRecord.busType}
				</td>
				<td>
					${finRecord.amount}
				</td>
				<td>
					${finRecord.description}
				</td>
				<td>
					${finRecord.inId}
				</td>
				<td>
					${finRecord.outId}
				</td>
				<td>
					<fmt:formatDate value="${finRecord.noteDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="finance:finRecord:edit"><td>
    				<a href="${ctx}/finance/finRecord/form?id=${finRecord.id}">修改</a>
					<a href="${ctx}/finance/finRecord/delete?id=${finRecord.id}" onclick="return confirmx('确认要删除该明细记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>