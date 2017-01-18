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

<display:table name="taxes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<security:authorize access="hasRole('DISTRIBUTOR')">
		<display:column title="${sessionsCol}">
			<a href="tax/distributor/edit.do?taxId=${row.id}"><spring:message code="tax.edit"/></a>
			<br/>
		</display:column>
	</security:authorize>
	
	<spring:message code="tax.name" var="name"/>
	<display:column property="name" title="${name}" sortable="true" />
	
	<spring:message code="tax.percent" var="percent"/>
	<display:column property="percent" title="${percent}" sortable="true" />
</display:table>

<security:authorize access="hasRole('DISTRIBUTOR')">
	<input type="button" name="create" value="<spring:message code="tax.create"/>" 
		onclick="javascript: window.location.replace('tax/distributor/create.do')"/>
	<br />
</security:authorize>