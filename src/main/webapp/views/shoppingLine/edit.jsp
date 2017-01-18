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


<form:form action="${requestURI }" modelAttribute="shoppingLine">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="shoppingCart"/>
	<form:hidden path="item"/>
	<form:hidden path="quantity"/>

	<fieldset>
	<legend align="left">
		<spring:message code="shoppingLine.fieldset1"></spring:message>
	</legend>
	
	<acme:formOut code="item.details.name" value="${shoppingLine.item.name}" path="item.name"/>
	<b><spring:message code="item.price"/></b>
	<jstl:out value="${lastHistoryPrice.distributorPrice.amount}"/>
	<jstl:out value="${lastHistoryPrice.distributorPrice.currency}"/>
	<br/>

	<acme:formOut code="item.details.category" value="${shoppingLine.item.category}" path="item.category"/>
	
	</fieldset>
	
	<spring:message code="shoppingLine.youwillbuy"/>
	<br/>
	<jstl:if test="${newItem==true}">
		<acme:submit name="save" code="shoppingLine.save"/>
		<br/>
	</jstl:if>
	<jstl:if test="${newItem==false}">
		<acme:submit name="add" code="shoppingLine.save"/>
		<br/>
		<spring:message code="shoppingLine.youwilladd"/>
		<br/>
	</jstl:if>

	<input type="button" name="return"
		value="<spring:message code="shoppingLine.return"/>"
		onClick="javascript: window.location.replace('item/list.do');"
	/>
</form:form>