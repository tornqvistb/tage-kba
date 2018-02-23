<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:renderURL var="cancelUrl"/><%
%><portlet:defineObjects/><%
%><portlet:resourceURL id="uploadPhoto" var="uploadUrl"/><%
%><portlet:resourceURL id="removePhoto" var="removeUrl"/><%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="photo" cacheability="cacheLevelFull" var="photoUrl"/><%
%>

<script type="text/javascript">
	window.urlConfig = {
		uploadUrl: "${uploadUrl}", 
		removeUrl: "${removeUrl}", 
		thumbnailUrl: "${thumbnailUrl}", 
		photoUrl: "${photoUrl}"
	};
</script>

<jsp:include page="include/incl_res.jsp" />