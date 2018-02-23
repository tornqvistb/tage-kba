<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="subCategories" var="subCatUrl"/><%
%><portlet:renderURL var="cancelUrl" /><%
%><portlet:renderURL var="listMyAdsUrl"><portlet:param name="page" value="listMyAds"/></portlet:renderURL><%
%><portlet:actionURL name="filterMyBookings" var="filterUrl"/><%
%><portlet:defineObjects/>

<jsp:include page="include/incl_res.jsp" />

<div id="content-primary" class="cf" role="main">
	<div class="content-header cf">
		<a href="${cancelUrl}" class="button">Tillbaka</a>
	</div>
	<h1>Mina bokningar</h1>
	<p>Här visas de annonser som du har bokat.</p>
	<h2>Sök annonser</h2>
	<form:form id="search-inventory-form" cssClass="form-general" modelAttribute="searchFilter" action="${filterUrl}" >
		<div class="row cols-3 cf">
			<div class="select medium col col-1">
				<label for="508e6b767babe">Kategori</label>
				<form:select id="508e6b767babe" path="topCategory.id">
					<form:option value="-1" selected="selected">Alla</form:option>
					<form:options items="${topCategories}" itemLabel="title" itemValue="id"/>
				</form:select>
			</div>
			<div class="select medium col col-2">
				<label for="508e6b767bc24">Underkategori</label>
				<form:select id="508e6b767bc24" path="subCategory.id">
					<option value="-1" selected="selected">Alla</option>
					<c:if test="${!empty subCategories}">
						<form:options items="${subCategories}" itemLabel="title" itemValue="id"/>
					</c:if>
				</form:select>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col small col-1 submit-area">
				<input type="submit" value="Visa" name="submit-508e6b767c18f">
			</div>
		</div>
	</form:form>
	<h2><span id="headerTitle">Mina bokningar</span></h2>
	<c:if test="${fn:length(ads.list) gt 0}">
		<ul class="inventory-listing">
			<c:forEach items="${ads.list}" var="ad">
				<portlet:renderURL var="viewAdUrl">
					<portlet:param name="page" value="viewAd"/>
					<portlet:param name="advertisementId" value="${ad.id}"/>
					<portlet:param name="previousPage" value="listMyAds" />
					<portlet:param name="pageIdx" value="${ads.page}"/>
				</portlet:renderURL>
				
				<c:if test="${fn:length(ad.photos) gt 0}">
					<c:forEach var="photo" items="${ad.photos}" end="0">
		     			<c:set var="imageUrl" value="${thumbnailUrl}&id=${photo.id}"/>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(ad.photos) eq 0}">
	     			<c:set var="imageUrl" value="${thumbnailUrl}&id="/>
				</c:if>
	
				<li class="cf">
					<div class="image">
						<a href="${viewAdUrl}"><img src="${imageUrl}" alt=""></a>
					</div>
					<div class="content">
						<div class="meta"><span class="date">${ad.formattedCreatedDate}</span></div>
						<h3><a href="${viewAdUrl}">${ad.title}</a></h3>
						<div class="meta"><span class="author">Upplagd av ${ad.unit.name}</span></div>
						<div class="meta"><span class="category">${ad.category.parent.title} <span class="sep">&gt;</span> ${ad.category.title}</span></div>
					</div>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${ads.numberOfPages > 1}">
			<div class="paging cf">
				<strong>Gå till sida:</strong>
				<ul>
					<c:if test="${ads.hasPrevious}">
						<portlet:actionURL name="filterMyBookings" var="prevUrl"><portlet:param name="pageIdx" value="${ads.page-1}"/></portlet:actionURL>
						<portlet:actionURL name="filterMyBookings" var="firstUrl"><portlet:param name="pageIdx" value="1"/></portlet:actionURL>
						<li><a href="${firstUrl}" title="Länk till första sidan">&lt;&lt;</a></li>
						<li><a href="${prevUrl}" title="Länk till föregående sida">&lt;</a></li>					
					</c:if>
					<c:forEach var="pageIdx" begin="1" end="${ads.numberOfPages}">
						<c:if test="${pageIdx eq ads.page}">
							<li>${pageIdx}</li>
						</c:if>
						<c:if test="${pageIdx ne ads.page}">
							<portlet:actionURL name="filterMyBookings" var="pageUrl"><portlet:param name="pageIdx" value="${pageIdx}"/></portlet:actionURL>
							<li><a href="${pageUrl}">${pageIdx}</a></li>
						</c:if>
					</c:forEach>
					<c:if test="${ads.hasNext}">
						<portlet:actionURL name="filterMyBookings" var="nextUrl"><portlet:param name="pageIdx" value="${ads.page+1}"/></portlet:actionURL>
						<portlet:actionURL name="filterMyBookings" var="lastUrl"><portlet:param name="pageIdx" value="${ads.numberOfPages}"/></portlet:actionURL>
						<li><a href="${nextUrl}" title="Länk till nästa sida">&gt;</a></li>
						<li><a href="${lastUrl}" title="Länk till sista sidan">&gt;&gt;</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>		
	</c:if>
	<c:if test="${fn:length(ads.list) eq 0}">
		<div>Inga annonser hittades.</div>
	</c:if>		
</div>

<script type="text/javascript">
	// construct the header based on filter selections
	$(document).ready(function() {
		if ($("#508e6b767babe").val() != -1) {
			$("#headerTitle").append(" i kategori " + $("#508e6b767babe option:selected").text());
		}
		if ($("#508e6b767bc24").val() != -1) {
			$("#headerTitle").append(" > " + $("#508e6b767bc24 option:selected").text());
		}
	});
	
	$("#508e6b767babe").change(function() {
		$.ajax({
			type: "POST",
			url: "${subCatUrl}", 
			dataType: "html",
			data: { parent: $(this).find(":selected").val() },
			success: function(result) {
				$("#508e6b767bc24").html("<option value=\"-1\" selected=\"selected\">Alla</option>" + result);
				$("#508e6b767bc24").trigger("change");
			},
			error: function(result) {
				alert("Ett fel uppstod på servern när underkategorier hämtades: " + result.responseText);
			}
		});
	});
</script>