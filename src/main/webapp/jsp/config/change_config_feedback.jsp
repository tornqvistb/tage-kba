<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:actionURL name="saveConfig" var="saveConfigUrl"/><%
%><portlet:renderURL portletMode="VIEW" var="cancelUrl"/><%
%><portlet:defineObjects/>

<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.configFeedback"); 
%>

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a href="${cancelUrl}">Avsluta administrationen</a></p>
	<h1>Administration</h1>
	<p>Konfiguration för synpunktshantering</p>

<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info error-message">
		<h2>Felaktigt inmatad konfiguration</h2>
		<p>Följande fel upptäcktes i dee inmatade fälten, rätta felen och försök igen.</p>
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
	<form:form id="change-texts-form" cssClass="form-general" modelAttribute="configFeedback" action="${saveConfigUrl}" >
		<p>Bekräftelsetexter</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmationText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmationText") ? "error" : "" %>">
				<label for="confirmationTextInput">Bekräftelsetext efter att skickat in en efterlysning <strong><form:errors path="confirmationText"/></strong></label>
				<form:textarea path="confirmationText" id="confirmationTextInput" cols="50" rows="10"/>
			</div>			
		</div>		
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>		
		<p>Konfiguration för e-postmeddelande som skickas</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="feedbackMailSender"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("feedbackMailSender") ? "error" : "" %>">
				<label for="feedbackMailSenderInput">Avsändaradress <strong><form:errors path="feedbackMailSender"/></strong></label>
				<form:input path="feedbackMailSender" id="feedbackMailSenderInput"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="feedbackMailReceiver"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("feedbackMailReceiver") ? "error" : "" %>">
				<label for="feedbackMailReceiver">Mottagaradress <strong><form:errors path="feedbackMailReceiver"/></strong></label>
				<form:input path="feedbackMailReceiver" id="feedbackMailReceiverInput"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="feedbackMailSubject"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("feedbackMailSubject") ? "error" : "" %>">
				<label for="feedbackMailSubjectInput">Rubrik<strong><form:errors path="feedbackMailSubject"/></strong></label>
				<form:input path="feedbackMailSubject" id="feedbackMailSubjectInput"/>
			</div>
		</div>
		<p>Följande ersättningsfält kan du använda i innehållstexten, de byts ut mot motsvarande text när epost-meddelandet skapas:<br/>
		Annonsens rubrik: <strong>{title}</strong>, bokarens namn: <strong>{bookerName}</strong>, bokarens telefon: <strong>{bookerPhone}</strong>, 
		bokarens epost-adress:<strong>{bookerMail}</strong>, annonsörens namn: <strong>{advertiserName}</strong>, 
		annonsörens telefon: <strong>{advertiserPhone}</strong>, annonsörens epost-adress: <strong>{advertiserMail}</strong>, 
		annonsens bild <strong>{image}</strong> och länk till annonsen: <strong>{link}</strong>. 
		HTML-taggar används enligt följande exempel: <strong>[div]</strong>lite innehåll<strong>[/div]</strong></p>		
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="feedbackMailBody"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("feedbackMailBody") ? "error" : "" %>">
				<label for="feedbackMailBodyInput">Innehåll <strong><form:errors path="feedbackMailBody"/></strong></label>
				<form:textarea path="feedbackMailBody" id="feedbackMailBodyInput" cols="50" rows="15"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="submit" value="uppdatera konfigurationen" name="submit-5086c4a3b380d">
				<a href="${cancelUrl}">Avbryt</a>
			</div>
		</div>
	</form:form>	
</div>
