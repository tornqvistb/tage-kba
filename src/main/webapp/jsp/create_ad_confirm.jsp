<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:defineObjects/><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:renderURL var="createAdUrl"><portlet:param name="page" value="createAd"/></portlet:renderURL><%
%>

<jsp:include page="include/incl_res.jsp" />

<div id="content-primary" class="article cf" role="main">
<div class="content-header cf">
	<a href="${cancelUrl}" class="button">Avbryt</a>
</div>
<h1>Skapa ny annons</h1>

<form id="new-ad-start-form" class="form-general" method="get" action="#">
	<p>${texts.confirmCreateAdText}</p>
	<div class="row cols-1 cf">
		<div class="col col-1">
			<div class="checkbox alt">
				<input type="checkbox" value="ett" name="checkboxgroup1" id="checkbox1-5097936784a63">
				<label for="checkbox1-5097936784a63">Jag förstår och följer de regler som gäller för att använda tjänsterna Annonsering och Efterlysning på Återvinsten.</label>
			</div>
		</div>
	</div>
	<div class="row cols-1 cf">
		<div class="col medium col-1 submit-area">
			<a href="#" id="submit-5097936784ac4" class="button">Fortsätt</a>
			<a href="${cancelUrl}">Avbryt</a>
		</div>
	</div>
</form>
</div>

<script type="text/javascript">
	$("#submit-5097936784ac4").click(function() {
		if ($("#checkbox1-5097936784a63").is(":checked")) {
			window.location.href="${createAdUrl}";
		} else {
			alert("Du måste klicka i rutan som bekräftar att du förstått reglerna för bortskänkning för att kunna gå vidare.")
		}
	});
</script>