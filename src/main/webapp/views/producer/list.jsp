<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="producers" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

		<spring:message code="producer.code" var="code"/>
		<display:column property="code" title="${code}" sortable="true" />
		
		<spring:message code="producer.name" var="name"/>
		<display:column property="name" title="${name}" sortable="true" />
				
		<spring:message code="producer.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="true" />
				
		<spring:message code="producer.address" var="address"/>
		<display:column property="address" title="${address}" sortable="true" />
				
		<spring:message code="producer.birthday" var="birthday"/>
		<display:column property="birthday" title="${birthday}" sortable="true" />
				
		<spring:message code="producer.contactPhone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="true" />
				
		<spring:message code="producer.email" var="email"/>
		<display:column property="email" title="${email}" sortable="true" />	
					
</display:table>