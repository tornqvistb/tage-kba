<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:actionURL name="performRepublishing" var="republishAdUrl"><portlet:param name="advertisementId" value="${advertisement.id}"/></portlet:actionURL><%
%><portlet:defineObjects/>

<jsp:include page="include/incl_res.jsp" />
<div id="content-primary" class="article cf" role="main">
	
	<div class="content-header cf">
		<a href="${cancelUrl}" class="button">Avbryt</a>
	</div>
	<h1>Återpublicera annons för ${advertisement.title}</h1>

	<form id="republish-ad-start-form" class="form-general" method="get" action="#">
		<p>${texts.confirmRepublishText}</p>
		<div class="row cols-1 cf">
			<div class="col col-1">
				<div class="checkbox alt">
					<input type="checkbox" value="ett" name="checkboxgroup1" id="checkbox1-5097936784a63">
					<label for="checkbox1-5097936784a63">Jag vill återpublicera min annons</label>
				</div>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="button" value="Fortsätt" name="submit-5097936784ac4" id="submit-5097936784ac4">
				<a href="${cancelUrl}">Avbryt</a>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$("#submit-5097936784ac4").click(function() {
		if ($("#checkbox1-5097936784a63").is(":checked")) {
			window.location.href="${republishAdUrl}";
		} else {
			alert("Du måste klicka i rutan som bekräftar att du vill återpublicera annonsen för att kunna gå vidare.")
		}
	});
</script>