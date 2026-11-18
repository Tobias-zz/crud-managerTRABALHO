<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Consultas</title>
</head>
<body>
	<%@include file="modal.html"%>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
		  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  <c:out value="${message}"/>
		</div>

		<div id="top" class="row">
			<div class="col-md-9">
				<h3>Consultas</h3>
			</div>

			<div class="col-md-3">
				<a href="${pageContext.request.contextPath}/appointment/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbspAdicionar Consulta</a>
			</div>
		</div>

		<hr />

		<div id="list" class="row">
			<div class="table-responsive col-md-12">
				<table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>Data</th>
							<th>Pet</th>
							<th>Motivo</th>
							<th>Valor</th>
							<th>Retorno</th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="appointment" items="${appointments}">
							<tr>
								<td><fmt:formatDate value="${appointment.getAppointmentDate()}" pattern="dd/MM/yyyy"/></td>
								<td><c:out value="${appointment.getPet().getName()}"/></td>
								<td><c:out value="${appointment.getReason()}"/></td>
								<td>R$ <fmt:formatNumber value="${appointment.getPrice()}" pattern="#,##0.00"/></td>
								<td>${appointment.isReturnNeeded() ? 'Sim' : 'Não'}</td>

								<td class="actions">
									<a class="btn btn-info btn-xs"
									   href="${pageContext.request.contextPath}/appointment/update?appointmentId=${appointment.getId()}">
									   <span class="glyphicon glyphicon-edit"></span>
									</a>
								</td>

								<td class="actions">
									<a class="btn btn-danger btn-xs modal-remove"
									   appointment-id="${appointment.getId()}"
									   appointment-reason="<c:out value="${appointment.getReason()}"/>" data-toggle="modal"
									   data-target="#delete-modal" href="#"><span
									   class="glyphicon glyphicon-trash"></span></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div id="bottom" class="row">
			<div class="col-md-12">
				<jsp:include page="pagination.jsp" />
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    setTimeout(function() {
		        $("#alert").slideUp(500);
		    }, 3000);

		    $(".modal-remove").click(function () {
	            var reason = $(this).attr('appointment-reason');
	            var appointmentId = $(this).attr('appointment-id');
	            $(".modal-body #hiddenValue").text("a consulta '" + reason + "'");
	            $("#id").attr("value", appointmentId);
	            $("#form").attr("action", "${pageContext.request.contextPath}/appointment/delete");
	        })
		});
	</script>
</body>
</html>
