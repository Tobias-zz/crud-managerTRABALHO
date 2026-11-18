<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - Login</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="text-center">
					<img height="70px" width="70px" alt="Logo" src="${pageContext.request.contextPath}/images/logo.png">
					<h3><strong>CRUD Manager</strong></h3>
					<p class="text-muted">Clínica Veterinária</p>
				</div>
				<hr />

				<c:if test="${not empty error}">
					<div class="alert alert-danger" style="position: static; width: 100%;">${error}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/login" method="POST">
					<div class="form-group">
						<label for="username">Usuário</label>
						<input type="text" class="form-control" id="username" name="username"
							   autofocus="autofocus" placeholder="Informe seu usuário" required
							   oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
							   oninput="setCustomValidity('')" />
					</div>

					<div class="form-group">
						<label for="password">Senha</label>
						<input type="password" class="form-control" id="password" name="password"
							   placeholder="Informe sua senha" required
							   oninvalid="this.setCustomValidity('Por favor, informe a senha.')"
							   oninput="setCustomValidity('')" />
					</div>

					<button type="submit" class="btn btn-primary btn-block">Entrar</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
