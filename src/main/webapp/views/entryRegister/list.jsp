<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<display:table name="entryRegisters" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="entryRegisters.item" var="item"/>
		<display:column property="item.name" title="${item}" sortable="false" />
		
		<spring:message code="entryRegisters.creationDate" var="creationDate"/>
		<display:column property="creationDate" title="${creationDate}" sortable="true" />
		
		<spring:message code="entryRegister.quantity" var="quantity"/>
		<display:column property="quantity" title="${quantity}" sortable="true" />
		
		<spring:message code="entryRegisters.totalPrice" var="totalPrice"/>
		<display:column property="totalPrice" title="${totalPrice}" sortable="true" />
	</display:table>

