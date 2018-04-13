<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="photo" cacheability="cacheLevelFull" var="photoUrl"/><%
%><portlet:defineObjects/> 

<jsp:include page="include/incl_res.jsp" />

<portlet:renderURL var="backUrl">
	<c:if test="${not empty previousPage}">
		<portlet:param name="page" value="${previousPage}"/>
	</c:if>
	<portlet:param name="externalPage" value=""/>
</portlet:renderURL>

<c:if test="${not empty request}">
	<div id="content-primary" class="article cf" role="main">
		<div class="content-header cf">
			<a href="${backUrl}" class="button">Tillbaka</a>
		</div>	
		<h1>${request.title}</h1>
		<div class="inventory-info">		
			<p class="meta"><span class="date">${request.formattedCreatedDate}</span> | <span class="category">${request.category.parent.title} <span class="sep">&gt;</span> ${request.category.title}</span></p>
			<p class="meta">Efterlyses av <strong>${request.unit.name}</strong></p>
			<p>${request.descriptionDisplay}</p>
			<c:if test="${fn:length(request.photos) ne 0}">
				<div class="flexslider">
					<ul class="slides cf">
						<c:forEach items="${request.photos}" var="photo">
							<li data-thumb="${thumbnailUrl}&id=${photo.id}">
								<img src="${photoUrl}&id=${photo.id}" alt="" />
							</li>
						</c:forEach>				
					</ul>
				</div>
			</c:if>	
			<h2>Kontaktuppgifter</h2>
			<div class="info-box contact-person">
				<p><strong>Kontaktperson/Verksamhet:</strong> ${request.contact.name}</p>
				<p><strong>Förvaltning/Bolag:</strong> ${request.unit.name}</p>
				<p><strong>E-post: </strong><a href="mailto:${request.contact.email}">${request.contact.email}</a></p>
				<p><strong>Tel/Mobil: </strong>${request.contact.phone}</p>
			</div>
			<div class="created-by">
				<span class="author">Efterlysningen skapad av: ${requestCreator.name}</span><br />
				<span class="unit">Förvaltning/Bolag: ${request.unit.name}</span><br />
				<span class="email">E-post: <a href="mailto:${requestCreator.email}">${requestCreator.email}</a></span>			
			</div>			
			
			<c:if test="${userId eq request.creatorUid}">
				<c:if test="${request.published}">
					<portlet:renderURL var="expireRequestUrl">
						<portlet:param name="page" value="expireRequest"/>
						<portlet:param name="requestId" value="${request.id}"/>
					</portlet:renderURL>
					<a href="${expireRequestUrl}" class="button">Ta bort efterlysning</a>
				</c:if>
				<portlet:actionURL name="loadRequest" var="changeRequestUrl">
					<portlet:param name="requestId" value="${request.id}"/>
				</portlet:actionURL>
				<a href="${changeRequestUrl}" class="button">Redigera</a>
			</c:if>
			<c:if test="${userId ne request.creatorUid}">
				<portlet:renderURL var="contactRequesterUrl">
					<portlet:param name="page" value="contactRequester"/>
					<portlet:param name="requestId" value="${request.id}"/>
				</portlet:renderURL>
				<a href="${contactRequesterUrl}" class="button">Kontakta efterlysaren</a>
			</c:if>			
		</div>
		<a href="${backUrl}" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>
<c:if test="${empty request}">
	<div id="content-primary" class="article cf" role="main">
		<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
		<h1>Efterlysningen kunde inte hittas</h1>
		<div class="inventory-info">
			Den begärda efterlysningen kunde inte hittas i systemet. Detta innebär förmodligen att den har tagits bort, eller att länken du klickat på är felaktig.
		</div>
		<a href="${cancelUrl}" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>