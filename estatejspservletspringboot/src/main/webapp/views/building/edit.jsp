<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<c:url var="buildingAPI" value="/api-admin-building" />
<c:url var="buildingURL" value="/admin-building" />
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
					<li class="active">Thêm sản phẩm</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<form id="edit">
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Tên sản phẩm</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="name" value="${model.name}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Quận</label>
								<div class="col-sm-10">
									<select class="form-control"name="district">
										<option value="">-- Chọn quận --</option>
										<c:forEach var="item" items="${districts }">
											<option value="${item.key }" ${item.key == model.district ? 'selected' : '' }>${item.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Phường</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="ward" value="${model.ward}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Đường</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="street" value="${model.street}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Kết cấu</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="structure" value="${model.structure}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Số tầng hầm</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="numberOfBasement" value="${model.numberOfBasement}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Diện tích sàn</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="buildingArea" value="${model.buildingArea}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Hướng</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="direction" value="${model.direction}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Hạng</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="level" value="${model.level}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Diện tích thuê</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="rentArea" value="${model.rentArea}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Mô tả</label>
								<div class="col-sm-10"><input type="text" class="form-control"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Giá thuê</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="costRent" value="${model.costRent}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Mô tả giá</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="costDescription" value="${model.costDescription}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Phí dịch vụ</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="serviceCost" value="${model.serviceCost}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Phí ô tô</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="carCost" value="${model.carCost}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Phí mô tô</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="motorBikeCost" value="${model.motorBikeCost}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Phí ngoài giờ</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="overtimeCost" value="${model.overtimeCost}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Tiền điện</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="electricityCost" value="${model.electricityCost}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Đặt cọc</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="deposit" value="${model.deposit}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Thanh toán</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="payment" value="${model.payment}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Thời hạn thuê</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="timeRent" value="${model.timeRent}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Thời gian trang trí</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="timeDecorator" value="${model.timeDecorator}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Tên quản lý</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="managerName" value="${model.managerName}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Số ĐT</label>
								<div class="col-sm-10"><input type="text" class="form-control" name="managerPhone" value="${model.managerPhone}"/></div>
							</div>
							<div class="form-group paddingButtom">
								<label class="col-sm-2">Loại tòa nhà</label>
								<div class="fg-line col-sm-10">
									<c:forEach var="item" items="${buildingTypes }">
										<label class="checkbox-inline">
										<input type="checkbox" name="buildingTypes" value="${item.key}"
											${fn:contains(fn:join(model.buildingTypes, ','), item.key) ? 'checked' : ''}/>${item.value}</label>
									</c:forEach>
								</div>
							</div>
							<input type="hidden" name="id" value="${model.id }" id="buildingId"/>
						</form>
						<div class="row text-center btn-addsp">
						<c:if test="${not empty model.id}">
							<button class="btn btn-success" id="btnAddOrUpdate">Cập nhật</button>
						</c:if>
						<c:if test="${empty model.id}">
							<button class="btn btn-success" id="btnAddOrUpdate">Thêm tòa nhà</button>
						</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#btnAddOrUpdate").click(function(){
			addOrUpdateBuilding();
		})
		
		function addOrUpdateBuilding() {
			var buildingId = $("#buildingId").val();
			var data = {};
			var buildingTypes = [];
			var formData = $("#edit").serializeArray();
			
			$.each(formData, function(index, v){
				if(v.name == 'buildingTypes') {
					buildingTypes.push(v.value);
				} else {
					data[""+v.name+""] = v.value;
				}
			});
			data['buildingTypes'] = buildingTypes;
			
			if(buildingId != "") {
				updateBuilding(data, buildingId);
			} else {
				addBuilding(data);
			}
		}
		
		function addBuilding(data) {
			$.ajax({
				// url:'${buildingAPI }',
				url: 'http://localhost:8087/api/building',
				data: JSON.stringify(data),
				type: 'POST',
				contentType: 'application/json',
				dataType: 'json',
				success: function(data){
					window.location.href="${buildingURL}?action=edit&id="+data.id+"&message=insert_success";
				},
				error: function() {
					window.location.href="${buildingURL}?action=edit&message=error_system";
				}
			});
		}
		
		function updateBuilding(data, id) {
			$.ajax({
				//url:'${buildingAPI }',
				url: 'http://localhost:8087/api/building/'+id,
				data: JSON.stringify(data),
				type: 'PUT',
				contentType: 'application/json',
				//dataType: 'json',
				success: function(data){
					window.location.href="${buildingURL}?action=list&message=update_success";
				},
				error: function() {
					window.location.href="${buildingURL}?action=list&message=error_system";
				}
			});
		}
	</script>
</body>
</html>