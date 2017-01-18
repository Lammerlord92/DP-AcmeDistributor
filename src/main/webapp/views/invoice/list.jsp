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

<display:table name="invoices" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column title="${sessionsCol}">
				<a href="invoice/customer/details.do?invoiceId=${row.id}"><spring:message code="comment.viewInvoice"/></a>
			</display:column>
			<display:column title="${sessionsCol}">
				<a href="invoice/customer/savePDF.do?invoiceId=${row.id}"><spring:message code="invoice.pdf"/></a>
			</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('DISTRIBUTOR')">
			<display:column title="${sessionsCol}">
				<a href="invoice/distributor/details.do?invoiceId=${row.id}"><spring:message code="comment.viewInvoice"/></a>
			</display:column>
		</security:authorize>
		
		<spring:message code="invoice.creationDate" var="creationDate"/>
		<display:column property="creationDate" title="${creationDate}" sortable="true" />
		
		<spring:message code="invoice.totalPrice.amount" var="totalPrice.amount"/>
		<display:column property="totalPrice.amount" title="${totalPrice.amount}" sortable="false" />
		
		<spring:message code="invoice.totalPrice.currency" var="totalPrice.currency"/>
		<display:column property="totalPrice.currency" title="${totalPrice.currency}" sortable="false" />
		
		<spring:message code="invoice.orderNumber" var="orderNumber"/>
		<display:column property="orderNumber" title="${orderNumber}" sortable="false" />
		
		<security:authorize access="hasRole('DISTRIBUTOR')">
			<spring:message code="invoice.customer" var="customer"/>
			<display:column property="customer.name" title="${customer}" sortable="true" />
		</security:authorize>		
</display:table>