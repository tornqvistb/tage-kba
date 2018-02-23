<%@page import="org.springframework.validation.ObjectError"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="backUrl" /><%
%><portlet:defineObjects/>


<jsp:include page="include/incl_res.jsp" />

<div id="content-primary" class="article cf" role="main">
	<p class="back-link"><a href="${backUrl}">Tillbaka</a></p>
	<h1>Ett fel uppstod när ${advertisement.title} skulle bokas</h1>
	<div class="system-info confirmation-message">
		<h2>${errorMessage}</h2>
		<p>Din bokning har inte genomförts, klicka på tillbaka-länken ovan för att börja om och söka efter en annan annons.</p>
	</div>	
</div> 