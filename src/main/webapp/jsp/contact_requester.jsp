<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=ISO-UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:actionURL name="sendMail" var="sendMailUrl" /><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>


<jsp:include page="include/incl_res.jsp" />
<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.requestMailContact"); 
%>

<div id="content-primary" class="article cf" role="main">
	
	<div class="content-header cf">
		<a href="${cancelUrl}" class="button">Avbryt</a>
	</div>
	<h1>Kontakta efterlysaren av: ${request.title}</h1>
<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info error-message">
		<h2>Felaktigt inmatade uppgifter</h2>
		<p>Följande fel upptäcktes i kontaktformuläret, rätta felen och försök igen.</p>
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
	
	<form:form id="contact-requester-form" cssClass="form-general" modelAttribute="requestMailContact" action="${sendMailUrl}" >
		<h2>Beskrivning av efterlysningen</h2>
		<p>${request.description}</p>
	
		<form:hidden path="requestId"/>
		<form:hidden path="requestTitle"/>

		<h2>Meddelande till efterlysaren</h2>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("message") ? "error" : "" %>">
				<form:textarea path="message" id="message" cols="50" rows="10"/>
			</div>
		</div>
		
		<h2>Kontaktuppgifter till mig</h2>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.name") ? "error" : "" %>">
				<label for="50911f89c015f">Mitt namn <em>(obligatoriskt)</em> <strong><form:errors path="contact.name"/></strong></label>
				<form:input path="contact.name" id="50911f89c015f"/>
			</div>
		</div>
		
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.phone") ? "error" : "" %>">
				<label for="50911f89c01d9">Telefonnummer <em>(obligatoriskt)</em> <strong><form:errors path="contact.phone"/></strong></label>
				<form:input path="contact.phone" id="50911f89c01d9"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.email") ? "error" : "" %>">
				<label for="50911f89c029f">E-postadress <em>(obligatoriskt)</em> <strong><form:errors path="contact.email"/></strong></label>
				<form:input path="contact.email" id="50911f89c029f"/>
			</div>
		</div>				
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Skicka meddelande" name="submit-50911f89c035a">
				<a href="${cancelUrl}">Avbryt</a>
			</div>
		</div>
	</form:form>
</div>