<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:form action="entryRegister/distributor/create.do" modelAttribute="entryRegister">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="item" />

	<form:select path="warehouse">
		<form:option label="----" value="0" />
		<form:options items="${warehouses}" itemLabel="name" itemValue="id" />
		<spring:message code="entryRegister.warehouse" />:
	</form:select>
	<acme:textbox code="entryRegister.quantity" path="quantity"/>

	<b><spring:message code="entryRegister.costUnit" />: </b>
	<jstl:out value="${amounts}" />
	<jstl:out value="${currencys}" />
	<div>
		<input type="submit" name="save"
			value="<spring:message code="entryRegister.create"/>" /> 
		&nbsp; 
		<input type="button" name="cancel"
			value="<spring:message code="entryRegister.cancel"/>"
			onClick="javascript: window.location.replace('item/list.do');" 
		/>
	</div>
</form:form>