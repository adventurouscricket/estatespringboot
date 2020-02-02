<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<c:url var="buildingURL" value="/admin-building?action=list&page=1&maxPageItem=10" />
<c:url var="assignmentBuildingURL" value="/admin-building-assignment" />
<c:url var="buildingAPI" value="/api-admin-building" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mr Henry</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="#">Trang chủ</a>
					</li>
					<li class="active">Danh sách sản phẩm</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<!-- start form -->
					<form action="${buildingURL }" method="get" id="formSubmit">
						<div class="widget-box table-filter">
							<div class="widget-header">
								<h4 class="widget-title">Tìm kiếm</h4>
								<div class="widget-toolbar">
									<a href="#" data-action="collapse">
										<i class="ace-icon fa fa-chevron-up"></i>
									</a>
								</div>
							</div>
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-horizontal">
										<div class="form-group">
											<div class="col-sm-6">
												<label>Tên sản phẩm</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="name" value="${model.name }"/>
												</div>
											</div>
											<div class="col-sm-6">
												<label>Diện tích sàn</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="buildingArea" value="${model.buildingArea }"/>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-4">
												<label>Quận hiện có</label>
												<div class="fg-line">
													<select class="form-control" id="" name="district">
														<option value="">-- Chọn quận --</option>
														<c:forEach var="item" items="${districts }">
															<option value="${item.key }" ${item.key == model.district ? "selected" : "" }>${item.value}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="col-sm-4">
												<label>Phường</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="ward" value="${model.ward }"/>
												</div>
											</div>
											<div class="col-sm-4">
												<label>Đường</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="street" value="${model.street }"/>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-4">
												<label>Số tầng hầm</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="numberOfBasement" value="${model.numberOfBasement }"/>
												</div>
											</div>
											<div class="col-sm-4">
												<label>Hướng</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="direction" value="${model.direction }"/>
												</div>
											</div>
											<div class="col-sm-4">
												<label>Hạng</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="level" value="${model.level }"/>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-4">
												<label>Tên quản lý</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="managerName" value="${model.managerName }"/>
												</div>
											</div>
											<div class="col-sm-4">
												<label>Điện thoại quản lý</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="managerPhone" value="${model.managerPhone }"/>
												</div>
											</div>
											<div class="col-sm-4">
												<label>Nhân viên phụ trách</label>
												<div class="fg-line">
													<select class="form-control" id="">
														<option>Hello</option>
														<option>Hello</option>
														<option>Hello</option>
													</select>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-3">
												<label>Diện tích từ</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="areaRentFrom" value="${model.areaRentFrom }"/>
												</div>
											</div>
											<div class="col-sm-3">
												<label>Diện tích đến</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="areaRentTo" value="${model.areaRentTo }"/>
												</div>
											</div>
											<div class="col-sm-3">
												<label>Giá thuê từ</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="costRentFrom" value="${model.costRentFrom }"/>
												</div>
											</div>
											<div class="col-sm-3">
												<label>Giá thuê đến</label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" name="costRentTo" value="${model.costRentTo }"/>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-6">
												<label>Loại tòa nhà</label>
												<div class="fg-line">
													<c:forEach var="item" items="${buildingTypes }">
														<label class="checkbox-inline">
															<input type="checkbox" name="buildingTypes" value="${item.key}"
																${fn:contains(fn:join(model.buildingTypes, ','), item.key) ? 'checked' : ''}/>${item.value}</label>
													</c:forEach>
												</div>
											</div>
										</div>
										<input type="hidden" name="action" value="list" />
										<div class="form-group">
											<div class="col-sm-6">
												<button type="button" id="btnSearch" class="btn btn-sm btn-success">Tìm kiếm
												<i class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i></button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" value="${model.page }" id="page" name="page" />
						<input type="hidden" value="${model.maxPageItem }" id="maxPageItem" name="maxPageItem" />
						<input type="hidden" value="" id="sortName" name="sortName" />
						<input type="hidden" value="" id="sortBy" name="sortBy" />
					</form>
					<!-- end form -->
						<!-- button edit & delete -->
						<div class="table-btn-controls">
							<div class="pull-right tableTools-container">
								<div class="dt-buttons btn-overlap btn-group">
									<a flag="info" class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
									data-toggle="tooltip" title="Thêm tòa nhà" href='<c:url value="/admin-building?action=edit"/>'>
									<span><i class="fa fa-plus-circle bigger-110 purple"></i></span></a>
									
									<button type="button" class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
									data-toggle="tooltip" title="Xóa tòa nhà" id="btnDelete">
										<span><i class="fa fa-trash-o bigger-110 pink"></i></span>
									</button>
								</div>
							</div>
						</div>
						<!-- table -->
						<div class="row">
							<div class="col-xs-12">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th><input type="checkbox" value="" id="checkAll" /></th>
											<th>Tên sản phẩm</th>
											<th>Địa chỉ</th>
											<th>Giá thuê</th>
											<th>Diện tích thuê</th>
											<th>Loại tòa nhà</th>
											<th>Tên quản lý</th>
											<th>SĐT</th>
											<th>Thao tác</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${models }">
											<tr>
												<td><input type="checkbox" id="checkbox${item.id }" value="${item.id }" /></td>
												<td>${item.name}</td>
												<td>${item.adress}</td>
												<td>${item.costRent}</td>
												<td>${item.rentArea}</td>
												<td>${item.type}</td>
												<td>${item.managerName}</td>
												<td>${item.managerPhone}</td>
												<td>
													<a class="btn btn-xs btn-primary btn-edit"
													data-toggle="tooltip" title="Sửa tòa nhà" 
													href='<c:url value="/admin-building?action=edit&id=${item.id}"/>'>
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
													<a><input type="hidden" value="${item.id }"/><button data-toggle="tooltip" title="Assignment" class="glyphicon glyphicon-user" style="top: 2.5px; line-height: 1.8;"></button></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="container">
								<ul class="pagination" id="pagination"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
		var totalPages = ${model.totalPage};
		var currentPage = ${model.page};
		var visiblePages = ${model.maxPageItem}
		var limit = 2;
		$(function () {
			window.pagObj = $('#pagination').twbsPagination({
				totalPages: totalPages,
				visiblePages: 5,
				startPage: currentPage,
				onPageClick: function (event, page) {
					if (currentPage != page) {
						$('#maxPageItem').val(limit);
						$('#page').val(page);
						//$('#sortName').val('name');
						//$('#sortBy').val('ASC');
						//$('#type').val('list');
						$('#formSubmit').submit();
					}
				}
			});
		});
		
		$('#btnSearch').click(function () {
			$('#page').val(1);
			$('#formSubmit').submit();
		});
	
		$('#btnDelete').click(function () {
			var dataArr = $('tbody input[type=checkbox]:checked').map(function(){
				return $(this).val();
			}).get();
			//var data ={};
			//data['ids'] = dataArr;
			deleteBuilding(dataArr);
		});
	
		function deleteBuilding(data){
			$.ajax({
				//url:'${buildingAPI }',
				url: 'http://localhost:8087/api/building',
				data: JSON.stringify(data),
				type: 'DELETE',
				contentType: 'application/json',
				//dataType: 'json',
				success: function(data){
					window.location.href="${buildingURL}&message=delete_success";
					$('#formSubmit').submit();
				},
				error: function() {
					window.location.href="${buildingURL}&message=error_system";
				}
			});
		}
	
		$('.table tbody').on('click', '.glyphicon-user', function(){
			var currow = $(this).closest('tr');
			var button = currow.find('input[type=hidden]')
			var id = button.val();
			if(id != null) {
				/* assignmentBuilding(id); */
				var strUrl = '${assignmentBuildingURL }?id='+id+'&page=1&maxPageItem=10';
				basicPopup(strUrl);
				/* $('#assignmentBuilding').modal(); */
			}
		})		
		
		// JavaScript popup window function
		function basicPopup(url) {
			popupWindow = window.open(url,'popUpWindow','height=500,width=700,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes')
		}
		
		function assignmentBuilding(id){
			$.ajax({
				url:'${assignmentBuildingURL }?id='+id+'&page=1&maxPageItem=10'
			});
		}
		
		function autoCheckAllChild(){
			$('#checkAll').change(function (){
				if((this).checked){
					$(this).closest('table').find('tbody').find('input[type=checkbox]').prop('checked', true);
				} else {
					$(this).closest('table').find('tbody').find('input[type=checkbox]').prop('checked', false);
					$('#btnDelete').prop('disabled', true);
				}
			});
		}
		
		function autoCheckParent() {
			var totalCheckChild = $('#checkAll').closest('table').find('tbody').find('input[type=checkbox]').length;
			$('#checkAll').closest('table').find('tbody').find('input[type=checkbox]').each(function (){
				$(this).on('change', function() {
					var totalCheckChildChecked = $('#checkAll').closest('table').find('tbody').find('input[type=checkbox]:checked').length;
					if(totalCheckChild==totalCheckChildChecked){
						$('#checkAll').prop('checked',true);
					} else {
						$('#checkAll').prop('checked',false);
					}
				});
			});
			
		}
	</script>
</body>
</html>