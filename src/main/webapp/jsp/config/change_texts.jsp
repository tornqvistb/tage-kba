<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:actionURL name="saveTexts" var="saveTextsUrl"/><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/>

<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.config"); 
%>

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
	<h1>Administration - ändra texter</h1>
	<p>Ändra de applikationstexter som används i Tage</p>
	
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
	
	<form:form id="change-texts-form" cssClass="form-general" modelAttribute="texts" action="${saveTextsUrl}" >
		<p>Brödtexter</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="startPageIngress"/></c:set> 
			<div class="text col col-1 mandatory <%= bindingResult.hasFieldErrors("startPageIngressText") ? "error" : "" %>">
				<label for="startPageIngressInput">Ingress startsida <strong><form:errors path="startPageIngress"/></strong></label>
				<form:textarea class="ckeditor" path="startPageIngress" id="startPageIngressInput" cols="70" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<p>Godkännandetexter</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmCreateAdText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmCreateAdText") ? "error" : "" %>">
				<label for="confirmCreateAdTextInput">Skapa annons <strong><form:errors path="confirmCreateAdText"/></strong></label>
				<form:textarea path="confirmCreateAdText" id="confirmCreateAdTextInput" cols="50" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmBookingText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmBookingText") ? "error" : "" %>">
				<label for="confirmBookingTextInput">Boka annons <strong><form:errors path="confirmBookingText"/></strong></label>
				<form:textarea path="confirmBookingText" id="confirmBookingTextInput" cols="50" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmRepublishText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmRepublishText") ? "error" : "" %>">
				<label for="confirmRepublishTextInput">Återpublicering <strong><form:errors path="confirmRepublishText"/></strong></label>
				<form:textarea path="confirmRepublishText" id="confirmRepublishTextInput" cols="50" rows="10"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="confirmRemoveRequestText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("confirmRemoveRequestText") ? "error" : "" %>">
				<label for="confirmRemoveRequestTextInput">Ta bort efterlysning <strong><form:errors path="confirmRemoveRequestText"/></strong></label>
				<form:textarea path="confirmRemoveRequestText" id="confirmRemoveRequestTextInput" cols="50" rows="10"/>
			</div>
		</div>			
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<p>Bekräftelsetexter</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="bookingConfirmationText"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("bookingConfirmationText") ? "error" : "" %>">
				<label for="bookingConfirmationTextInput">Bokningsbekräftelse <strong><form:errors path="bookingConfirmationText"/></strong></label>
				<form:textarea path="bookingConfirmationText" id="bookingConfirmationTextInput" cols="50" rows="10"/>
			</div>
		</div>			
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<p>Epost-utskick vid bokning av annons</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSenderAddress"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSenderAddress") ? "error" : "" %>">
				<label for="mailSenderAddressInput">Avsändare <strong><form:errors path="mailSenderAddress"/></strong></label>
				<form:input path="mailSenderAddress" id="mailSenderAddressInput"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSubject"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSubject") ? "error" : "" %>">
				<label for="mailSubjectInput">Rubrik<strong><form:errors path="mailSubject"/></strong></label>
				<form:input path="mailSubject" id="mailSubjectInput"/>
			</div>
		</div>
		<p>Följande ersättningsfält kan du använda i innehållstexten, de byts ut mot motsvarande text när epost-meddelandet skapas:<br/>
		Annonsens rubrik: <strong>{title}</strong>, bokarens namn: <strong>{bookerName}</strong>, bokarens telefon: <strong>{bookerPhone}</strong>, 
		bokarens epost-adress:<strong>{bookerMail}</strong>, annonsörens namn: <strong>{advertiserName}</strong>, 
		annonsörens telefon: <strong>{advertiserPhone}</strong>, annonsörens epost-adress: <strong>{advertiserMail}</strong>, 
		annonsens bild <strong>{image}</strong>, länk till regelsida <strong>{rules}</strong> och länk till annonsen: <strong>{link}</strong>. 
		HTML-taggar används enligt följande exempel: <strong>[div]</strong>lite innehåll<strong>[/div]</strong></p>		
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailBody"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailBody") ? "error" : "" %>">
				<label for="mailBodyInput">Innehåll <strong><form:errors path="mailBody"/></strong></label>
				<form:textarea path="mailBody" id="mailBodyInput" cols="50" rows="15"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>

		<p>Epost-utskick vid skapande av annons</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSenderAddressNewAd"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSenderAddressNewAd") ? "error" : "" %>">
				<label for="mailSenderAddressInputNewAd">Avsändare <strong><form:errors path="mailSenderAddressNewAd"/></strong></label>
				<form:input path="mailSenderAddressNewAd" id="mailSenderAddressInputNewAd"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSubjectNewAd"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSubjectNewAd") ? "error" : "" %>">
				<label for="mailSubjectInputNewAd">Rubrik<strong><form:errors path="mailSubjectNewAd"/></strong></label>
				<form:input path="mailSubjectNewAd" id="mailSubjectInputNewAd"/>
			</div>
		</div>
		<p>Följande ersättningsfält kan du använda i innehållstexten, de byts ut mot motsvarande text när epost-meddelandet skapas:<br/>
		Annonsens rubrik: <strong>{title}</strong>, annonsörens namn: <strong>{advertiserName}</strong>, 
		annonsörens telefon: <strong>{advertiserPhone}</strong>, annonsörens epost-adress: <strong>{advertiserMail}</strong>, 
		annonsens bild <strong>{image}</strong>, länk till regelsida <strong>{rules}</strong> och länk till annonsen: <strong>{link}</strong>. 
		HTML-taggar används enligt följande exempel: <strong>[div]</strong>lite innehåll<strong>[/div]</strong></p>		
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailBodyNewAd"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailBodyNewAd") ? "error" : "" %>">
				<label for="mailBodyInputNewAd">Innehåll <strong><form:errors path="mailBodyNewAd"/></strong></label>
				<form:textarea path="mailBodyNewAd" id="mailBodyInputNewAd" cols="50" rows="15"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>

		<p>Epost-utskick vid kontakt med efterlysare</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSenderAddressRequest"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSenderAddressRequest") ? "error" : "" %>">
				<label for="mailSenderAddressInputRequest">Avsändare <strong><form:errors path="mailSenderAddressRequest"/></strong></label>
				<form:input path="mailSenderAddressRequest" id="mailSenderAddressInputRequest"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSubjectRequest"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSubjectRequest") ? "error" : "" %>">
				<label for="mailSubjectInputRequest">Rubrik<strong><form:errors path="mailSubjectRequest"/></strong></label>
				<form:input path="mailSubjectRequest" id="mailSubjectInputRequest"/>
			</div>
		</div>
		<p>Följande ersättningsfält kan du använda i innehållstexten, de byts ut mot motsvarande text när epost-meddelandet skapas:<br/>
		Efterlysningens rubrik: <strong>{title}</strong>, svarandes namn: <strong>{respondentName}</strong>, svarandes telefon: <strong>{respondentPhone}</strong>, 
		svarandes epost-adress:<strong>{respondentMail}</strong>, efterlysarens namn: <strong>{requesterName}</strong>, 
		efterlysarens telefon: <strong>{requesterPhone}</strong>, efterlysarens epost-adress: <strong>{requesterMail}</strong>, 
		efterlysningens bild <strong>{image}</strong>, länk till regelsida <strong>{rules}</strong>, meddelande <strong>{message}</strong> och länk till efterlysningen: <strong>{link}</strong>. 
		HTML-taggar används enligt följande exempel: <strong>[div]</strong>lite innehåll<strong>[/div]</strong></p>		
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailBodyRequest"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailBodyRequest") ? "error" : "" %>">
				<label for="mailBodyInputRequest">Innehåll <strong><form:errors path="mailBodyRequest"/></strong></label>
				<form:textarea path="mailBodyRequest" id="mailBodyInputRequest" cols="50" rows="15"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		
		<p>Epost-utskick vid skapande av efterlysning</p>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSenderAddressNewRequest"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSenderAddressNewRequest") ? "error" : "" %>">
				<label for="mailSenderAddressInputNewRequest">Avsändare <strong><form:errors path="mailSenderAddressNewRequest"/></strong></label>
				<form:input path="mailSenderAddressNewRequest" id="mailSenderAddressInputNewRequest"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailSubjectNewRequest"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailSubjectNewRequest") ? "error" : "" %>">
				<label for="mailSubjectInputNewRequest">Rubrik<strong><form:errors path="mailSubjectNewRequest"/></strong></label>
				<form:input path="mailSubjectNewRequest" id="mailSubjectInputNewRequest"/>
			</div>
		</div>
		<p>Följande ersättningsfält kan du använda i innehållstexten, de byts ut mot motsvarande text när epost-meddelandet skapas:<br/>
		Annonsens rubrik: <strong>{title}</strong>, annonsörens namn: <strong>{advertiserName}</strong>, 
		annonsörens telefon: <strong>{advertiserPhone}</strong>, annonsörens epost-adress: <strong>{advertiserMail}</strong>, 
		annonsens bild <strong>{image}</strong>, länk till regelsida <strong>{rules}</strong> och länk till annonsen: <strong>{link}</strong>. 
		HTML-taggar används enligt följande exempel: <strong>[div]</strong>lite innehåll<strong>[/div]</strong></p>		
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="mailBodyNewRequest"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("mailBodyNewRequest") ? "error" : "" %>">
				<label for="mailBodyInputNewRequest">Innehåll <strong><form:errors path="mailBodyNewRequest"/></strong></label>
				<form:textarea path="mailBodyNewRequest" id="mailBodyInputNewRequest" cols="50" rows="15"/>
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
