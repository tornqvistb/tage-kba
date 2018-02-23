<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%><%
%><%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%><%
%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%
%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%
%><%@ page import="javax.portlet.PortletPreferences" %><%
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%
%><portlet:resourceURL id="thumbnail" cacheability="cacheLevelFull" var="thumbnailUrl"/><%
%><portlet:resourceURL id="photo" cacheability="cacheLevelFull" var="photoUrl"/><%
%><portlet:defineObjects/>

<%
PortletPreferences pref = renderRequest.getPreferences();
String baseURI=pref.getValue("baseURI","");
%>

<c:if test="${fn:length(ads.list) gt 0}">

	<div class="slider-box">
		<div class="flexslider">
			<ul class="slides cf">
				<c:forEach items="${ads.list}" var="ad">
					<c:if test="${fn:length(ad.photos) gt 0}">
						<c:forEach var="photo" items="${ad.photos}" end="0">
			     			<c:set var="imageUrl" value="${photoUrl}&id=${photo.id}"/>
							<li data-thumb="${photoUrl}&id=${photo.id}">
								<a href="${adsURL}">
									<img src="${photoUrl}&id=${photo.id}" alt="" class="slider-image"/>
								</a>
							</li>
						</c:forEach>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>	
</c:if>	
<script type="text/javascript">
$('.flexslider').flexslider({
    controlNav: false
});
</script>
<%-- 
 <c:if test="${fn:length(ads.list) gt 0}">
	<c:forEach items="${ads.list}" var="ad">
		<c:forEach var="photo" items="${ad.photos}" end="0">
			<c:set var="imageUrl" value="${thumbnailUrl}&id=${photo.id}"/>
			<img src="${imageUrl}" alt="" />
		</c:forEach>
	</c:forEach>
</c:if>
  --%>