<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Pets</title>
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
				<h3>Pets</h3>
			</div>

			<div class="col-md-3">
				<a href="${pageContext.request.contextPath}/pet/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbspAdicionar Pet</a>
			</div>
		</div>

		<hr />

		<div id="list" class="row">
			<div class="table-responsive col-md-12">
				<table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>Nome</th>
							<th>Espécie</th>
							<th>Nascimento</th>
							<th>Peso (kg)</th>
							<th>Castrado</th>
							<th>Tutor</th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="pet" items="${pets}">
							<tr>
								<td><c:out value="${pet.getName()}"/></td>
								<td>
									<c:choose>
										<c:when test="${pet.getSpecies() eq 'CAO'}">Cão</c:when>
										<c:when test="${pet.getSpecies() eq 'GATO'}">Gato</c:when>
										<c:otherwise>Outro</c:otherwise>
									</c:choose>
								</td>
								<td><fmt:formatDate value="${pet.getBirthDate()}" pattern="dd/MM/yyyy"/></td>
								<td><fmt:formatNumber value="${pet.getWeight()}" pattern="#,##0.00"/></td>
								<td>${pet.isNeutered() ? 'Sim' : 'Não'}</td>
								<td><c:out value="${pet.getUser().getName()}"/></td>

								<td class="actions">
									<a class="btn btn-info btn-xs"
									   href="${pageContext.request.contextPath}/pet/update?petId=${pet.getId()}">
									   <span class="glyphicon glyphicon-edit"></span>
									</a>
								</td>

								<td class="actions">
									<a class="btn btn-danger btn-xs modal-remove"
									   pet-id="${pet.getId()}"
									   pet-name="<c:out value="${pet.getName()}"/>" data-toggle="modal"
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
	            var petName = $(this).attr('pet-name');
	            var petId = $(this).attr('pet-id');
	            $(".modal-body #hiddenValue").text("o pet '" + petName + "'");
	            $("#id").attr("value", petId);
	            $("#form").attr("action", "${pageContext.request.contextPath}/pet/delete");
	        })
		});
	</script>
</body>
</html>
