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


<form:form action="${requestURI }" modelAttribute="warehouse">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="stocks"/>
	<form:hidden path="distributor"/>
	
	<acme:textbox code="warehouse.name" path="name"/>
	<acme:textbox code="warehouse.address" path="address"/>
	<acme:textbox code="warehouse.contactPhone" path="contactPhone"/>
	<acme:textbox code="warehouse.email" path="email"/>
	
	<acme:submit name="save" code="warehouse.saveCreate"/>

	<input type="button" name="return"
		value="<spring:message code="warehouse.return"/>"
		onClick="javascript: window.location.replace('warehouse/distributor/list.do');"
	/>
</form:form>