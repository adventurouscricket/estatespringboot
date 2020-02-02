<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assignment Building</title>
</head>
<body>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th><input type="checkbox" value="" id="checkAll" /></th>
							<th>Tên nhân viên</th>
							<th>Username</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${assignments }">
							<tr>
								<td><input type="checkbox" id="checkbox${item.id }"
									value="${item.id }" ${item.checked} /></td>
								<td>${item.fullName}</td>
								<td>${item.userName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="form-horizontal">
					<div class="form-group">
						<div class="col-sm-4">
							<div class="fg-line">
								<button type="button" id="btnUpdate"
									class="btn btn-sm btn-success">Update</button>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="fg-line">
								<button type="button" id="btnClose"
									class="btn btn-sm btn-success">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>