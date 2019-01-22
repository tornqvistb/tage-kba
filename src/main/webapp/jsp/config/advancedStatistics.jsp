<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:resourceURL var="advancedStatisticsUrl" cacheability="cacheLevelFull" id="advancedStatistics"/><%
%><portlet:defineObjects/>



<div id="content-primary" class="cf" role="main">
	<p class="back-link"><a href="<portlet:renderURL/>">Tillbaka</a></p>
	<div class="content-header cf">
		<h1>Export av avancerad statistik</h1>
	</div>
	<h2>Exportera statistik för valfritt årtal</h2>
	<ul>
		<c:forEach items="${years}" var="year" varStatus="status">
			<li>
				<a href="${advancedStatisticsUrl}&year=${year}"><c:out value="${year}"/></a>
			</li>
		</c:forEach>
	</ul>
</div>	
