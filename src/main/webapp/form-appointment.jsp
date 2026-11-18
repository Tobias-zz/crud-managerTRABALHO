<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Consulta</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty appointment ? 'Atualizar' : 'Adicionar'} Consulta</h3>

		<form action="${pageContext.request.contextPath}/appointment/${action}" method="POST">

			<input type="hidden" value="${appointment.getId()}" name="appointmentId">

			<div class="row">
				<div class="form-group col-md-6">
					<label for="pet">Pet</label>
					<select id="pet" class="form-control" name="pet" required
							oninvalid="this.setCustomValidity('Por favor, informe o pet.')"
							oninput="setCustomValidity('')">
						<option value="" ${not empty appointment ? '' : 'selected'}>Selecione o pet</option>
						<c:forEach var="p" items="${pets}">
							<option value="${p.getId()}" ${appointment.getPet().getId() == p.getId() ? 'selected' : ''}>
								<c:out value="${p.getName()}"/> (Tutor: <c:out value="${p.getUser().getName()}"/>)
							</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group col-md-3">
					<label for="appointmentDate">Data da consulta</label>
					<input type="date" class="form-control" id="appointmentDate" name="appointmentDate"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe a data da consulta.')"
						   oninput="setCustomValidity('')"
						   value="<c:out value="${appointment.getAppointmentDate()}"/>" />
				</div>

				<div class="form-group col-md-3">
					<label for="price">Valor (R$)</label>
					<input type="number" class="form-control" id="price" name="price"
						   step="0.01" min="0" placeholder="Ex.: 120.00"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe o valor da consulta.')"
						   oninput="setCustomValidity('')"
						   value="<c:out value="${appointment.getPrice()}"/>" />
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label for="reason">Motivo da consulta</label>
					<textarea class="form-control" id="reason" name="reason" rows="4"
							  autofocus="autofocus" placeholder="Descreva o motivo da consulta"
							  required
							  oninvalid="this.setCustomValidity('Por favor, informe o motivo da consulta.')"
							  oninput="setCustomValidity('')"><c:out value="${appointment.getReason()}"/></textarea>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<div class="checkbox">
						<label>
							<input type="checkbox" name="returnNeeded" value="true"
								   ${appointment.isReturnNeeded() ? 'checked' : ''}> Necessita de retorno
						</label>
					</div>
				</div>
			</div>

			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/appointments" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${not empty appointment ? 'Atualizar' : 'Cadastrar'} Consulta</button>
				</div>
			</div>
		</form>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
