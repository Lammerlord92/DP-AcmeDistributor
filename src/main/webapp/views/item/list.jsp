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

<display:table name="items" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column title="${sessionsCol}">
				<a href="comment/customer/create.do?itemId=${row.id}"><spring:message code="item.comment"/></a>
				<br/>
				<a href="shoppingLine/customer/create.do?itemId=${row.id}"><spring:message code="item.addToCart"/></a>
				<br/>
			</display:column>
		</security:authorize>
		<security:authorize access="hasRole('DISTRIBUTOR')">
			<display:column title="${sessionsCol}">
				<a href="entryRegister/distributor/create.do?itemId=${row.id}"><spring:message code="item.createEntryReg"/></a>
				<br/>
			</display:column>
		</security:authorize>		
		<display:column title="${sessionsCol}">
			<security:authorize access="hasRole('PRODUCER')">
				<jstl:if test="${requestURI=='item/producer/list.do'}">
					<a href="item/producer/edit.do?itemId=${row.id}"><spring:message code="item.edit"/></a>
					<br/>
				</jstl:if>
			</security:authorize>
			<a href="item/details.do?itemId=${row.id}"><spring:message code="item.details"/></a>
			<br/>
		</display:column>
		
		<spring:message code="item.reference" var="reference"/>
		<display:column property="reference" title="${reference}" sortable="true" />
		
		<spring:message code="item.name" var="name"/>
		<display:column property="name" title="${name}" sortable="true" />
		
		<spring:message code="item.category" var="category"/>
		<display:column property="category" title="${category}" sortable="true" />
		
</display:table>

<security:authorize access="hasRole('PRODUCER')">
	<jstl:if test="${isMine}">
		<input type="button" name="create" value="<spring:message code="item.create"/>" 
			onclick="javascript: window.location.replace('item/producer/create.do')"/>
		<br />
	</jstl:if>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="item.return"/>" 
		onclick="javascript: window.location.replace('warehouse/list.do')"/>
<br />