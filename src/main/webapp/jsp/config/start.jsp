<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="changeConfigUrl"><portlet:param name="page" value="changeConfig"/></portlet:renderURL><%
%><portlet:renderURL var="changeTextsUrl"><portlet:param name="page" value="changeTexts"/></portlet:renderURL><%
%><portlet:renderURL var="adminUnitsUrl"><portlet:param name="page" value="adminUnits"/></portlet:renderURL><%
%><portlet:renderURL var="adminCategoriesUrl"><portlet:param name="page" value="adminCategories"/></portlet:renderURL><%
%><portlet:renderURL var="adminAdsUrl"><portlet:param name="page" value="adminAds"/></portlet:renderURL><%
%><portlet:renderURL var="adminRequestsUrl"><portlet:param name="page" value="adminRequests"/></portlet:renderURL><%
%><portlet:renderURL var="statisticsUrl"><portlet:param name="page" value="statistics"/></portlet:renderURL><%
%><portlet:renderURL var="advancedStatisticsUrl"><portlet:param name="page" value="advancedStatistics"/></portlet:renderURL><%
%><portlet:defineObjects/>

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a href="<portlet:renderURL portletMode="VIEW"/>">Avsluta administrationen</a></p>
	<h1>Administration</h1>
	<p>
		Välj en administrationsåtgärd nedan:
		<ul>
			<li><a href="${changeConfigUrl}">Ändra konfigurationsdata</a></li>
			<li><a href="${changeTextsUrl}">Ändra texter</a></li>
			<li><a href="${adminUnitsUrl}">Administrera enheter</a></li>
			<li><a href="${adminCategoriesUrl}">Administrera kategorier</a></li>
			<li><a href="${adminAdsUrl}">Administrera annonser</a></li>
			<li><a href="${adminRequestsUrl}">Administrera efterlysningar</a></li>
		</ul>
	</p>
	<p>
		Övrigt:
		<ul>
			<li><a href="${statisticsUrl}">Visa statistik</a></li>
			<li><a href="${advancedStatisticsUrl}">Exportera statistik till excel</a></li>			
		</ul>
	</p>	
</div>
