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

<display:table name="customers" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column>
			<a href="customer/edit.do?customerId=${row.id}"><spring:message code="editDiscount"/></a>
			<br/>
		</display:column>
		
		<spring:message code="customer.code" var="code"/>
		<display:column property="code" title="${code}" sortable="true" />
		
		<spring:message code="customer.name" var="name"/>
		<display:column property="name" title="${name}" sortable="true" />
				
		<spring:message code="customer.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="true" />
				
		<spring:message code="customer.address" var="address"/>
		<display:column property="address" title="${address}" sortable="true" />
				
		<spring:message code="customer.birthday" var="birthday"/>
		<display:column property="birthday" title="${birthday}" sortable="true" />
				
		<spring:message code="customer.contactPhone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="true" />
				
		<spring:message code="customer.email" var="email"/>
		<display:column property="email" title="${email}" sortable="true" />	
					
		<spring:message code="customer.discount" var="discount"/>
		<display:column property="discount" title="${discount}" sortable="true" />		
		
</display:table>