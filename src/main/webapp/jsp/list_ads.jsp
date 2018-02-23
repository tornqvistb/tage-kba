<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="subCategories" var="subCatUrl"/><%
%><portlet:renderURL var="createAdUrl"><portlet:param name="page" value="createAdConfirm"/></portlet:renderURL><%
%><portlet:renderURL var="listMyAdsUrl"><portlet:param name="page" value="listMyAds"/></portlet:renderURL><%
%><portlet:renderURL var="listMyBookingsUrl"><portlet:param name="page" value="listMyBookings"/></portlet:renderURL><%
%><portlet:actionURL name="filterAds" var="filterUrl"/><%
%><portlet:defineObjects/>


<jsp:include page="include/incl_res.jsp" />

<c:if test="${empty wp.moduleList.current.capabilities['tage']}">
	<!-- Systemmodulen tage kunde inte hittas och läsas in -->
</c:if>
<div id="content-primary" class="cf" role="main">
	<div class="content-header cf" id="mobile-request-link-area">
		<a href="#m-requested-items" class="right">Till efterlysningar ></a>
	</div>
	<div class="content-header cf">
		<a href="${listMyBookingsUrl}" class="button">Mina bokningar</a>
		<a href="${listMyAdsUrl}" class="button">Mina annonser</a>		
		<a href="${createAdUrl}" class="button primary-button">Lägg in annons</a>
	</div>	
	<h1>Annonser</h1>
	${texts.startPageIngress}
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
			<div class="select medium col col-3">
				<label for="508e6b767bd2c">Förvaltning/Bolag</label>
				<form:select id="508e6b767bd2c" path="unit.id">
					<option value="-1" selected="selected">Alla</option>
					<form:options items="${units}" itemLabel="name" itemValue="id"/>
				</form:select>
			</div>
		</div>
		<div class="row cols-1 cf">
			<div class="col small col-1 submit-area">
				<input type="submit" value="Visa" name="submit-508e6b767c18f">
			</div>
		</div>
	</form:form>
	<h2><span id="headerTitle">Alla annonser</span></h2>
	<c:if test="${fn:length(ads.list) gt 0}">
		<ul class="inventory-listing">
			<c:forEach items="${ads.list}" var="ad">
				<portlet:renderURL var="viewAdUrl">
					<portlet:param name="page" value="viewAd"/>
					<portlet:param name="previousPage" value="listAds"/>
					<portlet:param name="pageIdx" value="${ads.page}"/>
					<portlet:param name="advertisementId" value="${ad.id}"/>
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
						<div class="meta"><span class="date">${ad.formattedPublishDate}</span></div>
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
						<portlet:actionURL name="filterAds" var="prevUrl"><portlet:param name="pageIdx" value="${ads.page-1}"/></portlet:actionURL>
						<portlet:actionURL name="filterAds" var="firstUrl"><portlet:param name="pageIdx" value="1"/></portlet:actionURL>
						<li><a href="${firstUrl}" title="Länk till första sidan">&lt;&lt;</a></li>
						<li><a href="${prevUrl}" title="Länk till föregående sida">&lt;</a></li>					
					</c:if>
					<c:forEach var="pageIdx" begin="1" end="${ads.numberOfPages}">
						<c:if test="${pageIdx eq ads.page}">
							<li>${pageIdx}</li>
						</c:if>
						<c:if test="${pageIdx ne ads.page}">
							<portlet:actionURL name="filterAds" var="pageUrl"><portlet:param name="pageIdx" value="${pageIdx}"/></portlet:actionURL>
							<li><a href="${pageUrl}">${pageIdx}</a></li>
						</c:if>
					</c:forEach>
					<c:if test="${ads.hasNext}">
						<portlet:actionURL name="filterAds" var="nextUrl"><portlet:param name="pageIdx" value="${ads.page+1}"/></portlet:actionURL>
						<portlet:actionURL name="filterAds" var="lastUrl"><portlet:param name="pageIdx" value="${ads.numberOfPages}"/></portlet:actionURL>
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
		if ($("#508e6b767bd2c").val() != -1) {
			$("#headerTitle").append(" från förvaltning/bolag " + $("#508e6b767bd2c option:selected").text());
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