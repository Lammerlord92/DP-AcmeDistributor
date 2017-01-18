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

<form:form action="distributor/register.do" modelAttribute="distributorForm">
	<fieldset>
		<legend align="left">
			<spring:message code="distributor.userAccount" />
		</legend>	
		<acme:textbox code="distributor.userAccount.username" path="userName"/>
		<acme:password code="distributor.userAccount.password" path="password"/>
		<acme:password code="distributor.userAccount.confirmPassword" path="confirmPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="distributor.personalInfo" />
		</legend>
			
		<acme:textbox code="distributor.name" path="name"/>
		<acme:textbox code="distributor.surname" path="surname"/>
		<acme:textbox code="distributor.email" path="email"/>
		<acme:textbox code="distributor.address" path="address"/>
		<acme:textbox code="distributor.contactPhone" path="contactPhone"/>
		<acme:textbox code="distributor.birthday" path="birthday"/>
	</fieldset>
	
	
	<p><acme:checkbox code="distributor.accepConditions" path="accepConditions"/>
	<a onClick="condiciones();" style="cursor:pointer;">(About)</a>
	</p>
	
	<input type="submit" name="save" value="<spring:message code="distributor.save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="distributor.cancel"/>" 
	onclick="javascript: window.location.replace('/AcmeDistributor')"/>
</form:form>