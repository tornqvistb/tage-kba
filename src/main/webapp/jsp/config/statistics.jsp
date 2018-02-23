<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:defineObjects/>

<div id="content-primary" class="cf" role="main">
	<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
	<div class="content-header cf">
		<h1>Statistik</h1>
	</div>
	<div style="font-size: 12pt;">Antal unika besökare på funktionen: <strong>${uniqueVisitors}</strong></div>
	<h2>Upplagda annonser</h2>
	<table style="border: 1px solid #AAA">
		<tr>
			<th>Förvaltning/Bolag</th>
			<th>Antal</th>
		</tr>
		<c:forEach items="${units}" var="unit" varStatus="status">
			<tr>
				<td>${unit.name}</td>
				<td><strong>${unitAdCount[status.index]}</strong></td>
			</tr>
		</c:forEach>
		<tr>
			<td>TOTALT</td>
			<td><strong>${totalNumberOfAds}</strong></td>
		</tr>
	</table>	
	
	<h2>Upplagda efterlysningar</h2>
	<table style="border: 1px solid #AAA">
		<tr>
			<th>Förvaltning/Bolag</th>
			<th>Antal</th>
		</tr>
		<c:forEach items="${units}" var="unit" varStatus="status">
			<tr>
				<td>${unit.name}</td>
				<td><strong>${unitRequestCount[status.index]}</strong></td>
			</tr>
		</c:forEach>
		<tr>
			<td>TOTALT</td>
			<td><strong>${totalNumberOfRequests}</strong></td>
		</tr>
	</table>	
	
	<h2>Bokade annonser</h2>
	<table style="border: 1px solid #AAA">
		<tr>
			<th>Förvaltning/Bolag</th>
			<th>Antal</th>
		</tr>
		<c:forEach items="${units}" var="unit" varStatus="status">
			<tr>
				<td>${unit.name}</td>
				<td><strong>${unitBookedCount[status.index]}</strong></td>
			</tr>
		</c:forEach>
		<tr>
			<td>TOTALT</td>
			<td><strong>${totalNumberOfBookedAds}</strong></td>
		</tr>
	</table>	
	
	<h2>Övrigt</h2>
	Antal icke bokade men utgångna annonser: <strong>${totalNumberOfExpiredAds}</strong>
</div>
