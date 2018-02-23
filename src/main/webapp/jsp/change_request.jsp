<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="cancelUrl"><portlet:param name="externalPage" value=""/></portlet:renderURL><%
%><portlet:actionURL name="updateRequest" var="updateRequestUrl"/><%
%><portlet:resourceURL id="subCategories" cacheability="cacheLevelFull" var="subCatUrl"/><%
%><portlet:resourceURL id="uploadPhoto" var="uploadUrl"/><%
%><portlet:resourceURL id="removePhoto" var="removeUrl"/><%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="photo" cacheability="cacheLevelFull" var="photoUrl"/><%
%><portlet:defineObjects/>

<script type="text/javascript">
	window.urlConfig = {
		uploadUrl: "${uploadUrl}", 
		removeUrl: "${removeUrl}", 
		thumbnailUrl: "${thumbnailUrl}", 
		photoUrl: "${photoUrl}"
	};
</script>

<jsp:include page="include/incl_res.jsp" />
<% 
	org.springframework.validation.BindingResult bindingResult = 
		(org.springframework.validation.BindingResult)renderRequest.getAttribute("org.springframework.validation.BindingResult.request"); 
%>

<div id="content-primary" class="article cf" role="main">
	<div class="content-header cf">
		<a href="${cancelUrl}" class="button">Avbryt</a>
	</div>
	<h1>Ändra efterlysning</h1>
	
	<p>När du ändrat din efterlysning och sparat så kommer ändringarna synas direkt.</p>
	

<%
	if (bindingResult.hasErrors()) {
%>
	<div class="system-info error-message">
		<h2>Felaktigt inmatade efterlysningsuppgifter</h2>
		<p>Följande fel upptäcktes i efterlysningen, rätta felen och försök igen.</p>
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
	
	<form:form id="new-want-ad-form" cssClass="form-general" modelAttribute="request" action="${updateRequestUrl}" >
		<form:hidden path="id"/>
		<div class="row cols-2 cf">
			<div class="row cols-2 cf">
				<div class="select medium col col-1 mandatory <%= bindingResult.hasFieldErrors("topCategory") ? "error" : "" %>">
					<label for="5086c4a3b2949">Kategori <em>(obligatoriskt)</em> <strong><form:errors path="topCategory"/></strong></label>
					<form:select id="5086c4a3b2949" path="topCategory.id">
						<form:option value="-1">Välj kategori...</form:option>
						<form:options items="${topCategories}" itemLabel="title" itemValue="id"/>
					</form:select>
				</div>
				<div id="subCategories" class="select medium col col-2 mandatory <%= bindingResult.hasFieldErrors("category") ? "error" : "" %>">
					<label for="5086c4a3b2aae">Underkategori <em>(obligatoriskt)</em> <strong><form:errors path="category"/></strong></label>
					<form:select id="5086c4a3b2aae" path="category.id">
						<option value="-1" selected="selected">Välj underkategori...</option>
						<c:if test="${!empty subCategories}">
							<form:options items="${subCategories}" itemLabel="title" itemValue="id"/>
						</c:if>
					</form:select>
				</div>
			</div>
		</div>
		<div class="row cols-1 cf">
			<c:set var="err"><form:errors path="title"/></c:set> 
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("title") ? "error" : "" %>">
				<label for="5086c4a3b2bb2">Rubrik <em>(obligatoriskt)</em> <strong><form:errors path="title"/></strong></label>
				<form:input path="title" id="5086c4a3b2bb2"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col full col-1 mandatory <%= bindingResult.hasFieldErrors("title") ? "error" : "" %>">
				<label for="5086c4a3b2c09">Beskrivning <em>(obligatoriskt)</em> <strong><form:errors path="description"/></strong></label>
				<form:textarea path="description" cols="30" rows="10" id="5086c4a3b2c09"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="file col medium col-1">
				<label for="5086c4a3b3355">Ladda upp bild</label>
				<!-- upload -->
				<div style="display:table;" id="thumbnails"></div>
				<div style="width:140px; float:left" id="upload-area"><noscript>JavaScript måste vara påslaget för att kunna ladda upp foton!</noscript></div>
				<div style="height:30px; display:table-cell; vertical-align:middle;" id="upload-info"></div>
				<form:hidden id="photos" path="photos"/>

			</div>
		</div>		
		<div class="row cols-1 cf">
			<div class="col hr col-1">
				<hr>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="select large col col-1 mandatory">
				<label for="5086c4a3b2c60">Förvaltning/Bolag som efterlyser <em>(obligatoriskt)</em></label>
				<form:select id="5086c4a3b2c60" items="${units}" itemValue="id" itemLabel="name" path="unit.id"></form:select>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.name") ? "error" : "" %>">
				<label for="5086c4a3b306c">Kontaktperson <em>(obligatoriskt)</em> <strong><form:errors path="contact.name"/></strong></label>
				<form:input path="contact.name" id="5086c4a3b306c"/>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.phone") ? "error" : "" %>">
				<label for="5086c4a3b30c2">Telefonnummer <em>(obligatoriskt)</em> <strong><form:errors path="contact.phone"/></strong></label>
				<spring:bind path="contact.phone">
  					<input id="5086c4a3b30c2" name="contact.phone" type="tel" value="${request.contact.phone}" />
				</spring:bind>				
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="text col large col-1 mandatory <%= bindingResult.hasFieldErrors("contact.email") ? "error" : "" %>">
				<label for="5086c4a3b3119">E-postadress <em>(obligatoriskt)</em> <strong><form:errors path="contact.email"/></strong></label>
				<spring:bind path="contact.email">				
  					<input id="5086c4a3b3119" name="contact.email" type="email" value="${request.contact.email}" />
				</spring:bind>				
			</div>
		</div>						
		
		<p><span class="author">Efterlysningen skapad av ${request.creatorUid}.</span></p>
		<div class="row cols-1 cf">
			<div class="col medium col-1 submit-area">
				<input type="submit" value="Uppdatera efterlysning" name="submit-509ae872593b6">
				<a href="${cancelUrl}">Avbryt</a>
			</div>
		</div>
	</form:form>
</div>
<script type="text/javascript">
	$(document).ready(initFileUploader("<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/img/loading.gif") %>"));
	$("#5086c4a3b2949").change(function() {
		$.ajax({
			type: "POST",
			url: "${subCatUrl}", 
			dataType: "html",
			data: { parent: $(this).find(":selected").val() },
			success: function(result) {
				$("#5086c4a3b2aae").html("<option value=\"-1\" selected=\"selected\">Välj underkategori...</option>" + result);
				$("#5086c4a3b2aae").trigger("change");
			},
			error: function(result) {
				alert("Ett fel uppstod på servern underkategorier hämtades: " + result.responseText);
			}
		});
	});
</script>