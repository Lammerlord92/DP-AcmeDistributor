<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<fieldset>
	<legend align="left">
		<spring:message code="shoppingCart.fieldset1"></spring:message>
	</legend>

	<spring:message code="customer.name"/>
	<jstl:out value="${customer.name}"/>
	<br/>
	<spring:message code="customer.surname"/>
	<jstl:out value="${customer.surname}"/>
	<br/>
	<spring:message code="customer.address"/>
	<jstl:out value="${customer.address}"/>
	<br/>
	<spring:message code="customer.contactPhone"/>
	<jstl:out value="${customer.contactPhone}"/>
	<br/>
	<spring:message code="customer.email"/>
	<jstl:out value="${customer.email}"/>
	<br/>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="shoppingCart.fieldset2"></spring:message>
	</legend>
	
	<display:table name="lines" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="line.item.name" var="item.name"/>
		<display:column property="item.name" title="${item.name}" sortable="true" />
		
		<spring:message code="line.item.quantity" var="quantity"/>
		<display:column property="quantity" title="${quantity}" sortable="true" />
		
		<display:column>
			<a href="customer/delete.do?shoppingLineId=${row.id}">
				<spring:message code="shoppingLine.delete"/>
			</a>
		</display:column>
	</display:table>
</fieldset>

<input type="button" name="create" value="<spring:message code="invoice.create"/>" 
		onclick="javascript: window.location.replace('invoice/customer/create.do')"/>
<br />