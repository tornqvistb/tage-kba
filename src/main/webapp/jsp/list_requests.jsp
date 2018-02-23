<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><%@ page import="javax.portlet.PortletPreferences" %>
<portlet:defineObjects/>
<portlet:renderURL var="listMyRequestsUrl"><portlet:param name="page" value="listMyRequests"/></portlet:renderURL>

<div id="m-requested-items" class="m m-link-list">
	<div class="content-header cf">
		<a href="${listMyRequestsUrl}" class="button">Mina efterlysningar</a>
		<a href="${baseURI}?p_p_id=Retursidan_WAR_tageportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_Retursidan_WAR_tageportlet_page=createRequest" class="button primary-button">Lägg in efterlysning</a>
	</div>
	<h1>Efterlysningar</h1>
	<div class="m-h">
		<h2>Alla efterlysningar</h2>
	</div>
	<div class="m-c">
		<c:if test="${fn:length(requests) gt 0}">
			<ul>
				<c:forEach items="${requests}" var="request">
					<li>
						<a href="${baseURI}?p_p_id=Retursidan_WAR_tageportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_Retursidan_WAR_tageportlet_page=viewRequest&_Retursidan_WAR_tageportlet_requestId=${request.id}">${request.title}</a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${fn:length(requests) eq 0}">
			<div>Inga efterlysningar hittades.</div>
		</c:if>		
	</div>
</div>