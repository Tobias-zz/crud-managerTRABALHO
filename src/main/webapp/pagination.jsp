<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<ul class="pagination">
	<c:choose>
		<c:when test="${currentPage <= 1}">
			<li class="disabled"><a>&lt; Anterior</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${pageContext.request.contextPath}${pageUrl}?page=${currentPage - 1}">&lt; Anterior</a></li>
		</c:otherwise>
	</c:choose>

	<c:forEach var="i" begin="1" end="${totalPages}">
		<li class="${i == currentPage ? 'active' : ''}">
			<a href="${pageContext.request.contextPath}${pageUrl}?page=${i}">${i}</a>
		</li>
	</c:forEach>

	<c:choose>
		<c:when test="${currentPage >= totalPages}">
			<li class="disabled"><a>Próximo &gt;</a></li>
		</c:when>
		<c:otherwise>
			<li class="next"><a href="${pageContext.request.contextPath}${pageUrl}?page=${currentPage + 1}" rel="next">Próximo &gt;</a></li>
		</c:otherwise>
	</c:choose>
</ul>
