<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Pet</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty pet ? 'Atualizar' : 'Adicionar'} Pet</h3>

		<form action="${pageContext.request.contextPath}/pet/${action}" method="POST">

			<input type="hidden" value="${pet.getId()}" name="petId">

			<div class="row">
				<div class="form-group col-md-6">
					<label for="name">Nome</label>
					<input type="text" class="form-control" id="name" name="name"
						   autofocus="autofocus" placeholder="Nome do pet" maxlength="100"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe o nome do pet.')"
						   oninput="setCustomValidity('')"
						   value="<c:out value="${pet.getName()}"/>" />
				</div>

				<div class="form-group col-md-6">
					<label for="user">Tutor</label>
					<select id="user" class="form-control" name="user" required
							oninvalid="this.setCustomValidity('Por favor, informe o tutor.')"
							oninput="setCustomValidity('')">
						<option value="" ${not empty pet ? '' : 'selected'}>Selecione o tutor</option>
						<c:forEach var="u" items="${users}">
							<option value="${u.getId()}" ${pet.getUser().getId() == u.getId() ? 'selected' : ''}>
								<c:out value="${u.getName()}"/>
							</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-4">
					<label for="birthDate">Data de nascimento</label>
					<input type="date" class="form-control" id="birthDate" name="birthDate"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe a data de nascimento.')"
						   oninput="setCustomValidity('')"
						   value="<c:out value="${pet.getBirthDate()}"/>" />
				</div>

				<div class="form-group col-md-4">
					<label for="weight">Peso (kg)</label>
					<input type="number" class="form-control" id="weight" name="weight"
						   step="0.01" min="0" placeholder="Ex.: 12.5"
						   required
						   oninvalid="this.setCustomValidity('Por favor, informe o peso do pet.')"
						   oninput="setCustomValidity('')"
						   value="<c:out value="${pet.getWeight()}"/>" />
				</div>

				<div class="form-group col-md-4">
					<label>Espécie</label>
					<div>
						<label class="radio-inline">
							<input type="radio" name="species" value="CAO" required
								   ${empty pet or pet.getSpecies() eq 'CAO' ? 'checked' : ''}> Cão
						</label>
						<label class="radio-inline">
							<input type="radio" name="species" value="GATO"
								   ${pet.getSpecies() eq 'GATO' ? 'checked' : ''}> Gato
						</label>
						<label class="radio-inline">
							<input type="radio" name="species" value="OUTRO"
								   ${pet.getSpecies() eq 'OUTRO' ? 'checked' : ''}> Outro
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<div class="checkbox">
						<label>
							<input type="checkbox" name="neutered" value="true"
								   ${pet.isNeutered() ? 'checked' : ''}> Pet castrado
						</label>
					</div>
				</div>
			</div>

			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/pets" class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${not empty pet ? 'Atualizar' : 'Cadastrar'} Pet</button>
				</div>
			</div>
		</form>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
