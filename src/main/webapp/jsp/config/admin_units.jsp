<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:actionURL name="saveUnit" var="saveUnitUrl"/><%
%><portlet:actionURL name="removeUnit" var="removeUnitUrl"/><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>

<%  
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.config"); 
%>

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
	<h1>Administration - administrera enheter</h1>
	
<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info error-message">
		<h2>Felaktigt inmatade texter</h2>
		<p>Följande fel upptäcktes i texterna, rätta felen och försök igen.</p>
		<ul>
<%
		for(ObjectError error: bindingResult.getAllErrors()) {
%>			
			<li><spring:message code="<%= error.getCode() %>"/></li>
<%
		}
%>
		</ul>
	</div>
<%
	}
%>	
	<c:if test="${!empty errorMessage}">
		<div class="system-info error-message">
			<h2>Ett fel har uppstått</h2>
			<p>${errorMessage}</p>
		</div>
	</c:if>
	
	<form:form id="change-unit-form" cssClass="form-general" modelAttribute="newUnit" action="${saveUnitUrl}" >
		<div class="row cols-2 cf">
			<c:set var="err"><form:errors path="name"/></c:set> 
			<div class="text col medium col-1 mandatory <%= bindingResult.hasFieldErrors("name") ? "error" : "" %>">
				<label for="nameInput">Enhetsnamn <strong><form:errors path="name"/></strong></label>
				<form:input path="name" id="nameInput"/>
			</div>
			<div class="text col small col-2 mandatory <%= bindingResult.hasFieldErrors("administrationId") ? "error" : "" %>">
				<label for="administrationIdInput">Enhetsskod <strong><form:errors path="administrationId"/></strong></label>
				<form:input path="administrationId" id="administrationIdInput"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Lägg till ny enhet">
			</div>
		</div>	
	</form:form>
	<form:form id="change-unit-form" cssClass="form-general" modelAttribute="removeUnit" action="${removeUnitUrl}" >	
		<div class="row cols-1 cf">
			<div class="col full col-1">
				<fieldset>
					<legend class="wrap"><span>Välj enhet att ta bort</span></legend>
					<div class="fieldset-content">
						<c:forEach items="${units}" var="unit">
							<div class="radio">
								<form:radiobutton path="id" title="${unit.name}" value="${unit.id}" id="unit${unit.id}"/>
								<label for="unit${unit.id}">${unit.name} (${unit.administrationId})</label>
							</div>
						</c:forEach>	
					</div>
				</fieldset>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Ta bort vald enhet">
			</div>
		</div>		
	</form:form>
</div>
