<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:defineObjects/>

<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() +  "/external/js/photo_handling.js")%>" type="text/javascript"></script>
<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() +  "/external/js/fileuploader.js")%>" type="text/javascript"></script>
<script src="<%= renderResponse.encodeURL(renderRequest.getContextPath() +  "/external/js/label_printing.js")%>" type="text/javascript"></script>
<link rel="stylesheet" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() +  "/external/css/fileuploader.css")%>" type="text/css" />
<link rel="stylesheet" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() +  "/external/css/tage-custom.css")%>" type="text/css" />
<link rel="stylesheet" href="<%= renderResponse.encodeURL(renderRequest.getContextPath() +  "/external/css/print.css")%>" type="text/css" media="print"/>
 