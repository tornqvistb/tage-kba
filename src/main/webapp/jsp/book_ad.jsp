<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=ISO-UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:actionURL name="performBooking" var="bookAdUrl" /><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>


<jsp:include page="include/incl_res.jsp" />
<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.booking"); 
%>

<div id="content-primary" class="article cf" role="main">
	<div class="content-header cf">
		<a href="${cancelUrl}" class="button">Avbryt</a>
	</div>
	<h1>Boka ${booking.advertisementTitle}</h1>

<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info error-message">
		<h2>Felaktigt inmatade bokningsuppgifter</h2>
		<p>Följande fel upptäcktes i bokningen, rätta felen och försök igen.</p>
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
	
	<form:form id="book-inventory-form" cssClass="form-general" modelAttribute="booking" action="${bookAdUrl}" >
		<form:hidden path="advertisementId"/>
		<form:hidden path="advertisementTitle"/>
		<p>${texts.confirmBookingText}</p>
		<div class="row cols-1 cf">
			<div class="col col-1">
				<div class="checkbox alt">
					<form:checkbox path="transportationConfirmed" id="checkbox1-50911f89bfed7"/>
					<label for="checkbox1-50911f89bfed7">Jag förstår att transport måste ordnas</label>
				</div>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
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
				<spring:bind path="contact.phone">
  					<input id="50911f89c01d9" name="contact.phone" type="tel" value="${booking.contact.phone}" />
				</spring:bind>				
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.email") ? "error" : "" %>">
				<label for="50911f89c029f">E-postadress <em>(obligatoriskt)</em> <strong><form:errors path="contact.email"/></strong></label>
				<spring:bind path="contact.email">				
  					<input id="50911f89c029f" name="contact.email" type="email" value="${booking.contact.email}" />
				</spring:bind>				
			</div>
		</div>				
		<p class="meta">Kvarvarande antal på annonsen: <strong>${booking.adQuantity}</strong></p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="bookedQuantity"/></c:set> 
			<div class="text col small col-1 mandatory <%= bindingResult.hasFieldErrors("bookedQuantity") ? "error" : "" %>">
				<label for="5086c4a3b2123">Antal att boka <em>(obligatoriskt)</em> <strong><form:errors path="bookedQuantity"/></strong></label>
				<form:input path="bookedQuantity" id="5086c4a3b2123" type="number"/>
			</div>
		</div>		
		
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Bekräfta bokningen" name="submit-50911f89c035a">
				<a href="${cancelUrl}">Avbryt</a>
			</div>
		</div>
	</form:form>
</div>