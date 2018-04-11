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
	<portlet:param name="page" value="${previousPage}"/>
	<portlet:param name="pageIdx" value="${pageIdx}"/>
</portlet:renderURL>


<c:if test="${not empty advertisement}">
	<div id="content-primary" class="article cf" role="main">
		<div class="content-header cf">
			<a href="${backUrl}" class="button">Tillbaka</a>
		</div>	
		<h1>${advertisement.title}</h1>
		<div class="inventory-info">
			<c:if test="${fn:length(advertisement.photos) ne 0}">
				<div class="flexslider">
					<ul class="slides cf">
						<c:forEach items="${advertisement.photos}" var="photo">
							<li data-thumb="${thumbnailUrl}&id=${photo.id}">
								<img src="${photoUrl}&id=${photo.id}" alt="" />
							</li>
						</c:forEach>				
					</ul>
				</div>
			</c:if>
			<c:if test="${advertisement.status eq 'PUBLISHED'}">
				<c:set var="statusTitle" value="PUBLICERAD" />
				<c:set var="statusClass" value="status-text-published" />
			</c:if>				
			<c:if test="${advertisement.status eq 'EXPIRED'}">
				<c:set var="statusTitle" value="UTGÅNGEN" />
				<c:set var="statusClass" value="status-text-expired" />
			</c:if>				
			<c:if test="${advertisement.status eq 'BOOKED'}">
				<c:set var="statusTitle" value="BOKAD" />
				<c:set var="statusClass" value="status-text-booked" />
			</c:if>			
			<c:if test="${advertisement.status eq 'UNPUBLISHED'}">
				<c:set var="statusTitle" value="AVPUBLICERAD" />
				<c:set var="statusClass" value="status-text-expired" />
			</c:if>						
			<p class="meta"><span class="date">${advertisement.formattedCreatedDate}</span> | <span class="category">${advertisement.category.parent.title} <span class="sep">&gt;</span> ${advertisement.category.title}</span> | <span class="${statusClass}">${statusTitle}</span></p>
			<p class="meta">Bortskänkes av <strong>${advertisement.unit.name}</strong></p>
			<p>${advertisement.description}</p>
			<p class="meta">Antal: <strong>${advertisement.count}</strong></p>
			<h2>Information om hämtning</h2>
			<div class="info-box">
				<p><strong>Hämtningsadress: </strong>${advertisement.pickupAddress}</p>
				<p><strong>Hämtningsvillkor: </strong>${advertisement.pickupConditions}</p>
			</div>
			<h2>Kontaktuppgifter</h2>
			<div class="info-box contact-person">
				<p><strong>Kontaktperson/Verksamhet:</strong> ${advertisement.contact.name}</p>
				<p><strong>Förvaltning/Bolag:</strong> ${advertisement.unit.name}</p>
				<p><strong>E-post: </strong><a href="mailto:${advertisement.contact.email}">${advertisement.contact.email}</a></p>
				<p><strong>Tel/Mobil: </strong>${advertisement.contact.phone}</p>
				<p><strong>Adress: </strong>${advertisement.pickupAddress}</p>
			</div>
			<div class="created-by">
				<span class="author">Annonsen skapad av: ${adCreator.name}</span><br />
				<span class="unit">Förvaltning/Bolag: ${advertisement.unit.name}</span><br />
				<span class="email">E-post: <a href="mailto:${adCreator.email}">${adCreator.email}</a></span>			
			</div>			
			<c:if test="${advertisement.booked}">
				<h2>Bokad av</h2>				
				<div class="contact-person">
					<span>${advertisement.booker.name}</span>
					<span>${advertisement.booker.phone}</span>
					<div class="email"><a href="mailto:${advertisement.booker.email}">${advertisement.booker.email}</a></div>
				</div>
			</c:if>
			<c:if test="${!advertisement.booked}">
				<portlet:renderURL var="bookUrl">
					<portlet:param name="page" value="bookAd"/>
					<portlet:param name="advertisementId" value="${advertisement.id}"/>
				</portlet:renderURL>
				<a href="${bookUrl}" class="button">Boka</a>
			</c:if>
			<c:if test="${userId eq advertisement.creatorUid}">
				<c:if test="${!advertisement.published}">
					<portlet:renderURL var="republishAdUrl">
						<portlet:param name="page" value="republishAd"/>
						<portlet:param name="advertisementId" value="${advertisement.id}"/>
					</portlet:renderURL>
					<a href="${republishAdUrl}" class="button">Återpublicera</a>
				</c:if>
				<portlet:actionURL name="loadAd" var="changeAdUrl">
					<portlet:param name="advertisementId" value="${advertisement.id}"/>
				</portlet:actionURL>
				<a href="${changeAdUrl}" class="button">Redigera</a>
				<c:if test="${advertisement.published}">
					<portlet:renderURL var="extendAdUrl">
						<portlet:param name="page" value="extendAd"/>
						<portlet:param name="advertisementId" value="${advertisement.id}"/>
					</portlet:renderURL>
					<a href="${extendAdUrl}" class="button">Förläng annonsen</a>
					<portlet:renderURL var="unpublishAdUrl">
						<portlet:param name="page" value="unpublishAd"/>
						<portlet:param name="advertisementId" value="${advertisement.id}"/>
					</portlet:renderURL>
					<a href="${unpublishAdUrl}" class="button">Avpublicera annonsen</a>					
				</c:if>
			</c:if>
		</div>
		<a href="<portlet:renderURL>
					<portlet:param name="page" value=""/>
				</portlet:renderURL>" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>
<c:if test="${empty advertisement}">
	<div id="content-primary" class="article cf" role="main">
		<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
		<h1>Annonsen kunde inte hittas</h1>
		<div class="inventory-info">
			Den begärda annonsen kunde inte hittas i systemet. Detta innebär förmodligen att den har tagits bort, eller att länken du klickat på är felaktig.
		</div>
		<a href="<portlet:renderURL>
					<portlet:param name="page" value=""/>
				</portlet:renderURL>" class="button">Tillbaka till startsidan</a>
	</div>
</c:if>