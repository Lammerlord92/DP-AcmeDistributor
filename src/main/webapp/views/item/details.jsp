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


<fieldset>
	<legend align="left">
		<spring:message code="item.details1"/>
	</legend>
	
	<b><spring:message code="item.details.reference"/></b>
	<jstl:out value="${item.reference}"/>
	<br/>
	<b><spring:message code="item.producer"/></b>
	<jstl:out value="${item.producer.code}"/>
	<br/>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="item.details2"/>
	</legend>
	
	<b><spring:message code="item.details.name"/></b>
	<jstl:out value="${item.name}"/>
	<br/>
	<b><spring:message code="item.price"/></b>
	<jstl:out value="${lastHistoryPrice.distributorPrice.amount}"/>
	<jstl:out value="${lastHistoryPrice.distributorPrice.currency}"/>
	<br/>
	<b><spring:message code="item.details.category"/></b>
	<jstl:out value="${item.category}"/>
	<br/>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="item.details3"></spring:message>
	</legend>
	
	<display:table name="stocks" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
		<spring:message code="stock.warehouse" var="warehouseName"/>
		<display:column property="warehouse.name" title="${warehouseName}" sortable="true" />
		
		<spring:message code="stock.address" var="warehouseAddress"/>
		<display:column property="warehouse.address" title="${warehouseAddress}" sortable="true" />
		
		<spring:message code="stock.quantity" var="quantity"/>
		<display:column property="quantity" title="${quantity}" sortable="true" />
		
	</display:table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="item.details4"></spring:message>
	</legend>
	
	<display:table name="comments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
		<spring:message code="comment.text" var="text"/>
		<display:column property="text" title="${text}" sortable="true" />
		
		<spring:message code="comment.customer" var="customerUsername"/>
		<display:column property="customer.userAccount.username" title="${customerUsername}" sortable="true" />
		
	</display:table>
</fieldset>

<security:authorize access="hasRole('DISTRIBUTOR')">
	<fieldset>
		<legend align="left">
			<spring:message code="item.historyPrices"></spring:message>
		</legend>
	
		<display:table name="historyPrices" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
			
			<spring:message code="hp.startDate" var="startDate"/>
			<display:column property="startDate" title="${startDate}" sortable="true" />
			
			<spring:message code="hp.finishDate" var="finishDate"/>
			<display:column property="finishDate" title="${finishDate}" sortable="true" />
			
			<spring:message code="hp.distributorPrice" var="distributorPrice"/>
			<display:column property="distributorPrice" title="${distributorPrice}" sortable="true" />
			
			<spring:message code="hp.producerPrice" var="producerPrice"/>
			<display:column property="producerPrice" title="${producerPrice}" sortable="true" />
			
		</display:table>
</fieldset>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="item.return"/>" 
		onclick="javascript: window.location.replace('item/list.do')"/>
<br />