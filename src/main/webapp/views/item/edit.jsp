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


<form:form action="${requestURI }" modelAttribute="itemForm">
	<form:hidden path="itemId"/>
	
	<acme:textbox code="item.reference" path="reference"/>
	<acme:textbox code="item.name" path="name"/>
	<acme:textbox code="item.category" path="category"/>
	<acme:textbox code="item.price.amount" path="price.amount"/>
	<acme:textbox code="item.price.currency" path="price.currency"/>
	
	
	<acme:submit name="save" code="item.save"/>

	<input type="button" name="return"
		value="<spring:message code="item.cancel"/>"
		onClick="javascript: window.location.replace('item/producer/list.do');"
	/>
</form:form>