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

<display:table name="warehouses" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="warehouse.actions" var="actions"/>
	<security:authorize access="hasRole('DISTRIBUTOR')">
		<jstl:if test="${isMine}">
			<display:column title="${actions}">
				<a href="warehouse/distributor/edit.do?warehouseId=${row.id}"><spring:message code="warehouse.edit"/></a>
				<br/>
				<a href="entryRegister/distributor/listByWarehouse.do?warehouseId=${row.id}"><spring:message code="warehouse.listEntryReg"/></a>
				<br/>
			</display:column>
		</jstl:if>
	</security:authorize>

	<display:column title="${actions}">
		<a href="item/listByWarehouse.do?warehouseId=${row.id}"><spring:message code="warehouse.listItems"/></a>
		<br/>
		<a href="warehouse/locationWarehouse.do?warehouseId=${row.id}"><spring:message code="warehouse.map"/></a>
		<br/>
	</display:column>
	<spring:message code="warehouse.name" var="name"/>
	<display:column property="name" title="${name}" sortable="true" />
	
	<spring:message code="warehouse.address" var="address"/>
	<display:column property="address" title="${address}" sortable="false" />
	
	<spring:message code="warehouse.contactPhone" var="contactPhone"/>
	<display:column property="contactPhone" title="${contactPhone}" sortable="false" />
	
	<spring:message code="warehouse.email" var="email"/>
	<display:column property="email" title="${email}" sortable="false" />
	
	<spring:message code="warehouse.distributor.name" var="nameD"/>
	<display:column property="distributor.name" title="${nameD}" sortable="true" />
</display:table>

<security:authorize access="hasRole('DISTRIBUTOR')">
	<jstl:if test="${isMine}">
		<input type="button" name="create" value="<spring:message code="warehouse.create"/>" 
			onclick="javascript: window.location.replace('warehouse/distributor/create.do')"/>
		<br />
	</jstl:if>
</security:authorize>