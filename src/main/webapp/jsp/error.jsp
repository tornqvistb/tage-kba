<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>
<jsp:include page="include/incl_res.jsp" />

<div id="content-primary" class="cf" role="main">
	<div class="content-header cf">
		<h1>Ett fel uppstod!</h1>
	</div>
	<p>Systemet kunde inte slutföra din begäran eftersom ett fel uppstod. Kontakta support om felet kvarstår!</p>
	<p>Klicka <a href="${cancelUrl}">här</a> för att börja om.
	<p>
		<strong>${error}</strong>
		<div style="border:1px #BBB solid; padding: 5px; width:600px">
			<a href="#" id="showHide" class="button">Visa detaljer</a>
			<div style="font-family:Courier New; font-size:8pt; display:none;" id="stack">
				<textarea style="white-space: nowrap;border:none;width:600px" rows="20">${stackTrace}</textarea>
			</div>
		</div>
	</p>
</div>

<script type="text/javascript">
	$("#showHide").click(function() {
		if ($("#stack").css("display") == "none") {
			$("#stack").css("display", "");
			$("#showHide").text("Dölj detaljer");
		} else {
			$("#stack").css("display", "none");
			$("#showHide").text("Visa detaljer");
		}
	});
</script>