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

	
<acme:formOut code="invoice.orderNumber" value="${orderNumber}" path="orderNumber"/>
<fieldset>
	<legend align="left">
		<spring:message code="invoice.fieldset1"></spring:message>
	</legend>
	
	<acme:formOut code="invoice.customer.name" path="customer.name" value="${customer.name}"/>
	<acme:formOut code="invoice.customer.surname" path="customer.surname" value="${customer.surname}"/>
	
	<acme:formOut code="invoice.customer.address" path="customer.address" value="${customer.address}"/>
	<acme:formOut code="invoice.customer.contactPhone" path="customer.contactPhone" value="${customer.contactPhone}"/>
	<acme:formOut code="invoice.customer.email" path="customer.email" value="${customer.email}"/>
</fieldset>
	
<fieldset>
	<legend align="left">
		<spring:message code="invoice.fieldset2"></spring:message>
	</legend>
	
	<display:table name="invoiceLines" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="invoice.line.name" var="item.name"/>
		<display:column property="item.name" title="${item.name}" sortable="true" />
		
		<spring:message code="invoice.line.quantity" var="quantity"/>
		<display:column property="quantity" title="${quantity}" sortable="true" />
		
	</display:table>
	<br/>
	<b><spring:message code="invoice.taxes"/></b>
	<br/>
	<display:table name="taxes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="tax.name" var="name"/>
		<display:column property="name" title="${name}" sortable="true" />
	
		<spring:message code="tax.percent" var="percent"/>
		<display:column property="percent" title="${percent}" sortable="true" />
	</display:table>
	<br/>
	<b><spring:message code="invoice.totalPrice"/></b>
	<br/>
	<acme:formOut code="invoice.totalPrice.amount" path="totalPrice.amount" value="${totalPrice.amount}"/>

	<acme:formOut code="invoice.totalPrice.currency" path="totalPrice.currency" value="${totalPrice.currency}"/>
	<br/>
</fieldset>

<acme:submit name="saveAsPDF" code="invoice.pdf"/>
<input type="button" name="return"
		value="<spring:message code="invoice.return"/>"
		onClick="javascript: window.location.replace('invoice/customer/myInvoices.do');"
	/>
		